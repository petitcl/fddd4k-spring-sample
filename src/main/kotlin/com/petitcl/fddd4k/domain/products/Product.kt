package com.petitcl.fddd4k.domain.products

import com.petitcl.fddd4k.domain.common.Money
import java.util.UUID

@JvmInline
value class ProductId(val value: UUID) {
    companion object {
        fun new(): ProductId = ProductId(UUID.randomUUID())
    }
}

@JvmInline
value class ProductName(val value: String) {
    init {
        require(value.isNotBlank()) { "Product name cannot be blank" }
    }
}

data class Product(
    val productId: ProductId,
    val productName: ProductName,
    val description: String,
    val price: Money,
)
