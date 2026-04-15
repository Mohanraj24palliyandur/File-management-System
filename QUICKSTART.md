# Quick Start Guide - File Management System

## Prerequisites

- Java 11 or higher
- Command line/Terminal access

## 📋 Compilation Steps

### Step 1: Navigate to project directory

```bash
cd d:\file management\src
```

### Step 2: Compile all Java files

```bash
# On Windows (PowerShell or CMD)
javac *.java models\*.java services\*.java utils\*.java

# On Mac/Linux
javac *.java models/*.java services/*.java utils/*.java
```

### Step 3: Run the application

**Option A - Interactive Menu (Recommended for first-time users)**

```bash
java FileManagementSystemApp
```

**Option B - Automated Demo (See all features in action)**

```bash
java FMSDemo
```

## 🎮 Interactive Application Usage

### Main Menu Options

1. **Create File**
   - Provide folder path (e.g., `root`, `root/Documents`)
   - Enter filename and content
   - File is created in specified folder

2. **Read File**
   - Navigate to folder path
   - Enter filename
   - View file content

3. **Update File**
   - Navigate to folder
   - Enter filename
   - Provide new content
   - File is updated with new content

4. **Delete File**
   - File moves to trash (soft delete)
   - Can be restored later

5. **Move File**
   - Cut file from source folder
   - Paste into destination folder

6. **Create Folder**
   - Option for root or subfolder
   - Root folders created directly under user
   - Subfolders created inside existing folders

7. **Delete Folder**
   - Folder moves to trash
   - Can be restored from trash

8. **View Folder Contents**
   - See all files and subfolders
   - View file sizes

9. **Search Files**
   - Search by filename (partial match)
   - Search by file type (extension)
   - Search by file size range

10. **Trash Management**
    - View deleted items
    - Restore from trash
    - Empty trash permanently

11. **Admin Features** (if logged in as Admin)
    - View all registered users
    - Full system access

## 👤 Demo Users (Available after registration)

### In FMSDemo.java:

- **Admin User**
  - Username: `admin`
  - Password: `admin123`
  - Role: ADMIN

- **User 1**
  - Username: `john_doe`
  - Password: `pass123`
  - Role: REGULAR_USER

- **User 2**
  - Username: `jane_smith`
  - Password: `pass456`
  - Role: REGULAR_USER

## 📂 Example Workflow

### 1. First Time Setup

```
Choose: 1 (Register)
Username: myname
Password: mypass123
Admin: n
```

### 2. Login

```
Choose: 2 (Login)
Username: myname
Password: mypass123
```

### 3. Create Folder Structure

```
Choose: 6 (Create Folder)
Type: root
Folder name: My Documents
```

### 4. Create Files

```
Choose: 1 (Create File)
Folder path: root
File name: notes.txt
Content: My first file in FMS
```

### 5. View Contents

```
Choose: 8 (View Folder Contents)
Folder path: root
```

### 6. Search Files

```
Choose: 9 (Search Files)
Option: 1 (Search by name)
Enter partial name: notes
```

## 🔍 Sample Folder Paths

```
root                              # Root directory
root/Documents                    # Subfolder
root/Documents/Work               # Nested subfolder
root/Projects                     # Another branch
```

## 📝 File Format Support

The system supports any file type:

- Text files: `.txt`, `.md`, `.doc`
- Code files: `.java`, `.py`, `.js`, `.cpp`
- Data files: `.json`, `.xml`, `.csv`
- Documents: `.pdf`, `.docx`
- Any other extension

## ✨ Key Features to Try

1. **Soft Delete**: Delete files to trash, restore them later
2. **Multi-level Folders**: Create nested folder structures
3. **Search**: Find files across all folders recursively
4. **File Permissions**: Make files public/private
5. **Multi-user**: Each user has isolated file system
6. **Admin Functions**: View all registered users (Admin only)

## 🐛 Troubleshooting

### Issue: "Command not found: javac"

- **Solution**: Java not in PATH. Install Java or add to PATH

### Issue: "Class not found" errors

- **Solution**: Ensure you're compiling with correct paths
- Try: `javac -cp . *.java models\*.java services\*.java utils\*.java`

### Issue: Files not appearing

- **Solution**: Make sure you're viewing the correct folder path
- Try viewing root with: `root` or `root/FolderName`

### Issue: Can't login after registering

- **Solution**: Username and password are case-sensitive
- Ensure exact match during login

## 📊 System Characteristics

| Feature     | Details                         |
| ----------- | ------------------------------- |
| Storage     | In-memory (RAM)                 |
| Persistence | Not saved between sessions      |
| Users       | Multiple isolated file systems  |
| Limits      | No size limits (limited by RAM) |
| Permissions | Owner-based access control      |
| Search      | Recursive file tree search      |

## 🚀 Next Steps

1. Run FMSDemo to see all features in action
2. Try Interactive App to create your own structure
3. Explore the source code in `/src` directory
4. Study the architecture in README.md

## 💡 Tips & Tricks

- Use consistent folder naming (avoid spaces if possible)
- Start with root folder creation before adding files
- Search results show full file information
- Trash items can be viewed anytime before emptying
- Admin users have access to more system information

---

**Need Help?** Check the comprehensive README.md or review the FMSDemo.java for working examples!
