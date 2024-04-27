package com.example.tbar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link card_one#newInstance} factory method to
 * create an instance of this fragment.
 */
public class card_one extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public card_one() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment card_one.
     */
    // TODO: Rename and change types and number of parameters
    public static card_one newInstance(int colorRes) {
        card_one fragment = new card_one();
        Bundle args = new Bundle();
        args.putInt("COLOR_RES", colorRes);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance() {
        return new card_one();
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
        View view= inflater.inflate(R.layout.fragment_card_one, container, false);
        TextView hintTextView1 = view.findViewById(R.id.hintTextView1);
        TextView valueTextView1 = view.findViewById(R.id.valueTextView1);
        TextView hintTextView2 = view.findViewById(R.id.hintTextView1);
        TextView valueTextView2 = view.findViewById(R.id.valueTextView1);
        TextView hintTextView3 = view.findViewById(R.id.hintTextView1);
        TextView valueTextView3 = view.findViewById(R.id.valueTextView1);
        TextView hintTextView4 = view.findViewById(R.id.hintTextView1);
        TextView valueTextView4 = view.findViewById(R.id.valueTextView1);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            int colorRes = getArguments().getInt("COLOR_RES");
            View rootView = view.findViewById(R.id.cardView); // Assuming you have a root view with this ID in your fragment's layout.
            rootView.setBackgroundColor(ContextCompat.getColor(getContext(), colorRes));
        }
    }

}