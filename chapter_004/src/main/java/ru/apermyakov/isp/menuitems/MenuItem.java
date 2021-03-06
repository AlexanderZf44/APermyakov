package ru.apermyakov.isp.menuitems;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for modulate menu item by item's interfaces.
 *
 * @author apermyakov.
 * @version 1.0.
 * @since 10.01.2018.
 */
public class MenuItem implements Item {

    /**
     * Field for item's name.
     */
    private String name;

    /**
     * Field for item's key.
     */
    private String key;

    /**
     * Field for parent.
     */
    private Item parent;

    /**
     * Field for sub menu flag.
     */
    private boolean subItem = false;

    /**
     * Field for root menu flag.
     */
    private boolean rootItem = false;

    /**
     * Field for list of item.
     */
    private List<Item> children = new ArrayList<>();

    /**
     * Constructor for menu item.
     *
     * @param key key.
     * @param name name.
     */
    public MenuItem(String key, String name) {
        this.key = key;
        this.name = name;
    }

    /**
     * Method for do item's action.
     */
    @Override
    public void doAction() {
        System.out.format("The action of the %s %s menu item is executed%s", key, name, System.lineSeparator());
    }

    /**
     * Method for get item's key.
     *
     * @return item's key.
     */
    @Override
    public String getKey() {
        return this.key;
    }

    /**
     * Method for get item's name.
     *
     * @return item's name.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Method for check item's key by another key.
     *
     * @param key another key.
     * @return check result.
     */
    @Override
    public boolean checkKey(String key) {
        return key.equals(this.key);
    }

    /**
     * Method for get parent.
     *
     * @return parent.
     */
    public Item getParent() {
        return this.parent;
    }

    /**
     * Method for set parent.
     *
     * @param parent parent.
     */
    public void setParent(Item parent) {
        this.parent = parent;
        this.subItem = true;
    }

    /**
     * Method for get children.
     *
     * @return list of children.
     */
    public List<Item> getChildren() {
        return this.children;
    }

    /**
     * Method for set child.
     *
     * @param child child.
     */
    @Override
    public void setChild(Item child) {
        this.children.add(child);
        child.setParent(this);
        this.rootItem = true;
    }

    /**
     * Method for check sub flag.
     *
     * @return sub flag.
     */
    @Override
    public boolean isSubItem() {
        return this.subItem;
    }

    /**
     * Method for check root flag.
     *
     * @return root flag.
     */
    @Override
    public boolean isRootItem() {
        return this.rootItem;
    }
}
