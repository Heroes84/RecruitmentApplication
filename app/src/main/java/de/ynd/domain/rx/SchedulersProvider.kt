package de.ynd.domain.rx

import io.reactivex.*
import javax.inject.Inject

interface SchedulersProvider {
    fun io(): Scheduler
    fun computation(): Scheduler
    fun ui(): Scheduler
}

interface SchedulerProviderDelegate {
    fun <T> Single<T>.connectIo(): Single<T>
    fun <T> Flowable<T>.connectIo(): Flowable<T>
    fun <T> Maybe<T>.connectIo(): Maybe<T>
    fun <T> Observable<T>.connectIo(): Observable<T>
    fun Completable.connectIo(): Completable

    fun <T> Single<T>.connectComputation(): Single<T>
    fun <T> Flowable<T>.connectComputation(): Flowable<T>
    fun <T> Maybe<T>.connectComputation(): Maybe<T>
    fun <T> Observable<T>.connectComputation(): Observable<T>
    fun Completable.connectComputation(): Completable
}

class SchedulerProviderDelegateImpl @Inject constructor(
    private val schedulersProvider: SchedulersProvider
) : SchedulerProviderDelegate {
    override fun <T> Single<T>.connectIo(): Single<T> {
        return this
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
    }

    override fun <T> Flowable<T>.connectIo(): Flowable<T> {
        return this
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
    }

    override fun <T> Maybe<T>.connectIo(): Maybe<T> {
        return this
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
    }

    override fun <T> Observable<T>.connectIo(): Observable<T> {
        return this
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
    }

    override fun Completable.connectIo(): Completable {
        return this
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
    }

    override fun <T> Single<T>.connectComputation(): Single<T> {
        return this
            .subscribeOn(schedulersProvider.computation())
            .observeOn(schedulersProvider.ui())
    }

    override fun <T> Flowable<T>.connectComputation(): Flowable<T> {
        return this
            .subscribeOn(schedulersProvider.computation())
            .observeOn(schedulersProvider.ui())
    }

    override fun <T> Maybe<T>.connectComputation(): Maybe<T> {
        return this
            .subscribeOn(schedulersProvider.computation())
            .observeOn(schedulersProvider.ui())
    }

    override fun <T> Observable<T>.connectComputation(): Observable<T> {
        return this
            .subscribeOn(schedulersProvider.computation())
            .observeOn(schedulersProvider.ui())
    }

    override fun Completable.connectComputation(): Completable {
        return this
            .subscribeOn(schedulersProvider.computation())
            .observeOn(schedulersProvider.ui())
    }
}