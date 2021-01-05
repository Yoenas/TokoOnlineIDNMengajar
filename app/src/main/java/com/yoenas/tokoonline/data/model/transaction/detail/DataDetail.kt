package com.yoenas.tokoonline.data.model.transaction.detail

import com.google.gson.annotations.SerializedName

data class DataDetail(
    @SerializedName("kd_transaksi_detail") val kd_transaksi_detail: Long?,
    @SerializedName("no_faktur") val no_faktur: String?,
    @SerializedName("kd_produk") val kd_produk: String?,
    @SerializedName("jumlah") val jumlah: String?,
    @SerializedName("harga") val harga: String?,
    @SerializedName("harga_rupiah") val harga_rupiah: String?,
    @SerializedName("nama_produk") val nama_produk: String?,
    @SerializedName("kategori") val kategori: String?,
    @SerializedName("gambar_produk") val gambar_produk: String?
)
