package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Pitanje

@Dao
interface PitanjeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg pitanje: Pitanje)

    @Query("SELECT * FROM pitanje WHERE naziv=:nazivp")
    fun getPitanjeByNaziv(nazivp:String):List<Pitanje>
}