package com.yoenas.tokoonline.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.database.PrefsManager
import com.yoenas.tokoonline.ui.agent.AgentActivity
import com.yoenas.tokoonline.ui.login.LoginActivity
import com.yoenas.tokoonline.ui.transaction.TransactionActivity
import com.yoenas.tokoonline.ui.user.UserActivity

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var presenter: MainPresenter
    private lateinit var prefsManager: PrefsManager

    private lateinit var btnMasuk: Button
    private lateinit var cvUser: CardView
    private lateinit var cvTransaction: CardView
    private lateinit var cvAgent: CardView
    private lateinit var tvUsername: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun onStart() {
        super.onStart()
        when (prefsManager.prefsIsLogin){
            true -> {
                tvUsername.text = prefsManager.prefsUsername
                tvUsername.visibility = View.VISIBLE
                cvUser.visibility = View.VISIBLE
                btnMasuk.visibility = View.GONE
            }
            false -> {
                tvUsername.text = ""
                tvUsername.visibility = View.GONE
                cvUser.visibility = View.GONE
                btnMasuk.visibility = View.VISIBLE
            }

        }
    }

    override fun initListener() {
        btnMasuk = findViewById(R.id.btn_masuk)
        cvUser = findViewById(R.id.cv_user)
        cvTransaction = findViewById(R.id.cv_transaction)
        cvAgent = findViewById(R.id.cv_agent)
        tvUsername = findViewById(R.id.tv_username_as)

        cvTransaction.setOnClickListener {
            if (prefsManager.prefsIsLogin)
                startActivity(Intent(this, TransactionActivity::class.java))
            else
                showMessage("Anda belum login")
        }
        cvAgent.setOnClickListener {
            if (prefsManager.prefsIsLogin)
                startActivity(Intent(this, AgentActivity::class.java))
            else
                showMessage("Anda belum login")
        }
        cvUser.setOnClickListener {
            startActivity(Intent(this, UserActivity::class.java))
        }

        btnMasuk.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}