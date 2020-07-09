package de.ynd.domain

import de.ynd.data.EncryptedStore
import de.ynd.data.STORE_KEY_PASSWORD
import io.reactivex.Single
import javax.inject.Inject

class PasswordCheckerUseCase @Inject constructor(
    private val encryptedStore: EncryptedStore
): () -> Single<Boolean> {

    override fun invoke() =
        encryptedStore.exists(STORE_KEY_PASSWORD)
}