package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Item> items;
    ItemAdapter itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 10;
    ItemsDatabaseHelper handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = ItemsDatabaseHelper.getsInstance(getApplicationContext());
        items = handler.getAllItems();
        itemsAdapter = new ItemAdapter(this, items);
        lvItems = (ListView)findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();
    }

    private void launchEditActivity(int pos) {
        Item item = items.get(pos);
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("item", item);
        startActivityForResult(i, REQUEST_CODE);
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View view, int pos, long id) {
                        Item item = items.get(pos);
                        handler.deleteItem(item);
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                            View view, int pos, long id) {
                        launchEditActivity(pos);
                    }
                }
        );
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        Item item = new Item();
        item.setItemText(itemText);
        long rowid = handler.addItem(item);
        item.setRowId(rowid);
        items.add(item);
        itemsAdapter.notifyDataSetChanged();
        etNewItem.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Item item = (Item)data.getSerializableExtra("saveItem");
            handler.updateItem(item);
            items.clear();
            items.addAll(handler.getAllItems());
            itemsAdapter.notifyDataSetChanged();
        }
    }
}
