package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.PitanjeAnketa

@Dao
interface PitanjeAnketaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg pitanjenketa: PitanjeAnketa)

    @Query("SELECT * FROM pitanjeanketa WHERE anketa=:nazivAnkete")
    fun getPitanjeAnketaZaAnketu(nazivAnkete:String):PitanjeAnketa
}