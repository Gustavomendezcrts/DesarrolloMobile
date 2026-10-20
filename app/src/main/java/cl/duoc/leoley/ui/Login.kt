package cl.duoc.leoley.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.leoley.viewmodel.LoginViewModel

@Composable
fun Login(viewModel: LoginViewModel, onLoginExitoso: (String) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            Text(text = "LOGIN", fontSize = 32.sp)

            OutlinedTextField(
                value = viewModel.usuario,
                onValueChange = { viewModel.cambiarUsuario(it) },
                label = { Text("Correo/Usuario") },
                singleLine = true
            )

            OutlinedTextField(
                value = viewModel.contrasena,
                onValueChange = { viewModel.cambiarContrasena(it) },
                label = { Text("Contraseña") },
                singleLine = true
            )

            Button(onClick = {
                viewModel.iniciarSesion(onLoginExitoso)
            }) {
                if (viewModel.cargando) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                } else {
                    Text(text = "Login")
                }
            }

            Text(text = viewModel.mensaje)
        }
    }
}