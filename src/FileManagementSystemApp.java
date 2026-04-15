import models.*;
import services.*;
import java.util.Scanner;
import java.util.List;

public class FileManagementSystemApp {
    private UserAuthenticationService authService;
    private FileSystemService fileSystemService;
    private SearchService searchService;
    private User currentUser;
    private Scanner scanner;

    public FileManagementSystemApp() {
        this.authService = new UserAuthenticationService();
        this.fileSystemService = new FileSystemService();
        this.searchService = new SearchService(fileSystemService);
        this.scanner = new Scanner(System.in);
        this.currentUser = null;
    }

    public static void main(String[] args) {
        FileManagementSystemApp app = new FileManagementSystemApp();
        app.run();
    }

    public void run() {
        System.out.println("════════════════════════════════════════════════");
        System.out.println("    FILE MANAGEMENT SYSTEM (FMS) - Welcome!    ");
        System.out.println("════════════════════════════════════════════════\n");

        boolean running = true;
        while (running) {
            if (currentUser == null) {
                showAuthMenu();
            } else {
                showMainMenu();
            }

            String choice = scanner.nextLine().trim();

            if (currentUser == null) {
                handleAuthChoice(choice);
            } else {
                running = handleMainChoice(choice);
            }
        }

        System.out.println("\n✓ Thank you for using FMS. Goodbye!");
        scanner.close();
    }

    private void showAuthMenu() {
        System.out.println("\n--- Authentication Menu ---");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choose option: ");
    }

    private void handleAuthChoice(String choice) {
        switch (choice) {
            case "1" -> registerUser();
            case "2" -> loginUser();
            case "3" -> System.exit(0);
            default -> System.out.println("✗ Invalid option");
        }
    }

    private void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Admin user? (y/n): ");
        String isAdmin = scanner.nextLine().trim().toLowerCase();

        User.UserRole role = isAdmin.equals("y") ? User.UserRole.ADMIN : User.UserRole.REGULAR_USER;
        String userId = "U_" + username.hashCode();

        User user = authService.registerUser(userId, username, password, role);
        if (user != null) {
            fileSystemService.createRootFolder(username + "'s Files", userId);
        }
    }

    private void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        String userId = "U_" + username.hashCode();
        User user = authService.loginUser(userId, password);
        if (user != null) {
            currentUser = user;
            fileSystemService.setCurrentUser(userId);
        }
    }

    private void showMainMenu() {
        System.out.println("\n════════════════════════════════════════════════");
        System.out.println("  Logged in as: " + currentUser.getUsername() + " (" + currentUser.getRole() + ")");
        System.out.println("════════════════════════════════════════════════");
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Create File");
        System.out.println("2. Read File");
        System.out.println("3. Update File");
        System.out.println("4. Delete File");
        System.out.println("5. Move File");
        System.out.println("6. Create Folder");
        System.out.println("7. Delete Folder");
        System.out.println("8. View Folder Contents");
        System.out.println("9. Search Files");
        System.out.println("10. View Trash");
        System.out.println("11. Restore from Trash");
        System.out.println("12. Empty Trash");

        if (currentUser.isAdmin()) {
            System.out.println("13. View All Users (ADMIN)");
        }

        System.out.println("14. Logout");
        System.out.print("Choose option: ");
    }

    private boolean handleMainChoice(String choice) {
        String userId = currentUser.getUserId();

        switch (choice) {
            case "1" -> createFile(userId);
            case "2" -> readFile(userId);
            case "3" -> updateFile(userId);
            case "4" -> deleteFile(userId);
            case "5" -> moveFile(userId);
            case "6" -> createFolder(userId);
            case "7" -> deleteFolder(userId);
            case "8" -> viewFolderContents(userId);
            case "9" -> searchFiles(userId);
            case "10" -> fileSystemService.viewTrash(userId);
            case "11" -> restoreFromTrash(userId);
            case "12" -> fileSystemService.emptyTrash(userId);
            case "13" -> {
                if (currentUser.isAdmin()) {
                    authService.displayAllUsers();
                } else {
                    System.out.println("✗ Admin only");
                }
            }
            case "14" -> {
                currentUser = null;
                return true;
            }
            default -> System.out.println("✗ Invalid option");
        }
        return true;
    }

    private void createFile(String userId) {
        System.out.print("Enter folder path (e.g., root, root/Documents): ");
        String folderPath = scanner.nextLine().trim();
        System.out.print("Enter file name: ");
        String fileName = scanner.nextLine().trim();
        System.out.print("Enter file content: ");
        String content = scanner.nextLine().trim();

        fileSystemService.createFile(folderPath, fileName, content, userId);
    }

    private void readFile(String userId) {
        System.out.print("Enter folder path: ");
        String folderPath = scanner.nextLine().trim();
        System.out.print("Enter file name: ");
        String fileName = scanner.nextLine().trim();

        FileEntity file = fileSystemService.readFile(folderPath, fileName, userId);
        if (file != null) {
            System.out.println("\n--- File Content ---");
            System.out.println(file.readFile());
            System.out.println("--- End of File ---");
        }
    }

    private void updateFile(String userId) {
        System.out.print("Enter folder path: ");
        String folderPath = scanner.nextLine().trim();
        System.out.print("Enter file name: ");
        String fileName = scanner.nextLine().trim();
        System.out.print("Enter new content: ");
        String newContent = scanner.nextLine().trim();

        fileSystemService.updateFile(folderPath, fileName, newContent, userId);
    }

    private void deleteFile(String userId) {
        System.out.print("Enter folder path: ");
        String folderPath = scanner.nextLine().trim();
        System.out.print("Enter file name: ");
        String fileName = scanner.nextLine().trim();

        fileSystemService.deleteFile(folderPath, fileName, userId);
    }

    private void moveFile(String userId) {
        System.out.print("Enter source folder path: ");
        String sourcePath = scanner.nextLine().trim();
        System.out.print("Enter file name: ");
        String fileName = scanner.nextLine().trim();
        System.out.print("Enter destination folder path: ");
        String destPath = scanner.nextLine().trim();

        fileSystemService.moveFile(sourcePath, fileName, destPath, userId);
    }

    private void createFolder(String userId) {
        System.out.print("Create root folder or subfolder? (root/sub): ");
        String type = scanner.nextLine().trim().toLowerCase();

        if (type.equals("root")) {
            System.out.print("Enter folder name: ");
            String folderName = scanner.nextLine().trim();
            fileSystemService.createRootFolder(folderName, userId);
        } else if (type.equals("sub")) {
            System.out.print("Enter parent folder path: ");
            String parentPath = scanner.nextLine().trim();
            System.out.print("Enter new folder name: ");
            String folderName = scanner.nextLine().trim();
            fileSystemService.createSubfolder(parentPath, folderName, userId);
        } else {
            System.out.println("✗ Invalid option");
        }
    }

    private void deleteFolder(String userId) {
        System.out.print("Enter folder path: ");
        String folderPath = scanner.nextLine().trim();

        fileSystemService.deleteFolder(folderPath, userId);
    }

    private void viewFolderContents(String userId) {
        System.out.print("Enter folder path (e.g., root, root/Documents): ");
        String folderPath = scanner.nextLine().trim();

        fileSystemService.showStructure(folderPath, userId);
    }

    private void searchFiles(String userId) {
        System.out.println("\n--- Search Options ---");
        System.out.println("1. Search by name");
        System.out.println("2. Search by type");
        System.out.println("3. Search by size range");
        System.out.print("Choose search type: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> {
                System.out.print("Enter file name (partial): ");
                String fileName = scanner.nextLine().trim();
                List<FileEntity> results = searchService.searchFilesByName(fileName, userId);
                searchService.displaySearchResults(results, "Name: " + fileName);
            }
            case "2" -> {
                System.out.print("Enter file type (e.g., pdf, txt, jpg): ");
                String fileType = scanner.nextLine().trim();
                List<FileEntity> results = searchService.searchFilesByType(fileType, userId);
                searchService.displaySearchResults(results, "Type: " + fileType);
            }
            case "3" -> {
                System.out.print("Enter min size (bytes): ");
                long minSize = Long.parseLong(scanner.nextLine().trim());
                System.out.print("Enter max size (bytes): ");
                long maxSize = Long.parseLong(scanner.nextLine().trim());
                List<FileEntity> results = searchService.searchFilesBySize(minSize, maxSize, userId);
                searchService.displaySearchResults(results, "Size: " + minSize + " - " + maxSize);
            }
            default -> System.out.println("✗ Invalid option");
        }
    }

    private void restoreFromTrash(String userId) {
        System.out.print("Enter item name to restore: ");
        String itemName = scanner.nextLine().trim();

        fileSystemService.restoreFromTrash(itemName, userId);
    }
}
