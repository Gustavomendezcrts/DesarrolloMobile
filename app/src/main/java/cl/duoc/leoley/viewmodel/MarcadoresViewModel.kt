package cl.duoc.leoley.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cl.duoc.leoley.model.Ley
import cl.duoc.leoley.repository.MarcadoresRepository

class MarcadoresViewModel : ViewModel() {

    private val repository = MarcadoresRepository()

    var marcadores by mutableStateOf(repository.obtenerMarcadores())
        private set

    // Controla si se muestra la lista o no
    var mostrarLista by mutableStateOf(false)
        private set

    fun alternarLista() {
        mostrarLista = !mostrarLista
    }

    fun eliminarMarcador(ley: Ley) {
        repository.eliminarMarcador(ley)
        marcadores = repository.obtenerMarcadores()
    }

    fun agregarMarcador(ley: Ley) {
        repository.agregarMarcador(ley)
        marcadores = repository.obtenerMarcadores()
    }
}