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

    /*
    O código aqui tá bem limpo também, cada coisa no seu método!
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        configureRecyclerView()

        /*
        Sempre lembrar que nomes de método são verbos porque
        são uma ação, então esse aqui poderia ser `configureCartObserver`
         */
        cartObserver()
    }

    private fun configureRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        cartRecyclerView.layoutManager = layoutManager
    }

    private fun cartObserver() {
        viewModel.cartItems.observe(this) {
            /* Isso que você (items ->) fez aqui é bem interessante, deixa o parâmetro da lambda
            mais descritivo */ items ->
            cartRecyclerView.adapter = CartAdapter(items,
                onItemClick = { item: Products ->
                    /*
                    Essa parte do código, da linha 44 até a 48 poderia ser um método
                    porque você faz essa mesma ação em outras Activity.
                     */
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