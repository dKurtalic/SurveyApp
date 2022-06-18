package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Account

@Dao
interface AccountDAO {

    @Query("SELECT * FROM account")
    suspend fun getAccount(): Account

    @Query ("DELETE FROM account")
    fun deleteAccount():Unit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAcc(vararg acc: Account)
}