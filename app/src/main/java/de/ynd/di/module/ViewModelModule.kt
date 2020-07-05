package de.ynd.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import de.ynd.di.ViewModelKey
import de.ynd.ui.desktop.DesktopViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DesktopViewModel::class)
    abstract fun bindSplashViewModel(viewModel: DesktopViewModel): ViewModel
}