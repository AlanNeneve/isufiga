package com.example.iletsufigastore.webservice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.iletsufigastore.repository.ProductsRepository

class CartItemsViewModel(
    repository: ProductsRepository
) : ViewModel(){

    val cartItems = repository.allCartItems().asLiveData()
}