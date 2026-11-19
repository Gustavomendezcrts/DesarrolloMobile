package cl.duoc.leoley.data

import cl.duoc.leoley.model.AuthResponse
import cl.duoc.leoley.model.LoginRequest
import cl.duoc.leoley.model.RegistroRequest
import cl.duoc.leoley.model.Usuario
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/login")
    suspend fun iniciarSesion(@Body datos: LoginRequest): AuthResponse

    @POST("auth/signup")
    suspend fun registrar(@Body datos: RegistroRequest): AuthResponse

    @GET("auth/me")
    suspend fun obtenerUsuario(@Header("Authorization") token: String): Usuario
}