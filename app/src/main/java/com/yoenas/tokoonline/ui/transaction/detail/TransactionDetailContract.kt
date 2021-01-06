package com.yoenas.tokoonline.ui.transaction.detail

import com.yoenas.tokoonline.data.model.transaction.detail.ResponseTransactionDetail

interface TransactionDetailContract {
    interface Presenter {
        fun getTransactionByInvoice(invoice: String)
    }

    interface View {
        fun initListener(view: android.view.View)
        fun onLoadingDetail(loading: Boolean)
        fun onResultDetail(responseTransactionDetail: ResponseTransactionDetail)
    }
}