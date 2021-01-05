package com.yoenas.tokoonline.data.model.transaction.detail

import com.google.gson.annotations.SerializedName

data class ResponseTransactionDetail(
    @SerializedName("data") val dataDetail: List<DataDetail>
)
