package de.ynd.domain

import de.ynd.data.EncryptedStore
import de.ynd.data.STORE_KEY_PASSWORD
import io.reactivex.Single
import javax.inject.Inject

class VerifyPasswordUseCase @Inject constructor(
    private val encryptedStore: EncryptedStore
): (String) -> Single<Boolean> {

    override fun invoke(password: String) =
        encryptedStore.get(STORE_KEY_PASSWORD)
            .flatMap {
                Single.just(
                    password == it
                )
            }
}