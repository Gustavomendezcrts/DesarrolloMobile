package cl.duoc.leoley.repository

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthRepositoryTest {

    // Este debe PASAR: usuario y clave correctos, la API devuelve un token
    @Test
    fun login_correcto() = runBlocking {
        println("Hizo: iniciarSesion(\"prueba@gmail.com\", \"prueba1234\") en la API real (este debe funcionar)")
        try {
            val token = AuthRepository().iniciarSesion("prueba@gmail.com", "prueba1234")
            println("Esperaba: un token que no esté vacío")
            println("Llegó:    $token")

            assertTrue(token.isNotBlank())
            println("TERMINÓ BIEN")
        } catch (e: AssertionError) {
            println("TERMINÓ MAL: el token llegó vacío")
            throw e
        } catch (e: Exception) {
            println("TERMINÓ MAL: no se pudo iniciar sesión (${e.message})")
            throw e
        }
    }

    // Este debe FALLAR: clave incorrecta, la API responde error y no hay token
    @Test
    fun login_error() = runBlocking {
        println("Hizo: iniciarSesion(\"prueba@gmail.com\", \"claveMala\") en la API real (este debe dar error)")
        try {
            val token = AuthRepository().iniciarSesion("prueba@gmail.com", "claveMala")
            println("Esperaba: un token que no esté vacío")
            println("Llegó:    $token")

            assertTrue(token.isNotBlank())
            println("TERMINÓ BIEN")
        } catch (e: AssertionError) {
            println("TERMINÓ MAL: el token llegó vacío")
            throw e
        } catch (e: Exception) {
            println("TERMINÓ MAL: no se pudo iniciar sesión (${e.message})")
            throw e
        }
    }
}