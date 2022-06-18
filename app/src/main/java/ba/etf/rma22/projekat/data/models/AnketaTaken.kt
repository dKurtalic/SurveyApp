package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*
/*
id	integer Auto-generated id
student*	string  email studenta
progres*	number procenat odgovorenih pitanja od 0 do 100
datumRada	date    datum rada ankete
 */
@Entity
class AnketaTaken(
    @PrimaryKey@SerializedName("id") var id:Int,
    @ColumnInfo(name="student")@SerializedName("student") var student: String,
    @ColumnInfo(name="progres")@SerializedName("progres") var progres:Double,
    @ColumnInfo(name="datumRada")@SerializedName("datumRada") var datumRada:Date,
    @ColumnInfo(name="AnketumId")@SerializedName("AnketumId") var AnketumId:Int
)