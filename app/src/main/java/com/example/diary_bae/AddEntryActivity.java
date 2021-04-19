package com.example.diary_bae;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diary_bae.database.DbAccessObject;

public class AddEntryActivity extends AppCompatActivity {
    DbAccessObject dao;
    EditText etEntry;
    EditText etDate;
    EditText etTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        etEntry = findViewById(R.id.etEntry);
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        dao = new DbAccessObject(this);
        dao.openDb();
    }

    public void entryHandler(View view) {
            switch (view.getId()){
                case R.id.btnSave:
                    if (!etEntry.getText().toString().equals("") && !etDate.getText().toString().equals("") && !etTime.getText().toString().equals("")) {
                        String title = etEntry.getText().toString();
                        String date = etDate.getText().toString();
                        String time = etTime.getText().toString();
                        //put the data into db
                        dao.createRow(title, date, time);
                        finish();
                    }
                    else {
                        Toast.makeText(AddEntryActivity.this,"All fields are mandatory",Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.btnDiscard:
                    finish();
                    break;

            }
    }

}