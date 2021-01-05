package com.yoenas.tokoonline.ui.login

import com.yoenas.tokoonline.data.database.PrefsManager
import com.yoenas.tokoonline.data.model.login.DataLogin
import com.yoenas.tokoonline.data.model.login.ResponseLogin
import com.yoenas.tokoonline.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(val view: LoginContract.View): LoginContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }
    override fun doLogin(username: String, password: String) {
        view.onLoading(true)
        ApiService.endpoint.loginUser(username, password)
            .enqueue(object : Callback<ResponseLogin> {
                override fun onResponse(
                    call: Call<ResponseLogin>,
                    response: Response<ResponseLogin>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful){
                        val responseLogin: ResponseLogin? = response.body()
                        view.showMessage(responseLogin!!.msg)

                        if (responseLogin.status){
                            view.onResult(responseLogin)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    view.onLoading(false)
                }
            })
    }

    override fun setPrefs(prefsManager: PrefsManager, dataLogin: DataLogin) {
        prefsManager.prefsIsLogin = true
        prefsManager.prefsUsername = dataLogin.username!!
    }

}