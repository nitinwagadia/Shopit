package com.cloudcomputing.shopit;

import android.location.Location;

import com.cloudcomputing.apicallmodels.CategoryDealHolder;
import com.cloudcomputing.apicallmodels.DealHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nitin on 12/4/2015.
 */
public class Constants {


    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    //public static String GETDEALS = "http://myrestapi-env.elasticbeanstalk.com/mywebservice/getdeals?location=%s,%s&radius=%s&page=%s&category=%s&keyword=%s";
    public static String GETELASTICDEAL = "http://finalrestapi-env.elasticbeanstalk.com/mywebservice/getelasticdeals?location=%s,%s&radius=20&page=%s&keyword=%s";
    //public static String GETDEALSBYCATEGORY = "http://myrestapi-env.elasticbeanstalk.com/mywebservice/getdealsbycategory?location=%s,%s&radius=%s&page=%s&category=%s";
    public static String GETELASTICCATEGORY = "http://finalrestapi-env.elasticbeanstalk.com/mywebservice/getelasticdealsbycategory?category=%s&location=%s,%s&radius=20&page=%s";

    public static String GETSIGNUP = "http://finalrestapi-env.elasticbeanstalk.com/mywebservice/signup?email=%s&password=%s&username=%s&deviceid=%s";
    public static String GETLOGIN = "http://finalrestapi-env.elasticbeanstalk.com/mywebservice/login?email=%s&password=%s";
    public final static String SHARED_PREFERENCES_NAME = "logindetails";
    public final static String LOGIN_USER_ID = "loginid";
    public final static String LOGIN_USER_TOKEN = "loginusertoken";
    public static boolean isConsumer = true;
    public static boolean mInitialization = true;
    public static int mPostion = 0;
    public static String USERNAME;
    public static ArrayList<String> mCategoryList;
    public static String token = "";
    public static Location mLocation;
    public static int radius = 5;
    public static List<DealHolder> mNotificationDeals;
    public static String keyword = "";
    public final static int GETDEAL_QUERY = 0;
    public final static int GETDEAL_CATEGORY_QUERY = 1;

    public final static int FRAGMENT_CATEGORY_DEALS = 4;


    public final static String QUERY_TYPE = "type";
    public final static String QUERY_PARAMETER_CATEGORY = "category";
    public static String mNotificationCategory = "";

    public static String getDealsQuery(int type) {
        String query = null;
        switch (type) {

            case 0:
                //query = GETDEALS;
                query = GETELASTICDEAL;
                break;
            case 1:
               // query = GETDEALSBYCATEGORY;
                query = GETELASTICCATEGORY;
                break;

        }

        return query;
    }


    public static void init() {
        mCategoryList = new ArrayList<String>();
        mCategoryList.add("Bowling");
        mCategoryList.add("City Tours");
        mCategoryList.add("Comedy Clubs ");
        mCategoryList.add("Concerts");
        mCategoryList.add("Dance Classes");
        mCategoryList.add("Golf");
        mCategoryList.add("Life Skills Classes");
        mCategoryList.add("Museums");
        mCategoryList.add("Outdoor Adventures");
        mCategoryList.add("Skiing");
        mCategoryList.add("Skydiving");
        mCategoryList.add("Sporting Events");
        mCategoryList.add("Theater");
        mCategoryList.add("Wine Tasting");
        mCategoryList.add("Bars & Clubs");
        mCategoryList.add("Fitness Classes");
        mCategoryList.add("Gym");
        mCategoryList.add("Martial Arts");
        mCategoryList.add("Personal Training");
        mCategoryList.add("Pilates");
        mCategoryList.add("Yoga");
        mCategoryList.add("Chiropractic");
        mCategoryList.add("Dental");
        mCategoryList.add("Dermatology");
        mCategoryList.add(" Eye & Vision ");
        mCategoryList.add(" Facial ");
        mCategoryList.add("Hair Removal");
        mCategoryList.add("Hair Salon");
        mCategoryList.add("Makeup");
        mCategoryList.add("Manicure & Pedicure");
        mCategoryList.add("Massage");
        mCategoryList.add("Spa");
        mCategoryList.add("Tanning");
        mCategoryList.add("Teeth Whitening");
        mCategoryList.add("Automotive Services");
        mCategoryList.add("Food & Grocery");
        mCategoryList.add("Home Services");
        mCategoryList.add("Men’s Clothing");
        mCategoryList.add("Photography Services");
        mCategoryList.add("Treats ");
        mCategoryList.add("Women’s Clothing");
        mCategoryList.add("Baby");
        mCategoryList.add("Bridal");
        mCategoryList.add("College");
        mCategoryList.add("Jewish");
        mCategoryList.add("Kids");
        mCategoryList.add("Kosher");
        mCategoryList.add("Pets");
        mCategoryList.add("Travel");

    }


}
