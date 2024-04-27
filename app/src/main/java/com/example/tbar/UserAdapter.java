package com.example.tbar;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private Context mContext;
    private List<User> userList;
    DatabaseHelper databaseHelper;

    public UserAdapter(@NonNull Context context, ArrayList<User> list,DatabaseHelper databaseHelper) {
        super(context, 0, list);
        mContext = context;
        userList = list;
        this.databaseHelper=databaseHelper;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_user, parent, false);
        }

        User user = userList.get(position);

        TextView userName = convertView.findViewById(R.id.userNameTextView);
      TextView dateAdded = convertView.findViewById(R.id.userDateTextView);
      databaseHelper=new DatabaseHelper(this.getContext());

        userName.setText(user.getName());
        dateAdded.setText(user.getDateAdded());
        int backgroundColor = position % 2 == 0 ? R.color.colorEven : R.color.colorOdd;
        convertView.setBackgroundColor(mContext.getResources().getColor(backgroundColor, null));

        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextInputLayout inputLayout = new TextInputLayout(mContext);
                inputLayout.setPadding(
                        (int) (16 * mContext.getResources().getDisplayMetrics().density), // start
                        0,
                        (int) (16 * mContext.getResources().getDisplayMetrics().density), // end
                        0
                );

                final TextInputEditText inputEditText = new TextInputEditText(mContext);
                inputLayout.addView(inputEditText);
                inputEditText.setText(user.getName());

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(mContext, R.style.CustomAlertDialogStyle)
                        .setTitle("Edit")
                        .setMessage("Enter new name:")
                        .setView(inputLayout)
                        .setPositiveButton("OK", null) // Set to null initially
                        .setNegativeButton("Cancel", null); // Set to null initially

                androidx.appcompat.app.AlertDialog dialog = builder.create();

// Override the button handlers
                dialog.setOnShowListener(dialogInterface -> {
                    Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                    positiveButton.setTextColor(mContext.getResources().getColor(R.color.positiveButtonColor)); // Green
                    negativeButton.setTextColor(mContext.getResources().getColor(R.color.negativeButtonColor)); // Red

                    positiveButton.setOnClickListener(view -> {
                        // Handle "OK" click
                        String updatedName = inputEditText.getText().toString();
                        if (!updatedName.isEmpty() && databaseHelper.updateData(user.getName(), updatedName, user.getDateAdded())) {
                            Toast.makeText(mContext, "User updated successfully!", Toast.LENGTH_SHORT).show();
                            user.setName(updatedName); // Update the user's name in the current list
                            notifyDataSetChanged(); // Notify the adapter to refresh the list
                            dialog.dismiss();
                        } else {
                            Toast.makeText(mContext, "Error updating user!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    negativeButton.setOnClickListener(view -> {
                        // Handle "Cancel" click
                        dialog.dismiss();
                    });
                });

                dialog.show();
            }
        });
        return convertView;
    }
}
