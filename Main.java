
package quickchat2;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Login login = new Login();

        System.out.println("==================================");
        System.out.println("       WELCOME TO QUICKCHAT");
        System.out.println("==================================");

        // Register the user
        System.out.println("\n--- USER REGISTRATION ---");

        System.out.print("Enter username: ");
        String username = input.nextLine();

        System.out.print("Enter password: ");
        String password = input.nextLine();

        System.out.print("Enter cellphone number (+27...): ");
        String cellphone = input.nextLine();

        String registrationResult =
                login.registerUser(username, password, cellphone);

        System.out.println(registrationResult);

        if (!registrationResult.equals("User registered successfully.")) {
            System.out.println(
                    "Registration unsuccessful. Please run the program again.");
            input.close();
            return;
        }

        // Login
        System.out.println("\n--- USER LOGIN ---");

        System.out.print("Enter username: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter password: ");
        String loginPassword = input.nextLine();

        boolean loggedIn =
                login.loginUser(loginUsername, loginPassword);

        System.out.println(login.returnLoginStatus(loggedIn));

        if (!loggedIn) {
            input.close();
            return;
        }

        // Main menu
        int choice = 0;

        while (choice != 3) {

            System.out.println("\n==================================");
            System.out.println("          QUICKCHAT MENU");
            System.out.println("==================================");
            System.out.println("1. Send a message");
            System.out.println("2. Coming Soon");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");

            if (!input.hasNextInt()) {
                System.out.println("Please enter 1, 2, or 3.");
                input.nextLine();
                continue;
            }

            choice = input.nextInt();
            input.nextLine();

            switch (choice) {

                case 1:
                    sendMessage(input);
                    break;

                case 2:
                    System.out.println("Coming Soon.");
                    break;

                case 3:
                    System.out.println("Thank you for using QuickChat!");
                    break;

                default:
                    System.out.println(
                            "Invalid option. Choose 1, 2, or 3.");
            }
        }

        System.out.println(
                "Total messages sent: "
                + Message.returnTotalMessagess());

        input.close();
    }

    // Method to create and send a message
    private static void sendMessage(Scanner input) {

        System.out.print("Enter recipient cellphone (+27...): ");
        String recipient = input.nextLine();

        System.out.print("Enter your message: ");
        String messageText = input.nextLine();

        Message message = new Message(
                Message.returnTotalMessagess(),
                recipient,
                messageText
        );

        if (!message.checkMessageID()) {
            System.out.println("Invalid message ID.");
            return;
        }

        if (!message.checkRecipientCell()) {
            System.out.println(
                    "Invalid cellphone number. Use +27 followed by 9 digits.");
            return;
        }

        if (!message.checkMessageLength()) {
            System.out.println(
                    "Message must contain between 1 and 250 characters.");
            return;
        }

        System.out.println("\n--- MESSAGE DETAILS ---");
        System.out.println("Message ID: " + message.getMessageID());
        System.out.println("Message Hash: " + message.createMessageHash());
        System.out.println("Recipient: " + message.getRecipientCell());
        System.out.println("Message: " + message.getMessageText());

        System.out.println("\nChoose an action:");
        System.out.println("1. Send message");
        System.out.println("2. Disregard message");
        System.out.println("3. Store message");
        System.out.print("Your choice: ");

        String action = input.nextLine();

        System.out.println(message.SentMessage(action));
    }
}