package ba.etf.rma22.projekat.data.repositories

class AccountRepository {
    companion object{

        var acHash= "57100ef2-8b05-4de5-b21f-215ac5bd2d4c"

    fun postaviHash(acHash:String):Boolean{
        return try {
            this.acHash =acHash
            true
        } catch (e: Exception){
            false
        }
    }
    fun getHash():String{
        return acHash
    }
    }
}