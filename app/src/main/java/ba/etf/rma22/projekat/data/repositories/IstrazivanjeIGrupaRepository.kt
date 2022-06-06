package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object IstrazivanjeIGrupaRepository {
    suspend fun getIstrazivanja(): List<Istrazivanje>? {
        var svaIstrazivanja= mutableListOf<Istrazivanje>()
        return withContext(Dispatchers.IO){
            var offset=1
            do {
                var respond : List<Istrazivanje> = ApiAdapter.retrofit.getIstrazivanja(offset).body()!!
                svaIstrazivanja.addAll(respond)
                offset++
            } while(respond!!.isNotEmpty())

            return@withContext svaIstrazivanja
        }
    }
    suspend fun getIstrazivanja(offset:Int):List<Istrazivanje>?{
        return withContext(Dispatchers.IO){
            var respond=ApiAdapter.retrofit.getIstrazivanja(offset).body()
            return@withContext respond
        }
    }
    suspend fun getIstrazivanjaPoGodinama(godina:Int):List<Istrazivanje>?{
        return withContext(Dispatchers.IO){
            var svaIstazivanja= getIstrazivanja()
            var odgovarajuca= mutableListOf<Istrazivanje>()
            for (i in svaIstazivanja!!){
                if (i.godina==godina){
                    odgovarajuca.add(i)
                }
            }
            return@withContext odgovarajuca
        }
    }
    suspend fun getMojaIstrazivanja():List<Istrazivanje>?{
        return withContext(Dispatchers.IO){
            var upisaneGrupe = getUpisaneGrupe()
            var svaIstrazivanja = getIstrazivanja()
            var mojaIstrazivanja = mutableListOf<Istrazivanje>()
            for (i in upisaneGrupe!!){
                for (j in svaIstrazivanja!!){
                    if (i.istrazivanjeId ==j.id){
                        mojaIstrazivanja.add(j)
                    }
                }
            }
            return@withContext mojaIstrazivanja
        }
    }
    suspend fun zaSpinner (godina:Int): List<Istrazivanje>?{
        return withContext(Dispatchers.IO){
            var svaIstrazivanja= getIstrazivanja()
            val zaSpinner = mutableListOf<Istrazivanje>()
            var mojaIstrazivanja= getMojaIstrazivanja()
            for (i in svaIstrazivanja!!){
                if (i.godina==godina && !mojaIstrazivanja!!.contains(i)){
                    zaSpinner.add(i)
                }
            }
            return@withContext zaSpinner
        }
    }
    suspend fun getGrupe(): List<Grupa>? {
        return withContext(Dispatchers.IO){
            var respond=ApiAdapter.retrofit.getGrupe()
            return@withContext respond.body()
        }
    }
    suspend fun getGrupuSIdem(gid:Int):Grupa?{
        return withContext(Dispatchers.IO){
            val respond=ApiAdapter.retrofit.dajGrupuSIdem(gid).body()
            return@withContext respond
        }
    }

   suspend fun getGrupeZaIstrazivanje(idIstrazivanja:Int):List<Grupa>?{
        return withContext(Dispatchers.IO){
           var sveGrupe= getGrupe()
            var odgovarajuceGrupe = mutableListOf<Grupa>()
            if (sveGrupe != null) {
                for (i in sveGrupe){
                    if (i.istrazivanjeId==idIstrazivanja){
                        odgovarajuceGrupe.add(i)
                    }
                }
            }
            return@withContext odgovarajuceGrupe
        }
    }
    suspend fun getIstrazivanjaZaGrupu(gid:Int):List<Istrazivanje>?{
        return withContext(Dispatchers.IO){
            var respond=ApiAdapter.retrofit.getIstrazivanjaZaGrupu(gid).body()
            return@withContext respond
        }
    }


    suspend fun upisiUGrupu(idGrupa:Int):Boolean{
        return withContext(Dispatchers.IO){
            try{
                var respond=ApiAdapter.retrofit.upisiUGrupu(idGrupa,AccountRepository.getHash())
                return@withContext true
            } catch(e:Exception){
                return@withContext false
            }

        }
    }
    suspend fun getUpisaneGrupe():List<Grupa>?{
        return withContext(Dispatchers.IO){
            val resp=ApiAdapter.retrofit.getUpisaneGrupe(AccountRepository.getHash())
            return@withContext resp.body()
        }
    }
}