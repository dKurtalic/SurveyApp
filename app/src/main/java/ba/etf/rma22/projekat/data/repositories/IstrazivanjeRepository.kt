package ba.etf.rma22.projekat.data.repositories
/*
import ba.etf.rma22.projekat.data.dajListuIstrazivanja
import ba.etf.rma22.projekat.data.models.Istrazivanje
import kotlin.streams.toList

object IstrazivanjeRepository {

    private var mojaIstrazivanja: ArrayList<Istrazivanje> = arrayListOf()
    private var praznaLista: ArrayList<String> = arrayListOf("")
    init {
        upisiMeNaIstrazivanje("ETF istraživanje")
    }
    fun getMojaIstrazivanja(): ArrayList<Istrazivanje> {
        return mojaIstrazivanja
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
    fun zaSpinner(godina: Int): ArrayList<Istrazivanje> {
        return getAll().stream().filter { istr-> istr.godina.equals(godina)  && !(mojaIstrazivanja.contains(istr)) }.toList().ifEmpty { praznaLista } as ArrayList<Istrazivanje>
    }
    fun getUpisani():List<Istrazivanje>{
        return mojaIstrazivanja
    }

}

 */