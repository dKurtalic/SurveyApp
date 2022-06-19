package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
class PitanjeOdgovor(
    @ColumnInfo(name="odgovor")@SerializedName("odgovor") var odgovor:Int?,
    @SerializedName("pitanje") var pitanje:Int?,
    @SerializedName("progres") var bodovi:Int?
) {
}