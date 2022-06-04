package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Grupa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.ArrayList

object GrupaRepository {
 /*   private var mojeGrupe: ArrayList<Grupa> = arrayListOf()
    init {
        upisiMe("Grupa0")
    }
    fun upisiMe(naziv:String){
        val g:Grupa= getAllGroups().stream().filter { grupa-> grupa.naziv == naziv }.findFirst().get()
        if (!mojeGrupe.contains(g))
         mojeGrupe.add(g)
    }

  */

    suspend fun getAllGroups(): List<Grupa>? {
       return withContext(Dispatchers.IO){
           var result=ApiAdapter.retrofit.getGrupe()
           return@withContext result.body()
       }
    }
    suspend fun getGroupsByIstrazivanje (id:Int) : List<Grupa>? {
        return withContext(Dispatchers.IO){
            val r=ApiAdapter.retrofit.getGrupeZaIstrazivanje(id)
            return@withContext r.body()
        }
    }
}