
package quickchat2;

import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    @Test
    public void testMessageIDHasTenDigits() {
        Message message = new Message(
                0, "+27718693002", "Hello there");

        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
    }

    @Test
    public void testInvalidMessageID() {
        Message message = new Message(
                "123", 0, "+27718693002", "Hello there");

        assertFalse(message.checkMessageID());
    }

    @Test
    public void testValidRecipientCell() {
        Message message = new Message(
                0, "+27718693002", "Hello there");

        assertTrue(message.checkRecipientCell());
    }

    @Test
    public void testInvalidRecipientCell() {
        Message message = new Message(
                0, "08575975889", "Hello there");

        assertFalse(message.checkRecipientCell());
    }

    @Test
    public void testMessageUnder250Characters() {
        Message message = new Message(
                0, "+27718693002", "Hello there");

        assertTrue(message.checkMessageLength());
    }

    @Test
    public void testMessageExactly250Characters() {
        String text = repeatCharacter('A', 250);

        Message message = new Message(
                0, "+27718693002", text);

        assertTrue(message.checkMessageLength());
    }

    @Test
    public void testMessageOver250Characters() {
        String text = repeatCharacter('A', 251);

        Message message = new Message(
                0, "+27718693002", text);

        assertFalse(message.checkMessageLength());
    }

    @Test
    public void testCreateMessageHash() {
        Message message = new Message(
                "0012345678",
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");

        assertEquals(
                "00:0:HITONIGHT",
                message.createMessageHash());
    }

    @Test
    public void testSendMessage() {
        Message message = new Message(
                0, "+27718693002", "Hello there");

        assertEquals(
                "Message successfully sent.",
                message.SentMessage("1"));
    }

    @Test
    public void testDisregardMessage() {
        Message message = new Message(
                0, "+27718693002", "Hello there");

        assertEquals(
                "Press 0 to delete the message.",
                message.SentMessage("2"));
    }

    @Test
    public void testStoreMessage() {
        Message message = new Message(
                0, "+27718693002", "Hello there");

        assertEquals(
                "Message successfully stored.",
                message.SentMessage("3"));
    }

    @Test
    public void testInvalidChoice() {
        Message message = new Message(
                0, "+27718693002", "Hello there");

        assertEquals(
                "Invalid choice. Please choose 1, 2, or 3.",
                message.SentMessage("4"));
    }

    @Test
    public void testTotalMessagesSentIncreases() {
        Message message = new Message(
                0, "+27718693002", "Hello there");

        int before = message.returnTotalMessagess();

        String result = message.SentMessage("1");

        assertEquals("Message successfully sent.", result);
        assertEquals(before + 1, message.returnTotalMessagess());
    }

    // Helper method to create text of a specified length.
    private String repeatCharacter(char character, int length) {
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < length; i++) {
            text.append(character);
        }

        return text.toString();
    }
}