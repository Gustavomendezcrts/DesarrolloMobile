package cl.duoc.leoley.repository

class MarcadoresRepository {

    private val leyes = mutableListOf("Ley 19.628", "Ley 20.000", "Ley 21.719")

    fun obtenerMarcadores(): List<String> {
        return leyes.toList()
    }

    fun agregarMarcador(ley: String) {
        if (!leyes.contains(ley)) {
            leyes.add(ley)
        }
    }

    fun eliminarMarcador(ley: String) {
        leyes.remove(ley)
    }
}