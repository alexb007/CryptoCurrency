package uz.diamondsolutions.mk_test.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import uz.diamondsolutions.mk_test.retrofit.ServiceGenerator
import uz.diamondsolutions.mk_test.retrofit.models.Crypto

class CurrenciesDataSourceFactory(private val compositeDisposable: CompositeDisposable,
                             private val serviceGenerator: ServiceGenerator)
    : DataSource.Factory<Long, Crypto>() {

    val currenciesDataSourceFactory = MutableLiveData<CurrencyDataSource>()

    override fun create(): DataSource<Long, Crypto> {
        val usersDataSource = CurrencyDataSource(serviceGenerator, compositeDisposable)
        currenciesDataSourceFactory.postValue(usersDataSource)
        return usersDataSource
    }
}