package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

class Pitanje(
    @SerializedName("id") var id:Int,
    @SerializedName("naziv")var naziv: String,
    @SerializedName("tekstPitanja") var tekst: String,
    @SerializedName("opcije") var opcije: List<String>
){
    override fun equals(other: Any?): Boolean {
        return other is Pitanje && other.id==this.id
    }
}