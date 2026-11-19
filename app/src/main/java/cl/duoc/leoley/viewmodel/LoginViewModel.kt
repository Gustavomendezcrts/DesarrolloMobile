package cl.duoc.leoley.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.leoley.repository.AuthRepository
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel : ViewModel() {

    private val repo = AuthRepository()

    var correo by mutableStateOf("")
        private set

    var contrasena by mutableStateOf("")
        private set

    var mensaje by mutableStateOf("")
        private set

    var cargando by mutableStateOf(false)
        private set

    fun cambiarCorreo(valor: String) {
        correo = valor
    }

    fun cambiarContrasena(valor: String) {
        contrasena = valor
    }

    fun iniciarSesion(onExito: (String, String) -> Unit) {
        if (correo.isBlank() || contrasena.isBlank()) {
            mensaje = "Completa el correo y la contraseña"
            return
        }

        viewModelScope.launch {
            cargando = true
            mensaje = ""
            try {
                val token = repo.iniciarSesion(correo.trim(), contrasena)
                val usuario = repo.obtenerUsuario(token)
                onExito(usuario.nombre, token)
            } catch (e: CancellationException) {
                throw e
            } catch (e: HttpException) {
                mensaje = "Correo o contraseña incorrectos"
            } catch (e: Exception) {
                mensaje = "No se pudo conectar con el servidor"
            }
            cargando = false
        }
    }

    fun limpiar() {
        correo = ""
        contrasena = ""
        mensaje = ""
    }
}