package cl.duoc.leoley.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val URL_LEYES = "https://x8ki-letl-twmt.n7.xano.io/api:hi5aJvi-/"
    private const val URL_AUTH = "https://x8ki-letl-twmt.n7.xano.io/api:pttr1rbI/"
    private fun crearRetrofit(urlBase: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val leyApi: LeyApiService = crearRetrofit(URL_LEYES).create(LeyApiService::class.java)
    val authApi: AuthApiService = crearRetrofit(URL_AUTH).create(AuthApiService::class.java)
}