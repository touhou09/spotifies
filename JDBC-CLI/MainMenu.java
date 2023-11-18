import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
//import java.sql.*;
import java.util.Scanner;

public class MainMenu {

    private static Scanner scanner = new Scanner(System.in);
    //userID: 3, password: password3
    public static void main(String[] args) throws IOException, ParseException, SQLException {
        ChartService chartService = new ChartService();
        SearchService searchService = new SearchService();
        PlaylistService playlistService = new PlaylistService();
        UserManager userManager = new UserManager();
        UserService userService = new UserService();

        System.out.println("Welcome to the Spotify CLI.");
        User user = userService.login();
        
        if (user != null) {
        	user.setAdmin(userService.checkAdmin(user));
            System.out.println("Login successful. Welcome, " + user.getUserName() + "!");
        } else {
            System.out.println("Login failed. User ID not found.");
        }

        boolean exit = false;
        
        if (user.getAdmin()) {
            System.out.println("Entering admin menu..");
            
            while (!exit) {
            	printAdminMainMenu();
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                    	System.out.print("Enter timeframe to search: ");
                    	String timeFrame = scanner.nextLine();
                        chartService.displayTopSongs(timeFrame);
                        waitForEnter();
                        break;
                    case 2:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        waitForEnter();
                        break;
                }
            }
        } else {
            while (!exit) {
                printMainMenu();
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                    	System.out.print("Enter timeframe to search: ");
                    	String timeFrame = scanner.nextLine();
                        chartService.displayTopSongs(timeFrame);
                        waitForEnter();
                        break;
                    case 2:
                        searchService.search();
                        waitForEnter();
                        break;
                    case 3:
                    	System.out.print("Enter your target duration(s): ");
                        int duration = scanner.nextInt();
                        scanner.nextLine();
                        playlistService.createTimeBasedPlaylist(user, duration * 1000);
                        waitForEnter();
                        break;
                    case 4:
                        userManager.updateUserInfo(user);
                        waitForEnter();
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        waitForEnter();
                        break;
                }
            }
        }
            System.out.println("Thank you for using our service!");
            scanner.close();
            
           
    }

    private static void waitForEnter() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static void printMainMenu() {
        System.out.println("**************** Spotify CLI ****************");
        System.out.println("*                                           *");
        System.out.println("*  1. Display Top Songs                     *");
        System.out.println("*  2. Search                                *");
        System.out.println("*  3. Create a Playlist                     *");
        System.out.println("*  4. Manage User Information               *");
        System.out.println("*  5. Exit                                  *");
        System.out.println("*                                           *");
        System.out.println("*********************************************");
    }
    
    private static void printAdminMainMenu() {
        System.out.println("**************** Spotify CLI ****************");
        System.out.println("*                                           *");
        System.out.println("*  1. Data inquiry                          *");
       // System.out.println("*  2. Search                                *");
       // System.out.println("*  3. Create a Playlist                     *");
       // System.out.println("*  4. Manage User Information               *");
        System.out.println("*  2. Exit                                  *");
        System.out.println("*                                           *");
        System.out.println("*********************************************");
    }
}

