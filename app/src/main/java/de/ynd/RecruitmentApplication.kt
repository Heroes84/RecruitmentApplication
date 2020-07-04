package de.ynd

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import de.ynd.di.component.DaggerAppComponent

class RecruitmentApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().application(this).build()
}