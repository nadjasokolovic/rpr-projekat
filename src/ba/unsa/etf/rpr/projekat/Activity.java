package ba.unsa.etf.rpr.projekat;

public class Activity {
    private int id, year;
    private String month;

    public Activity() {
    }

    public Activity(int id, String month, int year) {
        this.id = id;
        this.year = year;
        this.month = month;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
