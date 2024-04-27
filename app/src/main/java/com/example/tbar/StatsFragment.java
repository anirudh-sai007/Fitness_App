package com.example.tbar;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsFragment extends Fragment {

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

    public StatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatsFragment newInstance(String param1, String param2) {
        StatsFragment fragment = new StatsFragment();
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
        View view= inflater.inflate(R.layout.fragment_stats, container, false);
        userListView =(ListView) view.findViewById(R.id.usersRecyclerView);
        dbHelper = new DatabaseHelper(getActivity());

        userAdapter = new UserAdapter(getActivity(), userList,dbHelper);
        userListView.setAdapter(userAdapter);
        loadUsersFromDB();
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               User clickedUser = userAdapter.getItem(position);
               if (clickedUser != null) {
                   navigateToUserDetails(clickedUser.getName());
               }
           }
       });
        return view;
    }

    private void navigateToUserDetails(String userName) {
        // Logic to navigate to the UserDetailFragment with userName
        UserDetailFragment detailFragment = UserDetailFragment.newInstance(userName);
        Toast.makeText(getActivity(),userName,Toast.LENGTH_SHORT).show();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, detailFragment); // Ensure you have a container to replace fragments
        transaction.addToBackStack(null); // Add transaction to the back stack for navigation
        transaction.commit();
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