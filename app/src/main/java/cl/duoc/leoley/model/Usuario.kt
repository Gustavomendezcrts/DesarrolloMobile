package cl.duoc.leoley.model

import com.google.gson.annotations.SerializedName

data class Usuario(
    val id: Int,
    @SerializedName("name") val nombre: String,
    @SerializedName("email") val correo: String
)