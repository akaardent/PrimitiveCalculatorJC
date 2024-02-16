package ru.akaardent.primitivejetpackcomposecalculator.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.akaardent.primitivejetpackcomposecalculator.Bond
import ru.akaardent.primitivejetpackcomposecalculator.BottomBar
import ru.akaardent.primitivejetpackcomposecalculator.Komission
import ru.akaardent.primitivejetpackcomposecalculator.Note
import ru.akaardent.primitivejetpackcomposecalculator.Share
import ru.akaardent.primitivejetpackcomposecalculator.TypeOfInvestments
import ru.akaardent.primitivejetpackcomposecalculator.copyText
import ru.akaardent.primitivejetpackcomposecalculator.enterFloat
import ru.akaardent.primitivejetpackcomposecalculator.enterInt
import ru.akaardent.primitivejetpackcomposecalculator.investments
import ru.akaardent.primitivejetpackcomposecalculator.readData
import ru.akaardent.primitivejetpackcomposecalculator.readKomission
import ru.akaardent.primitivejetpackcomposecalculator.ui.theme.mainColor
import ru.akaardent.primitivejetpackcomposecalculator.writeDataToFile
import ru.akaardent.primitivejetpackcomposecalculator.writeKomission


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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = "Invest calculator with saving", fontSize = 20.sp)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(7f)
        ) {
            val allPad = 5.dp
            if (typeOfInvestments == TypeOfInvestments.BONDS) {
                var obj by remember {
                    mutableStateOf(Bond())
                }
                var komissionIsText by remember {
                    mutableStateOf(true)
                }
                var komission by remember {
                    mutableStateOf(
                        readKomission(
                            context = context,
                            type = typeOfInvestments
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    if (komissionIsText) {
                        Text(
                            text = "buy: ${komission.buy}%",
                            fontSize = 17.sp,
                            modifier = Modifier
                        )
                        IconButton(onClick = { komissionIsText = !komissionIsText }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "edit komission"
                            )
                        }
                    } else {
                        TextField(
                            value = komission.buyText, onValueChange = {
                                val text = it.enterFloat()
                                komission = komission.copy(
                                    buyText = text,
                                    sellText = text,
                                )
                            },
                            label = { Text(text = "buy") },
                            modifier = Modifier
                                .padding(allPad)
                                .width(100.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )
                        IconButton(onClick = {
                            komissionIsText = !komissionIsText
                            komission.writeKomission(context = context)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "save edited komission"
                            )
                        }
                    }
                }

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
                                obj = obj.copy(quantityText = it.enterInt())
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            label = { Text(text = "количество облигаций") },
                            modifier = Modifier.padding(allPad)
                        )

                        TextField(
                            value = obj.priceText, onValueChange = {
                                obj = obj.copy(priceText = it.enterFloat())
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            label = { Text(text = "средняя цена") },
                            modifier = Modifier.padding(allPad)
                        )

                        TextField(
                            value = obj.nominalText, onValueChange = {
                                obj = obj.copy(nominalText = it.enterFloat())
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            label = { Text(text = "номинал") },
                            modifier = Modifier.padding(allPad)
                        )

                        TextField(
                            value = obj.couponText, onValueChange = {
                                obj = obj.copy(couponText = it.enterFloat())
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            label = { Text(text = "размер купона") },
                            modifier = Modifier.padding(allPad)
                        )

                        TextField(
                            value = obj.numberOfCouponText, onValueChange = {
                                obj = obj.copy(numberOfCouponText = it.enterInt())
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            label = { Text(text = "количество купонов 1 облигации") },
                            modifier = Modifier.padding(allPad)
                        )


                        TextField(
                            value = obj.nkdText, onValueChange = {
                                obj = obj.copy(nkdText = it.enterFloat())
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
                            label = { Text(text = "срок (для себя)") },
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
                            Box(
                                modifier = Modifier
                            ) {
                                IconButton(onClick = {
                                    expand = true
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Error,
                                        contentDescription = "objIsEmpty()",
                                        tint = Color.Red,
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
                        resultState = obj.investments(komis = komission)
                    }

                }

                if (resultState != "") {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(3f)
                            .background(Color.LightGray)
                            .verticalScroll(rememberScrollState()),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.Center
                        ) {
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
                }
            } else {
                var obj by remember {
                    mutableStateOf(Share())
                }
                var komissionBuyIsText by remember {
                    mutableStateOf(true)
                }
                var komissionSellIsText by remember {
                    mutableStateOf(true)
                }
                var komission by remember {
                    mutableStateOf(
                        readKomission(
                            context = context,
                            type = typeOfInvestments
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        if (komissionBuyIsText) {
                            Text(
                                text = "buy: ${komission.buy}%",
                                fontSize = 17.sp,
                                modifier = Modifier
                            )
                            IconButton(onClick = { komissionBuyIsText = !komissionBuyIsText }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "edit komission"
                                )
                            }
                        } else {
                            TextField(
                                value = komission.buyText, onValueChange = {
                                    komission = komission.copy(
                                        buyText = it.enterFloat(),
                                    )
                                },
                                label = { Text(text = "buy") },
                                modifier = Modifier
                                    .padding(allPad)
                                    .width(100.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                )
                            )
                            IconButton(onClick = {
                                komissionBuyIsText = !komissionBuyIsText
                                komission.writeKomission(context = context)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "save edited komission"
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        if (komissionSellIsText) {
                            Text(
                                text = "sell: ${komission.sell}%",
                                fontSize = 17.sp,
                                modifier = Modifier
                            )
                            IconButton(onClick = { komissionSellIsText = !komissionSellIsText }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "edit komission"
                                )
                            }
                        } else {
                            TextField(
                                value = komission.sellText, onValueChange = {
                                    komission = komission.copy(
                                        sellText = it.enterFloat(),
                                    )
                                },
                                label = { Text(text = "sell") },
                                modifier = Modifier
                                    .padding(allPad)
                                    .width(100.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                )
                            )
                            IconButton(onClick = {
                                komissionSellIsText = !komissionSellIsText
                                komission.writeKomission(context = context)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "save edited komission"
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4f)
                ) {

                    Column(
                        modifier = Modifier
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
                                obj = obj.copy(quantityText = it.enterInt())
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            label = { Text(text = "количество") },
                            modifier = Modifier.padding(allPad)
                        )

                        TextField(
                            value = obj.priceBuyText, onValueChange = {
                                obj = obj.copy(priceBuyText = it.enterFloat())
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            label = { Text(text = "средняя цена покупки") },
                            modifier = Modifier.padding(allPad)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically)
                        {
                            TextField(
                                value = obj.priceSellText, onValueChange = {
                                    obj = obj.copy(priceSellText = it.enterFloat())
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                label = { Text(text = "средняя цена продажи") },
                                modifier = Modifier.padding(allPad)
                            )
                            if (obj.myIsEmpty()) {
                                var expand by remember {
                                    mutableStateOf(false)
                                }
                                resultState = ""

                                Box {
                                    IconButton(onClick = {
                                        expand = true
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Error,
                                            contentDescription = "objIsEmpty()",
                                            tint = Color.Red,
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


                            } else {
                                resultState = obj.investments(komis = komission)
                            }
                        }
                    }


                }

                if (resultState != "") {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(3f)
                            .background(Color.LightGray)
                            .verticalScroll(rememberScrollState()),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.Center
                        ) {
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
                                                Column(
                                                    verticalArrangement = Arrangement.Center,
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                )
                                                {
                                                    Text(text = "добавить выражение")
                                                    Text(text = "в список заметок")
                                                }
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
                                            Column(
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Text(text = "добавить текст в заметки")
                                                Text(text = "можно только")
                                                Text(text = "при наличии")
                                                Text(text = "названия")
                                                Text(text = "компании")
                                            }
                                        }
                                    }
                                }

                            }

                        }
                    }
                }
            }
            Row {
                Button(
                    onClick = { typeOfInvestments = TypeOfInvestments.SHARES },
                    modifier = Modifier.weight(1f),
                    enabled = (typeOfInvestments != TypeOfInvestments.SHARES)
                ) {
                    Text(text = "акция")
                }
                Button(
                    onClick = { typeOfInvestments = TypeOfInvestments.BONDS },
                    modifier = Modifier.weight(1f),
                    enabled = (typeOfInvestments != TypeOfInvestments.BONDS)
                ) {
                    Text(text = "облигация")
                }
            }
        }

        BottomBar(
            clickToMainScreen = clickToMainScreen,
            clickToNotesScreen = clickToNotesScreen,
            selected2 = true,
        )
    }


}

