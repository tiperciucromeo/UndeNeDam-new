package com.romeotutorial.undenedam;

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

public class Harti extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mGoogleMap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.harti);
        initMap();

    }
    private void initMap(){
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        goToLocationZoom(45.943161,24.966760000000022,7);
        Marker marker0  = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(47.541355, 25.888353)).title("Gura Humorului"));
        Marker marker1 = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(45.443246, 25.590048)).title("Azuga"));
        Marker marker2 = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(46.513504, 22.676317)).title("Ariseni"));
        Marker marker3 = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(46.383512, 25.640481)).title("Baile Harghita"));
        Marker marker4 = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(46.350421, 25.473513)).title("Baile Homorod"));
        Marker marker5 = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(45.671802, 23.942041)).title("Arena Platos"));
    }
private void goToLocation(double lat,double lng){
    LatLng ll = new LatLng(lat,lng);
    CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
    mGoogleMap.moveCamera(update);
}
    private void goToLocationZoom(double lat,double lng,float zoom){
        LatLng ll = new LatLng(lat,lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,zoom);
        mGoogleMap.moveCamera(update);
    }

}


