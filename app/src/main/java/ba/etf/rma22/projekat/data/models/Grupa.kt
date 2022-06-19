package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class Grupa (
    @PrimaryKey @SerializedName("id") var id:Int,
    @ColumnInfo(name = "naziv") @SerializedName("naziv") var naziv:String,
    @ColumnInfo(name = "istrazivanjeId") @SerializedName("IstrazivanjeId") var istrazivanjeId: Int,
    var upisana:Int) {
    override fun toString(): String {
        return naziv
    }
    override fun equals(other: Any?): Boolean {
        return other is Grupa && this.naziv == other.naziv && this.istrazivanjeId == other.istrazivanjeId
    }
}