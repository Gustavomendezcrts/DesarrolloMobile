package cl.duoc.leoley

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.leoley.repository.LoginRepository
import cl.duoc.leoley.ui.theme.LeoleyTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {

    val repo = LoginRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LeoleyTheme {

                var usuario by remember { mutableStateOf("") }
                var contrasena by remember { mutableStateOf("") }
                var mensaje by remember { mutableStateOf("") }
                var cargando by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column() {
                        Text(
                            text = "LOGIN",
                            fontSize = 32.sp
                        )

                        OutlinedTextField(
                            value = usuario,
                            onValueChange = { usuario = it },
                            label = { Text("Correo/Usuario")  },
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = contrasena,
                            onValueChange = { contrasena = it },
                            label = { Text("Contraseña") },
                            singleLine = true,
                        )

                        Button(onClick = {
                            val resultado = repo.login(usuario, contrasena)
                            cargando = true
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(2000.milliseconds)
                                cargando = false

                                if (resultado != null) {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        delay(2001.milliseconds)
                                        val intent =
                                            Intent(this@MainActivity, DashboardActivity::class.java)
                                        intent.putExtra("nombre", resultado.nombre)
                                        startActivity(intent)
                                    }
                                } else {
                                    mensaje = "Credenciales incorrectas"
                                }
                            }
                        }) {
                            if(cargando){
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = Color.White
                            )} else {
                            Text(text = "Login")}
                        }

                        Text(text = mensaje)
                    }

                }
            }
        }
    }
}