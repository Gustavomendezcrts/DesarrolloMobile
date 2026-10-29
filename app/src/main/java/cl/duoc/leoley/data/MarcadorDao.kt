package cl.duoc.leoley.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.duoc.leoley.model.Ley

@Dao
interface MarcadorDao {

    @Query("SELECT * FROM marcadores")
    suspend fun obtenerTodos(): List<Ley>

    // Si la ley ya existe, no hace nada
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(ley: Ley)

    @Delete
    suspend fun eliminar(ley: Ley)
}