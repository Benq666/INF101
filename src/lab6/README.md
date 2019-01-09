[Lab 6: Andedam](lab-6)
===

## Læringsmål

* Bli kjent med bruk av arv og abstrakte klasser
* Forstå forskjellen på private og protected

## Om oppgaven

I denne oppgaven skal vi modellere en andedam. Vi skal bruke arv til at felles oppførsel for ender og dam-objekter kan deles i en superklasse, mens spesiell oppførsel havner i subklassene.


# Steg 0: Gjør ferdig tidligere oppgaver

Laboppgavene bør gjøres i rekkefølge, som om du ikke er helt ferdig med den forrige, gjør den ferdig først. (Det er helt normalt å bruke mer enn en uke på en oppgave).

**Hvis du føler deg trygg på arv kan du hoppe over denne oppgaven gå begynne rett på semesteroppgaven – så kan du evt. gjøre den senere som eksamenstrening.**

# Steg 1: Hent Oppgaven fra git

Som før skal du ha et repository for oppgaven på retting.ii.uib.no. Oppgaven skal dere kunne finne i
repositoriet med den følgende urien:

    https://retting.ii.uib.no/<brukernavn>/inf101.v18.lab6.git

## Oversikt over koden

* [inf101.v18.pond](https://retting.ii.uib.no/inf101.v18.oppgaver/inf101.v18.lab6/blob/master/src/inf101/v18/pond/) – kode for andedam 
* [inf101.v18.pond.tests](https://retting.ii.uib.no/inf101.v18.oppgaver/inf101.v18.lab6/blob/master/src/inf101/v18/pond/tests) – tester for andedam 
* [**inf101.v18.pond.Duck**](https://retting.ii.uib.no/inf101.v18.oppgaver/inf101.v18.lab6/blob/master/src/inf101/v18/pond/Duck.java) – kode for ender 
* [**inf101.v18.pond.Frog**](https://retting.ii.uib.no/inf101.v18.oppgaver/inf101.v18.lab6/blob/master/src/inf101/v18/pond/Frog.java) – kode for frosker 
* [**inf101.v18.pond.PondDweller**](https://retting.ii.uib.no/inf101.v18.oppgaver/inf101.v18.lab6/blob/master/src/inf101/v18/pond/PondDweller.java) – abstrakt superklasse 
* [**inf101.v18.pond.PondDemo**](https://retting.ii.uib.no/inf101.v18.oppgaver/inf101.v18.lab6/blob/master/src/inf101/v18/pond/PondDemo.java) – kjørbart program
* [**inf101.v18.pond.AppInfo**](https://retting.ii.uib.no/inf101.v18.oppgaver/inf101.v18.lab6/blob/master/src/inf101/v18/pond/AppInfo.java) – fyll inn info om programmet ditt her
* [inf101.v18.pond.Pond](https://retting.ii.uib.no/inf101.v18.oppgaver/inf101.v18.lab6/blob/master/src/inf101/v18/pond/Pond.java) – kode for andedam 
* [inf101.v18.pond.IPondObject](https://retting.ii.uib.no/inf101.v18.oppgaver/inf101.v18.lab6/blob/master/src/inf101/v18/pond/IPondObject.java) – grensesnitt for ting som kan være i andedammen 
* [inf101.v18.gfx.gfxmode.Point](https://retting.ii.uib.no/inf101.v18.oppgaver/inf101.v18.lab6/blob/master/src/inf101/v18/gfx/gfxmode/Point.java) – klasse for (x,y)-posisjoner
* [inf101.v18.gfx.gfxmode.Direction](https://retting.ii.uib.no/inf101.v18.oppgaver/inf101.v18.lab6/blob/master/src/inf101/v18/gfx/gfxmode/Direction.java) – klasse for retninger 
 

## Steg 2: Helseproblemer
* Koden du har fått utlevert er ca. koden vi har hatt på forelesningene, inkl. at vi nylig introduserte en `PondDweller` klasse, og lot `Duck` og `Frog` arve fra denne – dvs. at de får med seg feltvariablene og metodene fra `PondDweller`.
* Koden har noen kompileringsfeil. Den vil sannsynligvis likevel virke hvis du prøver å kjøre den, men du bør gjerne fikse de først:
   1. Det er én metode (`getHealth()`) som mangler i både `Duck` og `Frog`, denne skal returnere nåværende “helse” til objektet.
   2. Finn ut hvor feltvariablen for `health` befinner seg.
   3. Prøv å legge til en `getHealth()` metode i `Duck` og/eller `Frog`, og la den returnere `health`. Fungerer det, eller får du en feil? Hvorfor?
   4. Fjern `getHealth()`-metoden igjen, og legg den til i `PondDweller` i stedet. Ser du noen forskjell? Hva tror du skjer?
   5. Kjør programmet i `inf101.v18.pond.PondDemo`. Du skal se frosker som hopper og en and. (Avslutt med Ctrl-Q / Cmd-Q)
   6. Prøv å legge til en `getHealth()`-metode i `Frog` eller `Duck` og la den returnere `1`. Hvordan tror du det vil påvirke programmet (spesielt helseindikatoren, den grønne/røde stripen)? Kjør programmet og se hva som skjer.
   7. Hvordan finner `drawHealth()`-metoden ut hva helsenivået er? Kan du endre den til å bruke `getHealth()`-metoden? Hva skjer i såfall om `Frog` har en `getHealth()` som alltid returnerer `1`?

Effektene du opplever kommer av arvemekanismen til Java:
* Hvis en klasse arver fra en annen klasse, får den alle metodene og feltvariablene fra den klassen.
* Metoder/feltvariabler som er `private` i klassen man arver fra (*superklassen*) er ikke tilgjengelige for klassen som arver (*subklassen*).
* Funksjonaliteten (hvilken kode som blir kjørt) velges mens programmet kjører. Hvis en subklasse mangler en metodeimplementasjon, vil Java finne den i superklassen i stedet.

#### Mer om arv
* Kikk på notatene om objekter: [objekter](https://retting.ii.uib.no/inf101/inf101.v18/wikis/objekter) og [FAQ: Objekter og referanser](https://retting.ii.uib.no/inf101/inf101.v18/wikis/objekter-og-referanser).
* Her er en oppsummering om arv (inkl et par avanserte temaer vi ikke har gått gjennom ennå): [arv, forkravt, datainvariant og substitusjonsprinsipper](https://retting.ii.uib.no/inf101/inf101.v18/wikis/arv-forkrav-invariant-substitusjonsprinsippet)

## Steg 3: Forskjellige ender

Vi har to typer objekter på skjermen:

* Brune hunn-ender
* Grønne frosker

Dette er selvfølgelig veldig kjedelig, og vi vil gjerne har litt mer variasjon i endene. F.eks. at vi har små gule andunger, og finpyntede hann-ender (grå med grønne hoder, evt. med flere detaljer). I tillegg vil vi at endene skal oppføre seg litt forskjellig, basert på om de er hannand, hunnand eller andunge. Vi kan gjøre dette på to måter:

1. Bruke `if`-setninger til å gjøre forskjell på dem, f.eks. i `step()`-metoden og `draw()`-metoden.
2. Lage forskjellige klasser for hver form for and, med forskjellig implementasjon av `step()`-metoden.

`if`-setninger er greit hvis vi har få forskjeller i oppførsel: ellers kan det bli voldsomt mange `if`-er, og uleselig kode. Forskjellige klasser er å foretrekke om vi har mange forskjellige varianter eller store forskjeller i oppførsel: da kan vi *modellere* forskjellige typer ender, ikke bare beskrive hvordan programmet skal produsere forskjellige farger. 

Vi skal bruke forskjellige klasser til å beskrive forskjellige ender. Vi ser imidlertid at hannender, hunnender og andunger har mye til felles: vi ønsker ikke å duplisere kode, så vi bruker *arv* til å dele den felles oppførselen i en superklasse. 


#### Duckling utseende
Vi skal begynne med å lage en egen klasse `Duckling`, med utseende og oppførselen til andunger. Gjør følgende:

* Behold klassen `Duck`
* Lag en ny klasse `Duckling extends Duck`. For å justere utseende, kan vi begynne med å endre størrelse og farge. Lag en konstruktør `public Duckling(double x, double y)` hvor du kan gjøre litt justeringer.
   * Aller først, la Duckling-konstruktøren kalle `super(x, y);` – da sørger du for at feltvariablene som er felles for alle ender blir satt opp.
   * Så kan du justere på feltvariablene som styrer utseende (du kan se hva som blir brukt i `Duck.draw()`) – f.eks. kan du sette `size = 0.3`.
   * Fargene `headColor` og `bodyColor` blir satt opp basert på metoden `getPrimaryColor()` (ikke nødvendigvis en smart teknikk, men den illustrerer hvordan `PondDweller` klassen kan kalle en metode og få forskjellig resultat avhengig av om du er frosk, and eller andunge). Kopier koden fra Duck, og sett den til å returnere f.eks. `Color.YELLOW`. Du har nå *overstyrt* `getPrimaryColor()` med din egen implementasjon for andunger.
   * Hvis du vil være mer spesifikk med fargene, kan du sette `headColor` og `bodyColor` selv i konstruktøren.
   * Gå inn in `Pond.setup()` og legg til kode som legger til en (eller flere) andunger; kjør programmet og se hva som skjer.

#### Enkel oppførsel

Oppførselen til `Duck` og `Duckling` er veldig kjedelig – de bare sitter der.

* Finn ut hvor implementasjonen av `step()` er for `Duck` (hint: den er ikke i `Duck`-klassen) og legg til f.eks. `pos = pos.move(dir, 5);`. Hva tror du vil skje nå? **(OBS: merk at det her er viktig å si `pos = pos...` fordi posisjonsobjektene (fra [`Point`-klassen](https://retting.ii.uib.no/inf101.v18.oppgaver/inf101.v18.lab6/blob/master/src/inf101/v18/gfx/gfxmode/Point.java)) er laget slik at de ikke kan endre seg (de er *immutable*) – en ny posisjon gir et nytt objekt, som må lagres i `pos`-feltvariabelen.)**
* Hvis du endret `step()` i `PondDweller` vil du se at både endene og froskene beveger seg mer enn før. Så du bør kanskje heller *overstyre* `step()` metoden, slik at `Duck` får sin egen metode, med `move`.
* For å unngå at endene forsvinner ut av skjermen, kan du flytte dem tilbake når de når kanten; se [`if`-setningene i `Frog.step()`](https://retting.ii.uib.no/inf101.v18.oppgaver/inf101.v18.lab6/blob/master/src/inf101/v18/pond/Frog.java#L98) (sjekker bare om du når bunnen eller høyre side, så du bør kanskje også teste om X og Y er mindre enn 0).
* Hvordan oppfører andungene seg nå?    
Merk at `Duckling`-klassen ikke har egne feltvariabler: alle feltvariablene er arvet fra superklassen `Duck` som har arvet dem fra sin superklasse `PondDweller`. Du har heller ikke en egen `step()`-metode (ennå), så de arver `step()`-implementasjonen fra superklassen.


#### Tilgang til metoder / variabler
Kikk på feltvariablene i `PondDweller`. Hva skjer når du bruker feltvariablene i Duck eller Duckling?

* Feltvariabler og metoder som er merket `private` er ikke direkte tilgjengelig i subklasser. For å bruke de må du enten kalle `public`-metoder (`getHealth()`, f.eks.), eller du må merke feltvariablene som `protected`. 
* `protected` betyr at feltvariabelen/metoden er tilgjengelig for klassen selv, og alle subklasser, samt klasser i samme pakke (mappe). De er fremdeles beskyttet (innkapslet) mot utenforstående klasser. Prøv å endre feltvariablene i PondDweller slik at de er `private`; prøv også `public` og `protected` og ingenting. Sjekk samtidig om du har tilgang til dem fra f.eks. `Pond`-klassen – du kan f.eks. legge inn `new Duck().bodyColor = null;` et sted, og justere tilgangen til bodyColor (prøv gjerne også dette med en klasse som ligger i en annen pakke, f.eks. `DuckTest`-klassen). 
* Generelt vil vi helst at objektene våre er best mulig innkapslet; jo færre steder i programmet en feltvariabel kan leses/skrives, jo færre steder trenger vi å forholde oss til hvis vi skal endre noe eller fikse et problem. Og hvis vi har spesielle forventninger til en feltvariabel, slik som at `0.0 <= health <= 1.0`, så er det dumt om kode fra helt andre steder i programmet kan bryte disse forutsentingene. Så hvis mulig vil vi helst aldri bruke `public` på feltvariabler, og gjerne også unngå å bruke `protected`. Vi kan i stedet legge til `get()`/`set()`-metoder, som f.eks. kan sjekke at verdiene er gyldige.
* Akkurat i dette tilfellet, hvor vi har slike ting som posisjon, retning og farge, som ikke skal tolkes på en spesiell måte, kan det være like greit å bruke `protected`. 

### Forslag til andunge-oppførsel

Du kan velge oppførsel til endene og andungene selv. En mulighet er å få andungen til å følge etter “mamma”:

* La konstruktøren til `Duckling` ta en `Duck` som parameter (eller, mer realistisk, et `IPondObject`, siden andunger er kjent for å *imprinte* på det første de ser, selv om det er noe annet enn moren), og lagre denne som “mammaen”.
* I `step()`-metoden kan du justere retningen til andungen slik at den følger etter moren. Du finner retningen til et annet objekt med `pos.directionTo(mommy.getPosition())`. Du kan lagre retningen direkte i `dir`, eller du kan justere retningen litt saktere med `dir = dir.turnTowards(OTHER_DIRECTION, 5);`.
* Så lenge `Duck` bruker `pos = pos.move(dir, speed)` eller tilsvarende, så er det `dir` som styrer hvor den går.
* For å unngå at de går oppå hverandre, går det an å sjekke avstanden (`pos.distanceTo(mommy.getPosition())`). Det er litt vanskelig å kalkulere hvor nær to objekter kan være før de kolliderer, men du kan f.eks. ta utgangspunkt i halve bredden på dette og det andre objektet.g

### Finne ting
En annen grei oppførsel er å få andungene til å følge etter nærmeste and. Da må du utvide `Pond`-klassen en del, for eksempel med:
* en metode `objects()` som gir deg alle objektene i andedammen, og
* en metode `nearbyObjects(IPondObject obj, double dist)` som gir deg alle objektene i andedammen som har distanse mindre enn `dist` – gjerne i sortert rekkefølge. Du finner eksempler på dette i Semesteroppgave 1.

(Hvis du prøver deg på dette, og finner nære objekter og prøver å følge etter noe som er `instanceof Duck` – kan det da tenkes at en Duckling også kan følge etter en annen Duckling?)

### Litt repetisjon om typer, klasser, grensesnitt og arv

La oss repetere litt av det vi har lært om typer og klasser:

* Typen bestemmer hvilke metoder vi har lov å kalle. Alle endene våre (og froskene) implementerer `IPondObject`-grensesnittet, og vi kan derfor kalle metodene derfra (inkl., f.eks. `step()` som utfører et steg av oppførselen, og `draw()` som tegner på skjermen).
* Klassen gir koden for metodene og feltvariablene, og bestemmer hva som skjer når programmet kjører. For eksempel, hvis vi har to ender, den ene av klassen `MaleDuck` og de andre av klassen `FemaleDuck` (og begge implementerer IPondObject), så kan de gjerne ha forskjellig oppførsel for `step`-metoden, og forsåvidt også forskjellig datastruktur.

Arv gjør at vi kan bygge en ny klasse *B* på en eksisterende klasse *A*. Hvis `B extends A`, så vil det si at:

* Objekter av klassen B har alle metodene fra både A og B. Hvis det er metoder med samme navn og parameterliste (*signatur*), så er det metodeimplementasjonen fra B som blir foretrukket.
* Objekter av klassen B har alle feltvariablene fra både A og B. Hvis det er feltvariabler med samme navn, blir variablene fra B brukt i B og variablene fra A brukt i A.
* Klassen B implementerer alle grensesnitt som A implementerer. Den kan også implementere grensesnitt på egenhånd, f.eks. `B extends A implements I`.
* Konstruktøren til B må kalle konstruktøren til A aller først. Dette er nødvendig fordi alle B-objekter også har feltvariablene til A, og de må settes opp i henhold til A.
* Alle B-objekter er kompatible med typen A, dvs. at du kan bruke dem i variabler med typen A.

## Steg 4: Testing

Du bør lage tester underveis. Til denne typen oppgave passer det med eksempelbaserte tester, f.eks.:

* Se på DuckTest-klassen: der lager vi en `Duck` og kaller `step()`-metoden, og så prøver vi å se hva som skjer (den utleverte koden er antakeligvis feil for din implementasjon).
* En annen ting du kan gjøre er å lage en Pond, `add()`-e en `Duck`, kalle `step()`-metoden til Pond, og se at anden oppfører seg som forventet.
* For å teste andunger, kan du f.eks. gi de et faststående objekt som “mamma”, og se at de nærmer seg for hvert steg.

#### Arvede tester
Vi har gjort et veldig enkelt forsøk på å teste felles oppførsel i klassen `PondObjectProperties`. Her ser du en test som sjekker at helsen går nedover for hver steg. Både `DuckTest` og `FrogTest` har arver fra denne, og implementerer en `generate()`-metode som lager et objekt av klassen som skal testes. Siden `DuckTest` og `FrogTest` arver metoden `healthTest()` blir dette en del av testene, som blir kjørt på lik linje med de vi har implementert i `DuckTest` og `FrogTest`.

Det er gjerne litt begrenset hvor langt man kommer med dette i praksis, når man skal teste oppførsel som er arvet / felles for flere klasser, men det er en mulig teknikk. 

## Steg 5: Mer fiksing
Se gjennom klassene, og se om det er kode som er felles og kan flyttes oppover til en superklasse. Hva med `get()`-metodene, f.eks.?

Generelle råd når du bruker arv for å skille oppførsel – særlig hvis du først har implementert ting uten arv:
* Se på hver `if`-setning, og se om den gjelder forskjeller som like gjerne kan implementeres ved å ha forskjellig kode i forskjellige subklasser.
* Er det noen feltvariabler som bare er relevante for subklassen? I såfall kan de flyttes til subklassen.
* Er det noe kode/feltvariabler som du har i *både* superklassen og subklassen? I såfall bør du vurdere å skrive om slik at felles kode bare er i superklassen.
    * Dette er særlig relevant for `step`-metoden. Her vil du antakelig at alle ender uansett skal ha koden som flytter anden i henhold til fart og retning. Dvs. at du gjerne er i situasjonen at `Duckling` sin `step()`-metode skal gjøre alt som `Duck` gjør + litt til. Dette fikser du ved å legge inne et super-kall til `step`-metoden i superklassen:
```
    @Override
	public void step() {
		super.step(); // kaller step() i superklassen
		// ....
	}
```
* Kjør programmet og se at det virker.
    
## Steg 6: Flere subklasser

* Lag to nye subklasser `MaleDuck` og `FemaleDuck` – eller noe helt annet om du vil. Gjør slik du gjorde for Duckling, og flytt koden som skiller hann-ender og hunn-ender ned i subklassene.
* Du kan selv velge hvordan `step` og `draw` skal være for `MaleDuck` og `FemaleDuck`. F.eks. kan hann-endene oppføre seg som før, mens du har litt ekstra kode for hunn-endene som gjør at de stopper om det ikke er andunger i nærheten (slik at andungene rekker å følge etter). Passende farge for hannender er f.eks. `Color.SILVER` på kroppen og `Color.GREEN` på hodet.
* Legg gjerne til metoder som kan være hendige for å skille endene fra hverandre, f.eks. `isAdult()`, `isMale()`, `isFemale()`. Burde disse evt. være implementert for alle `IPondObject`?
* Endre koden for `setup()` i `PondDemo` slik at den bruker de nye subklassene.
* Kjør programmet og se at det virker.

Du kan gjerne utvide tegningene slik at de blir mer detaljerte.

## Steg 7: Tankearbeid

* En naturlig del av å være andunge er at man (så sant man overlever) etterhvert blir til en voksen and. I Java kan ikke objekter endre klasse underveis – hvordan bør vi håndtere at en andunge blir voksen? (Koden inneholder for øyeblikket ikke noen teller for alder og slikt, så det er i prinsippet ikke noe problem i dette programmet.)
* Vi *kan* løse dette ved å bytte ut andunge-objektene med voksen-and-objekter når de blir voksne – filosofisk sett er det litt uelegant; det er jo fremdeles samme anden, den har bare endret størrelse, utseende og oppførsel.
* En annen mulighet er å skille i flere klasser, en for selve individene (som har posisjon, retning osv), og en for utseende og oppførsel. Hvert individ kan så ha et utseende- og oppførsel-objekt som kan byttes ut senere.
* Dette kan også bety at vi det var en *dårlig* ide å skille ut `Duckling`-oppførselen i en subklasse. Ofte er det vanskelig å vurdere på forhånd hvilke ting som egner seg for arv. Det kan lønne seg å først skrive programmet uten arv, og så eventuelt innføre arv senere når man vet bedre hva man trenger.

## Steg 8: Abstrakte superklasser

En *abstrakt klasse* er en klasse hvor ikke alle metodene er implementert. Det er derfor ikke mulig å lage objekter av klassen (ellers kunne vi risikere at noen kalte en metode som ikke var implementert). Eneste måten å bruke abstrakte klasser på er å *arve* fra dem, og så lage objekter av subklassene. 

Abstrakte klasser er perfekte for situasjoner der du har en del felles oppførsel, men samtidig ikke nok felles oppførsel til at du kan lage fornuftige objekter med *bare* den felles oppførselen. For eksempel kan vi ha tilfellet at alle dyr har en posisjon og en størrelse og `step` og `draw` metoder, uten at det finnes et komplett sett oppførsel som er felles for alle dyr. Alle konkrete dyr vil tilhøre en subklasse som and, torsk eller kanin.

Du gjør en klasse abstrakt ved å putte `abstract` foran `class` i deklarasjonen: `public abstract class Duck`. I den abstrakte klassen kan du ha metoder som ikke er implementert, men som må implementeres i subklassene. Du kan også ha metoder og feltvariabler som er implementert, og som blir arvet til subklassene.

`PondDweller` er et eksempel på en abstrakt superklasse – den inneholder hendige ting for `Duck` og `Frog`, men du kan ikke lage objekter av den, du må alltid lage en subklasse før du kan bruke den til noe.

* Gjør klassen `Duck` abstrakt.
* En del av metodene, som `getPrimaryColor()` og, hvis du har lagt dem til – `isAdult`, `isMale` og `isFemale` kan være abstrakte (e.g., `public abstract boolean isAdult();`.
* La `step`-metoden inneholde bare kode som er felles for alle subklassene (f.eks. koden for å oppdatere posisjon). Du kan la koden i subklassene bygge på dette ved å bruke `super.step();` i subklasse-metodene.
* Du kan gjerne endre navnet på `Duck` klassen (høyreklikk → Refactor →  Rename) til `AbstractDuck` for å indikere at den er abstrakt.
* Hvis du er språknerd, vil du kanskje også gjerne bruke *Refactor*-menyen og endre navn på klassene `FemaleDuck` og `MaleDuck` til det mer passende `Duck` og `Drake`.

## Steg 9: Finn på noe mer gøy

Du kan videreutvikle prosjektet slik du vil. F.eks. går det an å tegne selve dammen også. Noen ideer:

* Legg til vannliljer. Disse vil (kanskje?) være i ro hele tiden – du kan tegne dem som en hvit runding, eller søke litt på nettet om hvordan man tegner pene blomster med turtle graphics.
   * Froskens feltvariabel `jumpVariant` kontrollerer hvor raskt og langt den hopper (sammen med `stepLength` og `speed` i `step()`-metoden. Du kan f.eks. prøve å få den til å hoppe fra vannlilje til vannlilje.
* Froskene har en fancy implementasjon av ben i metoden `drawLeg()`. Den er selvfølgelig beregnet på frosker, men anatomisk sett er har ender og frosker (og alle tetrapoder) stort sett de samme leddene. Dvs. at du kan prøve å kopiere `drawLeg()`-metoden over til `Duck`, kalle den fra `draw()`, og justere den så den ser ut som andeføtter.
   * `jumpStage`-variabelen regulerer leddenes vinkler ut fra hvor frosken er i et skritt/hopp. Du kan sette denne til 0 midlertidig. Vinkelen i leddene er ment å variere etterhvert som denne variabelen endres. For å få fancy bevegelse, lar du den variere mellom 0.0 og 1.0 – ta f.eks. resultatet av `Math.sin(x)` for `x` fra `0` til `Math.PI`, så får du en relativt naturlig bevegelse (frosken har en litt mer komplisert utregning, basert på eksperimentering).
   * Hvis du prøver deg på andeføtter, så er mesteparten av bena skjult under fjærene, “kneet” som du ser er egentlig ankelen.
   * For en virkelig fancy utgave av `drawLeg()`, flytt den opp i en felles abstrakt superklasse (f.eks. PondDweller, eller noe som er felles for `Duck` og `Frog`), og legg til metoder i subklassene som gir riktig lengde og vinkler på knoklene.
