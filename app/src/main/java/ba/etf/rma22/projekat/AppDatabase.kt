package ba.etf.rma22.projekat

import android.content.Context
import androidx.room.*
import ba.etf.rma22.projekat.data.Converter
import ba.etf.rma22.projekat.data.dao.*
import ba.etf.rma22.projekat.data.models.*

@Database(entities = arrayOf(Account::class, Anketa::class, Grupa::class, Istrazivanje::class, Pitanje::class, Odgovor::class,AnketaTaken::class,PitanjeAnketa::class), version =2)
@TypeConverters(Converter::class)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun accountDAO(): AccountDAO
        abstract fun anketaDAO(): AnketaDAO
        abstract fun grupaDAO(): GrupaDAO
        abstract fun istrazivanjeDAO(): IstrazivanjeDAO
        abstract fun pitanjeDAO(): PitanjeDAO
        abstract fun odgovorDAO(): OdgovorDAO
        abstract fun anketaTakenDAO(): AnketaTakenDAO

        companion object {
            private var INSTANCE: AppDatabase? = null
            fun getInstance(context: Context): AppDatabase {
                if (INSTANCE == null) {
                    synchronized(AppDatabase::class) {
                        INSTANCE = buildRoomDB(context)
                    }
                }
                return INSTANCE!!
            }
            private fun buildRoomDB(context: Context) =
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "RMA22DB"
                ).fallbackToDestructiveMigration().build()
        }
    }
