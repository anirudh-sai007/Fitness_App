package com.example.tbar;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class CardPagerAdapter extends FragmentStateAdapter {
    private static final int CARD_ITEM_COUNT = 3;

    public CardPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Modulo for circular effect
        int index = position % CARD_ITEM_COUNT;
        int colorRes = getColorResByIndex(index);
        switch (index) {
            case 0:

                return card_one.newInstance(colorRes);
            case 1:
                return card_two.newInstance(colorRes);
            case 2:
                return card_three.newInstance(colorRes);
            default:

                return CardFragment.newInstance("Default Card", colorRes);
        }
//        return CardFragment.newInstance("Card " + (index + 1), colorRes);
    }

    @Override
    public int getItemCount() {
        // Large number for 'infinite' swipe
        return 3;
    }

    private int getColorResByIndex(int index) {
        // Example method to return a color resource ID based on the card index
        switch (index) {
            case 0:
                return R.color.colorCard1;
            case 1:
                return R.color.colorCard2;
            case 2:
                return R.color.colorCard3;
            default:
                return R.color.colorCard1; // Default color if something goes wrong
        }
    }
}
