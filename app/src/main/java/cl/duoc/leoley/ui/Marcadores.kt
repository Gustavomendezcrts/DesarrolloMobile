package cl.duoc.leoley.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cl.duoc.leoley.model.Ley
import cl.duoc.leoley.repository.PdfRepository
import cl.duoc.leoley.viewmodel.MarcadoresViewModel

@Composable
fun Marcadores(viewModel: MarcadoresViewModel, onVolver: () -> Unit) {
    val context = LocalContext.current

    // NUEVO: ventanita de Android que pide permiso para mostrar notificaciones
    val pedirPermiso = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    // NUEVO: se ejecuta una sola vez al entrar a la pantalla
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= 33 &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            pedirPermiso.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    // Resultado de la última descarga (verde si funcionó, rojo si falló).
    // Reemplaza al Toast, que no se puede pintar de colores.
    var resultadoDescarga by remember { mutableStateOf<Pair<String, Boolean>?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            "Marcadores",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Aquí puedes ver las leyes que guardaste y eliminarlas.",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.alternarLista() },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Icon(Icons.Default.Star, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Ver marcadores")
        }
        Spacer(modifier = Modifier.height(16.dp))

        resultadoDescarga?.let { (texto, exito) ->
            MensajeEstado(texto = texto, esError = !exito)
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (viewModel.mostrarLista) {
            if (viewModel.marcadores.isEmpty()) {
                Text(
                    "No tienes leyes guardadas",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
            }
            for (ley in viewModel.marcadores) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Ley ${ley.numero}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(ley.nombre)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Año de publicación: ${ley.anio}",
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(ley.descripcion)

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilledTonalButton(
                                onClick = {
                                    val exito = PdfRepository(context).descargarLey(ley)
                                    resultadoDescarga = if (exito) {
                                        "PDF guardado en Descargas" to true
                                    } else {
                                        "No se pudo guardar el PDF (requiere Android 10 o superior)" to false
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Descargar")
                            }
                            // Eliminar en rojo
                            Button(
                                onClick = { viewModel.eliminarMarcador(ley) },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error,
                                    contentColor = MaterialTheme.colorScheme.onError
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = null)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
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