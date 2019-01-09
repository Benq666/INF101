# Lab 4 Del II: “aMazing Labyrinth”

[Lab 4 Oversikt](LAB-4.md)

## Om oppgaven

I lab-oppgaven denne uken skal vi forbedre [labyrintoppgaven fra
Semesteroppgave 3](src/inf100/h16/sem3/semoppg3.pdf) i INF100 Høst 2016. I denne oppgaven skulle studentene
lage et lite program som leser inn en labyrint fra en fil og så
lar en spiller navigere den.

Labyrinten er lagret i en tekst fil der karakteren `"*"` er en vegg, `" "` (mellomrom) er en åpen plass som spilleren kan gå igjenom, og `"s"` er startposisjonen til spilleren. Vi forenkler innlesingen litt, men du kan legge til innlesing fra fil selv om du vil. I den opprinnelige oppgaven blir spilleren bedt om hvilken retning (nord, sør, øst og vest) han vil gå i, og blir vist hvor han er på kartet – vi skal i stedet bruke (veldig forenklet) “events” til å håndtere tastetrykkene.

## Forbedringer

Du kan [se den opprinnelige oppgaven og løsningsforslaget her](src/inf100/h16/sem3/). Det kan være nyttig å sette seg litt inn i den koden, bare for å forskjellen i hvordan man løser problemer med enkel programmering (INF100) og med bruk av abstraksjon og objekter (INF101).

Vi ønsker å forbedre implementasjonen fra INF100h16 for å

* Gjøre det enklere å teste
* Gjøre det enklere å gjenbruke koden
* Bruke et grafisk grensesnitt
* Håndtere feil i input bedre

I tillegg til et [grafisk grensesnitt](src/inf101/v18/labyrinth/GUIMain.java) har vi lagt ved en [klasse
med en main metode som gjenskaper det tekst-baserte grensesnittet](src/inf101/v18/labyrinth/ConsoleMain.java)
fra den opprinnelige oppgaven.


# Steg 0: Gjør ferdig forrige oppgave

Du trenger å ha gjort Del I av denne oppgaven + hele eller mesteparten av Lab 3. Generelt er det lurt å gjøre ting i rekkefølge – det er bedre å konsentrere seg om å bli ferdig med forrige enn å hoppe videre til neste. Hvis du står fast på en oppgave, spør om hjelp!

# Steg 1: Hent Oppgaven fra git

Etter at du har gjort [Del I](LAB-4-I_GIT.md) kan du hente (klone) oppgaven på vanlig måte fra

```
    https://retting.ii.uib.no/<brukernavn>/inf101.v18.lab4.git
```

## Oversikt over koden

* inf101.v18.cell – ting fra Lab 3
* **inf101.v18.datastructures** – IGrid og IList fra Lab 3
* inf101.v18.labyrinth.ILabyrinth – grensesnitt som skal implementeres
* **inf101.v18.labyrinth.Labyrinth** – skal implementeres av deg
* inf101.v18.labyrinth.LabyrinthTest – tester for labyrinten
* inf101.v18.labyrinth.LabyrinthTile – enum (OPEN, WALL, PLAYER, ...)
* inf101.v18.labyrinth.LabyrinthHelper – metoder for å lage labyrint-grids (tilfeldig eller basert på tabeller)
* inf101.v18.labyrinth.Direction – enum (NORTH, SOUTH, EAST, WEST)
* inf101.v18.labyrinth.GUIMain – grafisk hovedprogram
* inf101.v18.labyrinth.ConsoleMain – tekstbasert hovedprogram

**OBS!** Den utleverte labyrint-koden inneholder kompileringsfeil! Disse skal du fikse etterhvert.

## Steg 2: Gjøre IGrid og MyGrid generisk

Vi har lagt ved IGrid og MyGrid fra [Lab 2 og 3](LAB-3.md). Dette skal vi bruke til å holde styr
på hvor spilleren er, hvor det er vegger og de plassene spilleren kan bevege seg.
`MyGrid` er lagd for å holde styr på celle-tilstander, men nå ønsker vi å holde styr på
Labyrintbiter (`LabyrinthTile`).

Istedenfor å forandre MyGrid hver gang vi skal lagre en ny type elementer skal vi 
gjøre `IGrid` generisk og forandre `MyGrid` slik at den implementerer det nye IGrid interfacet.

For å gjøre det må IGrid nå være

    public interface IGrid<T> 

Hvor `T` er typen elementer som skal lagres. Derfor skal `set` ta i mot en `T` istedenfor
en CellState og `get` returnere en `T`. Dessuten vil `copy` returnere en `IGrid<T>`.

På samme måte må `MyGrid` forandres til

    public class MyGrid<T>

* Oppdater `IGrid` og `MyGrid` så de blir generiske
* Oppdater også `GridTest` til å fungere med generisk grid; testene er laget for `CellState`-elementer, så du kan her bruke `GridTest<CellState>` for eksempel. Vi har allerede oppdatert `IList`og `MyList`slik at de er generiske.


## Steg 3: Teste MyGrid

* Testene fra Lab 3 (i `GridTest`) skal fremdeles virke.

## Steg 4: Bruk `MyGrid<T>` i Lab 2/3

Vi har lagt ved programmene fra [Lab 2 og 3](LAB-3.md). Gjør om
programmet slik at det kan bruke `IGrid<T>`/`MyGrid<T>`.

* Sjekk celleautomatene (f.eks. `GameOfLife.java`) og se at de fremdeles fungerer og at celleautomat-programmet `inf101.v18.cell.Main` virker.
* Når du nå ser på koden for automatene (du kan gjerne kopiere over din egen løsning fra Lab 3), vil du se at du får advarsler om at `IGrid is a raw type` på stedene du har brukt IGrid/MyGrid. Slik koden er nå vil Java tolke IGrid/MyGrid som noe som kan lagre hva som helst (`Object`), i stedet for å være begrenset til å lagre `CellState`-elementer. Dette gjør koden litt mindre trygg (hva om noen fyller cellene med ender i stedet for cellstate?) – du bør derfor gå gjennom koden og *parameterisere* IGrid/MyGrid med elementtypen slik at du får f.eks. `IGrid<CellState>` i stedet.

## Steg 5: Lage en implentasjon av ILabyrinth

Vi har lagt ved et interface for klassen som brukergrensesnittet bruker for
å avgjøre oppførselen av Labyrintbitene. Lag en implementasjon av denne
som du kaller `Labyrinth`.

Vi har lagd ved noen tester for denne klassen i
`inf101.v18.labyrinth.LabyrinthTest`. Skriv gjerne egne tester for å sjekke at
implementasjonen din er korrekt.


### Konstruktør

Konstruktøren skal være slik:

    public Labyrinth(IGrid<LabyrinthTile> tiles)
    
* Du må ta en kopi av argumentet før du lagrer det i labyrint-objektet (`tiles.copy()`). Dette er nødvendig for å beskytte innkapslingen av datastrukturen (du lærer mer om dette senere i semesteret).
* Sjekk at ett og bare ett av feltene i `tiles` inneholder `LabyrinthTile.PLAYER`. Det er antakelig praktisk å lagre x,y-koordinatene til spilleren.
* Du bør sjekke at ingen av feltene inneholder `null`

### Metoder (m/hint)

Du må implementere disse metodene:
* `LabyrinthTile getCell(int x, int y)`
* `Color getColor(int x, int y)` – her kan du finne fargen ved hjelp av LabyrinthTile.getColor(); f.eks. getCell(x,y).getColor()
* `int getHeight()` og `int getWidth()` – kan hentes fra grid
* `boolean isPlaying()` – kan evt være `true` alltid (kan brukes for å implementere mer avanserte utgaver)
* `void movePlayer(Direction dir)` – skal flytte spillerens posisjon (se mer om Direction under), eller kaste `MovePlayerException` hvis den nye posisjonen ville vært ulovlig
* `boolean playerCanGo(Direction d)` – returnerer `true` hvis spilleren kan gå i retningen; gyldige felter for spilleren er alle som er innenfor brettet og som ikke inneholder `LabyrinthTile.WALL`

Disse kan du la returnere 0:
* `int getPlayerGold()`
* `int getPlayerHitPoints()`

Det kan lønne seg å lage noen hjelpemetoder: f.eks. `isValidPosition(int x, int y)` for å sjekke om en posisjon er gyldig for spilleren.

For **alle** metodene skal du (om nødvendig) sjekke argumentene før du bruker dem (f.eks. at x,y er innenfor rekkevidde). Ved feil i argumentene skal du `throw new IllegalArgumentException()`. 


### Enum

Det følger med to enums: 
* Direction – en av `NORTH`, `SOUTH`, `EAST`, `WEST`. Brettet er slik at `y` øker i `NORTH`-retningen og `x` øker i `EAST`-retningen
* LabyrinthTile – her trenger du bare bry deg om `OPEN` (ledig felt), `WALL` (vegg der spilleren ikke kan gå) og `PLAYER` (spilleren)


### Kjøre programmet

Det følger med to klasser med `main`-metoder, en som har grafisk brukergrensesnitt (`GUIMain`) og en som har INF100-style tekstgrensesnitt (`ConsoleMain`). I det grafiske brukegrensesnittet kan du navigere med piltastene eller med knappene.

### Legg merke til at...

* Sammenliknet med INF100 Semesteroppgave 3, har vi nå egne typer for flere av konseptene:
   * `IGrid`/`MyGrid` i stedet for `char[][]`
   * `LabyrinthTile` i stedet for `char`
   * `ILabyrinth`/`Labyrinth` for labyrinten
   * `Direction` for retning
   * `LabyrinthHelper` for å lage nye labyrinter
* Grid-typene er *abstrakte datatyper* (ADTer). En datatype er abstrakt når vi vet hva vi kan gjøre med verdiene (hvilke metoder vi kan kalle), uten at vi vet nøyaktig hvordan dataene er lagret eller hvordan metodene er implementert. Både `class` og `interface` spesifiserer en abstrakt datatype. I tillegg lager `class` en konkret implementasjon av datatypen (dvs. den sier hvilke feilvariabler som inngår i datastrukturen og hvordan metodene som bruker datastrukturen er implementert). Når du bruker objekter og kaller metoder på dem, jobber du med ADTer. Når du kikker på / endrer klassen til et objekt, jobber du med den konkrete typen (klassen) som implementerer ADTen. En abstrakt datatype har *data innkapslet bak et grensesnitt av veldefinerte metoder*; dvs. at programmeringen vi gjør med objekter i INF101 også er programmering med data abstraksjon og abstrakte datatyper (selv om vi ikke har brukt akkurat disse ordene så mye).
   * En del abstrakte datatyper er så vanlige at du kan snakke om dem på en måte hvor de i praksis er enda mer abstrakte. Dvs. når vi snakker om "en liste" eller "en liste-ADT", så vil de fleste programmører vite at det er snakk om noe hvor du kan sette inn og hente ut elementer, og hvor elementene er i rekkefølge, uavhengig av nøyaktig hvilket grensesnitt som brukes. Så både `List` (som følger med i Java) og `IList` (som vi har laget i INF101) er eksempler på liste-ADTen, og de er i praksis ganske like selv om du ikke kan erstatte den ene med den andre. Lister i f.eks. Python er en annen variant av lister. Dvs. at "liste" er et vanlig ADT-konsept; IList og List er konkrete eksempler på ADTen, og MyList og ArrayList er konkrete datatyper som implementerer IList og List ADTene.
* Fordelen med å abstrahere vekk detaljene rundt grid, er at vi slipper å forholde oss til detaljene rundt hvordan ting er lagret. Hvis vil lagrer gridet i en `char[][]` så må alle deler av programmet som bruker gridet forholde seg til at det består av en tabell av tabeller. Man må da også f.eks. ta med i beregning at Java ikke gir noen garanti for at hver undertabell i en `char[][]` er like lang. Og hvis vi har kode som er interessert i bare å se på samlingen av elementer, uavhengig av posisjon i gridet, så må den koden også forholde seg til at vi har en tabell av tabeller. Med IGrid/MyGrid kan vi i stedet f.eks. legge til metoder som lar oss gå gjennom alle elementene i vilkårlig rekkefølge. Vi har også mulighet til å legge til andre hendige metoder om vi vil; f.eks. "fyll alle feltene med X", "gi meg feltet som er NORTH for dette X,Y", "er (x,y) en gyldig koordinat? (uten å måtte skrive ned den litt kompliserte testen (`x >= 0 && x < grid.length && y >= 0 && grid.length > 0 && y < grid[0].length` e.l.) for dette alle steder) 
* Ved å trekke ut labyrinten i en egen klasse (i stedet for å blande den sammen med `main`-metoden), er det lett å gjenbruke den i flere situasjoner, f.eks. både med grafisk- og tekstgrensenitt. Samme labyrint-koden kunne f.eks. også brukes med 3D-grafikk.
* Å ha labyrinten i egen klasse er også praktisk når man skal teste den. I prinsippet kunne vi testet labyrinten ved å gi den tekst som input og sjekke utskriften; men det er litt komplisert å få til, og adskillig vanskeligere med programmer som har grafiske brukergrensesnitt. Vi vil derfor aller helst ha mest mulig funksjonalitetet (grid, retning, labyrint, ...) delt opp i seperate klasser som kan testes for seg (*enhetstesting*).
* Vi kunne gjerne gått lenger i dette, og skilt ut ideen om *posisjon* i en egen `Position`-klasse (slik vi gjorde på forelesningene). Da kunne logikken for å håndtere retninger vært plassert sammen med posisioner, i stedet for å være spredd utover. 

## Ekstra

Du kan utvide oppgaven i tråd med ideene fra INF100:

* Gull: La spilleren samle inn gull (`LabyrinthTile.GOLD`) som ligger på feltene. Tilfeldighetsgeneratoren legger ut gull allerede, og brukergrensesnittet viser hvor mye gull spilleren har samlet (basert på `getPlayerGold`).
* Monstre: La spilleren sloss med monstre (`LabyrinthTile.MONSTER`). Du kan f.eks. ha hit points som reduseres hvis spilleren taper en kamp. Disse kan vises i brukergrensesnittet (basert på `getPlayerHitPoints`).
* Du bør i såfall lage noen flere tester (f.eks. at `getPlayerGold` øker når spilleren går på et gull-felt).
* Vi har litt veldig enkel kode for å lage tilfeldige labyrinter i `LabyrinthHelper`. Det finnes [mange algoritmer](https://en.wikipedia.org/wiki/Maze_generation_algorithm) for å generere gode labyrinter. Interessant nok kan [celleautomater også brukes til å generere labyrinter](http://www.conwaylife.com/w/index.php?title=Maze).
* Du kan gjerne også implementere filinnlesing i `LabyrinthHelper`.



