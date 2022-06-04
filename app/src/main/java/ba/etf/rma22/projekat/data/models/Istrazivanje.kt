package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

data class Istrazivanje (
    @SerializedName("id") var id:Int,
    @SerializedName("naziv") val naziv:String,
    @SerializedName("godina") val godina: Int
    )
{
    override fun toString(): String {
        return naziv
    }
    override fun equals(other: Any?): Boolean {
        return other is Istrazivanje && this.naziv == other.naziv && this.godina==other.godina && this.id==other.id
    }
    override fun hashCode(): Int {
        return super.hashCode()
    }
}