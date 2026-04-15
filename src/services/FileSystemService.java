package services;

import models.*;
import java.util.*;

public class FileSystemService {
    private Map<String, Folder> rootFolders; // userId -> root folder
    private List<TrashItem> trash;
    private String currentUserId;

    public FileSystemService() {
        this.rootFolders = new HashMap<>();
        this.trash = new ArrayList<>();
        this.currentUserId = null;
    }

    public void setCurrentUser(String userId) {
        this.currentUserId = userId;
    }

    // FOLDER OPERATIONS
    public Folder createRootFolder(String folderName, String userId) {
        String folderId = UUID.randomUUID().toString();
        Folder folder = new Folder(folderId, folderName, userId);
        rootFolders.put(userId, folder);
        System.out.println("✓ Root folder created: " + folderName);
        return folder;
    }

    public Folder createSubfolder(String parentFolderPath, String newFolderName, String userId) {
        Folder parentFolder = navigateToFolder(parentFolderPath, userId);
        if (parentFolder == null) {
            System.out.println("✗ Parent folder not found");
            return null;
        }

        String folderId = UUID.randomUUID().toString();
        Folder newFolder = new Folder(folderId, newFolderName, parentFolder.getFolderId(), userId);
        parentFolder.addFolder(newFolder);
        System.out.println("✓ Subfolder created: " + newFolderName);
        return newFolder;
    }

    public void deleteFolder(String folderPath, String userId) {
        Folder folder = navigateToFolder(folderPath, userId);
        if (folder == null) {
            System.out.println("✗ Folder not found");
            return;
        }

        if (!folder.getOwnerUserId().equals(userId)) {
            System.out.println("✗ Permission denied");
            return;
        }

        // Move to trash instead of permanent delete
        TrashItem item = new TrashItem(folder.getFolderName(), "FOLDER", folder, userId);
        trash.add(item);
        System.out.println("✓ Folder moved to trash: " + folderPath);
    }

    public Folder navigateToFolder(String path, String userId) {
        if (path == null || path.equals("root") || path.equals("/")) {
            return rootFolders.get(userId);
        }

        String[] pathParts = path.split("/");
        Folder current = rootFolders.get(userId);

        for (String part : pathParts) {
            if (part.isEmpty()) continue;
            current = current.getFolder(part);
            if (current == null) return null;
        }

        return current;
    }

    // FILE OPERATIONS
    public FileEntity createFile(String folderPath, String fileName, String content, String userId) {
        Folder folder = navigateToFolder(folderPath, userId);
        if (folder == null) {
            System.out.println("✗ Folder not found");
            return null;
        }

        FileEntity file = new FileEntity(fileName, content, userId);
        folder.addFile(file);
        System.out.println("✓ File created: " + fileName + " in " + folderPath);
        return file;
    }

    public FileEntity readFile(String folderPath, String fileName, String userId) {
        Folder folder = navigateToFolder(folderPath, userId);
        if (folder == null) {
            System.out.println("✗ Folder not found");
            return null;
        }

        FileEntity file = folder.getFile(fileName);
        if (file == null) {
            System.out.println("✗ File not found");
            return null;
        }

        if (!file.getPermission().canUserAccess(userId, "read")) {
            System.out.println("✗ Permission denied");
            return null;
        }

        return file;
    }

    public void updateFile(String folderPath, String fileName, String newContent, String userId) {
        Folder folder = navigateToFolder(folderPath, userId);
        if (folder == null) {
            System.out.println("✗ Folder not found");
            return;
        }

        FileEntity file = folder.getFile(fileName);
        if (file == null) {
            System.out.println("✗ File not found");
            return;
        }

        if (!file.getOwnerUserId().equals(userId)) {
            System.out.println("✗ Permission denied");
            return;
        }

        file.updateContent(newContent);
        System.out.println("✓ File updated: " + fileName);
    }

    public void deleteFile(String folderPath, String fileName, String userId) {
        Folder folder = navigateToFolder(folderPath, userId);
        if (folder == null) {
            System.out.println("✗ Folder not found");
            return;
        }

        FileEntity file = folder.getFile(fileName);
        if (file == null) {
            System.out.println("✗ File not found");
            return;
        }

        if (!file.getOwnerUserId().equals(userId)) {
            System.out.println("✗ Permission denied");
            return;
        }

        // Move to trash
        TrashItem item = new TrashItem(fileName, "FILE", file, userId);
        trash.add(item);
        folder.deleteFile(fileName);
        System.out.println("✓ File moved to trash: " + fileName);
    }

    public void moveFile(String sourcePath, String fileName, String destPath, String userId) {
        Folder sourceFolder = navigateToFolder(sourcePath, userId);
        Folder destFolder = navigateToFolder(destPath, userId);

        if (sourceFolder == null || destFolder == null) {
            System.out.println("✗ Invalid path");
            return;
        }

        FileEntity file = sourceFolder.getFile(fileName);
        if (file == null) {
            System.out.println("✗ File not found");
            return;
        }

        if (!file.getOwnerUserId().equals(userId)) {
            System.out.println("✗ Permission denied");
            return;
        }

        sourceFolder.deleteFile(fileName);
        destFolder.addFile(file);
        System.out.println("✓ File moved: " + fileName + " from " + sourcePath + " to " + destPath);
    }

    // TRASH OPERATIONS
    public void viewTrash(String userId) {
        List<TrashItem> userTrash = trash.stream()
                .filter(t -> t.getDeletedByUserId().equals(userId))
                .toList();

        if (userTrash.isEmpty()) {
            System.out.println("Trash is empty");
            return;
        }

        System.out.println("\n--- Trash ---");
        userTrash.forEach(t -> System.out.println("  🗑️  " + t.getItemName() + " (" + t.getItemType() + ")"));
    }

    public void emptyTrash(String userId) {
        trash.removeIf(t -> t.getDeletedByUserId().equals(userId));
        System.out.println("✓ Trash emptied");
    }

    public void restoreFromTrash(String itemName, String userId) {
        TrashItem item = trash.stream()
                .filter(t -> t.getItemName().equals(itemName) && t.getDeletedByUserId().equals(userId))
                .findFirst()
                .orElse(null);

        if (item == null) {
            System.out.println("✗ Item not found in trash");
            return;
        }

        trash.remove(item);
        System.out.println("✓ Item restored from trash: " + itemName);
    }

    // UTILITY
    public void showStructure(String folderPath, String userId) {
        Folder folder = navigateToFolder(folderPath, userId);
        if (folder != null) {
            folder.showContents();
        } else {
            System.out.println("✗ Folder not found");
        }
    }

    public Folder getRootFolder(String userId) {
        return rootFolders.get(userId);
    }
}
