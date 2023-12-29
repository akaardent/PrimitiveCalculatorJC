package ru.akaardent.primitivejetpackcomposecalculator

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.File

private var fileBaseName = "dataCalc.txt"
fun getNotesFromListString(
    selectedTextByStrings: List<String>,
): MutableList<Note> {
    val result = mutableListOf<Note>()
    try {
        Log.e("MyLog", "selectedtext = $selectedTextByStrings")
        for (str in selectedTextByStrings) {
            val obj = str.substring(0, str.lastIndex).fromStringToNotes()
            result.add(obj)
        }
        Log.e("MyLog", "getNotesFromListString (этим будет subjects) = $result")
    } catch (e: Exception) {
        Log.e("MyLog", "exception = ${e.message.toString()}")
    }
    Log.e("MyLog", "return result in getNotesFromListString=  $result")
    return result
}

fun readData(context: Context): MutableList<Note> {
    try {
        val file = File(context.getExternalFilesDir(null), fileBaseName)
        val listWithPairs = file.readLines()
        Log.e("MyLog", "readData = $listWithPairs")
        return getNotesFromListString(listWithPairs)
    } catch (e: Exception) {
//        Toast.makeText(context, "ERROR READING LOCAL DATABASE", Toast.LENGTH_SHORT).show()
    }
    return mutableListOf()
}

fun MutableList<Note>.writeDataToFile(context: Context) {
    try {
        val file = File(context.getExternalFilesDir(null), fileBaseName)
        file.writeText("")
        Log.e("MyLog", "start = WriteDataByMutableMap")
        if (this.isNotEmpty()) {
            for (note in this) {
                file.appendText(note.toFileString() + "\n")
            }
        }
//            Toast.makeText(this, "local database has updated", Toast.LENGTH_SHORT).show()
        Log.e("MyLog", "end = WriteDataByMutableMap")
    } catch (e: Exception) {
        Toast.makeText(context, "ERROR WRITING", Toast.LENGTH_LONG).show()
//            Log.e("Artur", e.message ?: "")
    }
}