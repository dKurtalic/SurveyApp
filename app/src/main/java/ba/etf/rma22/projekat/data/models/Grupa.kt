package ba.etf.rma22.projekat.data.models

class Grupa (
    val naziv:String,
    val nazivIstrazivanja: String) {
    override fun toString(): String {
        return naziv
    }

    override fun equals(other: Any?): Boolean {
        return other is Grupa && this.naziv.equals(other.naziv) && this.nazivIstrazivanja.equals(other.nazivIstrazivanja)
    }
}