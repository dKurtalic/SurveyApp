package ba.etf.rma22.projekat.data.repositories


import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
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
                    }
                }
            }

            return@withContext mojeAnk
        }

    }


    suspend fun getDone(): List<Anketa>? {
        return withContext(Dispatchers.IO) {

            var pokusaji=TakeAnketaRepository.getPoceteAnkete()
            var zavrseniPokusaji= mutableListOf<AnketaTaken>()
            var upisaneAnkete= getUpisane()
            var zavrseneAnkete= mutableListOf<Anketa>()
            if (pokusaji != null) {
                for (pokusaj in pokusaji){
                    if (pokusaj.progres==100.0){
                        zavrseniPokusaji.add(pokusaj)
                    }
                }
            }

            for (pokusaj in zavrseniPokusaji){
                if (upisaneAnkete != null) {
                    for (anketa in upisaneAnkete){
                        if (anketa.id==pokusaj.AnketumId){
                            zavrseneAnkete.add(anketa)
                        }
                    }
                }
            }

            return@withContext zavrseneAnkete
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
            return@withContext respond
        }
    }
}
