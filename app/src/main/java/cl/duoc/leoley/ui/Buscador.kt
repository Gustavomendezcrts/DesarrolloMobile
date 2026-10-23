package cl.duoc.leoley.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            .padding(24.dp)
    ) {
        Text("Buscador", fontSize = 28.sp)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.texto,
            onValueChange = { viewModel.cambiarTexto(it) },
            label = { Text("Nombre o número de ley") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = viewModel.mensaje)
        Spacer(modifier = Modifier.height(8.dp))

        if (viewModel.resultados.isEmpty()) {
            Text("No se encontraron leyes")
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(viewModel.resultados) { ley ->
                val abierta = viewModel.leyesAbiertas.contains(ley.numero)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.alternarLey(ley.numero) }
                        .padding(vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Ley ${ley.numero}", fontSize = 18.sp)
                            Text(ley.nombre)
                        }
                        Text(if (abierta) "▲" else "▼")
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            onGuardar(ley)
                            viewModel.ponerMensaje("Ley ${ley.numero} guardada en marcadores")
                        }) {
                            Text("Guardar")
                        }
                    }

                    if (abierta) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Año de publicación: ${ley.anio}")
                        Text(ley.descripcion)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onVolver) {
            Text("Volver")
        }
    }
}