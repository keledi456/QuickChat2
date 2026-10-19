
package quickchat2;

import java.util.Scanner;

public class ChatApp {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Login login = new Login();

        System.out.println("=================================");
        System.out.println("       WELCOME TO QUICKCHAT");
        System.out.println("=================================");

        // STEP 1: Register a new user.
        System.out.println("\nREGISTER YOUR ACCOUNT");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter cellphone number (example +27718693002): ");
        String cellphone = scanner.nextLine();

        String registrationResult =
                login.registerUser(username, password, cellphone);

        System.out.println(registrationResult);

        // Stop if registration fails.
        if (!registrationResult.equals("User registered successfully.")) {
            System.out.println("Please restart the application and try again.");
            return;
        }

        // STEP 2: Log in.
        System.out.println("\nLOGIN");

        System.out.print("Enter username: ");
        String loginUsername = scanner.nextLine();

        System.out.print("Enter password: ");
        String loginPassword = scanner.nextLine();

        boolean loggedIn = login.loginUser(loginUsername, loginPassword);

        System.out.println(login.returnLoginStatus(loggedIn));

        if (!loggedIn) {
            System.out.println("Application closed.");
            return;
        }

        // STEP 3: Ask how many messages the user wants to enter.
        int numberOfMessages = readMessageCount();

        int messagesProcessed = 0;
        int nextMessageNumber = 0;

        boolean running = true;

        // STEP 4: Display the menu until the user quits.
        while (running) {

            System.out.println("\n========== QUICKCHAT MENU ==========");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine().trim();

            switch (option) {

                case "1":

                    if (messagesProcessed >= numberOfMessages) {
                        System.out.println(
                                "You have entered the requested number of messages.");
                        break;
                    }

                    // STEP 5: Create and process messages.
                    while (messagesProcessed < numberOfMessages) {

                        System.out.println("\n--- NEW MESSAGE ---");

                        String recipient;

                        while (true) {
                            System.out.print(
                                    "Enter recipient number (+27XXXXXXXXX): ");
                            recipient = scanner.nextLine().trim();

                            Message checkRecipient = new Message(
                                    "1234567890",
                                    nextMessageNumber,
                                    recipient,
                                    "Test message"
                            );

                            if (checkRecipient.checkRecipientCell()) {
                                break;
                            }

                            System.out.println(
                                    "Invalid recipient number. "
                                    + "Use the format +27 followed by 9 digits.");
                        }

                        String messageText;

                        while (true) {
                            System.out.print("Enter your message: ");
                            messageText = scanner.nextLine();

                            Message checkMessage = new Message(
                                    "1234567890",
                                    nextMessageNumber,
                                    recipient,
                                    messageText
                            );

                            if (checkMessage.checkMessageLength()) {
                                break;
                            }

                            System.out.println(
                                    "Your message must contain 1 to 250 characters. "
                                    + "Please try again.");
                        }

                        // Create the actual message.
                        Message message = new Message(
                                nextMessageNumber,
                                recipient,
                                messageText
                        );

                        System.out.println("\nChoose what to do:");
                        System.out.println("1. Send message");
                        System.out.println("2. Disregard message");
                        System.out.println("3. Store message");
                        System.out.print("Enter your choice: ");

                        String messageChoice = scanner.nextLine().trim();

                        String result = message.SentMessage(messageChoice);

                        System.out.println("\n" + result);

                        // Display details only when the message was sent.
                        if (messageChoice.equals("1")
                                && result.equals("Message successfully sent.")) {

                            System.out.println("\n--- MESSAGE DETAILS ---");
                            System.out.println(
                                    "Message ID: " + message.getMessageID());
                            System.out.println(
                                    "Message Hash: " + message.getMessageHash());
                            System.out.println(
                                    "Recipient: " + message.getRecipientCell());
                            System.out.println(
                                    "Message: " + message.getMessageText());
                        }

                        messagesProcessed++;
                        nextMessageNumber++;

                        if (messagesProcessed < numberOfMessages) {
                            System.out.println(
                                    "\nMessages processed: " + messagesProcessed
                                    + " of " + numberOfMessages);
                        }
                    }

                    System.out.println(
                            "\nYou have finished entering your requested messages.");
                    break;

                case "2":
                    System.out.println("Coming Soon.");
                    break;

                case "3":
                    running = false;
                    System.out.println("Thank you for using QuickChat.");
                    break;

                default:
                    System.out.println(
                            "Invalid menu option. Please choose 1, 2, or 3.");
            }
        }

        // STEP 6: Display the total number of messages sent.
        Message summary = new Message(0, "", "Summary");
        System.out.println("\n=================================");
        System.out.println(
                "Total messages sent: " + summary.returnTotalMessagess());
        System.out.println("QUICKCHAT CLOSED");
        System.out.println("=================================");
    }

    // Read and validate the number of messages.
    private static int readMessageCount() {

        while (true) {
            System.out.print(
                    "\nHow many messages would you like to enter? ");

            String input = scanner.nextLine().trim();

            try {
                int count = Integer.parseInt(input);

                if (count >= 0) {
                    return count;
                }

                System.out.println(
                        "Please enter zero or a positive whole number.");

            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input. Please enter a whole number.");
            }
        }
    }
}
