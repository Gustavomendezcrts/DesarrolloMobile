package cl.duoc.leoley.repository

import cl.duoc.leoley.model.Usuario

class LoginRepository {

    private val usuarioTest = Usuario(1, "Juan", "Pérez", "juan@correo.cl", "juanp", "1234", "admin")
    fun login(identificador: String, contrasena: String): Usuario? {
        val coincideUsuario = identificador == usuarioTest.correo || identificador == usuarioTest.username
        if (coincideUsuario && contrasena == usuarioTest.contrasena) {
            return usuarioTest
        }
        return null
    }
}