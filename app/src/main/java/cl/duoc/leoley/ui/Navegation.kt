package cl.duoc.leoley.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.leoley.repository.SesionRepository
import cl.duoc.leoley.viewmodel.AyudaViewModel
import cl.duoc.leoley.viewmodel.BuscadorViewModel
import cl.duoc.leoley.viewmodel.DashboardViewModel
import cl.duoc.leoley.viewmodel.LoginViewModel
import cl.duoc.leoley.viewmodel.MarcadoresViewModel
import cl.duoc.leoley.viewmodel.RegistroViewModel

@Composable
fun Navegacion(
    viewModelLogin: LoginViewModel,
    viewModelDashboard: DashboardViewModel,
    viewModelMarcadores: MarcadoresViewModel,
    viewModelAyuda: AyudaViewModel,
    viewModelBuscador: BuscadorViewModel,
    sesion: SesionRepository,
    destinoInicial: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = destinoInicial) {

        composable("login") {
            Login(
                viewModel = viewModelLogin,
                onLoginExitoso = { nombreUsuario, token ->
                    sesion.guardarSesion(nombreUsuario, token)
                    viewModelDashboard.guardarNombre(nombreUsuario)
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onIrARegistro = {
                    navController.navigate("registro")
                }
            )
        }

        composable("registro") {
            // viewModel() lo ata a esta pantalla: al salir, el formulario se descarta
            val viewModelRegistro: RegistroViewModel = viewModel()
            Registro(
                viewModel = viewModelRegistro,
                onRegistroExitoso = { nombreUsuario, token ->
                    sesion.guardarSesion(nombreUsuario, token)
                    viewModelDashboard.guardarNombre(nombreUsuario)
                    // Borra login y registro del historial para que "atrás" no vuelva
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("dashboard") {
            Dashboard(
                viewModel = viewModelDashboard,
                onMarcadores = {
                    navController.navigate("marcadores")
                },
                onAyuda = {
                    navController.navigate("ayuda")
                },
                onBuscador = {
                    navController.navigate("buscador")
                },
                onCerrarSesion = {
                    sesion.cerrarSesion()
                    viewModelLogin.limpiar()
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
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

        composable("ayuda") {
            Ayuda(
                viewModel = viewModelAyuda,
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("buscador") {
            Buscador(
                viewModel = viewModelBuscador,
                onGuardar = { ley ->
                    viewModelMarcadores.agregarMarcador(ley)
                },
                onVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}