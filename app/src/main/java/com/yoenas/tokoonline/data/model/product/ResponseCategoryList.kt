package com.yoenas.tokoonline.data.model.product

import com.google.gson.annotations.SerializedName

data class ResponseCategoryList(
    @SerializedName("status") val status: Boolean,
    @SerializedName("data") val dataCategory: List<DataCategory>
)
