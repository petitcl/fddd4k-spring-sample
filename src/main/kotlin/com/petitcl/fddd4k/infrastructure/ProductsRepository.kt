package com.petitcl.fddd4k.infrastructure

import arrow.core.recover
import com.petitcl.fddd4k.domain.common.DefaultError
import com.petitcl.fddd4k.domain.common.ErrorOr
import com.petitcl.fddd4k.domain.products.Product
import com.petitcl.fddd4k.domain.products.ProductId

interface ProductsRepository {
    fun save(product: Product): ErrorOr<Product>
    fun findById(product: ProductId): ErrorOr<Product>
    fun findByIdOrNull(product: ProductId): ErrorOr<Product?> = findById(product)
        .recover {
            when (it) {
                is ProductNotFoundError -> null
                else -> raise(it)
            }
        }
}

data class ProductNotFoundError(val productId: ProductId)
    : DefaultError(message = "Product not found: $productId")
