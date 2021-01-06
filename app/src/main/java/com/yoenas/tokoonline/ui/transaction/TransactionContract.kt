package com.yoenas.tokoonline.ui.transaction

import com.yoenas.tokoonline.data.model.transaction.ResponseTransactionList

interface TransactionContract {

    interface Presenter{
        fun getTransactionByUsername(username: String)
    }

    interface View{
        fun initListener(view: android.view.View)
        fun onLoadingTransaction(loading: Boolean)
        fun onResultTransaction(responseTransactionList: ResponseTransactionList)
        fun onClickTransaction(invoice: String)
    }
}