package com.example.scchallengeemail;
/**
 * @author Kai Chevannes
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.regex.*;

public class App {
    /**
     * Outputs a users real name given their associated email ID.
     */
    public static void main(String[] args) {
        // Get user email ID
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID: ");
        String userEmailID = scanner.next(); //dan1, pll
        // Construct webpage address from the standard link + given ID
        String webpageAddress = "https://www.ecs.soton.ac.uk/people/" + userEmailID;

        try {
            URL webpageURL = new URL(webpageAddress);
            // Create a BufferedReader object that will be used to read the contents
            // of the webpage specified by the webpageURL variable.
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(webpageURL.openStream()));

            String inputLine;
            String userName = "";
            // Regular expression to look for chars between 'property="name">' and '<em'.
            // This is where the name of a user is stored in the webpages source code and
            // explains why we search the inputLine for 'property="name"' as this signifies
            // that the users name is in the current inputLine.
            String nameRegex = "(?<=property=\"name\">)(.*)(?=<em)";
            while ((inputLine = reader.readLine()) != null) {
                if (inputLine.contains("property=\"name\"")) {
                    Pattern usernamePattern = Pattern.compile(nameRegex);
                    Matcher usernamePatternMatcher = usernamePattern.matcher(inputLine);

                    if (usernamePatternMatcher.find()) {
                        // group(0) gets the whole matched expression
                        userName = usernamePatternMatcher.group(0);
                    } else {
                        userName = "Failed to find username.";
                    }
                }
            }
            System.out.printf("Associated name for ID %s is %s%n", userEmailID, userName);
            reader.close();
        } catch (MalformedURLException error) {
            // Catch the error where an input is not a valid webpage
            System.out.printf("%s is an invalid webpage", webpageAddress);
        } catch (IOException e) {
            // Catch an I/O exception for the openStream() function
            e.printStackTrace();
        }
    }
}
