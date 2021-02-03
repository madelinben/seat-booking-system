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

    public static void setup() {
        try {
            String path = "seats.txt";
            Scanner reader = new Scanner(new FileReader(path));

            int counter = 0;
            while (reader.hasNext()) {
                String seatNum = reader.next();
                String seatClass = reader.next();
                boolean isWindow = reader.nextBoolean();
                boolean isAisle = reader.nextBoolean();
                boolean isTable = reader.nextBoolean();
                double seatPrice = reader.nextDouble();
                String eMail = reader.next();

                seatList[counter] = new Reservation(seatNum, seatClass, isWindow, isAisle, isTable, seatPrice, eMail);
                counter++;
            }
            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("ERROR! Reading reservation file!");
            e.printStackTrace();
        }
    }

    public static void menu() {
        boolean valid = false;
        do {
            System.out.println("- - Seat Booking System - -\n\n- - MAIN MENU - -\n1 - Reserve Seat\n2 - Cancel Seat\n3 - View Seat Reservations\nQ - Quit\nPick :");
            String userInput = keyboard.next().trim().toLowerCase();

            if (userInput.equals("1")) {
                valid = true;
                reserve();
            } else if (userInput.equals("2")) {
                valid = true;
                cancel();
            } else if (userInput.equals("3")) {
                valid = true;
                view();
            } else if (userInput.equals("q")) {
                valid = true;
                export();
                System.exit(0);
            } else {
                valid = false;
                System.out.println("ERROR! Input must be a number between 1 and 3, or the character Q to exit the program!");
            }
        } while (valid == false);
    }
}