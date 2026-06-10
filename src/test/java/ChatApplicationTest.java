package com.mycompany.chatapplication;

/*
 * ChatApplicationTest – JUnit 5 Unit Tests
 * ----------------------------------------
 * Tests all Part 3 requirements using the 5 test data messages:
 *
 * Message 1: +27834557896  | "Did you get the cake?"                          | Sent
 * Message 2: +27838884567  | "Where are you? You are late! ..."               | Stored
 * Message 3: +27834484567  | "Yohoooo, I am at your gate."                    | Disregard
 * Message 4: 0838884567    | "It is dinner time !"                            | Sent
 * Message 5: +27838884567  | "Ok, I am leaving without you."                  | Stored
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ChatApplicationTest {

    // Fresh arrays for each test
    private ArrayList<Message> sentMessages;
    private ArrayList<Message> disregardedMessages;
    private ArrayList<Message> storedMessages;
    private ArrayList<String>  messageHashes;
    private ArrayList<String>  messageIDs;

    // The 5 test data messages
    private Message m1, m2, m3, m4, m5;

    @BeforeEach
    public void setUp() {
        sentMessages        = new ArrayList<>();
        disregardedMessages = new ArrayList<>();
        storedMessages      = new ArrayList<>();
        messageHashes       = new ArrayList<>();
        messageIDs          = new ArrayList<>();

        // Message 1 – Sent
        m1 = new Message("+27834557896", "Did you get the cake?");
        m1.setSendMessageChoice("1");
        sentMessages.add(m1);
        messageIDs.add(m1.getMessageID());
        messageHashes.add(m1.getMessageHash());

        // Message 2 – Stored
        m2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        m2.setSendMessageChoice("3");
        storedMessages.add(m2);
        messageIDs.add(m2.getMessageID());
        messageHashes.add(m2.getMessageHash());

        // Message 3 – Disregard
        m3 = new Message("+27834484567", "Yohoooo, I am at your gate.");
        m3.setSendMessageChoice("2");
        disregardedMessages.add(m3);
        messageIDs.add(m3.getMessageID());
        messageHashes.add(m3.getMessageHash());

        // Message 4 – Sent (developer number, no +27)
        m4 = new Message("0838884567", "It is dinner time !");
        m4.setSendMessageChoice("1");
        sentMessages.add(m4);
        messageIDs.add(m4.getMessageID());
        messageHashes.add(m4.getMessageHash());

        // Message 5 – Stored
        m5 = new Message("+27838884567", "Ok, I am leaving without you.");
        m5.setSendMessageChoice("3");
        storedMessages.add(m5);
        messageIDs.add(m5.getMessageID());
        messageHashes.add(m5.getMessageHash());
    }

    // -------------------------------------------------------
    // TEST 1: Sent Messages array correctly populated
    // Expected: messages 1 and 4 are in sentMessages
    // System returns: "Did you get the cake?", "It is dinner time !"
    // -------------------------------------------------------
    @Test
    public void testSentMessagesArrayPopulatedCorrectly() {
        assertEquals(2, sentMessages.size(), "Sent messages array should contain 2 messages.");
        assertEquals("Did you get the cake?",  sentMessages.get(0).getMessageContent());
        assertEquals("It is dinner time !",    sentMessages.get(1).getMessageContent());
    }

    // -------------------------------------------------------
    // TEST 2: Display the longest message (messages 1–4)
    // Expected: Message 2 – "Where are you? You are late! ..."
    // -------------------------------------------------------
    @Test
    public void testDisplayLongestMessage() {
        // Combine sent + stored (as per spec: messages 1–4)
        ArrayList<Message> combined = new ArrayList<>();
        combined.addAll(sentMessages);
        combined.addAll(storedMessages);

        // Only use first 3 from combined (messages 1, 4 sent; 2 stored = messages 1,2,4 in combined)
        // Spec says messages 1-4 so we use m1, m2, m3, m4
        ArrayList<Message> firstFour = new ArrayList<>();
        firstFour.add(m1);
        firstFour.add(m2);
        firstFour.add(m3);
        firstFour.add(m4);

        Message longest = firstFour.get(0);
        for (Message m : firstFour) {
            if (m.getMessageContent().length() > longest.getMessageContent().length()) {
                longest = m;
            }
        }

        assertEquals(
            "Where are you? You are late! I have asked you to be on time.",
            longest.getMessageContent(),
            "Longest message should be message 2."
        );
    }

    // -------------------------------------------------------
    // TEST 3: Search for message by ID (Message 4)
    // Expected: recipient = "0838884567", message = "It is dinner time !"
    // -------------------------------------------------------
    @Test
    public void testSearchByMessageID() {
        String searchID = m4.getMessageID();

        ArrayList<Message> all = new ArrayList<>();
        all.addAll(sentMessages);
        all.addAll(storedMessages);

        Message found = null;
        for (Message m : all) {
            if (m.getMessageID().equals(searchID)) {
                found = m;
                break;
            }
        }

        assertNotNull(found, "Message should be found by ID.");
        assertEquals("0838884567",       found.getRecipientNumber());
        assertEquals("It is dinner time !", found.getMessageContent());
    }

    // -------------------------------------------------------
    // TEST 4: Search messages by recipient +27838884567
    // Expected: 2 messages returned – Message 2 and Message 5
    // -------------------------------------------------------
    @Test
    public void testSearchByRecipient() {
        String recipient = "+27838884567";

        ArrayList<Message> all = new ArrayList<>();
        all.addAll(sentMessages);
        all.addAll(storedMessages);

        ArrayList<String> results = new ArrayList<>();
        for (Message m : all) {
            if (m.getRecipientNumber().equals(recipient)) {
                results.add(m.getMessageContent());
            }
        }

        assertEquals(2, results.size(), "Should find 2 messages for +27838884567.");
        assertTrue(results.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(results.contains("Ok, I am leaving without you."));
    }

    // -------------------------------------------------------
    // TEST 5: Delete message using message hash (Message 2)
    // Expected: "Where are you?..." successfully deleted
    // -------------------------------------------------------
    @Test
    public void testDeleteMessageByHash() {
        String hashToDelete = m2.getMessageHash();
        String expectedContent = "Where are you? You are late! I have asked you to be on time.";

        Message toDelete = null;
        for (Message m : storedMessages) {
            if (m.getMessageHash().equalsIgnoreCase(hashToDelete)) {
                toDelete = m;
                break;
            }
        }

        assertNotNull(toDelete, "Message to delete should be found.");
        assertEquals(expectedContent, toDelete.getMessageContent());

        storedMessages.remove(toDelete);
        messageHashes.remove(toDelete.getMessageHash());
        messageIDs.remove(toDelete.getMessageID());

        assertEquals(1, storedMessages.size(), "Stored messages should have 1 remaining after delete.");

        // Verify deleted message no longer exists
        boolean stillExists = storedMessages.stream()
            .anyMatch(m -> m.getMessageHash().equalsIgnoreCase(hashToDelete));
        assertFalse(stillExists, "Deleted message should no longer be in storedMessages.");
    }

    // -------------------------------------------------------
    // TEST 6: Display report – verify all stored messages present
    // -------------------------------------------------------
    @Test
    public void testDisplayReportContainsAllStoredMessages() {
        assertEquals(2, storedMessages.size(), "Should have 2 stored messages.");

        StringBuilder report = new StringBuilder();
        for (Message m : storedMessages) {
            report.append(m.printMessages());
        }

        assertTrue(report.toString().contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(report.toString().contains("Ok, I am leaving without you."));
    }

    // -------------------------------------------------------
    // BONUS: Login tests (Part 1 validation)
    // -------------------------------------------------------
    @Test
    public void testValidUsername() {
        Login login = new Login();
        assertTrue(login.checkUserName("kyl_1"), "kyl_1 should be valid.");
    }

    @Test
    public void testInvalidUsernameNoUnderscore() {
        Login login = new Login();
        assertFalse(login.checkUserName("kyle1"), "No underscore – should be invalid.");
    }

    @Test
    public void testInvalidUsernameTooLong() {
        Login login = new Login();
        assertFalse(login.checkUserName("kyle_1"), "More than 5 chars – should be invalid.");
    }

    @Test
    public void testValidPassword() {
        Login login = new Login();
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"), "Valid password.");
    }

    @Test
    public void testInvalidPasswordTooShort() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("abc"), "Too short – should be invalid.");
    }

    @Test
    public void testValidCellNumber() {
        Login login = new Login();
        assertTrue(login.checkCellPhoneNumber("+27838884567"), "Valid SA number.");
    }

    @Test
    public void testInvalidCellNumberNoCode() {
        Login login = new Login();
        assertFalse(login.checkCellPhoneNumber("0838884567"), "Missing +27 – should be invalid.");
    }

    @Test
    public void testMessageIDLength() {
        Message m = new Message("+27834557896", "Test message");
        assertTrue(m.checkMessageID(), "Message ID should be 10 digits.");
    }

    @Test
    public void testMessageHashFormat() {
        Message m = new Message("+27834557896", "Did you get the cake?");
        String hash = m.getMessageHash();
        // Hash format: XX:0:LASTWORD
        assertTrue(hash.contains(":0:"), "Hash should contain ':0:' separator.");
        assertTrue(hash.endsWith("CAKE?"), "Hash should end with last word in uppercase.");
    }
}