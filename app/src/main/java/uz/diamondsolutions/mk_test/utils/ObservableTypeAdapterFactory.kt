package uz.diamondsolutions.mk_test.utils

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ObservableTypeAdapterFactory : TypeAdapterFactory {

    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        return if (Observable::class.java.isAssignableFrom(type.rawType)) {
            ObservableTypeAdapter<T>(type.type.getObservableParameterType(), gson) as TypeAdapter<T>
        } else null
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