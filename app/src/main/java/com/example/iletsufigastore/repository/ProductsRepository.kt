package com.example.iletsufigastore.repository

import com.example.iletsufigastore.webservice.ProductsWebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsRepository(
    val productsWebService: ProductsWebService
) {

    private var products: List<Products> = emptyList()

    suspend fun getProducts(
        onSuccess: (List<Products>) -> Unit,
        onFailure: () -> Unit
    ) {
        withContext(Dispatchers.IO) {
            productsWebService.getProducts().enqueue(object : Callback<List<Products>> {
                override fun onResponse(
                    call: Call<List<Products>>,
                    response: Response<List<Products>>
                ) {
                    products = response.body()!!
                    onSuccess(products)
                }

                override fun onFailure(call: Call<List<Products>>, t: Throwable) {
                    onFailure()
                }
            })
        }
    }

    fun searchProduct(query: String) =
        products.filter { it.title.contains(query, ignoreCase = true) }
}

