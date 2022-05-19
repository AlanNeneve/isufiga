package com.example.iletsufigastore.dependencyinjection

import androidx.core.content.ContentProviderCompat.requireContext
import com.example.iletsufigastore.repository.ProductsRepository
import com.example.iletsufigastore.viewmodel.ProductDetailViewModel
import com.example.iletsufigastore.viewmodel.ProductsViewModel
import com.example.iletsufigastore.webservice.CartItemsViewModel
import com.example.iletsufigastore.webservice.ProductsWebService
import com.example.iletsufigastore.webservice.ProductsWebServiceFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{

    factory{
        ProductsRepository(
            productsWebService = get(),
            context = androidContext()
        )
    }

    factory {
        ProductsWebServiceFactory.createWebService()
            .create(ProductsWebService::class.java)
    }

    viewModel{
        ProductsViewModel(
            repository = get()
        )
    }
}

val DetailsViewModelModule = module{
    viewModel{
        ProductDetailViewModel(
            repository = get()
        )
    }
}

val CartViewModelModule = module{
    viewModel{
        CartItemsViewModel(
            repository = get()
        )
    }
}


