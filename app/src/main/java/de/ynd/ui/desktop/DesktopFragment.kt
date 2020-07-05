package de.ynd.ui.desktop

import de.ynd.ui.view.BaseFragment
import de.ynd.ui.view.viewModelDelegate

class DesktopFragment : BaseFragment() {

    private val viewModel by lazy { viewModelDelegate<DesktopViewModel>() }
}