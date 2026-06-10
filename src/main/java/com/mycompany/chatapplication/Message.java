/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapplication;

/**
 *
 * @author 27745
 */

/*
 * Message Class
 * ----------------------------------------
 * This class represents a message in the Chat Application.
 * 
 * The class manages:
 * - Unique Message ID (10-digit auto-generated)
 * - Message Hash (auto-generated from ID, content, and recipient)
 * - Recipient cell number validation
 * - Message content validation (max 250 characters)
 * - Tracking number of messages sent
 * - User choices: Send, Discard, or Store
 *
 * References:
 * Oracle Java Documentation – Random Class
 * https://docs.oracle.com/javase/8/docs/api/java/util/Random.html
 *
 * Oracle Java Documentation – StringBuilder Class
 * https://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html
 *
 * GeeksforGeeks – Java String split() Method
 * https://www.geeksforgeeks.org/split-string-java-examples/
 *
 * Stack Overflow – Generating random 10-digit numbers in Java
 * https://stackoverflow.com/questions/26747017/generating-10-digit-unique-random-numbers-in-java
 */

import java.util.Random;

public class Message {
 
    private String messageID;
    private String messageHash;
    private String recipientNumber;
    private String messageContent;
    private int numMessagesSent;
    private String sendMessageChoice;
 
    // Constructor: initializes message with auto-generated ID and hash
    public Message(String recipientNumber, String messageContent) {
        this.recipientNumber = recipientNumber;
        this.messageContent = messageContent;
        this.numMessagesSent = 0;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }
 
    // Checks if message ID is exactly 10 characters long
    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.length() == 10;
    }
 
    /*
     * FIX: Original code checked length() == 10 AND length() <= 13 simultaneously.
     * A valid SA number like +27834557896 is 12 characters, so == 10 was always false.
     * Corrected to: starts with "+27" AND length is 12 or 13 characters.
     */
    public String checkRecipientCell() {
        if (recipientNumber != null
                && recipientNumber.startsWith("+27")
                && recipientNumber.length() >= 12
                && recipientNumber.length() <= 13) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain "
                    + "an international code. Must start with +27 and be 12-13 characters "
                    + "(e.g., +27834557896).";
        }
    }
 
    // Auto-generates message hash: First 2 digits of ID + ":0:" + Last word of message (uppercase)
    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2) {
            return "00:0:ERROR";
        }
        String firstTwoDigits = messageID.substring(0, 2);
        String lastWord = getLastWord(messageContent);
        return firstTwoDigits + ":0:" + lastWord.toUpperCase();
    }
 
    // Helper: extracts the last word from a message string
    private String getLastWord(String msg) {
        if (msg == null || msg.trim().isEmpty()) {
            return "EMPTY";
        }
        String[] words = msg.trim().split("\\s+");
        return words[words.length - 1];
    }
 
    // Processes user action choice: 1=Send, 2=Discard, 3=Store
    public String sendMessage(String choice) {
        this.sendMessageChoice = choice;
        this.numMessagesSent++;
 
        return switch (choice) {
            case "send", "1" -> "Message successfully sent.";
            case "discard", "2" -> "Press 0 to delete the message.";
            case "store", "3" -> "Message successfully stored.";
            default -> "Invalid choice. Message discarded.";
        };
    }
 
    // Returns a formatted string with all message details
    public String printMessages() {
        return "-----------------------------\n"
                + "Message ID:   " + messageID + "\n"
                + "Message Hash: " + messageHash + "\n"
                + "Recipient:    " + recipientNumber + "\n"
                + "Message:      " + messageContent + "\n"
                + "Status:       " + (sendMessageChoice != null ? sendMessageChoice : "Not processed") + "\n"
                + "-----------------------------";
    }
 
    // Returns the total number of messages sent
    public int returnTotalMessagesSent() {
        return this.numMessagesSent;
    }
 
    // Generates a random 10-digit unique message ID
    private String generateMessageID() {
        Random rand = new Random();
        return String.format("%010d", (long)(rand.nextDouble() * 9_000_000_000L) + 1_000_000_000L);
    }
 
    // Getters
    public String getMessageID()         { return messageID; }
    public String getMessageHash()       { return messageHash; }
    public String getRecipientNumber()   { return recipientNumber; }
    public String getMessageContent()    { return messageContent; }
    public String getSendMessageChoice() { return sendMessageChoice; }
 
    // Setter
    public void setSendMessageChoice(String choice) { this.sendMessageChoice = choice; }
}
