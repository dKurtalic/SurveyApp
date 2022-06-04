package ba.etf.rma22.projekat.data.repositories

import android.util.Log
import ba.etf.rma22.projekat.data.models.Anketa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AnketaRepository {

    suspend fun getById(id: Int): Anketa? {
        return withContext(Dispatchers.IO) {
            return@withContext ApiAdapter.retrofit.getAnketaById(id).body()
        }
    }

    suspend fun getAll(): List<Anketa>? {
        return withContext(Dispatchers.IO) {
            var offset = 1
            var sveAnkete = mutableListOf<Anketa>()
            do {
                var ankete = ApiAdapter.retrofit.getAll(offset).body()
                if (ankete != null) {
                    sveAnkete.addAll(ankete)
                }
                offset++
            } while (ankete!!.isNotEmpty())
            return@withContext sveAnkete
        }
    }
    suspend fun getAll(offset:Int):List<Anketa>?{
        return withContext(Dispatchers.IO){
            var ankete=ApiAdapter.retrofit.getAll(offset).body()
            return@withContext ankete
        }
    }

    suspend fun getUpisane(): List<Anketa>? {
        return withContext(Dispatchers.IO) {
            var upisaneGrupe = ApiAdapter.retrofit.getUpisaneGrupe(AccountRepository.getHash()).body()
            var mojeAnk = mutableListOf<Anketa>()
            if (upisaneGrupe != null) {
                for (i in upisaneGrupe) {
                    var privremene = anketeZaGrupu(i.id)
                    if (privremene != null) {
                        mojeAnk.addAll(privremene)
                        Log.v("AnketaRepository","dodalo se "+privremene.size.toString())
                    }
                }
            }
            Log.v("AnketaRepo", mojeAnk.size.toString())
            /* var listaPokusaja=ApiAdapter.retrofit.getPoceteAnkete(AccountRepository.getHash()).body()
            var sveAnkete=getAll()
            var mojeAnkete= mutableListOf<Anketa>()
            if (listaPokusaja != null) {
                for (i in listaPokusaja){
                    if (sveAnkete != null) {
                        for (j in sveAnkete){
                            if (i.id==j.id){
                                mojeAnkete.add(j)
                            }
                        }

                    }
                }
            }

            */
            return@withContext mojeAnk
        }

    }


    suspend fun getDone(): List<Anketa>? {
        return withContext(Dispatchers.IO) {
            var mojeAnkete = getUpisane()
            var doneAnkete = mutableListOf<Anketa>()
            if (mojeAnkete != null) {
                for (i in mojeAnkete) {
                    if (i.dajStatusAnkete() == "plava") {
                        doneAnkete.add(i)
                    }
                }
            }
            return@withContext doneAnkete
        }
    }

    suspend fun getFuture(): List<Anketa>? {
        return withContext(Dispatchers.IO) {
            var mojeAnkete = getUpisane()
            var futureAnkete = mutableListOf<Anketa>()
            if (mojeAnkete != null) {
                for (i in mojeAnkete) {
                    if (i.dajStatusAnkete() == "zuta") futureAnkete.add(i)
                }
            }
            return@withContext futureAnkete
        }
    }

    suspend fun getNotTaken(): List<Anketa>? {
        return withContext(Dispatchers.IO) {
            var mojeAnkete = getUpisane()
            var notTakenAnkete = mutableListOf<Anketa>()
            if (mojeAnkete != null) {
                for (i in mojeAnkete) {
                    if (i.dajStatusAnkete() == "crvena") notTakenAnkete.add(i)
                }
            }
            return@withContext notTakenAnkete
        }
    }

    suspend fun anketeZaGrupu(gid: Int): List<Anketa>? {
        return withContext(Dispatchers.IO) {
            var respond = ApiAdapter.retrofit.getAnketeZaGrupu(gid).body()
            Log.v("AnketaRepository", "ankete za grupu sa gid $gid, vel: "+respond?.size.toString())
            return@withContext respond
        }
    }
}
/*

   suspend fun upisiMe(istrazivanje: String, grupa: String){
       val a = getAll().stream().filter { ank -> ank.nazivIstrazivanja == istrazivanje &&
               ank.nazivGrupe == grupa }.toList()
       for (ank in a){
       if (!mojeAnkete.contains(ank))
            mojeAnkete.add(ank)
       }
   }

     fun getMyAnkete():List<Anketa>{
        var konacnaLista= mutableListOf<Anketa>()
        var sveAnkete= listOfAllSurveys
        for (i in sveAnkete){
            for (j in mojeAnkete){
                if (i.naziv==j.naziv && i.nazivIstrazivanja==j.nazivIstrazivanja)
                    konacnaLista.add(i)
            }
        }
      return konacnaLista.ifEmpty { emptyList()}
    }*/