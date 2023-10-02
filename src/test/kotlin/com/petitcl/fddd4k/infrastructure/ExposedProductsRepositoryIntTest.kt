package com.petitcl.fddd4k.infrastructure

import arrow.core.raise.either
import com.petitcl.fddd4k.domain.common.Money
import com.petitcl.fddd4k.domain.common.getOrThrow
import com.petitcl.fddd4k.domain.products.Product
import com.petitcl.fddd4k.domain.products.ProductId
import com.petitcl.fddd4k.domain.products.ProductName
import com.petitcl.fddd4k.infrastructure.products.ProductsRepository
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.shouldBe
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

private fun makeProduct() = Product(
    productId = ProductId.new(),
    productName = ProductName("Blue Shoes"),
    description = "Blue shoes collection Summer 2023",
    price = Money.of(100.0, "EUR"),
)

@SpringBootTest
class ExposedProductsRepositoryIntTest {

    @Autowired private lateinit var sut: ProductsRepository

    @Test
    fun `should allow to retrieve product after creating it`() {
        either {
            transaction {
                val p0 = makeProduct()
                val p1 = sut.save(p0)

                p1 shouldMatch p0

                val p2 = sut.findById(p0.productId)
                p2 shouldMatch p0
            }
        }.getOrThrow()
    }

    @Test
    fun `should allow to updated product after creating it`() {
        either {
            transaction {
                val p0 = makeProduct()
                val p1 = sut.save(p0)

                val p2 = p1.copy(
                    productName = ProductName("Red Shoes"),
                    description = "Red shoes collection Summer 2023",
                    price = Money.of(200.0, "EUR"),
                )

                val p3 = sut.save(p2)
                p3 shouldMatch p2
            }
        }.getOrThrow()
    }

    @Test
    fun `should return null if product does not exist`() {
        either {
            transaction {
                val product = sut.findByIdOrNull(ProductId.new())

                product shouldBe null
            }
        }.getOrThrow()
    }
}

internal infix fun Product.shouldMatch(other: Product) {
    this.productId shouldBe other.productId
    this.productName shouldBe other.productName
    this.description shouldBe other.description
    this.price.amount shouldBeEqualComparingTo other.price.amount
    this.price.currency shouldBe other.price.currency
}