package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Istrazivanje

@Dao
interface IstrazivanjeDAO {
    @Query("SELECT * FROM istrazivanje")
    fun getIstrazivanja():List<Istrazivanje>

    @Query("SELECT * FROM istrazivanje WHERE godina=:god")
    fun getIstrazivanjaPoGodinama(god:Int):List<Istrazivanje>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg istr: Istrazivanje)

    @Query ("DELETE FROM istrazivanje")
    fun deleteIstrazivanja():Unit
}