package com.petitcl.fddd4k.domain.common

import java.math.BigDecimal
import java.util.Currency

data class Money(
    val amount: BigDecimal,
    val currency: Currency,
) {
    companion object {
        fun of(amount: BigDecimal, currency: Currency): Money = Money(amount, currency)
        fun of(amount: Double, currency: Currency): Money = Money(amount.toBigDecimal(), currency)
        fun of(amount: Double, currencyCode: String): Money
            = Money(amount.toBigDecimal(), Currency.getInstance(currencyCode))
    }
}