package ru.akaardent.primitivejetpackcomposecalculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val symbolsList = listOf(
            "C", "()", "%", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ",", "<", "=",
        )
        var operationCount = 0
        val charDiv = symbolsList[3][0]
        setContent {
            val context = LocalContext.current
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
                        text = primerState.value,
                        fontSize = 40.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.25f)
                    )
                }
                Row(horizontalArrangement = Arrangement.End) {
                    Text(
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
                    for (i in 0 until 5) {
                        var index = 0
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,

                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            for (j in 0 until 4) {
                                val symbol: String = symbolsList[i * 4 + j]
                                Button(onClick = {
                                    when (symbol) {
                                        "7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "," -> {
                                            primerState.value = "${primerState.value}$symbol"
                                            if (operationCount > 0) resultState.value =
                                                makeResult(primerState.value)
                                        }

                                        "%", charDiv.toString(), "*", "-", "+" -> {
                                            primerState.value = "${primerState.value}$symbol"
                                            operationCount += 1
                                        }

                                        "()" -> {
                                            if (primerState.value.length > 0) {
                                                val ArrayOfText =
                                                    primerState.value.toCharArray()
                                                        .toTypedArray()
                                                val lastChar = ArrayOfText[ArrayOfText.lastIndex]
                                                if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == charDiv || lastChar == '(') {
                                                    primerState.value = "${primerState.value}("
                                                } else {
                                                    primerState.value = "${primerState.value})"
                                                    if (operationCount > 0) resultState.value =
                                                        makeResult(primerState.value)
                                                }

                                            } else {
                                                primerState.value = "${primerState.value}("
                                            }

                                        }

                                        "<" -> {
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
                                        }

                                        "=" -> {
                                            if (resultState.value != "") {
                                                primerState.value = resultState.value
                                                resultState.value = ""
                                            }
                                        }

                                        "C" -> {
                                            primerState.value = ""
                                            resultState.value = ""
                                        }
                                    }

                                }) {
                                    var text = symbol
//                                text = when {
//                                    symbol == "AC" -> "AC"
//                                    symbol == "()" -> "( )"
//                                    symbol.length == 1 -> " $symbol "
//                                    symbol.length == 2 -> " $symbol"
//                                    else -> symbol
//                                }
                                    Text(text = text, fontSize = 35.sp)
                                }

                            }
                        }
                    }
                }
            }
        }
    }


    fun makeResult(primerState: String): String {
        // using library exp4j (take string get result) (it's say me Tigran)
        val primer = primerState
        if (primer != "") {
            try {
                val localResult: Double =
                    ExpressionBuilder(primer.replace(',', '.')).build().evaluate()
                if (localResult.toInt().toDouble() == localResult) {
                    return localResult.toInt().toString()

                } else {
                    return localResult.toString().replace('.', ',')
                }
            } catch (e: Exception) {
                return ""
            }
        }
        return ""
    }


}

