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
        List<byte[]> timetables = generator.generateTimetables(1_000_000);
        long endTime = System.currentTimeMillis();
        float elapsedTimeInSeconds = (endTime - startTime) / 1000.0f;
        System.out.println("Elapsed Time: " + elapsedTimeInSeconds + " seconds");
        System.out.println("Timetables: " + timetables.size());

        for (int i = 0; i < 5; i++) {
            byte[] timetable = timetables.get(i);
            String formattedTimetable = generator.formatTimetable(timetable);
            System.out.println("Timetable " + (i + 1) + ": \n" + formattedTimetable);
        }
    }
}

//TODO upřesnit formát rozvrhu, tzn. maximální počet hodin týdně, jak může vypadat den (že nemůže být 10 hodin) apod
