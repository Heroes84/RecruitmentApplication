package de.ynd.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.ynd.ui.desktop.DesktopFragment

@Module
abstract class FragmentProviderModule {

    @ContributesAndroidInjector
    abstract fun provideDesktopFragment(): DesktopFragment
}