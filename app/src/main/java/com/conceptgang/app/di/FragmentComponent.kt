package com.conceptgang.app.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope

@Component(modules = [FragmentModule::class], dependencies = [ActivityComponent::class])
@FragmentScope
interface FragmentComponent{

    @Component.Factory
    interface Factory{
        fun create(
            activityComponent: ActivityComponent,
            @BindsInstance fragment: Fragment
        ):FragmentComponent
    }

}

@Module
object FragmentModule{

//    @Provides
//    fun getDashboardClient(retrofit: Retrofit) = retrofit.create(TaalaDashboardClient::class.java)

}