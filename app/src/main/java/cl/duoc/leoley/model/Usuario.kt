package cl.duoc.leoley.model

data class Usuario(
    val id: Int,
    var nombre: String,
    var apellido: String,
    var correo: String,
    var username: String,
    var contrasena: String,
    var rol: String
) {
    fun mostrarInfo(): String {
        return "ID: $id\nNombre: $nombre $apellido\nCorreo: $correo\nRol: $rol"
    }
}