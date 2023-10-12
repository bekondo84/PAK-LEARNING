package cm.nzock.eLearning.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomePageComponentData implements Serializable {
    private List<MenuItemData> menuitems ;

    public HomePageComponentData() {
        menuitems = new ArrayList<>();
    }

    public List<MenuItemData> getMenuitems() {
        menuitems.sort(new Comparator<MenuItemData>() {
            @Override
            public int compare(MenuItemData o1, MenuItemData o2) {
                return Integer.valueOf(o1.getSequence()).compareTo(Integer.valueOf(o2.getSequence()));
            }
        });
        return Collections.unmodifiableList(menuitems);
    }

    public void setMenuitems(List<MenuItemData> menuitems) {
        this.menuitems = menuitems;
    }

    public void addMenuItem(MenuItemData item) {
        menuitems.add(item);
    }
}
