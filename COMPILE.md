# File Management System - Compilation Guide

## System Requirements

- Java Development Kit (JDK) 11 or higher
- 100 MB free disk space
- Terminal/Command Prompt access

## 🔧 Installation & Setup

### Windows

#### Method 1: PowerShell

```powershell
cd 'd:\file management\src'
javac *.java models\*.java services\*.java utils\*.java
```

#### Method 2: Command Prompt (cmd)

```cmd
cd d:\file management\src
javac *.java models\*.java services\*.java utils\*.java
```

### Mac/Linux

```bash
cd '/your/path/to/file management/src'
javac *.java models/*.java services/*.java utils/*.java
```

## ▶️ Running the Application

### Interactive Mode

```bash
java FileManagementSystemApp
```

This launches an interactive menu where you can:

- Register and login users
- Manage files and folders
- Search and organize files
- Use admin features

### Demo Mode

```bash
java FMSDemo
```

This runs an automated demonstration showing:

- All system features in action
- Example workflows
- Best practices
- Complete feature tour

## 📦 Project Files

After compilation, you'll have the following class files:

### Models (models/ directory)

```
User.class
FileEntity.class
FilePermission.class
Folder.class
TrashItem.class
```

### Services (services/ directory)

```
UserAuthenticationService.class
FileSystemService.class
SearchService.class
```

### Utilities (utils/ directory)

```
IDGenerator.class
```

### Main Applications

```
FileManagementSystemApp.class
FMSDemo.class
```

## 🔄 Compilation Troubleshooting

### Problem: "javac: command not found"

**Cause**: Java compiler not in system PATH

**Solution**:

1. Install JDK from oracle.com
2. Add JDK bin directory to PATH
3. Restart terminal/command prompt

**Verify Installation**:

```bash
javac -version
```

### Problem: "Cannot find symbol" errors

**Cause**: Files not compiling together properly

**Solution**:

```bash
# Windows
javac /D . *.java models\*.java services\*.java utils\*.java

# Mac/Linux
javac -d . *.java models/*.java services/*.java utils/*.java
```

### Problem: "class FileManagementSystemApp not found"

**Cause**: Not in correct directory or compilation failed

**Solution**:

```bash
# Verify you're in src directory
cd path/to/file\ management/src
ls  # (Mac/Linux) or dir (Windows) to see files
javac *.java models/*.java services/*.java utils/*.java
java FileManagementSystemApp
```

## ✅ Successful Compilation Indicators

After successful compilation, you should see:

1. No error messages in terminal
2. New `.class` files created in each directory
3. Ability to run `java FileManagementSystemApp`

## 📋 Detailed File Structure After Compilation

```
src/
├── FileManagementSystemApp.class
├── FileManagementSystemApp.java
├── FMSDemo.class
├── FMSDemo.java
├── models/
│   ├── User.class
│   ├── User.java
│   ├── FileEntity.class
│   ├── FileEntity.java
│   ├── FilePermission.class
│   ├── FilePermission.java
│   ├── Folder.class
│   ├── Folder.java
│   ├── TrashItem.class
│   └── TrashItem.java
├── services/
│   ├── FileSystemService.class
│   ├── FileSystemService.java
│   ├── UserAuthenticationService.class
│   ├── UserAuthenticationService.java
│   ├── SearchService.class
│   └── SearchService.java
└── utils/
    ├── IDGenerator.class
    └── IDGenerator.java
```

## 🎯 Quick Compile & Run

### Windows (All in One)

```powershell
cd 'd:\file management\src'; javac *.java models\*.java services\*.java utils\*.java; java FileManagementSystemApp
```

### Mac/Linux (All in One)

```bash
cd './file management/src' && javac *.java models/*.java services/*.java utils/*.java && java FileManagementSystemApp
```

## 🧹 Cleanup (Remove compiled files)

### Windows

```powershell
Remove-Item -Recurse -Force *.class
Remove-Item -Recurse -Force models\*.class
Remove-Item -Recurse -Force services\*.class
Remove-Item -Recurse -Force utils\*.class
```

### Mac/Linux

```bash
find . -name "*.class" -delete
```

## 📝 Compile with Optimization

For better performance:

```bash
# Windows
javac -O *.java models\*.java services\*.java utils\*.java

# Mac/Linux
javac -O *.java models/*.java services/*.java utils/*.java
```

## 🔍 Verify Compilation

Check if all classes compiled:

```bash
# Windows
dir /s *.class

# Mac/Linux
find . -name "*.class"
```

You should see 13 .class files total:

- 5 in models/
- 3 in services/
- 1 in utils/
- 2 in src/ directory
- 2 additional (if running demo and app)

## 💾 Running in Background

### Windows (PowerShell)

```powershell
Start-Process java -ArgumentList "FileManagementSystemApp"
```

### Mac/Linux (Background)

```bash
java FileManagementSystemApp &
```

## 🚀 Advanced Compilation Options

### With Debug Information

```bash
javac -g *.java models/*.java services/*.java utils/*.java
```

### With Deprecation Warnings

```bash
javac -deprecation *.java models/*.java services/*.java utils/*.java
```

### Suppress Warnings

```bash
javac -nowarn *.java models/*.java services/*.java utils/*.java
```

---

**Ready to go!** Follow the "Running the Application" section above to start using FMS.
