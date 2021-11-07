package ui;

import java.util.Scanner;

public class UserInterface {
    private Scanner sc;

    public UserInterface() {
        sc = new Scanner(System.in);
    }

    public String getInputString(String prompt) {
        System.out.println(prompt);
        String input = sc.nextLine();
        return input.trim();
    }

    public int getInputInt(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                int input = sc.nextInt();
                sc.nextLine();

                return input;
            } catch (Exception ex) {
                System.out.println("Please enter a valid int");
                sc.nextLine();
            }
        }
    }

    public int getInputInt(String prompt, int min, int max) {
        while (true) {
            int input = getInputInt(prompt);
            if (input > max || input < min) {
                System.out.println("Please enter a value between " + min + " and " + max);
            } else {
                return input;
            }
        }
    }

    public double getInputDouble(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                double input = sc.nextDouble();
                sc.nextLine();

                return input;
            } catch (Exception ex) {
                System.out.println("Please enter a valid double");
                sc.nextLine();
            }
        }
    }

    public double getInputDouble(String prompt, double min, double max) {
        while (true) {
            double input = getInputDouble(prompt);
            if (input > max || input < min) {
                System.out.println("Please enter a value between " + min + " and " + max);
            } else {
                return input;
            }
        }
    }
}
