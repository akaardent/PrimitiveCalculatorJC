package ru.akaardent.primitivejetpackcomposecalculator

data class Share(
    var name: String = "",
    var quantityText: String = "",
    var priceBuyText: String = "",
    var priceSellText: String = "",
    ) {
    override fun toString(): String {
        return "Bond(quantity=${quantity}, priceBut=${priceBuy}, priceSell=${priceSell}, number=$quantity)"
    }

    fun myIsEmpty(): Boolean {
        return (quantityText.isEmpty() || priceBuyText.isEmpty() || priceSellText.isEmpty() || quantityText.isEmpty())
//
    }

    var quantity: Int = try {
        quantityText.toInt()
    } catch (e: Exception) {
        0
    }
    var priceBuy: Float = try {
        priceBuyText.toFloat()
    } catch (e: Exception) {
        0f
    }
    var priceSell: Float = try {
        priceSellText.toFloat()
    } catch (e: Exception) {
        0f
    }


}

fun Share.investments(komis: Komission): String {
    val resultKomissionBuy = komis.buy/100 * quantity * priceBuy
    val resultKomissionSell = komis.sell/100 * quantity * priceSell
    val zatrati = quantity * priceBuy + resultKomissionBuy + resultKomissionSell
    val prib = (quantity * priceSell - zatrati) * 0.87
    val nalog = (quantity * priceSell - zatrati) * 0.13
    return if (name != "") {
        "Компания: $name"
    } else {
        ""
    } + "\nДоходность: ${prib / zatrati * 100}%;\n" +
            "\nЗатраты: ${zatrati}р \n(включая комиссию: \nпокупка: ${komis.buy}%; \nпродажа: ${komis.sell}%: \n${resultKomissionBuy}р+ ${resultKomissionSell}р = ${resultKomissionBuy + resultKomissionSell}р);" +
            "\nНалог: ${nalog};" +
            "\nПрибыль: ${prib}р;"

}