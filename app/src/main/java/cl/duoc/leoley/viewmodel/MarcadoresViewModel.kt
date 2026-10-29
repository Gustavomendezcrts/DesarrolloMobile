package cl.duoc.leoley.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.leoley.model.Ley
import cl.duoc.leoley.repository.MarcadoresRepository
import kotlinx.coroutines.launch

class MarcadoresViewModel(private val repository: MarcadoresRepository) : ViewModel() {

    var marcadores by mutableStateOf(listOf<Ley>())
        private set

    var mostrarLista by mutableStateOf(false)
        private set

    // Se ejecuta al crear el ViewModel: trae lo que había guardado
    init {
        viewModelScope.launch {
            marcadores = repository.obtenerMarcadores()
        }
    }

    fun alternarLista() {
        mostrarLista = !mostrarLista
    }

    fun eliminarMarcador(ley: Ley) {
        viewModelScope.launch {
            repository.eliminarMarcador(ley)
            marcadores = repository.obtenerMarcadores()
        }
    }

    fun agregarMarcador(ley: Ley) {
        viewModelScope.launch {
            repository.agregarMarcador(ley)
            marcadores = repository.obtenerMarcadores()
        }
    }
}