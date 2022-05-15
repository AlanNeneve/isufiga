package com.example.iletsufigastore.repository

import com.example.iletsufigastore.webservice.ProductsWebService
import com.example.iletsufigastore.webservice.ResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductsRepository(
    val productsWebService: ProductsWebService
) {

    private var products: List<Products> = emptyList()

    suspend fun getProducts(): ResponseResult<List<Products>> {
        return withContext(Dispatchers.IO) {
            try {
                val products = productsWebService.getProducts()
                ResponseResult.Success(products)
            } catch (exception: Throwable) {
                ResponseResult.Error
            }
        }
    }

    fun searchProduct(query: String) =
        products.filter { it.title.contains(query, ignoreCase = true) }
}

