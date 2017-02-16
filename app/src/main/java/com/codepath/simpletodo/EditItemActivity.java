package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        EditText editItem = (EditText) findViewById(R.id.etEditItem);
        Item item = (Item) getIntent().getSerializableExtra("item");
        editItem.setText(item.getItemText());
        editItem.setSelection(editItem.getText().length());
        editItem.requestFocus();
    }

    public void onSave(View v) {
        EditText editItem = (EditText) findViewById(R.id.etEditItem);
        Item item = (Item) getIntent().getSerializableExtra("item");
        String itemText = editItem.getText().toString();
        Intent data = new Intent();
        item.setItemText(itemText);
        data.putExtra("saveItem", item);
        setResult(RESULT_OK, data);
        finish();
    }
}
