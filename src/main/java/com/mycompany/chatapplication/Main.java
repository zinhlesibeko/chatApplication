package com.mycompany.chatapplication;

/**
 * @author 27745
 */

/*
 * Main Class – Chat Application Console Program
 *
 * PART 3 ADDITIONS:
 * - Added messageHashes and messageIDs arrays
 * - Renamed discardedMessages -> disregardedMessages
 * - Added populateTestData() for test data
 * - Added loadStoredMessagesFromJSON() to read from JSON
 * - Added storedMessagesMenu() with sub-options a-f
 * - Added addMessageToArrays() helper
 * - Main menu now has option 3 = Stored Messages, 4 = Exit
 *
 * References:
 * Oracle Java Documentation – Scanner Class
 * https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html
 *
 * Oracle Java Documentation – ArrayList Class
 * https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
 *
 * W3Schools Java User Input Tutorial
 * https://www.w3schools.com/java/java_user_input.asp
 *
 * GeeksforGeeks – Java Switch Statement
 * https://www.geeksforgeeks.org/switch-statement-in-java/
 *
 * Alex Lee Java Tutorial (YouTube)
 * https://www.youtube.com/c/AlexLeeYT
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

    // ── ORIGINAL arrays (renamed discarded -> disregarded) ──
    static ArrayList<Message> sentMessages        = new ArrayList<>();
    static ArrayList<Message> disregardedMessages = new ArrayList<>(); // WAS: discardedMessages
    static ArrayList<Message> storedMessages      = new ArrayList<>();

    // ── PART 3: two new arrays ──
    static ArrayList<String> messageHashes = new ArrayList<>();
    static ArrayList<String> messageIDs    = new ArrayList<>();

    // ================================================================
    // MAIN METHOD
    // ================================================================
    public static void main(String[] args) {

        // ── PART 3: load test data before menu starts ──
        populateTestData();

        try (Scanner input = new Scanner(System.in)) {
            Login login = new Login();
            int option;

            do {
                // ── ORIGINAL menu, option 3 changed, option 4 added ──
                System.out.println("\n===== CHAT APPLICATION MENU =====");
                System.out.println("1. Register User");
                System.out.println("2. Login");
                System.out.println("3. Stored Messages");  // WAS: Exit (moved to 4)
                System.out.println("4. Exit");             // PART 3: new exit option
                System.out.print("Select an option: ");

                option = input.nextInt();
                input.nextLine();

                switch (option) {

                    // ── ORIGINAL case 1 (unchanged) ──
                    case 1 -> {
                        System.out.println("\n--- User Registration ---");
                        System.out.print("Enter First Name: ");
                        String firstName = input.nextLine();
                        System.out.print("Enter Last Name: ");
                        String lastName = input.nextLine();
                        System.out.print("Enter Username: ");
                        String username = input.nextLine();
                        System.out.print("Enter Password: ");
                        String password = input.nextLine();
                        System.out.print("Enter Cellphone (+27...): ");
                        String phone = input.nextLine();
                        System.out.println(login.registerUser(firstName, lastName, username, password, phone));
                    }

                    // ── ORIGINAL case 2 (unchanged) ──
                    case 2 -> {
                        System.out.println("\n--- User Login ---");
                        System.out.print("Enter Username: ");
                        String loginUser = input.nextLine();
                        System.out.print("Enter Password: ");
                        String loginPass = input.nextLine();
                        boolean status = login.loginUser(loginUser, loginPass);
                        System.out.println(login.returnLoginStatus(status));
                        if (status) {
                            startQuickChat(input);
                        }
                    }

                    // ── PART 3: new case 3 ──
                    case 3 -> storedMessagesMenu(input);

                    // ── PART 3: exit moved to 4 ──
                    case 4 -> System.out.println("Exiting application... Goodbye!");

                    default -> System.out.println("Invalid option. Please try again.");
                }

            } while (option != 4); // WAS: option != 3
        }
    }

    // ================================================================
    // PART 3 – Populate test data (5 messages from spec)
    // ================================================================
    public static void populateTestData() {
        // Message 1 – Sent
        Message m1 = new Message("+27834557896", "Did you get the cake?");
        m1.setSendMessageChoice("1");
        addMessageToArrays(m1, "1");

        // Message 2 – Stored
        Message m2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        m2.setSendMessageChoice("3");
        addMessageToArrays(m2, "3");
        saveMessageToJSON(m2);

        // Message 3 – Disregard
        Message m3 = new Message("+27834484567", "Yohoooo, I am at your gate.");
        m3.setSendMessageChoice("2");
        addMessageToArrays(m3, "2");

        // Message 4 – Sent (developer number)
        Message m4 = new Message("0838884567", "It is dinner time !");
        m4.setSendMessageChoice("1");
        addMessageToArrays(m4, "1");

        // Message 5 – Stored
        Message m5 = new Message("+27838884567", "Ok, I am leaving without you.");
        m5.setSendMessageChoice("3");
        addMessageToArrays(m5, "3");
        saveMessageToJSON(m5);

        System.out.println("Test data loaded successfully.");
    }

    // ================================================================
    // PART 3 – Helper: add message to the correct arrays
    // Replaces the manual ArrayList.add() calls you had in startQuickChat
    // ================================================================
    private static void addMessageToArrays(Message message, String action) {
        messageIDs.add(message.getMessageID());
        messageHashes.add(message.getMessageHash());

        switch (action) {
            case "1" -> sentMessages.add(message);
            case "2" -> disregardedMessages.add(message);
            case "3" -> storedMessages.add(message);
        }
    }

    // ================================================================
    // ORIGINAL – saveMessageToJSON (unchanged from your code)
    // ================================================================
    @SuppressWarnings("unchecked")
    private static void saveMessageToJSON(Message message) {
        JSONArray messageList = new JSONArray();
        JSONObject messageDetails = new JSONObject();
        messageDetails.put("Message ID",   message.getMessageID());
        messageDetails.put("Message Hash", message.getMessageHash());
        messageDetails.put("Recipient",    message.getRecipientNumber());
        messageDetails.put("Message",      message.getMessageContent());
        messageDetails.put("Status",       "Stored");
        messageList.add(messageDetails);

        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write(messageList.toJSONString());
            file.write(System.lineSeparator());
            System.out.println("Message saved to JSON file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving message to JSON file.");
        }
    }

    // ================================================================
    // PART 3 – Load stored messages FROM the JSON file
    // ================================================================
    private static void loadStoredMessagesFromJSON() {
        storedMessages.clear();
        JSONParser parser = new JSONParser();

        try {
            Scanner fileScanner = new Scanner(new java.io.File("messages.json"));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;
                try {
                    JSONArray arr = (JSONArray) parser.parse(line);
                    for (Object o : arr) {
                        JSONObject obj = (JSONObject) o;
                        String recipient = (String) obj.get("Recipient");
                        String content   = (String) obj.get("Message");
                        Message m = new Message(recipient, content);
                        m.setSendMessageChoice("3");
                        storedMessages.add(m);
                        if (!messageIDs.contains(m.getMessageID())) {
                            messageIDs.add(m.getMessageID());
                            messageHashes.add(m.getMessageHash());
                        }
                    }
                } catch (ParseException pe) {
                    System.out.println("Skipping malformed JSON line.");
                }
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("No JSON file found. Using in-memory stored messages.");
        }
    }

    // ================================================================
    // ORIGINAL – startQuickChat
    // Only changes: discardedMessages -> disregardedMessages
    //               manual ArrayList adds -> addMessageToArrays()
    // ================================================================
    private static void startQuickChat(Scanner scanner) {
        System.out.println("\n==================================================");
        System.out.println("Welcome to QuickChat!");
        System.out.println("==================================================");

        boolean messaging = true;

        while (messaging) {
            System.out.println("\n--- QuickChat Menu ---");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recent sent messages (Coming Soon)");
            System.out.println("3. Quit QuickChat (Return to main menu)");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.print("How many messages do you want to send? ");
                try {
                    int count = Integer.parseInt(scanner.nextLine());

                    for (int i = 0; i < count; i++) {
                        System.out.println("\n--- Message " + (i + 1) + " ---");

                        System.out.print("Enter Recipient Number (+27...): ");
                        String recipient = scanner.nextLine();

                        System.out.print("Enter Message (Max 250 chars): ");
                        String msgContent = scanner.nextLine();

                        // ORIGINAL validation (unchanged)
                        if (msgContent.length() > 250) {
                            System.out.println("ERROR: Message exceeds 250 characters by "
                                    + (msgContent.length() - 250) + " characters.");
                            i--;
                            continue;
                        }

                        Message message = new Message(recipient, msgContent);

                        // ORIGINAL display (unchanged)
                        System.out.println("\n--- Message Details ---");
                        System.out.println("Message ID: "   + message.getMessageID());
                        System.out.println("Message Hash: " + message.getMessageHash());
                        System.out.println("Recipient: "    + message.getRecipientNumber());
                        System.out.println("Message: "      + message.getMessageContent());

                        System.out.println("\nWhat would you like to do with this message?");
                        System.out.println("1. Send Message");
                        System.out.println("2. Disregard Message"); // WAS: Discard
                        System.out.println("3. Store Message");
                        System.out.print("Select action: ");
                        String action = scanner.nextLine();

                        System.out.println(">> " + message.sendMessage(action));

                        // CHANGED: was manual .add() calls, now uses helper
                        addMessageToArrays(message, action);

                        if (action.equals("3")) {
                            saveMessageToJSON(message);
                        }
                    }

                    // ORIGINAL summary (updated array name)
                    System.out.println("\n--- Summary ---");
                    System.out.println("Total messages sent: "        + sentMessages.size());
                    System.out.println("Total messages disregarded: " + disregardedMessages.size()); // renamed
                    System.out.println("Total messages stored: "      + storedMessages.size());

                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }

            } else if (choice.equals("2")) {
                // ORIGINAL coming soon message (unchanged)
                System.out.println("Feature is still in development and will display: 'Coming Soon'");

            } else if (choice.equals("3")) {
                System.out.println("Returning to main menu...");
                messaging = false;
            } else {
                System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }
    }

    // ================================================================
    // PART 3 – Stored Messages Menu (option 3 from main menu)
    // ================================================================
    private static void storedMessagesMenu(Scanner scanner) {
        loadStoredMessagesFromJSON(); // sync with JSON file first

        boolean running = true;
        while (running) {
            System.out.println("\n===== STORED MESSAGES MENU =====");
            System.out.println("a. Display sender and recipient of all stored messages");
            System.out.println("b. Display the longest stored message");
            System.out.println("c. Search for a message by ID");
            System.out.println("d. Search messages for a particular recipient");
            System.out.println("e. Delete a message using message hash");
            System.out.println("f. Display full report of all stored messages");
            System.out.println("q. Return to main menu");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "a" -> displayStoredSenderRecipient();
                case "b" -> displayLongestMessage();
                case "c" -> searchByMessageID(scanner);
                case "d" -> searchByRecipient(scanner);
                case "e" -> deleteByMessageHash(scanner);
                case "f" -> displayFullReport();
                case "q" -> { System.out.println("Returning to main menu..."); running = false; }
                default  -> System.out.println("Invalid option. Please enter a-f or q.");
            }
        }
    }

    // ── a. Display sender & recipient of all stored messages ──
    private static void displayStoredSenderRecipient() {
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        System.out.println("\n--- Stored Message Senders & Recipients ---");
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            System.out.println((i + 1) + ". Sender: You  |  Recipient: " + m.getRecipientNumber());
        }
    }

    // ── b. Display the longest stored message ──
    private static void displayLongestMessage() {
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        Message longest = storedMessages.get(0);
        for (Message m : storedMessages) {
            if (m.getMessageContent().length() > longest.getMessageContent().length()) {
                longest = m;
            }
        }
        System.out.println("\n--- Longest Stored Message ---");
        System.out.println("Recipient: " + longest.getRecipientNumber());
        System.out.println("Message:   " + longest.getMessageContent());
        System.out.println("Length:    " + longest.getMessageContent().length() + " characters");
    }

    // ── c. Search for a message by ID ──
    private static void searchByMessageID(Scanner scanner) {
        System.out.print("Enter Message ID to search: ");
        String searchID = scanner.nextLine().trim();

        ArrayList<Message> all = new ArrayList<>();
        all.addAll(sentMessages);
        all.addAll(storedMessages);

        boolean found = false;
        for (Message m : all) {
            if (m.getMessageID().equals(searchID)) {
                System.out.println("\n--- Message Found ---");
                System.out.println("Recipient: " + m.getRecipientNumber());
                System.out.println("Message:   " + m.getMessageContent());
                found = true;
                break;
            }
        }
        if (!found) System.out.println("No message found with ID: " + searchID);
    }

    // ── d. Search all messages for a particular recipient ──
    private static void searchByRecipient(Scanner scanner) {
        System.out.print("Enter recipient number to search: ");
        String searchNumber = scanner.nextLine().trim();

        ArrayList<Message> all = new ArrayList<>();
        all.addAll(sentMessages);
        all.addAll(storedMessages);

        boolean found = false;
        System.out.println("\n--- Messages for " + searchNumber + " ---");
        for (Message m : all) {
            if (m.getRecipientNumber().equals(searchNumber)) {
                System.out.println("- " + m.getMessageContent());
                found = true;
            }
        }
        if (!found) System.out.println("No messages found for: " + searchNumber);
    }

    // ── e. Delete a stored message using message hash ──
    private static void deleteByMessageHash(Scanner scanner) {
        System.out.print("Enter message hash to delete: ");
        String hash = scanner.nextLine().trim();

        Message toDelete = null;
        for (Message m : storedMessages) {
            if (m.getMessageHash().equalsIgnoreCase(hash)) {
                toDelete = m;
                break;
            }
        }

        if (toDelete != null) {
            storedMessages.remove(toDelete);
            messageHashes.remove(toDelete.getMessageHash());
            messageIDs.remove(toDelete.getMessageID());
            System.out.println("Message: \"" + toDelete.getMessageContent() + "\" successfully deleted.");
        } else {
            System.out.println("No stored message found with hash: " + hash);
        }
    }

    // ── f. Display full report of all stored messages ──
    private static void displayFullReport() {
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages to report.");
            return;
        }
        System.out.println("\n========== FULL STORED MESSAGES REPORT ==========");
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            System.out.println("\nMessage #" + (i + 1));
            System.out.println("Message ID:   " + m.getMessageID());
            System.out.println("Message Hash: " + m.getMessageHash());
            System.out.println("Recipient:    " + m.getRecipientNumber());
            System.out.println("Message:      " + m.getMessageContent());
            System.out.println("Status:       Stored");
        }
        System.out.println("\n=================================================");
        System.out.println("Total stored messages: " + storedMessages.size());
    }
}