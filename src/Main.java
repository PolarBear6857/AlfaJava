import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("Český jazyk", "ČJ", 3));
        subjects.add(new Subject("Angličtina", "A", 4));
        subjects.add(new Subject("Matematika", "M", 4));
        subjects.add(new Subject("Tělocvik", "TV", 2));
        subjects.add(new Subject("Programové vybavení", "PV", 3));
        subjects.add(new Subject("Databázové systémy", "DS", 3));
        subjects.add(new Subject("Podnikové informační systémy", "PIS", 4));
        subjects.add(new Subject("Počítačové systémy a sítě", "PSS", 3));
        subjects.add(new Subject("Aplikovaná matematika", "AM", 2));
        subjects.add(new Subject("Cvičení ze správy IT", "CIT", 2));
        subjects.add(new Subject("Webové aplikace", "WA", 3));
        subjects.add(new Subject("Technický projekt", "TP", 1));

        TimetableGenerator generator = new TimetableGenerator(subjects);
        long startTime = System.currentTimeMillis();
        List<byte[]> timetables = generator.generateTimetables(1000000);
        long endTime = System.currentTimeMillis();
        float elapsedTimeInSeconds = (endTime - startTime) / 1000.0f;
        System.out.println("Elapsed Time: " + elapsedTimeInSeconds + " seconds");
        System.out.println("Timetables: " + timetables.size());

        for (int i = 0; i < 5; i++) {
            byte[] timetable = timetables.get(i);
            String formattedTimetable = generator.formatTimetable(timetable);
            //TODO WTF CO TO SAKRA VYPISUJE
            System.out.println("Timetable " + (i + 1) + ": " + formattedTimetable);
        }
    }
}

//TODO upřesnit formát rozvrhu, tzn. maximální počet hodin týdně, jak může vypadat den (že nemůže být 10 hodin) apod

/*
vytvoř Java Generátor - Generuje různé varianty rozvrhu. Například zkouší všechny možné permutace, nebo jen některé variace.
Můžete zkoušet prohodit předměty v jednom dni, nebo přesunout výuku více na odpoledne/ráno.
Příliš ale neřeší, jestli jsou nové varianty dobré, nebo špatné.
Cílem je vygenerovat jich jen co nejvíce.
Výsledné varianty se musí vložit do sdíleného prostoru s ostatnímy paralelními částmi programu.
Generátorů může být samozřejmě více a mohou být i různé typy.

vzor rozvrhu (nemusí být zachován tvar ukládání, lze upravit podle potřeby pro efektivnost a rychlost):
rozvrh = [
    "M", "DS", "DS", "PSS", "PSS", "A", None, "TV", None, None,
    "PIS", "M", "PIS", "PIS", "TP", "A", "CJ", None, None, None,
    "CIT", "CIT", "WA", "DS", "PV", None, "PSS", None, None, None,
    "AM", "M", "WA", "WA", None, "A", "C", "PIS", "TV", None,
    "C",   "A", "M", "PV", "PV", "AM", None, None, None, None
]

Vzor předmětů  (nemusí být zachován tvar ukládání, lze upravit podle potřeby pro efektivnost a rychlost):
subjects = [
    ("Český jazyk", "ČJ", 3),
    ("Angličtina", "A", 4),
    ("Matematika", "M", 4),
    ("Tělocvik", "TV", 2),
    ("Programové vybavení", "PV", 3),
    ("Databázové systémy", "DS", 3),
    ("Podnikové informační systémy", "PIS", 4),
    ("Počítačové systémy a sítě", "PSS", 3),
    ("Aplikovaná matematika", "AM", 2),
    ("Cvičení ze správy IT", "CIT", 2),
    ("Webové aplikace", "WA", 3),
    ("Technický projekt", "TP", 1),
]

cílem je udělat program co nejefektivnější, tzn. využívat bytes, objekty apod, aby byl počet vygenerovaných rozvrhů co největší
*/