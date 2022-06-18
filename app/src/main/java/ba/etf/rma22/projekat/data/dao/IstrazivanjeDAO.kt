package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Istrazivanje

@Dao
interface IstrazivanjeDAO {
    @Query("SELECT * FROM istrazivanje")
    fun getIstrazivanja():List<Istrazivanje>

    @Query("SELECT * FROM istrazivanje WHERE godina=:god")
    fun getIstrazivanjaPoGodinama(god:Int):List<Istrazivanje>
}