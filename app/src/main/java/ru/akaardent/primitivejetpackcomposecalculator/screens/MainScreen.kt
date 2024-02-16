package ru.akaardent.primitivejetpackcomposecalculator.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shortcut
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.objecthunter.exp4j.ExpressionBuilder
import ru.akaardent.primitivejetpackcomposecalculator.BottomBar
import ru.akaardent.primitivejetpackcomposecalculator.Note
import ru.akaardent.primitivejetpackcomposecalculator.buttonSize
import ru.akaardent.primitivejetpackcomposecalculator.readData
import ru.akaardent.primitivejetpackcomposecalculator.ui.theme.colorClear
import ru.akaardent.primitivejetpackcomposecalculator.ui.theme.colorNum
import ru.akaardent.primitivejetpackcomposecalculator.ui.theme.colorZnak
import ru.akaardent.primitivejetpackcomposecalculator.copyText
import ru.akaardent.primitivejetpackcomposecalculator.insertTextFromClipboard
import ru.akaardent.primitivejetpackcomposecalculator.ui.theme.mainColor
import ru.akaardent.primitivejetpackcomposecalculator.ui.theme.textColor
import ru.akaardent.primitivejetpackcomposecalculator.writeDataToFile

var operationCount = 0
const val charDiv = '/'

@Composable
fun MainScreen(
    clickToInvestmentsScreen: () -> Unit,
    clickToNotesScreen: () -> Unit,
) {

    var resultState by remember {
        mutableStateOf("")
    }
    var primerState by remember {
        mutableStateOf("")
    }
    val sizeOfIcons = 50.dp
    val horPad = 3.dp
    val context = LocalContext.current
    var infoAddNote by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(), backgroundColor = mainColor
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = "Calculator with saving", fontSize = 20.sp)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(10f)
        ) {

            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    fontFamily = FontFamily.Serif,
                    color = textColor,
                    text = primerState,
                    fontSize = 40.sp,
                    modifier = Modifier.weight(7f)
                )
                Column(modifier = Modifier.weight(1f)) {
                    if (primerState.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                copyText(primerState, context)
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "copy",
                                modifier = Modifier.size(sizeOfIcons)
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            val text = insertTextFromClipboard(context)
                            if (text != null) {
                                primerState = text
                                resultState = makeResult(primerState)
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentPaste,
                            contentDescription = "paste",
                            modifier = Modifier.size(sizeOfIcons)
                        )
                    }
                }

            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    fontFamily = FontFamily.Serif,
                    color = textColor,
                    text = resultState,
                    fontSize = 60.sp,
                    modifier = Modifier.weight(7f)
//                    .fillMaxHeight(0.25f),
                )
                if (resultState != "") {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        IconButton(
                            onClick = {
                                copyText(resultState, context)
                            },
                            modifier = Modifier.weight(1f),
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "copy",
                                modifier = Modifier.size(sizeOfIcons)
                            )
                        }
                        Row {
                            IconButton(
                                onClick = {
                                    val notes = readData(context)
                                    notes.add(
                                        Note(
                                            resultState,
                                            primerState,
                                        )
                                    )
                                    notes.writeDataToFile(context)
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Shortcut,
                                    contentDescription = "copy",
                                    modifier = Modifier.size(sizeOfIcons)
                                )
                            }
                            Box {
                                IconButton(onClick = { infoAddNote = true }) {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = "info about adding note"
                                    )
                                }
                                DropdownMenu(
                                    expanded = infoAddNote, onDismissRequest = {
                                        infoAddNote = false
                                    }, modifier = Modifier.padding(4.dp)
                                ) {
                                    Text(text = "добавить выражение\nв список заметок")
                                }
                            }
                        }


                    }
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,

                    modifier = Modifier.fillMaxWidth()

                ) {

                    Button(
                        onClick = {
                            primerState = ""
                            resultState = ""

                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorClear),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = horPad)
                    ) {
                        Text(
                            fontFamily = FontFamily.Serif,
                            color = textColor,
                            text = "C",
                            fontSize = buttonSize
                        )

                    }

                    Button(
                        onClick = {
                            if (primerState.isNotEmpty()) {
                                val arrayOfText = primerState.toCharArray().toTypedArray()
                                val lastChar = arrayOfText[arrayOfText.lastIndex]
                                if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == charDiv || lastChar == '(') {
                                    primerState = "${primerState}("
                                } else {
                                    primerState = "${primerState})"
                                    if (operationCount > 0) resultState = makeResult(primerState)
                                }

                            } else {
                                primerState = "${primerState}("
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorZnak),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = horPad)
                    ) {
                        Text(
                            fontFamily = FontFamily.Serif,
                            color = textColor,
                            text = "()",
                            fontSize = buttonSize
                        )
                    }

                    Button(
                        onClick = {
                            primerState = "${primerState}%"
                            operationCount += 1
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorZnak),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = horPad)
                    ) {
                        Text(
                            fontFamily = FontFamily.Serif,
                            color = textColor,
                            text = "%",
                            fontSize = buttonSize
                        )
                    }

                    Button(
                        onClick = {
                            primerState = "${primerState}$charDiv"
                            operationCount += 1
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorZnak),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = horPad)
                    ) {
                        Text(
                            fontFamily = FontFamily.Serif,
                            color = textColor,
                            text = charDiv.toString(),
                            fontSize = buttonSize,

                            )
                    }

                }

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()

                ) {
                    for (i in 7..9) {
                        Button(
                            onClick = {
                                primerState = "${primerState}$i"
                                if (operationCount > 0) resultState = makeResult(primerState)

                            },
                            colors = ButtonDefaults.buttonColors(containerColor = colorNum),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = horPad)
                        ) {
                            Text(
                                fontFamily = FontFamily.Serif,
                                color = textColor,
                                text = i.toString(),
                                fontSize = buttonSize,

                                )
                        }
                    }
                    Button(
                        onClick = {
                            primerState = "${primerState}*"
                            operationCount += 1
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorZnak),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = horPad)
                    ) {
                        Text(
                            fontFamily = FontFamily.Serif,
                            color = textColor,
                            text = "*",
                            fontSize = buttonSize
                        )
                    }

                }

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()

                ) {
                    for (i in 4..6) {
                        Button(
                            onClick = {
                                primerState = "${primerState}$i"
                                if (operationCount > 0) resultState = makeResult(primerState)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = colorNum),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = horPad)
                        ) {
                            Text(
                                fontFamily = FontFamily.Serif,
                                color = textColor,
                                text = i.toString(),
                                fontSize = buttonSize
                            )
                        }
                    }

                    Button(
                        onClick = {
                            primerState = "${primerState}-"
                            operationCount += 1
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorZnak),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = horPad)
                    ) {
                        Text(
                            fontFamily = FontFamily.Serif,
                            color = textColor,
                            text = "-",
                            fontSize = buttonSize
                        )
                    }

                }

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,

                    modifier = Modifier.fillMaxWidth()

                ) {
                    for (i in 1..3) {
                        Button(
                            onClick = {
                                primerState = "${primerState}$i"
                                if (operationCount > 0) resultState = makeResult(primerState)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = colorNum),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = horPad)
                        ) {
                            Text(
                                fontFamily = FontFamily.Serif,
                                color = textColor,
                                text = i.toString(),
                                fontSize = buttonSize
                            )
                        }
                    }

                    Button(
                        onClick = {
                            primerState = "${primerState}+"
                            operationCount += 1

                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorZnak),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = horPad)
                    ) {
                        Text(
                            fontFamily = FontFamily.Serif,
                            color = textColor,
                            text = "+",
                            fontSize = buttonSize
                        )

                    }
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,

                    modifier = Modifier.fillMaxWidth()

                ) {
                    Button(
                        onClick = {
                            primerState = "${primerState}0"
                            if (operationCount > 0) resultState = makeResult(primerState)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorNum),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = horPad)
                    ) {
                        Text(
                            fontFamily = FontFamily.Serif,
                            color = textColor,
                            text = "0",
                            fontSize = buttonSize
                        )
                    }

                    Button(
                        onClick = {
                            primerState = "${primerState},"
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorNum),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = horPad)
                    ) {
                        Text(
                            fontFamily = FontFamily.Serif,
                            color = textColor,
                            text = ",",
                            fontSize = buttonSize
                        )
                    }

                    Button(
                        onClick = {
                            if (primerState.isNotEmpty()) {
                                val lastChar = primerState.substring(
                                    primerState.lastIndex, primerState.lastIndex + 1
                                )
                                if (lastChar == '+'.toString() || lastChar == '-'.toString() || lastChar == '*'.toString() || lastChar == charDiv.toString()) {
                                    operationCount -= 1
                                }
                                primerState = primerState.substring(
                                    0, primerState.lastIndex
                                )
                                if (operationCount > 0) resultState = makeResult(primerState)
                            }

                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorNum),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = horPad)
                            .height(62.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Backspace,
                            contentDescription = "backspace",
                            modifier = Modifier.size(30.dp),
                        )
                    }

                    Button(
                        onClick = {
                            primerState = resultState
                            resultState = ""
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorZnak),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = horPad)
                    ) {
                        Text(
                            fontFamily = FontFamily.Serif,
                            color = textColor,
                            text = "=",
                            fontSize = buttonSize
                        )

                    }

                }

            }
        }
        BottomBar(
            clickToNotesScreen = clickToNotesScreen,
            clickToInvestmentsScreen = clickToInvestmentsScreen,
            selected1 = true,
        )
    }

}


fun makeResult(primerState: String): String {
    // using library exp4j (take string get result) (it's say me Tigran)

    if (primerState != "") {
        return try {
            val localResult: Double =
                ExpressionBuilder(primerState.replace(',', '.')).build().evaluate()
            if (localResult.toInt().toDouble() == localResult) {
                localResult.toInt().toString()

            } else {
                localResult.toString().replace('.', ',')
            }
        } catch (e: Exception) {
            ""
        }
    }
    return ""
}