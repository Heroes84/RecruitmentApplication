package de.ynd.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import de.ynd.R
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
    }
}