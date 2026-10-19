
package quickchat2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipientCell;
    private String messageText;

    private static int totalMessagesSent = 0;

    private static final List<Message> sentMessages =
            new ArrayList<>();

    // Constructor
    public Message(int messageNumber,
                   String recipientCell,
                   String messageText) {

        this.messageID = generateMessageID();
        this.messageNumber = messageNumber;
        this.recipientCell = recipientCell;
        this.messageText = messageText;
    }

    // Constructor used for testing a specific message ID
    public Message(String messageID,
                   int messageNumber,
                   String recipientCell,
                   String messageText) {

        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipientCell = recipientCell;
        this.messageText = messageText;
    }

    // Generate a random 10-digit message ID
    private String generateMessageID() {

        Random random = new Random();

        long number = 1000000000L
                + (long) (random.nextDouble()
                * 9000000000L);

        return String.valueOf(number);
    }

    // Check that the message ID has 10 digits
    public boolean checkMessageID() {

        return messageID != null
                && messageID.matches("\\d{10}");
    }

    // Check the South African cellphone number
    public boolean checkRecipientCell() {

        return recipientCell != null
                && recipientCell.matches("\\+27\\d{9}");
    }

    // Check that the message is not empty
    // and does not exceed 250 characters
    public boolean checkMessageLength() {

        return messageText != null
                && !messageText.trim().isEmpty()
                && messageText.length() <= 250;
    }

    // Create the message hash
    public String createMessageHash() {

        String firstTwoDigits =
                messageID.substring(0, 2);

        String[] words =
                messageText.trim().split("\\s+");

        String firstWord =
                words[0].replaceAll("[^a-zA-Z]", "");

        String lastWord =
                words[words.length - 1]
                        .replaceAll("[^a-zA-Z]", "");

        return firstTwoDigits + ":"
                + messageNumber + ":"
                + firstWord.toUpperCase()
                + lastWord.toUpperCase();
    }

    // Send, disregard, or store a message
    public String SentMessage(String choice) {

        switch (choice) {

            case "1":
                sentMessages.add(this);
                totalMessagesSent++;

                return "Message successfully sent.";

            case "2":
                return "Press 0 to delete the message.";

            case "3":

                if (storeMessage()) {
                    return "Message successfully stored.";
                }

                return "Message could not be stored.";

            default:
                return "Invalid choice. Please choose 1, 2, or 3.";
        }
    }

    // Store the message in a JSON file
    private boolean storeMessage() {

        try (FileWriter writer =
                new FileWriter("messages.json", true)) {

            writer.write("{");

            writer.write("\"messageID\":\""
                    + escapeJSON(messageID) + "\",");

            writer.write("\"messageNumber\":"
                    + messageNumber + ",");

            writer.write("\"recipientCell\":\""
                    + escapeJSON(recipientCell) + "\",");

            writer.write("\"messageText\":\""
                    + escapeJSON(messageText) + "\",");

            writer.write("\"messageHash\":\""
                    + escapeJSON(createMessageHash()) + "\"");

            writer.write("}");
            writer.write(System.lineSeparator());

            return true;

        } catch (IOException exception) {

            System.out.println(
                    "Error storing message: "
                    + exception.getMessage());

            return false;
        }
    }

    // Escape special characters for JSON
    private String escapeJSON(String text) {

        if (text == null) {
            return "";
        }

        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // Display all sent messages
    public String printMessages() {

        StringBuilder result = new StringBuilder();

        for (Message message : sentMessages) {

            result.append("Message ID: ")
                    .append(message.messageID)
                    .append("\nRecipient: ")
                    .append(message.recipientCell)
                    .append("\nMessage: ")
                    .append(message.messageText)
                    .append("\n\n");
        }

        return result.toString();
    }

    // Return the total number of messages sent
    public static int returnTotalMessagess() {

        return totalMessagesSent;
    }

    // Getters
    public String getMessageID() {
        return messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getRecipientCell() {
        return recipientCell;
    }

    public String getMessageText() {
        return messageText;
    }
}