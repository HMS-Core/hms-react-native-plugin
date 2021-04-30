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
import android.text.TextUtils;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.huawei.hms.site.api.SearchResultListener;
import com.huawei.hms.site.api.SearchService;
import com.huawei.hms.site.api.SearchServiceFactory;
import com.huawei.hms.site.api.model.DetailSearchRequest;
import com.huawei.hms.site.api.model.DetailSearchResponse;
import com.huawei.hms.site.api.model.NearbySearchRequest;
import com.huawei.hms.site.api.model.NearbySearchResponse;
import com.huawei.hms.site.api.model.QueryAutocompleteRequest;
import com.huawei.hms.site.api.model.QueryAutocompleteResponse;
import com.huawei.hms.site.api.model.QuerySuggestionRequest;
import com.huawei.hms.site.api.model.QuerySuggestionResponse;
import com.huawei.hms.site.api.model.SearchStatus;
import com.huawei.hms.site.api.model.TextSearchRequest;
import com.huawei.hms.site.api.model.TextSearchResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.huawei.hms.rn.site.RNHMSSiteUtils.getQuerySuggestionRequestFromReadableMap;
import static com.huawei.hms.rn.site.RNHMSSiteUtils.hasValidKey;

public class RNHMSSiteWrapper {
    private static final String METHOD_NAME_INITIALIZE_SERVICE = "initializeService";
    private static final String METHOD_NAME_TEXT_SEARCH = "textSearch";
    private static final String METHOD_NAME_DETAIL_SEARCH = "detailSearch";
    private static final String METHOD_NAME_QUERY_SUGGESTION = "querySuggestion";
    private static final String METHOD_NAME_NEARBY_SEARCH = "nearbySearch";
    private static final String METHOD_NAME_QUERY_AUTOCOMPLETE = "queryAutocomplete";
    private String TAG = RNHMSSiteWrapper.class.getSimpleName();
    private SearchService searchService;
    private HMSLogger logger;

    public RNHMSSiteWrapper(Activity currentActivity) {
        this.logger = HMSLogger.getInstance(currentActivity);
    }

    public void initializeService(ReadableMap params, Activity activity, Promise promise) {
        if (params == null) {
            Log.e(TAG, "Illegal argument. Config must not be null.");
            promise.reject("INVALID_CONFIG", "Illegal argument. Config must not be null.");
            return;
        }

        if (!params.hasKey("apiKey") || params.isNull("apiKey") || params.getString("apiKey").isEmpty()) {
            promise.reject("INVALID_API_KEY", "Invalid API key.");
            return;
        }
        String encodedKey = null;
        try {
            encodedKey = URLEncoder.encode(params.getString("apiKey"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "API Key encoding error.");
            promise.reject("INVALID_API_KEY", "API Key encoding error.");
            return;
        }
        logger.startMethodExecutionTimer(METHOD_NAME_INITIALIZE_SERVICE);
        searchService = SearchServiceFactory.create(activity, encodedKey);
        logger.sendSingleEvent(METHOD_NAME_INITIALIZE_SERVICE);
        promise.resolve(null);
    }

    public boolean checkParams(ReadableMap params, Promise promise, String requestName) {
        if (searchService == null) {
            Log.e(TAG, "SearchService is not initialized.");
            promise.reject("NOT_INITIALIZED", "SearchService is not initialized.");
            return true;
        }

        if (params == null) {
            Log.e(TAG, "Illegal argument. " + requestName + " must not be null.");
            promise.reject("INVALID_REQUEST", "Illegal argument. " + requestName + " must not be null.");
            return true;
        }

        if (hasValidKey(params, "radius", ReadableType.Number)) {
            int radius = params.getInt("radius");

            if (radius < 1 || radius > 50000) {
                promise.reject("INVALID_REQUEST", "Illegal argument. radius field must be between 1 and 50000.");
                return true;
            }
        }
        return false;
    }

    public void textSearch(ReadableMap params, Promise promise) {
        if (checkParams(params, promise, METHOD_NAME_TEXT_SEARCH))
            return;

        if (!hasValidKey(params, "query", ReadableType.String) || TextUtils.isEmpty(params.getString("query"))) {
            Log.e(TAG, "Illegal argument. query field is mandatory and it must not be null.");
            promise.reject("INVALID_REQUEST", "Illegal argument. query field is mandatory and it must not be null.");
            return;
        }

        try {
            logger.startMethodExecutionTimer(METHOD_NAME_TEXT_SEARCH);
            TextSearchRequest request = RNHMSSiteUtils.getTextSearchRequestFromReadableMap(params, promise);

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
        } catch (Exception e) {
            RNHMSSiteUtils.handleResult(e, false, promise);
        }
    }

    public void detailSearch(ReadableMap params, Promise promise) {
        checkParams(params, promise, METHOD_NAME_DETAIL_SEARCH);

        if (!hasValidKey(params, "siteId", ReadableType.String) || TextUtils.isEmpty(params.getString("siteId"))) {
            Log.e(TAG, "Illegal argument. siteId field is mandatory and it must not be null.");
            promise.reject("INVALID_REQUEST", "Illegal argument. siteId field is mandatory and it must not be null.");
            return;
        }

        logger.startMethodExecutionTimer(METHOD_NAME_DETAIL_SEARCH);

        try {
            DetailSearchRequest request = RNHMSSiteUtils.getDetailSearchRequestFromReadableMap(params, promise);
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
        } catch (Exception e) {
            RNHMSSiteUtils.handleResult(e, false, promise);
        }
    }

    public void querySuggestion(ReadableMap params, Promise promise) {
        if (checkParams(params, promise, METHOD_NAME_QUERY_SUGGESTION))
            return;

        if (!hasValidKey(params, "query", ReadableType.String) || TextUtils.isEmpty(params.getString("query"))) {
            Log.e(TAG, "Illegal argument. query field is mandatory and it must not be null.");
            promise.reject("INVALID_REQUEST", "Illegal argument. query field is mandatory and it must not be null.");
            return;
        }

        try {
            QuerySuggestionRequest request = getQuerySuggestionRequestFromReadableMap(params, promise);

            logger.startMethodExecutionTimer(METHOD_NAME_QUERY_SUGGESTION);
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
        } catch (Exception e) {
            RNHMSSiteUtils.handleResult(e, false, promise);
        }
    }

    public void nearbySearch(ReadableMap params, Promise promise) {
        checkParams(params, promise, METHOD_NAME_NEARBY_SEARCH);

        if (!hasValidKey(params, "location", ReadableType.Map)) {
            Log.e(TAG, "Illegal argument. location field is mandatory and it must not be null.");
            promise.reject("INVALID_REQUEST", "Illegal argument. location field is mandatory and it must not be null.");
            return;
        }

        logger.startMethodExecutionTimer(METHOD_NAME_NEARBY_SEARCH);

        try {
            NearbySearchRequest request = RNHMSSiteUtils.getNearbySearchRequestFromReadableMap(params, promise);

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
        } catch (Exception e) {
            RNHMSSiteUtils.handleResult(e, false, promise);
        }
    }

    public void queryAutocomplete(ReadableMap params, Promise promise) {
        checkParams(params, promise, METHOD_NAME_QUERY_AUTOCOMPLETE);

        if (!hasValidKey(params, "query", ReadableType.String) || TextUtils.isEmpty(params.getString("query"))){
            Log.e(TAG, "Illegal argument. query field is mandatory and it must not be null.");
            promise.reject("INVALID_REQUEST", "Illegal argument. query field is mandatory and it must not be null.");
            return;
        }

        logger.startMethodExecutionTimer(METHOD_NAME_QUERY_AUTOCOMPLETE);

        try {
            QueryAutocompleteRequest request = RNHMSSiteUtils.getQueryAutocompleteRequestFromReadableMap(params, promise);

            searchService.queryAutocomplete(request, new SearchResultListener<QueryAutocompleteResponse>() {
                @Override
                public void onSearchResult(QueryAutocompleteResponse response) {
                    logger.sendSingleEvent(METHOD_NAME_QUERY_AUTOCOMPLETE);
                    RNHMSSiteUtils.handleResult(response, true, promise);
                }

                @Override
                public void onSearchError(SearchStatus searchStatus) {
                    logger.sendSingleEvent(METHOD_NAME_QUERY_AUTOCOMPLETE, searchStatus.getErrorCode());
                    RNHMSSiteUtils.handleResult(searchStatus, false, promise);
                }
            });
        } catch (Exception e) {
            RNHMSSiteUtils.handleResult(e, false, promise);
        }
    }
}
