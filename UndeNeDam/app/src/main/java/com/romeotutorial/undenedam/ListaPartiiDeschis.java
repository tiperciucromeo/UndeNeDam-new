package com.romeotutorial.undenedam;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class ListaPartiiDeschis extends FragmentActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_partii_deschis);

        viewPager = (ViewPager)findViewById(R.id.view_pager);

        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        String numePartie = getIntent().getStringExtra("NUME_PARTIE");
        swipeAdapter.setNumePartie(numePartie);
        viewPager.setAdapter(swipeAdapter);


    }
}
