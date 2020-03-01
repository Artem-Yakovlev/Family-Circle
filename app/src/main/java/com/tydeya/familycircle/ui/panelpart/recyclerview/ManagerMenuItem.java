package com.tydeya.familycircle.ui.panelpart.recyclerview;

public class ManagerMenuItem {

    private int imageId;
    private String title;
    private ManagerMenuItemType itemType;

    public ManagerMenuItem(int imageId, String title, ManagerMenuItemType itemType) {
        this.imageId = imageId;
        this.title = title;
        this.itemType = itemType;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ManagerMenuItemType getItemType() {
        return itemType;
    }

    public void setItemType(ManagerMenuItemType itemType) {
        this.itemType = itemType;
    }
}
