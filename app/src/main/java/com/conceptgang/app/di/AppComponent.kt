package com.conceptgang.app.di

import android.content.Context
import android.content.SharedPreferences
import com.conceptgang.app.MainApp
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope

@Component
@AppScope
interface AppComponent {

    @Component.Factory
    interface Factory {

        fun create(
            @Named(MainApp.NAME) @BindsInstance applicationContext: Context,
            @BindsInstance retrofit: Retrofit,
            @BindsInstance prefs: SharedPreferences
        ): AppComponent
    }

    fun retrofit(): Retrofit
}