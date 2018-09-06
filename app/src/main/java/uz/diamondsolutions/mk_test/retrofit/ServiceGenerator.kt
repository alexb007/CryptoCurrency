package uz.diamondsolutions.mk_test.retrofit

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import uz.diamondsolutions.mk_test.retrofit.models.ResponseData


interface ServiceGenerator {

    @GET("listings")
    fun getCurrencies(): Single<ResponseData>

    companion object {
        fun getService(): ServiceGenerator {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.coinmarketcap.com/v2/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(ServiceGenerator::class.java)
        }
    }
}