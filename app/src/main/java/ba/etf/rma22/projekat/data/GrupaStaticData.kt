package ba.etf.rma22.projekat.data

import ba.etf.rma22.projekat.data.models.Grupa

fun dajListuGrupa(): List<Grupa> {
    return listOf(
        Grupa("Grupa0","ETF istraživanje"),
        Grupa("Grupa1", "PMF istraživanje"),
        Grupa("Grupa2", "MF istraživanje"),
        Grupa("Grupa3", "PFSA istraživanje"),
        Grupa("Grupa4", "Veliko istraživanje"),
        Grupa("Grupa5", "Malo istraživanje")
    )
}