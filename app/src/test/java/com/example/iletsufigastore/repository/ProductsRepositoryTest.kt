package com.example.iletsufigastore.repository

import com.example.iletsufigastore.webservice.ProductsWebService
import com.example.iletsufigastore.webservice.ResponseResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Before
import org.junit.Test

class ProductsRepositoryTest {

    private val mockWebService = mockk<ProductsWebService>()

    private val repository = ProductsRepository(mockWebService)

    @Before
    fun setUp() {
    }

    @Test
    fun `Should return list with results when web service succeeds`() = runBlocking {
        // AAA
        // Arrange - Action - Assert

        // Arrange
        val expected = listOf(
            Products(
                title = "title",
                id = 2,
                price = 2.0,
                description = "dois",
                category = "catega",
                image = "linkImage"
            )
        )

        // Stubbing
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

        // Stubbing
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
    fun `Should return list with results when query is contained in list`() {

    }

    @Test
    fun `Should return empty list when list is empty`() {

    }

    @After
    fun tearDown() {
    }
}