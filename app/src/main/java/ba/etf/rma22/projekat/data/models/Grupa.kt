package ba.etf.rma22.projekat.data.models

class Grupa (
    val naziv:String,
    val nazivIstrazivanja: String) {
    override fun toString(): String {
        return naziv
    }
}