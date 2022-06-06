package ba.etf.rma22.projekat.data.repositories

class ApiConfig {
   companion object {
       var baseURL="https://rma22ws.herokuapp.com"
   }


  fun postaviBaseURL(baseUrl1:String){
      baseURL=baseUrl1
  }

    fun getBaseUrl():String{
        return baseURL
    }

}