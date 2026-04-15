package services;

import models.FileEntity;
import models.Folder;
import java.util.*;

public class SearchService {
    private FileSystemService fileSystemService;

    public SearchService(FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }

    public List<FileEntity> searchFilesByName(String fileName, String userId) {
        Folder rootFolder = fileSystemService.getRootFolder(userId);
        List<FileEntity> results = new ArrayList<>();

        if (rootFolder != null) {
            searchFilesRecursive(rootFolder, fileName, results);
        }

        return results;
    }

    public List<FileEntity> searchFilesByType(String fileType, String userId) {
        Folder rootFolder = fileSystemService.getRootFolder(userId);
        List<FileEntity> results = new ArrayList<>();

        if (rootFolder != null) {
            searchFilesByTypeRecursive(rootFolder, fileType, results);
        }

        return results;
    }

    public List<FileEntity> searchFilesBySize(long minSize, long maxSize, String userId) {
        Folder rootFolder = fileSystemService.getRootFolder(userId);
        List<FileEntity> results = new ArrayList<>();

        if (rootFolder != null) {
            searchFilesBySizeRecursive(rootFolder, minSize, maxSize, results);
        }

        return results;
    }

    public List<Folder> searchFoldersByName(String folderName, String userId) {
        Folder rootFolder = fileSystemService.getRootFolder(userId);
        List<Folder> results = new ArrayList<>();

        if (rootFolder != null) {
            searchFoldersRecursive(rootFolder, folderName, results);
        }

        return results;
    }

    private void searchFilesRecursive(Folder folder, String fileName, List<FileEntity> results) {
        // Search in current folder
        folder.getAllFiles().stream()
                .filter(f -> f.getFileName().toLowerCase().contains(fileName.toLowerCase()))
                .forEach(results::add);

        // Search in subfolders
        folder.getAllSubfolders().forEach(subFolder ->
                searchFilesRecursive(subFolder, fileName, results)
        );
    }

    private void searchFilesByTypeRecursive(Folder folder, String fileType, List<FileEntity> results) {
        folder.getAllFiles().stream()
                .filter(f -> f.getFileType().equalsIgnoreCase(fileType))
                .forEach(results::add);

        folder.getAllSubfolders().forEach(subFolder ->
                searchFilesByTypeRecursive(subFolder, fileType, results)
        );
    }

    private void searchFilesBySizeRecursive(Folder folder, long minSize, long maxSize, List<FileEntity> results) {
        folder.getAllFiles().stream()
                .filter(f -> f.getFileSize() >= minSize && f.getFileSize() <= maxSize)
                .forEach(results::add);

        folder.getAllSubfolders().forEach(subFolder ->
                searchFilesBySizeRecursive(subFolder, minSize, maxSize, results)
        );
    }

    private void searchFoldersRecursive(Folder folder, String folderName, List<Folder> results) {
        folder.getAllSubfolders().stream()
                .filter(f -> f.getFolderName().toLowerCase().contains(folderName.toLowerCase()))
                .forEach(results::add);

        folder.getAllSubfolders().forEach(subFolder ->
                searchFoldersRecursive(subFolder, folderName, results)
        );
    }

    public void displaySearchResults(List<? extends Object> results, String searchType) {
        if (results.isEmpty()) {
            System.out.println("✗ No results found");
            return;
        }

        System.out.println("\n--- Search Results (" + searchType + ") ---");
        results.forEach(result -> System.out.println("  ✓ " + result.toString()));
    }
}
