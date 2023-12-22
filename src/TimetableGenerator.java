import java.util.*;

/**
 * The TimetableGenerator class is responsible for generating and evaluating timetables for a given set of subjects.
 */
public class TimetableGenerator {
    private final List<Subject> subjects;
    private final Map<Byte, Subject> subjectCodeMap;

    /**
     * Constructs a TimetableGenerator with the provided list of subjects.
     *
     * @param subjects The list of subjects to be used for timetable generation.
     */
    public TimetableGenerator(List<Subject> subjects) {
        this.subjects = subjects;
        this.subjectCodeMap = createSubjectCodeMap();
    }

    private Map<Byte, Subject> createSubjectCodeMap() {
        Map<Byte, Subject> map = new HashMap<>();
        byte code = 1;
        for (Subject subject : subjects) {
            map.put(code++, subject);
        }
        return map;
    }


    /**
     * Generates a specified number of timetables.
     *
     * @param numTimetables The number of timetables to generate.
     * @return A list of generated timetables.
     */
    public List<byte[]> generateTimetables(int numTimetables) {
        List<byte[]> timetables = new ArrayList<>();
        for (int i = 0; i < numTimetables; i++) {
            byte[] timetable = generateSingleTimetable();
            timetables.add(timetable);
        }
        return timetables;
    }

    private byte[] generateSingleTimetable() {
        byte[] timetable = new byte[50];
        List<Byte> availableSubjects = new ArrayList<>(subjectCodeMap.keySet());

        for (int day = 0; day < 5; day++) {
            if (dayHasLunchBreak(day)) {
                timetable[day * 10 + 4] = -1;
            }

            for (int hour = 0; hour < 10; hour++) {
                Collections.shuffle(availableSubjects);

                for (Byte code : availableSubjects) {
                    if (consecutiveHoursConstraintSatisfied(code, timetable, day, hour)) {
                        timetable[day * 10 + hour] = code;

                        Subject subject = subjectCodeMap.get(code);
                        subject.decrementHours();

                        subject.setClassroom(generateRandomClassroom());

                        if (timetable[day * 10 + hour] == -1 && dayHasLunchBreak(day)) {
                            int nextHour = hour + 1;
                            if (nextHour < 10 && timetable[day * 10 + nextHour] != 0) {
                                break;
                            }
                        }

                        if (subject.getHours() == 0) {
                            availableSubjects.remove(code);
                        }

                        break;
                    }
                }
            }
        }

        return timetable;
    }

    private String generateRandomClassroom() {
        String[] classrooms = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"};
        return classrooms[new Random().nextInt(classrooms.length)];
    }


    private boolean consecutiveHoursConstraintSatisfied(byte code, byte[] timetable, int day, int hour) {
        return true;
    }

    private boolean dayHasLunchBreak(int day) {
        return day == 0 || day == 2 || day == 4;
    }

    /**
     * Formats a timetable into a human-readable string.
     *
     * @param timetable The timetable to be formatted.
     * @return The formatted timetable as a string.
     */
    public String formatTimetable(byte[] timetable) {
        StringBuilder formattedTimetable = new StringBuilder();
        for (int day = 0; day < 5; day++) {
            formattedTimetable.append("(day ").append(day + 1).append("):");
            for (int hour = 0; hour < 10; hour++) {
                byte code = timetable[day * 10 + hour];
                String subjectInfo;
                if (code == -1) {
                    subjectInfo = "Lunch Break";
                } else {
                    Subject subject = subjectCodeMap.get(code);
                    String subjectName = subject.getCode();
                    String classroom = subject.getClassroom();
                    subjectInfo = "(" + subjectName + ", " + classroom + ")";
                }
                formattedTimetable.append("\t").append(subjectInfo);
            }
            formattedTimetable.append("\n");
        }
        return formattedTimetable.toString();
    }

    /**
     * Evaluates a list of timetables and prints the timetable with the highest score.
     *
     * @param timetables The list of timetables to be evaluated.
     */
    public void timetableEvaluator(List<byte[]> timetables) {
        Map<byte[], Integer> ohodnoceneRozvrhy = new HashMap<>();
        for (byte[] b : timetables) {
            ohodnoceneRozvrhy.put(b, TimetableEvaluator.evaluateTimetable(b, subjectCodeMap));
        }
        Map.Entry<byte[], Integer> maxEntry = null;
        for (Map.Entry<byte[], Integer> entry : ohodnoceneRozvrhy.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }

        if (maxEntry != null) {
            System.out.println("Rozvrh s největším skóre: \n" + formatTimetable(maxEntry.getKey()));
            System.out.println("Skóre: " + maxEntry.getValue());
        } else {
            System.out.println("Mapa je prázdná.");
        }
    }

}
