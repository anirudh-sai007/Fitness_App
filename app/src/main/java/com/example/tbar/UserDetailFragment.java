package com.example.tbar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment UserDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserDetailFragment newInstance(String param1) {
        UserDetailFragment fragment = new UserDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
       // args.putString(ARG_PARAM2, param2);
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
        View view= inflater.inflate(R.layout.fragment_user_detail, container, false);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new CardPagerAdapter(getActivity()));
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        final int pageOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());




// Configure margins and page transformer
        int nextItemVisiblePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        int currentItemHorizontalMarginPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        int pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx;
        ViewPager2.PageTransformer pageTransformer = (page, position) -> {
            page.setTranslationX(-pageTranslationX * position);
            // Optional: scale the page or adjust its alpha
            page.setScaleY(1 - (0.25f * Math.abs(position)));
            page.setAlpha(0.5f + (1 - Math.abs(position)));
        };

        viewPager.setPageTransformer(pageTransformer);

// Set the offscreen page limit
        viewPager.setOffscreenPageLimit(1);

// Additional optional step: Adjust padding to ensure the first and last items have the same effect
        int recyclerViewPadding = nextItemVisiblePx + currentItemHorizontalMarginPx;
        viewPager.setPadding(recyclerViewPadding, 0, recyclerViewPadding, 0);
        viewPager.setClipToPadding(false);



//        viewPager.setPageTransformer(new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                final float MIN_SCALE = 0.75f;
//
//                if (position < -1) { // [-Infinity,-1)
//                    // This page is way off-screen to the left.
//                    page.setAlpha(0);
//                } else if (position <= 1) { // [-1,1]
//                    page.setAlpha(1);
//
//                    // Modify the default slide transition to shrink the page as well
//                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
//                    page.setScaleX(scaleFactor);
//                    page.setScaleY(scaleFactor);
//                } else { // (1,+Infinity]
//                    // This page is way off-screen to the right.
//                    page.setAlpha(0);
//                }
//            }
//        });


        // Optional: If you want to start in the middle of the 'infinite' range
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);
        return  view;
    }
}