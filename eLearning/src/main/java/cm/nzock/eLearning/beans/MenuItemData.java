package cm.nzock.eLearning.beans;

import java.io.Serializable;

public class MenuItemData implements Serializable {
    private String code ;
    private String text ;
    private String badge ;
    private int total ;
    private String icon;
    private int sequence;

    public MenuItemData() {
    }

    public String getCode() {
        return code;
    }

    public MenuItemData setCode(String code) {
        this.code = code;
        return this;
    }

    public String getText() {
        return text;
    }

    public MenuItemData setText(String intitule) {
        this.text = intitule;
        return this;
    }

    public String getBadge() {
        return badge;
    }

    public MenuItemData setBadge(String badge) {
        this.badge = badge;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public MenuItemData setTotal(int total) {
        this.total = total;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public MenuItemData setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public int getSequence() {
        return sequence;
    }

    public MenuItemData setSequence(int sequence) {
        this.sequence = sequence;
        return this;
    }
}
