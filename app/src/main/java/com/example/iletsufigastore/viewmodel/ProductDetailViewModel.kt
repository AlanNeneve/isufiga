package com.example.iletsufigastore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iletsufigastore.repository.CartItems
import com.example.iletsufigastore.repository.ProductsRepository
import kotlinx.coroutines.launch

class ProductDetailViewModel (private val repository: ProductsRepository): ViewModel(){

    private val _isInCart = MutableLiveData<Boolean>()
    val isInCart: LiveData<Boolean> = _isInCart

    fun onCreate(item: CartItems){
        viewModelScope.launch {
            _isInCart.value = repository.isInCart(item.id)
        }
    }

    fun addToCart(item: CartItems){
        viewModelScope.launch {
            repository.addToCart(item)
            _isInCart.value = repository.isInCart(item.id)
        }
    }

    fun removeFromCart(item: CartItems){
        viewModelScope.launch {
            repository.delete(item)
            _isInCart.value = repository.isInCart(item.id)
        }
    }
}