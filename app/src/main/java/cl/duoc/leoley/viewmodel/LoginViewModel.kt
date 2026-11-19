
package cl.duoc.leoley.viewmodel

import android.util.Log
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

            val token = try {
                repo.iniciarSesion(correo.trim(), contrasena)
            } catch (e: CancellationException) {
                throw e
            } catch (e: HttpException) {
                Log.e("LeoLeyLogin", "auth/login falló: ${e.code()}", e)
                mensaje = if (e.code() in 400..499) {
                    "Correo o contraseña incorrectos"
                } else {
                    "El servidor tuvo un problema, intenta más tarde"
                }
                cargando = false
                return@launch
            } catch (e: Exception) {
                Log.e("LeoLeyLogin", "auth/login error", e)
                mensaje = "No se pudo conectar con el servidor"
                cargando = false
                return@launch
            }

            try {
                val usuario = repo.obtenerUsuario(token)
                onExito(usuario.nombre, token)
            } catch (e: CancellationException) {
                throw e
            } catch (e: HttpException) {
                Log.e("LeoLeyLogin", "auth/me falló: ${e.code()}", e)
                mensaje = "No se pudo cargar tu perfil, intenta nuevamente"
            } catch (e: Exception) {
                Log.e("LeoLeyLogin", "auth/me error", e)
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
