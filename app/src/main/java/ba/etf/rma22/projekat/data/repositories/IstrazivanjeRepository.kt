package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.dajListuIstrazivanja
import ba.etf.rma22.projekat.data.models.Istrazivanje
import java.util.stream.Collector
import kotlin.streams.toList

object IstrazivanjeRepository {

    private var mojaIstrazivanja: ArrayList<Istrazivanje> = arrayListOf()

    init {
        upisiMeNaIstrazivanje("ETF istraživanje")
    }
    fun getAll():List<Istrazivanje>{
       return dajListuIstrazivanja()
    }
    fun getIstrazivanjeByGodina(godina: Int): List<Istrazivanje>{
        return dajListuIstrazivanja().filter { istr->istr.godina==godina }
    }
    fun upisiMeNaIstrazivanje(nazivIstrazivanja:String){
        var i:Istrazivanje = getAll().stream().filter { istr->istr.naziv.equals(nazivIstrazivanja) }.findFirst().get()
        if (!mojaIstrazivanja.contains(i)) mojaIstrazivanja.add(i)
    }
    fun istrazivanjaNaKojaNisamUpisana():ArrayList<Istrazivanje>{
        return getAll().stream().filter { istr -> !(mojaIstrazivanja.contains(istr))}.toList() as ArrayList<Istrazivanje>
    }

}