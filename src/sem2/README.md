# [Semesteroppgave 2: “Fire på rad”](https://retting.ii.uib.no/inf101.v18.sem2/blob/master/SEM-2.md)


* **README**
* [Oppgavetekst](SEM-2.md)

Dette prosjektet inneholder [Semesteroppgave 2](SEM-2.md). Du kan også [lese oppgaven online](https://retting.ii.uib.no/inf101.v18.oppgaver/inf101.v18.sem2/blob/master/SEM-2.md) (kan evt. ha små oppdateringer i oppgaveteksten som ikke er med i din private kopi).

**Innleveringsfrist:**
* Hele oppgaven skal være ferdig til **fredag 27. april kl. 2359** ([AoE](https://www.timeanddate.com/worldclock/fixedtime.html?msg=4&iso=20180427T2359&p1=3399))
* [Ekstra tips til innlevering](https://retting.ii.uib.no/inf101/inf101.v18/wikis/innlevering)

(Kryss av under her, i README.md, så kan vi følge med på om du anser deg som ferdig med ting eller ikke.)

**Utsettelse:** Hvis du trenger forlenget frist er det mulig å be om det (spør gruppeleder – evt. foreleser/assistenter hvis det er en spesiell situasjon). Hvis du ber om utsettelse bør du være i gang (ha gjort litt ting, og pushet) før fristen
   * En dag eller to går greit uten begrunnelse.
   * Eksamen er relativt tidlig i år, så vi vil helst unngå lange utsettelser.
   * Om det er spesielle grunner til at du vil trenge lengre tid, så er det bare å ta kontakt, så kan vi avtale noe. Ta også kontakt om du [trenger annen tilrettelegging](http://www.uib.no/student/49241/trenger-du-tilrettelegging-av-ditt-studiel%C3%B8p). 
   
# Fyll inn egne svar/beskrivelse/kommentarer til prosjektet under
* Levert av:   Andrey Belinskiy (zur008)
* [x] hele semesteroppgaven er ferdig og klar til retting!
* Code review:
   * [x] jeg har fått tilbakemelding underveis fra @ttr006, Terje Trommestad.
   * [x] jeg har gitt tilbakemelding underveis til @ttr006, Terje Trommestad.
* Sjekkliste:
   * [x] Kjørbart Fire på Rad-spill
   * [x] Forklart designvalg, hvordan koden er organisert, abstraksjon, og andre ting
   * [x] Tester
   * [x] Dokumentasjon (JavaDoc, kommentarer, diagrammer, README, etc.)
   * [x] Fornuftige navn på klasser, interfaces, metoder og variabler
   * [x] Fornuftige abstraksjoner og innkapsling (bruk av klasser, interface, metoder, etc.)

## Oversikt

This small program respresents the Four-in-a-row game. The code is represented
by separate classes. It provides modularity as well as easier understanding of
how the program works. It's also very useful for adding new features, because
you don't have to rewite whole program to add something to it.

The program consists of several parts:
* Main - starts the program;
* Game-related classes - contain main game logic, interaction with the board and
game rules;
* Elements - game pieces which are placed on the game board;
* Grid - provides a datastructure for the game;
* Player - contains player classes to be able to distinguish the computer and human player;
* Utils - provides useful tools for the game, such as Input checking and colors shortcuts;
* Tests - various tests to find these pesky bugs.

The program was written almost from scratch. The only thing that was copied from the
previous labs is the datastructure (IGrid).


### Bruk
#### For å starte programmet kjør Main

## Designvalg

* The datastructure from the previous labs was chosen as the base for the program.
It was used to represent the gaming board. After that the interfaces and classes were written.
* First of all the game needed its own class to be able to hold the game process.
* Then the game board needed the elements that will be placed on it. The elements share a lot in
common, so there can be one base element, while other will inherit from it (didn't implement it
due to low number of elements in total).
* Then the players were added. Computer players place their chips at random,
while human players need to choose the column where to place the chip.
* After that the game required the winning/losing conditions. Separate class with game rules was created
for this purpose. If the game will need different rules - they can be added in this class, with minimal
changes to the game code itself.
* In paralell, different small parts were added, like custom board output, input checking and
and different loops.
* In the end the game can be player by either no human players, 1 human vs 1 computer or
2 humans against each other. More players can be added to the game with some small tweaks
of the code.
* The terminal output was chosen mainly because of the simplicity of implementation and huge lack of
time for development due to other projects :)
* To mix up the thing a little, different colors were used to distinguish the players.
* Some unicode characters were used as well.

### Bruk av abstraksjon

The abstraction is mainly used by the game board to be able to hold different elements. It also helps
when creating boards with different contents. Some classes could've been abstract too,
but I didn't found where it can be useful in this program.
Other than that the program is separated into several classes that represent different parts of the game.
It's very useful when you need to change something without changing the whole program. It also provides
incapsulation that keeps the variables safe :)
Some classes are represented by interfaces which provide much better understanding of the
functionality without the need to fully dive into the programs code.

### Erfaring – hvilke valg viste seg å være gode / dårlige?

* The graphical representation of the game is the first thing that I would've changed.
JavaFX should solve this task very well.
* The second step would've been to change the player's input from console input to pressing the keys
(same as it was in the sem1 oppgave). The idea is that the player could've moved the
arrow on top of the gaming board and press enter to drop the chip to the column under that arrow.
Even better if the player could've been able to just click on the column where he/she wants to
drop the chip.
* Third thing is to add several different game mechanics, like the ones that are described in the
oppgaveteksten. The player could've dropped the bomb to destroy some chips. Other idea is to
remove connected row and add points to the player (similar to Tetris). Then the player with the most
points will win the game after some number of turns.

## Testing

At the start of the creation of the program most of the coding was done by try and error.
After there was enough modules the testing was made with the help of JUnit.
During those last testing cycles some of the critical errors were found and corrected.
So this testing was very useful.

## Funksjonalitet, bugs

Base game works as intended. There are of course some potential bugs due to the text
input from the human players, but the probability of them happening is mitigated to the minimum with
the help of the inmput checks. The only bugs that I've found are some additional messages that
are sometimes printed to the terminal when the user enters empty line instead of actual characters.

## Evt. erfaring fra code review

We have mainly used FaceBook to share some tips. We have the access to each other's code to be
able to review it. We've contacted several times at the end of the development cycle and shared
 some information. I was able to get some ideas from it.

## Annet
*Er det noe du ville gjort annerledes?*

Nothing that I can think of right now.
