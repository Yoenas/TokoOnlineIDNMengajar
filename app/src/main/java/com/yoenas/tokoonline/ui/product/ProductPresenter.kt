package com.yoenas.tokoonline.ui.product

import com.yoenas.tokoonline.data.model.product.ResponseCategoryList
import com.yoenas.tokoonline.data.model.product.ResponseProductList
import com.yoenas.tokoonline.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductPresenter (val view : ProductContract.View): ProductContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
    }

    override fun getCategory() {
        view.onLoading(true)
        ApiService.endpoint.getCategory()
            .enqueue(object : Callback<ResponseCategoryList> {
                override fun onResponse(
                    call: Call<ResponseCategoryList>,
                    response: Response<ResponseCategoryList>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful){
                        val responseCategoryList: ResponseCategoryList? = response.body()
                        view.onResultCategory(responseCategoryList!!)
                    }
                }

                override fun onFailure(call: Call<ResponseCategoryList>, t: Throwable) {
                    view.onLoading(false)
                }
            })
    }

    override fun getProduct(kd_kategory: Long) {
        view.onLoading(true)
        ApiService.endpoint.getProductByCategory(kd_kategory)
            .enqueue(object : Callback<ResponseProductList> {
                override fun onResponse(
                    call: Call<ResponseProductList>,
                    response: Response<ResponseProductList>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful){
                        val responseProductList: ResponseProductList? = response.body()
                        view.onResultProduct(responseProductList!!)
                    }
                }

                override fun onFailure(call: Call<ResponseProductList>, t: Throwable) {
                    view.onLoading(false)
                }
            })
    }
}