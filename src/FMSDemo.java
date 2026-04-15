import models.*;
import services.*;

/**
 * Demonstration class for File Management System
 * This class shows various operations without user input
 */
public class FMSDemo {
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════");
        System.out.println("  FILE MANAGEMENT SYSTEM - DEMONSTRATION");
        System.out.println("════════════════════════════════════════════════\n");

        // Initialize services
        UserAuthenticationService authService = new UserAuthenticationService();
        FileSystemService fileSystemService = new FileSystemService();
        SearchService searchService = new SearchService(fileSystemService);

        // ============ USER REGISTRATION & LOGIN ============
        System.out.println("\n--- Step 1: User Registration & Authentication ---");
        User admin = authService.registerUser("U001", "admin", "admin123", User.UserRole.ADMIN);
        User user1 = authService.registerUser("U002", "john_doe", "pass123", User.UserRole.REGULAR_USER);
        User user2 = authService.registerUser("U003", "jane_smith", "pass456", User.UserRole.REGULAR_USER);

        User loggedInUser = authService.loginUser("U001", "admin123");
        System.out.println("Current User: " + loggedInUser.getUsername());

        // ============ FOLDER STRUCTURE ============
        System.out.println("\n--- Step 2: Creating Folder Structure ---");
        fileSystemService.setCurrentUser("U001");

        Folder rootFolder = fileSystemService.createRootFolder("Admin Files", "U001");
        fileSystemService.createSubfolder("root", "Documents", "U001");
        fileSystemService.createSubfolder("root", "Projects", "U001");
        fileSystemService.createSubfolder("root/Documents", "Work", "U001");

        // ============ FILE OPERATIONS ============
        System.out.println("\n--- Step 3: Creating Files ---");
        fileSystemService.createFile("root/Documents", "resume.txt", "This is my professional resume...", "U001");
        fileSystemService.createFile("root/Documents", "cover_letter.pdf", "Dear Hiring Manager...", "U001");
        fileSystemService.createFile("root/Documents/Work", "project_notes.txt", "Project information and notes...", "U001");
        fileSystemService.createFile("root/Projects", "code.java", "public class Main { ... }", "U001");

        // ============ READ FILE ============
        System.out.println("\n--- Step 4: Reading File Content ---");
        FileEntity fileRead = fileSystemService.readFile("root/Documents", "resume.txt", "U001");
        if (fileRead != null) {
            System.out.println("File: " + fileRead.getFileName());
            System.out.println("Size: " + fileRead.getFileSize() + " bytes");
            System.out.println("Type: " + fileRead.getFileType());
            System.out.println("Content: " + fileRead.readFile());
        }

        // ============ UPDATE FILE ============
        System.out.println("\n--- Step 5: Updating File Content ---");
        fileSystemService.updateFile("root/Documents", "resume.txt", "Updated resume with new skills...", "U001");
        FileEntity updatedFile = fileSystemService.readFile("root/Documents", "resume.txt", "U001");
        System.out.println("Updated content: " + updatedFile.readFile());

        // ============ VIEW FOLDER CONTENTS ============
        System.out.println("\n--- Step 6: Viewing Folder Contents ---");
        fileSystemService.showStructure("root/Documents", "U001");

        // ============ MOVE FILE ============
        System.out.println("\n--- Step 7: Moving File ---");
        fileSystemService.moveFile("root/Documents", "cover_letter.pdf", "root/Projects", "U001");
        fileSystemService.showStructure("root/Projects", "U001");

        // ============ SEARCH FUNCTIONALITY ============
        System.out.println("\n--- Step 8: Searching Files ---");

        // Search by name
        System.out.println("\nSearching for files containing 'notes':");
        var searchByName = searchService.searchFilesByName("notes", "U001");
        searchService.displaySearchResults(searchByName, "Name Search");

        // Search by type
        System.out.println("\nSearching for .txt files:");
        var searchByType = searchService.searchFilesByType("txt", "U001");
        searchService.displaySearchResults(searchByType, "Type Search");

        // Search by size
        System.out.println("\nSearching for files between 10-100 bytes:");
        var searchBySize = searchService.searchFilesBySize(10, 100, "U001");
        searchService.displaySearchResults(searchBySize, "Size Search");

        // ============ DELETE FILE (TO TRASH) ============
        System.out.println("\n--- Step 9: Deleting Files (to Trash) ---");
        fileSystemService.deleteFile("root/Projects", "cover_letter.pdf", "U001");
        fileSystemService.deleteFile("root/Documents/Work", "project_notes.txt", "U001");

        // ============ VIEW TRASH ============
        System.out.println("\n--- Step 10: Viewing Trash ---");
        fileSystemService.viewTrash("U001");

        // ============ RESTORE FROM TRASH ============
        System.out.println("\n--- Step 11: Restoring from Trash ---");
        fileSystemService.restoreFromTrash("project_notes.txt", "U001");
        System.out.println("After restore:");
        fileSystemService.viewTrash("U001");

        // ============ FILE PERMISSIONS ============
        System.out.println("\n--- Step 12: File Permissions Demo ---");
        FileEntity protectedFile = fileSystemService.readFile("root/Documents", "resume.txt", "U001");
        if (protectedFile != null) {
            System.out.println("File: " + protectedFile.getFileName());
            System.out.println("Owner: " + protectedFile.getOwnerUserId());
            System.out.println("Permission: " + protectedFile.getPermission());
            protectedFile.getPermission().setPublic(true);
            System.out.println("After making public: " + protectedFile.getPermission());
        }

        // ============ ADMIN OPERATIONS ============
        System.out.println("\n--- Step 13: Admin Operations ---");
        if (authService.isAdmin("U001")) {
            System.out.println("✓ Admin privileges confirmed");
            authService.displayAllUsers();
        }

        System.out.println("\n════════════════════════════════════════════════");
        System.out.println("  DEMONSTRATION COMPLETE");
        System.out.println("════════════════════════════════════════════════");
    }
}
