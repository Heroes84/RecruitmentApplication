package de.ynd.ui.desktop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.ynd.ui.view.BaseFragment
import de.ynd.ui.view.viewModelDelegate

class DesktopFragment : BaseFragment() {

    private val viewModel by lazy { viewModelDelegate<DesktopViewModel>() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}