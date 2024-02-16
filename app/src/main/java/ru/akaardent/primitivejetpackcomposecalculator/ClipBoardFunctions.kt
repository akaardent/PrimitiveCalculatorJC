package ru.akaardent.primitivejetpackcomposecalculator

import android.content.ClipData
import android.content.Context

fun insertTextFromClipboard(context: Context): String? {
    val clipboard =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
    val clipData = clipboard.primaryClip
    if (clipData != null && clipData.itemCount > 0) {
        val item = clipData.getItemAt(0)
        return item.text?.toString()
    }
    return null
}

fun copyText(value: String, context: Context) {
    if(value != "") {
        val clipboard =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = ClipData.newPlainText("label", value)
        clipboard.setPrimaryClip(clip)
    }
}