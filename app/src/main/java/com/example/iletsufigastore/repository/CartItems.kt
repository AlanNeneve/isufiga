package com.example.iletsufigastore.repository

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartItems(
    val title: String,
    val id: Int,
    val price: Double,
    val description: String,
    val category: String,
    val image: String
): Parcelable
