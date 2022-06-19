package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import ba.etf.rma22.projekat.AppDatabase
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IstrazivanjeIGrupaRepository {
    companion object{
        private lateinit var context:Context
        fun setContext(c:Context){context=c}
        fun getContext():Context{return context}


        suspend fun getIstrazivanja():List<Istrazivanje>?{
            var database=AppDatabase.getInstance(context)
            return withContext(Dispatchers.IO){
                if (!AnketaRepository.isOnline(context)){
                    return@withContext database.istrazivanjeDAO().getIstrazivanja()
                }
                else{
                   var istr= getIstrazivanjaFromServer()
                    if (istr!=null){
                        for (i in istr) database.istrazivanjeDAO().insert(i)
                    }
                    return@withContext istr
                }
            }
        }
        suspend fun getIstrazivanjaFromServer(): List<Istrazivanje>? {
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

            var database=AppDatabase.getInstance(context)
            database.istrazivanjeDAO().deleteIstrazivanja()
            return withContext(Dispatchers.IO){
                if (!AnketaRepository.isOnline(context)){
                    var istrazivanja=database.istrazivanjeDAO().getIstrazivanja()
                    var vrati= mutableListOf<Istrazivanje>()
                    for (i in 0 until offset-1){
                        vrati.add(istrazivanja[i])
                    }
                    return@withContext vrati
                }
                else {
                    var respond=ApiAdapter.retrofit.getIstrazivanja(offset).body()
                    if (respond!=null){
                        for ( i in respond){
                            database.istrazivanjeDAO().insert(i)
                        }
                    }

                    return@withContext respond
                }

            }
        }
        suspend fun getIstrazivanjaPoGodinama(godina:Int):List<Istrazivanje>?{
            var database=AppDatabase.getInstance(context)
            return withContext(Dispatchers.IO){
                if (!AnketaRepository.isOnline(context)){
                    var istr= database.istrazivanjeDAO().getIstrazivanjaPoGodinama(godina)
                    return@withContext istr
                }
                else {
                    var istr= getIstrazivanjaPoGodinamaFromServer(godina)
                    if (istr!=null){
                        for ( i in istr){
                            database.istrazivanjeDAO().insert(i)
                        }
                    }
                    return@withContext istr
                }
            }
        }
        suspend fun getIstrazivanjaPoGodinamaFromServer(godina:Int):List<Istrazivanje>?{
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
            var database=AppDatabase.getInstance(context)
            return withContext(Dispatchers.IO){
                if (!AnketaRepository.isOnline(context)) {
                    var grupe=database.grupaDAO().getAllGrupe()
                    return@withContext grupe
                }
                else {
                    var respond = ApiAdapter.retrofit.getGrupe().body()
                    if (respond != null) {
                        for (i in respond){
                            database.grupaDAO().insertGrupa(i)
                        }
                    }

                    return@withContext respond
                }
            }
        }
        suspend fun getGrupuSIdem(gid:Int):Grupa?{
            return withContext(Dispatchers.IO){
                val respond=ApiAdapter.retrofit.dajGrupuSIdem(gid).body()
                return@withContext respond
            }
        }

        suspend fun getGrupeZaIstrazivanje(idIstrazivanja:Int):List<Grupa>?{
            var database=AppDatabase.getInstance(context)
            var odgovarajuceGrupe = mutableListOf<Grupa>()
            return withContext(Dispatchers.IO){
                if (!AnketaRepository.isOnline(context)) {
                    odgovarajuceGrupe=database.grupaDAO().dajGrupeZaIstrazivanje(idIstrazivanja).toMutableList()
                    return@withContext odgovarajuceGrupe
                }
                else {
                    var sveGrupe= getGrupe()
                    if (sveGrupe != null) {
                        for (i in sveGrupe){
                            if (i.istrazivanjeId==idIstrazivanja){
                                odgovarajuceGrupe.add(i)
                            }
                        }
                    }
                    if (sveGrupe != null) {
                        for (i in sveGrupe)
                            database.grupaDAO().insertGrupa(i)
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
                    var database= AppDatabase.getInstance(AnketaRepository.getContext())
                    ApiAdapter.retrofit.upisiUGrupu(idGrupa,AccountRepository.getHash())
                    var grupa= getGrupuSIdem(idGrupa)
                    if (grupa != null) {
                        grupa.upisana=1
                        database.grupaDAO().insertGrupa(grupa)
                        var anketeZaGrupu=AnketaRepository.anketeZaGrupu(idGrupa)
                        if (anketeZaGrupu != null) {
                            for (i in anketeZaGrupu){
                                i.upisana=1
                                database.anketaDAO().insert(i)
                            }
                        }
                    }

                    return@withContext true
                } catch(e:Exception){
                    return@withContext false
                }

            }
        }
        suspend fun getUpisaneGrupe():List<Grupa>?{
            var database=AppDatabase.getInstance(context)
            return withContext(Dispatchers.IO){
                if (!AnketaRepository.isOnline(context)) {
                    var grupe=database.grupaDAO().getUpisane()
                    return@withContext grupe
                }
                else {
                    val resp = ApiAdapter.retrofit.getUpisaneGrupe(AccountRepository.getHash()).body()
                    if (resp!=null){
                        for ( i in resp){
                            i.upisana=1
                            database.grupaDAO().insertGrupa(i)
                        }
                    }
                    return@withContext resp
                }
            }
        }

    }

}