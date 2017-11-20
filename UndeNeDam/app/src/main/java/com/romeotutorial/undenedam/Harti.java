package com.romeotutorial.undenedam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by romeotiperciuc on 09/07/2017.
 */

public class Harti extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnInfoWindowClickListener {

    GoogleMap mGoogleMap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.harti);
        initMap();

    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        goToLocationZoom(45.943161, 24.966760000000022, 7);
        Marker marker0 = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(47.541355, 25.888353)).title("Gura Humorului"));
        Marker marker1 = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(45.443246, 25.590048)).title("Azuga"));
        Marker marker2 = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(45.671802, 23.942041)).title("Arena Platos"));
        mGoogleMap.setOnInfoWindowClickListener(this);
    }

    private void goToLocation(double lat, double lng) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        mGoogleMap.moveCamera(update);
    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String numePartie = marker.getTitle();

        Intent intent = new Intent(this, ListaPartiiDeschis.class);
        intent.putExtra("NUME_PARTIE", numePartie);
        startActivity(intent);
    }
}

