package ru.akaardent.primitivejetpackcomposecalculator.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shortcut
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.akaardent.primitivejetpackcomposecalculator.Bond
import ru.akaardent.primitivejetpackcomposecalculator.BottomBar
import ru.akaardent.primitivejetpackcomposecalculator.investments
import ru.akaardent.primitivejetpackcomposecalculator.Note
import ru.akaardent.primitivejetpackcomposecalculator.TypeOfInvestments
import ru.akaardent.primitivejetpackcomposecalculator.readData
import ru.akaardent.primitivejetpackcomposecalculator.ui.theme.copyText
import ru.akaardent.primitivejetpackcomposecalculator.ui.theme.mainColor
import ru.akaardent.primitivejetpackcomposecalculator.writeDataToFile


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestmentsScreen(
    clickToMainScreen: () -> Unit,
    clickToNotesScreen: () -> Unit,

) {
    var resultState by remember {
        mutableStateOf("")
    }
    val sizeOfIcons = 50.dp
    val context = LocalContext.current
    var typeOfInvestments by remember {
        mutableStateOf(TypeOfInvestments.BONDS)
    }
    var obj by remember {
        mutableStateOf(Bond())
    }
    var infoAddNote by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = mainColor
        ) {
            Text(text = "TopApp")
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(7f)
        ) {
            val allPad = 5.dp
//QUANTITY | PRICE | NOMINAL | COUPON | HOW MUCH | NKD | Term (months)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)
            ) {
                Column(
                    modifier = Modifier
                        .weight(7f)
                        .verticalScroll(rememberScrollState())
                        .padding(3.dp)
                ) {

                    TextField(
                        value = obj.name, onValueChange = {

                            obj = obj.copy(name = it)
                        },
                        label = { Text(text = "название") },
                        modifier = Modifier.padding(allPad)
                    )

                    TextField(
                        value = obj.quantityText, onValueChange = {

                            obj = obj.copy(quantityText = it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = { Text(text = "количество") },
                        modifier = Modifier.padding(allPad)
                    )

                    TextField(
                        value = obj.priceText, onValueChange = {
                            obj = obj.copy(priceText = it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = { Text(text = "средняя цена") },
                        modifier = Modifier.padding(allPad)
                    )

                    TextField(
                        value = obj.nominalText, onValueChange = {
                            obj = obj.copy(nominalText = it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = { Text(text = "номинал") },
                        modifier = Modifier.padding(allPad)
                    )

                    TextField(
                        value = obj.couponText, onValueChange = {
                            obj = obj.copy(couponText = it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = { Text(text = "размер купона") },
                        modifier = Modifier.padding(allPad)
                    )

                    TextField(
                        value = obj.numberOfCouponText, onValueChange = {
                            obj = obj.copy(numberOfCouponText = it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = { Text(text = "количество купонов 1 облигации") },
                        modifier = Modifier.padding(allPad)
                    )

                    TextField(
                        value = obj.numberOfBondsText, onValueChange = {
                            obj = obj.copy(numberOfBondsText = it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = { Text(text = "средняя цена") },
                        modifier = Modifier.padding(allPad)
                    )

                    TextField(
                        value = obj.nkdText, onValueChange = {
                            obj = obj.copy(nkdText = it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = { Text(text = "НКД") },
                        modifier = Modifier.padding(allPad)
                    )

                    TextField(
                        value = obj.termText, onValueChange = {
                            obj = obj.copy(termText = it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = { Text(text = "срок (число для себя)") },
                        modifier = Modifier.padding(allPad)
                    )

                }
                if (obj.myIsEmpty()) {
                    var expand by remember {
                        mutableStateOf(false)
                    }
                    resultState = ""
                    Column(
                        modifier = Modifier
                            .weight(3f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        Box {
                            IconButton(onClick = {
                                expand = true
                            }) {
                                Icon(
                                    imageVector = Icons.Default.HighlightOff,
                                    contentDescription = "objIsEmpty()"
                                )
                            }
                            DropdownMenu(
                                expanded = expand,
                                onDismissRequest = {
                                    expand = false
                                },
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Text(text = "не все поля заполнены")
                            }

                        }
                    }

                } else {
                    resultState = obj.investments()
                }

            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
                    .background(Color.LightGray),
                horizontalArrangement = Arrangement.Center
            ) {

                if (resultState != "") {
                    Text(
                        text = resultState,
                        modifier = Modifier.weight(10f)
                    )
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
                        if (obj.name != "") {
                            Row {
                                IconButton(
                                    onClick = {
                                        val notes = readData(context)
                                        notes.add(
                                            Note(
                                                obj.name,
                                                resultState,

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
                                        expanded = infoAddNote,
                                        onDismissRequest = {
                                            infoAddNote = false
                                        },
                                        modifier = Modifier.padding(4.dp)
                                    ) {
                                        Text(text = "добавить выражение\nв список заметок")
                                    }
                                }
                            }
                        } else {
                            Box {
                                IconButton(onClick = { infoAddNote = true }) {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = "info about adding note"
                                    )
                                }
                                DropdownMenu(
                                    expanded = infoAddNote,
                                    onDismissRequest = {
                                        infoAddNote = false
                                    },
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Text(
                                        text = "добавить текст в заметки\n" +
                                                "можно только\n при наличии\n" +
                                                "названия\n" +
                                                "компании"
                                    )
                                }
                            }
                        }

                    }
                }
            }

            Row {
                Button(
                    onClick = { typeOfInvestments = TypeOfInvestments.SHARES },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "акция")
                }
                Button(
                    onClick = { typeOfInvestments = TypeOfInvestments.BONDS },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "облигация")
                }
            }
        }

        BottomBar(
            clickToMainScreen = clickToMainScreen,
            clickToNotesScreen = clickToNotesScreen
        )
    }


}

