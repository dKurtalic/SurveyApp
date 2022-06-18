package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class Istrazivanje (
    @PrimaryKey@SerializedName("id") var id:Int,
    @ColumnInfo(name="naziv")@SerializedName("naziv") val naziv:String,
    @ColumnInfo(name="godina")@SerializedName("godina") val godina: Int
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