
package ba.etf.rma22.projekat

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.UtilTestClass.Companion.hasItemCount
import ba.etf.rma22.projekat.UtilTestClass.Companion.itemTest
import ba.etf.rma22.projekat.UtilTestClass.Companion.itemTestNotVisited
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Matchers.greaterThan
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.CoreMatchers.`is` as Is

@RunWith(AndroidJUnit4::class)
class PocetniTest {

    @get:Rule
    val intentsTestRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    @Test
    fun postojiSveNaPocetnoj() {

        onView(withId(R.id.filterAnketa)).check(matches(isDisplayed()))
        onView(withId(R.id.listaAnketa)).check(matches(isDisplayed()))
        onView(withId(R.id.upisDugme)).check(matches(isDisplayed()))

        var listaOdabira = listOf<String>(
            "Sve moje ankete",
            "Sve ankete",
            "Urađene ankete",
            "Buduće ankete",
            "Prošle ankete"
        )

        for (odabir in listaOdabira) {
            onView(withId(R.id.filterAnketa)).perform(click())
            onData(allOf(Is(instanceOf(String::class.java)), Is(odabir))).perform(click())
        }

    }

    @Test
    fun popuniAnketeGetDone() {

        onView(withId(R.id.filterAnketa)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), Is("Sve moje ankete"))).perform(click())
        onView(withId(R.id.filterAnketa)).check(matches(withSpinnerText("Sve moje ankete")))
        val ankete = AnketaRepository.getMyAnkete()
        onView(withId(R.id.listaAnketa)).check(hasItemCount(ankete.size))
        for (anketa in ankete) {
            itemTest(R.id.listaAnketa, anketa)
        }

    }

    @Test
    fun godineTest() {
        onView(withId(R.id.upisDugme)).perform(click())
        var listaOdabira = listOf<String>("1", "2", "3", "4", "5")
        for (odabir in listaOdabira) {
            onView(withId(R.id.odabirGodina)).perform(click())
            onData(allOf(Is(instanceOf(String::class.java)), Is(odabir))).perform(click())
        }
    }

    @Test
    fun filtriranjeTest(){
        var listaOdabira = listOf<String>(
            "Sve moje ankete",
            "Sve ankete",
            "Urađene ankete",
            "Buduće ankete",
            "Prošle ankete"
        )
        var kolikoAnketa = 0
        for (odabir in listaOdabira) {
            onView(withId(R.id.filterAnketa)).perform(click())
            onData(allOf(Is(instanceOf(String::class.java)), Is(odabir))).perform(click())
            var ankete = emptyList<Anketa>()
            when(odabir){
                "Sve moje ankete" -> ankete= AnketaRepository.getMyAnkete()
                "Sve ankete" -> ankete= AnketaRepository.getAll()
                "Urađene ankete" -> ankete= AnketaRepository.getDone()
                "Buduće ankete" -> ankete= AnketaRepository.getFuture()
                "Prošle ankete" -> ankete= AnketaRepository.getNotTaken()
            }
            kolikoAnketa+=ankete.size
            onView(withId(R.id.listaAnketa)).check(hasItemCount(ankete.size))
            var posjeceni:MutableList<Int> = mutableListOf()
            for (anketa in ankete) {
                itemTestNotVisited(R.id.listaAnketa, anketa,posjeceni)
            }
        }
        val ukupno = AnketaRepository.getAll().size
        kolikoAnketa-=ukupno
        assertThat(kolikoAnketa, allOf(Is(greaterThan(0))))
    }

}



