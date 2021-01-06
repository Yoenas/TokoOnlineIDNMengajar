package com.yoenas.tokoonline.ui.agent

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.Constant
import com.yoenas.tokoonline.data.model.agent.DataAgent
import com.yoenas.tokoonline.data.model.agent.ResponseAgentList
import com.yoenas.tokoonline.data.model.agent.ResponseAgentUpdate
import com.yoenas.tokoonline.ui.agent.create.AgentCreateActivity
import com.yoenas.tokoonline.ui.agent.update.AgentUpdateActivity
import com.yoenas.tokoonline.utils.GlideHelper
import com.yoenas.tokoonline.utils.MapsHelper

class AgentActivity : AppCompatActivity(), AgentContract.View, OnMapReadyCallback {

    private lateinit var presenter: AgentPresenter
    private lateinit var agentAdapter: AgentAdapter

    private lateinit var toolbarAgent: Toolbar
    private lateinit var fab: FloatingActionButton
    private lateinit var swipeLayoutAgent: SwipeRefreshLayout
    private lateinit var rvAgent: RecyclerView

    private lateinit var agent: DataAgent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent)

        presenter = AgentPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.getAgent()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun initActivity() {
        supportActionBar?.title = "Agen"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarAgent = findViewById(R.id.toolbar_agent)
        setSupportActionBar(toolbarAgent)
        fab = findViewById(R.id.fab_agent)
        rvAgent = findViewById(R.id.rv_agent)
        swipeLayoutAgent = findViewById(R.id.swipe_agent)

        MapsHelper.permissionMap(this, this)
    }

    override fun initListener() {
        agentAdapter = AgentAdapter(this, arrayListOf()){
            dataAgent: DataAgent, position: Int, type: String ->

            agent = dataAgent

            when (type) {
                "update" -> startActivity(Intent(this, AgentUpdateActivity::class.java))
                "delete" -> showDialogDelete(dataAgent, position)
                "detail" -> showDialogDetail(dataAgent, position)
            }
        }

        rvAgent.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = agentAdapter
        }

        swipeLayoutAgent.setOnRefreshListener {
            presenter.getAgent()
        }

        fab.setOnClickListener {
            startActivity(Intent(this, AgentCreateActivity::class.java))
        }
    }

    override fun onLoadingAgent(loading: Boolean) {
        when (loading) {
            true -> swipeLayoutAgent.isRefreshing = true
            false -> swipeLayoutAgent.isRefreshing = false
        }
    }

    override fun onResultAgent(responseAgentList: ResponseAgentList) {
        val dataAgent: List<DataAgent> = responseAgentList.dataAgent
        agentAdapter.setData(dataAgent)
    }

    override fun onResultDelete(responseAgentUpdate: ResponseAgentUpdate) {
        showMessage(responseAgentUpdate.msg)
    }

    override fun showDialogDelete(dataAgent: DataAgent, position: Int) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Konfirmasi")
            .setMessage("Hapus ${agent.nama_toko}?")
            .setPositiveButton("Hapus"){ dialog, which ->
                presenter.deleteAgent(Constant.AGENT_ID)
                agentAdapter.deleteAgent(position)
                dialog.dismiss()
            }
            .setNegativeButton("Batal"){ dialog, which ->
                dialog.dismiss()
            }
        dialog.show()
    }

    override fun showDialogDetail(dataAgent: DataAgent, position: Int) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_agent, null)

        val imgStoreDialog: ImageView = findViewById(R.id.img_store_dialog)
        val tvNameStoreDialog: TextView = findViewById(R.id.tv_name_store_dialog)
        val tvNameDialog: TextView = findViewById(R.id.tv_name_dialog)
        val tvAddressDialog: TextView = findViewById(R.id.tv_address_dialog)

        GlideHelper.setImage( applicationContext, dataAgent.gambar_toko!!, imgStoreDialog!! )

        tvNameStoreDialog.text = dataAgent.nama_toko
        tvNameDialog.text = dataAgent.nama_pemilik
        tvAddressDialog.text = dataAgent.alamat

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        imgStoreDialog.setOnClickListener {
            supportFragmentManager.beginTransaction().remove(mapFragment).commit()
            dialog.dismiss()
        }

        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(mMap: GoogleMap?) {
        val latLng = LatLng(agent.latitude!!.toDouble(), agent.longitude!!.toDouble())
        mMap?.addMarker(MarkerOptions().position(latLng).title( agent.nama_toko ))
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }
}