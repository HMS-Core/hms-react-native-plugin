/*
    Copyright 2020-2021. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License")
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.huawei.hms.rn.site;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
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

        if (!params.hasKey("searchIntent")) {
            promise.reject("The searchIntent field may not be null.");
            return;
        }

        ReadableMap searchIntentMap = params.getMap("searchIntent");

        if (!searchIntentMap.hasKey("apiKey") || searchIntentMap.isNull("apiKey") || searchIntentMap.getString("apiKey").isEmpty()) {
            promise.reject("Error", "Invalid API key.");
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
            SearchFilter searchFilter = createSearchFilter(params.getMap("searchFilter"), promise);
            searchIntent.setSearchFilter(searchFilter);
        }

        HMSLogger.getInstance(activity).startMethodExecutionTimer("createSearchWidget");
        Intent intent = searchIntent.getIntent(activity);
        activity.startActivityForResult(intent, SearchIntent.SEARCH_REQUEST_CODE);

    }

    private SearchFilter createSearchFilter(ReadableMap searchFilterMap, Promise promise) {
        SearchFilter searchFilter = new SearchFilter();

        if (searchFilterMap.hasKey("query") && !searchFilterMap.isNull("query") && searchFilterMap.getType("query") == ReadableType.String) {
            String query = searchFilterMap.getString("query");
            searchFilter.setQuery(query);
        }

        if (searchFilterMap.hasKey("bounds") && !searchFilterMap.isNull("bounds") && searchFilterMap.getType("bounds") == ReadableType.Map) {
            CoordinateBounds bounds =
                    RNHMSSiteUtils.toObject(searchFilterMap.getMap("bounds"), CoordinateBounds.class);
            searchFilter.setBounds(bounds);
        }

        if (searchFilterMap.hasKey("location") && !searchFilterMap.isNull("location") && searchFilterMap.getType("location") == ReadableType.Map) {
            Coordinate location =
                    RNHMSSiteUtils.toObject(searchFilterMap.getMap("location"), Coordinate.class);
            searchFilter.setLocation(location);
        }

        if (searchFilterMap.hasKey("countryCode") && !searchFilterMap.isNull("countryCode") && searchFilterMap.getType("countryCode") == ReadableType.String) {
            String countryCode = searchFilterMap.getString("countryCode");
            searchFilter.setCountryCode(countryCode);
        }

        if (searchFilterMap.hasKey("language") && !searchFilterMap.isNull("language") && searchFilterMap.getType("language") == ReadableType.String) {
            String language = searchFilterMap.getString("language");
            searchFilter.setLanguage(language);
        }

        if (searchFilterMap.hasKey("radius") && !searchFilterMap.isNull("radius") && searchFilterMap.getType("radius") == ReadableType.Number) {
            int radius = searchFilterMap.getInt("radius");

            if (radius < 1 || radius > 50000) {
                Log.e(TAG, "Illegal argument. radius field must be between 1 and 50000.");
                promise.reject("Illegal argument. radius field must be between 1 and 50000.");
            }
            searchFilter.setRadius(radius);
        }


        if (searchFilterMap.hasKey("poiTypes") && !searchFilterMap.isNull("poiTypes") && searchFilterMap.getType("poiTypes") == ReadableType.Array) {
            ArrayList<Object> poiTypes = searchFilterMap.getArray("poiTypes").toArrayList();
            List<LocationType> poiTypeList = new ArrayList<>();

            for (Object poiType : poiTypes) {

                if (RNHMSSiteUtils.isValidPoiType((String) poiType)) {
                    LocationType locationType = LocationType.valueOf((String) poiType);
                    poiTypeList.add(locationType);
                } else {
                    promise.reject((String) poiType + " is not available Poi Type");
                }
            }

            searchFilter.setPoiType(poiTypeList);
        }

        if (searchFilterMap.hasKey("strictBounds") && !searchFilterMap.isNull("strictBounds") && searchFilterMap.getType("strictBounds") == ReadableType.Boolean) {
            boolean strictBounds = searchFilterMap.getBoolean("strictBounds");
            searchFilter.setStrictBounds(strictBounds);
        }

        if (searchFilterMap.hasKey("children") && !searchFilterMap.isNull("children") && searchFilterMap.getType("children") == ReadableType.Boolean) {
            boolean children = searchFilterMap.getBoolean("children");
            searchFilter.setChildren(children);
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
