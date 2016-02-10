package com.cloudcomputing.shopit;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.cloudcomputing.adapters.NavigationDrawerAdapter;
import com.cloudcomputing.adapters.SpinnerAdapter;
import com.cloudcomputing.animation.Animation;
import com.cloudcomputing.models.GenericModel;
import com.cloudcomputing.models.onDrawerItemSelected;
import com.pkmmte.view.CircularImageView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {

    ActionBarDrawerToggle mActionBarDrawerToggle;
    DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer;
    private static final String PREF_FILE_NAME = "drawer";
    private static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawers";
    RecyclerView mNavigationDrawerRecyclerView;
    Spinner mAccountSwitchSpinner;
    TextView mUserName;
    CircularImageView mUserImage;
    onDrawerItemSelected mDrawerItemSelection;
    MainActivity mainActivity;

    public NavigationDrawerFragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserImage = (CircularImageView) view.findViewById(R.id.user_image);
        ImageLoader mImageLoader = ApplicationInstance.getInstance().getImageLoader();
        mImageLoader.get("https://api.sqoot.com/v2/deals/4294143/image", ImageLoader.getImageListener(mUserImage, R.drawable.app_icon, R.drawable.background));

        mUserName = (TextView) view.findViewById(R.id.userName);
        mUserName.setText(Constants.USERNAME);
        mNavigationDrawerRecyclerView = (RecyclerView) view.findViewById(R.id.navigation_drawer_recyclerview);
        mAccountSwitchSpinner = (Spinner) view.findViewById(R.id.user_merchant_spinner);
        SpinnerAdapter mSpinnerAdapter = new SpinnerAdapter(getActivity(), R.layout.navigation_drawer_list_item, getSpinnerData());
        mAccountSwitchSpinner.setAdapter(mSpinnerAdapter);
        mAccountSwitchSpinner.setSelection(Constants.mPostion);
        NavigationDrawerAdapter recyclerViewAdapter = new NavigationDrawerAdapter(getActivity(), getListItems(), this);
        mNavigationDrawerRecyclerView.setAdapter(recyclerViewAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mNavigationDrawerRecyclerView.setLayoutManager(linearLayoutManager);

        mAccountSwitchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (Constants.mInitialization) {
                    Constants.mInitialization = false;
                } else {
                    switch (position) {
                        case 0:
                            Constants.isConsumer = true;
                            Constants.mPostion = 0;
                            break;
                        case 1:
                            Constants.isConsumer = false;
                            Constants.mPostion = 1;
                            break;

                    }

                    mDrawerLayout.closeDrawers();
                    Constants.mInitialization = true;
                    startActivity(new Intent(getActivity(), MainScreen.class));
                    getActivity().finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private ArrayList<GenericModel> getListItems() {

        ArrayList<GenericModel> spinnerList = new ArrayList<GenericModel>();
        if (Constants.isConsumer) {
            GenericModel temp = new GenericModel("Deals", R.drawable.nav_deals);
            spinnerList.add(temp);

            temp = new GenericModel("Search", R.drawable.nav_search);
            spinnerList.add(temp);

            temp = new GenericModel("Category", R.drawable.nav_setting);
            spinnerList.add(temp);

            temp = new GenericModel("Merchant Deals", R.drawable.nav_logout);
            spinnerList.add(temp);

            temp = new GenericModel("Set Event", R.drawable.nav_setting);
            spinnerList.add(temp);

        } else {
           /* GenericModel temp = new GenericModel("Orders", R.drawable.nav_deals);
            spinnerList.add(temp);*/

            GenericModel temp = new GenericModel("Post", R.drawable.nav_search);
            spinnerList.add(temp);

            /*temp = new GenericModel("Merchant Settings", R.drawable.nav_setting);
            spinnerList.add(temp);

            temp = new GenericModel("Merchant Logout", R.drawable.nav_logout);
            spinnerList.add(temp);*/
        }

        return spinnerList;

    }

    private ArrayList<GenericModel> getSpinnerData() {
        ArrayList<GenericModel> spinnerList = new ArrayList<GenericModel>();
        GenericModel temp = new GenericModel("Consumer", R.mipmap.ic_launcher);
        spinnerList.add(temp);
        temp = new GenericModel("Merchant", R.mipmap.ic_launcher);
        spinnerList.add(temp);
        return spinnerList;
    }

    public void setUp(DrawerLayout drawer, Toolbar toolbar) {
        mDrawerLayout = drawer;

        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                getActivity().invalidateOptionsMenu();

                Animation.CircularImageAnimation(mUserImage, false);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

                Animation.CircularImageAnimation(mUserImage, true);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, Boolean.toString(mUserLearnedDrawer));
                }

                getActivity().invalidateOptionsMenu();
            }
        };
        drawer.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
    }

    private static void saveToPreferences(Context context, String preferenceName, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, value);
        editor.apply();
    }

    private static String readFromPreferences(Context context, String preferenceName, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, value);

    }

    public void CallFragmentChanger(int position) {

        mDrawerItemSelection.fragmentFetcher(position, 0, "");
    }
}
