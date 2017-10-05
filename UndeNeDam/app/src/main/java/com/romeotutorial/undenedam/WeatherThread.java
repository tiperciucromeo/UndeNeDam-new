package com.romeotutorial.undenedam;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by romeotiperciuc on 03/10/2017.
 */

public class WeatherThread implements Runnable {
    private double longitudine;
    private double latitudine;

    private static final String OPEN_WEATHER_MAP_URL =
            "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric";
    private static final String OPEN_WEATHER_MAP_API = "86959c8c1c6abc2d763601006da98745";

    public WeatherThread(double longitudine, double latitudine) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    @Override
    public void run() {
        try {
            Log.i("WEATHER THREAD", "Getting Weather Info");
            URL url = new URL(String.format(OPEN_WEATHER_MAP_URL, latitudine, longitudine));
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null) {
                json.append(tmp).append("\n");
            }
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful
            if (data.getInt("cod") != 200) {
                Log.e("Invalid return code", "Return code for weather API call: " + data.getInt("cod"));
            }

            JSONObject details = data.getJSONArray("weather").getJSONObject(0);
            JSONObject main = data.getJSONObject("main");
            DateFormat df = DateFormat.getDateTimeInstance();


            String city = data.getString("name").toUpperCase(Locale.US) + ", " + data.getJSONObject("sys").getString("country");
            String description = details.getString("description").toUpperCase(Locale.US);
            String temperature = String.format("%.2f", main.getDouble("temp")) + "°";
            String humidity = main.getString("humidity") + "%";
            String pressure = main.getString("pressure") + " hPa";
            String updatedOn = df.format(new Date(data.getLong("dt") * 1000));

            Log.
            Log.i("WEATHER DATA", city);
            Log.i("WEATHER DATA", description);
            Log.i("WEATHER DATA", temperature);
            Log.i("WEATHER DATA", humidity);
            Log.i("WEATHER DATA", pressure);
            Log.i("WEATHER DATA", updatedOn);
//                                String iconText = setWeatherIcon(details.getInt("id"),
//                                        json.getJSONObject("sys").getLong("sunrise") * 1000,
//                                        json.getJSONObject("sys").getLong("sunset") * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
