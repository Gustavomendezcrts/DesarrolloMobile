package cl.duoc.leoley.repository

import cl.duoc.leoley.data.RetrofitClient
import cl.duoc.leoley.model.Ley

class BuscadorRepository {

    // Pide a la API las leyes que coinciden con el texto (vacío devuelve todas)
    suspend fun buscar(texto: String): List<Ley> {
        return RetrofitClient.leyApi.buscarLeyes(texto.trim())
    }
}