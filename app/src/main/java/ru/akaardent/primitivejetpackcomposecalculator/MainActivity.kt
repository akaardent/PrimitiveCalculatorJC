package ru.akaardent.primitivejetpackcomposecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val buttonSize = 35.sp
        val colorZnak = Color(0xFF2196F3)
        val colorClear = Color(0xFFD576BD)
        val colorNum = Color(0xFF868585)
        val textColor = Color(0xFF000000)
        var operationCount = 0
        val charDiv = '/'

        setContent {
            val resultState = remember {
                mutableStateOf("")
            }
            val primerState = remember {
                mutableStateOf("")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Row(horizontalArrangement = Arrangement.Start) {
                    Text(
                        fontFamily = FontFamily.Serif,
                        color = textColor,
                        text = primerState.value,
                        fontSize = 40.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.25f)
                    )
                }
                Row(horizontalArrangement = Arrangement.End) {
                    Text(
                        fontFamily = FontFamily.Serif,
                        color = textColor,
                        text = resultState.value,
                        fontSize = 60.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.25f),
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,

                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        Button(onClick = {
                            primerState.value = ""
                            resultState.value = ""

                        }, colors = ButtonDefaults.buttonColors(containerColor = colorClear)) {
                            Text(
                                fontFamily = FontFamily.Serif,
                                color = textColor,
                                text = "C",
                                fontSize = buttonSize
                            )
                        }

                        Button(onClick = {
                            if (primerState.value.isNotEmpty()) {
                                val arrayOfText =
                                    primerState.value.toCharArray()
                                        .toTypedArray()
                                val lastChar = arrayOfText[arrayOfText.lastIndex]
                                if (lastChar == '+' || lastChar == '-' || lastChar == '×' || lastChar == charDiv || lastChar == '('
                                ) {
                                    primerState.value = "${primerState.value}("
                                } else {
                                    primerState.value = "${primerState.value})"
                                    if (operationCount > 0) resultState.value =
                                        makeResult(primerState.value)
                                }

                            } else {
                                primerState.value = "${primerState.value}("
                            }
                        }, colors = ButtonDefaults.buttonColors(containerColor = colorZnak)) {
                            Text(
                                fontFamily = FontFamily.Serif,
                                color = textColor,
                                text = "()",
                                fontSize = buttonSize
                            )
                        }

                        Button(onClick = {
                            primerState.value = "${primerState.value}%"
                            operationCount += 1
                        }, colors = ButtonDefaults.buttonColors(containerColor = colorZnak)) {
                            Text(
                                fontFamily = FontFamily.Serif,
                                color = textColor,
                                text = "%",
                                fontSize = buttonSize
                            )
                        }

                        Button(onClick = {
                            primerState.value = "${primerState.value}$charDiv"
                            operationCount += 1
                        }, colors = ButtonDefaults.buttonColors(containerColor = colorZnak)) {
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
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        for (i in 7..9) {
                            Button(onClick = {
                                primerState.value = "${primerState.value}$i"
                                if (operationCount > 0) resultState.value =
                                    makeResult(primerState.value)

                            }, colors = ButtonDefaults.buttonColors(containerColor = colorNum)) {
                                Text(
                                    fontFamily = FontFamily.Serif,
                                    color = textColor,
                                    text = i.toString(),
                                    fontSize = buttonSize,

                                    )
                            }
                        }
                        Button(onClick = {
                            primerState.value = "${primerState.value}*"
                            operationCount += 1
                        }, colors = ButtonDefaults.buttonColors(containerColor = colorZnak)) {
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
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        for (i in 4..6) {
                            Button(onClick = {
                                primerState.value = "${primerState.value}$i"
                                if (operationCount > 0) resultState.value =
                                    makeResult(primerState.value)
                            }, colors = ButtonDefaults.buttonColors(containerColor = colorNum)) {
                                Text(
                                    fontFamily = FontFamily.Serif,
                                    color = textColor,
                                    text = i.toString(),
                                    fontSize = buttonSize
                                )
                            }
                        }

                        Button(onClick = {
                            primerState.value = "${primerState.value}-"
                            operationCount += 1
                        }, colors = ButtonDefaults.buttonColors(containerColor = colorZnak)) {
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

                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        for (i in 1..3) {
                            Button(onClick = {
                                primerState.value = "${primerState.value}$i"
                                if (operationCount > 0) resultState.value =
                                    makeResult(primerState.value)
                            }, colors = ButtonDefaults.buttonColors(containerColor = colorNum)) {
                                Text(
                                    fontFamily = FontFamily.Serif,
                                    color = textColor,
                                    text = i.toString(),
                                    fontSize = buttonSize
                                )
                            }
                        }

                        Button(onClick = {
                            primerState.value = "${primerState.value}+"
                            operationCount += 1

                        }, colors = ButtonDefaults.buttonColors(containerColor = colorZnak)) {
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

                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        Button(onClick = {
                            primerState.value = "${primerState.value}0"
                            if (operationCount > 0) resultState.value =
                                makeResult(primerState.value)
                        }, colors = ButtonDefaults.buttonColors(containerColor = colorNum)) {
                            Text(
                                fontFamily = FontFamily.Serif,
                                color = textColor,
                                text = "0",
                                fontSize = buttonSize
                            )
                        }

                        Button(onClick = {
                            primerState.value = "${primerState.value}$,"
                        }, colors = ButtonDefaults.buttonColors(containerColor = colorNum)) {
                            Text(
                                fontFamily = FontFamily.Serif,
                                color = textColor,
                                text = ",",
                                fontSize = buttonSize
                            )
                        }

                        Button(onClick = {
                            if (primerState.value.isNotEmpty()) {
                                val lastChar = primerState.value.substring(
                                    primerState.value.lastIndex,
                                    primerState.value.lastIndex + 1
                                )
                                if (lastChar == '+'.toString() || lastChar == '-'.toString() || lastChar == '*'.toString() || lastChar == charDiv.toString()) {
                                    operationCount -= 1
                                }
                                primerState.value = primerState.value
                                    .substring(
                                        0,
                                        primerState.value.lastIndex
                                    )
                                if (operationCount > 0) resultState.value =
                                    makeResult(primerState.value)
                            }

                        }, colors = ButtonDefaults.buttonColors(containerColor = colorNum)) {
                            Text(
                                fontFamily = FontFamily.Serif,
                                color = textColor,
                                text = "<",
                                fontSize = buttonSize
                            )
                        }

                        Button(onClick = {
                            primerState.value = resultState.value
                            resultState.value = ""
                        }, colors = ButtonDefaults.buttonColors(containerColor = colorZnak)) {
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

        }
    }


    private fun makeResult(primerState: String): String {
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


}
