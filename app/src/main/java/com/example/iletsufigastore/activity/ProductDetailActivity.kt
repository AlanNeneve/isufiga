package com.example.iletsufigastore.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.iletsufigastore.R
import com.example.iletsufigastore.repository.Products
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_detail.*
import android.text.method.ScrollingMovementMethod
import androidx.core.content.ContextCompat.startActivity
import com.example.iletsufigastore.repository.ProductCartItemMapper
import com.example.iletsufigastore.viewmodel.ProductDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailActivity : AppCompatActivity() {

    private val viewModel: ProductDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_detail)

        val selectedProduct = intent.extras?.getParcelable<Products>(MainActivity.ID_KEY)
        if (selectedProduct != null) {
            val category = formatCategory(selectedProduct.category)
            bindViews(selectedProduct, category)
            itemInCartObserver(selectedProduct)
            viewModel.onCreate(
                ProductCartItemMapper.productToCartItem(
                    selectedProduct
                )
            )
        } else {
            finish() // Fazer algo mais decente aqui ou um check melhor
        }
    }

    private fun itemInCartObserver(selectedProduct: Products) {
        viewModel.isInCart.observe(
            this
        ) { isInCart ->
            configureAddRemoveFromCartButton(isInCart, selectedProduct)
        }
    }

    private fun configureAddRemoveFromCartButton(isInCart: Boolean, selectedProduct: Products) {
        if (isInCart) {
            addOrRemoveCartButton.setImageResource(R.drawable.ic_remove_cart)
            addOrRemoveCartButton.setOnClickListener {
                viewModel.removeFromCart(
                    ProductCartItemMapper.productToCartItem(
                        selectedProduct
                    )
                )
            }
        } else {
            addOrRemoveCartButton.setImageResource(R.drawable.ic_add_cart)
            addOrRemoveCartButton.setOnClickListener {
                viewModel.addToCart(
                    ProductCartItemMapper.productToCartItem(
                        selectedProduct
                    )
                )
            }
        }
    }

    private fun bindViews(selectedProduct: Products?, category: String?) {
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

    companion object {
        fun Activity.startProductDetailActivity(product: Products) {
            val intent = Intent(this, ProductDetailActivity::class.java)
            val extraID = Bundle()
            extraID.putParcelable(MainActivity.ID_KEY, product)
            intent.putExtras(extraID)
            startActivity(intent)
        }
    }

}