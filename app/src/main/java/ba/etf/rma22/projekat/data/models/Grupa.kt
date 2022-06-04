package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

class Grupa (
    @SerializedName("id") var id:Int,
    @SerializedName("naziv") var naziv:String,
    @SerializedName("IstrazivanjeId") var istrazivanjeId: Int) {
    override fun toString(): String {
        return naziv
    }
    override fun equals(other: Any?): Boolean {
        return other is Grupa && this.naziv == other.naziv && this.istrazivanjeId == other.istrazivanjeId
    }
}