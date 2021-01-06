package com.yoenas.tokoonline.ui.agent.create

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.lazday.poslaravel.util.GalleryHelper
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.Constant
import com.yoenas.tokoonline.data.model.agent.ResponseAgentUpdate
import com.yoenas.tokoonline.ui.agent.AgentMapsActivity
import com.yoenas.tokoonline.utils.FileUtils

class AgentCreateActivity : AppCompatActivity(), AgentCreateContract.View {

    private lateinit var edtLocation: EditText
    private lateinit var edtNameStore: EditText
    private lateinit var edtNameOwner: EditText
    private lateinit var edtAddress: EditText
    private lateinit var imgImageAgent: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnSubmit: Button

    private var uriImage: Uri? = null
    private var pickImage = 1

    private lateinit var presenter: AgentCreatePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent_create)

        presenter = AgentCreatePresenter(this)
    }

    override fun onStart() {
        super.onStart()
        if (Constant.LATITUDE.isNotEmpty()){
            edtLocation.setText("${Constant.LATITUDE}, ${Constant.LONGITUDE}")
        }
    }

    override fun initActivity() {
        supportActionBar?.title = "Agen Baru"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        edtLocation = findViewById(R.id.edt_location_agent)
        imgImageAgent = findViewById(R.id.img_image_agent)
        progressBar = findViewById(R.id.progress_agent)
        btnSubmit = findViewById(R.id.btn_submit_create_agent)
        edtNameStore = findViewById(R.id.edt_namestore_agent)
        edtNameOwner = findViewById(R.id.edt_name_owner_agent)
        edtAddress = findViewById(R.id.edt_address_agent)
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
                location.isNullOrEmpty() || uriImage == null ) {
                showMessage( "Lengkapi data dengan benar" )
            } else {
                presenter.insertAgent(
                    nameStore.toString(),
                    nameOwner.toString(),
                    address.toString(),
                    Constant.LATITUDE,
                    Constant.LONGITUDE,
                    FileUtils.getFile(this, uriImage)
                )
                showMessage("Toko $nameStore berhasil ditambahkan.")
                finish()
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

    override fun onResult(responseAgentUpdate: ResponseAgentUpdate) {
        showMessage(responseAgentUpdate.msg)
        finish()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uriImage = data!!.data
            imgImageAgent.setImageURI(uriImage)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.LATITUDE = ""
        Constant.LONGITUDE = ""
    }
}