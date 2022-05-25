package com.example.iletsufigastore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.iletsufigastore.R
import com.example.iletsufigastore.repository.Products
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.products_items.view.*

open class ProductsAdapter(
    private val onItemClick: (Products) -> Unit,
    private val context: Context
) : RecyclerView.Adapter<ProductsAdapter.ProductsHolder>() {

    private var products: List<Products> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.products_items, parent, false)
        return ProductsHolder(layout)
    }

    fun configureList(products: List<Products>) {
        this.products = products
        /*
        Vale dar uma pesquisada na internet sobre os problemas relacionados
        ao uso de `notifyDataSetChanged()` e quais possíveis melhorias seriam feitas aqui.
        Esse tema é algo que poderia ser questionado no teu processo para um emprego. Não
        precisa saber implementar o correto, mas saber identificar o que está errado e quais
        possíveis caminhos é interessante.
         */
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProductsHolder, position: Int) {
        val volume = products[position]
        holder.productItemView.text = volume.title
        Picasso.get().load(volume.image).error(R.drawable.broken_image).into(holder.productImageView)
        /*
        Uso de strings pelo resources com o placeholder que está sendo usado para configurar
        o preço, boa!
         */
        holder.productPrice.text = context.getString(R.string.dollar_price, volume.price.toString())
        holder.productLinearLayout.setOnClickListener { onItemClick(volume) }
        holder.productCategory.text = volume.category.replaceFirstChar { it.uppercase() }
    }

    override fun getItemCount() = products.size

    class ProductsHolder(rootView: View): RecyclerView.ViewHolder(rootView) {
        val productItemView: TextView = rootView.productTitle
        val productImageView: ImageView = rootView.imageView
        val productPrice: TextView = rootView.productPrice
        val productLinearLayout: LinearLayout = rootView.itemLine
        val productCategory: TextView = rootView.productCategory
    }
}