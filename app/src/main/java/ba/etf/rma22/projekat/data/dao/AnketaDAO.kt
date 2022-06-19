package ba.etf.rma22.projekat.data.dao

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Anketa

@Dao
interface AnketaDAO {
    @Query("SELECT * FROM anketa")
    fun getAll():List<Anketa>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg anketa:Anketa)

    @Query ("SELECT * FROM anketa WHERE upisana = 1")
    fun getUpisane():List<Anketa>

    @Query("DELETE FROM anketa")
    fun obrisiAnkete()

    @Query("SELECT * FROM anketa WHERE id=:id1")
    fun getAnketuSaIdem(id1:Int):Anketa
}