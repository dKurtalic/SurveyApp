package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma22.projekat.AppDatabase
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Odgovor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TakeAnketaRepository {
    companion object{
        lateinit private var context: Context
        fun setContext(c:Context){ this.context =c}
        fun getContext():Context{return context }
        suspend fun zapocniAnketu(idAnkete:Int): AnketaTaken?   {
            return withContext(Dispatchers.IO){
                try {
                    var zapocetaAnketa = getPocetuAnketu(idAnkete)
                    if (zapocetaAnketa == null) {
                        var ank: AnketaTaken =
                            ApiAdapter.retrofit.zapocniAnketu(AccountRepository.getHash(), idAnkete)
                                .body()!!
                        var database=AppDatabase.getInstance(AnketaRepository.getContext())
                        database.anketaTakenDAO().insert(ank)
                        return@withContext ank
                    } else return@withContext zapocetaAnketa
                } catch (e:Exception){
                    return@withContext null
                }
            }
        }



        suspend fun getPoceteAnkete():List<AnketaTaken>?{ //ovo su zapravo pocetiPokusaji
            var database= AppDatabase.getInstance(context)
            return withContext(Dispatchers.IO){
                if (!AnketaRepository.isOnline(context)){
                   /* var poceteAnkete = mutableListOf<AnketaTaken>()

                    var pokusaji= database.anketaTakenDAO().getSvePokusaje()
                    var sveAnkete=database.anketaDAO().getAll()
                    if (sveAnkete!=null && pokusaji!=null){
                        for (i in sveAnkete){
                            for (j in pokusaji){
                                if (i.id==j.AnketumId){
                                    poceteAnkete.add(j)
                                }
                            }
                        }
                    }*/
                        var pocetiPokusaji=database.anketaTakenDAO().getSvePokusaje()

                   return@withContext pocetiPokusaji
                }
                else {
                    var r=ApiAdapter.retrofit.getPoceteAnkete(AccountRepository.getHash()).body()
                    if (r!=null){
                        for (i in r) database.anketaTakenDAO().insert(i)
                    }
                    if(r==null || (r!=null && r.isEmpty())) return@withContext null
                    else return@withContext r
                }
            }
        }
        suspend fun getPocetuAnketu(anketaId:Int):AnketaTaken?{
            return withContext(Dispatchers.IO){

                var zapocetiPokusaji= getPoceteAnkete()
                if (zapocetiPokusaji==null) Log.v("TakeAnketaRepository","pokusaji.size: null")
                else Log.v("TakeAnketaRepository","pokusaji.size: "+zapocetiPokusaji.size)
                var anketa: AnketaTaken? = null
                if (zapocetiPokusaji != null) {
                    for (i in zapocetiPokusaji){
                        if (anketaId==i.AnketumId){
                            anketa=i
                        }
                    }
                }
                if (anketa!=null)Log.v("TakeAnketaRepostory","vraca se "+anketa.AnketumId)
                else Log.v("TakeAnketaRepostory","vraca se null" )
                return@withContext anketa
            }
        }
        suspend fun getMojiOdgovori(pokusaj:AnketaTaken):List<Odgovor>?{
            return withContext(Dispatchers.IO){
                if (!AnketaRepository.isOnline(AnketaRepository.getContext())){
                    var database=AppDatabase.getInstance(AnketaRepository.getContext())
                    var odg=database.odgovorDAO().getOdgovorZaPitanje(pokusaj.AnketumId)
                    return@withContext odg
                }
                else {
                    var rez = ApiAdapter.retrofit.getOdgovoriZaAnketu(AccountRepository.getHash(), pokusaj.id).body()
                    return@withContext rez
                }
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

}