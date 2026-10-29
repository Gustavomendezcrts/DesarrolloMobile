package cl.duoc.leoley.repository

import android.content.Context

class SesionRepository(context: Context) {

    private val prefs = context.getSharedPreferences("sesion", Context.MODE_PRIVATE)

    fun guardarSesion(nombre: String) {
        prefs.edit().putString("nombre", nombre).apply()
    }

    // Devuelve el nombre guardado, o null si no hay sesión
    fun obtenerNombre(): String? {
        return prefs.getString("nombre", null)
    }

    fun cerrarSesion() {
        prefs.edit().clear().apply()
    }
}