package cl.duoc.leoley.repository

import cl.duoc.leoley.data.MarcadorDao
import cl.duoc.leoley.model.Ley

class MarcadoresRepository(private val dao: MarcadorDao) {

    suspend fun obtenerMarcadores(): List<Ley> {
        return dao.obtenerTodos()
    }

    suspend fun agregarMarcador(ley: Ley) {
        dao.insertar(ley)
    }

    suspend fun eliminarMarcador(ley: Ley) {
        dao.eliminar(ley)
    }
}