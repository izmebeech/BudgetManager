# BudgetManager

## 1. Projektbeskrivning

BudgetManager är en Java-baserad konsolapplikation för att hantera privatekonomi genom att registrera och sammanställa inkomster och utgifter.
Användaren kan kategorisera transaktioner, visa transaktionshistorik, filtrera transaktioner och se ekonomiska sammanställningar (inklusive totala inkomster, utgifter, saldo och belopp per kategori).
Applikationen använder Java Collections för att lagra transaktioner, en generisk repository-klass för datahantering och Stream API för filtrering och beräkningar.
Transaktioner sparas i en lokal textfil och läses in när applikationen startar, så att historiken finns kvar mellan körningar.

## 2. Tekniker och verktyg

Projektet är utvecklat i Java och använder följande tekniker och verktyg:

- Java: Applikationens programmeringsspråk.
- Maven: Hantering av projektets byggprocess och beroenden.
- JUnit 5: Automatiserade enhetstester av repository och beräkningslogik.
- Java Collections Framework: Lagring och hantering av transaktioner i samlingar.
- Generics: En generisk klass, `Repository<T>`, för återanvändbar datahantering.
- Stream API: Filtrering, summering och gruppering av transaktioner.
- Java NIO (`java.nio.file`): Läsning och skrivning av transaktioner till en textfil.
- Java Logging API (`java.util.logging`): Loggning av händelser, varningar och fel.

## 3. Applikationens struktur

Applikationen är uppdelad i flera klasser med separata ansvarsområden:

- **`BudgetManager`** – Hanterar konsolgränssnittet, användarens menyval, inmatning och validering samt samordnar applikationens funktioner.
- **`Transaction`** – Ett record som representerar en transaktion med datum, kategori, belopp och transaktionstyp. Validerar även att beloppet är ändligt och större än noll.
- **`TransactionType`** – En enum som definierar de två transaktionstyperna `INCOME` och `EXPENSE`.
- **`Repository<T>`** – En generisk klass som lagrar objekt i en samling och erbjuder metoder för att lägga till, hämta och filtrera data.
- **`Calculator`** – Beräknar totala inkomster, utgifter, saldo och totalsummor per kategori med hjälp av Stream API.
- **`TransactionFileHandler`** – Ansvarar för att spara transaktioner till en textfil och läsa in dem vid uppstart, inklusive hantering av filfel och felaktigt formaterade rader.
- **`InvalidTransactionException`** – En egen undantagsklass som används när en transaktion innehåller ett ogiltigt belopp.

## 4. Funktionalitet

BudgetManager erbjuder följande funktioner:

- **Registrera transaktioner:** Användaren kan registrera inkomster och utgifter med kategori och belopp. Datum och tid registreras automatiskt.
- **Validera indata:** Applikationen kontrollerar transaktionstyp, kategori och belopp. Beloppet måste vara ett ändligt, positivt tal. Både punkt och decimalkomma accepteras som decimaltecken.
- **Visa transaktionshistorik:** Användaren kan visa samtliga registrerade transaktioner med datum, typ, kategori och belopp.
- **Visa ekonomisk sammanställning:** Applikationen beräknar totala inkomster, utgifter, aktuellt saldo och totalsummor per kategori. Om utgifterna överstiger inkomsterna visas en varning.
- **Filtrera transaktioner:** Användaren kan visa transaktioner uppdelade efter typ eller filtrera dem efter kategori. Kategorijämförelsen är inte case sensitive.
- **Spara transaktioner:** Användaren kan spara transaktionshistoriken till en lokal textfil via huvudmenyn.
- **Ladda transaktioner:** Tidigare sparade transaktioner läses in när applikationen startar.
- **Hantera felaktig indata:** Ogiltiga transaktioner avvisas och användaren får återkoppling. Varningar och fel loggas för att underlätta felsökning.

## 5. Filhantering och datalagring

Applikationen använder klassen `TransactionFileHandler` för att spara och läsa in transaktioner.

- **Lagringsplats:** Transaktionerna sparas i filen `transactions.txt` i mappen `BudgetTransactions` under användarens hemkatalog.
- **Filformat:** Filen använder UTF-8 och varje transaktion lagras på en egen rad. Datum och tid, kategori, belopp och transaktionstyp separeras med semikolon.
- **Inläsning:** När applikationen startar läses tidigare sparade transaktioner in och läggs till i repositoryt. Om filen inte finns startar applikationen med en tom transaktionshistorik.
- **Sparning:** Användaren sparar transaktionerna genom att välja alternativ 5 i huvudmenyn. Den befintliga filens innehåll ersätts med den aktuella transaktionshistoriken.
- **Felhantering:** Felaktigt formaterade rader hoppas över och loggas som varningar. Fel vid läsning, skrivning eller skapande av lagringsmappen loggas som allvarliga fel.

Den lokala filen gör att transaktionshistoriken kan finnas kvar mellan körningar av applikationen.

## 6. Generics och Stream API

I applikationen använder jag den generiska klassen `Repository<T>` för att lagra och hantera objekt. I det här projektet används den för att hantera transaktioner av typen `Transaction`.
Fördelen med generics är att samma repository-klass kan återanvändas för olika typer av objekt, i stället för att behöva skapa en separat klass för varje typ.
Det gör också att Java kan kontrollera typerna redan vid kompilering, vilket minskar risken för fel.
Utan generics hade jag behövt skriva mer specifik kod för att hantera transaktioner. Det hade gjort lösningen mindre flexibel och skapat betydligt mer kod att underhålla och hålla rätt på.

Jag använder Stream API i bland annat `Repository` och `Calculator` för att filtrera transaktioner, beräkna totalsummor och gruppera belopp per kategori.
Det används till exempel för att skilja mellan inkomster och utgifter, räkna ut det totala saldot och sammanställa belopp per kategori med hjälp av `filter`, `map` och `collect`.
Jämfört med vanliga loopar gör Streams det möjligt att skriva den här typen av kod på ett mer direkt sätt. Koden beskriver tydligare *vad* som ska göras, utan att jag behöver skriva ut varje steg för *hur* det ska göras. Det gör, enligt mig, koden lättare att läsa och underhålla.

## 7. Testning

Applikationen testas med JUnit 5. Jag har skrivit enhetstester för `Repository` och `Calculator` för att kontrollera att de viktigaste delarna av logiken fungerar som de ska.

Testerna för repository kontrollerar bland annat att:

- Transaktioner kan läggas till och hämtas.
- `findWhere` filtrerar fram rätt transaktioner.
- Tomma repositories och sökningar utan träffar hanteras korrekt.
- Listorna som returneras inte kan ändras direkt.
- Det inte går att lägga till `null` som ett objekt.

Testerna för calculator kontrollerar bland annat att:

- Totala inkomster och utgifter beräknas korrekt.
- Saldo beräknas korrekt utifrån inkomster och utgifter.
- Belopp summeras per kategori.
- Tomma repositories ger korrekta resultat, till exempel noll i saldo och tomma kategorisammanställningar.

Testerna använder konkreta exempel och förväntade värden för att kontrollera resultaten.
På så sätt kan jag upptäcka fel i beräkningslogiken och repositoryt utan att behöva testa hela konsolapplikationen manuellt varje gång.

## 8. Felsökning och felhantering

Under utvecklingen har jag arbetat med att hitta och lösa problem samt lägga till validering och felhantering för att göra applikationen mer robust.

### Exempel på felsökning: Repository

**Problem:** Metoden `findAll()` returnerade repositoryts interna lista. Det innebar att kod som använde metoden kunde ändra listan direkt och till exempel ta bort alla transaktioner.
**Undersökning:** Jag skrev ett JUnit-test som tömde listan som returnerades från `findAll()` och sedan kontrollerade repositoryts innehåll. Testet visade att repositoryt också hade tömts.
**Lösning:** Jag ändrade metoden så att den returnerar `List.copyOf(items)` i stället. Då kan anroparen inte ändra repositoryts interna lista via den returnerade listan.
**Verifiering:** Jag uppdaterade testet med `assertThrows()` för att kontrollera att ett försök att ändra listan ger ett undantag. Därefter körde jag testerna igen och verifierade att de passerade.

### Övriga förbättringar och felhantering

- **Decimalkomma:** `Double.parseDouble()` accepterar inte decimalkomma direkt. Jag ändrade därför inmatningen så att kommatecken ersätts med punkt innan beloppet parsas. Det gör att både `12.50` och `12,50` kan användas.
- **Validering av belopp:** Applikationen kontrollerar att beloppet är ett giltigt, ändligt tal större än noll. Ogiltig inmatning hanteras med undantagshantering, så att användaren kan försöka igen. Klassen `Transaction` gör också en egen kontroll för att skydda mot ogiltiga transaktioner.
- **Skydd av repositoryt:** `Repository<T>` avvisar `null`-värden. Det skyddar datan även om repositoryt används från andra delar av programmet än konsolgränssnittet.
- **Paus i konsolgränssnittet:** Jag implementerade metoden `pauseForUser()` så att användaren kan läsa informationen på skärmen innan programmet fortsätter. Metoden väntar på att användaren trycker Enter.
- **Spara och läsa in transaktioner:** Jag testade manuellt att spara transaktioner, starta om applikationen och kontrollera att transaktionerna fanns kvar i historiken.
- **Fel vid filläsning:** Felaktigt formaterade rader hoppas över och loggas som varningar. Fel vid läsning, skrivning eller skapande av lagringsmappen loggas för att underlätta felsökning.

## 9. Loggning

Applikationen använder Java Logging API (`java.util.logging`) för att logga viktiga händelser, varningar och fel. Loggningen gör det lättare att följa vad som händer i programmet och att felsöka problem.

Jag använder följande loggnivåer:

- **`FINE`** – Används för mer detaljerad information, till exempel när en transaktion läses in från fil.
- **`INFO`** – Används för att logga normala händelser, till exempel när transaktioner sparas eller när en transaktion registreras.
- **`WARNING`** – Används när något oväntat inträffar men programmet kan fortsätta, till exempel vid ogiltig inmatning eller felaktigt formaterade rader i filen.
- **`SEVERE`** – Används vid allvarligare fel, till exempel när transaktioner inte kan läsas eller sparas på grund av ett filfel.

Loggnivåerna hjälper till att skilja mellan vanlig aktivitet, problem som kan hanteras och allvarligare fel. `FINE`-meddelanden kan kräva en mer detaljerad loggningskonfiguration för att visas.

## 10. Reflektion

Under arbetet med labben har jag fått en bättre förståelse för hur man kan bygga upp en Java-applikation med flera klasser som har olika ansvarsområden. Jag har också fått mer erfarenhet av JUnit, filhantering, loggning och Streams. Speciellt streams tycker jag känns som ett väldigt logiskt och användbart verktyg.

Jag kan fortfarande få lite panik när jag stöter på ett moment som kräver en lång och komplicerad for-loop och där jag känner att jag måste hålla koll på alldeles för många saker samtidigt. Men ju mer vi arbetar med olika uppgifter, desto mer självsäker känner jag mig. Jag märker att saker som först känns ganska överväldigande blir lättare att förstå när jag får bryta ner dem i mindre delar och faktiskt testa mig fram.

Jag tog även med mig med många lärdomar om Intellij från förra labben. Till exempel att extrahera en `switch` till en egen metod för att göra koden renare och lättare att läsa. Det känns bra att inte bara fokusera på att få koden att fungera, utan också på hur jag kan strukturera den bättre och få det att se snyggare ut. Jag tycker fortfarande att det är väldigt kul att sitta och pilla med estetiken och att lägga till emojis osv, så jag fick bita mig i fingret för att inte fastna för mycket i det.

En annan viktig lärdom har varit att tänka på vad som kan gå fel, inte bara på att få programmet att fungera när allt går som det ska. Arbetet med tester och `Repository<T>` har gjort det tydligare för mig hur viktigt det är att skydda data och kontrollera att koden beter sig som förväntat.

Sammantaget känner jag att jag har blivit åtminstone lite tryggare i att ta mig an problem som först verkar komplicerade, även om jag fortfarande har mycket kvar att lära mig.
Det känns motiverande att märka att jag faktiskt blir bättre ju mer jag övar! 
