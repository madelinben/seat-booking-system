import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SeatBookingSystem {

    public static Reservation[] seatList = new Reservation[18];
    //seatNum seatClass isWindow isAisle isTable seatPrice eMail
    //String  String    Boolean  Boolean Boolean Double    String

    static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {
        boolean repeat = false;

        setup();

        do {
            menu();
            System.out.println("Continue... (Y/N) :");
            String userInput = keyboard.next().trim().toLowerCase();
            if (userInput.equals("y")) {
                repeat = true;
            } else if (userInput.equals("n")) {
                repeat = false;
            }
        } while (repeat == true);

        export();

        System.exit(0);
    }
}