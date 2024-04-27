package com.example.tbar;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Add_user extends AppCompatActivity {
    EditText usernameEditText;
    TextView dateTextView;
    Button saveButton,rombutton;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        usernameEditText = findViewById(R.id.usernameEditText);
        rombutton=findViewById(R.id.rombutton);
        dateTextView = findViewById(R.id.dateTextView);
        saveButton = findViewById(R.id.saveButton);


        dbHelper = new DatabaseHelper(this);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(new Date());
        dateTextView.setText(currentDate);

        saveButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            if (dbHelper.insertData(username, currentDate)) {
                Toast.makeText(this, "User added successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error adding user!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
