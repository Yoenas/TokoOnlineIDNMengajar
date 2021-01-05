package com.yoenas.tokoonline.data.model.product

import com.google.gson.annotations.SerializedName

data class DataCategory(
    @SerializedName("kd_kategori") val kd_kategori: Long?,
    @SerializedName("kategori") val kategori: String?,
    @SerializedName("gambar_kategori") val gambar_kategori: String?
)
