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

    public static void reserve() {
        String seatClass;
        boolean isWindow = false;
        boolean isAisle = false;
        boolean isTable = false;
        String eMail;

        System.out.println("- - Seat Booking System - -\n\n- - SEAT REQUIREMENTS - -\n");

        boolean valid = true;
        do {
            valid = true;
            System.out.println("STD or 1ST :");
            seatClass = keyboard.next().trim().toLowerCase();
            if (!(seatClass.equals("std") || seatClass.equals("1st"))) {
                valid = false;
                System.out.println("ERROR! An input of STD or 1ST is required!");
            }
        } while (valid == false);

        String userInput;
        do {
            valid = true;
            System.out.println("Window (Y/N) :");
            userInput = keyboard.next().trim().toLowerCase();
            if (userInput.equals("y")) {
                isWindow = true;
            } else if (userInput.equals("n")) {
                isWindow = false;
            } else {
                valid = false;
                System.out.println("ERROR! An input of Y or N is required!");
            }
        } while (valid == false);

        do {
            valid = true;
            System.out.println("Aisle (Y/N) :");
            userInput = keyboard.next().trim().toLowerCase();
            if (userInput.equals("y")) {
                isAisle = true;
            } else if (userInput.equals("n")) {
                isAisle = false;
            } else {
                valid = false;
                System.out.println("ERROR! An input of Y or N is required!");
            }
        } while (valid == false);

        do {
            valid = true;
            System.out.println("Table (Y/N) :");
            userInput = keyboard.next().trim().toLowerCase();
            if (userInput.equals("y")) {
                isTable = true;
            } else if (userInput.equals("n")) {
                isTable = false;
            } else {
                valid = false;
                System.out.println("ERROR! An input of Y or N is required!");
            }
        } while (valid == false);

        do {
            valid = true;
            System.out.println("Email :");
            eMail = keyboard.next();
            if (eMail.indexOf('@') < 0) {
                valid = false;
                System.out.println("ERROR! Email is invalid!");
            }
        } while (valid == false);

        sort(seatClass, isWindow, isAisle, isTable, eMail);
    }

    public static void sort(String seatClass, boolean isWindow, boolean isAisle, boolean isTable, String eMail) {
        int[] weightedList = new int[18];
        int closestMatch = -1;

        for(int i = 0; i < seatList.length; i++) {
            Reservation seat = seatList[i];
            int check = 0;

            if (seat.eMail.equals("free")) {
                if (seat.seatClass.equals(seatClass)) {
                    check++;
                }
                if (isWindow == seat.isWindow) {
                    check++;
                }
                if (isAisle == seat.isAisle) {
                    check++;
                }
                if (isTable == seat.isTable) {
                    check++;
                }
            }

            weightedList[i] = check;

            if (check > closestMatch) {
                closestMatch = check;
            }
        }

        if (closestMatch == 4) {
            System.out.println("- - Seat Booking System - -\n\n- - SEATS FOUND - -\nSEAT\tCLASS\tWINDOW\tAISLE\tTABLE\tPRICE\tEMAIL");
        } else if (closestMatch == -1) {
            System.out.println("- - Seat Booking System - -\n\n- - NO SEATS FOUND - -\nSEAT\tCLASS\tWINDOW\tAISLE\tTABLE\tPRICE\tEMAIL");
        } else {
            System.out.println("- - Seat Booking System - -\n\n- - CLOSEST MATCH - -\nSEAT\tCLASS\tWINDOW\tAISLE\tTABLE\tPRICE\tEMAIL");
        }

        for(int x = 0; x < seatList.length; x++) {
            if (weightedList[x] == closestMatch) {
                print(x);
            }
        }

        boolean valid = false;
        do {
            System.out.println("Specify which Seat Number you would like to book : ");
            String seatNum = keyboard.next().trim().toLowerCase();

            for(int y = 0; y < seatList.length; y++) {
                String seat = seatList[y].seatNum.toLowerCase();

                if (seat.equals(seatNum)) {
                    System.out.println("Are you sure you want to reserve Seat " + seatList[y].seatNum + " (Y/N) : ");
                    String userInput = keyboard.next().trim().toLowerCase();
                    if (userInput.equals("y")) {
                        valid = true;
                        seatList[y].eMail = eMail;
                        System.out.println("Successfully Reserved Seat : "+ seatList[y].seatNum);
                    } else if (userInput.equals("n")) {
                        valid = true;
                        System.out.println("Reservation has not been changed!");
                    } else {
                        valid = false;
                    }
                }
            }

            if (valid == false) {
                System.out.println("ERROR! The Seat Number entered is not valid!");
            }

        } while (valid == false);

    }

    public static void cancel() {
        boolean valid = false;
        String eMail;
        do {
            System.out.println("- - Seat Booking System - -\n\n- - CANCEL SEAT - -\nEmail :");
            eMail = keyboard.next();

            for(int i = 0; i < seatList.length; i++) {
                if (seatList[i].eMail.equals(eMail)) {
                    valid = true;
                    print(i);
                }
            }

            if (valid == false) {
                System.out.println("ERROR! There is NO Reservation under that Email!");
            }
        } while (valid == false);

        do {
            valid = false;

            System.out.println("Seat Number :");
            String seatNum = keyboard.next();

            int index = -1;
            for(int i = 0; i < seatList.length; i++) {
                if ((seatList[i].seatNum.toLowerCase().equals(seatNum)) && (seatList[i].eMail.equals(eMail))) {
                    index = i;
                }
            }

            if (index == -1) {
                System.out.println("ERROR! That Seat Number does not exist!");
            } else {
                System.out.println("- - RESERVATION - -\nSEAT\tCLASS\tWINDOW\tAISLE\tTABLE\tPRICE\tEMAIL");
                print(index);

                do {
                    System.out.println("Are you sure you want to cancel this reservation (Y/N) : ");
                    String userInput = keyboard.next().trim().toLowerCase();
                    if (userInput.equals("y")) {
                        valid = true;
                        seatList[index].eMail = "free";
                        System.out.println("Successfully CANCELLED the reservation!");
                        System.out.println("- - RESERVATION - -\nSEAT\tCLASS\tWINDOW\tAISLE\tTABLE\tPRICE\tEMAIL");
                        print(index);
                    } else if (userInput.equals("n")) {
                        valid = true;
                        System.out.println("Reservation has not been changed!");
                        System.out.println("- - RESERVATION - -\nSEAT\tCLASS\tWINDOW\tAISLE\tTABLE\tPRICE\tEMAIL");
                        print(index);
                    } else {
                        valid = false;
                    }
                } while (valid == false);
            }

        } while (valid == false);
    }

    public static void view() {
        System.out.println("- - Seat Booking System - -\n\n- - VIEW SEAT RESERVATIONS - -\nSEAT\tCLASS\tWINDOW\tAISLE\tTABLE\tPRICE\tEMAIL");

        for(int i = 0; i < seatList.length; i++) {
            print(i);
        }
    }

    public static void print(int listIndex) {
        Reservation seat = seatList[listIndex];
        System.out.println(seat.seatNum + "\t" + seat.seatClass + "\t" + seat.isWindow + "\t" + seat.isAisle + "\t" + seat.isTable + "\t" + seat.seatPrice + "\t" + seat.eMail);
    }

    public static void export() {
        try {
            String path = "seats.txt";
            FileWriter writer = new FileWriter(path);

            for(int i = 0; i < seatList.length; i++) {
                Reservation seat = seatList[i];
                writer.write(seat.seatNum + " " + seat.seatClass + " " + seat.isWindow + " " + seat.isAisle + " " + seat.isTable + " " + seat.seatPrice + " " + seat.eMail + "\n");
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("ERROR! Writing reservation file!");
            e.printStackTrace();
        }
    }
}