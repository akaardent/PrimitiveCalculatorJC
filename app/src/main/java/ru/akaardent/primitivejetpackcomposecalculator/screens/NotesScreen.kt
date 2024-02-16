package ru.akaardent.primitivejetpackcomposecalculator.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.akaardent.primitivejetpackcomposecalculator.BottomBar
import ru.akaardent.primitivejetpackcomposecalculator.Note
import ru.akaardent.primitivejetpackcomposecalculator.NotesCard
import ru.akaardent.primitivejetpackcomposecalculator.readData
import ru.akaardent.primitivejetpackcomposecalculator.ui.theme.mainColor
import ru.akaardent.primitivejetpackcomposecalculator.writeDataToFile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    clickToMainScreen: () -> Unit,
    clickToInvestmentsScreen: () -> Unit,
) {
    val context = LocalContext.current
    val notesMutableState = remember {
        mutableStateListOf(*(readData(context).toTypedArray()))
    }
    var adding by remember {
        mutableStateOf(false)
    }
    var permissionForRemoving by remember {
        mutableStateOf(false)
    }
    var currentIndex by remember {
        mutableIntStateOf(0)
    }


    Column(modifier = Modifier.fillMaxSize()) {
        var dark = isSystemInDarkTheme()
        TopAppBar(
            modifier = Modifier.fillMaxWidth(), backgroundColor = mainColor,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Редактировать данные", fontSize = 22.sp)
                IconButton(onClick = {
                    permissionForRemoving = !permissionForRemoving
                }) {
                    if (permissionForRemoving) {
                        Icon(imageVector = Icons.Filled.Done,
                            contentDescription = "remove permission for remove",
                            modifier = Modifier.clickable {
                                permissionForRemoving = false
                            })
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "take permission for remove",
                            modifier = Modifier.clickable {
                                permissionForRemoving = true
                            }
                        )
                    }
                }
            }

        }

        Box(
            modifier = Modifier
                .weight(10f)
        ) {
            if (!adding) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            MaterialTheme.colorScheme.background
                        )
                ) {
                    itemsIndexed(notesMutableState) { index, lesson ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                            ),
                        ) {
                            Row {
                                if (permissionForRemoving) {
                                    IconButton(onClick = {
                                        notesMutableState.remove(lesson)
                                        Log.e("MyLog", "delete = $lesson")
//                                            Toast.makeText(context, "deleted?", Toast.LENGTH_SHORT)
//                                                .show()
                                        notesMutableState.writeDataToFile(context)
                                    }) {
                                        Icon(
                                            Icons.Filled.Delete,
                                            contentDescription = "Delete note"
                                        )
                                    }
                                }
                                notesMutableState[index].NotesCard {
                                    currentIndex = index
                                    adding = true
                                }
                            }
                        }
                    }
                }


                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp),
                    onClick = {
                        notesMutableState.add(Note())
                        currentIndex = notesMutableState.lastIndex
                        Toast.makeText(
                            context,
                            "size = ${notesMutableState.size}, cur = $currentIndex",
                            Toast.LENGTH_LONG
                        ).show()
                        adding = true
                    },
//                            contentColor = mainColor,
                    backgroundColor = mainColor
                ) {
                    Icon(Icons.Filled.Add, "Floating action button.")
                }

            } else {
                Box(modifier = Modifier.fillMaxSize())
                {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TextField(
                            value = notesMutableState[currentIndex].title, onValueChange = {
                                notesMutableState[currentIndex] =
                                    notesMutableState[currentIndex].copy(title = it)
                            }, modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                textColor = MaterialTheme.colorScheme.primary
                            )
                        )
                        TextField(
                            value = notesMutableState[currentIndex].text, onValueChange = {
                                notesMutableState[currentIndex] =
                                    notesMutableState[currentIndex].copy(text = it)
                            }, modifier = Modifier
                                .weight(10f)
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                textColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                    Box(modifier = Modifier.fillMaxSize())
                    {
                        FloatingActionButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(10.dp),
                            onClick = {
                                adding = false
                                if (notesMutableState[currentIndex].title.isEmpty() && notesMutableState[currentIndex].text.isEmpty()) {
                                    notesMutableState.remove(notesMutableState[currentIndex])
                                } else {
                                    notesMutableState.writeDataToFile(context)
                                }
                            },
//                            contentColor = mainColor,
                            backgroundColor = mainColor
                        ) {
                            Icon(
                                Icons.Filled.Undo,
                                "Floating action button escape."
                            )
                        }
                    }
                }
            }

        }

        BottomBar(
            clickToMainScreen = clickToMainScreen,
            clickToInvestmentsScreen = clickToInvestmentsScreen,
            selected3 = true,
        )

    }

}