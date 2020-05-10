package com.conceptgang.app.di

import com.conceptgang.app.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

@Component(modules = [ActivityModule::class], dependencies = [AppComponent::class])
@ActivityScope
interface ActivityComponent {

    @Component.Factory
    interface Factory{
        fun create(
            appComponent: AppComponent,
            @BindsInstance activity: MainActivity
        ): ActivityComponent
    }


}

@Module
object ActivityModule {

}