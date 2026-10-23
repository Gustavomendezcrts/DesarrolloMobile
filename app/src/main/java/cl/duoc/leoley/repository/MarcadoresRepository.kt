package cl.duoc.leoley.repository

import cl.duoc.leoley.model.Ley

class MarcadoresRepository {

    private val leyes = mutableListOf<Ley>()

    fun obtenerMarcadores(): List<Ley> {
        return leyes.toList()
    }

    fun agregarMarcador(ley: Ley) {
        if (!leyes.contains(ley)) {
            leyes.add(ley)
        }
    }

    fun eliminarMarcador(ley: Ley) {
        leyes.remove(ley)
    }
}