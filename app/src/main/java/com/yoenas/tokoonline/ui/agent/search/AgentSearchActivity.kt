package com.yoenas.tokoonline.ui.agent.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.Constant
import com.yoenas.tokoonline.data.model.agent.DataAgent
import com.yoenas.tokoonline.data.model.agent.ResponseAgentList

class AgentSearchActivity : AppCompatActivity(), AgentSearchContract.View {

    private lateinit var agentSearchPresenter: AgentSearchPresenter
    private lateinit var agentSearchAdapter: AgentSearchAdapter

    private lateinit var rvSearchAgent: RecyclerView
    private lateinit var swipeSearch: SwipeRefreshLayout
    private lateinit var edtSearch: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent_search)

        agentSearchPresenter = AgentSearchPresenter(this)
        agentSearchPresenter.getAgent()

        rvSearchAgent = findViewById(R.id.rv_search_agent)
        swipeSearch = findViewById(R.id.swipe_search)
        edtSearch = findViewById(R.id.edt_search)
    }

    override fun initActivity() {
        supportActionBar!!.title = "Pilih Agen"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun initListener() {
        agentSearchAdapter = AgentSearchAdapter(
            this,
            arrayListOf()
        ) { dataAgent: DataAgent, position: Int ->
            Constant.AGENT_ID = dataAgent.kd_agen!!
            Constant.AGENT_NAME = dataAgent.nama_toko!!
            Constant.IS_CHANGE = true
            finish()
        }

        edtSearch.setOnEditorActionListener { view, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                agentSearchPresenter.searchAgent(edtSearch.text.toString())
                true
            } else {
                false
            }
        }

        rvSearchAgent.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = agentSearchAdapter
        }

        swipeSearch.setOnRefreshListener {
            agentSearchPresenter.getAgent()
        }
    }

    override fun onLoadingAgent(loading: Boolean) {
        when (loading) {
            true -> swipeSearch.isRefreshing = true
            false -> swipeSearch.isRefreshing = false
        }
    }

    override fun onResultAgent(responseAgentList: ResponseAgentList) {
        val dataAgent: List<DataAgent> = responseAgentList.dataAgent
        agentSearchAdapter.setData(dataAgent)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}