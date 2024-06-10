package com.oaiscloud.billing.core.utils

import java.text.NumberFormat
import java.util.Locale

class NumberUtils {
    companion object {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    }
}