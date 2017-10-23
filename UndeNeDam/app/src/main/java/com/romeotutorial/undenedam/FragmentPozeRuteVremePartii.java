package com.romeotutorial.undenedam;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.List;


public class FragmentPozeRuteVremePartii extends Fragment {
    TextView textView;
    File localFile;
    ImageView imagine;
    private String numePartie;

    private FragmentPozeRuteVremePartii fragmentRef = this;
    private View view;

    public FragmentPozeRuteVremePartii() {
        // Required empty public constructor
    }

    public void setNumePartie(String numePartie) {
        this.numePartie = numePartie;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_fragment_poze_rute_vreme_partii, container, false);

        textView = (TextView) view.findViewById(R.id.textView);
        imagine = (ImageView) view.findViewById(R.id.imageView);

        Bundle bundle = getArguments();

        Integer message = bundle.getInt("count");

        // String message = Integer.toString(bundle.getInt("count"));
        //Gaseste aici cum sa iti afiseze textul care vrei tu
        //DACA ESTI PE FRAGMENTUL 1 IMI IA IMAGINILE DE PE SERVER INTR-UN ARRAY SI MI LE AFISEAZA

        if (message.equals(1)) {
            textView.setText("Poze-Video");
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference pozeRef = database.getReference("Partii").child(this.numePartie).child("Poze-Video");

            pozeRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> pozeList = (List<String>) dataSnapshot.getValue();

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    for (String nume : pozeList) {
                        StorageReference pozaRef = storage.getReference().child(nume);

                        try {
                            Log.d("DEBUG", "creating temp file");
                            localFile = File.createTempFile("images", "jpg");
                        } catch (Exception e) {
                            Log.d("nu,nu", "nu funtioneaza");
                        }

                        Log.d("DEBUG", "downloading file from firebase to temp files");
                        pozaRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                // Vezi aici cum pui ca sa fie galerie de poze
                                Glide.with(getContext()).load(localFile).into(imagine);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.d("DEBUG", "failed to download img from firebase storage: " + exception.getMessage());
                            }
                        });

                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w("nu nu nu", "Failed to read value.", error.toException());
                }
            });

        } else if (message.equals(2)) {
            textView.setText("Rute");
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ruteRef = database.getReference("Partii").child(this.numePartie).child("Rute");

            ruteRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> ruteList = (List<String>) dataSnapshot.getValue();

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    for (String nume : ruteList) {
                        StorageReference ruteRef = storage.getReference().child(nume);

                        try {
                            Log.d("DEBUG", "creating temp file");
                            localFile = File.createTempFile("images", "jpg");
                        } catch (Exception e) {
                            Log.d("nu,nu", "nu funtioneaza");
                        }

                        Log.d("DEBUG", "downloading file from firebase to temp files");
                        ruteRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                // Vezi aici cum pui ca sa fie galerie de poze
                                Glide.with(getContext()).load(localFile).into(imagine);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.d("DEBUG", "failed to download img from firebase storage: " + exception.getMessage());
                            }
                        });

                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w("nu nu nu", "Failed to read value.", error.toException());
                }
            });
        } else if (message.equals(3)) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            //OBTIN O REFERENTA IN BAZA DE DATE LA PARTIA CU NUMELE CORESPUNZATOR
            DatabaseReference partieRef = database.getReference("Partii").child(this.numePartie);

            partieRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    double latitudine = (double) dataSnapshot.child("Lat").getValue();
                    double longitudine = (double) dataSnapshot.child("Long").getValue();

                    (new Thread(new WeatherThread(fragmentRef, longitudine, latitudine))).start();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w("nu nu nu", "Failed to read value.", error.toException());
                }
            });
            textView.setText("Vreme");

        }
        return view;
    }

    public void updateWeatherInfo(WeatherInfo weatherInfo) {
        Log.i("???", weatherInfo.getHumidity());
        ((TextView) view.findViewById(R.id.city)).setText(weatherInfo.getCity());
        ((TextView) view.findViewById(R.id.description)).setText(weatherInfo.getDescription());
        ((TextView) view.findViewById(R.id.temperature)).setText(weatherInfo.getTemperature());
        ((TextView) view.findViewById(R.id.umiditate)).setText(weatherInfo.getHumidity());;
        ((TextView) view.findViewById(R.id.presiune)).setText(weatherInfo.getPressure());;
        ((TextView) view.findViewById(R.id.update)).setText(weatherInfo.getUpdatedOn());;
    }
}

        //else if (message.equals(4)) {
           // textView.setText("Poze-Video");

      //  }



