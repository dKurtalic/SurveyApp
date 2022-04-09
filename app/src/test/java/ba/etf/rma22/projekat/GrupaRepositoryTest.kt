package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.hamcrest.beans.HasPropertyWithValue
import org.hamcrest.core.Is
import org.junit.Assert.assertEquals
import org.junit.Test
import org.hamcrest.CoreMatchers.`is` as Is

class GrupaRepositoryTest {
    @Test
    fun getAllGroupsTest(){
        var grupe = GrupaRepository.getAllGroups()
        assertEquals(grupe.size,10)
        assertThat(grupe, hasItem<Anketa>(HasPropertyWithValue.hasProperty("naziv", Is("Grupa0"))))
        assertThat(grupe, hasItem<Anketa>(HasPropertyWithValue.hasProperty("nazivIstrazivanja", Is("ETF istraživanje"))))
        assertThat(grupe, hasItem<Anketa>(HasPropertyWithValue.hasProperty("naziv", Is("Grupa1"))))
        assertThat(grupe, hasItem<Anketa>(HasPropertyWithValue.hasProperty("nazivIstrazivanja", Is("PMF istraživanje"))))
        assertThat(grupe, hasItem<Anketa>(HasPropertyWithValue.hasProperty("naziv", Is("Grupa5"))))
        assertThat(grupe, hasItem<Anketa>(HasPropertyWithValue.hasProperty("nazivIstrazivanja", Is("Malo istraživanje"))))
    }
    @Test
    fun getGroupsByIstrazivanje(){
        var grupe=GrupaRepository.getGroupsByIstrazivanje("ETF istraživanje")
        assertEquals(grupe.size,2)
        assertThat(grupe, hasItem<Anketa>(HasPropertyWithValue.hasProperty("naziv", Is("Grupa0"))))
        assertThat(grupe, hasItem<Anketa>(HasPropertyWithValue.hasProperty("naziv", Is("Grupa1"))))

        var grupe2=GrupaRepository.getGroupsByIstrazivanje("Veliko istraživanje")
        assertEquals(grupe2.size,1)
        assertThat(grupe2, hasItem<Anketa>(HasPropertyWithValue.hasProperty("naziv", Is("Grupa4"))))
    }

}