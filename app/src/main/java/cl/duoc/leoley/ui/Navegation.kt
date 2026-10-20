package cl.duoc.leoley.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.leoley.viewmodel.DashboardViewModel
import cl.duoc.leoley.viewmodel.LoginViewModel
import cl.duoc.leoley.viewmodel.MarcadoresViewModel

@Composable
fun Navegacion(
    viewModelLogin: LoginViewModel,
    viewModelDashboard: DashboardViewModel,
    viewModelMarcadores: MarcadoresViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            Login(
                viewModel = viewModelLogin,
                onLoginExitoso = { nombreUsuario ->
                    viewModelDashboard.guardarNombre(nombreUsuario)
                    // Navegamos al dashboard y limpiamos el historial para que no pueda volver con el botón atrás
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("dashboard") {
            Dashboard(
                viewModel = viewModelDashboard,
                onMarcadores = {
                    navController.navigate("marcadores")
                }
            )
        }

        composable("marcadores") {
            Marcadores(
                viewModel = viewModelMarcadores,
                onVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}