package ru.akaardent.primitivejetpackcomposecalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
fun Note.NotesCard(clickable: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                clickable()
            },
        ) {
        Column(modifier = Modifier.padding(5.dp)) {
            if(title!="") {
                Text(text = title, fontSize = 19.sp)
            }
            if(text!="") {
                Text(text = text, fontSize = 14.sp)
            }
        }
    }
}