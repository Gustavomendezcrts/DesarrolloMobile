package cl.duoc.leoley.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cl.duoc.leoley.model.Ley
import cl.duoc.leoley.viewmodel.BuscadorViewModel

@Composable
fun Buscador(
    viewModel: BuscadorViewModel,
    onGuardar: (Ley) -> Unit,
    onVolver: () -> Unit
) {
    // Se ejecuta cada vez que entras a la pantalla: la deja limpia
    LaunchedEffect(Unit) {
        viewModel.limpiar()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            "Buscador",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.texto,
            onValueChange = { viewModel.cambiarTexto(it) },
            label = { Text("Nombre o número de ley") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (viewModel.texto.isNotEmpty()) {
                    IconButton(onClick = { viewModel.cambiarTexto("") }) {
                        Icon(Icons.Default.Close, contentDescription = "Borrar búsqueda")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Verde: la ley se guardó bien
        if (viewModel.mensaje.isNotEmpty()) {
            MensajeEstado(texto = viewModel.mensaje, esError = false)
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (viewModel.cargando) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        // Rojo: falló la conexión
        if (viewModel.error.isNotEmpty()) {
            MensajeEstado(texto = viewModel.error, esError = true)
        }

        if (!viewModel.cargando && viewModel.error.isEmpty() && viewModel.resultados.isEmpty()) {
            Text(
                "No se encontraron leyes",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(viewModel.resultados) { ley ->
                val abierta = viewModel.leyesAbiertas.contains(ley.numero)

                Card(
                    onClick = { viewModel.alternarLey(ley.numero) },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    "Ley ${ley.numero}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(ley.nombre)
                            }
                            Icon(
                                imageVector = if (abierta) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = if (abierta) "Ocultar detalle" else "Ver detalle"
                            )
                        }

                        if (abierta) {
                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Año de publicación: ${ley.anio}",
                                style = MaterialTheme.typography.labelLarge
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(ley.descripcion)
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {
                                    onGuardar(ley)
                                    viewModel.ponerMensaje("Ley ${ley.numero} guardada en marcadores")
                                },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Guardar")
                            }
                        }
                    }
                }
            }
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
