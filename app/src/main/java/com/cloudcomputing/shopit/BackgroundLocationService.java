package com.cloudcomputing.shopit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.cloudcomputing.apicallmodels.CategoryDealHolder;
import com.cloudcomputing.apicallmodels.DealHolder;
import com.cloudcomputing.apicallmodels.Test;
import com.cloudcomputing.models.NetworkListener;
import com.cloudcomputing.networkcalls.Network;

import java.util.List;

public class BackgroundLocationService extends Service implements NetworkListener {
    private LocationManager locationManager;
    private MyLocation mMyLocation;
    String mCategory;
    String mFormattedQuery, mQuery;

    public BackgroundLocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        mCategory = Constants.mNotificationCategory;
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public void onStart(Intent intent, int StartId) {
        mCategory = Constants.mNotificationCategory;
        locationManager = (LocationManager) BackgroundLocationService.this.getSystemService(Context.LOCATION_SERVICE);
        mMyLocation = new MyLocation();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 100, mMyLocation);
        Toast.makeText(BackgroundLocationService.this, "Retreiveing Location", Toast.LENGTH_SHORT).show();
        //permittednotification(no);


    }

    @Override
    public <T> void onLoadResults(T result) {


        //Test mQueryData = (Test) result;
        Test mTemp = (Test) result;
        List<DealHolder> mQueryData = mTemp.getHits().getHitsList();
        if (mQueryData != null) {
            if (mQueryData.size() > 0) {
                Constants.mNotificationDeals = mQueryData;//.getDeals();

                Intent intent = new Intent(BackgroundLocationService.this, NotificationDealActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(BackgroundLocationService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification.Builder notificationBuilder = new Notification.Builder(this);
                notificationBuilder.setAutoCancel(true);
                notificationBuilder.setDefaults(Notification.DEFAULT_ALL);
                notificationBuilder.setContentTitle("Shop it");
                notificationBuilder.setContentText("New Deals for you!!");
                notificationBuilder.setSmallIcon(R.drawable.app_icon);
                notificationBuilder.setTicker("Ticker");
                notificationBuilder.setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, notificationBuilder.build());
            }
        } else {
            Toast.makeText(BackgroundLocationService.this, "No Deals", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public <T> void onRefreshResults(T result) {

    }

    @Override
    public <T> void onPagingResults(T result) {

    }


    class MyLocation implements LocationListener {


        @Override
        public void onLocationChanged(Location location) {

            if (location != null) {
                mQuery = Constants.getDealsQuery(Constants.GETDEAL_CATEGORY_QUERY);
                mFormattedQuery = String.format(mQuery, !mCategory.equals("") ? mCategory.toLowerCase() : "", location.getLatitude(), location.getLongitude(), 1);

                //mFormattedQuery = String.format(mQuery, "", "", "", "", !mCategory.equals("") ? mCategory.toLowerCase() : "");
                // mFormattedQuery = String.format(mQuery, location.getLatitude(), location.getLongitude(), 10 + "", mCategory, "", "");


                //  mFormattedQuery = String.format(mQuery, location.getLatitude(), location.getLongitude(),10+"", "", "", "");

                Network.getInstance().getDealsNotification(mFormattedQuery, BackgroundLocationService.this);
                Log.e("Location", "Location changed" + mCategory);
            } else
                Log.e("Location", "Location not changed");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
