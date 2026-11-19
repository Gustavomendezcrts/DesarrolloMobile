package cl.duoc.leoley.repository

import cl.duoc.leoley.model.Ley
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class BuscadorRepositoryTest {

    // Este debe PASAR: los datos coinciden con lo que devuelve la API
    @Test
    fun buscar_ley_correcta() = runBlocking {
        val esperada = Ley(
            numero = "19.628",
            nombre = "Protección de la vida privada",
            descripcion = "Regula el tratamiento de datos personales en registros o bancos de datos.",
            anio = 1999
        )

        println("Hizo: buscar(\"19628\") en la API real (este debe funcionar)")
        try {
            val resultado = BuscadorRepository().buscar("19628")
            println("Esperaba: ${listOf(esperada)}")
            println("Llegó:    $resultado")

            assertEquals(listOf(esperada), resultado)
            println("TERMINÓ BIEN")
        } catch (e: AssertionError) {
            println("TERMINÓ MAL: los datos no coinciden")
            throw e
        } catch (e: Exception) {
            println("TERMINÓ MAL: no se pudo consultar la API (${e.message})")
            throw e
        }
    }

    // Este debe FALLAR: busca la 19628 pero espera el número de la 20000
    @Test
    fun buscar_ley_error() = runBlocking {
        val esperada = Ley(
            numero = "20.000",
            nombre = "Protección de la vida privada",
            descripcion = "Regula el tratamiento de datos personales en registros o bancos de datos.",
            anio = 1999
        )

        println("Hizo: buscar(\"19628\") en la API real (este debe dar error)")
        try {
            val resultado = BuscadorRepository().buscar("19628")
            println("Esperaba: ${listOf(esperada)}")
            println("Llegó:    $resultado")

            assertEquals(listOf(esperada), resultado)
            println("TERMINÓ BIEN")
        } catch (e: AssertionError) {
            println("TERMINÓ MAL: los datos no coinciden")
            throw e
        } catch (e: Exception) {
            println("TERMINÓ MAL: no se pudo consultar la API (${e.message})")
            throw e
        }
    }
}