package ru.akaardent.primitivejetpackcomposecalculator

fun String.enterInt(): String {
    if (this.isNotEmpty()) {
        val last = this[this.lastIndex]
        if (last == '-' || last == ' ' || last == ',' || last == '.') {
            return this.substring(0, this.lastIndex)
        }
    }
    return this
}

fun String.enterFloat(): String {
    if (this.isNotEmpty()) {
        val last = this[this.lastIndex]
        if (last == '-' || last == ' ') {
            return this.substring(0, this.lastIndex)
        }
        var text: String = this
        if (last == ',') {
            text = this.replace(',', '.')
        }
        return if (text.count { char ->
                char == '.'
            } <= 1) {
            text
        } else {
            this.substring(0, this.lastIndex)
        }
    }
    return this
}