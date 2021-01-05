package com.yoenas.tokoonline.ui.product

import com.yoenas.tokoonline.data.model.product.ResponseCategoryList
import com.yoenas.tokoonline.data.model.product.ResponseProductList

interface ProductContract {
    interface Presenter{
        fun getCategory()
        fun getProduct(kd_kategory: Long)
    }

    interface View{
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResultCategory(responseCategoryList: ResponseCategoryList)
        fun onResultProduct(responseProductList: ResponseProductList)
    }
}