package models;

import java.time.LocalDateTime;
import java.util.*;

public class Folder {
    private String folderId;
    private String folderName;
    private String parentFolderId; // null for root folders
    private List<FileEntity> files;
    private List<Folder> subfolders;
    private LocalDateTime createdDate;
    private String ownerUserId;

    public Folder(String folderId, String folderName, String ownerUserId) {
        this.folderId = folderId;
        this.folderName = folderName;
        this.parentFolderId = null;
        this.files = new ArrayList<>();
        this.subfolders = new ArrayList<>();
        this.createdDate = LocalDateTime.now();
        this.ownerUserId = ownerUserId;
    }

    public Folder(String folderId, String folderName, String parentFolderId, String ownerUserId) {
        this(folderId, folderName, ownerUserId);
        this.parentFolderId = parentFolderId;
    }

    public void addFile(FileEntity file) {
        files.add(file);
    }

    public void deleteFile(String fileName) {
        files.removeIf(f -> f.getFileName().equals(fileName));
    }

    public FileEntity getFile(String fileName) {
        return files.stream()
                .filter(f -> f.getFileName().equals(fileName))
                .findFirst()
                .orElse(null);
    }

    public void addFolder(Folder folder) {
        subfolders.add(folder);
    }

    public void deleteFolder(String folderName) {
        subfolders.removeIf(f -> f.getFolderName().equals(folderName));
    }

    public Folder getFolder(String folderName) {
        return subfolders.stream()
                .filter(f -> f.getFolderName().equals(folderName))
                .findFirst()
                .orElse(null);
    }

    public List<FileEntity> getAllFiles() {
        return new ArrayList<>(files);
    }

    public List<Folder> getAllSubfolders() {
        return new ArrayList<>(subfolders);
    }

    public void showContents() {
        System.out.println("\n--- Folder: " + folderName + " ---");
        System.out.println("Files:");
        if (files.isEmpty()) {
            System.out.println("  (empty)");
        } else {
            files.forEach(f -> System.out.println("  📄 " + f.getFileName() + " (" + f.getFileSize() + " bytes)"));
        }

        System.out.println("Subfolders:");
        if (subfolders.isEmpty()) {
            System.out.println("  (empty)");
        } else {
            subfolders.forEach(f -> System.out.println("  📂 " + f.getFolderName()));
        }
    }

    // Getters and Setters
    public String getFolderId() {
        return folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getParentFolderId() {
        return parentFolderId;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public int getFileCount() {
        return files.size();
    }

    public int getSubfolderCount() {
        return subfolders.size();
    }

    @Override
    public String toString() {
        return "Folder{" +
                "folderId='" + folderId + '\'' +
                ", folderName='" + folderName + '\'' +
                ", files=" + files.size() +
                ", subfolders=" + subfolders.size() +
                ", owner='" + ownerUserId + '\'' +
                '}';
    }
}
