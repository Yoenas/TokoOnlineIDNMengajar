package com.yoenas.tokoonline.data.model.agent

import com.google.gson.annotations.SerializedName
import com.yoenas.tokoonline.data.model.agent.DataAgent

data class ResponseAgentList(
    @SerializedName("status") val status: Boolean,
    @SerializedName("data") val dataAgent: List<DataAgent>
)
