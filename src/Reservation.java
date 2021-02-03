public class Reservation {
    String seatNum;
    String seatClass;
    boolean isWindow;
    boolean isAisle;
    boolean isTable;
    double seatPrice;
    String eMail = "free";

    public Reservation(String seatNum, String seatClass, boolean isWindow, boolean isAisle, boolean isTable, double seatPrice, String eMail) {
        this.seatNum = seatNum;
        this.seatClass = seatClass;
        this.isWindow = isWindow;
        this.isAisle = isAisle;
        this.isTable = isTable;
        this.seatPrice = seatPrice;
        this.eMail = eMail;
    }
}
