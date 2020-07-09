package de.ynd.domain

import de.ynd.data.EncryptedStore
import de.ynd.data.STORE_KEY_PASSWORD
import de.ynd.domain.rx.SchedulerProviderDelegate
import io.reactivex.Completable
import javax.inject.Inject

class SetPasswordUseCase @Inject constructor(
    private val encryptedStore: EncryptedStore,
    private val schedulerProviderDelegate: SchedulerProviderDelegate
) : (String) -> Completable, SchedulerProviderDelegate by schedulerProviderDelegate {

    override fun invoke(password: String) =
        encryptedStore
            .set(STORE_KEY_PASSWORD, password)
            .ignoreElement()
            .connectComputation()
}