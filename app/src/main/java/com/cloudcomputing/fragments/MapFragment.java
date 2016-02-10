package com.cloudcomputing.fragments;


import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cloudcomputing.models.onDrawerItemSelected;
import com.cloudcomputing.shopit.Constants;
import com.cloudcomputing.shopit.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    SupportMapFragment mMapFragment;
    GoogleMap mMap;
    SeekBar mSeekbar;
    TextView mRadiusTextView;
    EditText mKeywordEditText;
    Button mKeywordSearchButton;
    private final int MIN_PROGRESS = 10;
    private float mZoomLevel = 8;
    private LatLng mLatLng;
    private CameraUpdate mCamera;
    private MarkerOptions mMarkerOptions;
    private CircleOptions mCircleOptions;
    private Circle mMapCircle;
    private Marker mMarker;
    private boolean flag;
    private double mRadius = 1600d;
    private final double ONE_MILE = 1600d;
    onDrawerItemSelected mDrawerItemSelection;
    int mProgress = 10;
    //private final int FILL_COLOR= android.R.color.darker_gray;
    private final int FILL_COLOR = R.color.colorPrimaryDark;

    public MapFragment() {
        // Required empty public const1ructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDrawerItemSelection = (onDrawerItemSelected) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mMapFragment.getMapAsync(this);
        mRadiusTextView = (TextView) view.findViewById(R.id.radiusTextView);
        mKeywordEditText = (EditText) view.findViewById(R.id.searchEditText);
        mKeywordSearchButton = (Button) view.findViewById(R.id.searchButton);
        mSeekbar = (SeekBar) view.findViewById(R.id.seekbar);

        mKeywordSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerItemSelection.fragmentFetcher(5,0,mKeywordEditText.getText().toString());
                Constants.mLocation.setLatitude(mLatLng.latitude);
                Constants.mLocation.setLongitude(mLatLng.longitude);
                Constants.radius = mProgress / 10;

                Log.e("Keyword is ", mKeywordEditText.getText().toString());
            }
        });


        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mProgress = progress;
                if (mMap != null)
                    mMap.clear();

                if (progress <= MIN_PROGRESS) {
                    seekBar.setProgress(MIN_PROGRESS);
                    mRadius = seekBar.getProgress() * ONE_MILE / 10;
                    mRadiusTextView.setText("1 \nMile");
                } else {
                    mRadius = progress * ONE_MILE / 10;
                    mRadiusTextView.setText(progress / 10f + "\nMiles");
                }


                if ((progress / 10) < 2) {
                    mZoomLevel = 8;
                } else if ((progress / 10) >= 2 && (progress / 10) < 5) {
                    mZoomLevel = 10;
                } else if ((progress / 10) > 5) {
                    mZoomLevel = 11;
                }


                mMarkerOptions = new MarkerOptions().position(mLatLng).draggable(true).title("You are here!");
                mMarker = mMap.addMarker(mMarkerOptions);

                mCircleOptions = new CircleOptions().radius(mRadius).center(mLatLng).strokeWidth(0).fillColor(FILL_COLOR);
                mMapCircle = mMap.addCircle(mCircleOptions);

                mCamera = CameraUpdateFactory.newLatLngZoom(mLatLng, 21 - mZoomLevel);
                mMap.moveCamera(mCamera);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (!flag) {
                    flag = true;
                    mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMarkerOptions = new MarkerOptions().position(mLatLng).draggable(true).title("You are here!");
                    mMarker = mMap.addMarker(mMarkerOptions);

                    mCircleOptions = new CircleOptions().radius(1600).center(mLatLng).strokeWidth(0).fillColor(FILL_COLOR);
                    mMapCircle = mMap.addCircle(mCircleOptions);

                    mCamera = CameraUpdateFactory.newLatLngZoom(mLatLng, mZoomLevel);
                    mMap.moveCamera(mCamera);

                }

            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                DrawCircle(latLng);
                Log.d("map", "MAP LISTENER");
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                if (mMapCircle != null)
                    mMapCircle.remove();
            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

                DrawCircle(marker.getPosition());
            }
        });

    }

    private void DrawCircle(LatLng latLng) {
        if (mMap != null)
            mMap.clear();
        mLatLng = latLng;
        mMarkerOptions = new MarkerOptions().position(mLatLng).draggable(true).title("You are here!");
        mMarker = mMap.addMarker(mMarkerOptions);

        mCircleOptions = new CircleOptions().radius(mRadius).center(mLatLng).strokeWidth(0).fillColor(FILL_COLOR);
        mMapCircle = mMap.addCircle(mCircleOptions);
    }


}
