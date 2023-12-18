import java.util.*;

public class TimetableGenerator {
    private List<Subject> subjects;
    private Map<Byte, Subject> subjectCodeMap;

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

        for (int day = 0; day < 5; day++) {
            if (dayHasLunchBreak(day)) {
                // Use a special code for lunch break
                timetable[day * 10 + 4] = -1;
            }

            for (int hour = 0; hour < 10 && !availableSubjects.isEmpty(); hour++) {
                byte code = availableSubjects.remove(0);

                // Check constraints for consecutive hours
                if (!consecutiveHoursConstraintSatisfied(code, timetable, day, hour)) {
                    availableSubjects.add(code);
                    continue;
                }

                timetable[day * 10 + hour] = code;
                subjectCodeMap.get(code).decrementHours();

                // Check if lunch break needs to be skipped
                if (timetable[day * 10 + hour] == -1 && dayHasLunchBreak(day)) {
                    int nextHour = hour + 1;
                    if (nextHour < 10 && timetable[day * 10 + nextHour] != 0) {
                        availableSubjects.add(code);
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

    public String formatTimetable(byte[] timetable) {
        StringBuilder formattedTimetable = new StringBuilder();
        for (int day = 0; day < 5; day++) {
            formattedTimetable.append("(day ").append(day + 1).append("):");
            for (int hour = 0; hour < 10; hour++) {
                byte code = timetable[day * 10 + hour];
                if (code == -1) {
                    formattedTimetable.append("\t(Lunch Break)");
                } else {
                    formattedTimetable.append("\t(").append(code).append(")");
                }
            }
            formattedTimetable.append("\n");
        }
        return formattedTimetable.toString();
    }

}
