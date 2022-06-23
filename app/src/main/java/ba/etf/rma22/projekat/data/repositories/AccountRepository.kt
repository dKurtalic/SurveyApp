package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import ba.etf.rma22.projekat.AppDatabase
import ba.etf.rma22.projekat.data.dao.AccountDAO
import ba.etf.rma22.projekat.data.models.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountRepository {
    companion object {
        private lateinit var context: Context
        var acHash = "57100ef2-8b05-4de5-b21f-215ac5bd2d4c"

        fun setContext(c: Context) {
            context = c
        }

        fun getContext(): Context {
            return context
        }


        fun getHash(): String {
            return acHash
        }


        suspend fun postaviHash(accHash: String): Boolean {
            acHash = accHash
            return withContext(Dispatchers.Main) {
                try {
                    var database = AppDatabase.getInstance(context)
                    try {
                        var account = ApiAdapter.retrofit.getStudent(accHash).body()
                        if (account != null) database.accountDAO().insertAcc(account)
                    } catch (exc: Exception) {
                        var account = Account(0, "student", accHash)
                        database.accountDAO().insertAcc(account)
                    }
                    return@withContext true
                } catch(exc:Exception){
                    return@withContext false
                }
            }
        }
    }
}