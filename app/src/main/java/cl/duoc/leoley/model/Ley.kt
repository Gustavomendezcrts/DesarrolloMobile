package cl.duoc.leoley.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marcadores")
data class Ley(
    @PrimaryKey val numero: String,
    val nombre: String,
    val descripcion: String,
    val anio: Int
)