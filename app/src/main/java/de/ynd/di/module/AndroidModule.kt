package de.ynd.di.module

import android.app.Application
import dagger.Module
import dagger.Provides

private const val DEFAULT = "default"

@Module
class AndroidModule {

    @Provides
    fun providesContext(application: Application) = application.baseContext

}