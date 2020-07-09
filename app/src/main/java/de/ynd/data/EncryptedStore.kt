package de.ynd.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import io.reactivex.Single
import javax.inject.Inject

const val STORE_KEY_PASSWORD = "pass"

private const val FILENAME = "data"

class EncryptedStore @Inject constructor(
    context: Context
) {

    private val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        FILENAME,
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun set(key: String, data: String): Single<Boolean> =
        Single.create { emitter ->
            val result = encryptedSharedPreferences
                .edit()
                .putString(key, data)
                .commit()

            emitter.onSuccess(result)
        }

    fun get(key: String) =
        Single.create<String> { emitter ->
            val data = encryptedSharedPreferences.getString(key, "")

            if (data.isNullOrEmpty()) {
                emitter.onError(IllegalArgumentException("$key is not saved in encryptedSharedPref"))
            } else {
                emitter.onSuccess(data)
            }
        }

    fun exists(key: String) =
        Single.just(encryptedSharedPreferences.contains(key))
}