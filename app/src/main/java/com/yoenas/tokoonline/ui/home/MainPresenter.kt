package com.yoenas.tokoonline.ui.home

class MainPresenter (val view : MainContract.View) {

    init {
        view.initListener()
    }
}