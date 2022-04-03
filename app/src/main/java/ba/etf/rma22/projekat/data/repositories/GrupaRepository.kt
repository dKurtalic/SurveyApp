package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.dajListuGrupa
import ba.etf.rma22.projekat.data.models.Grupa

class GrupaRepository {
    fun getAllGroups(): List<Grupa> {
        return dajListuGrupa().ifEmpty { emptyList() }
    }
    fun getGroupsByIstrazivanje (nazivIstrazivanja: String) : List<Grupa> {
        return dajListuGrupa().filter { grupa -> grupa.nazivIstrazivanja==nazivIstrazivanja }
    }
}