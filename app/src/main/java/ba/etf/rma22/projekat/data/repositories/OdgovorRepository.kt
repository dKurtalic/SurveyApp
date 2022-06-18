package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.PitanjeOdgovor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.ceil

class OdgovorRepository {
companion object{

    lateinit private var context: Context
    fun setContext(c:Context){ this.context =c}
    fun getContext():Context{return context }

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
            var getPitanja = PitanjeAnketaRepository.getPitanja(pokusaj!!.AnketumId)!!.size
            var getOdgovori =0
            var v =TakeAnketaRepository.getMojiOdgovori(pokusaj.id)
            if (v!=null) getOdgovori= v.size
            var bodovi:Double=0.0

            bodovi=(((getOdgovori+1).toDouble()/getPitanja))*100

            bodovi=(((ceil(bodovi/20.0)).toInt())*20).toDouble()


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
                    if (pokusaj.AnketumId == idAnkete) {
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

}