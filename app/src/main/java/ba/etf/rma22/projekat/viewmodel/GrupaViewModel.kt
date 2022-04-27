package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.repositories.GrupaRepository

class GrupaViewModel {
    fun getGroupsByIstrazivanje(nazivIstrazivanja:String):List<Grupa>{
       return GrupaRepository.getGroupsByIstrazivanje(nazivIstrazivanja)
    }
    fun upisiMe(nazivIstrazivanja: String){
       return GrupaRepository.upisiMe(nazivIstrazivanja)
    }
    fun getAllGroups(): List<Grupa> {
        return GrupaRepository.getAllGroups()
    }
}