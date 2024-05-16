/*
    Copyright 2020-2024. Huawei Technologies Co., Ltd. All rights reserved.

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
import android.text.TextUtils;
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

import static com.huawei.hms.rn.site.RNHMSSiteUtils.hasValidKey;

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
            promise.reject("ILLEGAL_ARGUMENT", "Illegal argument.");
            return;
        }

        if (!hasValidKey(params, "searchIntent", ReadableType.Map)) {
            promise.reject("NULL_SEARCH_INTENT", "The searchIntent field may not be null.");
            return;
        }

        ReadableMap searchIntentMap = params.getMap("searchIntent");

        if (!hasValidKey(searchIntentMap, "apiKey", ReadableType.String) || TextUtils.isEmpty("apiKey")) {
            promise.reject("INVALID_API_KEY_ERROR", "Invalid API key.");
            return;
        }

        String encodedKey = null;
        try {
            encodedKey = URLEncoder.encode(searchIntentMap.getString("apiKey"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "API Key encoding error.");
            promise.reject("API_KEY_ENCODING_ERROR", "API Key encoding error.");
            return;
        }

        this.searchPromise = promise;

        searchIntent = new SearchIntent();
        searchIntent.setApiKey(encodedKey);

        if (params.hasKey("hint") && !params.isNull("hint")) {
            searchIntent.setHint(params.getString("hint"));
        }

        if (hasValidKey(params, "searchFilter", ReadableType.Map)) {

            ReadableMap searchFilterMap = params.getMap("searchFilter");

            if (hasValidKey(searchFilterMap, "radius", ReadableType.Number)) {
                int radius = searchFilterMap.getInt("radius");

                if (radius < 1 || radius > 50000) {
                    promise.reject("ILLEGAL_ARGUMENT", "Illegal argument. radius field must be between 1 and 50000.");
                    return;
                }
            }

            SearchFilter searchFilter = createSearchFilter(searchFilterMap, promise);
            searchIntent.setSearchFilter(searchFilter);
        }

        HMSLogger.getInstance(activity).startMethodExecutionTimer("createSearchWidget");
        Intent intent = searchIntent.getIntent(activity);
        activity.startActivityForResult(intent, SearchIntent.SEARCH_REQUEST_CODE);
    }

    private SearchFilter createSearchFilter(ReadableMap searchFilterMap, Promise promise) {
        SearchFilter searchFilter = new SearchFilter();

        if (hasValidKey(searchFilterMap, "query", ReadableType.String)) {
            searchFilter.setQuery(searchFilterMap.getString("query"));
        }
        if (hasValidKey(searchFilterMap, "location", ReadableType.Map)) {
            Coordinate location =
                RNHMSSiteUtils.toObject(searchFilterMap.getMap("location"), Coordinate.class);
            searchFilter.setLocation(location);
        }
        if (hasValidKey(searchFilterMap, "radius", ReadableType.Number)) {
            searchFilter.setRadius(searchFilterMap.getInt("radius"));
        }
        if (hasValidKey(searchFilterMap, "bounds", ReadableType.Map)) {
            CoordinateBounds bounds =
                RNHMSSiteUtils.toObject(searchFilterMap.getMap("bounds"), CoordinateBounds.class);
            searchFilter.setBounds(bounds);
        }
        if (hasValidKey(searchFilterMap, "countryCode", ReadableType.String)) {
            searchFilter.setCountryCode(searchFilterMap.getString("countryCode"));
        }
        if (hasValidKey(searchFilterMap, "language", ReadableType.String)) {
            searchFilter.setLanguage(searchFilterMap.getString("language"));
        }
        if (hasValidKey(searchFilterMap, "poiTypes", ReadableType.Array)) {
            ArrayList<Object> poiTypes = searchFilterMap.getArray("poiTypes").toArrayList();
            List<LocationType> poiTypeList = new ArrayList<>();

            for (Object poiType : poiTypes) {
                LocationType locationType = LocationType.valueOf((String) poiType);
                poiTypeList.add(locationType);
            }

            searchFilter.setPoiType(poiTypeList);
        }
        if (hasValidKey(searchFilterMap, "politicalView", ReadableType.String)) {
            searchFilter.setPoliticalView(searchFilterMap.getString("politicalView"));
        }
        if (hasValidKey(searchFilterMap, "children", ReadableType.Boolean)) {
            searchFilter.setChildren(searchFilterMap.getBoolean("children"));
        }
        if (hasValidKey(searchFilterMap, "strictBounds", ReadableType.Boolean)) {
            searchFilter.setStrictBounds(searchFilterMap.getBoolean("strictBounds"));
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
            } else if(searchIntent != null) {
                SearchStatus searchStatus = searchIntent.getStatusFromIntent(data);
                logger.sendSingleEvent("createSearchWidget", searchStatus.getErrorCode());
                RNHMSSiteUtils.handleResult(searchStatus, false, searchPromise);
            } else {
                logger.sendSingleEvent("createSearchWidget", "-1");
                RNHMSSiteUtils.handleResult(null, false, searchPromise);
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {

    }
}
