package com.amuyu.customview.model;

import com.amuyu.customview.view.button.StateButtonFragment;
import com.amuyu.customview.view.wheel.WheelFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class ViewList {

    public static final List<Item> ITEMS = new ArrayList<Item>();
    public static final Map<String, Item> ITEM_MAP = new HashMap<String, Item>();


    static {

        addItem(createItem("StateButton",  "Button that changes style when clicked", StateButtonFragment.class.getName()));
        addItem(createItem("Wheel",  "Wheel that use fling animation", WheelFragment.class.getName()));

    }

    public static Item getItem(String id) {
        return ITEM_MAP.get(id);
    }

    private static void addItem(Item item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Item createItem(String title, String detail, String className) {
        return new Item(title, detail, className);
    }

    public static class Item {
        public final String id;
        public final String title;
        public final String detail;
        public final String className;

        public Item(String title, String detail, String className) {
            this(UUID.randomUUID().toString(), title, detail, className);
        }

        public Item(String id, String title, String detail, String className) {
            this.id = id;
            this.title = title;
            this.detail = detail;
            this.className = className;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
