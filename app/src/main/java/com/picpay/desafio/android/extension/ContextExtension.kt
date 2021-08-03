package com.picpay.desafio.android.extension

import android.content.Context
import android.content.DialogInterface
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.picpay.desafio.android.R

fun Context.showDialog(
    @StringRes title: Int = R.string.error_title,
    @StringRes message: Int,
    @StringRes positiveButtonText: Int? = null,
    @ColorRes positiveButtonColor: Int? = null,
    positiveButtonClick: (dialog: DialogInterface, which: Int) -> Unit = { _, _ -> },
    @StringRes negativeButtonText: Int? = null,
    @ColorRes negativeButtonColor: Int? = null,
    negativeButtonClick: (dialog: DialogInterface, which: Int) -> Unit = { _, _ -> }
) {
    AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(message)

        positiveButtonText?.let {
            setPositiveButton(it, positiveButtonClick)
        } ?: run {
            setPositiveButton(getString(R.string.error_button), positiveButtonClick)
        }

        negativeButtonText?.let {
            setNegativeButton(it, negativeButtonClick)
        }

        setCancelable(false)
        create()
        val dialog = show()

        positiveButtonColor?.let {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                ContextCompat.getColor(this@showDialog, it)
            )
        }

        negativeButtonColor?.let {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                ContextCompat.getColor(this@showDialog, it)
            )
        }
    }
}