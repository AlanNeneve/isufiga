package com.example.iletsufigastore.webservice

import com.example.iletsufigastore.repository.Products
import retrofit2.Call
import retrofit2.http.GET

interface ProductsWebService {

    @GET("products")
    fun getProducts(): Call<List<Products>>

}