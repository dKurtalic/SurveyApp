package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.dajListuIstrazivanja
import ba.etf.rma22.projekat.data.models.Istrazivanje

class IstrazivanjeRepository {
    fun getAll():List<Istrazivanje>{
       return dajListuIstrazivanja()
    }
    fun getIstrazivanjeByGodina(godina: Int): List<Istrazivanje>{
        return dajListuIstrazivanja().filter { istr->istr.godina==godina }
    }

}