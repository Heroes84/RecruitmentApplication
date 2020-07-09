package de.ynd.di.module.schedulers

import dagger.Binds
import dagger.Module
import de.ynd.domain.rx.SchedulerProviderDelegate
import de.ynd.domain.rx.SchedulerProviderDelegateImpl

@Module
abstract class SchedulerProviderModule {

    @Binds
    abstract fun bindsSchedulerProviderDelegate(
        schedulerProviderDelegateImpl: SchedulerProviderDelegateImpl
    ): SchedulerProviderDelegate
}