package com.example.iletsufigastore.repository

import android.content.Context
import com.example.iletsufigastore.webservice.ProductsWebService
import com.example.iletsufigastore.webservice.ResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ProductsRepository(
    context: Context,
    val productsWebService: ProductsWebService
) {

    private val dao: ProductsDao = ProductsDatabase.getDatabase(context).getBookDao()

    private var products: List<Products> = emptyList()

    suspend fun getProducts(): ResponseResult<List<Products>> {
        return withContext(Dispatchers.IO) {
            try {
                val products = productsWebService.getProducts()
                ResponseResult.Success(products)
            } catch (exception: Throwable) {
                ResponseResult.Error
            }
        }
    }

    fun searchProduct(query: String) =
        /*
        Isso aqui também é observado numa análise de código, mostra
        que você tem domínio sobre os métodos que o Kotlin tem para
        as coleções, nesse caso, o `filter`.
         */
        products.filter { it.title.contains(query, ignoreCase = true) }

    suspend fun addToCart(item: CartItems) {
        dao.add(ProductCartItemMapper.cartItemToProduct(item))
    }

    suspend fun delete(item: CartItems) {
        dao.delete(ProductCartItemMapper.cartItemToProduct(item))
    }

    suspend fun isInCart(id: Int): Boolean {
        return dao.isInCart(id) > 0
    }

    fun allCartItems(): Flow<List<CartItems>> {
        return dao.allCartItems()
            /*
            Mesmo caso do comentário acima, agora com o `map`, mostra conhecimento sobre
            as funções de collections do Kotlin. Muitos processos de entrevistas
            tem um ponto de avaliação para proficiência com a linguagem escolhida
            e aqui nesse caso, quem estivesse analisando poderia coletar isso como evidência
            do teu conhecimento.
             */
            .map { productsList ->
                productsList.map { product ->
                    ProductCartItemMapper.productToCartItem(product)
                }
            }
    }
}

