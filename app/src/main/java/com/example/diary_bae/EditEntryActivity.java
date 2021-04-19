package com.example.diary_bae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diary_bae.adapters.EntriesAdapter;
import com.example.diary_bae.database.DbAccessObject;
import com.example.diary_bae.pojo.EntryDetails;

import java.util.ArrayList;

public class EditEntryActivity extends AppCompatActivity {
    DbAccessObject dao;
    EditText etEntry;
    EditText etDate;
    EditText etTime;
    ArrayList<EntryDetails> entryDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
        etEntry = findViewById(R.id.etEntry);
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        dao = new DbAccessObject(this);
        dao.openDb();
    }

    public void editEntryHandler(View view) {
        Intent mIntent = getIntent();
        int intValue = mIntent.getIntExtra("position", 0);
        entryDetails = mIntent.getParcelableArrayListExtra("entryDetails");
        switch (view.getId()){
            case R.id.btnEditSave:
                if (etEntry.getText().toString().equals("") && etDate.getText().toString().equals("") && etTime.getText().toString().equals("")) {
                    Toast.makeText(EditEntryActivity.this, "No new details are entered", Toast.LENGTH_LONG).show();
                }
                else {
                    String title = etEntry.getText().toString();
                    String date = etDate.getText().toString();
                    String time = etTime.getText().toString();
                    //put the data into db
                    dao.updateRow(title, date, time, entryDetails.get(intValue).getId());
                    finish();
                }
                break;
            case R.id.btnEditDiscard:
                finish();
                break;

        }
    }

}
