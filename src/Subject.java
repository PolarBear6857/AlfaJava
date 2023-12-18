public class Subject {
    private String name;
    private String code;
    private int hours;

    private int remainingHours;

    public Subject(String name, String code, int hours) {
        this.name = name;
        this.code = code;
        this.hours = hours;
        this.remainingHours = hours;
    }

    // Gettery a Settery pro name, code, hours

    public int getRemainingHours() {
        return remainingHours;
    }

    public void decrementHours() {
        if (remainingHours > 0) {
            remainingHours--;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
