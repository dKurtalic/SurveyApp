package ba.etf.rma22.projekat.data

import ba.etf.rma22.projekat.data.models.Istrazivanje

fun dajListuIstrazivanja():List<Istrazivanje> {
    return listOf(
        Istrazivanje("ETF istraživanje",1),
        Istrazivanje("PMF istraživanje",2),
        Istrazivanje("MF istraživanje",3),
        Istrazivanje("PFSA istraživanje",4),
        Istrazivanje("Veliko istraživanje",5),
        Istrazivanje("Malo istraživanje",1)
    )
}