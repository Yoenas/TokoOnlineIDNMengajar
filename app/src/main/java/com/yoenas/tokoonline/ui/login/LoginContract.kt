package com.yoenas.tokoonline.ui.login

import com.yoenas.tokoonline.data.database.PrefsManager
import com.yoenas.tokoonline.data.model.login.DataLogin
import com.yoenas.tokoonline.data.model.login.ResponseLogin

interface LoginContract {

    interface Presenter{
        fun doLogin(username: String, password: String)
        fun setPrefs(prefsManager: PrefsManager, dataLogin: DataLogin)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseLogin: ResponseLogin)
        fun showMessage(message: String)
    }
}