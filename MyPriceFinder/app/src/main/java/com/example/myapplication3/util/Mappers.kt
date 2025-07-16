package com.example.myapplication3.util

import android.text.Html
import com.example.myapplication3.data.model.NaverShopItem
import com.example.myapplication3.domain.model.Product
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern

fun NaverShopItem.toDomain(isFavorite: Boolean): Product {
    return Product(
        name = Html.fromHtml(this.title, Html.FROM_HTML_MODE_LEGACY).toString(),
        price = parsePrice(this.lprice),
        imageUrl = this.image,
        productLink = this.link,
        isFavorite = isFavorite
    )
}

fun parsePrice(price: String): String {
    val pattern = Pattern.compile("\\d{1,3}(,\\d{3})*")
    val matcher = pattern.matcher(price)
    return if (matcher.find()) {
        try {
            val numberStr = matcher.group().replace(",", "")
            val number = numberStr.toLong()
            NumberFormat.getNumberInstance(Locale.US).format(number) + "원"
        } catch (e: NumberFormatException) {
            price
        }
    } else {
        price
    }
}