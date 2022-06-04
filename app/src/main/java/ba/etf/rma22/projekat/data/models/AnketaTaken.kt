package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName
import java.util.*
/*
id	integer Auto-generated id
student*	string  email studenta
progres*	number procenat odgovorenih pitanja od 0 do 100
datumRada	date    datum rada ankete
 */
class AnketaTaken(
    @SerializedName("id") var id:Int,
    @SerializedName("student") var student: String,
    @SerializedName("progres") var progres:Double,
    @SerializedName("datumRada") var datumRada:Date,
    @SerializedName("AnketumId") var anketaId:Int
) {

}