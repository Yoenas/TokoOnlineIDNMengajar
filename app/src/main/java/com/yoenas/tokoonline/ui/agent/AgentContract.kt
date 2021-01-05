package com.yoenas.tokoonline.ui.agent

import com.yoenas.tokoonline.data.model.agent.DataAgent
import com.yoenas.tokoonline.data.model.agent.ResponseAgentList
import com.yoenas.tokoonline.data.model.agent.ResponseAgentUpdate

interface AgentContract {

    interface Presenter{
        fun getAgent()
        fun deleteAgent(kd_agen: Long)
    }

    interface View{
        fun initActivity()
        fun initListener()
        fun onLoadingAgent(loading: Boolean)
        fun onResultAgent(responseAgentList: ResponseAgentList)
        fun onResultDelete(responseAgentUpdate: ResponseAgentUpdate)
        fun showDialogDelete(dataAgent: DataAgent, position: Int)
        fun showDialogDetail(dataAgent: DataAgent, position: Int)
        fun showMessage(message: String)
    }
}