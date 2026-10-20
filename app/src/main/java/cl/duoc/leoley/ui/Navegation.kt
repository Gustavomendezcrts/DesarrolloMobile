package cl.duoc.leoley.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cl.duoc.leoley.viewmodel.DashboardViewModel
import cl.duoc.leoley.viewmodel.LoginViewModel
import cl.duoc.leoley.viewmodel.MarcadoresViewModel

@Composable
fun Navegacion(
    viewModelLogin: LoginViewModel,
    viewModelDashboard: DashboardViewModel,
    viewModelMarcadores: MarcadoresViewModel
) {
    var pantalla by remember { mutableStateOf("login") }

    if (pantalla == "login") {
        Login(
            viewModel = viewModelLogin,
            onLoginExitoso = { nombreUsuario ->
                viewModelDashboard.guardarNombre(nombreUsuario)
                pantalla = "dashboard"
            }
        )
    } else if (pantalla == "dashboard") {
        Dashboard(
            viewModel = viewModelDashboard,
            onMarcadores = { pantalla = "marcadores" }
        )
    } else if (pantalla == "marcadores") {
        Marcadores(
            viewModel = viewModelMarcadores,
            onVolver = { pantalla = "dashboard" }
        )
    }
}