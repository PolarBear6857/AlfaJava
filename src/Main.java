import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * The Main class contains the main method to demonstrate multithreaded timetable generation and evaluation.
 */
public class Main {

    /**
     * The main method initializes subjects, creates a TimetableGenerator, generates timetables on multiple threads,
     * evaluates the generated timetables, and measures the elapsed time for both generation and evaluation.
     *
     * @param args The command line arguments (not used in this example).
     */
    public static void main(String[] args) {
        // Initialization of subjects
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

        // Creating a TimetableGenerator
        TimetableGenerator generator = new TimetableGenerator(subjects);

        // Configuration for multithreaded timetable generation
        int numberOfThreads = 6;
        int timetablesToGenerate = 1_200_000;

        // Using ExecutorService to manage threads
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        List<CompletableFuture<List<byte[]>>> futures = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        int workloadPerThread = timetablesToGenerate / numberOfThreads;

        // Generating timetables on multiple threads
        for (int i = 0; i < numberOfThreads; i++) {
            CompletableFuture<List<byte[]>> future = CompletableFuture.supplyAsync(() ->
                    generator.generateTimetables(workloadPerThread), executorService);
            futures.add(future);
        }

        // Combining results from all threads
        List<byte[]> timetables = futures.stream()
                .flatMap(future -> future.join().stream())
                .toList();

        // Measuring elapsed time for timetable generation
        long endTime = System.currentTimeMillis();
        float elapsedTimeInSeconds = (endTime - startTime) / 1000.0f;

        // Printing results for timetable generation
        System.out.println("Timetables: " + timetables.size());
        System.out.println("Elapsed Time (Generation): " + elapsedTimeInSeconds + " seconds");

        // Shutting down the executor service
        executorService.shutdown();

        /* TODO původní Singlethreaded
        System.out.println("------------------------------------------------------------------------");
        TimetableGenerator generator = new TimetableGenerator(subjects);
        long startTime = System.currentTimeMillis();
        List<byte[]> timetables = generator.generateTimetables(1_000);
        long endTime = System.currentTimeMillis();
        float elapsedTimeInSeconds = (endTime - startTime) / 1000.0f;
        System.out.println("Timetables: " + timetables.size());
        System.out.println("Elapsed Time: " + elapsedTimeInSeconds + " seconds");

        for (int i = 0; i < 1; i++) {
            byte[] timetable = timetables.get(i);
            String formattedTimetable = generator.formatTimetable(timetable);
            System.out.println("Timetable " + (i + 1) + ": \n" + formattedTimetable);
        }
         */

        // Evaluating generated timetables
        long startTime2 = System.currentTimeMillis();
        System.out.println("------------------------------------------------------------------------");
        generator.timetableEvaluator(timetables);
        long endTime2 = System.currentTimeMillis();
        float elapsedTimeInSeconds2 = (endTime2 - startTime2) / 1000.0f;

        // Printing results for timetable evaluation
        System.out.println("Elapsed Time (Evaluation): " + elapsedTimeInSeconds2 + " seconds");
        System.out.println("------------------------------------------------------------------------");
    }
}
