package ru.akaardent.primitivejetpackcomposecalculator.screens

import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.akaardent.primitivejetpackcomposecalculator.BottomBar
import ru.akaardent.primitivejetpackcomposecalculator.NotesCard
import ru.akaardent.primitivejetpackcomposecalculator.readData
import ru.akaardent.primitivejetpackcomposecalculator.ui.theme.mainColor
import ru.akaardent.primitivejetpackcomposecalculator.ui.theme.notesColor
import ru.akaardent.primitivejetpackcomposecalculator.writeDataToFile

@Composable
fun NotesScreen(
    onClickToScreen1: () -> Unit,
    onClickToScreen2: () -> Unit,
    onClickToScreen3: () -> Unit,
) {
    val context = LocalContext.current
    val notesMutableState = remember {
        mutableStateListOf(*(readData(context).toTypedArray()))
    }
    val editingSome = remember {
        mutableIntStateOf(0)
    }
    var adding by remember {
        mutableStateOf(false)
    }
    var permissionForRemoving by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.fillMaxSize()) {
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
                ) {
                    itemsIndexed(notesMutableState) { index, lesson ->

//                        var isEditing by remember {
//                            mutableStateOf(false)
//                        }
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(5.dp),
//                            colors = CardDefaults.cardColors(
//                                containerColor = notesColor,
//                            ),
//                        ) {
//
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                            ) {
//                                if (!isEditing && permissionForRemoving) {
//                                    IconButton(onClick = {
//                                        notesMutableState.remove(lesson)
//                                        Log.e("MyLog", "delete = $lesson")
////                                            Toast.makeText(context, "deleted?", Toast.LENGTH_SHORT)
////                                                .show()
//                                        notesMutableState.writeDataToFile(context)
//                                    }) {
//                                        Icon(
//                                            Icons.Filled.Delete,
//                                            contentDescription = "Delete note"
//                                        )
//                                    }
//                                }
////                                IconButton(onClick = {
////                                    if (isEditing) {
////                                        notesMutableState.writeDataToFile(context)
////                                        editingSome.intValue -= 1
////                                    } else {
////                                        editingSome.intValue += 1
////                                    }
////                                    isEditing = !isEditing
////                                }) {
////                                    if (!isEditing) {
////                                        Icon(
////                                            Icons.Default.Edit,
////                                            contentDescription = "IconEdit"
////                                        )
////                                    } else {
////                                        Icon(
////                                            Icons.Default.Done,
////                                            contentDescription = "IconDone"
////                                        )
////                                    }
////                                }
//
//
//                            }
//
//                        }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = notesColor,
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
                                notesMutableState[index].NotesCard()
                            }
                        }
                    }
                }

                if (editingSome.intValue == 0) {
                    FloatingActionButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(10.dp),
                        onClick = { adding = true },
//                            contentColor = mainColor,
                        backgroundColor = mainColor
                    ) {
                        Icon(Icons.Filled.Add, "Floating action button.")
                    }
                }
            }

        }

        BottomBar(
            onClickToScreen1, onClickToScreen2, onClickToScreen3, selected3 = true
        )

    }

}