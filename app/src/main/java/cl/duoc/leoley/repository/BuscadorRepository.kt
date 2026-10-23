package cl.duoc.leoley.repository

import cl.duoc.leoley.model.Ley

class BuscadorRepository {

    private val leyes = listOf(
        Ley("19.628", "Protección de la vida privada", "Regula el tratamiento de datos personales en registros o bancos de datos.", 1999),
        Ley("20.000", "Tráfico ilícito de estupefacientes", "Sanciona el tráfico ilícito de estupefacientes y sustancias psicotrópicas.", 2005),
        Ley("21.719", "Protección de datos personales", "Regula la protección y el tratamiento de los datos personales.", 2024),
        Ley("19.496", "Derechos de los consumidores", "Establece normas sobre protección de los derechos de los consumidores.", 1997),
        Ley("21.459", "Delitos informáticos", "Establece normas sobre delitos informáticos.", 2022),
        Ley("19.799", "Firma electrónica", "Regula los documentos electrónicos y la firma electrónica.", 2002),
        Ley("20.609", "Medidas contra la discriminación", "Establece medidas contra la discriminación.", 2012),
        Ley("20.285", "Acceso a la información pública", "Regula el acceso a la información pública.", 2008)
    )

    // Busca por nombre o por número (con o sin puntos)
    fun buscar(texto: String): List<Ley> {
        val busqueda = texto.trim().replace(".", "")
        return leyes.filter { ley ->
            ley.nombre.contains(busqueda, ignoreCase = true) ||
                    ley.numero.replace(".", "").contains(busqueda)
        }
    }
}