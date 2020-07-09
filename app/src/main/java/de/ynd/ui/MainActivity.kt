package de.ynd.ui

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import de.ynd.R

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
    }
}