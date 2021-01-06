package com.yoenas.tokoonline.ui.cart.add

import com.yoenas.tokoonline.data.model.cart.ResponseCartUpdate
import com.yoenas.tokoonline.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartAddPresenter(val view : CartAddContract.View): CartAddContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }
    
    override fun addCart(username: String, kdProduk: Long, jumlah: Long) {
        view.onLoading(true)
        ApiService.endpoint.addCart(username, kdProduk, jumlah)
            .enqueue(object : Callback<ResponseCartUpdate> {
                override fun onResponse(
                    call: Call<ResponseCartUpdate>,
                    response: Response<ResponseCartUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful){
                        val responseCartAdd: ResponseCartUpdate? = response.body()
                        view.showMessage(responseCartAdd!!.msg)
                        view.onResult(responseCartAdd)
                    }
                }

                override fun onFailure(call: Call<ResponseCartUpdate>, t: Throwable) {
                    view.onLoading(false)
                }

            })
    }
}