# [Semesteroppgave 1: “Rogue One oh one”](https://retting.ii.uib.no/inf101.v18.sem1/blob/master/SEM-1.md)


* **README**
* [Oversikt](SEM-1.md) – [Praktisk informasjon 5%](SEM-1.md#praktisk-informasjon)
* [Del A: Bakgrunn, modellering og utforskning 15%](SEM-1_DEL-A.md)
* [Del B: Fullfør basisimplementasjonen 40%](SEM-1_DEL-B.md)
* [Del C: Videreutvikling 40%](SEM-1_DEL-C.md)

Dette prosjektet inneholder [Semesteroppgave 1](SEM-1.md). Du kan også [lese oppgaven online]
(https://retting.ii.uib.no/inf101.v18.oppgaver/inf101.v18.sem1/blob/master/SEM-1.md)
(kan evt. ha små oppdateringer i oppgaveteksten som ikke er med i din private kopi).

**Innleveringsfrist:**
* Del A + minst to deloppgaver av Del B skal være ferdig til **fredag 9. mars kl. 2359**. 
* Hele oppgaven skal være ferdig til **fredag 16. mars kl. 2359** ([AoE](https://www.timeanddate.com/worldclock/fixedtime.html?msg=4&iso=20180316T2359&p1=3399))
* [Ekstra tips til innlevering](https://retting.ii.uib.no/inf101/inf101.v18/wikis/innlevering)

(Kryss av under her, i README.md, så kan vi følge med på om du anser deg som ferdig med ting eller ikke. Hvis du er
helt ferdig til den første fristen, eller før den andre fristen, kan du si fra til gruppeleder
slik at de kan begynne å rette.)

**Utsettelse:** Hvis du trenger forlenget frist er det mulig å be om det (spør gruppeleder – evt.
foreleser/assistenter hvis det er en spesiell situasjon). Hvis du ber om utsettelse bør du helst være i gang
(ha gjort litt ting, og pushet) innen den første fristen.
   * Noen dagers utsettelse går helt fint uten begrunnelse, siden oppgaven er litt forsinket.
   * Hvis du jobber med labbene fremdeles, si ifra om det, og så kan du få litt ekstra tid til å gjøre
   ferdig labbene før du går i gang med semesteroppgaven. Det er veldig greit om du er ferdig med Lab 4 først.
   * Om det er spesielle grunner til at du vil trenge lengre tid, så er det bare å ta kontakt, så kan vi avtale noe.
   Ta også kontakt om du [trenger annen tilrettelegging](http://www.uib.no/student/49241/trenger-du-tilrettelegging-av-ditt-studiel%C3%B8p).
   

# Fyll inn egne svar/beskrivelse/kommentarer til prosjektet under

Levert av: Andrey Belinskiy (zur008)

 **Del A:**
- [x] **helt ferdig**

- [ ] delvis ferdig

 **Del B:**
- [x] **helt ferdig**

- [ ] delvis ferdig

 **Del C:**
- [x] **helt ferdig**

- [ ] delvis ferdig

**Hele semesteroppgaven:**
- [x] **ferdig og klar til retting!**

# Del A
## Svar på spørsmål
## A1
**a)** **IGame** - the IGame-objects must "know" about the game map with locations and about the current actor;

**IMapView** - this interface has methods to work with a map using the provided location, as well as different "items",
so the objects basically will need to have a map and the list of items to work with;

**IItem** - the item object should have an hp value (all of the other interfaces that extend this interface should have hp
as well);

**IActor** - an extension of the IItem interface for the actors, used as a base for INonPlayer and IPlayer
interfaces. The objects should have an attack rating and damage values.

**INonPlayer** and **IPlayer** - extensions of the IActor interface, one is for NPC and other is for the player. The player
object acts depending on the key that was pressed, and the NPC is controlled by the computer.

**b)** Many of these interfaces extend other interfaces and further add different methods to be used within specific objects.
They also use other interfaces to get or return objects that implement them.
For example, IGame objects get IItem objects and return IItem, ILocation, IMapView and ITurtle objects.

**c)** There are two different interfaces for maps because IMapView has methods to work with a map in general,
while IGameMap has methods for the specific GameMap.
Also, IMapView in general has more "get" methods to receive the information about the map, while IGameMap has more
"actions", like drawing and cleaning the game map.
Finally it's worth noting that IMapView can return the list of all items on the location, while IGameMap can return the list of items
that can be modified (for example, instance of IPlayer).

**d)** They are different because the IPlayer object is controlled by the human (i.e. the player), while the INonPlayer
object is controlled by the computer. If there were only player controlled objects, then the player would have to
play just by himself and control all the actors. While there is nothing wrong with such idea, this specific genre of
"Roguelike" is based on the interactions with some kind of NPCs (non-player characters) controlled by the computer,
to make it more interesting and challenging to play, and to keep the spirit of the "Role Playing Games" genre.

## A2
**e)** "Rabbit" and "Carrot" both have hp, carrot can't attack and the rabbit has the attack values. They also have a size,
which I didn't see first time, so the player potentially can pick up the item or the monster with the addition of the weight value
(not a good idea to pick up a monster, but can be further extended to f.ex. monster throwing mechanic).

**f)** The Rabbit object uses the IGame object that uses an IGameMap map with the locations of the rabbits and other IItem objects. IGame
uses the getLocalItems method which, again, uses said IGameMap object to find the items in the current location (and if
it's a carrot then the rabbit eats it and adds the eaten amount to the "food" var). Finally, the rabbit uses the canGo
method to "scan" the surrounding area. Then all the possible move locations are saved to the array, which then is
shuffled and the first (i.e. random) location is chosen (to imitate the random movement).

**g)** The IGame object constructs the IGameMap based on the IGrid from the file or from the prebuilt map in Main method.
Therefore this IGameMap has all the items and getLocation method returns the location of the item from the map.
To know which actor is currently active the array list named "actors" is used that consists of all the IActor objects
that can act on this turn. All the actions are happening to the currentActor from this list that resembles a queue.
So when the rabbit starts to act, then it's this rabbit's turn according to the queue of actors.

# Del B
## Svar på spørsmål
**a)** In the previous labs we used CellAutomata class to move the actor, the direction of the movement was dependant on the color of
the cell the actor was standing on. In Labyrinth we also used the contents of the cell to determine if the player can go in that direction.
And now we are using collections of items separated to several types (Actor, Item and so on) and we place them into cells.
Therefore we can store several different types of items in one place and determine the way other actors act with these items.

**b)** The Game class acts as a playground for the player. It uses the GameMap to store and get elements in each Location.
At the same time it's using different methods to influence the gameplay itself (like picking up and dropping items).
It's much easier to use one class and send commands from player and other actors. From the other hand there is a lot of methods
in one place and it could be hard to search and change something in this class if it becomes very big.

**c)** Some of the methods (like mentioned pickUp() and drop() in the Game class, as well as remove() and some other in the GameMap class)
don't check for some conditions before executing the actions. This can be dangerous and potentially lead to not very obvious problems, but from the other hand
the methods can execute basic actions while the actual check for conditions is performed before calling for these methods. It also heavily
depends on the rules of the game. For example, we can say, that one cell can only contain one IActor, but we can also say that that is OK if the cell
contains three actors. In this situation it shouldn't be a problem, because the actual movement and actions of the actors happen in turns.

**d)** While implementing the methods in Del B some things became more clear, for example how actors move to chosen location, how the game turns are executed,
how the message system works. The most interesting thing was to implement the visibility method with the help of which actors can search for something around them
and go in that direction using gridLineTo() method.

# Del C
## Oversikt over designvalg og hva du har gjort
In this part of the project the following was implemented:
- Items. Several items were added that influence the game. Sword buffs attack and damage of the player, shield provides more defence.
The potion can be used to restore HP.
- Inventory (basic). Didn't have enough time to implement the separate interface based inventory, simple List was used
to store picked up items.
- Walls. Dynamically generate the graphical orientation of the walls based on the position of walls in neighbourhood.
- Emojis. Symbola font was used to have a better graphical representation of the actors and items. Tried to use the font
adjuster (TextFontAdjuster), but didn't figure out why it's not working.
- Ending. The game has a win and lose condition. Player wins if he/she successfully completes several stages (opens several locked doors with keys).
If the player's hp goes below 0 the game ends.
- Other small mechanics. Zombies follow and attack player and rabbits according to the visibility. Rabbits search for carrots if they have low HP,
and search for and attack the player if their HP is high. Zombies restore HP from defeating the enemies.

Some methods are not very "clean", but they work nonetheless. Didn't have enough time to implement something super smart and effective.
 
Some ideas that can be implemented without too much trouble:
- Actors can use ranged attacks while holding a ranged weapon or cast a spell (with the help of visibility var + we can use modifier keys to reduce the number of keys to attack,
i.e. shift+left to shoot to the left and so on).
- More enemies that have different attack patterns or that have several action points during one turn.
- Leveling system. Level up after accumulating enough EXP. You can get EXP from defeating enemies, solving puzzles or just advancing through each stage.
- Gold hoarding and shops to get some additional items, reveal the map and so on.
- Survival aspects like thirst, hunger, warmth and so on.