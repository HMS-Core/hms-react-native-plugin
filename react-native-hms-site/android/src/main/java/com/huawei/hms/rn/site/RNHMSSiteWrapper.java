/*
Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.huawei.hms.rn.site;

import android.app.Activity;
import android.util.Log;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import com.huawei.hms.site.api.SearchResultListener;
import com.huawei.hms.site.api.SearchService;
import com.huawei.hms.site.api.SearchServiceFactory;
import com.huawei.hms.site.api.model.DetailSearchRequest;
import com.huawei.hms.site.api.model.DetailSearchResponse;
import com.huawei.hms.site.api.model.NearbySearchRequest;
import com.huawei.hms.site.api.model.NearbySearchResponse;
import com.huawei.hms.site.api.model.QuerySuggestionRequest;
import com.huawei.hms.site.api.model.QuerySuggestionResponse;
import com.huawei.hms.site.api.model.SearchStatus;
import com.huawei.hms.site.api.model.TextSearchRequest;
import com.huawei.hms.site.api.model.TextSearchResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RNHMSSiteWrapper {
    private static final String METHOD_NAME_INITIALIZE_SERVICE = "initializeService";
    private static final String METHOD_NAME_TEXT_SEARCH = "textSearch";
    private static final String METHOD_NAME_DETAIL_SEARCH = "detailSearch";
    private static final String METHOD_NAME_QUERY_SUGGESTION = "querySuggestion";
    private static final String METHOD_NAME_NEARBY_SEARCH = "nearbySearch";
    private String TAG = RNHMSSiteWrapper.class.getSimpleName();
    private SearchService searchService;
    private HMSLogger logger;

    public RNHMSSiteWrapper(Activity currentActivity) {
        this.logger = HMSLogger.getInstance(currentActivity);
    }

    public void initializeService(ReadableMap params, Activity activity, Promise promise) {
        if (params == null) {
            Log.e(TAG, "Illegal argument. Config must not be null.");
            promise.reject("Illegal argument. Config must not be null.");
            return;
        }

        if (!params.hasKey("apiKey") || params.isNull("apiKey") || params.getString("apiKey").isEmpty()) {
            promise.reject("Invalid API key.");
            return;
        }
        String encodedKey = null;
        try {
            encodedKey = URLEncoder.encode(params.getString("apiKey"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "API Key encoding error.");
            promise.reject("API Key encoding error.");
            return;
        }
        logger.startMethodExecutionTimer(METHOD_NAME_INITIALIZE_SERVICE);
        searchService = SearchServiceFactory.create(activity, encodedKey);
        logger.sendSingleEvent(METHOD_NAME_INITIALIZE_SERVICE);
        promise.resolve(null);
    }

    public void textSearch(ReadableMap params, Promise promise) {
        if (searchService == null) {
            Log.e(TAG, "SearchService is not initialized.");
            promise.reject("SearchService is not initialized.");
            return;
        }

        if (params == null) {
            Log.e(TAG, "Illegal argument. TextSearchRequest must not be null.");
            promise.reject("Illegal argument. TextSearchRequest must not be null.");
            return;
        }

        if (!params.hasKey("query") || params.isNull("query")) {
            Log.e(TAG, "Illegal argument. query field is mandatory and it must not be null.");
            promise.reject("Illegal argument. query field is mandatory and it must not be null.");
            return;
        }
        logger.startMethodExecutionTimer(METHOD_NAME_TEXT_SEARCH);
        TextSearchRequest request = RNHMSSiteUtils.toObject(params, TextSearchRequest.class);
        searchService.textSearch(request, new SearchResultListener<TextSearchResponse>() {
            @Override
            public void onSearchResult(TextSearchResponse response) {
                logger.sendSingleEvent(METHOD_NAME_TEXT_SEARCH);
                RNHMSSiteUtils.handleResult(response, true, promise);
            }

            @Override
            public void onSearchError(SearchStatus searchStatus) {
                logger.sendSingleEvent(METHOD_NAME_TEXT_SEARCH, searchStatus.getErrorCode());
                RNHMSSiteUtils.handleResult(searchStatus, false, promise);
            }
        });
    }

    public void detailSearch(ReadableMap params, Promise promise) {
        if (searchService == null) {
            Log.e(TAG, "SearchService is not initialized.");
            promise.reject("SearchService is not initialized.");
            return;
        }

        if (params == null) {
            Log.e(TAG, "Illegal argument. DetailSearchRequest must not be null.");
            promise.reject("Illegal argument. DetailSearchRequest must not be null.");
            return;
        }

        if (!params.hasKey("siteId") || params.isNull("siteId")) {
            Log.e(TAG, "Illegal argument. siteId field is mandatory and it must not be null.");
            promise.reject("Illegal argument. siteId field is mandatory and it must not be null.");
            return;
        }
        logger.startMethodExecutionTimer(METHOD_NAME_DETAIL_SEARCH);
        DetailSearchRequest request = RNHMSSiteUtils.toObject(params, DetailSearchRequest.class);
        searchService.detailSearch(request, new SearchResultListener<DetailSearchResponse>() {
            @Override
            public void onSearchResult(DetailSearchResponse response) {
                logger.sendSingleEvent(METHOD_NAME_DETAIL_SEARCH);
                RNHMSSiteUtils.handleResult(response, true, promise);
            }

            @Override
            public void onSearchError(SearchStatus searchStatus) {
                logger.sendSingleEvent(METHOD_NAME_DETAIL_SEARCH, searchStatus.getErrorCode());
                RNHMSSiteUtils.handleResult(searchStatus, false, promise);
            }
        });
    }

    public void querySuggestion(ReadableMap params, Promise promise) {

        if (searchService == null) {
            Log.e(TAG, "SearchService is not initialized.");
            promise.reject("SearchService is not initialized.");
            return;
        }

        if (params == null) {
            Log.e(TAG, "Illegal argument. QuerySuggestionRequest must not be null.");
            promise.reject("Illegal argument. QuerySuggestionRequest must not be null.");
            return;
        }

        if (!params.hasKey("query") || params.isNull("query")) {
            Log.e(TAG, "Illegal argument. query field is mandatory and it must not be null.");
            promise.reject("Illegal argument. query field is mandatory and it must not be null.");
            return;
        }
        logger.startMethodExecutionTimer(METHOD_NAME_QUERY_SUGGESTION);
        QuerySuggestionRequest request = RNHMSSiteUtils.toObject(params, QuerySuggestionRequest.class);
        searchService.querySuggestion(request, new SearchResultListener<QuerySuggestionResponse>() {
            @Override
            public void onSearchResult(QuerySuggestionResponse response) {
                logger.sendSingleEvent(METHOD_NAME_QUERY_SUGGESTION);
                RNHMSSiteUtils.handleResult(response, true, promise);
            }

            @Override
            public void onSearchError(SearchStatus searchStatus) {
                logger.sendSingleEvent(METHOD_NAME_QUERY_SUGGESTION, searchStatus.getErrorCode());
                RNHMSSiteUtils.handleResult(searchStatus, false, promise);
            }
        });
    }

    public void nearbySearch(ReadableMap params, Promise promise) {
        if (searchService == null) {
            Log.e(TAG, "SearchService is not initialized.");
            promise.reject("SearchService is not initialized.");
            return;
        }

        if (params == null) {
            Log.e(TAG, "Illegal argument. NearbySearchRequest must not be null.");
            promise.reject("Illegal argument. NearbySearchRequest must not be null.");
            return;
        }

        if (!params.hasKey("location") || params.isNull("location")) {
            Log.e(TAG, "Illegal argument. location field is mandatory and it must not be null.");
            promise.reject("Illegal argument. location field is mandatory and it must not be null.");
            return;
        }
        logger.startMethodExecutionTimer(METHOD_NAME_NEARBY_SEARCH);
        NearbySearchRequest request = RNHMSSiteUtils.toObject(params, NearbySearchRequest.class);
        searchService.nearbySearch(request, new SearchResultListener<NearbySearchResponse>() {
            @Override
            public void onSearchResult(NearbySearchResponse response) {
                logger.sendSingleEvent(METHOD_NAME_NEARBY_SEARCH);
                RNHMSSiteUtils.handleResult(response, true, promise);
            }

            @Override
            public void onSearchError(SearchStatus searchStatus) {
                logger.sendSingleEvent(METHOD_NAME_NEARBY_SEARCH, searchStatus.getErrorCode());
                RNHMSSiteUtils.handleResult(searchStatus, false, promise);
            }
        });
    }
}
