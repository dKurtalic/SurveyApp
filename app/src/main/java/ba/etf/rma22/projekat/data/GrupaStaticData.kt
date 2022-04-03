package ba.etf.rma22.projekat.data

import ba.etf.rma22.projekat.data.models.Grupa

fun dajListuGrupa(): List<Grupa> {
    return listOf(
        Grupa("Grupica","Istrazivanje"),
        Grupa("Grupa1", "Istraživanje broj 1"),
        Grupa("Grupa2", "Istraživanje 2"),
        Grupa("Grupa3", "Istrazivanje2"),
        Grupa("Grupa4", "Veliko istrazivanje"),
        Grupa("Grupa5", "Malo istrazivanje")
    )
}