package ba.etf.rma22.projekat

import android.os.Message
import ba.etf.rma22.projekat.data.models.*
import ba.etf.rma22.projekat.data.models.AnketaTaken
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @GET("/anketa")
    suspend fun getAll(@Query("offset") offset:Int = 1): Response<List<Anketa>>

    @GET("/anketa{id}")
    suspend fun getAnketaById(@Path("id") id:Int): Response<Anketa>

    @GET("/anketa/{id}/pitanja")
    suspend fun getPitanja(@Path("id") id:Int): Response<List<Pitanje>>

    @GET("/istrazivanje")
    suspend fun getIstrazivanja(@Query("offset") offset: Int): Response<List<Istrazivanje>>

    @GET("/grupa")
    suspend fun getGrupe(): Response<List<Grupa>>

    @GET("/grupa/{id}")
    suspend fun dajGrupuSIdem(@Path("id") gid:Int):Response<Grupa>

    @GET("/student/{id}")
    suspend fun getStudent(@Path("id") id:String): Response<Account>

    @POST("/student/{id}/anketataken/{ktid}/odgovor")
    suspend fun postaviOdgovorAnketa(@Path("id") hashStudenta: String,@Path("ktid") ktid:Int, @Body odgovor:PitanjeOdgovor): Response<Odgovor>

    @GET("/grupa/{gid}/istrazivanje")
   suspend fun getGrupeZaIstrazivanje (@Path("gid") id:Int): Response<List<Grupa>>

    @GET("/student/{id}/anketataken")
    suspend fun getPoceteAnkete (@Path("id") hashStudenta: String): Response<List<AnketaTaken>>

    @POST("/student/{id}/anketa/{kid}")
    suspend fun zapocniAnketu(@Path("id") hashStudenta:String, @Path("kid") idAnkete:Int): Response<AnketaTaken>

    @POST("/grupa/{gid}/student/{id}")
    suspend fun upisiUGrupu (@Path("gid") gid:Int, @Path("id") id:String): Message

    @GET("/student/{id}/grupa")
    suspend fun getUpisaneGrupe(@Path("id") hashStudenta: String ): Response<List<Grupa>>

    @GET("/grupa/{id}/ankete")
    suspend fun getAnketeZaGrupu(@Path("id") idGrupe: Int ): Response<List<Anketa>>

    @GET("/student/{id}/anketataken/{ktid}/odgovori")
    suspend fun getOdgovoriZaAnketu(@Path("id") hashStudenta:String, @Path("ktid") pokusaj:Int ) :Response<List<Odgovor>>

    @GET("/grupa/{gid}/istrazivanje")
    suspend fun getIstrazivanjaZaGrupu(@Path("id") gid:Int):Response<List<Istrazivanje>>

    @DELETE("/student/{id}/upisugrupeipokusaji")
    suspend fun obrisiStudenta(@Path("id") hashStudenta: String):Message

}


