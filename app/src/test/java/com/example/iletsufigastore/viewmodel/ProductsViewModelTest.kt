package com.example.iletsufigastore.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.iletsufigastore.CoroutinesTestRule
import com.example.iletsufigastore.repository.Products
import com.example.iletsufigastore.repository.ProductsRepository
import com.example.iletsufigastore.webservice.ResponseResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductsViewModelTest {

    // 1. Configurar como vai ser a execução do livedata
    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    // 2. Configurar como vai ser a execução do viewmodelscope
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    // 3. Configurar o mock do repository
    private val repository = mockk<ProductsRepository>()
    private val viewModel = ProductsViewModel(repository)

    @Test
    fun `Should return success when repository succeeds`() {
        //Arrange
        val response = listOf(
            Products(
                title = "title",
                id = 2,
                price = 2.0,
                description = "two",
                category = "cat",
                image = "linkImage"
            )
        )

        val expected = ProductsViewModel.State.ProductsList(products = response)

        coEvery { repository.getProducts() } returns ResponseResult.Success(response)

        //Action
        viewModel.fetchProducts()

        //Assert
        assertEquals(expected, viewModel.state.value)

        coVerify(exactly = 1) { repository.getProducts() }
    }

    @Test
    fun `Should return error when repository fails`() {
        //Arrange
        coEvery { repository.getProducts() } returns ResponseResult.Error

        //Action
        viewModel.fetchProducts()

        //Assert
        assertEquals(null, viewModel.state.value)

        coVerify(exactly = 1) { repository.getProducts() }
    }

    @Test
    fun `Should return list returned by filter method on repository`() {
        //Arrange
        val response = listOf(
            Products(
                title = "title",
                id = 2,
                price = 2.0,
                description = "two",
                category = "cat",
                image = "linkImage"
            )
        )

        val expected = ProductsViewModel.State.ProductsList(products = response)

        coEvery { repository.searchProduct("query") } returns response

        //Action
        viewModel.searchProduct("query")

        //Assert
        assertEquals(expected, viewModel.state.value)

        coVerify(exactly = 1) { repository.searchProduct("query") }
    }
}