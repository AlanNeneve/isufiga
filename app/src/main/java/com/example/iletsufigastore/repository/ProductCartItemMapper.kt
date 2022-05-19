package com.example.iletsufigastore.repository

object ProductCartItemMapper {
    fun productToCartItem(product: Products) = CartItems(
        product.title,
        product.id,
        product.price,
        product.description,
        product.category,
        product.image
    )

    fun cartItemToProduct(item: CartItems) = item.run{
        Products(
            item.title,
            item.id,
            item.price,
            item.description,
            item.category,
            item.image
        )
    }
}