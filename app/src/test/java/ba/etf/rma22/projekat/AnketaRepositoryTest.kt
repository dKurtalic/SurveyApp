package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.not
import org.hamcrest.beans.HasPropertyWithValue.hasProperty
import org.hamcrest.core.Is
import org.junit.Assert.assertEquals
import org.junit.Test
import org.hamcrest.CoreMatchers.`is` as Is

class AnketaRepositoryTest {
   @Test
    fun testSveAnkete(){
        val sveAnkete = AnketaRepository.getAll()
        assertEquals(sveAnkete.size,6)
        assertThat(sveAnkete, hasItem<Anketa>(hasProperty("naziv", Is("Anketa 0"))))
        assertThat(sveAnkete, not(hasItem<Anketa>(hasProperty("naziv", Is("Ozbiljna anketa")))))
        assertThat(sveAnkete, hasItem<Anketa>(hasProperty("nazivIstrazivanja", Is("MF istraživanje"))))
    }

    //testira da li je korisnik na početku upisan na barem 1 predmet
    @Test
    fun mojeAnketeTest() {
        val mojeAnkete=AnketaRepository.getMyAnkete()
        assertEquals(mojeAnkete.size,1)
        assertThat(mojeAnkete,hasItem<Anketa>(hasProperty("nazivIstrazivanja", Is("ETF istraživanje"))))
        assertThat(mojeAnkete,hasItem<Anketa>(hasProperty("trajanje", Is(24))))
        assertThat(mojeAnkete,hasItem<Anketa>(hasProperty("nazivGrupe", Is("Grupa0"))))
    }
    @Test
    fun getDoneTest(){
        val doneAnkete=AnketaRepository.getDone()
        assertEquals(doneAnkete.size,3)
        assertThat(doneAnkete,hasItem<Anketa>(hasProperty("nazivIstrazivanja", Is("ETF istraživanje"))))
        assertThat(doneAnkete,hasItem<Anketa>(hasProperty("nazivIstrazivanja", Is("MF istraživanje"))))
        assertThat(doneAnkete,hasItem<Anketa>(hasProperty("nazivIstrazivanja", Is("Malo istraživanje"))))
        assertThat(doneAnkete,hasItem<Anketa>(hasProperty("trajanje", Is(15))))
        assertThat(doneAnkete,hasItem<Anketa>(hasProperty("nazivGrupe", Is("Grupa5"))))
        assertThat(doneAnkete,hasItem<Anketa>(hasProperty("nazivGrupe", Is("Grupa2"))))
    }
    @Test
    fun getFutureTest(){
        val futureAnkete=AnketaRepository.getFuture()
        assertEquals(futureAnkete.size,1)
        assertThat(futureAnkete,hasItem<Anketa>(hasProperty("nazivIstrazivanja", Is("PMF istraživanje"))))
        assertThat(futureAnkete,hasItem<Anketa>(hasProperty("nazivGrupe", Is("Grupa1"))))
        assertThat(futureAnkete,hasItem<Anketa>(hasProperty("trajanje", Is(30))))
        assertThat(futureAnkete,hasItem<Anketa>(hasProperty("naziv", Is("Anketa1"))))
    }

    @Test
    fun getNotTakenTest(){
        val notTakenAnkete=AnketaRepository.getNotTaken()
        assertEquals(notTakenAnkete.size,1)
        assertThat(notTakenAnkete, hasItem<Anketa>(hasProperty("naziv",Is("Anketa 2"))))
        assertThat(notTakenAnkete, hasItem<Anketa>(hasProperty("nazivIstrazivanja",Is("PFSA istraživanje"))))
        assertThat(notTakenAnkete, hasItem<Anketa>(hasProperty("nazivGrupe",Is("Grupa3"))))
    }


}