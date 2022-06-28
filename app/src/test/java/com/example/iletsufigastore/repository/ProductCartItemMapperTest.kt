package com.example.iletsufigastore.repository

import com.example.iletsufigastore.repository.ProductCartItemMapper.cartItemToProduct
import com.example.iletsufigastore.repository.ProductCartItemMapper.productToCartItem
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ProductCartItemMapperTest {

    @Test
    fun `Should return Product when given CartItem`() = runBlocking {
        //Arrange
        val cartItem = CartItems(
        title = "Cart Item title",
        id = 1,
        price = 3.4,
        description = "Item test description",
        category = "Testing",
        image = "link_to_test_image"
        )

        val expected = Products(
            title = "Cart Item title",
            id = 1,
            price = 3.4,
            description = "Item test description",
            category = "Testing",
            image = "link_to_test_image"
        )

        //Action
        val actual = cartItemToProduct(cartItem)

        //Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `Should return CartItem when given Product`() = runBlocking {
        //Arrange
        val product = Products(
            title = "Cart Item title",
            id = 1,
            price = 3.4,
            description = "Item test description",
            category = "Testing",
            image = "link_to_test_image"
        )

        val expected = CartItems(
            title = "Cart Item title",
            id = 1,
            price = 3.4,
            description = "Item test description",
            category = "Testing",
            image = "link_to_test_image"
        )

        //Action
        val actual = productToCartItem(product)

        //Assert
        assertEquals(expected, actual)
    }

}