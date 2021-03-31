# Espoon tiedonhallintamallin ylläpitoon Archi-työvälineessä käytettyjä JArchi-skriptejä

Julkaistu avoimena lähdekoodina hyödynnä sellaisena kuin ovat -periaatteella. Emme vastaa
soveltumisesta mihinkään tiettyyn käyttötarkoitukseen emmekä tarjoa tukea. Saa käyttää 
ja muokata sekä edelleen julkaista vapaasti.

## Mitä tarvitset

Tarvitset Archi-työvälineen: https://www.archimatetool.com/

Sekä siihen JArchi-liitännäisen: https://www.archimatetool.com/plugins/#jArchi
Liitännäisen saa käyttöön lahjoittamalla Archin kehittäjälle pienen kuukausimaksun. 
Asennetaan Help > Managa Plug-Ins... toiminnolla, eikä vaadi muuta.
Tämän jälkeen työkaluun tulee näkyviin Properties-osion rinnalle Scripts Manager-osio,
josta voi alkaa muokata skriptejä. Skriptien tallennushakemisto kannattaa muuttaa ensin
Preferences-toiminnolla jonnekin helpommin löydettävään paikkaan. Itse olen muokannut
skriptejä ihan Notepadilla, joka aukeaa oletuksena.

Skriptini käyttävät paria ulkopuolista kirjastoa, joiden oletetaan löytyvän skriptihakemistosi
**~lib**-alihakemistosta:

* date.format.js -kirjasto, joka löytyy https://blog.stevenlevithan.com/archives/date-time-format
* Papa Parse -kirjasto CSV-tiedostojen käsittelyyn, joka löytyy https://www.papaparse.com/

Näiden osalta ei tarvitse muuta kuin ladata kirjastotiedostot **~lib**-hakemistoon.

Käytämme myös coArchi-liitännäistä, jolla malleihin tehdyt muokkaukset voidaan jakaa useamman
käyttäjän kesken. Muutokset päivitetään meillä Github-repositorioon. Github oli jo aiemmin
Espoolla käytössä avoimen lähdekoodin kehitystä varten. coArchi-pluginin voi ladata ilmaiseksi:
https://www.archimatetool.com/plugins/#coArchi

Käyttö vaatii jonkin verran ohjelmointi- tai raportointitaustaa, mutta minusta tässä oli kuitenkin aika 
matala oppimiskäyrä, eikä käyttöni vaatinut mitään sen isompia kehitysympäristöjen asennuksia työasemalle.
Vain itse Archi-sovelluksen asennukseen työasemalleni tarvitsin käyttöpalveluidemme apua 
(järjestelmävalvojan oikeudet).

## Mistä lisäohjetta skriptien muokkaamiseen?

- JArchi-skriptien opastus löytyy täältä: https://github.com/archimatetool/archi-scripting-plugin/wiki/jArchi-API-Overview
- JArchi on ohjelmointikielenä eräs JavaScript-murre, johon olen hankkinut vinkkejä tästä yleisestä JavaScript tutoriaalista:
https://www.w3schools.com/js/DEFAULT.asp 

## Muuta huomioitavaa

- Useimmat näistä skripteistä toimivat Archissa käyttöliittymässä valitulle kohteelle. Valinta voi olla joka 
vasemman laidan navigaatiossa (useamman kohteen voi valita yhtä aikaa shift- tai ctrl-näppäimillä) **tai** 
avoinna olevassa kaaviossa, mutta ei molemmissa yhtä aikaa.

- Skriptin tekemät muutokset voi aina perua Undo-toiminnolla.

- Joidenkin skriptien suoritus kestää, joten kannattaa olla tehokas kone ja/tai kärsivällisyyttä.

- Osan skripteistä, esim. näkymien luonnit, voi suorittaa kokonaisille kansiohierarkioille 
valitsemalla haluamansa ylätason. Esim. meidän yli 500 prosessin kaikkien 360-näkymien generoimisessa 
kestää koneellani sen verran pitkään, että kahvit ehtii hyvin keittää ja juoda. Onneksi tätä ei 
tarvitse tehdä joka päivä.

- Tiedonhallintamalliin kuuluvien elementtien tietorakenne on tietynlainen, eikä siitä voi poiketa ilman,
että skriptejä pitää myös muuttaa. Tietomalli on tarkoitus jossain vaiheessa dokumentoida tarkemmin. Tässä
vaiheessa todettakoon, että tiedonhallintamalliin kuuluvat elementit (Prosessit = business-process, 
Tietovarannot = product, Tietojärjestelmät = application-component, Organisaatioyksiköt = business-actor)
on merkitty siten, että niillä on ominaisuuskentän \_Tiedonhallintamalli arvona "1". Jos tämä arvo puuttuu,
skriptit ohittavat ko. elementin. Toisin sanoen, tiedonhallintamallin voisi ottaa pohjaksi ja jatkaa
tavoitetilan piirtämiseen, jollloin tavoitetilan kohteet erottuvat tiedonhallintamallissa olevista
(tätä emme ole juurikaan vielä testanneet käytännössä).

- Relaatioista liittymiä kuvaavat flow-relaatiot on myös merkitty ominaisuuskentän \_Tiedonhallintamalli 
arvolla "1". Muut relaatiot oletetaan olevan osa tiedonhallintamallia, jos relaation lähde- ja kohde-elementit
kuuluvat tiedonhallintamalliin. Omistus/vastuu mallinnetaan association-relationship:illä, jonka nimi tulee
aina olla "vastaa" (pienet kirjaimet), ja jossa lähde on Organisaatioyksikkö ja kohde on muu tiedonhallintamallin
elementti.

