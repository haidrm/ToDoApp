package com.codepath.simpletodo;

import java.io.Serializable;

/**
 * Created by haidrm on 2/15/2017.
 */

public class Item implements Serializable{

    private long rowId;
    private String itemText;

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String item) {
        this.itemText = item;
    }
}
