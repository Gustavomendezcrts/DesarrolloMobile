package cl.duoc.leoley.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.leoley.repository.AuthRepository
import com.google.gson.Gson
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.launch
import retrofit2.HttpException

// Forma del error que devuelve Xano: {"code": "...", "message": "..."}
private data class ErrorXano(val code: String?, val message: String?)

class RegistroViewModel : ViewModel() {

    private val repo = AuthRepository()

    var nombre by mutableStateOf("")
        private set

    var correo by mutableStateOf("")
        private set

    var contrasena by mutableStateOf("")
        private set

    var confirmacion by mutableStateOf("")
        private set

    var mensaje by mutableStateOf("")
        private set

    var cargando by mutableStateOf(false)
        private set

    fun cambiarNombre(valor: String) { nombre = valor }
    fun cambiarCorreo(valor: String) { correo = valor }
    fun cambiarContrasena(valor: String) { contrasena = valor }
    fun cambiarConfirmacion(valor: String) { confirmacion = valor }

    // Devuelve el texto del error, o null si los datos están bien
    private fun validar(): String? {
        return when {
            nombre.isBlank() -> "Ingresa tu nombre"
            !Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches() -> "El correo no es válido"
            contrasena.length < 8 -> "La contraseña debe tener al menos 8 caracteres"
            contrasena != confirmacion -> "Las contraseñas no coinciden"
            else -> null
        }
    }

    fun registrar(onExito: (String, String) -> Unit) {
        val error = validar()
        if (error != null) {
            mensaje = error
            return
        }

        viewModelScope.launch {
            cargando = true
            mensaje = ""
            try {
                val token = repo.registrar(nombre.trim(), correo.trim(), contrasena)
                onExito(nombre.trim(), token)
            } catch (e: CancellationException) {
                throw e
            } catch (e: HttpException) {
                mensaje = traducirError(e)
            } catch (e: Exception) {
                mensaje = "No se pudo conectar con el servidor"
            }
            cargando = false
        }
    }

    private fun traducirError(e: HttpException): String {
        val cuerpo = e.response()?.errorBody()?.string().orEmpty()
        val textoXano = runCatching {
            Gson().fromJson(cuerpo, ErrorXano::class.java)?.message
        }.getOrNull().orEmpty()

        return when {
            textoXano.contains("duplicate", ignoreCase = true) ||
                textoXano.contains("already", ignoreCase = true) ->
                "Ese correo ya está registrado"
            e.code() in 400..499 && textoXano.isNotBlank() -> textoXano
            e.code() >= 500 -> "El servidor tuvo un problema, intenta más tarde"
            else -> "No se pudo crear la cuenta"
        }
    }

    fun limpiar() {
        nombre = ""
        correo = ""
        contrasena = ""
        confirmacion = ""
        mensaje = ""
    }
}
