package cl.duoc.leoley

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.leoley.ui.theme.LeoleyTheme

class MarcadorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeoleyTheme {
                // Leyes guardadas (de ejemplo)
                var marcadores by remember {
                    mutableStateOf(listOf("Ley 19.628", "Ley 20.000", "Ley 21.719"))
                }
                // Controla si se muestra la lista o no
                var mostrarLista by remember { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .padding(24.dp)
                ) {
                    Text("Marcadores", fontSize = 28.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Aquí puedes ver las leyes que guardaste y eliminarlas.")
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = { mostrarLista = !mostrarLista }) {
                        Text("Ver marcadores")
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    if (mostrarLista) {
                        if (marcadores.isEmpty()) {
                            Text("No tienes leyes guardadas")
                        }
                        for (ley in marcadores) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(ley, modifier = Modifier.weight(1f))
                                Button(onClick = { marcadores = marcadores - ley }) {
                                    Text("Eliminar marcador")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}