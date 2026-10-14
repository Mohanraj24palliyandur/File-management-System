```java
package models;

import java.time.LocalDateTime;

public class FileEntity {

    // Stores the file details
    private String fileName;
    private String content;
    private long fileSize;
    private String fileType;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String ownerUserId;
    private FilePermission permission;

    // Initializes the file with its name, content, and owner
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

    // Extracts the file extension from the file name
    private String extractFileType(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        return lastDot > 0 ? fileName.substring(lastDot + 1) : "txt";
    }

    // Updates the file content, size, and modification date
    public void updateContent(String newContent) {
        this.content = newContent;
        this.fileSize = newContent.length();
        this.lastModifiedDate = LocalDateTime.now();
    }

    // Returns the file content
    public String readFile() {
        return this.content;
    }

    // Returns the file name
    public String getFileName() {
        return fileName;
    }

    // Returns the file content
    public String getContent() {
        return content;
    }

    // Returns the file size
    public long getFileSize() {
        return fileSize;
    }

    // Returns the file type
    public String getFileType() {
        return fileType;
    }

    // Returns the file creation date
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    // Returns the last modification date
    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    // Returns the owner's user ID
    public String getOwnerUserId() {
        return ownerUserId;
    }

    // Returns the file permission details
    public FilePermission getPermission() {
        return permission;
    }

    // Returns the file details as a string
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
```
