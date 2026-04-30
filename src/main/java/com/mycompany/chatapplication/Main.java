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
 *
 * The class collects user input and sends it to the Login class
 * for validation and authentication.
 *
 * References:
 * Oracle Java Documentation – Scanner Class
 * https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html
 *
 * W3Schools Java User Input Tutorial
 * https://www.w3schools.com/java/java_user_input.asp
 *
 * Alex Lee Java Tutorial (YouTube)
 * https://www.youtube.com/c/AlexLeeYT
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Scanner input = new Scanner(System.in)) {
            Login login = new Login();
            
            int option;
            
            do {
                
                // Display menu options
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
                    }
                        
                    case 3 -> System.out.println("Exiting application... Goodbye!");
                        
                    default -> System.out.println("Invalid option. Please try again.");
                }
                
            } while (option != 3);
        }
    }
}

