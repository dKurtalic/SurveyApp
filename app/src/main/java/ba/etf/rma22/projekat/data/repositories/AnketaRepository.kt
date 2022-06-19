package ba.etf.rma22.projekat.data.repositories


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import ba.etf.rma22.projekat.AppDatabase
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnketaRepository {
companion object{
    private lateinit var context: Context
    fun setContext(context: Context){this.context=context}
    fun getContext():Context{return context}
    suspend fun getById(id: Int): Anketa? {
        return withContext(Dispatchers.IO) {
            return@withContext ApiAdapter.retrofit.getAnketaById(id).body()
        }
    }

    suspend fun getAll():List<Anketa>?{
        var database= AppDatabase.getInstance(context)
        return withContext(Dispatchers.IO){
            if (!isOnline(context)){
                Log.v("AnketaRepository","Nisam online")
                var anketeDB=database.anketaDAO().getAll()
                Log.v("AnketaRepository","Velicina: "+anketeDB.size)
                return@withContext anketeDB
            }
            else {
                var anketeServis= getAllFromServis()
                if (anketeServis !=null){
                    for (a in anketeServis) {
                        a.setStatus("")
                        database.anketaDAO().insert(a)
                    }
                }
                return@withContext anketeServis
            }
        }
    }

    suspend fun getAllFromServis(): List<Anketa>? {
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
        var database= AppDatabase.getInstance(context)
        return withContext(Dispatchers.IO) {
            if (!isOnline(context)){
                var anketeDB= database.anketaDAO().getUpisane()
                return@withContext anketeDB
            }
            else {
                var upisaneServer:List<Anketa>?= getUpisaneSaServera()
                if (upisaneServer != null) {
                    for (a1 in upisaneServer) {
                            a1.upisana = 1
                            a1.setStatus("")
                            database.anketaDAO().insert(a1)
                    }
                }
                return@withContext upisaneServer
            }
        }
    }

    suspend fun getUpisaneSaServera():List<Anketa>?{
        return withContext(Dispatchers.IO){
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

    suspend fun getDone():List<Anketa>?{
        var database=AppDatabase.getInstance(context)
        var zavrseneAnkete= mutableListOf<Anketa>()
        return withContext(Dispatchers.IO) {
            if (!isOnline(context)) {
                var sveAnkete = database.anketaDAO().getAll()
                for (a in sveAnkete) {
                    if (a.izracunajStatusAnkete() == "plava") {
                        zavrseneAnkete.add(a)
                        return@withContext zavrseneAnkete
                    }
                }
            } else {
                var ankete = getDoneSaServera()
                return@withContext ankete
            }
            return@withContext null
        }
    }


    suspend fun getDoneSaServera(): List<Anketa>? {
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
    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}

}
