**Aihe:** Skrapple - suomenkielinen Scrabble-klooni

Kahden pelaajan suomenkielinen [Scrabble](https://fi.wikipedia.org/wiki/Scrabble)-klooni. Sanasto kotukselta. Ohjelmassa voisi olla mukana myös esim. all-time high-score, tai pelien tallentamisominaisuus mikäli kerkeää toteuttamaan. Peliä pelataan nk. "[hotseat](https://fi.wikipedia.org/wiki/Hotseat)"-moninpelimuodolla, ei siis internetin kautta.

**Käyttäjät:** Kaksi (ihmis)pelaajaa

**Kaikkien käyttäjien toiminnot:**
* uusi peli
  * kysytään varmistus, mikäli edellinen peli on jo käynnissä
  * Scrabblen virallisten sääntöjen mukaan jokainen pelaaja nostaa kirjainpussista yhden kirjaimen ja lähimmäksi A:ta päässyt pelaaja aloittaa
    * ohjelmassa mahdollisesti Pelaaja 1 aloittaa aina

* pelin pelaaminen
  * pelaa vuoro
    * aseta kirjaimet pelilaudalle
      * epäonnistuu, mikäli kirjaimet on asetettu sääntöjen vastaisesti
      * peli pisteyttää siirron
        * epäonnistuu, mikäli sanaa/sanoja ei löydy sanastosta
        * joka kirjaimella oma pistemäärä
        * bonusruuduista lisäpisteitä
    * nosta max 7 kirjainta kirjainpussista kirjaintelineelle
      * epäonnistuu, mikäli kirjainpussissa ei ole kirjaimia
      * tapahtuu luultavasti automaattisesti pelaajan pelattua vuoron
    * vaihda 1-7 kirjainta
      * onnistuu vain, jos kirjaimia ei ole pelattu vuoron aikana
      * 0 pistettä
    * luovuta
      * toinen pelaaja voittaa pelin
  * päätä vuoro
    * mahdollista myös ilman että on ensin pelannut vuoroa, tällöin 0 pistettä
  * pelin päättyminen
    * Scrabblen virallisten sääntöjen mukaan peli päättyy, kun:
      * 1) kaikki kirjainlaatat on nostettu ja yksi pelaaja on käyttänyt kaikki laattansa
      * 2) jos mitään ei ole enää mahdollista tehdä
      * 3) jos kaikki pelaajat jättävät vuoronsa väliin kaksi kertaa peräkkäin
    * ohjelma noudattaa ainakin sääntöä 1), mahdollisesti toiminto, jolla pelaajat voivat sopia keskenään pelin päättämisestä
  * lopeta

**Toiminnot, jotka toteutetaan mikäli aikaa riittää:**

* pelin tallentaminen
  * käyttäjältä kysytään, minne peli tallennetaan, käyttäjä tallentaa tiedoston
* all-time top 10 high-score
  * käyttäjä avaa listan parhaista pisteistä ja voi tarkastella sitä
  * päivittyy automattisesti pelin päätyttyä

**Käyttöohjeet (englanniksi):**

[Manual.pdf](Manual.pdf) 

**Rakennekuvaus:**

Kun aloitetaan uusi peli, luodaan uusi luokka SkrappleGame, jonka UI ottaa konstruktorina, ja joka toimii linkkinä UI:n ja logiikan välillä. SkrappleGame luo uuden luokan Game parametrinaan kaksi pelaajaa ja sanakirja, jota käytetään pelissä. Luokasta Game voidaan gettereillä saada kaikki pelaamiseen tarvittavat loput luokat.

Käyttäjä asettaa kirjaimia pelilaudalle LetterQueuen kautta. LetterQueue on luokka, joka lisää kirjaimia omaan jonoonsa, muttei vielä pelilaudalle. Ennen jonoon laittamista LetterQueueChecks tarkistaa voiko kirjainta asettaa jonoon (sääntöjen mukaisesti sanan pitää voida koskettaa edellistä sanaa). Jonon käyttämisestä on se hyöty, että jonossa olevat kirjaimet voidaan perua ennen lopullista siirtoa, mikäli käyttäjä muuttaa mieltänsä.

Kun käyttäjä haluaa tehdä siirron (Move), tarkistaa LetterQueueValidator ensin Neighboursin avulla, että kirjainjono on ehjä ja että vähintään yksi kirjain jonossa koskettaa laudalla jo valmiina ollutta kirjainlaattaa. Sitten WordCreator luo kirjaimista sanoja ja WordChecker tarkistaa sanakirjan (Dictionary) avulla että sanat ovat oikeita sanoja.

Jos sanat hyväksytään, kirjaimet asetetaan pelilaudalle (Board). Sanat pisteytetään (jokainen sana pitää sisällään oman pistemääränsä) ja pisteet annetaan pelaajalle. Pelaajan kirjainteline (Rack) hakee pelaajalle uudet kirjaimet kirjainpussista (LetterBag). Pelaaja voi sitten vaihtaa vuoroa katsottuaan ensin saadut uudet kirjaimet (EndTurn).

Pelin päätyttyä SkrappleGame miinustaa tarvittaessa kirjaintelineelle jääneiden kirjainten pistemäärän ja julistaa voittajan.
  
**Luokkakaavio:**
  
![Luokkakaavio](https://yuml.me/09e96321)
  
**Sekvenssikaavioita:**
  
*Kirjaimen lisääminen jonoon*
  
![Lisää kirjain jonoon](https://i.imgur.com/emLTbv5.png)
  
*Kokonaisen siirron tekeminen*
  
![Tee siirto](https://i.imgur.com/w59Llzj.png)
