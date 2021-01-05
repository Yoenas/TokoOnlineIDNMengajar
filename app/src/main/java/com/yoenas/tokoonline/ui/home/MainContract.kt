package com.yoenas.tokoonline.ui.home

interface MainContract {

    interface View {
        fun initListener()
        fun showMessage(message: String)
    }
}