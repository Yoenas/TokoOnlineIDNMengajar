package com.yoenas.tokoonline.ui.cart

import com.yoenas.tokoonline.data.model.cart.ResponseCartList
import com.yoenas.tokoonline.data.model.cart.ResponseCartUpdate
import com.yoenas.tokoonline.data.model.cart.ResponseCheckout

interface CartContract {
    interface Presenter {
        fun getCart(username: String)

        fun deleteItemCart(kd_keranjang: Long)
        fun deleteCart(username: String)

        fun checkOut(username: String, kd_agent: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoadingCart(loading: Boolean)
        fun showMessage(message: String)
        fun onResultCart(responseCartList: ResponseCartList)

        fun showDialog()
        fun onResultDelete(responseCartUpdate: ResponseCartUpdate)

        fun onLoadingCheckout(loading: Boolean)
        fun onResultCheckout(responseCheckOut: ResponseCheckout)
    }
}