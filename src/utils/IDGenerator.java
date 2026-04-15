package utils;

import java.util.UUID;

public class IDGenerator {
    public static String generateUserId() {
        return "U_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static String generateFileId() {
        return "F_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static String generateFolderId() {
        return "D_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
