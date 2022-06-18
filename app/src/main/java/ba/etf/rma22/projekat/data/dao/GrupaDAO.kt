package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Grupa

@Dao
interface GrupaDAO {

    @Query("SELECT * FROM grupa WHERE id=:ID1")
    suspend fun dajGrupeZaPredmet(ID1:Int) : List<Grupa>


}