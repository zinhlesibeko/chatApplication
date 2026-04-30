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
 * Login Class
 * ----------------------------------------
 * This class manages the core functionality of the Chat Application.
 * It is responsible for validating user registration details and
 * verifying login credentials.
 *
 * The class checks:
 * - Username format (must contain an underscore and be ≤ 5 characters)
 * - Password complexity (minimum 8 characters, capital letter, number, special character)
 * - South African cell phone number format (must contain international code +27)
 *
 * It also stores the registered user's details and verifies login
 * attempts by comparing entered credentials with stored data.
 */

public class Login {

    private String username;
    private String password;
    private String cellPhone;
    private String firstName;
    private String lastName;

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {

        boolean length = password.length() >= 8;
        boolean capital = password.matches(".*[A-Z].*");
        boolean number = password.matches(".*[0-9].*");
        boolean special = password.matches(".*[!@#$%^&*()_+=-].*");

        return length && capital && number && special;
    }

    public boolean checkCellPhoneNumber(String phone) {
        return phone.startsWith("+27") && phone.length() <= 13;
    }

    public String registerUser(String firstName, String lastName, String username, String password, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;

        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        System.out.println("Username successfully captured.");

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
         System.out.println("Password successfully captured.");


        if (!checkCellPhoneNumber(phone)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
         System.out.println("Cell phone number was added successfully.");

        this.username = username;
        this.password = password;
        this.cellPhone = phone;

        return "User successfully registered.";
    }

    public boolean loginUser(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public String returnLoginStatus(boolean status) {

        if (status) {
            return "Welcome " + firstName + " " + lastName + ", " + "it is great to see you again.";
        } 
        else {
            return "Username or password incorrect, please try again.";
        }
    }
}
