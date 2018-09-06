package uz.diamondsolutions.mk_test.utils

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import io.reactivex.Observable
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


class ObservableTypeAdapter<T>(private val elementType: Type, private val gson: Gson) : TypeAdapter<Observable<T>>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter, observable: Observable<T>) {
        out.beginArray()
        observable.blockingForEach { gson.toJson(it, elementType, out) }
        out.endArray()
    }

    override fun read(reader: JsonReader): Observable<T> {
        return JsonReaderObservable(elementType, gson, reader)
    }

}

private fun Type.getObservableParameterType(): Type = getTParameterType(Observable::class.java)

private fun Type.getTParameterType(expectedParameterizedType: Type): Type {
    if (expectedParameterizedType == this) {
        return expectedParameterizedType
    }
    if (this is ParameterizedType) {
        if (expectedParameterizedType == rawType) {
            val actualTypeArguments = actualTypeArguments
            if (actualTypeArguments.size == 1) {
                return actualTypeArguments[0]
            }
        }
    }
    throw IllegalArgumentException(toString())
}