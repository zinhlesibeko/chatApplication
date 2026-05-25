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

    // Constructor initializes message with auto-generated ID and hash
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

    // Validates recipient cell number format (+27 and appropriate length)
    public String checkRecipientCell() {
        if (recipientNumber != null && recipientNumber.startsWith("+27") && 
            recipientNumber.length() == 10 && recipientNumber.length() <= 13) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Must be exactly 10 characters starting with +27 (e.g.,+2782123456).";
        }
    }

    // Auto-generates message hash: First 2 digits of ID + ":0:" + Last word of message
    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2) {
            return "00:0:ERROR";
        }
        String firstTwoDigits = messageID.substring(0, 2);
        String lastWord = getLastWord(messageContent);
        return firstTwoDigits + ":0:" + lastWord.toUpperCase();
    }

    // Helper method to extract the last word from a message
    private String getLastWord(String msg) {
        if (msg == null || msg.trim().isEmpty()) {
            return "EMPTY";
        }
        String[] words = msg.trim().split(" ");
        return words.length > 0 ? words[words.length - 1] : "MSG";
    }

    // Processes user choice: Send, Discard, or Store
    public String sendMessage(String choice) {
        this.sendMessageChoice = choice;
        this.numMessagesSent++;
        
        switch (choice.toLowerCase()) {
            case "send":
            case "1":
                return "Message successfully sent.";
            case "discard":
            case "2":
                return "Press 0 to delete the message.";
            case "store":
            case "3":
                return "Message successfully stored.";
            default:
                return "Invalid choice. Message discarded.";
        }
    }

    // Returns formatted string with all message details
    public String printMessages() {
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------\n");
        sb.append("Message ID: ").append(messageID).append("\n");
        sb.append("Message Hash: ").append(messageHash).append("\n");
        sb.append("Recipient: ").append(recipientNumber).append("\n");
        sb.append("Message: ").append(messageContent).append("\n");
        sb.append("Status: ").append(sendMessageChoice != null ? sendMessageChoice : "Not processed").append("\n");
        sb.append("-----------------------------");
        return sb.toString();
    }

    // Returns total number of messages sent
    public int returnTotalMessagesSent() {
        return this.numMessagesSent;
    }

    // Generates a random 10-digit unique message ID
    private String generateMessageID() {
        Random rand = new Random();
        return String.format("%010d", rand.nextInt(1000000000));
    }

    // Getters and Setters
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipientNumber() { return recipientNumber; }
    public String getMessageContent() { return messageContent; }
    public String getSendMessageChoice() { return sendMessageChoice; }
    public void setSendMessageChoice(String choice) { this.sendMessageChoice = choice; }
}