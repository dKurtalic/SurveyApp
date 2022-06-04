package ba.etf.rma22.projekat.data.repositories

import android.util.Log
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Odgovor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object TakeAnketaRepository {
    suspend fun zapocniAnketu(idAnkete:Int): AnketaTaken?   {
        return withContext(Dispatchers.IO){
            try {
                var zapocetaAnketa = getPocetuAnketu(idAnkete)
                Log.v("TakeAnkRepo1", zapocetaAnketa?.id.toString())
                if (zapocetaAnketa == null) {
                    var ank: AnketaTaken =
                        ApiAdapter.retrofit.zapocniAnketu(AccountRepository.getHash(), idAnkete)
                            .body()!!
                    Log.v("TakeAnkRepo2", ank.id.toString())
                    return@withContext ank
                } else return@withContext zapocetaAnketa
            } catch (e:Exception){
                return@withContext null
            }
        }
    }



    suspend fun getPoceteAnkete():List<AnketaTaken>?{
        return withContext(Dispatchers.IO){
            var r=ApiAdapter.retrofit.getPoceteAnkete(AccountRepository.getHash())
            return@withContext r.body()
        }
    }
    suspend fun getPocetuAnketu(anketaId:Int):AnketaTaken?{
        return withContext(Dispatchers.IO){
            var zapocetiPokusaji= getPoceteAnkete()
            var anketa: AnketaTaken? = null
            if (zapocetiPokusaji != null) {
                for (i in zapocetiPokusaji){
                    if (anketaId==i.anketaId){
                        anketa=i
                    }
                }
            }
            return@withContext anketa
        }
    }
    suspend fun getMojiOdgovori(idPokusaja:Int):List<Odgovor>?{
        return withContext(Dispatchers.IO){
            var rez = ApiAdapter.retrofit.getOdgovoriZaAnketu(AccountRepository.getHash(),idPokusaja).body()
            return@withContext rez
        }
    }
    suspend fun daLiJeZapocetaAnketa(idAnkete:Int):Boolean{
        return withContext(Dispatchers.IO){
            var bool=false
            var zapocete= getPoceteAnkete()
            if (zapocete != null) {
                for (i in zapocete){
                    if (i.id==idAnkete) {
                        bool=true
                        break
                    }
                }
            }
            return@withContext bool
        }
    }

}