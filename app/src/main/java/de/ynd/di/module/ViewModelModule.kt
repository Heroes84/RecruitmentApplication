package de.ynd.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import de.ynd.di.InjectingViewModelFactory
import de.ynd.di.ViewModelKey
import de.ynd.ui.desktop.DesktopViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DesktopViewModel::class)
    abstract fun bindDesktopViewModel(viewModel: DesktopViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: InjectingViewModelFactory): ViewModelProvider.Factory
}