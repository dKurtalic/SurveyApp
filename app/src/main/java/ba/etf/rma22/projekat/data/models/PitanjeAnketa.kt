package ba.etf.rma22.projekat.data.models

import android.util.Log

class PitanjeAnketa(
    var naziv:String, //naziv pitanja
    var anketa:String, // naziv ankete
    var nazivIstrazivanja:String
) {

    private var odgovoreno=false
    private var odgovor:String =""
    private var popunjenaAnketa=false

    fun postaviOdgovor(odg:String){
      if (!odgovoreno)  {
          odgovor=odg
          odgovoreno=true
      }
        Log.v("PitanjeAnketa", "odgovoreno: $odgovoreno, odgovor: $odgovor za pitanje: $naziv")
    }

    fun dajOdgovor():String{
        return odgovor
    }
    fun daLiJeOdgovoreno():Boolean{
        return odgovoreno
    }

    override fun equals(other: Any?): Boolean {
        if (other is PitanjeAnketa)
        {
            return (naziv==other.naziv && anketa==other.anketa && nazivIstrazivanja==other.nazivIstrazivanja)
        }
        return false
    }

}
