package ba.etf.rma22.projekat.data.models

data class Istrazivanje (
    val naziv:String,
    val godina: Int
){
    override fun toString(): String {
        return naziv
    }

}