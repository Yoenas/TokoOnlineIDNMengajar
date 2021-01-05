package com.yoenas.tokoonline.ui.agent.search

import com.yoenas.tokoonline.data.model.agent.ResponseAgentList
import com.yoenas.tokoonline.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgentSearchPresenter(val view: AgentSearchContract.View): AgentSearchContract.Presenter  {

    init {
        view.initActivity()
        view.initListener()
    }

    override fun getAgent() {
        view.onLoadingAgent(true)
        ApiService.endpoint.getAgent()
            .enqueue(object : Callback<ResponseAgentList> {
                override fun onResponse(
                    call: Call<ResponseAgentList>,
                    response: Response<ResponseAgentList>
                ) {
                    view.onLoadingAgent(false)
                    if (response.isSuccessful){
                        val responseAgentList: ResponseAgentList? = response.body()
                        view.onResultAgent(responseAgentList!!)
                    }
                }

                override fun onFailure(call: Call<ResponseAgentList>, t: Throwable) {
                    view.onLoadingAgent(false)
                }
            })
    }

    override fun searchAgent(keyword: String) {

    }
}