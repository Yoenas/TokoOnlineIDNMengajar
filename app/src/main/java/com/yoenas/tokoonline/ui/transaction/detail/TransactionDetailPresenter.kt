package com.yoenas.tokoonline.ui.transaction.detail

import com.yoenas.tokoonline.data.model.transaction.detail.ResponseTransactionDetail
import com.yoenas.tokoonline.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionDetailPresenter(val view: TransactionDetailContract.View) :
    TransactionDetailContract.Presenter {

    override fun getTransactionByInvoice(invoice: String) {
        view.onLoadingDetail(true)
        ApiService.endpoint.getTransactionByInvoice(invoice)
            .enqueue(object : Callback<ResponseTransactionDetail> {
                override fun onResponse(
                    call: Call<ResponseTransactionDetail>,
                    response: Response<ResponseTransactionDetail>
                ) {
                    view.onLoadingDetail(false)
                    if (response.isSuccessful) {
                        val responseTransactionDetail: ResponseTransactionDetail? = response.body()
                        view.onResultDetail(responseTransactionDetail!!)
                    }
                }

                override fun onFailure(call: Call<ResponseTransactionDetail>, t: Throwable) {
                    view.onLoadingDetail(false)
                }
            })
    }


}