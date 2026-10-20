package cl.duoc.leoley

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cl.duoc.leoley.ui.Navegacion
import cl.duoc.leoley.ui.theme.LeoleyTheme
import cl.duoc.leoley.viewmodel.DashboardViewModel
import cl.duoc.leoley.viewmodel.LoginViewModel
import cl.duoc.leoley.viewmodel.MarcadoresViewModel

class MainActivity : ComponentActivity() {

    private val viewModelLogin = LoginViewModel()
    private val viewModelDashboard = DashboardViewModel()
    private val viewModelMarcadores = MarcadoresViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LeoleyTheme {
                Navegacion(viewModelLogin, viewModelDashboard, viewModelMarcadores)
            }
        }
    }
}