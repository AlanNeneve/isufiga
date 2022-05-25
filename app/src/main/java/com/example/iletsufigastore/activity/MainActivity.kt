package com.example.iletsufigastore.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iletsufigastore.R
import com.example.iletsufigastore.adapter.ProductsAdapter
import com.example.iletsufigastore.repository.Products
import com.example.iletsufigastore.viewmodel.ProductsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
class MainActivity : AppCompatActivity() {

    companion object {
        /*
        Isso aqui também é muito bom, é recomendado evitar o uso de valores de Strings de forma
        solta no código e usar uma variável constante para isso.
         */
        const val ID_KEY = "ID"
    }

    private val viewModel: ProductsViewModel by viewModel()

    private val adapter = ProductsAdapter(
        onItemClick = { product: Products ->
            /*
            Aqui o trecho que eu mencionei que poderia ter sido refatorado para um método.
             */
            val intent = Intent(this, ProductDetailActivity::class.java)
            val extraID = Bundle()
            extraID.putParcelable(ID_KEY, product)
            intent.putExtras(extraID)
            startActivity(intent)
        },
        context = this
    )

    // Massa, tá bem organizado!
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.cartMenu){
            val cartIntent = Intent(this, CartActivity::class.java)
            startActivity(cartIntent)
        }
        return true
    }
}






