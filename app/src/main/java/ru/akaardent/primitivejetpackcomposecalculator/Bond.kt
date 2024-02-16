package ru.akaardent.primitivejetpackcomposecalculator

data class Bond(
    var name: String = "",
    var quantityText: String = "",
    var priceText: String = "",
    var nominalText: String = "",
    var couponText: String = "",
    var numberOfCouponText: String = "",
    var nkdText: String = "",
    var termText: String = "",
) {
    override fun toString(): String {
        return "Bond(quantity=${quantity}, price=${price}, number=$quantity, " + "nominal=$nominal, coupon=$coupon,num_kupons = $numberOfCoupon, nkd=$nkd, term=$term)"
    }

    fun myIsEmpty(): Boolean {
        return (quantityText.isEmpty() || priceText.isEmpty() || nominalText.isEmpty() || couponText.isEmpty() || numberOfCouponText.isEmpty() || quantityText.isEmpty() || nkdText.isEmpty())
//

    }

    var quantity: Int = try {
        quantityText.toInt()
    } catch (e: Exception) {
        0
    }
    var price: Float = try {
        priceText.toFloat()
    } catch (e: Exception) {
        0f
    }

    var nominal: Float = try {
        nominalText.toFloat()
    } catch (e: Exception) {
        0f
    }
    var coupon: Float = try {
        couponText.toFloat()
    } catch (e: Exception) {
        0f
    }
    var nkd: Float = try {
        nkdText.toFloat()
    } catch (e: Exception) {
        0f
    }
    var term: Float = try {
        termText.toFloat()
    } catch (e: Exception) {
        0f
    }
    var numberOfCoupon: Int = try {
        numberOfCouponText.toInt()
    } catch (e: Exception) {
        0
    }
}

fun Bond.investments(komis: Komission): String {
    val sumCoup = coupon * numberOfCoupon * quantity
    val resultKomission = price * komis.buy / 100 * quantity
    val zatrati = quantity * (price + nkd) + resultKomission
    val prib = (quantity * nominal - zatrati) * 0.87 + coupon * numberOfCoupon * 0.87
    return if (name != "") {
        "Компания: $name"
    } else {
        ""
    } + if (termText.isNotEmpty()) {
        "\nГоризонт - $term"
    } else {
        ""
    } + "\nДоходность: ${prib / zatrati * 100}%;" + "\nЗатраты: ${zatrati}р (включая комиссию: ${komis.sell}% : ${resultKomission}р);" + "\nКупонами будет начислено: ${sumCoup * 0.87}р;" + "\n(сумма начислений: ${sumCoup}р, \nНалог: ${sumCoup * 0.13}р);" + "\nНоминал вернется: ${nominal * quantity}р;" + "\nВыручка(общая сумма поступлений): \n${nominal * quantity + sumCoup * 0.87}p;" + "\nНалог на прибыль по номиналу(без купонов): \n${(quantity * nominal - zatrati) * 0.13}р;" + "\nПрибыль: ${prib}р;"
}