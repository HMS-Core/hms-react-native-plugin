package com.huawei.hms.rn.site;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hms.site.api.model.Coordinate;
import com.huawei.hms.site.api.model.CoordinateBounds;
import com.huawei.hms.site.api.model.LocationType;
import com.huawei.hms.site.api.model.SearchStatus;
import com.huawei.hms.site.api.model.Site;
import com.huawei.hms.site.widget.SearchFilter;
import com.huawei.hms.site.widget.SearchIntent;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class RNHMSWidgetWrapper implements ActivityEventListener {

    private String TAG = RNHMSWidgetWrapper.class.getSimpleName();

    private Activity activity;

    private SearchIntent searchIntent;

    private Promise searchPromise;

    public RNHMSWidgetWrapper(Activity activity) {
        this.activity = activity;
    }


    public void createSearchWidget(ReadableMap params, Promise promise) {
        if (params == null) {
            Log.e(TAG, "Illegal argument.");
            promise.reject("Illegal argument.");
            return;
        }

        if (!params.hasKey("searchIntent")){
            promise.reject("The searchIntent field may not be null.");
            return;
        }

        ReadableMap searchIntentMap = params.getMap("searchIntent");

        if (!searchIntentMap.hasKey("apiKey") || searchIntentMap.isNull("apiKey") || searchIntentMap.getString("apiKey").isEmpty()) {
            promise.reject("Invalid API key.");
            return;
        }

        String encodedKey = null;
        try {
            encodedKey = URLEncoder.encode(searchIntentMap.getString("apiKey"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "API Key encoding error.");
            promise.reject("API Key encoding error.");
            return;
        }

        this.searchPromise = promise;

        searchIntent = new SearchIntent();
        searchIntent.setApiKey(encodedKey);

        if (params.hasKey("hint") && !params.isNull("hint")) {
            searchIntent.setHint(params.getString("hint"));
        }

        if (params.hasKey("searchFilter") && !params.isNull("searchFilter")) {
            SearchFilter searchFilter = createSearchFilter(params.getMap("searchFilter"));
            searchIntent.setSearchFilter(searchFilter);
        }

        HMSLogger.getInstance(activity).startMethodExecutionTimer("createSearchWidget");
        Intent intent = searchIntent.getIntent(activity);
        activity.startActivityForResult(intent, SearchIntent.SEARCH_REQUEST_CODE);

    }

    private SearchFilter createSearchFilter(ReadableMap searchFilterMap) {
        SearchFilter searchFilter = new SearchFilter();

        if (searchFilterMap.hasKey("bounds") && !searchFilterMap.isNull("bounds")) {
            CoordinateBounds bounds =
                RNHMSSiteUtils.toObject(searchFilterMap.getMap("bounds"), CoordinateBounds.class);
            searchFilter.setBounds(bounds);
        }

        if (searchFilterMap.hasKey("location") && !searchFilterMap.isNull("location")) {
            Coordinate location =
                RNHMSSiteUtils.toObject(searchFilterMap.getMap("location"), Coordinate.class);
            searchFilter.setLocation(location);
        }

        if (searchFilterMap.hasKey("countryCode") && !searchFilterMap.isNull("countryCode")) {
            String countryCode = searchFilterMap.getString("countryCode");
            searchFilter.setCountryCode(countryCode);
        }

        if (searchFilterMap.hasKey("language") && !searchFilterMap.isNull("language")) {
            String language = searchFilterMap.getString("language");
            searchFilter.setLanguage(language);
        }

        if (searchFilterMap.hasKey("politicalView") && !searchFilterMap.isNull("politicalView")) {
            String politicalView = searchFilterMap.getString("politicalView");
            searchFilter.setPoliticalView(politicalView);
        }

        if (searchFilterMap.hasKey("query") && !searchFilterMap.isNull("query")) {
            String query = searchFilterMap.getString("query");
            searchFilter.setQuery(query);
        }

        if (searchFilterMap.hasKey("radius") && !searchFilterMap.isNull("radius")) {
            int radius = searchFilterMap.getInt("radius");
            searchFilter.setRadius(radius);
        }

        if (searchFilterMap.hasKey("poiTypes") && !searchFilterMap.isNull("poiTypes")) {
            ArrayList<Object> poiTypes = searchFilterMap.getArray("poiTypes").toArrayList();
            List<LocationType> poiTypeList = new ArrayList<>();

            for (Object poiType : poiTypes) {
                LocationType locationType = LocationType.valueOf((String) poiType);
                poiTypeList.add(locationType);
            }

            searchFilter.setPoiType(poiTypeList);
        }
        return searchFilter;
    }


    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (SearchIntent.SEARCH_REQUEST_CODE == requestCode) {
            HMSLogger logger = HMSLogger.getInstance(activity);
            if (SearchIntent.isSuccess(resultCode) && searchIntent != null) {
                Site site = searchIntent.getSiteFromIntent(data);
                logger.sendSingleEvent("createSearchWidget");
                RNHMSSiteUtils.handleResult(site, true, searchPromise);
            } else {
                SearchStatus searchStatus = searchIntent.getStatusFromIntent(data);
                logger.sendSingleEvent("createSearchWidget", searchStatus.getErrorCode());
                RNHMSSiteUtils.handleResult(searchStatus, false, searchPromise);
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {

    }
}
