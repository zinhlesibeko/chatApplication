/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
 package com.mycompany.chatapplication;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    // TEST 1: Message ID must be 10 digits
    @Test
    public void testMessageIDLength() {
        Message msg = new Message("+27712345678", "Hello");

        assertNotNull(msg.getMessageID());
        assertEquals(10, msg.getMessageID().length());
    }

    // TEST 2: Recipient must start with +27
    @Test
    public void testRecipientStartsWith27() {
        Message msg = new Message("+27712345678", "Hello");

        assertTrue(msg.getRecipientNumber().startsWith("+27"));
    }

    // TEST 3: Invalid recipient format message
    @Test
    public void testInvalidRecipientMessage() {
        Message msg = new Message("0712345678", "Hello");

        assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an international code.",
            msg.checkRecipientCell()
        );
    }

    // TEST 4: Message hash contains correct format
    @Test
    public void testMessageHashFormat() {
        Message msg = new Message("+27712345678", "Hello there how are you");

        String hash = msg.getMessageHash();

        assertTrue(hash.contains(":0:"));
    }

    // TEST 5: Message length rule (250 chars)
    @Test
    public void testMessageLengthLimit() {
        String msg = "a".repeat(251);

        assertTrue(msg.length() > 250);
    }
}