package com.example.iletsufigastore.dependencyinjection

import com.example.iletsufigastore.repository.ProductsRepository
import com.example.iletsufigastore.viewmodel.ProductsViewModel
import com.example.iletsufigastore.webservice.ProductsWebService
import com.example.iletsufigastore.webservice.ProductsWebServiceFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module{


    factory{
        ProductsRepository(
            productsWebService = ProductsWebServiceFactory.createWebService()
                .create(ProductsWebService::class.java)
        )
    }

    viewModel{
        ProductsViewModel(
            repository = get()
        )
    }

}

