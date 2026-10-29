package cl.duoc.leoley

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cl.duoc.leoley.data.AppDatabase
import cl.duoc.leoley.repository.MarcadoresRepository
import cl.duoc.leoley.repository.SesionRepository
import cl.duoc.leoley.ui.Navegacion
import cl.duoc.leoley.ui.theme.LeoleyTheme
import cl.duoc.leoley.viewmodel.AyudaViewModel
import cl.duoc.leoley.viewmodel.BuscadorViewModel
import cl.duoc.leoley.viewmodel.DashboardViewModel
import cl.duoc.leoley.viewmodel.LoginViewModel
import cl.duoc.leoley.viewmodel.MarcadoresViewModel

class MainActivity : ComponentActivity() {

    private val viewModelLogin = LoginViewModel()
    private val viewModelDashboard = DashboardViewModel()
    private lateinit var viewModelMarcadores: MarcadoresViewModel
    private val viewModelAyuda = AyudaViewModel()
    private val viewModelBuscador = BuscadorViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val baseDeDatos = AppDatabase.obtener(this)
        viewModelMarcadores = MarcadoresViewModel(MarcadoresRepository(baseDeDatos.marcadorDao()))

        val sesion = SesionRepository(this)
        val nombreGuardado = sesion.obtenerNombre()
        if (nombreGuardado != null) {
            viewModelDashboard.guardarNombre(nombreGuardado)
        }
        val destinoInicial = if (nombreGuardado != null) "dashboard" else "login"

        setContent {
            LeoleyTheme {
                Navegacion(
                    viewModelLogin,
                    viewModelDashboard,
                    viewModelMarcadores,
                    viewModelAyuda,
                    viewModelBuscador,
                    sesion,
                    destinoInicial
                )
            }
        }
    }
}