package com.conceptgang.component.util

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.res.getDimensionOrThrow
import androidx.core.content.res.getDimensionPixelSizeOrThrow
import androidx.core.content.res.use
import androidx.core.graphics.ColorUtils
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

inline val @receiver:ColorInt Int.shade200
    @ColorInt
    get() = ColorUtils.blendARGB(this, Color.WHITE, 0.43f)


inline val @receiver:ColorInt Int.shade900
    @ColorInt
    get() = ColorUtils.blendARGB(this, Color.BLACK, 0.43f)

inline val @receiver:ColorInt Int.shade100
    @ColorInt
    get() = ColorUtils.blendARGB(this, Color.WHITE, 0.90f)


//if you want to use shade for background then the ratio would be 95
inline val @receiver:ColorInt Int.transParentBG
    @ColorInt
    get() = Color.argb(30, Color.red(this), Color.green(this), Color.blue(this))


//val Int.dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val <T> T.exhaustive: T
    get() = this


@ColorInt
fun Context.getThemeColor(@AttrRes attr: Int): Int {
    return obtainStyledAttributes(intArrayOf(attr)).use { it.getColorOrThrow(0) }
}

@Dimension
fun Context.getThemeDimen(@AttrRes attr: Int): Int {
    return obtainStyledAttributes(intArrayOf(attr)).use { it.getDimensionPixelSizeOrThrow(0) }
}


fun ImageView.changeImageColor(@ColorInt colorInt: Int) {
    val porterDuffColorFilter = PorterDuffColorFilter(colorInt, PorterDuff.Mode.SRC_ATOP)
    this.colorFilter = porterDuffColorFilter
}

@ExperimentalCoroutinesApi
fun EditText.asFlow(): Flow<CharSequence> = callbackFlow {
    val watcher = this@asFlow.doAfterTextChanged {
        offer(it.toString())
    }
    awaitClose { this@asFlow.removeTextChangedListener(watcher) }
}

fun Uri.getName(contentResolver: ContentResolver): String {


    // The query, because it only applies to a single document, returns only
    // one row. There's no need to filter, sort, or select fields,
    // because we want all fields for one document.
    val cursor: Cursor? = contentResolver.query(
        this, null, null, null, null, null
    )

    var displayName: String = "unknown"

    try {
        cursor?.use {
            // moveToFirst() returns false if the cursor has 0 rows. Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (it.moveToFirst()) {

                // Note it's called "Display Name". This is
                // provider-specific, and might not necessarily be the file name.
                displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
    } catch (ex: Exception) {

    }

    return displayName

}

fun Uri.getSize(contentResolver: ContentResolver): String {


    // The query, because it only applies to a single document, returns only
    // one row. There's no need to filter, sort, or select fields,
    // because we want all fields for one document.
    val cursor: Cursor? = contentResolver.query(
        this, null, null, null, null, null
    )

    var displaySize: String = "0"

    try {
        cursor?.use {
            // moveToFirst() returns false if the cursor has 0 rows. Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (it.moveToFirst()) {

                val sizeIndex: Int = it.getColumnIndex(OpenableColumns.SIZE)
                // If the size is unknown, the value stored is null. But because an
                // int can't be null, the behavior is implementation-specific,
                // and unpredictable. So as
                // a rule, check if it's null before assigning to an int. This will
                // happen often: The storage API allows for remote files, whose
                // size might not be locally known.
                displaySize = if (!it.isNull(sizeIndex)) {
                    // Technically the column stores an int, but cursor.getString()
                    // will do the conversion automatically.
                    it.getString(sizeIndex)
                } else {
                    "0"
                }
            }
        }
    } catch (ex: Exception) {

    }

    return displaySize

}

fun Uri.getType(contentResolver: ContentResolver): String {
    val mime = MimeTypeMap.getSingleton()
    var type: String = "unknown"

    try {
        mime.getExtensionFromMimeType(contentResolver.getType(this))?.let { type = it }
    } catch (ex: Exception) {

    }

    return type
}

fun Set<Uri>.getTotalSize(contentResolver: ContentResolver): Long =
    this.map { it.getSize(contentResolver).toLong() }.sum()

fun TextView.setUpTextAppearance(@StyleRes textAppearance: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setTextAppearance(textAppearance)
    } else {
        setTextAppearance(context, textAppearance)
    }
}


/**
 * https://stackoverflow.com/a/49147787/6307259
 * If no window token is found, keyboard is checked using
 * reflection to know if keyboard visibility toggle is needed
 *
 * @param useReflection - whether to use reflection in case
 * of no window token or not
 */
fun Fragment.hideKeyboard(context: Context = requireContext(), useReflection: Boolean = true) {
    val windowToken = view?.rootView?.windowToken
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    windowToken?.let {
        imm.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    } ?: run {
        if (useReflection) {
            try {
                if (getKeyboardHeight(imm) > 0) {
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
                }
            } catch (exception: Exception) {

            }
        }
    }
}

/*
*
* https://stackoverflow.com/a/55401335/6307259
* */

fun Fragment.showKeyboard(view: View) {
    if (view.requestFocus()) {
        val imm =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        //imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }
}

fun getKeyboardHeight(imm: InputMethodManager): Int =
    InputMethodManager::class
        .java
        .getMethod("getInputMethodWindowVisibleHeight")
        .invoke(imm) as Int