package de.ynd.ui.component.dialog

import android.content.Context
import android.view.View
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import de.ynd.R

fun passwordDialog(
    context: Context,
    @StringRes title: Int = R.string.dialog_password_title,
    onPositiveButtonClicked: (value: String) -> Unit
): AlertDialog {
    val view = View.inflate(context, R.layout.alert_dialog_password, null)
    val editTextView: EditText = view.findViewById(R.id.password_input_edit_text)

    return MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setView(view)
        .setPositiveButton(R.string.dialog_password_positive_button) { _, _ ->
            onPositiveButtonClicked(editTextView.text.toString())
        }
        .setNegativeButton(R.string.dialog_password_negative_button) { _, _ -> }
        .create()
}