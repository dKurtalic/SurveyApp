package ba.etf.rma22.projekat.data.models

data class Istrazivanje (
    val naziv:String,
    val godina: Int
    )
{
    override fun toString(): String {
        return naziv
    }
    override fun equals(other: Any?): Boolean {
        return other is Istrazivanje && this.naziv == other.naziv && this.godina==other.godina
    }
    override fun hashCode(): Int {
        return super.hashCode()
    }
}