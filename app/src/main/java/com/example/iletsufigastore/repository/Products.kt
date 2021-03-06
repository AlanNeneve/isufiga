package com.example.iletsufigastore.repository

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Products(
    val title: String,
    @PrimaryKey
    val id: Int,
    val price: Double,
    val description: String,
    val category: String,
    val image: String
) : Parcelable

