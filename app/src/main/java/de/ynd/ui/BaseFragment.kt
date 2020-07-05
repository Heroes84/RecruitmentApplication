package de.ynd.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

open class BaseFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
}

inline fun <reified T : ViewModel> BaseFragment.viewModelDelegate(): T =
    ViewModelProvider(this, this.viewModelFactory)[T::class.java]