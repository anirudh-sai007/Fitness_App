package com.example.tbar;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddUserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText nameEditText;
    EditText usernameEditText;
    TextView dateTextView;
    Button saveButton,rombutton;
    DatabaseHelper dbHelper;

    ListView userListView;

    UserAdapter userAdapter;
    ArrayList<User> userList = new ArrayList<>();
    public AddUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddUserFragment newInstance(String param1, String param2) {
        AddUserFragment fragment = new AddUserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_user, container, false);
//        rombutton=view.findViewById(R.id.rombutton);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        rombutton=view.findViewById(R.id.romstartButton);
        dateTextView = view.findViewById(R.id.dateTextView);
        saveButton = view.findViewById(R.id.savenameButton);


        dbHelper = new DatabaseHelper(getActivity());


        dbHelper = new DatabaseHelper(getActivity());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(new Date());
        dateTextView.setText(currentDate);

        saveButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            if (dbHelper.insertData(username, currentDate)) {
                Toast.makeText(getActivity(), "User added successfully!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getActivity(), "Error adding user!", Toast.LENGTH_SHORT).show();
            }
        });
        rombutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RomLiveFragment romLiveFragment= RomLiveFragment.newInstance();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,romLiveFragment)
                        .commit();



            }
        });



        return  view;
    }




}