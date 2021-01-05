package com.yoenas.tokoonline.ui.cart.add

import com.yoenas.tokoonline.data.model.cart.ResponseCartUpdate

interface CartAddContract {

    interface Presenter {
        fun addCart(username: String, kdProduk: Long, jumlah: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseCartAdd: ResponseCartUpdate)
        fun showMessage(message: String)
    }
}