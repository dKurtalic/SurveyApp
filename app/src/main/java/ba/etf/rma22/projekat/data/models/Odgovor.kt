package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
class Odgovor(
    @PrimaryKey@SerializedName("id") var id: Int,
    @ColumnInfo(name="odgovoreno")@SerializedName("odgovoreno") var odgovoreno: Int,
    @ColumnInfo(name="PitanjeId")@SerializedName("PitanjeId") var pitanjeId:Int
) {
}