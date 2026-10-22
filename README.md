# Laboration 2 – Budgethanteraren

Budgethanteraren är en konsolapplikation där användaren kan lägga till inkomster och utgifter, visa alla transaktioner, se saldo och kategorisummor samt filtrera transaktioner efter typ.

Transaktionerna sparas i en CSV-fil och läses in igen när programmet startar.

## Tekniker

Projektet använder bland annat:

- `ArrayList` och `List` för att lagra transaktioner.
- En generisk `Repository<T>` för att lagra och filtrera objekt.
- `Predicate<T>` och lambda-uttryck vid filtrering.
- Ett eget undantag, `InvalidTransactionException`, för ogiltiga transaktioner.
- JUnit 5 för tester av Repository, beräkningar och validering.
- `java.nio.file.Files` och `Path` för att spara och läsa transaktioner från fil.
- `Logger` för att logga felaktig indata och fel vid filhantering.


## Köra programmet

Projektet är byggt med Java 27 och Maven.

Starta `Main`-klassen för att köra Budgethanteraren.

För att köra alla tester från projektmappen:

```bash
mvn test
```


## Felsökning

Jag upptäckte en bugg när jag testade att skriva en kategori som innehöll ett komma, till exempel `Mat, ute`. Transaktionen sparades till filen, men när programmet startades igen kunde raden inte läsas in och en varning skrevs ut i loggen.

Jag satte en breakpoint på raden där filraden delas upp med `split(",")` och startade programmet i debug-läge. När jag stegade igenom koden såg jag att raden med kategorin `Mat, ute` delades upp i fem delar istället för fyra. Då hamnade `ute` där beloppet skulle vara och programmet kunde därför inte göra om värdet till en `double`.

Jag löste problemet genom att byta separator i filen från komma till semikolon. Då kan en kategori fortfarande innehålla komma utan att raden delas upp fel. Efter ändringen testade jag samma kategori igen och kunde starta om programmet och läsa in transaktionen korrekt.