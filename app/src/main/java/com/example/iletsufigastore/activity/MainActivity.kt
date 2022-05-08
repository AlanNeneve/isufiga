package com.example.iletsufigastore.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iletsufigastore.R
import com.example.iletsufigastore.adapter.ProductsAdapter
import com.example.iletsufigastore.repository.Products
import com.example.iletsufigastore.repository.ProductsRepository
import com.example.iletsufigastore.viewmodel.ProductsViewModel
import com.example.iletsufigastore.viewmodel.ProductsViewModelFactory
import com.example.iletsufigastore.webservice.ProductsWebService
import com.example.iletsufigastore.webservice.ProductsWebServiceFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val ID_KEY = "ID"
    }

    private val adapter = ProductsAdapter(
        onItemClick = { product: Products ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            val extraID = Bundle()
            extraID.putParcelable(ID_KEY, product)
            intent.putExtras(extraID)
            startActivity(intent)
        },
        context = this
    )

    private val viewModel: ProductsViewModel by lazy {
        ViewModelProvider(
            this,
            ProductsViewModelFactory(
                ProductsRepository(
                    productsWebService = ProductsWebServiceFactory.createWebService()
                        .create(ProductsWebService::class.java)
                )
            )
        )[ProductsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureRecyclerView()

        configureObserver()

        viewModel.fetchProducts()

        productSearch()
    }

    private fun productSearch() {
        productSearch.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchProduct(query)
                if (query.isEmpty()) configureObserver()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchProduct(newText)
                return false
            }
        })
    }

    private fun configureRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        recyclerView.addItemDecoration(
            DividerItemDecoration(this, layoutManager.orientation)
        )
        recyclerView.adapter = adapter
    }

    private fun configureObserver() {
        viewModel.state.observe(this) {
            adapter.configureList(it.products)
        }
    }
}




