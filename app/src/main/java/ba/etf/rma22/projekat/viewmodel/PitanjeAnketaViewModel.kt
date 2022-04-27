package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository

class PitanjeAnketaViewModel {
    fun getPitanja(nazivAnkete:String,nazivIstrazivanja:String): List<Pitanje> {
        return PitanjeAnketaRepository.getPitanja(nazivAnkete,nazivIstrazivanja)
    }
    fun dajBrojPitanja(nazivAnkete:String,nazivIstrazivanja:String): Int {
        return PitanjeAnketaRepository.dajBrojPitanja(nazivAnkete,nazivIstrazivanja)
    }
    fun dajOdgovorZaPitanje(pitanje: Pitanje,anketa: Anketa): String {
        return PitanjeAnketaRepository.dajOdgovorZaPitanje(pitanje,anketa)
    }
    fun dajMojeOdgovoreZaAnketu(anketa:Anketa): List<PitanjeAnketa> {
        return PitanjeAnketaRepository.dajMojeOdgovoreZaAnketu(anketa)
    }
    fun upisiOdgovor(anketa: Anketa,pitanje: Pitanje,odgovor:String){
        PitanjeAnketaRepository.upisiOdgovor(anketa,pitanje, odgovor)
    }
    fun getMojiOdgovori(): MutableList<PitanjeAnketa> {
        return PitanjeAnketaRepository.getMojiOdgovori()
    }
}