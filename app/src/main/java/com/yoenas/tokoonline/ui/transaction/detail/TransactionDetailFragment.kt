package com.yoenas.tokoonline.ui.transaction.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.Constant
import com.yoenas.tokoonline.data.model.transaction.detail.DataDetail
import com.yoenas.tokoonline.data.model.transaction.detail.ResponseTransactionDetail

class TransactionDetailFragment : Fragment(), TransactionDetailContract.View {

    lateinit var detailAdapter: TransactionDetailAdapter
    lateinit var presenter: TransactionDetailPresenter

    lateinit var swipeDetailTransaction: SwipeRefreshLayout
    lateinit var tvInvoiceDetail: TextView
    lateinit var rvDetailTransaction: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transaction_detail, container, false)
        presenter = TransactionDetailPresenter(this)
        initListener(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar!!.title = "Transaksi"
        presenter.getTransactionByInvoice(Constant.INVOICE)
    }

    override fun initListener(view: View) {
        swipeDetailTransaction = view.findViewById(R.id.swipe_detail_transaction)
        tvInvoiceDetail = view.findViewById(R.id.tv_invoice_detail_transaction)
        rvDetailTransaction = view.findViewById(R.id.rv_detail_transaction)

        tvInvoiceDetail.text = Constant.INVOICE

        detailAdapter = TransactionDetailAdapter(context!!, arrayListOf())
        rvDetailTransaction.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = detailAdapter
        }

        swipeDetailTransaction.setOnRefreshListener {
            presenter.getTransactionByInvoice(Constant.INVOICE)
        }
    }

    override fun onLoadingDetail(loading: Boolean) {
        when(loading){
            true -> swipeDetailTransaction.isRefreshing = true
            false -> swipeDetailTransaction.isRefreshing = false
        }
    }

    override fun onResultDetail(responseTransactionDetail: ResponseTransactionDetail) {
        val dataDetail: List<DataDetail> = responseTransactionDetail.dataDetail
        detailAdapter.setData(dataDetail)
    }

}