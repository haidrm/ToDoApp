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
        String itemText = getIntent().getStringExtra("item");
        editItem.setText(itemText);
        editItem.setSelection(editItem.getText().length());
        editItem.requestFocus();
    }

    public void onSave(View v) {
        EditText editItem = (EditText) findViewById(R.id.etEditItem);
        int pos = getIntent().getIntExtra("pos", 0);
        Intent data = new Intent();
        data.putExtra("item", editItem.getText().toString());
        data.putExtra("pos", pos);
        setResult(RESULT_OK, data);
        finish();
    }
}
