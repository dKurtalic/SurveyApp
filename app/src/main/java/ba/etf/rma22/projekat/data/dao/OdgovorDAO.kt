package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Odgovor

@Dao
interface OdgovorDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg odgovor: Odgovor)

    @Query("SELECT * FROM odgovor WHERE PitanjeId=:pid")
    fun getOdgovorZaPitanje(pid:Int):List<Odgovor>
}