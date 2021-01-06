package com.yoenas.tokoonline.ui.transaction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yoenas.tokoonline.R

class TransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initActivity()
    }

    private fun initActivity() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container_transaction, TransactionFragment(), "trans_fragment")
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        if (supportFragmentManager.findFragmentByTag("trans_fragment") == null )
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_transaction, TransactionFragment(), "trans_fragment")
                .commit()
        else
            finish()

        return super.onSupportNavigateUp()
    }
}