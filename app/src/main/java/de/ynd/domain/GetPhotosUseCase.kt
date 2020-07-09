package de.ynd.domain

import android.content.Context
import android.graphics.BitmapFactory
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import de.ynd.domain.rx.SchedulerProviderDelegate
import de.ynd.ui.desktop.PhotoItem
import io.reactivex.Single
import okio.buffer
import okio.source
import timber.log.Timber
import java.io.File
import javax.inject.Inject

private const val BITMAP_OFFSET = 0

class GetPhotosUseCase @Inject constructor(
    private val context: Context,
    private val schedulerProviderDelegate: SchedulerProviderDelegate
) : () -> Single<List<PhotoItem>>, SchedulerProviderDelegate by schedulerProviderDelegate {

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun invoke() =
        Single.create<List<PhotoItem>> { emitter ->
            val list = mutableListOf<PhotoItem>()

            try {
                context
                    .filesDir
                    .walkTopDown()
                    .forEach {
                        Timber.d("file: ${it.name} is ${it.isFile}")
                        if (it.isFile) {
                            processEncryptedFile(it, list)
                        }
                    }

            } catch (exception: Exception) {
                emitter.tryOnError(exception) //TODO for demo purposes catch the all !
            }

            emitter.onSuccess(list)

        }.connectIo()

    private fun processEncryptedFile(
        file: File,
        list: MutableList<PhotoItem>
    ) {
        val sourceByteArray = createDecryptedFile(file)
            .openFileInput()
            .source()
            .buffer()
            .readByteArray()

        list.add(
            PhotoItem(
                BitmapFactory.decodeByteArray(sourceByteArray, BITMAP_OFFSET, sourceByteArray.size)
            )
        )
    }

    private fun createDecryptedFile(file: File) =
        EncryptedFile.Builder(
            file,
            context,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()
}