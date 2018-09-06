package uz.diamondsolutions.mk_test

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*
import uz.diamondsolutions.mk_test.adapter.CurrenciesAdapter
import uz.diamondsolutions.mk_test.retrofit.models.Crypto
import uz.diamondsolutions.mk_test.viewmodel.CurrenciesViewModel

class MainActivityFragment : Fragment() {

    private lateinit var mViewModel: CurrenciesViewModel

    private lateinit var mAdapter: CurrenciesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(CurrenciesViewModel::class.java)
        initData()
    }

    private fun initData() {
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mAdapter = CurrenciesAdapter {
            mViewModel.retry()
        }
        currenciesRecyclerView.layoutManager = linearLayoutManager
        currenciesRecyclerView.adapter = mAdapter
        mViewModel.currencies.observe(this, Observer<PagedList<Crypto>> { mAdapter.submitList(it) })
    }

}
