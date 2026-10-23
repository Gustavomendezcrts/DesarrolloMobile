package cl.duoc.leoley.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Text("Ayuda", fontSize = 28.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("¿Tienes un problema o una duda? Escríbenos y te responderemos.")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.nombre,
            onValueChange = { viewModel.cambiarNombre(it) },
            label = { Text("Nombre") },
            singleLine = true
        )

        OutlinedTextField(
            value = viewModel.correo,
            onValueChange = { viewModel.cambiarCorreo(it) },
            label = { Text("Correo") },
            singleLine = true
        )

        OutlinedTextField(
            value = viewModel.mensaje,
            onValueChange = { viewModel.cambiarMensaje(it) },
            label = { Text("Mensaje") },
            minLines = 4
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = viewModel.error)
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
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
        }) {
            Text("Enviar")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onVolver) {
            Text("Volver")
        }
    }
}