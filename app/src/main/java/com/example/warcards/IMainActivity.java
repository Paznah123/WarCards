package com.example.warcards;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.example.warcards.objects.Winner;
import java.util.LinkedList;

public interface IMainActivity {

    void hideSystemUI();

    void playSound(int rawSound);

    void inflateFragment(String fragmentTag, boolean addToBackStack, Bundle bundle);

    void putFragmentInView(int layout_id, Fragment fragment);

    void addTo_winnersList(Winner winner, LinkedList<Winner> winners_list);

}
