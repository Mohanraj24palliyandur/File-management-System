package models;

public class FilePermission {
    private String ownerUserId;
    private boolean isPublic;
    private boolean canRead;
    private boolean canWrite;
    private boolean canDelete;

    public FilePermission(String ownerUserId) {
        this.ownerUserId = ownerUserId;
        this.isPublic = false;
        this.canRead = true;
        this.canWrite = true;
        this.canDelete = true;
    }

    public boolean canUserAccess(String userId, String action) {
        // Owner always has full access
        if (userId.equals(ownerUserId)) {
            return true;
        }

        // Public files can be read by anyone
        if (isPublic && "read".equals(action)) {
            return true;
        }

        return false;
    }

    // Getters and Setters
    public String getOwnerUserId() {
        return ownerUserId;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean canRead() {
        return canRead;
    }

    public boolean canWrite() {
        return canWrite;
    }

    public boolean canDelete() {
        return canDelete;
    }

    @Override
    public String toString() {
        return "FilePermission{" +
                "owner='" + ownerUserId + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }
}
