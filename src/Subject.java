/**
 * The Subject class represents a subject in a school curriculum, including information such as name, code,
 * allocated hours, remaining hours, teacher, and assigned classroom.
 */
public class Subject {

    private String name;
    private String code;
    private int hours;
    private int remainingHours;
    private String teacher;
    private String classroom;

    /**
     * Constructs a Subject with the specified name, code, and allocated hours.
     *
     * @param name  The name of the subject.
     * @param code  The code or abbreviation for the subject.
     * @param hours The total hours allocated to the subject.
     */
    public Subject(String name, String code, int hours) {
        this.name = name;
        this.code = code;
        this.hours = hours;
        this.remainingHours = hours;
        this.teacher = "";
        this.classroom = "";
    }

    /**
     * Gets the remaining hours for the subject.
     *
     * @return The remaining hours for the subject.
     */
    public int getRemainingHours() {
        return remainingHours;
    }

    /**
     * Decreases the remaining hours for the subject by one, if there are remaining hours.
     */
    public void decrementHours() {
        if (remainingHours > 0) {
            remainingHours--;
        }
    }

    /**
     * Gets the name of the subject.
     *
     * @return The name of the subject.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the subject.
     *
     * @param name The new name for the subject.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the code or abbreviation for the subject.
     *
     * @return The code or abbreviation for the subject.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code or abbreviation for the subject.
     *
     * @param code The new code or abbreviation for the subject.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the total hours allocated to the subject.
     *
     * @return The total hours allocated to the subject.
     */
    public int getHours() {
        return hours;
    }

    /**
     * Sets the total hours allocated to the subject.
     *
     * @param hours The new total hours for the subject.
     */
    public void setHours(int hours) {
        this.hours = hours;
    }

    /**
     * Gets the teacher assigned to the subject.
     *
     * @return The teacher assigned to the subject.
     */
    public String getTeacher() {
        return teacher;
    }

    /**
     * Sets the teacher assigned to the subject.
     *
     * @param teacher The new teacher for the subject.
     */
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    /**
     * Gets the assigned classroom for the subject.
     *
     * @return The assigned classroom for the subject.
     */
    public String getClassroom() {
        return classroom;
    }

    /**
     * Sets the assigned classroom for the subject.
     *
     * @param classroom The new assigned classroom for the subject.
     */
    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
