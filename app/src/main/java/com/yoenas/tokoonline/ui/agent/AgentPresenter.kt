package com.yoenas.tokoonline.ui.agent

import android.util.Log
import com.yoenas.tokoonline.data.model.agent.ResponseAgentList
import com.yoenas.tokoonline.data.model.agent.ResponseAgentUpdate
import com.yoenas.tokoonline.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgentPresenter(var view: AgentContract.View?): AgentContract.Presenter {

    init {
        view!!.initActivity()
    }

    override fun getAgent() {
        view!!.onLoadingAgent(true)
        ApiService.endpoint.getAgent().enqueue(object : Callback<ResponseAgentList> {
            override fun onFailure(call: Call<ResponseAgentList>, t: Throwable) {
                Log.d("AgentPresenter", t.toString())
                view!!.onLoadingAgent(false)
            }

            override fun onResponse(
                call: Call<ResponseAgentList>,
                response: Response<ResponseAgentList>
            ) {
                view!!.onLoadingAgent(false)
                if (response.isSuccessful) {
                    val responseAgent: ResponseAgentList? = response.body()
                    view!!.onResultAgent(responseAgent!!)
                }
            }

        })
    }

    override fun deleteAgent(kd_agen: Long) {
        view?.onLoadingAgent(true)
        ApiService.endpoint.deleteAgent(kd_agen).enqueue(object : Callback<ResponseAgentUpdate> {
            override fun onResponse(
                call: Call<ResponseAgentUpdate>,
                response: Response<ResponseAgentUpdate>
            ) {
                view?.onLoadingAgent(false)
                if (response.isSuccessful) {
                    val responseAgentUpdate: ResponseAgentUpdate? = response.body()
                    view?.onResultDelete(responseAgentUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponseAgentUpdate>, t: Throwable) {
                view?.onLoadingAgent(false)
            }
        })
    }
}