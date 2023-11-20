import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
//import java.sql.*;
import java.util.Scanner;

public class MainMenu {

    private static Scanner scanner = new Scanner(System.in);
    //userID: 3, password: password3
    public static void main(String[] args) throws IOException, ParseException, SQLException {
        System.out.println("Welcome to the Spotify CLI.");
        User user = UserService.login();
        
        if (user != null) {
        	user.setAdmin(UserService.checkAdmin(user));
            System.out.println("Login successful. Welcome, " + user.getUserName() + "!");
        } else {
            System.out.println("Login failed. User Name not found.");
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
                    	ChartService.displayTopSongs(timeFrame);
                        waitForEnter();
                        break;
                    case 2:
                    	AdminDashboard.displayStats();
                    	waitForEnter();
                    	break;
                    case 3:
                    	SearchService.adminSearch();
                    	waitForEnter();
                    	break;
                    case 4:
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
                    	ChartService.displayTopSongs(timeFrame);
                        waitForEnter();
                        break;
                    case 2:
                    	SearchService.search();
                        waitForEnter();
                        break;
                    case 3:
                    	PlaylistService.createTimeBasedPlaylist(user);
                        waitForEnter();
                        break;
                    case 4:
                    	Boolean done = false;
                    	while (!done) {
	                    	System.out.print(user);
	                    	
	                    	printManageInformationMenu();
	                        System.out.print("Enter your choice: ");
	                        int choice2 = scanner.nextInt();
	                        scanner.nextLine();
	                        switch (choice2) {
	                        case 1:
	                        	UserManager.updateEmailInfo(user);
	                            
	                            break;
	                        case 2:
	                        	UserManager.updatePasswordInfo(user);
	                            break;
	                        case 3:
	                        	done = true;
	                            break;
	                        default:
	                            System.out.println("Invalid option. Please try again.");
	                            break;
	                        }
                        }
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
        PopularGenre.DisplayPopularGenre();
        System.out.println("*                                           *");
        System.out.println("*  1. Display Top Songs                     *");
        System.out.println("*  2. Search                                *");
        System.out.println("*  3. Create a Playlist                     *");
        System.out.println("*  4. Manage User Information               *");
        System.out.println("*  5. Exit                                  *");
        System.out.println("*                                           *");
        System.out.println("*********************************************");
    }
    
    private static void printManageInformationMenu() {
        System.out.println("**************** Spotify CLI ****************");
        System.out.println("*                                           *");
        System.out.println("*  1. Edit EmailInfo                        *");
        System.out.println("*  2. Edit PasswordInfo                     *");
        System.out.println("*  3. Exit                                  *");
        System.out.println("*                                           *");
        System.out.println("*********************************************");
    }
    
    private static void printAdminMainMenu() {
        System.out.println("**************** Spotify CLI ****************");
        System.out.println("*                                           *");
        System.out.println("*  1. Display Top Songs                     *");
        System.out.println("*  2. Admin Dashboard                       *");
        System.out.println("*  3. Search + Users                        *");
        System.out.println("*  4. Exit                                  *");
        System.out.println("*                                           *");
        System.out.println("*********************************************");
    }
}

