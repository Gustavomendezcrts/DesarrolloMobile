package cl.duoc.leoley

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Dashboard", fontSize = 32.sp)
                        Text(text = "Hola $nombre")
                        Spacer(modifier = Modifier.height(24.dp))

                        Button(onClick = {
                            val intent = Intent(this@DashboardActivity, MarcadorActivity::class.java)
                            startActivity(intent)
                        }) {
                            Text("Marcadores")
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Button(onClick = {
                            // Aquí irá el salto a AyudaActivity cuando la crees
                        }) {
                            Text("Ayuda")
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Button(onClick = {
                            // Aquí irá el salto a BuscadorActivity cuando la crees
                        }) {
                            Text("Buscador")
                        }
                    }
                }
            }
        }
    }
}