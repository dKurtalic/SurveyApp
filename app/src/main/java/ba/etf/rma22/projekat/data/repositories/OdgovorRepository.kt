package ba.etf.rma22.projekat.data.repositories

import android.provider.Settings
import android.util.Log
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.PitanjeOdgovor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OdgovorRepository {


   suspend fun postaviOdgovorAnketa(idAnketeTaken:Int, idPitanje:Int, odgovor:Int):Int?{
       return withContext(Dispatchers.IO){
           var pokusaji=TakeAnketaRepository.getPoceteAnkete()
           var pokusaj:AnketaTaken? =null
           if (pokusaji != null) {
               for (p in pokusaji){
                   if (p.id==idAnketeTaken){
                       pokusaj=p
                   }
               }
           }
            var getPitanja = PitanjeAnketaRepository.getPitanja(pokusaj!!.anketaId)!!.size
            var getOdgovori =0
           var v =TakeAnketaRepository.getMojiOdgovori(pokusaj.id)
           if (v!=null) getOdgovori= v.size
            var bodovi:Double=0.0
           var stariBodovi:Double=0.0
           bodovi=(((getOdgovori+1).toDouble()/getPitanja))*100
           if (getOdgovori>0) stariBodovi= ((getOdgovori/getPitanja)*100).toDouble()

           var pitanjeOdgovor=PitanjeOdgovor(odgovor,idPitanje,bodovi.toInt())
             var odgovor=ApiAdapter.retrofit.postaviOdgovorAnketa(AccountRepository.getHash(),pokusaj.id,pitanjeOdgovor)
           if (odgovor.code()==200) return@withContext bodovi.toInt()
           else return@withContext -1
        }
   }
    suspend fun getOdgovoriAnketa(idAnkete:Int):List<Odgovor>? {
        var odgovori: List<Odgovor>? = emptyList()
            return withContext(Dispatchers.IO) {
                var odgovarajuciPokusaj: AnketaTaken? = null
                var pokusaji = TakeAnketaRepository.getPoceteAnkete()
                if (pokusaji != null) {
                    for (pokusaj in pokusaji) {
                        if (pokusaj.anketaId == idAnkete) {
                            odgovarajuciPokusaj = pokusaj
                        }
                    }
                }
                var odgovori: List<Odgovor>? = emptyList()
                if (odgovarajuciPokusaj != null) odgovori =
                    TakeAnketaRepository.getMojiOdgovori(odgovarajuciPokusaj.id)
                return@withContext odgovori
         }
        }



}