# File Management System - Architecture & Design Document

## 🏗️ System Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│         User Interface Layer (FileManagementSystemApp)      │
│              (Interactive Menu-Driven Interface)             │
└────────────────────┬────────────────────────────────────────┘
                     │
┌─────────────────────▼────────────────────────────────────────┐
│               Service Layer (Business Logic)                 │
│  ┌──────────────────────────────────────────────────────┐   │
│  │ • UserAuthenticationService    (User Mgmt & Auth)    │   │
│  │ • FileSystemService            (Core Operations)     │   │
│  │ • SearchService                (Search Logic)        │   │
│  └──────────────────────────────────────────────────────┘   │
└────────────────────┬────────────────────────────────────────┘
                     │
┌─────────────────────▼────────────────────────────────────────┐
│               Model Layer (Data Structures)                  │
│  ┌──────────────────────────────────────────────────────┐   │
│  │ • User                 (User entity with roles)      │   │
│  │ • FileEntity           (File with metadata)          │   │
│  │ • FilePermission       (Access control)              │   │
│  │ • Folder               (Directory hierarchy)         │   │
│  │ • TrashItem            (Deleted items)               │   │
│  └──────────────────────────────────────────────────────┘   │
└────────────────────┬────────────────────────────────────────┘
                     │
┌─────────────────────▼────────────────────────────────────────┐
│          Utility Layer (Helper Functions)                    │
│              IDGenerator (Unique ID creation)                │
└─────────────────────────────────────────────────────────────┘
```

## 📊 Class Diagram

```
┌──────────────────────────────────────────────────────────────────┐
│                           User                                   │
├──────────────────────────────────────────────────────────────────┤
│ - userId: String                                                 │
│ - username: String                                               │
│ - password: String                 ┌─────────────────┐          │
│ - role: UserRole ─────────────────→│   UserRole      │          │
│ - createdAt: LocalDateTime         │  - ADMIN        │          │
├──────────────────────────────────────┤ - REGULAR_USER │          │
│ + isAdmin(): boolean               └─────────────────┘          │
│ + getters/setters                                               │
└──────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────┐
│                       FileEntity                                 │
├──────────────────────────────────────────────────────────────────┤
│ - fileName: String                                               │
│ - content: String                                                │
│ - fileSize: long                                                 │
│ - fileType: String                                               │
│ - createdDate: LocalDateTime                                     │
│ - lastModifiedDate: LocalDateTime                                │
│ - ownerUserId: String              ┌──────────────────────┐    │
│ - permission: FilePermission ─────→│ FilePermission       │    │
├──────────────────────────────────────├──────────────────────┤    │
│ + updateContent(String): void      │ - ownerUserId        │    │
│ + readFile(): String               │ - isPublic           │    │
│ + getters/setters                  │ - canRead            │    │
└──────────────────────────────────────│ - canWrite           │    │
                                       │ - canDelete          │    │
                                       ├──────────────────────┤    │
                                       │ + canUserAccess()   │    │
                                       │ + getters/setters   │    │
                                       └──────────────────────┘    │

┌──────────────────────────────────────────────────────────────────┐
│                        Folder                                    │
├──────────────────────────────────────────────────────────────────┤
│ - folderId: String                                               │
│ - folderName: String                                             │
│ - parentFolderId: String (optional)                              │
│ - files: List<FileEntity>                                        │
│ - subfolders: List<Folder>  (Recursive aggregation)             │
│ - createdDate: LocalDateTime                                     │
│ - ownerUserId: String                                            │
├──────────────────────────────────────────────────────────────────┤
│ + addFile(FileEntity): void                                      │
│ + deleteFile(String): void                                       │
│ + getFile(String): FileEntity                                    │
│ + addFolder(Folder): void                                        │
│ + deleteFolder(String): void                                     │
│ + getFolder(String): Folder                                      │
│ + showContents(): void                                           │
│ + getters/setters                                                │
└──────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────┐
│                      TrashItem                                   │
├──────────────────────────────────────────────────────────────────┤
│ - itemName: String                                               │
│ - itemType: String (FILE or FOLDER)                             │
│ - originalItem: Object (FileEntity or Folder)                   │
│ - deletedDate: LocalDateTime                                     │
│ - deletedByUserId: String                                        │
├──────────────────────────────────────────────────────────────────┤
│ + getters (read-only after creation)                            │
└──────────────────────────────────────────────────────────────────┘
```

## 🔄 Service Interactions Diagram

```
FileManagementSystemApp (Main Entry Point)
        │
        ├─→ UserAuthenticationService
        │   • Register new users
        │   • Login user verification
        │   • User validation
        │
        ├─→ FileSystemService
        │   • Create/Read/Update/Delete files
        │   • Create/Delete folders
        │   • Move files between folders
        │   • Folder navigation
        │   • Trash management
        │
        └─→ SearchService
            • Search by name
            • Search by type
            • Search by size
            • Recursive folder traversal
```

## 🎯 OOP Concepts Implementation

### 1. Encapsulation

**Definition**: Data hiding and controlled access

```java
// Example from FileEntity
private String fileName;      // Hidden
private String content;       // Hidden
private LocalDateTime createdDate;  // Hidden

public String getFileName() {  // Controlled read access
    return fileName;
}

public void updateContent(String newContent) {  // Controlled write access
    this.content = newContent;
    this.lastModifiedDate = LocalDateTime.now();
}
```

### 2. Inheritance

**Definition**: Hierarchy and code reuse

```java
// User roles inheritance
public enum UserRole {
    ADMIN,
    REGULAR_USER
}

// Could be extended to:
// - AbstractUser (parent) → Admin, RegularUser (children)
// - Different permission levels based on inheritance
```

### 3. Polymorphism

**Definition**: Different behaviors for different types

```java
// Generic search results handling
public <T> void displaySearchResults(List<T> results, String searchType) {
    // Works with FileEntity or Folder objects
    results.forEach(result -> System.out.println(result.toString()));
}

// User roles with different capabilities
if (user.isAdmin()) {
    // Admin-specific operations
} else {
    // Regular user operations
}
```

### 4. Abstraction

**Definition**: Hidden complexity, simplified interfaces

```java
// FilePermission abstracts complex permission rules
public boolean canUserAccess(String userId, String action) {
    if (userId.equals(ownerUserId)) {
        return true;  // Owner always has access
    }
    if (isPublic && "read".equals(action)) {
        return true;  // Public files readable by all
    }
    return false;
}

// Service methods abstract implementation details
public void createFile(String folderPath, String fileName,
                       String content, String userId) {
    // Complex logic hidden from caller
}
```

## 🔐 Data Flow Examples

### File Creation Flow

```
User Input (FileManagementSystemApp)
    ↓
Validate folder path
    ↓
Create FileEntity instance
    ↓
Add FileEntity to Folder collection
    ↓
Set file metadata (size, type, date, owner)
    ↓
Return success message
```

### Search Operation Flow

```
User Input (filename/type/size)
    ↓
Get root folder for user
    ↓
Recursive folder traversal
    ├─ Search current folder
    └─ Search subfolders
    ↓
Collect matching results
    ↓
Filter by user permissions
    ↓
Display results
```

### Authentication Flow

```
User Credentials (username, password)
    ↓
Lookup user in authentication service
    ↓
Verify password match
    ↓
If valid:
    - Set currentUser
    - Initialize FileSystemService for user
    - Provide access to file operations
    ↓
If invalid:
    - Display error
    - Return to login screen
```

## 📈 Scalability Considerations

### Current Design Limitations

- In-memory storage (max: available RAM)
- Single-machine execution
- No concurrent user support

### Potential Improvements

```
1. Database Integration
   - Replace HashMap with SQL database
   - Scale to millions of files

2. Distributed Architecture
   - Multiple server nodes
   - Distributed file storage (like HDFS)

3. Concurrency Control
   - Thread-safe operations
   - Lock management for file access

4. Caching Layer
   - Frequently accessed files in cache
   - Improved performance

5. Cloud Storage Integration
   - S3, Google Cloud, Azure storage
   - Scalable and reliable
```

## 🧩 Design Patterns Used

### 1. Service Layer Pattern

- Separates business logic from presentation
- Services handle all operations
- Easy to test and maintain

### 2. Repository Pattern (Implicit)

- FileSystemService acts as repository
- Manages data access and persistence
- Centralized data management

### 3. Strategy Pattern (SearchService)

- Different search strategies (name, type, size)
- Flexible and extensible

### 4. Factory Pattern (Optional Enhancement)

- Could create FileEntity, Folder, User through factories
- Centralizes object creation

### 5. Observer Pattern (Optional Enhancement)

- Notify users of file system changes
- Event-driven notifications

## 📋 Method Signatures Overview

### FileSystemService Core Methods

```java
// Folder Operations
Folder createRootFolder(String name, String userId)
Folder createSubfolder(String parentPath, String name, String userId)
void deleteFolder(String path, String userId)
Folder navigateToFolder(String path, String userId)

// File Operations
FileEntity createFile(String path, String name, String content, String userId)
FileEntity readFile(String path, String name, String userId)
void updateFile(String path, String name, String newContent, String userId)
void deleteFile(String path, String name, String userId)
void moveFile(String srcPath, String name, String destPath, String userId)

// Trash Operations
void viewTrash(String userId)
void emptyTrash(String userId)
void restoreFromTrash(String itemName, String userId)
```

### SearchService Methods

```java
List<FileEntity> searchFilesByName(String name, String userId)
List<FileEntity> searchFilesByType(String type, String userId)
List<FileEntity> searchFilesBySize(long min, long max, String userId)
List<Folder> searchFoldersByName(String name, String userId)
void displaySearchResults(List<?> results, String searchType)
```

### UserAuthenticationService Methods

```java
User registerUser(String id, String username, String password, UserRole role)
User loginUser(String id, String password)
User getUserById(String id)
boolean isValidUser(String id)
boolean isAdmin(String id)
void displayAllUsers()
```

## 🔍 Key Design Decisions

### 1. Why Separate Services?

- **Responsibility**: Each service has single responsibility
- **Testability**: Easy to test individual services
- **Maintainability**: Changes isolated to relevant service
- **Reusability**: Services can be used independently

### 2. Why In-Memory Storage?

- **Simplicity**: Educational prototype focus
- **Performance**: Fastest access for demonstration
- **Scalability**: Easy to replace with database

### 3. Why Folder Hierarchy?

- **Intuitive**: Mirrors real file systems
- **Organization**: Natural grouping of files
- **Navigation**: Easy path-based access

### 4. Why Soft Delete (Trash)?

- **Safety**: Recover deleted items
- **User Experience**: Familiar from OS
- **Data Integrity**: Audit trail maintained

### 5. Why Permission System?

- **Security**: Control file access
- **Privacy**: Protect user data
- **Scalability**: Ready for multi-user scenarios

---

## 🚀 Extension Points

### Easy Enhancements

1. Add file versioning
2. Implement sharing with permissions
3. Add file compression
4. Create backup functionality

### Medium Complexity

1. Integrate with database
2. Add concurrent user support
3. Implement encryption
4. Create audit logging

### Advanced Features

1. Distributed file system
2. Cloud storage integration
3. Advanced search with indexing
4. User collaboration features

---

**Document Version**: 1.0
**Last Updated**: 2026
**Architecture**: Service-Oriented with MVC Pattern
