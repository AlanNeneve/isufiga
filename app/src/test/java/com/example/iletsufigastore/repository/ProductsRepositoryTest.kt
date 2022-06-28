package com.example.iletsufigastore.repository

import com.example.iletsufigastore.webservice.ProductsWebService
import com.example.iletsufigastore.webservice.ResponseResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ProductsRepositoryTest{

    private val mockDao = mockk<ProductsDao>()

    private val mockWebService = mockk<ProductsWebService>()

    private val repository = ProductsRepository(mockDao, mockWebService)

    @Test
    fun `Should return list with results when web service succeeds`() = runBlocking {
        // Arrange
        val expected = listOf(
            Products(
                title = "title",
                id = 2,
                price = 2.0,
                description = "two",
                category = "cat",
                image = "linkImage"
            )
        )

        coEvery {
            mockWebService.getProducts()
        } returns expected

        // Action
        val actual = repository.getProducts() as ResponseResult.Success

        // Assert
        assertEquals(expected, actual.data)

        coVerify(exactly = 1) { mockWebService.getProducts() }
    }

    @Test
    fun `Should return error when web service fails`() = runBlocking {
        // Arrange
        val expected = ResponseResult.Error

        coEvery {
            mockWebService.getProducts()
        } throws Throwable()

        // Action
        val actual = repository.getProducts()

        // Assert
        assertEquals(expected, actual)
        coVerify(exactly = 1) { mockWebService.getProducts() }
    }

    @Test
    fun `Should return list with results when query is contained in list`() = runBlocking {
        // Arrange
        val response = listOf(
            Products(
                title = "title",
                id = 2,
                price = 2.0,
                description = "two",
                category = "cat",
                image = "linkImage"
            ), Products(
                title = "git2",
                id = 2,
                price = 2.0,
                description = "three",
                category = "cat",
                image = "linkImage"
            )
        )

        val expected = listOf(
            Products(
                title = "title",
                id = 2,
                price = 2.0,
                description = "two",
                category = "cat",
                image = "linkImage"
            )
        )

        // Stubbing
        coEvery {
            mockWebService.getProducts()
        } returns response

        // Action
        repository.getProducts() as ResponseResult.Success
        val actual = repository.searchProduct("title")

        // Assert
        assertEquals(expected, actual)

        coVerify(exactly = 1) { mockWebService.getProducts() }
    }

    @Test
    fun `Should return empty list when list is empty`() = runBlocking {
        // Arrange
        val expected = emptyList<Products>()

        coEvery {
            mockWebService.getProducts()
        } returns expected

        // Action
        val actual = repository.getProducts() as ResponseResult.Success

        // Assert
        assertEquals(expected, actual.data)

        coVerify(exactly = 1) { mockWebService.getProducts() }
    }

    @Test
    fun `Should call add correctly when repository adds item locally`() = runBlocking {
        //Arrange
        val input = CartItems(
            title = "title",
            id = 2,
            price = 2.0,
            description = "two",
            category = "cat",
            image = "linkImage"
        )

        val product = Products(
            title = "title",
            id = 2,
            price = 2.0,
            description = "two",
            category = "cat",
            image = "linkImage"
        )

        coEvery {
            mockDao.add(product)
        } returns 0

        //Action
        repository.addToCart(input)

        //Assert
        coVerify(exactly = 1) { mockDao.add(product) }
    }

    @Test
    fun `Should call delete correctly when repository deletes item locally`() = runBlocking {
        //Arrange
        val input = CartItems(
            title = "title",
            id = 2,
            price = 2.0,
            description = "two",
            category = "cat",
            image = "linkImage"
        )

        val product = Products(
            title = "title",
            id = 2,
            price = 2.0,
            description = "two",
            category = "cat",
            image = "linkImage"
        )

        coEvery {
            mockDao.delete(product)
        } returns 0

        //Action
        repository.delete(input)

        //Assert
        coVerify(exactly = 1) { mockDao.delete(product) }
    }

    @Test
    fun `Should return true when repository calls isInCart given product exist`() = runBlocking {

        //Arrange
        val id = 2
        val expected = true

        coEvery {
            mockDao.isInCart(id)
        } returns 1

        //Action
        val actual = repository.isInCart(id)

        //Assert
        assertEquals(expected, actual)
        coVerify(exactly = 1) { mockDao.isInCart(id) }
    }

    @Test
    fun `Should return false when repository calls isInCart given product does not exist`() = runBlocking {
        //Arrange
        val expected = false
        val id = 3

        coEvery {
            mockDao.isInCart(id)
        } returns 0

        //Action
        val actual = repository.isInCart(id)

        //Assert
        assertEquals(expected, actual)
        coVerify(exactly = 1) { mockDao.isInCart(id) }
    }
}