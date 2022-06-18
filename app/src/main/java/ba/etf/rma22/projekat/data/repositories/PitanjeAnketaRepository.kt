package ba.etf.rma22.projekat.data.repositories


import android.content.Context
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.withContext
import kotlin.streams.toList

class PitanjeAnketaRepository {
companion object{
    lateinit private var context: Context
    fun setContext(c:Context){ this.context =c}
    fun getContext():Context{return context }
    suspend fun getPitanja(idAnkete:Int):List<Pitanje>{
        return withContext(Dispatchers.IO){
            var rez=emptyList<Pitanje>()
            var r= ApiAdapter.retrofit.getPitanja(idAnkete).body()
            if (r!=null) rez=r
            return@withContext rez
        }
    }

    suspend fun dajBrojPitanja(idAnkete:Int):Int{
        return getPitanja(idAnkete)!!.size
    }

    suspend fun dajOdgovorZaPitanje(pitanje:Pitanje,anketa:Anketa,pokusaj: AnketaTaken):Int? {
        var pitanja=getPitanja(anketa.id)
        var odgovori=TakeAnketaRepository.getMojiOdgovori(pokusaj.id)
        var indeksPitanje=0
        var p=pitanje
        var pitanje: Pitanje?=null
        for (i in pitanja!!){
            if (i.equals(p)){
                pitanje=i
                break
            }
            indeksPitanje++
        }
        var odgovor=-1
        if (odgovori!=null){
            for (i in odgovori){
                if (i.pitanjeId==p.id)
                    odgovor=i.odgovoreno
            }
        }
        if (odgovor!=-1)  return odgovor
        else return null
    }
    /* fun dajOdgovorZaPitanje(pitanje:Pitanje, anketa:Anketa):String{
         val nazivPitanja=pitanje.naziv
         val nazivAnkete=anketa.naziv
         val nazivIstrazivanja=anketa.nazivIstrazivanja
         val pom= mojiodgovori.stream().filter { p -> p.naziv==nazivPitanja && p.nazivIstrazivanja==nazivIstrazivanja
                 && p.anketa==nazivAnkete}.findFirst()
         return if( pom.isPresent){
             pom.get().dajOdgovor()
         } else ""
      }

     fun dajMojeOdgovoreZaAnketu(anketa:Anketa):List<PitanjeAnketa>{
         val nazivAnkete=anketa.naziv

         return mojiodgovori.stream().filter { pa -> pa.anketa==nazivAnkete }.toList()
      }

     */

}


}
