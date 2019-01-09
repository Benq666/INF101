# Lab 4 Del I: Social Software Engineering: Git, Reviews, Bugs

*"Muligens den mest relevante laben de kommer til å ha under hele studiet spør du meg"* -- tredjeårsstudent

[Lab 4 Oversikt](LAB-4.md)

## Steg 0: Teit melding
Hvis du får beskjeden "You won't be able to pull or push project code via SSH until you add an SSH key to your profile" på websidene, trykk "Don't show again". Av administrative grunner kan du uansett ikke bruke SSH-nøkkel, så meldingen er overflødig. :(

## Steg 1: Finne en løsning på Lab 2/3

### 1.1 Tilgang
Til denne delen av oppgaven trenger du hjelp fra en eller flere andre studenter. Du kan spørre naboen(e) dine på labben, f.eks., eller noen du kjenner. (Du *kan* evt. gjøre alt på egenhånd, men det gir gjerne litt mindre utbytte.)

På samme måte som i Lab 3, bygger Lab 4 på løsningen fra forrige (bare litt, egentlig: primært er det grid-datastrukturen du trenger). Det vil si at du også denne gang skal merge løsningen fra forrige oppgave; forskjellen nå er at dere skal prøve dere på å sende/motta *merge request* til/fra andre studenter, i stedet for å være alene om jobben. Dette er et betydelig mer realistisk scenario enn forrige gang.

Aller først må du gi de aktuelle andre studentene tilgang til prosjektet ditt:

* Gå til prosjektsiden for *din Lab 4* (`https://retting.ii.uib.no/BRUKERNAVN/inf101.v18.lab4`).
* Gå til *Settings -> Members* på menyen på venstre side.
* I *Add member* tabben, søk opp studentene du skal samarbeide med (du kan taste litt av navnet eller brukernavnet). Du kan velge flere samtidig.
* På "Choose a role permission" velg *Reporter* eller *Developer*. (Du kan [lese om prosjekt-rettigheter her](https://docs.gitlab.com/ce/user/permissions.html). Det tryggeste er kanskje å velge *Developer*, for å unngå eventuelle problemer med begrensninger senere; men i praksis bør *Reporter* (eller til og med *Guest*) være tilstrekkelig. *Guest* vil kunne gjør det du trenger å kunne gjøre her (sende merge request og rapportere bug), men ikke kunne laste ned koden og du vil få en feilmelding underveis fordi du ikke får lov å se merge requesten etter at du har laget den.)
* Trykk *Add to project*
* Gjør det samme med din *Lab 3*.

(Ikke bruk *Share with group*, selv om dere er flere; dette er for en annen type gruppe...)

Dere vil få eposter om at dere har fått tilgang.

### 1.2 New Merge Request
Så må du finne din løsning på Lab 3 (`https://retting.ii.uib.no/BRUKERNAVN/inf101.v18.lab3`), og sende den som *merge request* til en av de andre (hvis dere er flere enn to, kan dere f.eks. la person A sende merge request til B, B sende til C, og C sende til A)

* Hvis du vil, kan du gjerne legge inn en liten ekstra feil i Lab 3 koden din, så blir ting litt mer spennende for de du samarabeider med.
* Lag merge request på samme måte som i Lab 3 (*+ Create new merge request* på forsiden til Lab 3), men i stedet for for å velge *din* Lab 4 som target branch, skal du velge en annen students Lab 4 (dvs. student B om du er student A). New merge request siden skal altså ha: "DittBrukerNavn/inf101.v18.lab3 master" på venstre side og "DenAndresBrukernavn/inf101.v18.lab4 master" på høyre side. Husk at du skal merge *fra Lab 3* (har allerede løsning) *til Lab 4* (mangler løsning).
* Hvis du ser på Lab 4 prosjektet ditt, vil du nå (forhåpentligvis) se at du har "Merge requests: 1" i menyen på venstre side. Du vil også gjerne se et ett-tall på merge request knappen øverst til høyre på siden, ved siden av søkefeltet.
 
## Steg 2: Enkel code review, bug rapporter

### 1.3 Vurdering og merging

Nå skal du se på *din Lab 4* (`https://retting.ii.uib.no/BRUKERNAVN/inf101.v18.lab4`).

Du må kikke på merge requesten som noen har sendt og vurdere om den er "akseptabel". Finn fram til merge requesten (via menyen på venstre side eller knappen øverst til høyre).

Den skal se ut som siden du trykket "Merge" på sist gang. Denne gangen kan du være litt mer kritisk:

* Klikk på Changes, og se gjennom endringene som er gjort
* Du kan legge inn små kommentarer på enkeltlinjer med endringer. Prøv dette.
* Test også diskusjonen, og se hva som skjer. 
* Hvis dere har lagt inn feil til hverandre, prøv å se om dere finner de ved å kikke på koden (neppe særlig enkelt!).
* Test gjerne også ut hva som skjer hvis noen gjør flere endringer i Lab 3 etter å ha sendt merge request.
* Når du er fornøyd, kan du trykke *Merge*.

(I verste fall, eller om du er veldig ukomfortabel med å jobbe videre med andres kode (er egentlig bare Grid-koden du trenger), så kan du "Close merge request" og merge din egen Lab 3 (evt bare kopiere grid-filene).)

### 1.4 Issues

Når du har merget, kan du klone koden på vanlig måte (som i [Del II](LAB-4-II_LABYRINTH.md)) og kikke på den i Eclipse.

* Kjør testene og kjør Brian's Brain og Langton's Ant.
* Se på koden og se hvordan den er forskjellig fra eller liknende til din egen.
* Prøv å lage en bug rapport:
   * Trykk *Issues* i menyen på venstre side på prosjektet på retting.ii.uib.no. Du kan gjøre dette på ditt eget prosjekt, eller på en annen students prosjekt (mer spennende, kanskje).
   * Velg *New Issue*
   * Fyll inn hva du mener er problemet. Enten noe oppdiktet, eller en faktisk feil du har funnet (kanskje noe som er lagt inn med vilje).
   * Du kan gjerne også lage en ny *issue* med en "feature request" om å implementere labyrinten i Del II av denne labben (her kan du f.eks. linke til Del II i forklaringen din). Du kan bruke "Labels" for å skikke forskjellige typer issues fra hverandre. Velg *Labels*, *Manage labels*, *Generate a default set of labels*. "Enhancement" vil f.eks. passe for denne type "fint om du implementerer denne tingen"-forespørsel.
* Test også hvordan du kan [lukke en "issue" ved å committe](https://docs.gitlab.com/ce/user/project/issues/automatic_issue_closing.html). Enten fiks det faktiske problemet som er rapportert, eller bare gjør en endring (hvis dere har rapportert fiktive problemer). Når du committer i Eclipse (evt. direkte på websiden), legg til en setning nederst i commit-meldingen med f.eks. "Fixes #1." (der "1" er nummeret på bugrapporten). Når du har committet og pushet, skal issuen bli stengt av seg selv.


## Steg 3: Flere muligheter

Hvis du/dere er lei av GitLab og bug-rapporter nå, kan du gå videre til [Del II](LAB-4-II_LABYRINTH.md). Ellers har vi noen flere tips til ting dere kan prøve:

* Hvis du finner mulige forbedringer, faktiske feil eller annet så kan du sende en merge request tilbake til de andre med fix for problemet.
* Hvis dere ser på hverandres kode går det også an å se om det er løsninger som er "bedre" (koden er lettere å forstå f.eks.) enn de andre. Så lenge du har rettigheter til andres prosjekter, går det an å spre kode mellom seg med merge requests; enten at du sender til en annen, eller at du lager merge request på ditt eget prosjekt med en annens prosjekt som "source".
* Det går an å gjøre Del II av denne oppgaven "felles" ved at alle kloner og jobber mot det samme repositoriet. Til slutt kan dere bruke merge request for å få alt over på deres egen bruker, slik at det blir sporet i testesystemet vårt.
* Legg også merke til at endringene dere gjør eller overfører til hverandre blir korrekt sporet med hvem som har gjort hva. Dvs. at ditt navn dukker opp på commits i andre sine prosjekter (slik som navnet på den som har laget oppgaven også dukker opp i historikken). I mer avanserte arbeidsflyter vil man gjerne også bruke kryptografisk signering for å være sikker på hvor koden kommer fra og hvem som har godkjent den.
* 



