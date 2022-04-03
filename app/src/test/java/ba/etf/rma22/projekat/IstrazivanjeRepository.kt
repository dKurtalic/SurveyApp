package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.hamcrest.beans.HasPropertyWithValue
import org.junit.Assert.assertEquals
import org.junit.Test
import org.hamcrest.CoreMatchers.`is` as Is

class IstrazivanjeRepository {
    @Test
    fun getAllTest(){
        var istrazivanja=IstrazivanjeRepository.getAll()
        assertEquals(istrazivanja.size,6)
        assertThat(istrazivanja, hasItem<Istrazivanje>(HasPropertyWithValue.hasProperty("naziv", Is("ETF istraživanje"))))
        assertThat(istrazivanja, hasItem<Istrazivanje>(HasPropertyWithValue.hasProperty("godina", Is(1))))
        assertThat(istrazivanja, hasItem<Istrazivanje>(HasPropertyWithValue.hasProperty("naziv", Is("MF istraživanje"))))
        assertThat(istrazivanja, hasItem<Istrazivanje>(HasPropertyWithValue.hasProperty("godina", Is(2))))
        assertThat(istrazivanja, hasItem<Istrazivanje>(HasPropertyWithValue.hasProperty("naziv", Is("Veliko istraživanje"))))
        assertThat(istrazivanja, hasItem<Istrazivanje>(HasPropertyWithValue.hasProperty("godina", Is(3))))
        assertThat(istrazivanja, hasItem<Istrazivanje>(HasPropertyWithValue.hasProperty("godina", Is(4))))
        assertThat(istrazivanja, hasItem<Istrazivanje>(HasPropertyWithValue.hasProperty("godina", Is(5))))
    }
    @Test
    fun getIstrazivanjeByGodinaTest(){
        var istrazivanje1=IstrazivanjeRepository.getIstrazivanjeByGodina(1)
        assertEquals(istrazivanje1.size,2)
        assertThat(istrazivanje1, hasItem<Istrazivanje>(HasPropertyWithValue.hasProperty("naziv", Is("ETF istraživanje"))))
        assertThat(istrazivanje1, hasItem<Istrazivanje>(HasPropertyWithValue.hasProperty("naziv", Is("Malo istraživanje"))))
        assertThat(istrazivanje1, hasItem<Istrazivanje>(HasPropertyWithValue.hasProperty("godina", Is(1))))

        var istrazivanje4=IstrazivanjeRepository.getIstrazivanjeByGodina(4)
        assertEquals(istrazivanje4.size,1)
        assertThat(istrazivanje4, hasItem<Istrazivanje>(HasPropertyWithValue.hasProperty("godina", Is(4))))
        assertThat(istrazivanje4, hasItem<Istrazivanje>(HasPropertyWithValue.hasProperty("naziv", Is("PFSA istraživanje"))))
    }

}