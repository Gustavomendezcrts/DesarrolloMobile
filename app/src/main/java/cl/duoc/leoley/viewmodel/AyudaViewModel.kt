package cl.duoc.leoley.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AyudaViewModel : ViewModel() {

    var nombre by mutableStateOf("")
        private set

    var correo by mutableStateOf("")
        private set

    var mensaje by mutableStateOf("")
        private set

    var error by mutableStateOf("")
        private set

    fun cambiarNombre(valor: String) {
        nombre = valor
    }

    fun cambiarCorreo(valor: String) {
        correo = valor
    }

    fun cambiarMensaje(valor: String) {
        mensaje = valor
    }

    fun ponerError(texto: String) {
        error = texto
    }

    // Revisa que el formulario esté bien llenado
    fun datosValidos(): Boolean {
        if (nombre.isBlank() || correo.isBlank() || mensaje.isBlank()) {
            error = "Completa todos los campos"
            return false
        }
        if (!correo.contains("@")) {
            error = "El correo no es válido"
            return false
        }
        error = ""
        return true
    }
}