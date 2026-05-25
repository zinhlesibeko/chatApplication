/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.mycompany.chatapplication;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/*
 * MessageTest – JUnit 5
 * ----------------------------------------
 * Tests the Message class using the exact methods available:
 *   - checkMessageID()
 *   - checkRecipientCell()
 *   - createMessageHash()
 *   - sendMessage(String choice)
 *   - printMessages()
 *   - returnTotalMessagesSent()
 *
 * Test Data (from POE):
 *   Message 1: +27718693002  "Hi Mike, can you join us for dinner tonight?"  → Send
 *   Message 2: 08575975889   "Hi Keegan, did you receive the payment?"       → Discard
 */
class MessageTest {

    private Message message1;
    private Message message2;

    @BeforeEach
    void setUp() {
        // Test Data from POE
        message1 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        message2 = new Message("08575975889",  "Hi Keegan, did you receive the payment?");
    }

    // ================================================================
    // checkMessageID()
    // ================================================================

    @Test
    @DisplayName("Message ID is exactly 10 characters – success")
    void testCheckMessageIDSuccess() {
        assertTrue(message1.checkMessageID(),
            "Message ID should be exactly 10 characters. ID was: " + message1.getMessageID());
    }

    @Test
    @DisplayName("Message ID is created and printed")
    void testMessageIDGenerated() {
        assertNotNull(message1.getMessageID(), "Message ID should not be null.");
        assertEquals(10, message1.getMessageID().length(),
            "Message ID should be exactly 10 digits.");
        System.out.println("Message ID generated: " + message1.getMessageID());
    }

    // ================================================================
    // checkRecipientCell()
    // ================================================================

    @Test
    @DisplayName("Recipient cell number correctly formatted – success")
    void testCheckRecipientCellSuccess() {
        // +2782123456 is exactly 10 chars and starts with +27
        Message validMsg = new Message("+2782123456", "Test message.");
        assertEquals("Cell phone number successfully captured.",
            validMsg.checkRecipientCell());
    }

    @Test
    @DisplayName("Recipient cell number incorrectly formatted – failure")
    void testCheckRecipientCellFailure() {
        // 08575975889 does not start with +27
        String result = message2.checkRecipientCell();
        assertTrue(result.contains("incorrectly formatted") || result.contains("international code"),
            "Should return failure message for invalid number.");
    }

    // ================================================================
    // createMessageHash()
    // ================================================================

    @Test
    @DisplayName("Message hash is created and not null")
    void testCreateMessageHashNotNull() {
        String hash = message1.createMessageHash();
        assertNotNull(hash, "Message hash should not be null.");
        System.out.println("Message Hash (Message 1): " + hash);
    }

    @Test
    @DisplayName("Message hash contains last word of message in caps")
    void testCreateMessageHashContainsLastWord() {
        // "Hi Mike, can you join us for dinner tonight?" → last word = "tonight?"
        String hash = message1.createMessageHash();
        assertTrue(hash.toUpperCase().contains("TONIGHT"),
            "Hash should contain last word 'TONIGHT'. Hash was: " + hash);
    }

    @Test
    @DisplayName("Message hashes for both messages tested in a loop")
    void testMessageHashesInLoop() {
        Message[] messages = { message1, message2 };
        String[] expectedWords = { "TONIGHT", "PAYMENT" };

        for (int i = 0; i < messages.length; i++) {
            String hash = messages[i].createMessageHash();
            assertNotNull(hash, "Hash should not be null for message " + (i + 1));
            assertTrue(hash.toUpperCase().contains(expectedWords[i]),
                "Hash " + (i + 1) + " should contain '" + expectedWords[i]
                + "' but was: " + hash);
            System.out.println("Hash " + (i + 1) + ": " + hash);
        }
    }

    // ================================================================
    // sendMessage(String choice)
    // ================================================================

    @Test
    @DisplayName("sendMessage returns 'Message successfully sent.' when user selects Send")
    void testSendMessageSend() {
        assertEquals("Message successfully sent.", message1.sendMessage("1"));
    }

    @Test
    @DisplayName("sendMessage returns 'Press 0 to delete the message.' when user selects Discard")
    void testSendMessageDiscard() {
        assertEquals("Press 0 to delete the message.", message2.sendMessage("2"));
    }

    @Test
    @DisplayName("sendMessage returns 'Message successfully stored.' when user selects Store")
    void testSendMessageStore() {
        assertEquals("Message successfully stored.", message1.sendMessage("3"));
    }

    // ================================================================
    // returnTotalMessagesSent()
    // ================================================================

    @Test
    @DisplayName("returnTotalMessagesSent increments after each sendMessage call")
    void testReturnTotalMessagesSent() {
        message1.sendMessage("1");
        message1.sendMessage("1");
        assertEquals(2, message1.returnTotalMessagesSent(),
            "Total messages sent should be 2.");
    }

    @Test
    @DisplayName("returnTotalMessagesSent starts at 0 before any messages are sent")
    void testReturnTotalMessagesSentStartsAtZero() {
        Message freshMessage = new Message("+2782123456", "Hello world.");
        assertEquals(0, freshMessage.returnTotalMessagesSent(),
            "New message object should start with 0 messages sent.");
    }

    // ================================================================
    // printMessages()
    // ================================================================

    @Test
    @DisplayName("printMessages returns a non-empty string with message details")
    void testPrintMessages() {
        message1.sendMessage("1");
        String output = message1.printMessages();

        assertNotNull(output, "printMessages should not return null.");
        assertTrue(output.contains(message1.getMessageID()),   "Should contain message ID.");
        assertTrue(output.contains(message1.getMessageHash()), "Should contain message hash.");
        assertTrue(output.contains("+27718693002"),            "Should contain recipient.");
        assertTrue(output.contains("Hi Mike"),                 "Should contain message text.");
        System.out.println(output);
    }
}
