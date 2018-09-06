package uz.diamondsolutions.mk_test.retrofit.models

import com.google.gson.annotations.Expose
import java.sql.Timestamp

data class Metadata(@Expose val timestamp: Long?, @Expose val numCryptocurrencies: Long?)