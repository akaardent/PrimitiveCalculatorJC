package ru.akaardent.primitivejetpackcomposecalculator

import android.content.Context
import android.util.Log
import java.io.File

//Write andRead komission
private var fileBaseNameBond = "komissionBond.txt"
private var fileBaseNameShare = "komissionShare.txt"

data class Komission(
    var type: TypeOfInvestments,
    var buyText: String = "0.0",
    var sellText: String = "0.0",
) {
    var buy: Float = try{
        buyText.toFloat()
    }catch (e: Exception) {
        0f
    }
    var sell: Float = try{
        sellText.toFloat()
    }catch (e: Exception) {
        0f
    }
    fun toFileString(): String {
        return "buy: $buy;sell: $sell"
    }
}

fun String.StringToKomission(type: TypeOfInvestments): Komission {
    val mas = this.split(";").map {
        it.split(" ")
    }
    return Komission(
        type = type,
        buyText = mas[0][1],
        sellText = mas[1][1],
    )
}

fun Komission.writeKomission(context: Context) {
    try {
        if (type == TypeOfInvestments.BONDS) {
            File(context.getExternalFilesDir(null), fileBaseNameBond)
        } else {
            File(context.getExternalFilesDir(null), fileBaseNameShare)
        }.writeText(toFileString())

    } catch (e: Exception) {
        Log.e("MyLog", "Error")
    }
}

fun readKomission(context: Context, type: TypeOfInvestments): Komission {
    try {
        return if (type == TypeOfInvestments.BONDS) {
            File(context.getExternalFilesDir(null), fileBaseNameBond)
        } else {
            File(context.getExternalFilesDir(null), fileBaseNameShare)
        }.readLines()[0].StringToKomission(type)
    } catch (e: Exception) {
        Log.e("MyLog", "Error")
    }
    return Komission(type)
}