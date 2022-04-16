package ba.etf.rma22.projekat.data

import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import kotlin.streams.toList


fun dajSvaPitanja():List<Pitanje>{
    return listOf(
        Pitanje("Anketa1_1","Da li ste vegetarijanac/ka?", listOf("Da","Ne")),
        Pitanje("Anketa1_2","Koji je vaš način ishrane?", listOf("Veganski","Vegetarijanski", "Gluten-free", "Sugar-free","Ništa od navedenog")),
        Pitanje("Anketa1_3","Da li mislite da se hranite zdravo?",listOf("Da","Ne")),
        Pitanje("Anketa1_4","Da li ste svjesni negativnih posljedica agrikulture",listOf("Da","Ne")),
        Pitanje("Anketa1_5","Da li je vaša ishrana raznovrsna?",listOf("Da","Ne")),
        Pitanje("Anketa PMF_1", "Koji je stepen vašeg obrazovanja?", listOf("Osnovna škola","Srednja škola","Bachelor","Master","Doktorat")),
        Pitanje("Anketa PMF_2", "Ocijenite svoje znanje iz matematike:", listOf("0","1","2","3","4","5")),
        Pitanje("Anketa PMF_3", "Ocijenite svoje znanje iz hemije:",listOf("0","1","2","3","4","5")),
        Pitanje("Anketa PMF_4", "Podržavate li STEM nastavu?",listOf("Da","Ne")),
        Pitanje("Anketa MF_1", "Da li ste vakcinisani protiv COVID-a?",listOf("Da","Ne")),
        Pitanje("Anketa MF_2", "Da li ste prebolovali COVID?",listOf("Da","Ne")),
        Pitanje("Anketa MF_3", "Da li ste poštovali epidemiološke mjere?",listOf("Da","Ne")),
        Pitanje("Anketa MF_4", "Da li ste tokom pandemije radili od kuće?",listOf("Da","Ne")),
        Pitanje("Anketa PFSA_1", "Smatrate li da ste politički pismeni?",listOf("Da","Ne")),
        Pitanje("Anketa PFSA_2", "Da li ste ikada učestvovali na protestnom skupu?",listOf("Da","Ne")),
        Pitanje("Anketa PFSA_3", "Da li izlazite na izbore?",listOf("Da","Ne")),
        Pitanje("Anketa PFSA_4", "Da li znate koje su vaše građanske dužnosti?",listOf("Da","Ne")),
        Pitanje("Anketa 2_1", "Da li vodite aktivan način života?",listOf("Da","Ne")),
        Pitanje("Anketa 2_2", "Šta vas najviše sprečava da aktivno vježbate?",listOf("Nedostatak vremena", "Nedostatak motivacije", "Nedostatak znanja","Zdravstveni problemi","Ništa od navedenog")),
        Pitanje("Anketa 2_3", "Koliko vremena dnevno provedete za računarom?",listOf("<1h","1h-3h","3h-5h",">5h")),
        Pitanje("Anketa 0_1", "Da li znate osnove programiranja?",listOf("Da","Ne")),
        Pitanje("Anketa 0_2", "Koji je vaš najdraži programski jezik?",listOf("C","C++","Java","Python","Ništa od navedenog")),
        Pitanje("Anketa 0_3", "Kada bi djeca, po Vašem mišljenju, trebala početi učiti programiranje?",listOf("<10 god.","10 - 13 god.","13 - 15 god.","15 - 17 god.",">17 god.")),
        Pitanje("Anketa 0_4", "Koji programski jezik se, po Vašem mišljenju, treba prvi učiti?",listOf("C","C++","Java","Python","Ništa od navedenog")),
        Pitanje("O zagađenju_1", "Da li pazite na zaštitu okoliša?",listOf("Da","Ne")),
        Pitanje("O zagađenju_2", "Da li ste upoznati sa terminom net zero?",listOf("Da","Ne")),
        Pitanje("O online nastavi_1", "Da li ste studirali tokom online nastave?",listOf("Da","Ne")),
        Pitanje("O online nastavi_2", "Da li mislite da je kvalitet nastave bolji uživo ili online?",listOf("online","uživo")),
        Pitanje("O online nastavi_3", "Da li više volite nastavu u učionici ili online?",listOf("online","uživo")),
        Pitanje("O obrazovanju_1", "Koliko po, po Vašem mišljenju, trebao trajati školski čas?",listOf("<25 min","25-35 min","35-45 min","45-55 min")),
        Pitanje("O obrazovanju_2", "Da li mislite da zadaće pomažu učenicima da savladaju gradivo?",listOf("Da","Ne")),
        Pitanje("O obrazovanju_3", "Koliko bi stranih jezika, po Vašem mišljenju trebalo da se uči u školi?",listOf("0","1","2","3","4")),
        Pitanje("Anketa urađena_1", "Da li slušate više stranu ili domaću muziku?",listOf("domaću","stranu")),
        Pitanje("Anketa urađena_2", "Da li svirate ijedan instrument?",listOf("Da","Ne")),
        Pitanje("Aktivna anketa_1", "Koliko država ste posjetili?",listOf("0-3","4-7","8-11","12+")),
        Pitanje("Aktivna anketa_2", "Da li smatrate da je Sarajevo atraktivno turističko odredište?",listOf("Da","Ne")),
        Pitanje("Mala anketa_1", "Da li podržavate zabranu pušenja u zatvorenim prostorima?",listOf("Da","Ne")),
        Pitanje("Mala anketa_2", "Da li ste pušač/ica?",listOf("Da","Ne")),
        Pitanje("Aktivna anketa_1","Koliko stranih jezika govorite?",listOf("0","1","2","3","4","5+")),
        Pitanje("Aktivna anketa_2","Koji je, po Vašem mišljenju, najefikasniji način učenja jezika?",listOf("Slušanje strane muzike", "Učenje gramatike", "Duolingo", "Gledanje stanih video sadržaja")),
        Pitanje("Aktivna anketa_3","Da li ste ikada koristili aplikaciju Duolingo?",listOf("Da","Ne"))
    )

}


