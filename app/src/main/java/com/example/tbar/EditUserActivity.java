package com.example.tbar;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class EditUserActivity extends AppCompatActivity {
    EditText editUsernameEditText;
    Button updateButton;
    DatabaseHelper dbHelper;
    String originalName, originalDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        editUsernameEditText = findViewById(R.id.editUsernameEditText);
        updateButton = findViewById(R.id.updateButton);

        dbHelper = new DatabaseHelper(this);

        originalName = getIntent().getStringExtra("selectedName");
        originalDate = getIntent().getStringExtra("selectedDate");

        editUsernameEditText.setText(originalName);

        updateButton.setOnClickListener(v -> {
            String updatedName = editUsernameEditText.getText().toString().trim();
            if (dbHelper.updateData(originalName, updatedName, originalDate)) {
                Toast.makeText(this, "User updated successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error updating user!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}