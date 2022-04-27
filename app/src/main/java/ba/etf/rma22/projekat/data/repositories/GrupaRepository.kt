package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.dajListuGrupa
import ba.etf.rma22.projekat.data.models.Grupa
import java.util.ArrayList

object GrupaRepository {
    private var mojeGrupe: ArrayList<Grupa> = arrayListOf()
    init {
        upisiMe("Grupa0")
    }
    fun upisiMe(naziv:String){
        val g:Grupa= getAllGroups().stream().filter { grupa-> grupa.naziv == naziv }.findFirst().get()
        if (!mojeGrupe.contains(g))
         mojeGrupe.add(g)
    }

    fun getAllGroups(): List<Grupa> {
        return dajListuGrupa().ifEmpty { emptyList() }
    }
    fun getGroupsByIstrazivanje (nazivIstrazivanja: String) : List<Grupa> {
        return dajListuGrupa().filter { grupa -> grupa.nazivIstrazivanja==nazivIstrazivanja }.ifEmpty { emptyList() }
    }
}