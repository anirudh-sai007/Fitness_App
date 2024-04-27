package com.example.tbar;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    DatabaseHelper dbHelper;

    ListView userListView;

    UserAdapter userAdapter;
    ArrayList<User> userList = new ArrayList<>();

    Button newuser,loaduser;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public UserFragment() {
        // Required empty public constructor
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
        View view= inflater.inflate(R.layout.fragment_user, container, false);

        newuser=view.findViewById(R.id.addUserButton);
        loaduser=view.findViewById(R.id.loadUsersButton);
        userListView =(ListView) view.findViewById(R.id.usersRecyclerView);
        dbHelper = new DatabaseHelper(getActivity());

        userAdapter = new UserAdapter(getActivity(), userList,dbHelper);
        userListView.setAdapter(userAdapter);
        loadUsersFromDB();

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction= getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new AddUserFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return  view;
    }
    @Override
    public void onResume() {
        super.onResume();
        // Load users from the database and update the RecyclerView adapter
        loadUsersFromDB();
    }

    private void loadUsersFromDB() {
        Cursor data = dbHelper.getData();
        userList.clear();
        while (data.moveToNext()) {
            String name = data.getString(1); // Assuming name is at index 1
            String dateAdded = data.getString(2); // Assuming dateAdded is at index 2
            userList.add(new User(name, dateAdded));
        }
        userAdapter.notifyDataSetChanged();
    }
}