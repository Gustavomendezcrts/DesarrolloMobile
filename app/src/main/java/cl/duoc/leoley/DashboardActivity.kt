package cl.duoc.leoley

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import cl.duoc.leoley.ui.theme.LeoleyTheme

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Recibe el nombre que mandó el login
        val nombre = intent.getStringExtra("nombre") ?: ""

        setContent {
            LeoleyTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text(text = "Dashboard", fontSize = 32.sp)
                        Text(text = "Hola $nombre")
                    }
                }
            }
        }
    }
}