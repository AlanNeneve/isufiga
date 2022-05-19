package com.example.iletsufigastore.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iletsufigastore.R
import com.example.iletsufigastore.adapter.CartAdapter
import com.example.iletsufigastore.repository.Products
import com.example.iletsufigastore.webservice.CartItemsViewModel
import kotlinx.android.synthetic.main.activity_cart.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartActivity : AppCompatActivity() {

    private val viewModel: CartItemsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        configureRecyclerView()

        cartObserver()
    }

    private fun configureRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        cartRecyclerView.layoutManager = layoutManager
    }

    private fun cartObserver() {
        viewModel.cartItems.observe(this) { items ->
            cartRecyclerView.adapter = CartAdapter(items,
                onItemClick = { item: Products ->
                    val intent = Intent(this, ProductDetailActivity::class.java)
                    val extraID = Bundle()
                    extraID.putParcelable(MainActivity.ID_KEY, item)
                    intent.putExtras(extraID)
                    startActivity(intent)
                }
            )
        }
    }
}