package ru.akaardent.primitivejetpackcomposecalculator

data class Bond(
    var name: String = "",
    var quantityText: String = "",
    var priceText: String = "",
    var nominalText: String = "",
    var couponText: String = "",
    var numberOfCouponText: String = "",
    var numberOfBondsText: String = "",
    var nkdText: String = "",
    var termText: String = "",
) {
    override fun toString(): String {
        return "Bond(quantity=${quantity}, price=${price}, number=$numberOfBonds, " + "nominal=$nominal, coupon=$coupon,num_kupons = $numberOfCoupon, nkd=$nkd, term=$term)"
    }

    fun myIsEmpty(): Boolean {
        return (quantityText.isEmpty() || priceText.isEmpty() || nominalText.isEmpty() || couponText.isEmpty() || numberOfCouponText.isEmpty() || numberOfBondsText.isEmpty() || nkdText.isEmpty())
//

    }

    private var quantity: Int = try {
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
    var numberOfBonds: Int = try {
        numberOfBondsText.toInt()
    } catch (e: Exception) {
        0
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

fun Bond.investments(): String {
    val komis = 0
    val sumCoup = coupon * numberOfCoupon * numberOfBonds
    val resultKomission = price * komis / 100 * numberOfBonds
    val zatrati = numberOfBonds * (price + nkd) + resultKomission
    val prib = (numberOfBonds * nominal - zatrati) * 0.87 + coupon * numberOfCoupon * 0.87
    return if (name != "") {
        "Компания: $name\n"
    } else {
        ""
    } + "Горизонт - $term\n" + "Доходность - ${prib / zatrati * 100}%\n" + "Затраты - ${zatrati}р (включая комиссию: ${komis}% : ${resultKomission}р\n" + "Купонами будет начислено: ${sumCoup * 0.87}р, (сумма начислений: ${sumCoup}р, Налог: ${sumCoup * 0.13}р)\n" + "Номинал вернется: ${nominal * numberOfBonds}р\n" + "Выручка(общая сумма поступлений): ${nominal * numberOfBonds + sumCoup * 0.87}p\n" + "Налог на прибыль по номиналу(без купонов): ${(numberOfBonds * nominal - zatrati) * 0.13}р." + "Прибыль - ${prib}р\n"
}