package com.petitcl.fddd4k.application.products

import arrow.core.raise.Raise
import com.petitcl.fddd4k.application.common.publishAll
import com.petitcl.fddd4k.domain.common.ApplicationError
import com.petitcl.fddd4k.domain.products.CreateProductCommand
import com.petitcl.fddd4k.domain.products.Product
import com.petitcl.fddd4k.domain.products.createProduct
import com.petitcl.fddd4k.infrastructure.products.ProductsRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class ProductsService(
    private val repository: ProductsRepository,
    private val eventsPublisher: ApplicationEventPublisher,
) {

    context(Raise<ApplicationError>)
    fun handleCreateProductCommand(command: CreateProductCommand): Product {
        val (product, events) = createProduct(command)
        
        val savedProduct = repository.save(product)
        eventsPublisher.publishAll(events)

        return savedProduct
    }
}