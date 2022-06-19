package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
class Pitanje(
    @PrimaryKey( autoGenerate = true) @SerializedName("id") var id:Int,
    @ColumnInfo(name="naziv")@SerializedName("naziv") var naziv: String,
    @ColumnInfo(name="tekstPitanja")@SerializedName("tekstPitanja") var tekstPitanja: String,
    @ColumnInfo(name="opcije")@SerializedName("opcije") var opcije: List<String>
){
    override fun equals(other: Any?): Boolean {
        return other is Pitanje && other.id==this.id
    }
}