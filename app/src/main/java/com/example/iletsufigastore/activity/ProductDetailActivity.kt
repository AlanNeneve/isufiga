package com.example.iletsufigastore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.iletsufigastore.R
import com.example.iletsufigastore.repository.Products
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_detail.*
import android.text.method.ScrollingMovementMethod
import com.example.iletsufigastore.repository.ProductCartItemMapper
import com.example.iletsufigastore.viewmodel.ProductDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailActivity : AppCompatActivity() {

    private val viewModel: ProductDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_detail)

        val selectedProduct = intent.extras?.getParcelable<Products>(MainActivity.ID_KEY)
        if(selectedProduct != null){
            val category = formatCategory(selectedProduct.category)
            bindViews(selectedProduct, category)
            itemInCartObserver(selectedProduct)
            viewModel.onCreate(ProductCartItemMapper.productToCartItem(
                selectedProduct)
            )
        } else {
            finish() // Fazer algo mais decente aqui ou um check melhor
        }
    }

    private fun itemInCartObserver(selectedProduct: Products) {
        viewModel.isInCart.observe(
            /*
            A primeira coisa que você pode fazer aqui é seguir a sugestão da IDE
            e fazer a alteração sugerida no ALT + ENTER. Depois, acho que vale
            notar que boa parte do código é parecido, o que muda é o ícone configurado
            e a ação feita pelo ViewModel. Como tu pensa em transformar isso num método
            em que esses 2 pontos sejam configurados?
             */
            this, { isInCart ->
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
        )
    }

    private fun bindViews(selectedProduct: Products?, category:String?) {
        Picasso.get().load(selectedProduct?.image).error(R.drawable.broken_image)
            .into(detailImageView)
        detailTitle.text = selectedProduct?.title
        productDescription.text = selectedProduct?.description
        productCategory.text = category
        // Massa que tu tá usando as strings pelo resources lá no strings.xml, boa!
        productPrice.text = this.getString(R.string.dollar_price, selectedProduct?.price.toString())
        productDescription.movementMethod = ScrollingMovementMethod()
        productID.text = this.getString(R.string.item_id, selectedProduct?.id.toString())
    }

    private fun formatCategory(category: String?): String? {
        return category?.replaceFirstChar { it.uppercase() }
    }

}