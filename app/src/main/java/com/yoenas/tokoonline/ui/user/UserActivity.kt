package com.yoenas.tokoonline.ui.user

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.database.PrefsManager
import kotlin.math.abs

class UserActivity : AppCompatActivity(), UserContract.View {

    private lateinit var prefsManager: PrefsManager
    private lateinit var presenter: UserPresenter

    private lateinit var btnBack: Button
    private lateinit var btnLogout: Button
    private lateinit var tvUsername: TextView
    private lateinit var tvName: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvGender: TextView
    private lateinit var containerHeadTitle: ConstraintLayout
    private lateinit var appBarUser: AppBarLayout
    private lateinit var collapsingUser: CollapsingToolbarLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        prefsManager = PrefsManager(this)
        presenter = UserPresenter(this)
        presenter.doLogin(prefsManager)
    }

    override fun initActivity() {
        supportActionBar?.hide()

        btnBack = findViewById(R.id.btn_back)
        btnLogout = findViewById(R.id.btn_logout)

        tvUsername = findViewById(R.id.tv_username)
        tvName = findViewById(R.id.tv_name_user)
        tvAddress = findViewById(R.id.tv_alamat_user)
        tvGender = findViewById(R.id.tv_gender_user)

        containerHeadTitle = findViewById(R.id.container_head_title)
        appBarUser = findViewById(R.id.app_bar_user)
        collapsingUser = findViewById(R.id.collapsing_user)
        toolbar = findViewById(R.id.toolbar_user)

        initToolbar()

    }

    override fun initListener() {
        btnBack.setOnClickListener {
            finish()
        }
        btnLogout.setOnClickListener {
            presenter.doLogout(prefsManager)
        }
    }

    override fun onResultLogin(prefsManager: PrefsManager) {
        tvUsername.text = prefsManager.prefsUsername
        tvName.text = prefsManager.prefsNamaPegawai
        tvAddress.text = prefsManager.prefsAlamat
        tvGender.text = prefsManager.prefsJk
    }

    override fun onResultLogout() {
        finish()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun initToolbar(){
        setSupportActionBar(toolbar)

        var isShow = true
        var scrollRange = -1
        appBarUser.addOnOffsetChangedListener(
            AppBarLayout
            .OnOffsetChangedListener { appBarLayout, verticalOffset ->

                // fade out content heading when scroll up
                containerHeadTitle.translationY =
                    -verticalOffset.toFloat() // Un-slide the image or container from views

                val percent =
                    (abs(verticalOffset)).toFloat() / appBarLayout?.totalScrollRange!! // 0F to 1F

                containerHeadTitle.alpha = 1F - percent

                containerHeadTitle.scaleY = (1F - percent) + percent / 1.199F
                containerHeadTitle.scaleX = (1F - percent) + percent / 1.199F

                // show text toolbar
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingUser.title = "Profile Pengguna"

                    isShow = true
                } else if (isShow) {
                    collapsingUser.title = " "
                    isShow = false
                }
            })
    }
}