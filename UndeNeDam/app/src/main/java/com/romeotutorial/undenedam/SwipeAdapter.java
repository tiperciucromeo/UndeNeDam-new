package com.romeotutorial.undenedam;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by romeotiperciuc on 19/07/2017.
 */

public class SwipeAdapter extends FragmentStatePagerAdapter {

    private String numePartie;

    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new FragmentPozeRuteVremePartii();
        ((FragmentPozeRuteVremePartii) fragment).setNumePartie(numePartie);

        Bundle bundle = new Bundle();
        bundle.putInt("count",position + 1);
        fragment.setArguments(bundle);

        return fragment;
    }

    public void setNumePartie(String numePartie) {
        this.numePartie = numePartie;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
