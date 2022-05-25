package com.example.iletsufigastore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iletsufigastore.repository.Products
import com.example.iletsufigastore.repository.ProductsRepository
import com.example.iletsufigastore.webservice.ResponseResult
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val repository: ProductsRepository
) : ViewModel(){

    private val _state = MutableLiveData<State.ProductsList>()
    var state: LiveData<State.ProductsList> = _state

    fun fetchProducts() {
        viewModelScope.launch {
            when (val result = repository.getProducts()) {
                is ResponseResult.Success -> {
                    _state.value = State.ProductsList(result.data)
                }
                is ResponseResult.Error -> {
                    /*
                    FIUSAHFASIUFHASIUFHSAIUFH
                     */
                    Throwable("Pane no sistema alguem nao me configurou")
                }
            }
        }
    }

    fun searchProduct(query: String) {
       val filtered = repository.searchProduct(query)
        _state.value = State.ProductsList(filtered)
    }

    sealed class State {
        data class ProductsList(val products: List<Products>) : State()
    }

}