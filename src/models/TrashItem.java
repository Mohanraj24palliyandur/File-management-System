package models;

import java.time.LocalDateTime;

public class TrashItem {
    private String itemName;
    private String itemType; // "FILE" or "FOLDER"
    private Object originalItem;
    private LocalDateTime deletedDate;
    private String deletedByUserId;

    public TrashItem(String itemName, String itemType, Object originalItem, String deletedByUserId) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.originalItem = originalItem;
        this.deletedByUserId = deletedByUserId;
        this.deletedDate = LocalDateTime.now();
    }

    // Getters
    public String getItemName() {
        return itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public Object getOriginalItem() {
        return originalItem;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public String getDeletedByUserId() {
        return deletedByUserId;
    }

    @Override
    public String toString() {
        return "TrashItem{" +
                "itemName='" + itemName + '\'' +
                ", itemType='" + itemType + '\'' +
                ", deletedDate=" + deletedDate +
                ", deletedBy='" + deletedByUserId + '\'' +
                '}';
    }
}
