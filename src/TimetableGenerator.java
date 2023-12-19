import java.util.*;

public class TimetableGenerator {
    private final List<Subject> subjects;
    private final Map<Byte, Subject> subjectCodeMap;

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
        Collections.shuffle(availableSubjects);

        int lunchDay = getRandomDayWithLunchBreak();

        for (int day = 0; day < 5; day++) {
            if (day == lunchDay) {
                // Use a special code for lunch break
                timetable[day * 10 + 5] = -1;
            }

            for (int hour = 0; hour < 10; hour++) {
                // Shuffle available subjects for each hour to get randomness
                Collections.shuffle(availableSubjects);

                for (Byte code : availableSubjects) {
                    // Check constraints for consecutive hours
                    if (consecutiveHoursConstraintSatisfied(code, timetable, day, hour)) {
                        timetable[day * 10 + hour] = code;
                        subjectCodeMap.get(code).decrementHours();

                        // Check if lunch break needs to be skipped
                        if (timetable[day * 10 + hour] == -1 && dayHasLunchBreak(day)) {
                            int nextHour = hour + 1;
                            if (nextHour < 10 && timetable[day * 10 + nextHour] != 0) {
                                break; // Break if lunch break needs to be skipped
                            }
                        }

                        // Remove the subject from availableSubjects only if it has no more hours left
                        if (subjectCodeMap.get(code).getHours() == 0) {
                            availableSubjects.remove(code);
                        }

                        break; // Break after successfully scheduling a subject for the hour
                    }
                }
            }
        }

        return timetable;
    }


    private boolean consecutiveHoursConstraintSatisfied(byte code, byte[] timetable, int day, int hour) {
        // Implement your specific constraints for consecutive hours
        return true;
    }

    private boolean dayHasLunchBreak(int day) {
        // For simplicity, let's assume a lunch break on days 1, 3, and 5
        return day == 0 || day == 2 || day == 4;
    }

    private int getRandomDayWithLunchBreak() {
        // Return a random day between lesson 5 and 7 for the lunch break
        return new Random().nextInt(3) * 2 + 1;
    }

    public String formatTimetable(byte[] timetable) {
        StringBuilder formattedTimetable = new StringBuilder();
        for (int day = 0; day < 5; day++) {
            formattedTimetable.append("(day ").append(day + 1).append("):");
            for (int hour = 0; hour < 10; hour++) {
                byte code = timetable[day * 10 + hour];
                String subjectName;
                if (code == -1) {
                    subjectName = "Lunch Break";
                } else {
                    subjectName = subjectCodeMap.get(code).getCode();
                }
                formattedTimetable.append("\t(").append(subjectName).append(")");
            }
            formattedTimetable.append("\n");
        }
        return formattedTimetable.toString();
    }
}
