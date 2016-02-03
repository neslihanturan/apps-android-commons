package fr.free.nrw.commons.upload;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.util.Log;

import java.io.IOException;


public class GPSExtractor {

    private String filePath;
    private double decLatitude, decLongitude;
    Context context;
    private static final String TAG = GPSExtractor.class.getName();

    public GPSExtractor(String filePath, Context context){
        this.filePath = filePath;
        this.context = context;
    }

    //Extract GPS coords of image
    public String getCoords() {

        ExifInterface exif;
        String latitude = "";
        String longitude = "";
        String latitude_ref = "";
        String longitude_ref = "";
        String decimalCoords = "";

        try {
            exif = new ExifInterface(filePath);
        } catch (IOException e) {
            Log.w("Image", e);
            return null;
        }

        if (exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE) == null) {
            Log.d(TAG, "Picture has no GPS info");

            LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            Location getLastLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            double currentLongitude = getLastLocation.getLongitude();
            double currentLatitude = getLastLocation.getLatitude();
            Log.d(TAG, "Current location values: Lat = " + currentLatitude + " Long = " + currentLongitude);

            String currentCoords = String.valueOf(currentLatitude) + "|" + String.valueOf(currentLongitude);
            return currentCoords;
        }
        else {
            latitude = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            latitude_ref = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            longitude = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            longitude_ref = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);


            Log.d("Image", "Latitude: " + latitude + " " + latitude_ref);
            Log.d("Image", "Longitude: " + longitude + " " + longitude_ref);
            decimalCoords = getDecimalCoords(latitude, latitude_ref, longitude, longitude_ref);
            return decimalCoords;

        }
    }

    public double getDecLatitude() {
        return decLatitude;
    }

    public double getDecLongitude() {
        return decLongitude;
    }

    //Converts format of coords into decimal coords as required by MediaWiki API
    private String getDecimalCoords(String latitude, String latitude_ref, String longitude, String longitude_ref) {



        if(latitude_ref.equals("N")){
            decLatitude = convertToDegree(latitude);
        }
        else{
            decLatitude = 0 - convertToDegree(latitude);
        }

        if(longitude_ref.equals("E")){
            decLongitude = convertToDegree(longitude);
        }
        else{
            decLongitude = 0 - convertToDegree(longitude);
        }

        String decimalCoords = String.valueOf(decLatitude) + "|" + String.valueOf(decLongitude);
        Log.d("Coords", "Latitude and Longitude are " + decimalCoords);
        return decimalCoords;
    }


    private double convertToDegree(String stringDMS){
        double result;
        String[] DMS = stringDMS.split(",", 3);

        String[] stringD = DMS[0].split("/", 2);
        double d0 = Double.parseDouble(stringD[0]);
        double d1 = Double.parseDouble(stringD[1]);
        double degrees = d0/d1;

        String[] stringM = DMS[1].split("/", 2);
        double m0 = Double.parseDouble(stringM[0]);
        double m1 = Double.parseDouble(stringM[1]);
        double minutes = m0/m1;

        String[] stringS = DMS[2].split("/", 2);
        double s0 = Double.parseDouble(stringS[0]);
        double s1 = Double.parseDouble(stringS[1]);
        double seconds = s0/s1;

        result = degrees + (minutes/60) + (seconds/3600);
        return result;
    }

}
