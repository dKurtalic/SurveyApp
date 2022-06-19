package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje

@Dao
interface GrupaDAO {

    @Query("SELECT * FROM grupa WHERE istrazivanjeId=:ID1")
    suspend fun dajGrupeZaIstrazivanje(ID1:Int) : List<Grupa>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGrupa(vararg grupa: Grupa)

    @Query("SELECT * FROM grupa")
    fun getAllGrupe():List<Grupa>

    @Query("SELECT * FROM grupa WHERE upisana=1")
    fun getUpisane(): List<Grupa>

}