package cl.duoc.leoley.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    // Nombre del usuario que inició sesión
    var nombre by mutableStateOf("")
        private set

    fun guardarNombre(nuevoNombre: String) {
        nombre = nuevoNombre
    }
}