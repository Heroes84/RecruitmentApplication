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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import de.ynd.R
import de.ynd.databinding.DesktopFragmentBinding
import de.ynd.ui.BaseFragment
import de.ynd.ui.component.dialog.passwordDialog
import de.ynd.ui.viewModelDelegate

private val PERMISSIONS = arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.CAMERA
)

private const val REQUEST_CODE_PERMISSIONS = 11
private const val REQUEST_CODE_TAKE_PHOTO = 12

private const val SPAN = 4

private const val EXTRAS_PHOTO = "data"

class DesktopFragment : BaseFragment() {

    private lateinit var binding: DesktopFragmentBinding
    private val viewModel by lazy { viewModelDelegate<DesktopViewModel>() }
    private val photoAdapter = PhotoAdapter(arrayListOf())

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
        binding.recyclerView.apply {
            adapter = photoAdapter
            layoutManager = GridLayoutManager(requireContext(), SPAN)
        }

        viewModel.apply {
            canShowSavePasswordDialog.observe(viewLifecycleOwner, Observer {
                if (it) {
                    savePasswordFromUser()
                }
            })
            canShowPasswordDialog.observe(viewLifecycleOwner, Observer {
                if (it) {
                    passwordDialog(
                        requireContext(),
                        title = R.string.dialog_password_title,
                        onPositiveButtonClicked = { password ->
                            viewModel.verifyPassword(password)
                        }
                    ).show()
                }
            })
            authError.observe(viewLifecycleOwner, Observer {
                if (it) {
                    Snackbar.make(binding.coordinator, R.string.error_auth, Snackbar.LENGTH_LONG).show()
                }
            })
            takePhoto.observe(viewLifecycleOwner, Observer {
                if (it) {
                    verifyPermissionsAndTakePhoto()
                } else {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(R.string.dialog_info_title)
                        .setMessage(R.string.dialog_info_message)
                        .setPositiveButton(R.string.ok) { _, _ -> /* no -op */ }
                        .create()
                        .show()
                }
            })
            photoList.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    photoAdapter.newPhotos(it)
                }
            })
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
                if (resultCode == RESULT_OK
                    && (data?.extras?.containsKey(EXTRAS_PHOTO) == true)
                    && (data.extras?.get(EXTRAS_PHOTO) as? Bitmap != null)
                ) {
                    viewModel.onNewPhoto(
                        requireContext().filesDir,
                        data.extras?.get(EXTRAS_PHOTO) as Bitmap
                    )
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleToolbarMenu(menuItem: MenuItem) =
        when (menuItem.itemId) {
            R.id.menu_add_photo -> {
                viewModel.onTakePhotoClicked()
                true
            }
            R.id.menu_password -> {
                viewModel.onLockIcon()
                true
            }
            else -> false
        }

    private fun savePasswordFromUser() {
        passwordDialog(
            requireContext(),
            title = R.string.dialog_password_set_title,
            onPositiveButtonClicked = { password ->
                viewModel.onSetPassword(password)
            }
        ).show()
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