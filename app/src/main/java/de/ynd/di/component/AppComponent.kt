package de.ynd.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import de.ynd.di.module.ViewModelModule

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent: AndroidInjector<de.ynd.RecruitmentApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}