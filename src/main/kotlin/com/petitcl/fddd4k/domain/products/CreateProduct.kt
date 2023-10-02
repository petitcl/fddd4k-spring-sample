package com.petitcl.fddd4k.domain.products

import com.petitcl.fddd4k.domain.common.DomainEvent
import com.petitcl.fddd4k.domain.common.DomainOperationResult
import com.petitcl.fddd4k.domain.common.Money

data class ProductCreatedEvent(
    val productId: ProductId,
    val productName: ProductName,
    val description: String,
    val price: Money,
) : DomainEvent

data class CreateProductCommand(
    val productName: ProductName,
    val description: String,
    val price: Money,
)

fun createProduct(command: CreateProductCommand): DomainOperationResult<Product> {
    val productId = ProductId.new()
    val product = Product(
        productId = productId,
        productName = command.productName,
        description = command.description,
        price = command.price,
    )
    val events = listOf(
        ProductCreatedEvent(
            productId = productId,
            productName = command.productName,
            description = command.description,
            price = command.price,
        )
    )
    return product to events
}
