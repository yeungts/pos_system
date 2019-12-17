package posSystem.model;

import java.util.*;

/**
 * Class name: ItemList
 * Authors: Kendrick Tsz-Kin Yeung
 * Date: 9 Apr 2019
 *
 * Description:
 * This class extends from ArrayList and will be used by other class
 */
public class ItemList extends ArrayList<Item> {

    
    /**
     * Default constructor
     */
    public ItemList() {
    }

    /**
     * This method will loop through all the items stored inside the arrayList,
     * and will write all the item names into a string array
     *
     * @return an array of item names
     */
    public String[] getItemNames() {
        String[] names = new String[this.size()];
        for (int i = 0; i < names.length; i++) {
            names[i] = this.get(i).getItemName();
        }
        return names;
    }

    /**
     * This method will loop through all the items stored inside the arrayList,
     * and will write all the item ID into a string array
     *
     * @return an array of item ID
     */
    public String[] getItemIDs() {
        String[] IDs = new String[this.size()];
        for (int i = 0; i < IDs.length; i++) {
            IDs[i] = this.get(i).getItemId();
        }
        return IDs;
    }

    /**
     * This method will loop through the arrayList and get the item that the ID
     * match the given ID
     *
     * @param ID ID of an Item
     * @return found item with same ID
     */
    public Item searchID(String ID) {
        Item foundItem = null;
        for (Item item : this) {
            if (item.getItemId().equals(ID)) {
                foundItem = item;
                break;
            }
        }
        return foundItem;
    }

}
