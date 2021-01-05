package com.yoenas.tokoonline.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.database.PrefsManager
import com.yoenas.tokoonline.data.model.login.ResponseLogin

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var presenter: LoginPresenter
    private lateinit var prefsManager: PrefsManager

    private lateinit var progressBar: ProgressBar
    private lateinit var btnLogin: Button
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressBar = findViewById(R.id.progress_login)
        btnLogin = findViewById(R.id.btn_login)
        edtUsername = findViewById(R.id.edt_username)
        edtPassword = findViewById(R.id.edt_password)

        onLoading(false)
        presenter = LoginPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun initActivity() {
        supportActionBar?.title = "MASUK"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun initListener() {
        btnLogin.setOnClickListener {
            presenter.doLogin(edtUsername.text.toString(), edtPassword.text.toString())
        }
    }

    override fun onResult(responseLogin: ResponseLogin) {
        presenter.setPrefs(prefsManager, responseLogin.pegawai!!)
        finish()
    }

    override fun onLoading(loading: Boolean){
        when(loading) {
            true -> {
                progressBar.visibility = View.VISIBLE
                btnLogin.visibility = View.GONE
            }

            false -> {
                progressBar.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
            }
        }
    }

    override fun showMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}