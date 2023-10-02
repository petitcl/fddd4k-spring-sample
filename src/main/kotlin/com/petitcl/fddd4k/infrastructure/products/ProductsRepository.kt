package com.petitcl.fddd4k.infrastructure.products

import arrow.core.raise.Raise
import com.petitcl.fddd4k.domain.common.ApplicationError
import com.petitcl.fddd4k.domain.common.DefaultError
import com.petitcl.fddd4k.domain.products.Product
import com.petitcl.fddd4k.domain.products.ProductId

interface ProductsRepository {

    context(Raise<ApplicationError>)
    fun save(product: Product): Product

    context(Raise<ApplicationError>)
    fun findByIdOrNull(productId: ProductId): Product?

    context(Raise<ApplicationError>)
    fun findById(productId: ProductId): Product =
        findByIdOrNull(productId) ?: raise(ProductNotFoundError(productId))

}

data class ProductNotFoundError(val productId: ProductId) : DefaultError(message = "Product not found: $productId")
