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
 * Main Class – Chat Application Console Program
 *
 * This class runs the chat application and allows the user to
 * interact with the system through a console menu.
 *
 * The program allows the user to:
 * 1. Register a new account
 * 2. Login with an existing account
 * 3. Exit the application
 * 4. After successful login, access the QuickChat messaging system
 *
 * The class collects user input and sends it to the Login class
 * for validation and authentication, then to the Message class
 * for message processing.
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
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Main {

    // ArrayLists for storing messages (Assignment Task 3)
    static ArrayList<String> sentMessages = new ArrayList<>();
    static ArrayList<String> discardedMessages = new ArrayList<>();
    static ArrayList<String> storedMessages = new ArrayList<>();

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            Login login = new Login();
            int option;
            
            do {
                // Display main menu options
                System.out.println("\n===== CHAT APPLICATION MENU =====");
                System.out.println("1. Register User");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Select an option: ");
                
                option = input.nextInt();
                input.nextLine(); // clears scanner buffer
                
                switch (option) {
                    
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
                        
                        String result = login.registerUser(firstName, lastName, username, password, phone);
                        
                        System.out.println(result);
                    }
                        
                    case 2 -> {
                        System.out.println("\n--- User Login ---");
                        
                        System.out.print("Enter Username: ");
                        String loginUser = input.nextLine();
                        
                        System.out.print("Enter Password: ");
                        String loginPass = input.nextLine();
                        
                        boolean status = login.loginUser(loginUser, loginPass);
                        
                        System.out.println(login.returnLoginStatus(status));
                        
                        // If login successful, enter QuickChat messaging system
                        if (status) {
                            startQuickChat(input);
                        }
                    }
                        
                    case 3 -> System.out.println("Exiting application... Goodbye!");
                        
                    default -> System.out.println("Invalid option. Please try again.");
                }
                
            } while (option != 3);
        }
    }
    
    private static void saveMessageToJSON(Message message) {

       JSONArray messageList = new JSONArray();

       JSONObject messageDetails = new JSONObject();

       messageDetails.put("Message ID", message.getMessageID());
       messageDetails.put("Message Hash", message.getMessageHash());
       messageDetails.put("Recipient", message.getRecipientNumber());
       messageDetails.put("Message", message.getMessageContent());
       messageDetails.put("Status", "Stored");

       messageList.add(messageDetails);

       try (FileWriter file = new FileWriter("messages.json", true)) {

         file.write(messageList.toJSONString());
         file.write(System.lineSeparator());

         System.out.println("Message saved to JSON file successfully.");

       } catch (IOException e) {
        System.out.println("Error saving message to JSON file.");
       }
    }

    /*
     * QuickChat Messaging System
     * ----------------------------------------
     * This method is called after successful login and provides
     * the messaging functionality required by the assignment.
     *
     * Features:
     * - Allows user to send multiple messages
     * - Validates message length (max 250 characters)
     * - Auto-generates Message ID and Hash
     * - Allows Send, Discard, or Store actions
     * - Tracks messages in ArrayLists
     * - Displays summary statistics
     *
     * References:
     * Oracle Java Documentation – Integer Class
     * https://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html
     *
     * GeeksforGeeks – Java Exception Handling
     * https://www.geeksforgeeks.org/exception-handling-in-java/
     */
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
                        
                        // Validation: Message cannot exceed 250 chars (Assignment Task 9)
                        if (msgContent.length() > 250) {
                            System.out.println("ERROR: Message exceeds 250 characters by " 
                                + (msgContent.length() - 250) + " characters.");
                            i--; // Retry this message
                            continue;
                        }
                        
                        // Create Message object
                        Message message = new Message(recipient, msgContent);
                        
                        // Display message details (Assignment Task 5)
                        System.out.println("\n--- Message Details ---");
                        System.out.println("Message ID: " + message.getMessageID());
                        System.out.println("Message Hash: " + message.getMessageHash());
                        System.out.println("Recipient: " + message.getRecipientNumber());
                        System.out.println("Message: " + message.getMessageContent());
                        
                        // User Action Choice (Assignment Task 3)
                        System.out.println("\nWhat would you like to do with this message?");
                        System.out.println("1. Send Message");
                        System.out.println("2. Discard Message");
                        System.out.println("3. Store Message");
                        System.out.print("Select action: ");
                        String action = scanner.nextLine();
                        
                        // Process the message based on user choice
                        String result = message.sendMessage(action);
                        System.out.println(">> " + result);
                        
                        // Store in appropriate ArrayList (Assignment Task 3)
                        if (action.equals("1")) {
                            sentMessages.add(message.printMessages());
                        } else if (action.equals("2")) {
                            discardedMessages.add(message.printMessages());
                        } else if (action.equals("3")) {
                            storedMessages.add(message.printMessages());
                            
                            // Save message to JSON file
                            saveMessageToJSON(message);
                        }
                    }
                    
                    // Display summary statistics (Assignment Task 6)
                    System.out.println("\n--- Summary ---");
                    System.out.println("Total messages sent: " + sentMessages.size());
                    System.out.println("Total messages discarded: " + discardedMessages.size());
                    System.out.println("Total messages stored: " + storedMessages.size());
                    
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
                
            } else if (choice.equals("2")) {
                // Feature still in development (Assignment Task 4)
                System.out.println("Feature is still in development and will display: 'Coming Soon'");
                
            } else if (choice.equals("3")) {
                System.out.println("Returning to main menu...");
                messaging = false;
            } else {
                System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }
    }
}