
package cl.duoc.leoley.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cl.duoc.leoley.viewmodel.RegistroViewModel

@Composable
fun Registro(
    viewModel: RegistroViewModel,
    onRegistroExitoso: (String, String) -> Unit,
    onVolver: () -> Unit
) {
    val hayError = viewModel.mensaje.isNotEmpty()
    var verContrasena by rememberSaveable { mutableStateOf(false) }
    val visual = if (verContrasena) VisualTransformation.None else PasswordVisualTransformation()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Logo(120.dp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Crear cuenta",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = viewModel.nombre,
            onValueChange = { viewModel.cambiarNombre(it) },
            label = { Text("Nombre") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.correo,
            onValueChange = { viewModel.cambiarCorreo(it) },
            label = { Text("Correo") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.contrasena,
            onValueChange = { viewModel.cambiarContrasena(it) },
            label = { Text("Contraseña (mínimo 8 caracteres)") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                TextButton(onClick = { verContrasena = !verContrasena }) {
                    Text(if (verContrasena) "Ocultar" else "Ver")
                }
            },
            visualTransformation = visual,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.confirmacion,
            onValueChange = { viewModel.cambiarConfirmacion(it) },
            label = { Text("Repite la contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = visual,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            isError = hayError,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (hayError) {
            MensajeEstado(texto = viewModel.mensaje, esError = true)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = { viewModel.registrar(onRegistroExitoso) },
            enabled = !viewModel.cargando,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            if (viewModel.cargando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = LocalContentColor.current
                )
            } else {
                Text(text = "Registrarme")
            }
        }

        TextButton(onClick = onVolver) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}

@Composable
private fun MensajeEstado(texto: String, esError: Boolean) {
    val fondo = MaterialTheme.colorScheme.errorContainer
    val contenido = MaterialTheme.colorScheme.onErrorContainer

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(fondo)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = contenido)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = texto, color = contenido, style = MaterialTheme.typography.bodyMedium)
    }
}
 