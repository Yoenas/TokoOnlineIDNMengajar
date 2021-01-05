package com.yoenas.tokoonline.ui.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.model.product.DataCategory
import com.yoenas.tokoonline.data.model.product.DataProduct
import com.yoenas.tokoonline.data.model.product.ResponseCategoryList
import com.yoenas.tokoonline.data.model.product.ResponseProductList

class ProductActivity : AppCompatActivity(), ProductContract.View {

    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var presenter: ProductPresenter
    private var kdKateogri: Long = 0

    private lateinit var imgCloseProduct: ImageView
    private lateinit var swipeProduct: SwipeRefreshLayout
    private lateinit var tvCategoryProduct: TextView
    private lateinit var rvCategory: RecyclerView
    private lateinit var rvProduct: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        imgCloseProduct = findViewById(R.id.img_close_product)
        swipeProduct = findViewById(R.id.swipe_product)
        tvCategoryProduct = findViewById(R.id.tv_category_product)
        rvCategory = findViewById(R.id.rv_category)
        rvProduct = findViewById(R.id.rv_product)

        productAdapter = ProductAdapter(this, arrayListOf()){
            dataProduct: DataProduct, position: Int ->
        }

        presenter = ProductPresenter(this)
        presenter.getCategory()
    }

    override fun initActivity() {
        supportActionBar?.hide()
        categoryAdapter = CategoryAdapter(this, arrayListOf()){
                dataCategory: DataCategory, position: Int ->
            kdKateogri = dataCategory.kd_kategori!!
            presenter.getProduct( dataCategory.kd_kategori )
        }
    }

    override fun initListener() {
        rvCategory.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = categoryAdapter
        }

        rvProduct.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = productAdapter
        }

        swipeProduct.setOnRefreshListener {
            when (View.VISIBLE) {
                rvCategory.visibility -> presenter.getCategory()
                rvProduct.visibility -> presenter.getProduct( kdKateogri )
            }
        }

        imgCloseProduct.setOnClickListener {
            when (View.VISIBLE){
                rvCategory.visibility -> finish()
                rvProduct.visibility -> {
                    rvProduct.visibility = View.GONE
                    rvCategory.visibility = View.VISIBLE
                    tvCategoryProduct.text = "Pilih Kategori"
                }
            }
        }
    }

    override fun onLoading(loading: Boolean) {
        when (loading){
            true -> {
                swipeProduct.isRefreshing = true
                rvCategory.visibility = View.GONE
                rvProduct.visibility = View.GONE
            }
            false -> swipeProduct.isRefreshing = false
        }
    }

    override fun onResultCategory(responseCategoryList: ResponseCategoryList) {
        rvCategory.visibility = View.VISIBLE
        val dataCategory: List<DataCategory> = responseCategoryList.dataCategory
        categoryAdapter.setData(dataCategory)
        tvCategoryProduct.text = getString(R.string.choose_category)
    }

    override fun onResultProduct(responseProductList: ResponseProductList) {
        rvProduct.visibility = View.VISIBLE
        val dataProduct: List<DataProduct> = responseProductList.dataProduct
        productAdapter.setData(dataProduct)
        tvCategoryProduct.text = dataProduct[0].kategori
    }

    override fun onBackPressed() {
        super.onBackPressed()
        when (View.VISIBLE) {
            rvCategory.visibility -> finish()
            rvProduct.visibility -> {
                rvProduct.visibility = View.GONE
                rvCategory.visibility = View.VISIBLE
                tvCategoryProduct.text = getString(R.string.choose_category)
            }
        }
    }
}