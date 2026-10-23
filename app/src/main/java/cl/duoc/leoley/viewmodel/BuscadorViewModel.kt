package cl.duoc.leoley.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cl.duoc.leoley.repository.BuscadorRepository

class BuscadorViewModel : ViewModel() {

    private val repository = BuscadorRepository()

    var texto by mutableStateOf("")
        private set

    var resultados by mutableStateOf(repository.buscar(""))
        private set

    // Números de las leyes que están desplegadas (puede haber varias)
    var leyesAbiertas by mutableStateOf(listOf<String>())
        private set

    var mensaje by mutableStateOf("")
        private set

    fun cambiarTexto(valor: String) {
        texto = valor
        resultados = repository.buscar(valor)
        mensaje = ""
    }

    // Si la ley está abierta la cierra, y si está cerrada la abre
    fun alternarLey(numero: String) {
        leyesAbiertas = if (leyesAbiertas.contains(numero)) {
            leyesAbiertas - numero
        } else {
            leyesAbiertas + numero
        }
    }

    fun ponerMensaje(valor: String) {
        mensaje = valor
    }

    // Deja la pantalla como nueva
    fun limpiar() {
        texto = ""
        resultados = repository.buscar("")
        leyesAbiertas = listOf()
        mensaje = ""
    }
}