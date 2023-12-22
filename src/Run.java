import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Run class contains the main method to demonstrate multithreaded timetable generation and evaluation.
 */
public class Run {
    /**
     * The run method initializes subjects, creates a TimetableGenerator, generates timetables on multiple threads,
     * evaluates the generated timetables, and measures the elapsed time for both generation and evaluation.
     */
    public static void run() {
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
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Number of threads: " + numberOfThreads);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("(Generating 1_200_000 timetables takes around 50 seconds)");
        Scanner sc = new Scanner(System.in);
        int timetablesToGenerate;
        while (true) {
            System.out.println("How many timetables do you want to generate:");
            if (sc.hasNextInt()) {
                timetablesToGenerate = sc.nextInt();
                sc.nextLine();
                break;
            } else {
                sc.nextLine();
            }
        }
        System.out.println("------------------------------------------------------------------------");

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
