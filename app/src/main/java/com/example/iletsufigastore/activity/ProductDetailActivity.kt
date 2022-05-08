package com.example.iletsufigastore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.iletsufigastore.R
import com.example.iletsufigastore.repository.Products
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_detail.*
import android.text.method.ScrollingMovementMethod

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_detail)

        val selectedProduct = intent.extras?.getParcelable<Products>(MainActivity.ID_KEY)

        val category = formatCategory(selectedProduct?.category)

        bindViews(selectedProduct, category)
    }

    private fun bindViews(selectedProduct: Products?, category:String?) {
        Picasso.get().load(selectedProduct?.image).error(R.drawable.broken_image)
            .into(detailImageView)
        detailTitle.text = selectedProduct?.title
        productDescription.text = selectedProduct?.description
        productCategory.text = category
        productPrice.text = this.getString(R.string.dollar_price, selectedProduct?.price.toString())
        productDescription.movementMethod = ScrollingMovementMethod()
        productID.text = this.getString(R.string.item_id, selectedProduct?.id.toString())
    }

    private fun formatCategory(category: String?): String? {
        return category?.replaceFirstChar { it.uppercase() }
    }
}