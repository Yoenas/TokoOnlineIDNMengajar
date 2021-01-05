package com.yoenas.tokoonline.data.model.agent

import com.google.gson.annotations.SerializedName

data class DataAgent(
    @SerializedName("kd_agen") val kd_agen: Long?,
    @SerializedName("nama_toko") val nama_toko: String?,
    @SerializedName("nama_pemilik") val nama_pemilik: String?,
    @SerializedName("alamat") val alamat: String?,
    @SerializedName("latitude") val latitude: String?,
    @SerializedName("longitude") val longitude: String?,
    @SerializedName("gambar_toko") val gambar_toko: String?
)
