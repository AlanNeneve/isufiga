package com.example.iletsufigastore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.iletsufigastore.repository.ProductsRepository

/*
Não precisa mais dessa classe porque o Koin provê isso para você.
Caso alguém pergunte isso em algum momento. Aquele método `viewModel` quando
você vai criar o módulo do Koin, ele provê essa factory de ViewModel por baixo dos panos
pra você.
 */
class ProductsViewModelFactory(private val repository: ProductsRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductsViewModel(repository) as T
    }
}