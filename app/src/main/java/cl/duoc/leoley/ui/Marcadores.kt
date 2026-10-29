package cl.duoc.leoley.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.leoley.repository.PdfRepository
import cl.duoc.leoley.viewmodel.MarcadoresViewModel

@Composable
fun Marcadores(viewModel: MarcadoresViewModel, onVolver: () -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Marcadores", fontSize = 28.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Aquí puedes ver las leyes que guardaste y eliminarlas.")
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.alternarLista() }) {
            Text("Ver marcadores")
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.mostrarLista) {
            if (viewModel.marcadores.isEmpty()) {
                Text("No tienes leyes guardadas")
            }
            for (ley in viewModel.marcadores) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Ley ${ley.numero}", fontSize = 18.sp)
                        Text(ley.nombre)
                        Text("Año de publicación: ${ley.anio}")
                        Text(ley.descripcion)
                    }
                    Column {
                        Button(onClick = {
                            val exito = PdfRepository(context).descargarLey(ley)
                            val mensaje = if (exito) {
                                "PDF guardado en Descargas"
                            } else {
                                "No se pudo guardar el PDF (requiere Android 10 o superior)"
                            }
                            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
                        }) {
                            Text("Descargar")
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Button(onClick = { viewModel.eliminarMarcador(ley) }) {
                            Text("Eliminar")
                        }
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