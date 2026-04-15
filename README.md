# File Management System (FMS) - OOP Prototype

A comprehensive Java-based File Management System built with Object-Oriented Programming principles.

## 📁 Project Structure

```
src/
├── models/                      # Data models
│   ├── User.java               # User entity with roles
│   ├── FileEntity.java         # File data model
│   ├── Folder.java             # Folder/Directory model
│   ├── FilePermission.java     # File access control
│   └── TrashItem.java          # Deleted items tracking
│
├── services/                    # Business logic layer
│   ├── UserAuthenticationService.java  # User login/registration
│   ├── FileSystemService.java          # Core file/folder operations
│   └── SearchService.java              # File search functionality
│
├── utils/                       # Utility classes
│   └── IDGenerator.java        # ID generation utility
│
├── FileManagementSystemApp.java # Interactive menu-driven application
└── FMSDemo.java                # Demonstration of system features
```

## 🎯 Key Features

### ✅ Core Operations

- **File Operations**: Create, Read, Update, Delete files
- **Folder Operations**: Create, Delete, Navigate through folder hierarchies
- **File Movement**: Move files between folders
- **Search**: Search files by name, type, or size range
- **Trash System**: Soft delete files and restore from trash

### 👤 User Management

- User registration and login system
- Role-based access control (Admin, Regular User)
- User-specific file ownership and privacy
- Admin privileges for system management

### 🔐 Security & Permissions

- File ownership tracking
- Permission system (Public/Private)
- Access control verification
- User-based file isolation

### 📊 Advanced Features

- Recursive folder navigation
- File metadata (size, type, creation date, modification date)
- File type detection
- Trash/Recycle bin functionality
- Multi-user support with isolated file systems

## 🏗️ Class Hierarchy & OOP Concepts

### OOP Principles Used

1. **Encapsulation**:
   - Private fields with public getters/setters
   - Data hiding within models

2. **Inheritance**:
   - Could extend to specialized file types
   - Enum-based role system

3. **Polymorphism**:
   - SearchService uses generics for different search types
   - Different user roles with different permissions

4. **Abstraction**:
   - Services abstract complex operations
   - High-level methods hide implementation details
   - FilePermission abstraction for access control

5. **Single Responsibility**:
   - Each service handles specific domain
   - Each model represents single entity

## 🚀 How to Run

### Option 1: Interactive Application

```bash
cd src
javac *.java models/*.java services/*.java utils/*.java
java FileManagementSystemApp
```

### Option 2: Demonstration

```bash
cd src
javac *.java models/*.java services/*.java utils/*.java
java FMSDemo
```

## 📖 Usage Examples

### Creating and Managing Files

```java
FileSystemService fs = new FileSystemService();
fs.createRootFolder("My Files", "U001");
fs.createFile("root", "resume.txt", "Content here", "U001");
fs.updateFile("root", "resume.txt", "Updated content", "U001");
fs.deleteFile("root", "resume.txt", "U001");
```

### User Authentication

```java
UserAuthenticationService auth = new UserAuthenticationService();
User user = auth.registerUser("U001", "john", "pass123", User.UserRole.REGULAR_USER);
User logged = auth.loginUser("U001", "pass123");
```

### Search Operations

```java
SearchService search = new SearchService(fileSystemService);
List<FileEntity> results = search.searchFilesByName("resume", "U001");
List<FileEntity> pdfFiles = search.searchFilesByType("pdf", "U001");
List<FileEntity> largeFiles = search.searchFilesBySize(10000, 1000000, "U001");
```

### Folder Navigation

```java
// Create folder structure
fs.createSubfolder("root", "Documents", "U001");
fs.createSubfolder("root/Documents", "Work", "U001");

// Navigate and view
fs.createFile("root/Documents/Work", "project.txt", "...", "U001");
fs.showStructure("root/Documents", "U001");
```

## 🔄 Data Flow

```
Authentication
    ↓
User Login
    ↓
Create Root Folder
    ↓
Manage Files & Folders
    ├─ Create/Read/Update/Delete
    ├─ Move between folders
    ├─ Search functionality
    └─ Trash management
    ↓
Restore or Empty Trash
```

## 🧩 Architecture

### Service Layer Pattern

- **UserAuthenticationService**: Handles user registration, login, validation
- **FileSystemService**: Core CRUD operations, folder navigation
- **SearchService**: Advanced search with recursive traversal

### Model Layer (Data Representation)

- Entities represent real-world objects
- Each model has its own responsibilities
- Metadata tracking (creation date, owner, permissions)

### Utility Layer

- IDGenerator for unique identifiers
- Helper functions for common operations

## 🔒 Security Features

1. **Password Protection**: Stored plaintext (for demo; use hashing in production)
2. **File Permissions**: Owner-based access control
3. **User Isolation**: Each user has separate file system scope
4. **Admin Access**: Admin users can view all registered users
5. **Ownership Tracking**: Every file/folder has owner information

## 💾 File Metadata Tracked

- Filename
- File content
- File size (in bytes)
- File type (extension)
- Creation date/time
- Last modified date/time
- Owner user ID
- File permissions (Public/Private)

## 🎓 Learning Outcomes

This project demonstrates:

- Object-Oriented Design principles
- Service-oriented architecture
- Recursive algorithms (folder tree traversal)
- Collection framework usage (ArrayList, HashMap)
- Exception handling and validation
- User authentication basics
- Multi-user system design
- Search algorithm optimization

## 🚧 Potential Enhancements

1. **Database Integration**: Replace in-memory storage with database
2. **File Compression**: Add compression functionality
3. **Versioning**: Track file version history
4. **Sharing**: Share files with other users
5. **Backup**: Automatic backup functionality
6. **Encryption**: Encrypt sensitive files
7. **Audit Logging**: Track all operations
8. **UI Enhancement**: Add GUI with Swing/JavaFX
9. **File Upload**: Handle actual file uploads
10. **Quotas**: Storage space limitations per user

## 📝 Notes

- This is an educational prototype demonstrating OOP concepts
- All data is stored in memory (lost on application shutdown)
- For production use, integrate with a database
- Consider adding proper exception handling and logging
- Implement proper password hashing and security measures

---

**Status**: Prototype/Educational Project
**Language**: Java 11+
**Architecture**: Service-Oriented with MVC patterns
