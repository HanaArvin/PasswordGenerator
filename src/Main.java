import java.security.SecureRandom;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\t\t\t\t\t\t\t\t\t\t\t\t------------------------------------");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\tWelcome to Password Generator");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t------------------------------------");
        System.out.println("\nProtect your accounts with strong and reliable passwords!");
        int length;
        while (true) {
            length = getValidInt(scanner, "\n\t\tEnter desired password length: ");
            if (length < 4 || length > 128) {
                System.out.println("Please choose a length between 4 and 128.");
            } else {
                break;
            }
        }
        String letters;
        String digits;
        String characters;
        boolean flag = false;
        while (!flag) {
            letters = getYesOrNo(scanner, "\n\t\tInclude uppercase letters? (y/n): ");
            System.out.println(letters.equalsIgnoreCase("y") ? "\t\t✔ Uppercase letters included" : "\t\t✘ Uppercase letters excluded");
            digits = getYesOrNo(scanner, "\n\t\tInclude digits? (y/n): ");
            System.out.println(digits.equalsIgnoreCase("y") ? "\t\t✔ Digits included" : "\t\t✘ Digits excluded");
            characters = getYesOrNo(scanner, "\n\t\tInclude special characters? (y/n): ");
            System.out.println(characters.equalsIgnoreCase("y") ? "\t\t✔ Special characters included" : "\t\t✘ Special characters excluded");
            if (letters.equalsIgnoreCase("n") && digits.equalsIgnoreCase("n") && characters.equalsIgnoreCase("n")) {
                int choice;
                while (true) {
                    System.out.println("No character types selected!\nDo you want to:\n(1) Pick at least one type\n(2) Continue with lowercase only (weak password)");
                    System.out.print("Enter 1 or 2: ");
                    if (scanner.hasNextInt()) {
                        choice = scanner.nextInt();
                        scanner.nextLine();
                        if (choice == 1) {
                            break;
                        } else if (choice == 2) {
                            flag = true;
                            break;
                        } else {
                            System.out.println("Please enter 1 or 2 only!");
                        }
                    } else {
                        System.out.println("Please enter a number!");
                        scanner.nextLine();
                    }
                }
            } else {
                flag = true;
            }
            if (flag) {
                String generatedPassword = GeneratePassword(length, letters, digits, characters);
                System.out.println("\nGenerated Password: " + generatedPassword);
                System.out.println("Password Strength: " + evaluateStrength(generatedPassword));
            }
        }
        scanner.close();
    }


    public static int getValidInt(Scanner scanner, String massage) {
        int number;
        while (true) {
            System.out.print(massage);
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                System.out.println("Error: Please enter integer number!");
                scanner.next();
            }
        }
        return number;
    }

    public static String getYesOrNo(Scanner scanner, String massage) {
        String input;
        while (true) {
            System.out.print(massage);
            input = scanner.next();
            if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n")) {
                break;
            } else {
                System.out.println("Error: Please enter 'y' or 'n' !");
            }
        }
        return input;
    }

    public static String GeneratePassword(int length, String letters, String digits, String characters) {
        StringBuilder sb = new StringBuilder("abcdefghijklmnopqrstuvwxyz");
        if (letters.equalsIgnoreCase("y")) {
            sb.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        }
        if (digits.equalsIgnoreCase("y")) {
            sb.append("0123456789");
        }
        if (characters.equalsIgnoreCase("y")) {
            sb.append("!@#$%^&*()-_=+[]{};:,.<>?/");
        }
        String pool = sb.toString();
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(pool.length());
            password.append(pool.charAt(index));
        }
        return password.toString();
    }

    public static String evaluateStrength(String password) {
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSpecial = true;
            }
        }
        int count = 0;
        if (hasLower) count++;
        if (hasUpper) count++;
        if (hasDigit) count++;
        if (hasSpecial) count++;
        if (password.length() >= 12 && count >= 3) {
            return "Strong";
        } else if (password.length() >= 8 && count >= 2) {
            return "Medium";
        } else {
            return "Weak";
        }
    }
}