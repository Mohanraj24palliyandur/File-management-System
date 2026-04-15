package models;

import java.time.LocalDateTime;

public class FileEntity {
    private String fileName;
    private String content;
    private long fileSize;
    private String fileType;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String ownerUserId;
    private FilePermission permission;

    public FileEntity(String fileName, String content, String ownerUserId) {
        this.fileName = fileName;
        this.content = content;
        this.ownerUserId = ownerUserId;
        this.fileSize = content.length();
        this.fileType = extractFileType(fileName);
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
        this.permission = new FilePermission(ownerUserId);
    }

    private String extractFileType(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        return lastDot > 0 ? fileName.substring(lastDot + 1) : "txt";
    }

    public void updateContent(String newContent) {
        this.content = newContent;
        this.fileSize = newContent.length();
        this.lastModifiedDate = LocalDateTime.now();
    }

    public String readFile() {
        return this.content;
    }

    // Getters and Setters
    public String getFileName() {
        return fileName;
    }

    public String getContent() {
        return content;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public FilePermission getPermission() {
        return permission;
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", fileType='" + fileType + '\'' +
                ", createdDate=" + createdDate +
                ", owner='" + ownerUserId + '\'' +
                '}';
    }
}
