package cl.duoc.leoley.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email") val correo: String,
    @SerializedName("password") val contrasena: String
)

data class RegistroRequest(
    @SerializedName("name") val nombre: String,
    @SerializedName("email") val correo: String,
    @SerializedName("password") val contrasena: String
)

data class AuthResponse(
    val authToken: String
)