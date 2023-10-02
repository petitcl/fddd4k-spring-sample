package com.petitcl.fddd4k.infrastructure.products

import arrow.core.raise.Raise
import arrow.core.raise.catch
import com.petitcl.fddd4k.domain.common.ApplicationError
import com.petitcl.fddd4k.domain.common.GenericError
import com.petitcl.fddd4k.domain.products.Product
import com.petitcl.fddd4k.domain.products.ProductId
import com.petitcl.fddd4k.infrastructure.common.upsertOneV1
import com.petitcl.fddd4k.infrastructure.products.ProductEntity.description
import com.petitcl.fddd4k.infrastructure.products.ProductEntity.name
import com.petitcl.fddd4k.infrastructure.products.ProductEntity.priceAmount
import com.petitcl.fddd4k.infrastructure.products.ProductEntity.priceCurrency
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.upsert
import org.springframework.stereotype.Repository
import java.util.Currency

internal object ProductEntity : UUIDTable() {
    val name = varchar("product_name", 255)
    val description = varchar("description", 500)
    val priceAmount = decimal("price_amount", 65, 8)
    val priceCurrency = varchar("price_currency", 3)
}

@Repository
internal class ExposedProductsRepository : ProductsRepository {

    context(Raise<ApplicationError>)
    override fun save(product: Product): Product = catch(
        {
            ProductEntity.upsertOneV1 {
                it[id] = product.productId.value
                it[name] = product.productName.value
                it[description] = product.description
                it[priceAmount] = product.price.amount
                it[priceCurrency] = product.price.currency.currencyCode
            }.toDomain()
        },
        { raise(GenericError(it)) }
    )

    context(Raise<ApplicationError>)
    override fun findByIdOrNull(productId: ProductId): Product? = catch(
        {
            ProductEntity
                .select { ProductEntity.id eq productId.value }
                .firstOrNull()
                ?.toDomain()
        },
        { raise(GenericError(it)) }
    )
}

internal fun ResultRow.toDomain(): Product = Product(
    productId = ProductId(this[ProductEntity.id].value),
    productName = com.petitcl.fddd4k.domain.products.ProductName(this[ProductEntity.name]),
    description = this[ProductEntity.description],
    price = com.petitcl.fddd4k.domain.common.Money(
        amount = this[ProductEntity.priceAmount],
        currency = Currency.getInstance(this[ProductEntity.priceCurrency]),
    )
)
