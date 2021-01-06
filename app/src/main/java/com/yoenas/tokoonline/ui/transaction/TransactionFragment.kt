package com.yoenas.tokoonline.ui.transaction

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yoenas.tokoonline.R
import com.yoenas.tokoonline.data.Constant
import com.yoenas.tokoonline.data.database.PrefsManager
import com.yoenas.tokoonline.data.model.transaction.DataTransaction
import com.yoenas.tokoonline.data.model.transaction.ResponseTransactionList
import com.yoenas.tokoonline.ui.cart.CartActivity
import com.yoenas.tokoonline.ui.transaction.detail.TransactionDetailFragment

class TransactionFragment : Fragment(), TransactionContract.View {

    lateinit var prefsManager: PrefsManager
    lateinit var transactionAdapter: TransactionAdapter
    lateinit var presenter: TransactionPresenter

    lateinit var swipeTransaction: SwipeRefreshLayout
    lateinit var rvTransaction: RecyclerView
    lateinit var fabTransaction: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transaction, container, false)
        prefsManager = PrefsManager(context!!)
        presenter = TransactionPresenter(this)
        initListener(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.title = "Transaksi"
        presenter.getTransactionByUsername(prefsManager.prefsUsername)
    }

    override fun initListener(view: View) {
        swipeTransaction = view.findViewById(R.id.swipe_transaction)
        rvTransaction = view.findViewById(R.id.rv_transaction)
        fabTransaction = view.findViewById(R.id.fab_transaction)
        transactionAdapter =
            TransactionAdapter(context!!, arrayListOf()) { dataTransaction ->
                onClickTransaction(dataTransaction.no_faktur!!)
            }
        rvTransaction.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
        }

        swipeTransaction.setOnRefreshListener {
            presenter.getTransactionByUsername(prefsManager.prefsUsername)
        }

        fabTransaction.setOnClickListener {
            context?.startActivity(Intent(context, CartActivity::class.java))
        }
    }

    override fun onLoadingTransaction(loading: Boolean) {
        when (loading) {
            true -> swipeTransaction.isRefreshing = true
            false -> swipeTransaction.isRefreshing = false
        }
    }

    override fun onResultTransaction(responseTransactionList: ResponseTransactionList) {
        val dataTransaction: List<DataTransaction> = responseTransactionList.dataTransaction
        transactionAdapter.setData(dataTransaction)
    }

    override fun onClickTransaction(invoice: String) {
        Constant.INVOICE = invoice
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.container_transaction, TransactionDetailFragment(), "TransactionDetail")
            .commit()
    }

}