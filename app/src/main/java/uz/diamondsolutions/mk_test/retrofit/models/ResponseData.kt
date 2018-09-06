package uz.diamondsolutions.mk_test.retrofit.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import uz.diamondsolutions.mk_test.utils.ObservableTypeAdapterFactory

data class ResponseData(@JsonAdapter(ObservableTypeAdapterFactory::class) @Expose val data: Observable<Crypto>?, @Expose val metadata: Metadata?)

