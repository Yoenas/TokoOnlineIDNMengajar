package com.yoenas.tokoonline.ui.agent.update

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.lazday.poslaravel.util.GalleryHelper
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.Constant
import com.yoenas.tokoonline.data.model.agent.ResponseAgentDetail
import com.yoenas.tokoonline.data.model.agent.ResponseAgentUpdate
import com.yoenas.tokoonline.ui.agent.AgentMapsActivity
import com.yoenas.tokoonline.utils.FileUtils
import com.yoenas.tokoonline.utils.GlideHelper

class AgentUpdateActivity : AppCompatActivity(), AgentUpdateContract.View {

    private lateinit var edtLocation: EditText
    private lateinit var edtNameStore: EditText
    private lateinit var edtNameOwner: EditText
    private lateinit var edtAddress: EditText
    private lateinit var imgImageAgent: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnSubmit: Button

    private var uriImage: Uri? = null
    private var pickImage = 1

    lateinit var presenter: AgentUpdatePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent_create)

        edtLocation = findViewById(R.id.edt_location_agent)
        imgImageAgent = findViewById(R.id.img_image_agent)
        progressBar = findViewById(R.id.progress_agent)
        btnSubmit = findViewById(R.id.btn_submit_create_agent)
        edtNameStore = findViewById(R.id.edt_namestore_agent)
        edtNameOwner = findViewById(R.id.edt_name_owner_agent)
        edtAddress = findViewById(R.id.edt_address_agent)

        presenter = AgentUpdatePresenter(this)
        presenter.getDetail(Constant.AGENT_ID)
    }

    override fun onStart() {
        super.onStart()
        if (Constant.LATITUDE.isNotEmpty()) {
            edtLocation?.setText( "${Constant.LATITUDE}, ${Constant.LONGITUDE}" )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.LATITUDE = ""
        Constant.LONGITUDE = ""
    }

    override fun initActivity() {

    }

    override fun initListener() {
        edtLocation.setOnClickListener {
            startActivity(Intent(this, AgentMapsActivity::class.java))
        }
        imgImageAgent.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage))
                GalleryHelper.openGallery(this)
        }
        btnSubmit.setOnClickListener {
            val nameStore   = edtNameStore.text
            val nameOwner   = edtNameOwner.text
            val address     = edtAddress.text
            val location    = edtLocation.text

            if ( nameStore.isNullOrEmpty() || nameOwner.isNullOrEmpty() || address.isNullOrEmpty() ||
                location.isNullOrEmpty() ) {
                showMessage( "Lengkapi data dengan benar" )
            } else {
                presenter.updateAgent(
                    Constant.AGENT_ID, nameStore.toString(), nameOwner.toString(), address.toString(), Constant.LATITUDE,
                    Constant.LONGITUDE, FileUtils.getFile(this, uriImage)
                )
            }

        }
    }

    override fun onLoading(loading: Boolean) {
        when (loading){
            true -> {
                progressBar.visibility = View.VISIBLE
                btnSubmit.visibility = View.GONE
            }
            false -> {
                progressBar.visibility = View.GONE
                btnSubmit.visibility = View.VISIBLE
            }
        }
    }

    override fun onResultDetail(responseAgentDetail: ResponseAgentDetail) {

        val agent = responseAgentDetail.dataAgent
        edtNameStore.setText(agent.nama_toko)
        edtNameOwner.setText(agent.nama_pemilik)
        edtAddress.setText(agent.alamat)
        edtLocation.setText("${agent.latitude}, ${agent.longitude}")

        Constant.LATITUDE = agent.latitude!!
        Constant.LONGITUDE = agent.longitude!!

        GlideHelper.setImage(this, agent.gambar_toko!!, imgImageAgent)
    }

    override fun onResultUpdate(responseAgentUpdate: ResponseAgentUpdate) {
        showMessage(responseAgentUpdate.msg)
        finish()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK){
            uriImage = data!!.data
            imgImageAgent.setImageURI(uriImage)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}