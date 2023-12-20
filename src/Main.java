import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("Český jazyk", "ČJ, Mr", 3));
        subjects.add(new Subject("Angličtina", "A, Jz", 4));
        subjects.add(new Subject("Matematika", "M, Hr", 4));
        subjects.add(new Subject("Tělocvik", "TV, Lc", 2));
        subjects.add(new Subject("Programové vybavení", "PV, Ma", 3));
        subjects.add(new Subject("Databázové systémy", "DS, Vc", 3));
        subjects.add(new Subject("Podnikové informační systémy", "PIS, Bc", 4));
        subjects.add(new Subject("Počítačové systémy a sítě", "PSS, Mo", 3));
        subjects.add(new Subject("Aplikovaná matematika", "AM, Rk", 2));
        subjects.add(new Subject("Cvičení ze správy IT", "CIT, Sv", 2));
        subjects.add(new Subject("Webové aplikace", "WA, Na", 3));
        subjects.add(new Subject("Technický projekt", "TP, No", 1));
        TimetableGenerator generator = new TimetableGenerator(subjects);

        int numberOfThreads = 6;
        int timetablesToGenerate = 1_200_000;

        // Using ExecutorService to manage threads
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        List<CompletableFuture<List<byte[]>>> futures = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        // Divide the workload among the threads
        int workloadPerThread = timetablesToGenerate / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {

            CompletableFuture<List<byte[]>> future = CompletableFuture.supplyAsync(() ->
                    generator.generateTimetables(workloadPerThread), executorService);
            futures.add(future);
        }

        // Combine results from all threads
        List<byte[]> timetables = futures.stream()
                .flatMap(future -> future.join().stream())
                .toList();

        long endTime = System.currentTimeMillis();
        float elapsedTimeInSeconds = (endTime - startTime) / 1000.0f;

        System.out.println("Timetables: " + timetables.size());
        System.out.println("Elapsed Time: " + elapsedTimeInSeconds + " seconds");

        // Shutdown the executor service
        executorService.shutdown();

        /* TODO původní Singlethreaded
        System.out.println("------------------------------------------------------------------------");
        TimetableGenerator generator = new TimetableGenerator(subjects);
        long startTime = System.currentTimeMillis();
        List<byte[]> timetables = generator.generateTimetables(1_000_000);
        long endTime = System.currentTimeMillis();
        float elapsedTimeInSeconds = (endTime - startTime) / 1000.0f;
        System.out.println("Timetables: " + timetables.size());
        System.out.println("Elapsed Time: " + elapsedTimeInSeconds + " seconds");

        for (int i = 0; i < 5; i++) {
            byte[] timetable = timetables.get(i);
            String formattedTimetable = generator.formatTimetable(timetable);
            System.out.println("Timetable " + (i + 1) + ": \n" + formattedTimetable);
        }
         */

        long startTime2 = System.currentTimeMillis();
        System.out.println("------------------------------------------------------------------------");
        generator.timetableEvaluator(timetables);
        long endTime2 = System.currentTimeMillis();
        float elapsedTimeInSeconds2 = (endTime2 - startTime2) / 1000.0f;
        System.out.println("Elapsed Time: " + elapsedTimeInSeconds2 + " seconds");
        System.out.println("------------------------------------------------------------------------");
    }
}

