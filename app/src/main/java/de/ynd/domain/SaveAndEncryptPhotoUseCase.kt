package de.ynd.domain

import android.content.Context
import android.graphics.Bitmap
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import io.reactivex.Completable
import okio.buffer
import okio.sink
import de.ynd.domain.rx.SchedulerProviderDelegate
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import javax.inject.Inject

class SaveAndEncryptPhotoUseCase @Inject constructor(
    private val context: Context,
    private val schedulerProviderDelegate: SchedulerProviderDelegate
) : (File, Bitmap) -> Completable, SchedulerProviderDelegate by schedulerProviderDelegate {

    override fun invoke(folder: File, file: Bitmap) =
        Completable.create { emitter ->
            if (!emitter.isDisposed) {
                val sink = createEncryptedFile(folder, createFilename())
                    .openFileOutput()
                    .sink()
                    .buffer()

                sink.write(file.convertToByteArray())
                sink.close()
            }

            file.recycle()
            emitter.onComplete()
        }.connectIo()

    private fun createEncryptedFile(folder: File, filename: String) =
        EncryptedFile.Builder(
            File(folder, filename),
            context,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

    private fun createFilename() = Date().time.toString()

    private fun Bitmap.convertToByteArray(): ByteArray {
        val blob = ByteArrayOutputStream()
        this.compress(
            Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob
        )

        return blob.toByteArray()
    }

}
