/*
    Copyright 2020-2022. Huawei Technologies Co., Ltd. All rights reserved.

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

import com.huawei.hms.site.api.model.Coordinate;
import com.huawei.hms.site.api.model.CoordinateBounds;
import com.huawei.hms.site.api.model.DetailSearchRequest;
import com.huawei.hms.site.api.model.HwLocationType;
import com.huawei.hms.site.api.model.LocationType;
import com.huawei.hms.site.api.model.NearbySearchRequest;
import com.huawei.hms.site.api.model.QueryAutocompleteRequest;
import com.huawei.hms.site.api.model.QuerySuggestionRequest;
import com.huawei.hms.site.api.model.TextSearchRequest;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RNHMSSiteUtils {

    private static Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();

    private RNHMSSiteUtils() {
    }

    public static <T> Map<String, Object> toMap(T obj) {
        return gson.fromJson(gson.toJson(obj), Map.class);
    }

    public static List<String> toCountyList(ReadableMap params) {
        List<String> countryList = new ArrayList<>();

        if (params == null) {
            return countryList;
        }

        ArrayList<Object> countries = params.getArray("countries").toArrayList();


        for (Object country : countries) {
            countryList.add((String) country);
        }

        return countryList;
    }

    public static <T> T toObject(ReadableMap params, Class<T> clazz) {

        if (params == null || clazz == null) {
            return null;
        }

        HashMap<String, Object> paramMap = params.toHashMap();

        return gson.fromJson(gson.toJson(paramMap), clazz);
    }

    public static void handleResult(Object response, boolean isSuccess, Promise promise) {
        if (response != null) {
            Map<String, Object> result = RNHMSSiteUtils.toMap(response);
            if (isSuccess) {
                promise.resolve(Arguments.makeNativeMap(result));
            } else {
                promise.reject("SEARCH_ERROR", Arguments.makeNativeMap(result));
            }
        } else {
            promise.reject("-1", "UNKNOWN_ERROR");
        }
    }

    public static boolean hasValidKey(ReadableMap rm, String key, ReadableType type) {
        return rm.hasKey(key) && rm.getType(key) == type;
    }

    public static QuerySuggestionRequest getQuerySuggestionRequestFromReadableMap(ReadableMap rm, Promise promise) {
        QuerySuggestionRequest querySuggestionRequest = new QuerySuggestionRequest();

        if (hasValidKey(rm, "query", ReadableType.String)) {
            querySuggestionRequest.setQuery(rm.getString("query"));
        }
        if (hasValidKey(rm, "location", ReadableType.Map)) {
            Coordinate location = RNHMSSiteUtils.toObject(rm.getMap("location"), Coordinate.class);
            querySuggestionRequest.setLocation(location);
        }
        if (hasValidKey(rm, "bounds", ReadableType.Map)) {
            CoordinateBounds bounds = RNHMSSiteUtils.toObject(rm.getMap("bounds"), CoordinateBounds.class);
            querySuggestionRequest.setBounds(bounds);
        }
        if (hasValidKey(rm, "radius", ReadableType.Number)) {
            querySuggestionRequest.setRadius(rm.getInt("radius"));
        }
        if (hasValidKey(rm, "poiTypes", ReadableType.Array)) {
            ArrayList<Object> poiTypes = rm.getArray("poiTypes").toArrayList();
            List<LocationType> poiTypeList = new ArrayList<>();

            for (Object poiType : poiTypes) {
                try {
                    LocationType locationType = LocationType.valueOf((String) poiType);
                    poiTypeList.add(locationType);
                } catch (IllegalArgumentException e){
                    promise.reject(
                            "INVALID_POI_TYPE",
                            poiType + " is not available Poi Type. ( " + e.getMessage() + " )"
                    );
                }
            }

            querySuggestionRequest.setPoiTypes(poiTypeList);
        }
        if (hasValidKey(rm, "countryCode", ReadableType.String)) {
            querySuggestionRequest.setCountryCode(rm.getString("countryCode"));
        }
        if (hasValidKey(rm, "language", ReadableType.String)) {
            querySuggestionRequest.setLanguage(rm.getString("language"));
        }
        if (hasValidKey(rm, "politicalView", ReadableType.String)) {
            querySuggestionRequest.setPoliticalView(rm.getString("politicalView"));
        }
        if (hasValidKey(rm, "children", ReadableType.Boolean)) {
            querySuggestionRequest.setChildren(rm.getBoolean("children"));
        }
        if (hasValidKey(rm, "strictBounds", ReadableType.Boolean)) {
            querySuggestionRequest.setStrictBounds(rm.getBoolean("strictBounds"));
        }
        if (hasValidKey(rm, "countries", ReadableType.Array)) {
            querySuggestionRequest.setCountries(toCountyList(rm));
        }
        return querySuggestionRequest;
    }

    public static DetailSearchRequest getDetailSearchRequestFromReadableMap(ReadableMap rm, Promise promise) {
        DetailSearchRequest detailSearchRequest = new DetailSearchRequest();

        if (hasValidKey(rm, "siteId", ReadableType.String)) {
            detailSearchRequest.setSiteId(rm.getString("siteId"));
        }
        if (hasValidKey(rm, "language", ReadableType.String)) {
            detailSearchRequest.setLanguage(rm.getString("language"));
        }
        if (hasValidKey(rm, "politicalView", ReadableType.String)) {
            detailSearchRequest.setPoliticalView(rm.getString("politicalView"));
        }
        if (hasValidKey(rm, "children", ReadableType.Boolean)) {
            detailSearchRequest.setChildren(rm.getBoolean("children"));
        }
        return detailSearchRequest;
    }

    public static TextSearchRequest getTextSearchRequestFromReadableMap(ReadableMap rm, Promise promise) {
        TextSearchRequest textSearchRequest = new TextSearchRequest();

        if (hasValidKey(rm, "query", ReadableType.String)) {
            textSearchRequest.setQuery(rm.getString("query"));
        }
        if (hasValidKey(rm, "location", ReadableType.Map)) {
            Coordinate location = RNHMSSiteUtils.toObject(rm.getMap("location"), Coordinate.class);
            textSearchRequest.setLocation(location);
        }
        if (hasValidKey(rm, "radius", ReadableType.Number)) {
            textSearchRequest.setRadius(rm.getInt("radius"));
        }
        if (hasValidKey(rm, "poiType", ReadableType.String)) {
            textSearchRequest.setPoiType(LocationType.valueOf(rm.getString("poiType")));
        }
        if (hasValidKey(rm, "hwPoiType", ReadableType.String)) {
            textSearchRequest.setHwPoiType(HwLocationType.valueOf(rm.getString("hwPoiType")));
        }
        if (hasValidKey(rm, "countryCode", ReadableType.String)) {
            textSearchRequest.setCountryCode(rm.getString("countryCode"));
        }
        if (hasValidKey(rm, "language", ReadableType.String)) {
            textSearchRequest.setLanguage(rm.getString("language"));
        }
        if (hasValidKey(rm, "pageSize", ReadableType.Number)) {
            textSearchRequest.setPageSize(rm.getInt("pageSize"));
        }
        if (hasValidKey(rm, "pageIndex", ReadableType.Number)) {
            textSearchRequest.setPageIndex(rm.getInt("pageIndex"));
        }
        if (hasValidKey(rm, "politicalView", ReadableType.String)) {
            textSearchRequest.setPoliticalView(rm.getString("politicalView"));
        }
        if (hasValidKey(rm, "children", ReadableType.Boolean)) {
            textSearchRequest.setChildren(rm.getBoolean("children"));
        }
        if (hasValidKey(rm, "countries", ReadableType.Array)) {
            textSearchRequest.setCountries(toCountyList(rm));
        }
        return textSearchRequest;
    }

    public static NearbySearchRequest getNearbySearchRequestFromReadableMap(ReadableMap rm, Promise promise) {
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest();

        if (hasValidKey(rm, "location", ReadableType.Map)) {
            Coordinate location = RNHMSSiteUtils.toObject(rm.getMap("location"), Coordinate.class);
            nearbySearchRequest.setLocation(location);
        }
        if (hasValidKey(rm, "radius", ReadableType.Number)) {
            nearbySearchRequest.setRadius(rm.getInt("radius"));
        }
        if (hasValidKey(rm, "query", ReadableType.String)) {
            nearbySearchRequest.setQuery(rm.getString("query"));
        }
        if (hasValidKey(rm, "poiType", ReadableType.String)) {
            nearbySearchRequest.setPoiType(LocationType.valueOf(rm.getString("poiType")));
        }
        if (hasValidKey(rm, "hwPoiType", ReadableType.String)) {
            nearbySearchRequest.setHwPoiType(HwLocationType.valueOf(rm.getString("hwPoiType")));
        }
        if (hasValidKey(rm, "language", ReadableType.String)) {
            nearbySearchRequest.setLanguage(rm.getString("language"));
        }
        if (hasValidKey(rm, "pageSize", ReadableType.Number)) {
            nearbySearchRequest.setPageSize(rm.getInt("pageSize"));
        }
        if (hasValidKey(rm, "pageIndex", ReadableType.Number)) {
            nearbySearchRequest.setPageIndex(rm.getInt("pageIndex"));
        }
        if (hasValidKey(rm, "politicalView", ReadableType.String)) {
            nearbySearchRequest.setPoliticalView(rm.getString("politicalView"));
        }
        if (hasValidKey(rm, "strictBounds", ReadableType.Boolean)) {
            nearbySearchRequest.setStrictBounds(rm.getBoolean("strictBounds"));
        }
        return nearbySearchRequest;
    }

    public static QueryAutocompleteRequest getQueryAutocompleteRequestFromReadableMap(ReadableMap rm, Promise promise) {
        QueryAutocompleteRequest queryAutocompleteRequest = new QueryAutocompleteRequest();

        if (hasValidKey(rm, "query", ReadableType.String)) {
            queryAutocompleteRequest.setQuery(rm.getString("query"));
        }
        if (hasValidKey(rm, "location", ReadableType.Map)) {
            Coordinate location = RNHMSSiteUtils.toObject(rm.getMap("location"), Coordinate.class);
            queryAutocompleteRequest.setLocation(location);
        }
        if (hasValidKey(rm, "radius", ReadableType.Number)) {
            queryAutocompleteRequest.setRadius(rm.getInt("radius"));
        }
        if (hasValidKey(rm, "language", ReadableType.String)) {
            queryAutocompleteRequest.setLanguage(rm.getString("language"));
        }
        if (hasValidKey(rm, "politicalView", ReadableType.String)) {
            queryAutocompleteRequest.setPoliticalView(rm.getString("politicalView"));
        }
        if (hasValidKey(rm, "children", ReadableType.Boolean)) {
            queryAutocompleteRequest.setChildren(rm.getBoolean("children"));
        }
        return queryAutocompleteRequest;
    }
}
