/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package chatApplication;

import com.mycompany.chatapplication.Login;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
* @author 27745
*/
public class LoginTest {

   private Login login;

   @BeforeEach
   public void setUp() {
      login = new Login();
   }

// ============ assertEquals TESTS ============

   @Test
   public void testUsernameCorrectlyFormatted() {
      String result = login.registerUser("Kyle", "Smith", "kyl_1", "Ch&sec@ke99!", "+27838968976");
      assertEquals("User successfully registered.", result);
   }

   @Test
   public void testUsernameIncorrectlyFormatted() {
      String result = login.registerUser("Kyle", "Smith", "kyle!!!!!!!", "Ch&sec@ke99!", "+27838968976");
      assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.", result);
   }

   @Test
   public void testPasswordMeetsComplexity() {
      String result = login.registerUser("Kyle", "Smith", "kyl_1", "Ch&sec@ke99!", "+27838968976");
      assertEquals("User successfully registered.", result);
   }

   @Test
   public void testPasswordDoesNotMeetComplexity() {
      String result = login.registerUser("Kyle", "Smith", "kyl_1", "password", "+27838968976");
      assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", result);
   }

   @Test
   public void testCellPhoneCorrectlyFormatted() {
      String result = login.registerUser("Kyle", "Smith", "kyl_1", "Ch&sec@ke99!", "+27838968976");
      assertEquals("User successfully registered.", result);
   }

   @Test
   public void testCellPhoneIncorrectlyFormatted() {
      String result = login.registerUser("Kyle", "Smith", "kyl_1", "Ch&sec@ke99!", "08966553");
      assertEquals("Cell phone number incorrectly formatted or does not contain international code.", result);
   }

// ============ assertTrue/False TESTS ============

   @Test
   public void testLoginSuccessful() {
      login.registerUser("Kyle", "Smith", "kyl_1", "Ch&sec@ke99!", "+27838968976");
      boolean result = login.loginUser("kyl_1", "Ch&sec@ke99!");
      assertTrue(result);
   }

   @Test
   public void testLoginFailed() {
      login.registerUser("Kyle", "Smith", "kyl_1", "Ch&sec@ke99!", "+27838968976");
      boolean result = login.loginUser("kyl_1", "wrongpassword");
      assertFalse(result);
   }

   @Test
   public void testUsernameCorrectlyFormattedBoolean() {
      boolean result = login.checkUserName("kyl_1");
      assertTrue(result);
   }

   @Test
   public void testUsernameIncorrectlyFormattedBoolean() {
      boolean result = login.checkUserName("kyle!!!!!!!");
      assertFalse(result);
   }

   @Test
   public void testPasswordMeetsComplexityBoolean() {
      boolean result = login.checkPasswordComplexity("Ch&sec@ke99!");
      assertTrue(result);
   }

   @Test
   public void testPasswordDoesNotMeetComplexityBoolean() {
      boolean result = login.checkPasswordComplexity("password");
      assertFalse(result);
   }

   @Test
   public void testCellPhoneCorrectlyFormattedBoolean() {
      boolean result = login.checkCellPhoneNumber("+27838968976");
      assertTrue(result);
   }

   @Test
   public void testCellPhoneIncorrectlyFormattedBoolean() {
      boolean result = login.checkCellPhoneNumber("08966553");
      assertFalse(result);
   }

   @Test
   public void testReturnLoginStatusSuccess() {
      login.registerUser("Kyle", "Smith", "kyl_1", "Ch&sec@ke99!", "+27838968976");
      boolean loginResult = login.loginUser("kyl_1", "Ch&sec@ke99!");
      String status = login.returnLoginStatus(loginResult);
      assertEquals("Welcome Kyle Smith, it is great to see you again.", status);
   }

   @Test
   public void testReturnLoginStatusFailure() {
      boolean loginResult = false;
      String status = login.returnLoginStatus(loginResult);
      assertEquals("Username or password incorrect, please try again.", status);
   }
}

