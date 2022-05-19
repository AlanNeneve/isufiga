package com.example.iletsufigastore.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Products::class],
    version = 1,
    exportSchema = false
)
abstract class ProductsDatabase(): RoomDatabase() {

    abstract fun getBookDao(): ProductsDao

    companion object{
        private var instance: ProductsDatabase? = null

        fun getDatabase(context: Context): ProductsDatabase{
            if(instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductsDatabase::class.java,
                    "appDb"
                ).build()
            }
            return instance!!
        }
    }
}