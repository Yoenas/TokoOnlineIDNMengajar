package com.yoenas.tokoonline.ui.agent.search

import com.yoenas.tokoonline.data.model.agent.ResponseAgentList

interface AgentSearchContract {
    interface Presenter {
        fun getAgent()
        fun searchAgent(keyword: String)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoadingAgent(loading: Boolean)
        fun onResultAgent(responseAgentList: ResponseAgentList)
    }
}