package ru.akaardent.primitivejetpackcomposecalculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Note(
    var title: String = "",
    var text: String = "",
) {

    fun toFileString(): String {
        return "$title|${text.replace("\n", "/;")};"
    }
}

fun String.fromStringToNotes(): Note {
    val (title, text) = this.replace("/;", "\n").split("|")
    return Note(title, text)
}

@Composable
fun Note.NotesCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),

        ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Text(text = title)
            Text(text = text)
        }
    }
}