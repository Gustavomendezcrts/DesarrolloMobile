package cl.duoc.leoley.repository

import cl.duoc.leoley.data.RetrofitClient
import cl.duoc.leoley.model.LoginRequest
import cl.duoc.leoley.model.RegistroRequest
import cl.duoc.leoley.model.Usuario

class AuthRepository {

    suspend fun iniciarSesion(correo: String, contrasena: String): String {
        return RetrofitClient.authApi.iniciarSesion(LoginRequest(correo, contrasena)).authToken
    }

    suspend fun registrar(nombre: String, correo: String, contrasena: String): String {
        return RetrofitClient.authApi.registrar(RegistroRequest(nombre, correo, contrasena)).authToken
    }

    suspend fun obtenerUsuario(token: String): Usuario {
        return RetrofitClient.authApi.obtenerUsuario("Bearer $token")
    }
}