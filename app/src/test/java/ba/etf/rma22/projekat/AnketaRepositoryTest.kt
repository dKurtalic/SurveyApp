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
        assertEquals(sveAnkete.size,12)
        assertThat(sveAnkete, hasItem<Anketa>(hasProperty("naziv", Is("Anketa 0"))))
        assertThat(sveAnkete, not(hasItem<Anketa>(hasProperty("naziv", Is("Ozbiljna anketa")))))
        assertThat(sveAnkete, hasItem<Anketa>(hasProperty("nazivIstrazivanja", Is("MF istraživanje"))))
    }


    @Test
    fun mojeAnketeTest() {
        val mojeAnkete=AnketaRepository.getMyAnkete()
        assertEquals(mojeAnkete.size,4)
        assertThat(mojeAnkete,hasItem<Anketa>(hasProperty("nazivIstrazivanja", Is("ETF istraživanje"))))
        assertThat(mojeAnkete,hasItem<Anketa>(hasProperty("trajanje", Is(24))))
        assertThat(mojeAnkete,hasItem<Anketa>(hasProperty("nazivGrupe", Is("Grupa0"))))
    }
    @Test
    fun getDoneTest(){
        val doneAnkete=AnketaRepository.getDone()
        assertEquals(doneAnkete.size,1)
        assertThat(doneAnkete,hasItem<Anketa>(hasProperty("nazivIstrazivanja", Is("ETF istraživanje"))))
        assertThat(doneAnkete,hasItem<Anketa>(hasProperty("trajanje", Is(24))))
        assertThat(doneAnkete,hasItem<Anketa>(hasProperty("nazivGrupe", Is("Grupa0"))))

    }
    @Test
    fun getFutureTest(){
        val futureAnkete=AnketaRepository.getFuture()
        assertEquals(futureAnkete.size,1)
        assertThat(futureAnkete,hasItem<Anketa>(hasProperty("nazivIstrazivanja", Is("ETF istraživanje"))))
        assertThat(futureAnkete,hasItem<Anketa>(hasProperty("nazivGrupe", Is("Grupa1"))))
        assertThat(futureAnkete,hasItem<Anketa>(hasProperty("trajanje", Is(100))))
        assertThat(futureAnkete,hasItem<Anketa>(hasProperty("naziv", Is("O zagađenju"))))
    }

    @Test
    fun getNotTakenTest(){
        val notTakenAnkete=AnketaRepository.getNotTaken()
        assertEquals(notTakenAnkete.size,1)
        assertThat(notTakenAnkete, hasItem<Anketa>(hasProperty("naziv",Is("O obrazovanju"))))
        assertThat(notTakenAnkete, hasItem<Anketa>(hasProperty("nazivIstrazivanja",Is("ETF istraživanje"))))
        assertThat(notTakenAnkete, hasItem<Anketa>(hasProperty("nazivGrupe",Is("Grupa0"))))
    }


}