package de.ynd.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.ynd.ui.MainActivity

@Module
abstract class ActivityProviderModule {

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity
}
