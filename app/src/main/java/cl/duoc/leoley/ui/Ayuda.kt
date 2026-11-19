package cl.duoc.leoley.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import android.content.Intent
import android.net.Uri
import cl.duoc.leoley.viewmodel.AyudaViewModel

@Composable
fun Ayuda(viewModel: AyudaViewModel, onVolver: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            "Ayuda",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "¿Tienes un problema o una duda? Escríbenos y te responderemos.",
            color = MaterialTheme.colorScheme.onSurfaceVariant
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
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.mensaje,
            onValueChange = { viewModel.cambiarMensaje(it) },
            label = { Text("Mensaje") },
            minLines = 4,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Rojo: errores del formulario
        if (viewModel.error.isNotEmpty()) {
            MensajeEstado(texto = viewModel.error, esError = true)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = {
                if (viewModel.datosValidos()) {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:")
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("gu.mendezc@duocuc.cl", "ar.ruiztagle@duocuc.cl"))
                        putExtra(Intent.EXTRA_SUBJECT, "Ayuda LeoLey - ${viewModel.nombre}")
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Correo de contacto: ${viewModel.correo}\n\n${viewModel.mensaje}"
                        )
                    }
                    try {
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        viewModel.ponerError("No se encontró una app de correo")
                    }
                }
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Enviar")
        }

        Spacer(modifier = Modifier.height(12.dp))
        BotonVolver(onVolver)
    }
}

// Caja de mensaje: roja (error) o verde (algo funcionó)
@Composable
private fun MensajeEstado(texto: String, esError: Boolean) {
    val oscuro = isSystemInDarkTheme()
    val fondo = if (esError) MaterialTheme.colorScheme.errorContainer
    else if (oscuro) Color(0xFF123D21) else Color(0xFFD7F5DF)
    val contenido = if (esError) MaterialTheme.colorScheme.onErrorContainer
    else if (oscuro) Color(0xFF9BE8B3) else Color(0xFF0B5D27)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(fondo)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (esError) Icons.Default.Warning else Icons.Default.CheckCircle,
            contentDescription = null,
            tint = contenido
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = texto, color = contenido, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun BotonVolver(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Volver")
    }
}
