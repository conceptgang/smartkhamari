package com.conceptgang.component.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.conceptgang.component.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.DescendantOffsetUtils;

import java.util.List;
import java.util.Timer;

import static android.view.View.VISIBLE;

class SearchBarBehavior extends CoordinatorLayout.Behavior<SearchBar> {

    private Rect tmpRect;
    private boolean autoHideEnabled = true;

    public SearchBarBehavior() {
        super();
    }

    public SearchBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull SearchBar child, @NonNull View dependency) {

        if (dependency instanceof AppBarLayout) {
            // If we're depending on an AppBarLayout we will show/hide it automatically
            // if the FAB is anchored to the AppBarLayout
            updateFabVisibilityForAppBarLayout(parent, (AppBarLayout) dependency, child);
        }
        return false;
    }

    private boolean shouldUpdateVisibility(@NonNull View dependency, @NonNull SearchBar child) {
        final CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (!autoHideEnabled) {
            return false;
        }

        if (lp.getAnchorId() != dependency.getId()) {
            // The anchor ID doesn't match the dependency, so we won't automatically
            // show/hide the FAB
            return false;
        }

        return true;
    }

    private boolean updateFabVisibilityForAppBarLayout(
            CoordinatorLayout parent,
            @NonNull AppBarLayout appBarLayout,
            @NonNull SearchBar child) {
        if (!shouldUpdateVisibility(appBarLayout, child)) {
            return false;
        }

        if (tmpRect == null) {
            tmpRect = new Rect();
        }

        // First, let's get the visible rect of the dependency
        final Rect rect = tmpRect;
        ZHDescendantOffsetUtils.getDescendantRect(parent, appBarLayout, rect);


        Log.d("ZHBehavior", String.format("%d %d", rect.bottom, child.getBottom()));


        if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
            // If the anchor's bottom is below the seam, we'll animate our FAB out
            //child.setVisibility(VISIBLE);
            child.setTranslationY(24);

        } else {
            // Else, we'll animate our FAB back in
            //child.setVisibility(VISIBLE);
            child.setTranslationY(-(child.getHeight()/2));

        }
        return true;
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull SearchBar child, int layoutDirection) {
        // First, let's make sure that the visibility of the FAB is consistent
        final List<View> dependencies = parent.getDependencies(child);
        for (int i = 0, count = dependencies.size(); i < count; i++) {
            final View dependency = dependencies.get(i);
            if (dependency instanceof AppBarLayout) {
                if (updateFabVisibilityForAppBarLayout(parent, (AppBarLayout) dependency, child)) {
                    break;
                }
            }
        }
        // Now let the CoordinatorLayout lay out the FAB
        parent.onLayoutChild(child, layoutDirection);
        return true;
    }
}


class ZHDescendantOffsetUtils {
    private static final ThreadLocal<Matrix> matrix = new ThreadLocal<>();
    private static final ThreadLocal<RectF> rectF = new ThreadLocal<>();

    /**
     * This is a port of the common {@link ViewGroup#offsetDescendantRectToMyCoords(View, Rect)} from
     * the framework, but adapted to take transformations into account. The result will be the
     * bounding rect of the real transformed rect.
     *
     * @param descendant view defining the original coordinate system of rect
     * @param rect (in/out) the rect to offset from descendant to this view's coordinate system
     */
    public static void offsetDescendantRect(
            @NonNull ViewGroup parent, @NonNull View descendant, @NonNull Rect rect) {
        Matrix m = matrix.get();
        if (m == null) {
            m = new Matrix();
            matrix.set(m);
        } else {
            m.reset();
        }

        offsetDescendantMatrix(parent, descendant, m);

        RectF rectF = ZHDescendantOffsetUtils.rectF.get();
        if (rectF == null) {
            rectF = new RectF();
            ZHDescendantOffsetUtils.rectF.set(rectF);
        }
        rectF.set(rect);
        m.mapRect(rectF);
        rect.set(
                (int) (rectF.left + 0.5f),
                (int) (rectF.top + 0.5f),
                (int) (rectF.right + 0.5f),
                (int) (rectF.bottom + 0.5f));
    }

    /**
     * Retrieve the transformed bounding rect of an arbitrary descendant view. This does not need to
     * be a direct child.
     *
     * @param descendant descendant view to reference
     * @param out rect to set to the bounds of the descendant view
     */
    public static void getDescendantRect(
            @NonNull ViewGroup parent, @NonNull View descendant, @NonNull Rect out) {
        out.set(0, 0, descendant.getWidth(), descendant.getHeight());
        offsetDescendantRect(parent, descendant, out);
    }

    private static void offsetDescendantMatrix(
            ViewParent target, @NonNull View view, @NonNull Matrix m) {
        final ViewParent parent = view.getParent();
        if (parent instanceof View && parent != target) {
            final View vp = (View) parent;
            offsetDescendantMatrix(target, vp, m);
            m.preTranslate(-vp.getScrollX(), -vp.getScrollY());
        }

        m.preTranslate(view.getLeft(), view.getTop());

        if (!view.getMatrix().isIdentity()) {
            m.preConcat(view.getMatrix());
        }
    }
}