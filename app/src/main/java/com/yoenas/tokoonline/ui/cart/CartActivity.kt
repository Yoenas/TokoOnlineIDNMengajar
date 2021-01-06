package com.yoenas.tokoonline.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.Constant
import com.yoenas.tokoonline.data.database.PrefsManager
import com.yoenas.tokoonline.data.model.cart.DataCart
import com.yoenas.tokoonline.data.model.cart.ResponseCartList
import com.yoenas.tokoonline.data.model.cart.ResponseCartUpdate
import com.yoenas.tokoonline.data.model.cart.ResponseCheckout
import com.yoenas.tokoonline.ui.agent.search.AgentSearchActivity
import com.yoenas.tokoonline.ui.cart.add.CartAddActivity

class CartActivity : AppCompatActivity(), CartContract.View, View.OnClickListener{

    private lateinit var prefsManager: PrefsManager
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartPresenter: CartPresenter

    private lateinit var btnReset: Button
    private lateinit var btnAdd: Button
    private lateinit var btnCheckout: Button
    private lateinit var edtAgent: EditText
    private lateinit var rvCart: RecyclerView
    private lateinit var swipeCart: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        prefsManager = PrefsManager(this)
        cartPresenter = CartPresenter(this)
        cartPresenter.getCart(prefsManager.prefsUsername)
    }

    override fun onStart() {
        super.onStart()
        if (Constant.IS_CHANGE) {
            Constant.IS_CHANGE = false
            cartPresenter.getCart(prefsManager.prefsUsername)
            edtAgent.setText(Constant.AGENT_NAME)
        }
    }

    override fun initActivity() {
        supportActionBar?.title = "Keranjang"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnReset = findViewById(R.id.btn_reset)
        btnAdd = findViewById(R.id.btn_add_cart)
        btnCheckout = findViewById(R.id.btn_checkout)
        edtAgent = findViewById(R.id.edt_agent_cart)
        rvCart = findViewById(R.id.rv_cart)
        swipeCart = findViewById(R.id.swipe_cart)

        cartAdapter = CartAdapter(this, arrayListOf()) { dataCart: DataCart, position: Int ->
            cartPresenter.deleteItemCart(dataCart.kd_keranjang!!)
        }
    }

    override fun initListener() {
        btnReset.visibility = View.GONE
        edtAgent.visibility = View.GONE

        rvCart.apply { layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }

        btnAdd.setOnClickListener(this)
        btnReset.setOnClickListener (this)
        edtAgent.setOnClickListener(this)
        btnCheckout.setOnClickListener (this)
    }
    override fun onClick(view: View?) {
        when(view!!.id) {
            btnAdd.id   -> {
                startActivity(Intent(this, CartAddActivity::class.java))
            }
            btnReset.id -> {
                showDialog()
            }
            edtAgent.id -> {
                startActivity(Intent(this, AgentSearchActivity::class.java))
            }
            btnCheckout.id -> {
                if (cartAdapter.cart.isNullOrEmpty()) {
                    showMessage("Keranjang kosong")
                } else {
                    if (edtAgent.text.isNullOrEmpty()) {
                        edtAgent.error = "Tidak boleh kosong"
                    } else {
                        cartPresenter.checkOut( prefsManager.prefsUsername, Constant.AGENT_ID )
                    }
                }
            }
        }
    }

    override fun onLoadingCart(loading: Boolean) {
        when (loading){
            true -> swipeCart.isRefreshing = true
            false -> swipeCart.isRefreshing = false
        }
    }

    override fun onResultCart(responseCartList: ResponseCartList) {
        val dataCart: List<DataCart> = responseCartList.dataCart
        if (dataCart.isNullOrEmpty()) {
            btnReset.visibility = View.GONE
            edtAgent.visibility = View.GONE
        } else {
            cartAdapter.setData(dataCart)
            btnReset.visibility = View.VISIBLE
            edtAgent.visibility = View.VISIBLE
        }
    }

    override fun showDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Hapus semua item dalam Keranjang?")

        dialog.setPositiveButton("Hapus") { dialog, which ->
            cartPresenter.deleteCart( prefsManager.prefsUsername )
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onResultDelete(responseCartUpdate: ResponseCartUpdate) {
        cartPresenter.getCart(prefsManager.prefsUsername)
        cartAdapter.removeAll()
    }

    override fun onLoadingCheckout(loading: Boolean) {
        when (loading) {
            true -> {
                btnCheckout.isEnabled = false
                btnCheckout.setText("Memuat...")
            }
            false -> {
                btnCheckout.isEnabled = true
                btnCheckout.setText("Checkout")
            }
        }
    }

    override fun onResultCheckout(responseCheckOut: ResponseCheckout) {
        cartPresenter.getCart(prefsManager.prefsUsername)
        cartAdapter.removeAll()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.AGENT_NAME = ""
    }
}