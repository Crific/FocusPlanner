public class Entry {
    // Variables for the entry object
    int day;
    String month;
    int year;
    String description;

    // creating the entry object
    public Entry(int day, String month, int year, String description) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.description = description;
    }

    public Entry() {

    }

    // initiating getters and setters

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDay() {
        return day;
    }
    public String getMonth() {
            return month;
    }
    public int getYear() {
        return year;
    }
    public String getDescription() {
        return description;
    }


    @Override
    public String toString(){
        return "Date:\n" + day + " " + month + " " + year + "\nNotes:\n" + description;
    }

}
