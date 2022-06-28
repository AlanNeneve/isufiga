package com.example.iletsufigastore.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iletsufigastore.R
import com.example.iletsufigastore.repository.CartItems
import com.example.iletsufigastore.repository.ProductCartItemMapper
import com.example.iletsufigastore.repository.Products
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.products_items.view.imageView
import kotlinx.android.synthetic.main.products_items.view.productPrice
import kotlinx.android.synthetic.main.products_items.view.productTitle

class CartAdapter(
    private val items: List<CartItems>,
    private val onItemClick: (Products) -> Unit,
    ) : RecyclerView.Adapter<CartAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_items, parent, false)
        return ItemHolder(layout)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val volume = items[position]
        holder.itemTitle.text = volume.title
        Picasso.get().load(volume.image).error(R.drawable.broken_image).into(holder.itemImage)
        holder.itemPrice.text = volume.price.toString()
        holder.itemTitle.setOnClickListener { onItemClick(ProductCartItemMapper.cartItemToProduct(volume)) }
    }

    override fun getItemCount() = items.size

    class ItemHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val itemTitle: TextView = rootView.productTitle
        val itemImage: ImageView = rootView.imageView
        val itemPrice: TextView = rootView.productPrice
    }
}

