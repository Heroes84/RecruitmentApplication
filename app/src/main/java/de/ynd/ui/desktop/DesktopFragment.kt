package de.ynd.ui.desktop

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import de.ynd.R
import de.ynd.databinding.DesktopFragmentBinding
import de.ynd.ui.BaseFragment
import de.ynd.ui.viewModelDelegate
import timber.log.Timber

private val PERMISSIONS = arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.CAMERA
)

private const val REQUEST_CODE_PERMISSIONS = 11
private const val REQUEST_CODE_TAKE_PHOTO = 12

private const val EXTRAS_PHOTO = "data"

class DesktopFragment : BaseFragment() {

    private lateinit var binding: DesktopFragmentBinding
    private val viewModel by lazy { viewModelDelegate<DesktopViewModel>() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        DesktopFragmentBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.apply {
            setNavigationOnClickListener { requireActivity().finish() }
            setOnMenuItemClickListener { handleToolbarMenu(it) }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_PERMISSIONS -> {
                if (verifyAllPermissionsGranted(grantResults)) {
                    takePhoto()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        when (requestCode) {
            REQUEST_CODE_TAKE_PHOTO -> {
                Timber.d("Have photo !")
                if (resultCode == RESULT_OK && (data?.extras?.containsKey(EXTRAS_PHOTO) == true)) {
                    viewModel.onNewPhoto(data.extras?.get(EXTRAS_PHOTO) as? Bitmap)
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleToolbarMenu(menuItem: MenuItem) =
        when (menuItem.itemId) {
            R.id.menu_add_photo -> {
                verifyPermissionsAndTakePhoto()
                true
            }
            R.id.menu_password -> {
                true
            }
            else -> false
        }

    private fun verifyPermissionsAndTakePhoto() {
        if (checkAllPermissions(PERMISSIONS)) {
            takePhoto()
        } else {
            requestPermissions(PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO)
        }
    }

    private fun checkAllPermissions(permissions: Array<String>) =
        permissions
            .asSequence()
            .map { requireActivity().checkSelfPermission(it) }
            .all { it == PackageManager.PERMISSION_GRANTED }

    private fun verifyAllPermissionsGranted(result: IntArray) =
        result
            .asSequence()
            .all { it == PackageManager.PERMISSION_GRANTED }
}