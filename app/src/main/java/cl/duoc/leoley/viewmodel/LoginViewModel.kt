package cl.duoc.leoley.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cl.duoc.leoley.repository.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class LoginViewModel : ViewModel() {

    private val repo = LoginRepository()

    var usuario by mutableStateOf("")
        private set

    var contrasena by mutableStateOf("")
        private set

    var mensaje by mutableStateOf("")
        private set

    var cargando by mutableStateOf(false)
        private set

    fun cambiarUsuario(valor: String) {
        usuario = valor
    }

    fun cambiarContrasena(valor: String) {
        contrasena = valor
    }

    fun iniciarSesion(onExito: (String) -> Unit) {
        val resultado = repo.login(usuario, contrasena)
        cargando = true
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000.milliseconds)
            cargando = false

            if (resultado != null) {
                onExito(resultado.nombre)
            } else {
                mensaje = "Credenciales incorrectas"
            }
        }
    }
}