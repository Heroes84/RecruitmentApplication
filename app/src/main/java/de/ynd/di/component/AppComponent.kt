package de.ynd.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import de.ynd.di.module.ActivityProviderModule
import de.ynd.di.module.AndroidModule
import de.ynd.di.module.FragmentProviderModule
import de.ynd.di.module.ViewModelModule
import de.ynd.di.module.schedulers.SchedulerProviderModule
import de.ynd.di.module.schedulers.SchedulersModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityProviderModule::class,
        FragmentProviderModule::class,
        SchedulerProviderModule::class,
        SchedulersModule::class,
        AndroidModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<de.ynd.RecruitmentApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}