package sem2.inf101.v18.sem2.FourInARow.Utils;

import java.util.Scanner;

/**
 * Collection of input checks for user input.
 *
 */
public class InputCheck {

    /**
     * Small method for checking the user's input for int values.
     * Input params are exclusive.
     *
     * @param low Lowest value.
     * @param high Highest value.
     * @param type Type of the check. Only one for now.
     * @return  Correct input value.
     */
    public static int checkInputInt(int low, int high, int type) {
        int userInt = 2;
        Scanner inp = new Scanner(System.in);

        if (type == 0) {
            while (true) {
                while (!inp.hasNextInt()) {
                    System.out.println("Wrong input, try again.");
                    inp.nextLine();
                }

                while (inp.hasNextInt()) {
                    userInt = inp.nextInt();
                    if (userInt < low || userInt > high) {
                        System.out.println("Wrong input, try again.");
                    } else {
                        return userInt;
                    }
                }
                inp.nextLine();
            }
        }
        return userInt;
    }

    /**
     * Check the String input from the user.
     * Type of the check depends on the param. type.
     *
     * @param type 1 to check the "yes/no" input.
     *             0 returns whole String without check.
     *
     * @return Valid String.
     */
    public static String checkInputStr (int type) {
        String userStrng = "";
        Scanner inp = new Scanner(System.in);

        if (type == 1) {
            while (inp.hasNextLine()) {
                userStrng = inp.nextLine();

                if (userStrng.equalsIgnoreCase("y") ||
                        (userStrng.equalsIgnoreCase("yes"))) {
                    userStrng = "y";
                    return userStrng;
                }

                if (userStrng.equalsIgnoreCase("n") ||
                        userStrng.equalsIgnoreCase("no")) {
                    userStrng = "n";
                    return userStrng;
                }

                 else {
                    System.out.println("Wrong input, try again.");
                }
            }
        } else if (type == 0) {
            userStrng = inp.nextLine();
            return userStrng;
        }

        inp.nextLine();
        return userStrng;
    }
}
