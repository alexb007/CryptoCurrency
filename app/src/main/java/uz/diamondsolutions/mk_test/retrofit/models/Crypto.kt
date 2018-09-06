package uz.diamondsolutions.mk_test.retrofit.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Crypto (

    @Expose
    var id: Int? = null,
    @Expose
    var name: String? = null,
    @Expose
    var symbol: String? = null,
    @Expose
    @SerializedName("website_slug")
    var websiteSlug: String? = null

)