package cl.duoc.leoley.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val URL_BASE = "https://x8ki-letl-twmt.n7.xano.io/api:hi5aJvi-/"

    val leyApi: LeyApiService = Retrofit.Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LeyApiService::class.java)
}