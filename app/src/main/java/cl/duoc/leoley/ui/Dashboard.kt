package cl.duoc.leoley.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.leoley.viewmodel.DashboardViewModel

@Composable
fun Dashboard(viewModel: DashboardViewModel, onMarcadores: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Dashboard", fontSize = 32.sp)
            Text(text = "Hola ${viewModel.nombre}")
            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onMarcadores) {
                Text("Marcadores")
            }
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                // Aquí irá Ayuda
            }) {
                Text("Ayuda")
            }
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                // Aquí irá Buscador
            }) {
                Text("Buscador")
            }
        }
    }
}