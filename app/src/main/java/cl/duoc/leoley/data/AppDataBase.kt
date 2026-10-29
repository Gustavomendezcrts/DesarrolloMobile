package cl.duoc.leoley.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cl.duoc.leoley.model.Ley

@Database(entities = [Ley::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun marcadorDao(): MarcadorDao

    companion object {
        private var instancia: AppDatabase? = null

        // Crea la base de datos una sola vez y la reutiliza
        fun obtener(context: Context): AppDatabase {
            val existente = instancia
            if (existente != null) {
                return existente
            }
            val nueva = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "leoley.db"
            ).build()
            instancia = nueva
            return nueva
        }
    }
}