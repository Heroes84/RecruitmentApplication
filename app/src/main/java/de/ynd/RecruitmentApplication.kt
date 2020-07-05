package de.ynd

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import de.ynd.di.component.DaggerAppComponent
import timber.log.Timber

class RecruitmentApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().application(this).build()

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}