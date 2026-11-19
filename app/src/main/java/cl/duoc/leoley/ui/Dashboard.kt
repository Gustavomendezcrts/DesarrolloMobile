package cl.duoc.leoley.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.leoley.viewmodel.DashboardViewModel

@Composable
fun Dashboard(
    viewModel: DashboardViewModel,
    onMarcadores: () -> Unit,
    onAyuda: () -> Unit,
    onBuscador: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Logo(120.dp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Hola ${viewModel.nombre}",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(32.dp))

        BotonMenu("Marcadores", Icons.Default.Star, onMarcadores)
        Spacer(modifier = Modifier.height(12.dp))

        BotonMenu("Ayuda", Icons.Default.Info, onAyuda)
        Spacer(modifier = Modifier.height(12.dp))

        BotonMenu("Buscador", Icons.Default.Search, onBuscador)
        Spacer(modifier = Modifier.height(32.dp))

        // Cerrar sesión en rojo
        OutlinedButton(
            onClick = onCerrarSesion,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Cerrar sesión")
        }
    }
}

@Composable
private fun BotonMenu(texto: String, icono: ImageVector, onClick: () -> Unit) {
    FilledTonalButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Icon(imageVector = icono, contentDescription = null)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = texto, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))
    }
}
