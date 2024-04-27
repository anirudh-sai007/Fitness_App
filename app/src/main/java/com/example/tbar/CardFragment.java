package com.example.tbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
public class CardFragment extends Fragment {
    private static final String ARG_TEXT = "argText";

    public CardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.activity_card_fragment, container, false);

        return view;
    }
    public static CardFragment newInstance(String text,int colorRes) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text); // Put the text into the bundle
        args.putInt("ARG_COLOR_RES", colorRes);
        fragment.setArguments(args); // Attach the bundle to the fragment
        return fragment; // Return the new fragment instance
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.cardText);
        CardView cardView = view.findViewById(R.id.cardView);
        if (getArguments() != null) {
            String text = getArguments().getString(ARG_TEXT);
            int colorRes = getArguments().getInt("ARG_COLOR_RES");
            textView.setText(text);
            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), colorRes));
        }
    }


}