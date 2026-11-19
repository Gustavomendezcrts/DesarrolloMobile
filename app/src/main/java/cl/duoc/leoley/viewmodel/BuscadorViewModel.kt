package cl.duoc.leoley.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.leoley.model.Ley
import cl.duoc.leoley.repository.BuscadorRepository
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BuscadorViewModel : ViewModel() {

    private val repository = BuscadorRepository()
    private var busquedaActual: Job? = null

    var texto by mutableStateOf("")
        private set

    var resultados by mutableStateOf(listOf<Ley>())
        private set

    var cargando by mutableStateOf(false)
        private set

    var error by mutableStateOf("")
        private set
    var leyesAbiertas by mutableStateOf(listOf<String>())
        private set

    var mensaje by mutableStateOf("")
        private set

    fun cambiarTexto(valor: String) {
        texto = valor
        mensaje = ""
        buscar(valor, esperar = true)
    }

    private fun buscar(valor: String, esperar: Boolean) {
        busquedaActual?.cancel()
        busquedaActual = viewModelScope.launch {
            if (esperar) {
                delay(400)
            }
            cargando = true
            error = ""
            try {
                resultados = repository.buscar(valor)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                resultados = listOf()
                error = "No se pudo conectar con el servidor"
            }
            cargando = false
        }
    }
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
    fun limpiar() {
        texto = ""
        resultados = listOf()
        leyesAbiertas = listOf()
        mensaje = ""
        buscar("", esperar = false)
    }
}