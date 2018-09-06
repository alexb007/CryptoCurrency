package uz.diamondsolutions.mk_test.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import uz.diamondsolutions.mk_test.datasource.CurrenciesDataSourceFactory
import uz.diamondsolutions.mk_test.retrofit.ServiceGenerator
import uz.diamondsolutions.mk_test.retrofit.models.Crypto

class CurrenciesViewModel : ViewModel() {

    var currencies: LiveData<PagedList<Crypto>>

    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 15

    private val sourceFactory: CurrenciesDataSourceFactory

    init {
        sourceFactory = CurrenciesDataSourceFactory(compositeDisposable, ServiceGenerator.getService())
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build()
        currencies = LivePagedListBuilder<Long, Crypto>(sourceFactory, config).build()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun retry() {
        sourceFactory.currenciesDataSourceFactory.value!!.retry()
    }

    fun refresh() {
        sourceFactory.currenciesDataSourceFactory.value!!.invalidate()
    }

}