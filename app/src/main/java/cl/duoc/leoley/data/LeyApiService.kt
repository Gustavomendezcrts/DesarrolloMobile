package cl.duoc.leoley.data

import cl.duoc.leoley.model.Ley
import retrofit2.http.GET
import retrofit2.http.Query

interface LeyApiService {

    @GET("leyes")
    suspend fun buscarLeyes(@Query("busqueda") busqueda: String): List<Ley>
}