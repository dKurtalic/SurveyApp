package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.AnketaTaken


@Dao
interface AnketaTakenDAO {
    @Query("SELECT * FROM AnketaTaken")
    fun getSvePokusaje():List<AnketaTaken>
}