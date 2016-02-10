package com.cloudcomputing.shopit;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;

import com.cloudcomputing.fragments.Category;
import com.cloudcomputing.fragments.CategoryDeals;
import com.cloudcomputing.fragments.DealsFragment;
import com.cloudcomputing.fragments.EventFragment;
import com.cloudcomputing.fragments.MapDealFragment;
import com.cloudcomputing.fragments.MapFragment;
import com.cloudcomputing.fragments.MerchantDeals;
import com.cloudcomputing.fragments.PostDeal;
import com.cloudcomputing.fragments.WebFragment;
import com.cloudcomputing.models.onDrawerItemSelected;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class MainScreen extends AppCompatActivity implements onDrawerItemSelected, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    DrawerLayout drawer;
    Toolbar toolbar;
    MainScreen mainScreen;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }


    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constants.init();
        mainScreen = this;
        if (Build.VERSION.SDK_INT >= 21) {
            Explode slide = new Explode();
            slide.setDuration(1000);
            getWindow().setEnterTransition(slide);
        }

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        setContentView(R.layout.activity_main_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationDrawerFragment navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        navigationDrawerFragment.setUp(drawer, toolbar);


        //changeFragment(0, Constants.GETDEAL_QUERY, "");

    }


    public void changeFragment(int position, int queryType, String categoryName) {


        Fragment frag = null;

        if (Constants.isConsumer) {

            switch (position) {
                case 0:
                    frag = new DealsFragment();
                    break;
                case 1:
                    frag = new MapFragment();
                    break;
                case 2:
                    frag = new Category();
                    break;
                case 3:
                    frag = new MerchantDeals();
                    break;
                case 4:
                    frag = new CategoryDeals();
                    Bundle bundle = new Bundle();
                    //bundle.putInt(Constants.QUERY_TYPE, Constants.GETDEAL_CATEGORY_QUERY);
                    bundle.putString(Constants.QUERY_PARAMETER_CATEGORY, categoryName);
                    frag.setArguments(bundle);
                    break;

                case 5:
                    frag = new MapDealFragment();
                    Constants.keyword = categoryName;
 /*                   bundle = new Bundle();
                    bundle.putString("keyword", categoryName);
                    frag.setArguments(bundle);*/
                    break;

                case 6:
                    frag = new WebFragment();
                    bundle = new Bundle();
                    bundle.putString("url", categoryName);
                    frag.setArguments(bundle);
                    break;

                case 7:
                    frag = new EventFragment();
                    break;

            }
        } else {
            switch (position) {
                case 0:
                    // frag = new MerchantOrders();
                    frag = new PostDeal();
                    break;
                case 1:
                    frag = new PostDeal();
                    break;

            }
        }
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        trans.replace(R.id.container, frag).commit();
        drawer.closeDrawers();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void fragmentFetcher(int position, int queryType, String categoryName) {

        changeFragment(position, queryType, categoryName);
    }


    @Override
    public void onConnected(Bundle bundle) {

        if (mGoogleApiClient.isConnected()) {
            new LocationFinder(MainScreen.this).execute();

        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    class LocationFinder extends AsyncTask<Void, Void, Location> {

        ProgressDialog mProgressDialog;

        public LocationFinder(Context context) {

            mProgressDialog = ProgressDialog.show(context, "Please Wait!", " Getting your location");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Location doInBackground(Void... params) {

            return LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        @Override
        protected void onPostExecute(Location location) {
            super.onPostExecute(location);

            Constants.mLocation = location;
            mProgressDialog.cancel();
            changeFragment(0, Constants.GETDEAL_QUERY, "");
        }
    }
}
