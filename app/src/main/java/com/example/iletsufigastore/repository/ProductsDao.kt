package com.example.iletsufigastore.repository

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(product: Products): Long

    @Delete
    suspend fun delete(vararg product: Products): Int

    @Query ("SELECT*FROM Products")
    fun allCartItems(): Flow<List<Products>>

    @Query("SELECT COUNT(id) FROM Products WHERE id = :id")
    suspend fun isInCart(id: Int): Int

}