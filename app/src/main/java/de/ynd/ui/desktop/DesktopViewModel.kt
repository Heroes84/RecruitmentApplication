package de.ynd.ui.desktop

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import de.ynd.domain.*
import de.ynd.ui.component.SingleLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class DesktopViewModel @Inject constructor(
    application: Application,
    private val checkPasswordUseCaseUseCase: PasswordCheckerUseCase,
    private val setPasswordUseCase: SetPasswordUseCase,
    private val saveAndEncryptPhotoUseCase: SaveAndEncryptPhotoUseCase,
    private val getPhotosUseCase: GetPhotosUseCase,
    private val verifyPasswordUseCase: VerifyPasswordUseCase
) : AndroidViewModel(application) {

    val canShowSavePasswordDialog = SingleLiveData<Boolean>()
    val canShowPasswordDialog = SingleLiveData<Boolean>()
    val authError = SingleLiveData<Boolean>()
    val takePhoto = SingleLiveData<Boolean>()
    val photoList = MutableLiveData<List<PhotoItem>>()

    private val disposables = CompositeDisposable()
    private var isPasswordSet = false
    private var isVerified = false

    init {
        checkPassword()

        if (isVerified) {
            loadPhotos()
        }
    }

    override fun onCleared() {
        super.onCleared()

        disposables.clear()
    }

    fun onNewPhoto(folder: File, bitmap: Bitmap) {
        saveAndEncryptPhotoUseCase(folder, bitmap)
            .subscribeBy(
                onError = { Timber.d(it) },
                onComplete = {
                    if (isVerified) {
                        loadPhotos()
                    }
                }
            )
            .addTo(disposables)
    }

    fun onTakePhotoClicked() {
        takePhoto.value = isPasswordSet
    }

    fun onLockIcon() {
        when {
            !isPasswordSet -> {
                canShowSavePasswordDialog.value = true
            }
            isPasswordSet && isVerified -> {
                isVerified = false
                photoList.value = emptyList()
            }
            else -> canShowPasswordDialog.value = true
        }
    }

    fun onSetPassword(password: String) =
        setPasswordUseCase(password)
            .subscribeBy(
                onError = { Timber.e(it) },
                onComplete = { isPasswordSet = true }
            )
            .addTo(disposables)

    fun verifyPassword(password: String) {
        verifyPasswordUseCase(password)
            .subscribeBy(
                onError = { Timber.e(it) },
                onSuccess = {
                    isVerified = it
                    loadPhotos()
                }
            )
            .addTo(disposables)
    }

    private fun checkPassword() =
        checkPasswordUseCaseUseCase()
            .subscribeBy(
                onSuccess = {
                    isPasswordSet = it
                    canShowSavePasswordDialog.value = !it
                },
                onError = { Timber.e(it) }
            )
            .addTo(disposables)

    private fun loadPhotos() =
        getPhotosUseCase()
            .subscribeBy(
                onError = { Timber.d(it) },
                onSuccess = { photoList.value = it }
            )
            .addTo(disposables)

}