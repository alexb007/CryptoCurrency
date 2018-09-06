package uz.diamondsolutions.mk_test.datasource

import android.arch.paging.ItemKeyedDataSource
import android.util.Log
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import uz.diamondsolutions.mk_test.retrofit.ServiceGenerator
import uz.diamondsolutions.mk_test.retrofit.models.Crypto
import uz.diamondsolutions.mk_test.retrofit.models.ResponseData

class CurrencyDataSource(
        private val service: ServiceGenerator,
        private val compositeDisposable: CompositeDisposable)
    : ItemKeyedDataSource<Long, Crypto>() {

    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Crypto>) {
        compositeDisposable.add(
                service.getCurrencies()
                        .flatMapObservable { it.data }
                        .toList()
                        .subscribe({ data ->
                            callback.onResult(data)
                        }) { throwable -> setRetry(Action { loadInitial(params, callback) })
                            Log.e("Exception", throwable.message)
                        }
        )
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Crypto>) {

    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Crypto>) {
    }


    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ }, { throwable -> Log.e("ERROR", throwable.message) }))
        }
    }

    override fun getKey(item: Crypto): Long {
        return item.id!!.toLong()
    }

    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }

}