package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.dajListuGrupa
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import java.util.ArrayList

class GrupaRepository {
    private var mojeGrupe: ArrayList<Grupa> = arrayListOf()

    fun upisiMe(naziv:String){
        var g:Grupa=getAllGroups().stream().filter {grupa->grupa.naziv.equals(naziv)}.findFirst().get()
        if (!mojeGrupe.contains(g))
        mojeGrupe.add(g)
    }

    fun getAllGroups(): List<Grupa> {
        return dajListuGrupa().ifEmpty { emptyList() }
    }
    fun getGroupsByIstrazivanje (nazivIstrazivanja: String) : List<Grupa> {
        return dajListuGrupa().filter { grupa -> grupa.nazivIstrazivanja==nazivIstrazivanja }
    }
}