package com.tydeya.familycircle.ui.deliverypart.main.details.recyclerview;

public class MainPlanItem {


    private String title;
    private String text;
    private int iconId;
    private MainPlanItemType itemType;

    public MainPlanItem(String title, String text, int iconId, MainPlanItemType itemType) {
        this.title = title;
        this.text = text;
        this.iconId = iconId;
        this.itemType = itemType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public MainPlanItemType getItemType() {
        return itemType;
    }

    public void setItemType(MainPlanItemType itemType) {
        this.itemType = itemType;
    }
}
