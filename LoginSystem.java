package com.javaPrograms.LoginValid;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LoginSystem {
    private static final HashMap<String, User> users = new HashMap<>();
    private static final String ADMIN_PASSWORD = "Shivani1234"; 
    private static User loggedInUser = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select an option:\n1. Register\n2. Log in\n3. Log out\n4. Operate");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    System.out.println("Logged out");
                    System.exit(0);
                    break;
                case 4:
                    operate(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void registerUser(Scanner scanner) {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        String email;
        while (true) {
            System.out.print("Enter Email/User ID: ");
            email = scanner.nextLine();
            if (email.endsWith("@gmail.com")) {
                break;
            }
            System.out.println("Invalid username, try again.");
        }

        String dob;
        while (true) {
            System.out.print("Enter Date of Birth (dd/mm/yyyy): ");
            dob = scanner.nextLine();
            if (isValidDate(dob)) {
                dob = formatDOB(dob);
                break;
            }
            System.out.println("Invalid date format or out-of-range date, try again.");
        }

        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();

        String password = generatePassword();
        System.out.println("Generated Password: " + password);

        users.put(email, new User(name, email, dob, gender, password));
        System.out.println("User registered successfully!");
    }

    private static boolean isValidDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate parsedDate = LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private static String formatDOB(String dob) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(dob, inputFormatter);
        return date.format(outputFormatter);
    }

    private static void loginUser(Scanner scanner) {
        System.out.print("Enter Email/User ID: ");
        String email = scanner.nextLine();

        if (!users.containsKey(email)) {
            System.out.println("Wrong username");
            return;
        }

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = users.get(email);
        if (!user.getPassword().equals(password)) {
            System.out.println("Wrong password");
        } else {
            loggedInUser = user;
            System.out.println("Logged in successfully");
        }
    }

    private static String generatePassword() {
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialChars = "!@#$%^&*()-_+=";

        StringBuilder password = new StringBuilder();
        Random random = new Random();

        password.append(uppercase.charAt(random.nextInt(uppercase.length())));
        password.append(lowercase.charAt(random.nextInt(lowercase.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));

        String allChars = uppercase + lowercase + digits + specialChars;
        while (password.length() < 8) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        return shuffleString(password.toString());
    }

    private static String shuffleString(String input) {
        char[] characters = input.toCharArray();
        Random random = new Random();
        for (int i = 0; i < characters.length; i++) {
            int randomIndex = random.nextInt(characters.length);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        return new String(characters);
    }

    private static void operate(Scanner scanner) {
        System.out.print("Enter the admin password: ");
        String adminPassword = scanner.nextLine();

        if (ADMIN_PASSWORD.equals(adminPassword)) {
            System.out.println("Authentication successful. Displaying all registered users:");
            for (Map.Entry<String, User> entry : users.entrySet()) {
                User user = entry.getValue();
                System.out.println("Name: " + user.getName() + ", Email: " + user.getEmail() +
                        ", Date of Birth: " + user.getDob() + ", Gender: " + user.getGender());
            }
        } else {
            System.out.println("Authentication failed. Returning to main menu.");
        }
    }

    static class User {
        private final String name;
        private final String email;
        private final String dob;
        private final String gender;
        private final String password;

        public User(String name, String email, String dob, String gender, String password) {
            this.name = name;
            this.email = email;
            this.dob = dob;
            this.gender = gender;
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getDob() {
            return dob;
        }

        public String getGender() {
            return gender;
        }

        public String getPassword() {
            return password;
        }
    }
}
