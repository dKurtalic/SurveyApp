package ba.etf.rma22.projekat.data.repositories

class ApiConfig {
   companion object {
       var baseUrl="https://rma22ws.herokuapp.com "
   }

  fun postaviBaseURL(baseUrl1:String){
      baseUrl=baseUrl1
  }

    fun getBaseUrl():String{
        return baseUrl
    }

}