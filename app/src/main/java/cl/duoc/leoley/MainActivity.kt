package cl.duoc.leoley

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import cl.duoc.leoley.repository.LoginRepository
import cl.duoc.leoley.ui.theme.LeoleyTheme

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
                            if (resultado != null) {
                                val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                                intent.putExtra("nombre", resultado.nombre)
                                startActivity(intent)
                            } else {
                                mensaje = "Credenciales incorrectas"
                            }
                        }) {
                            Text(text = "Login")
                        }

                        Text(text = mensaje)

                        Button(onClick = {

                                val intent = Intent(this@MainActivity, CamaraActivity::class.java)
                                startActivity(intent)
                        }) {
                            Text(text = "Login")
                        }
                    }

                }
            }
        }
    }
}