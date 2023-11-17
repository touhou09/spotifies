import java.sql.*;
import java.util.Scanner;

public class Spotify {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        AuthenticationService authService = new AuthenticationService();
        ChartService chartService = new ChartService();
        SearchService searchService = new SearchService();
        PlaylistService playlistService = new PlaylistService();
        UserManager userManager = new UserManager();

        System.out.println("Welcome to the Spotify CLI.");
        User user = authService.login();

        if (user == null) {
            System.out.println("Login failed. Please check your credentials.");
            return;
        }
        System.out.println("Login successful.");

        boolean exit = false;
        while (!exit) {
            clearScreen();
            printMainMenu();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1:
                    chartService.displayTopSongs();
                    waitForEnter();
                    break;
                case 2:
                    searchService.search();
                    waitForEnter();
                    break;
                case 3:
                    playlistService.createTimeBasedPlaylist();
                    waitForEnter();
                    break;
                case 4:
                    userManager.updateUserInfo();
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
        System.out.println("Thank you for using our service!");
        scanner.close();
    }

    // Enter를 기다리는 메서드
    private static void waitForEnter() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    // 메인 메뉴 출력 메서드
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

    // 화면 지우기 메서드
    private static void clearScreen() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {
            // 예외 처리, 혹은 대체 로직
            for (int i = 0; i < 100; i++) System.out.println(); // 대체 로직
        }
    }
}