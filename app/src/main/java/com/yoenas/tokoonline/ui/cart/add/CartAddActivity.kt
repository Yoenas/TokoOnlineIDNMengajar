package com.yoenas.tokoonline.ui.cart.add

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.Constant
import com.yoenas.tokoonline.data.database.PrefsManager
import com.yoenas.tokoonline.data.model.cart.ResponseCartUpdate
import com.yoenas.tokoonline.ui.product.ProductActivity

class CartAddActivity : AppCompatActivity(), CartAddContract.View {

    private lateinit var cartAddPresenter: CartAddPresenter
    private lateinit var prefsManager: PrefsManager

    private lateinit var edtAddProduct: EditText
    private lateinit var tvQuantity: TextView
    private lateinit var npQuantity: NumberPicker
    private lateinit var btnSubmit: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_add)

        prefsManager = PrefsManager(this)
        cartAddPresenter = CartAddPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        if (Constant.IS_CHANGE) {
            Constant.IS_CHANGE = false
            edtAddProduct.setText(Constant.PRODUCT_NAME)
            tvQuantity.visibility = View.VISIBLE
            npQuantity.visibility = View.VISIBLE
        }
    }

    override fun initActivity() {
        supportActionBar!!.title = "Tambah Produk"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        edtAddProduct = findViewById(R.id.edt_add_product)
        tvQuantity = findViewById(R.id.tv_quantity)
        npQuantity = findViewById(R.id.np_quantity)
        btnSubmit = findViewById(R.id.btn_submit_add_cart)
        progressBar = findViewById(R.id.progress_cart)

        tvQuantity.visibility = View.INVISIBLE
        npQuantity.visibility = View.INVISIBLE
    }

    override fun initListener() {
        edtAddProduct.setOnClickListener {
            startActivity(Intent(this, ProductActivity::class.java))
        }

        npQuantity.minValue = 1
        npQuantity.maxValue = 25
        npQuantity.wrapSelectorWheel = true
        npQuantity.setOnValueChangedListener { _, i, i2 ->
            Constant.PRODUCT_QTY = i2.toLong()
            Log.d("CartAddActivity", "qty: i $i i2 $i2")
        }

        btnSubmit.setOnClickListener {
            if (Constant.PRODUCT_ID > 0) {
                Constant.PRODUCT_QTY = if (Constant.PRODUCT_QTY > 0) Constant.PRODUCT_QTY else 1
                cartAddPresenter.addCart(
                    prefsManager.prefsUsername, Constant.PRODUCT_ID, Constant.PRODUCT_QTY
                )
            } else {
                edtAddProduct.error = "Tidak boleh kosong"
            }
        }
    }

    override fun onLoading(loading: Boolean) {
        when(loading) {
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

    override fun onResult(responseCartAdd: ResponseCartUpdate) {
        if (responseCartAdd.status) {
            Constant.IS_CHANGE = true
            finish()
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.PRODUCT_ID = 0
        Constant.PRODUCT_NAME = ""
        Constant.PRODUCT_QTY = 0
    }
}