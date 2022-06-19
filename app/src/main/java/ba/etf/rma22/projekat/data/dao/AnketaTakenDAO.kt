package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken


@Dao
interface AnketaTakenDAO {
    @Query("SELECT * FROM AnketaTaken")
    fun getSvePokusaje():List<AnketaTaken>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg anketaTaken: AnketaTaken)
}