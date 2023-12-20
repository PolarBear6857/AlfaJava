import java.util.*;

public class TimetableEvaluator {

    private static final int RULE_ONE_PENALTY = 50;
    private static final int RULE_TWO_PENALTY = 30;
    private static final int RULE_THREE_PENALTY = 20;
    private static final int RULE_FOUR_PENALTY = 40;
    private static final int RULE_FIVE_PENALTY = 10;
    private static final int RULE_SIX_PENALTY = 25;
    private static final int RULE_SEVEN_PENALTY = 15;
    private static final int RULE_EIGHT_PENALTY = 5;
    private static final int RULE_NINE_PENALTY = 10;
    private static final int RULE_TEN_BONUS = 15;

    public static int evaluateTimetable(byte[] timetable, Map<Byte, Subject> subjectCodeMap) {
        int penalty = 0;

        penalty += ruleOne(timetable);
        penalty += ruleTwo(timetable);
        penalty += ruleThree(timetable);
        penalty += ruleFour(timetable);
        penalty += ruleFive(timetable);
        penalty += ruleSix(timetable, subjectCodeMap);
        penalty += ruleSeven(timetable, subjectCodeMap);
        penalty += ruleEight(timetable);
        penalty += ruleNine(timetable);
        penalty += ruleTen(timetable);

        return penalty;
    }

    private static int ruleOne(byte[] timetable) {
        int penalty = 0;

        if (timetable[4] == 0) {
            penalty += RULE_ONE_PENALTY;
        }

        return penalty;
    }

    private static int ruleTwo(byte[] timetable) {
        int penalty = 0;

        Set<Byte> subjectsInDay = new HashSet<>();
        for (int day = 0; day < 5; day++) {
            for (int hour = 0; hour < 10; hour++) {
                byte code = timetable[day * 10 + hour];
                if (code != 0) {
                    if (!subjectsInDay.add(code)) {
                        penalty += RULE_TWO_PENALTY;
                    }
                }
            }
            subjectsInDay.clear();
        }

        return penalty;
    }

    private static int ruleThree(byte[] timetable) {
        int penalty = 0;

        for (int day = 0; day < 5; day++) {
            for (int hour = 1; hour < 10; hour++) {
                byte currentCode = timetable[day * 10 + hour];
                byte previousCode = timetable[day * 10 + hour - 1];

                if (currentCode != 0 && previousCode != 0) {
                    if (Math.abs(currentCode - previousCode) > 1) {
                        penalty += RULE_THREE_PENALTY;
                    }
                }
            }
        }

        return penalty;
    }

    private static int ruleFour(byte[] timetable) {
        int penalty = 0;

        for (int day = 0; day < 5; day++) {
            byte lunchHour = (byte) findLunchHour(timetable, day);
            if (lunchHour != -1 && (lunchHour < 4 || lunchHour > 7)) {
                penalty += RULE_FOUR_PENALTY;
            }
        }

        return penalty;
    }

    private static int ruleFive(byte[] timetable) {
        int penalty = 0;

        for (int day = 0; day < 5; day++) {
            int dailyHours = countDailyHours(timetable, day);
            if (dailyHours > 8) {
                penalty += RULE_FIVE_PENALTY;
            }
        }

        return penalty;
    }

    private static int ruleSix(byte[] timetable, Map<Byte, Subject> subjectCodeMap) {
        int penalty = 0;

        int hoursInDay = timetable.length / 5;
        for (int day = 0; day < 5; day++) {
            for (int hour = 0; hour < hoursInDay; hour++) {
                int index = day * hoursInDay + hour;
                if (index < timetable.length - 1) {
                    byte currentCode = timetable[index];
                    byte nextCode = timetable[index + 1];

                    if (currentCode != 0 && nextCode != 0 && nextCode != currentCode + 1) {
                        int classHours = getClassHours(subjectCodeMap.get(currentCode));
                        if (classHours > 1) {
                            for (int i = 1; i < classHours && (index + 1 + i) < timetable.length; i++) {
                                byte nextHourCode = timetable[index + 1 + i];
                                if (nextHourCode != currentCode + 1 + i) {
                                    penalty += RULE_SIX_PENALTY;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return penalty;
    }


    private static int ruleSeven(byte[] timetable, Map<Byte, Subject> subjectCodeMap) {
        int penalty = 0;

        for (int day = 0; day < 5; day++) {
            for (int hour = 0; hour < 2; hour++) {
                byte code = timetable[day * 10 + hour];
                if (code != 0 && isMathSubject(subjectCodeMap.get(code))) {
                    penalty += RULE_SEVEN_PENALTY;
                }
            }
            for (int hour = 4; hour < 10; hour++) {
                byte code = timetable[day * 10 + hour];
                if (code != 0 && isMathSubject(subjectCodeMap.get(code))) {
                    penalty += RULE_SEVEN_PENALTY;
                }
            }
        }

        return penalty;
    }

    private static int ruleEight(byte[] timetable) {
        int penalty = 0;

        return penalty;
    }

    private static int ruleNine(byte[] timetable) {
        int penalty = 0;

        return penalty;
    }

    private static int ruleTen(byte[] timetable) {
        int bonus = 0;

        return bonus;
    }

    private static int findLunchHour(byte[] timetable, int day) {
        for (int hour = 4; hour < 8; hour++) {
            if (timetable[day * 10 + hour] == -1) {
                return hour;
            }
        }
        return (byte) -1;
    }

    private static int countDailyHours(byte[] timetable, int day) {
        int count = 0;
        for (int hour = 0; hour < 10; hour++) {
            if (timetable[day * 10 + hour] != 0) {
                count++;
            }
        }
        return count;
    }

    private static int getClassHours(Subject subject) {
        return subject.getHours();
    }

    private static boolean isMathSubject(Subject subject) {
        return "M, Hr".equals(subject.getCode());
    }

}
