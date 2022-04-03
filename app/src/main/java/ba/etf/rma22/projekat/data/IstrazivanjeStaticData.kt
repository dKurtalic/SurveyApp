package ba.etf.rma22.projekat.data

import ba.etf.rma22.projekat.data.models.Istrazivanje

fun dajListuIstrazivanja():List<Istrazivanje> {
    return listOf(
        Istrazivanje("Istrazivanje",1),
        Istrazivanje("Istraživanje broj 1",2),
        Istrazivanje("Istraživanje 2",3),
        Istrazivanje("Istrazivanje2",4),
        Istrazivanje("Veliko istrazivanje",5),
        Istrazivanje("Malo istrazivanje",1)
    )
}