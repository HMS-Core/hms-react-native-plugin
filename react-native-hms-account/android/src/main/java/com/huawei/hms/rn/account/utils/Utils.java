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

package com.huawei.hms.rn.account.utils;

import android.accounts.Account;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.api.client.Status;
import com.huawei.hms.support.api.entity.auth.Scope;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Utils {
    private static final String FIELD_ACCESS_TOKEN = "accessToken";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_TYPE = "type";
    private static final String FIELD_OPEN_ID = "openId";
    private static final String FIELD_DISPLAY_NAME = "displayName";
    private static final String FIELD_PHOTO_URL = "photoUrl";
    private static final String FIELD_UID = "uid";
    private static final String FIELD_SERVICE_COUNTRY_CODE = "serviceCountryCode";
    private static final String FIELD_STATUS = "status";
    private static final String FIELD_GENDER = "gender";
    private static final String FIELD_SCOPE_ARRAY = "scopes";
    private static final String FIELD_SERVER_AUTH_CODE = "serverAuthCode";
    private static final String FIELD_UNION_ID = "unionId";
    private static final String FIELD_COUNTRY_CODE = "countryCode";
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_FAMILY_NAME = "familyName";
    private static final String FIELD_GIVEN_NAME = "givenName";
    private static final String FIELD_AUTHORIZATION_CODE = "authorizationCode";
    private static final String FIELD_ACCOUNT = "account";
    private static final String FIELD_AVATAR_URI_STRING = "avatarUriString";
    private static final String FIELD_ERROR_STRING = "errorCode";
    private static final String FIELD_STATUS_MESSAGE = "statusMessage";
    private static final String FIELD_STATUS_CODE = "statusCode";

    public static ReadableMap parseAuthHuaweiId(AuthHuaweiId authHuaweiId) {
        if(authHuaweiId==null){
            return null;
        }
        WritableMap arguments = Arguments.createMap();
        arguments.putString(FIELD_ACCESS_TOKEN, authHuaweiId.getAccessToken());
        arguments.putString(FIELD_DISPLAY_NAME, authHuaweiId.getDisplayName());
        arguments.putString(FIELD_EMAIL, authHuaweiId.getEmail());
        arguments.putString(FIELD_FAMILY_NAME, authHuaweiId.getFamilyName());
        arguments.putString(FIELD_GIVEN_NAME, authHuaweiId.getGivenName());
        arguments.putString(FIELD_AUTHORIZATION_CODE, authHuaweiId.getAuthorizationCode());
        arguments.putString(FIELD_UNION_ID, authHuaweiId.getUnionId());
        arguments.putString(FIELD_OPEN_ID, authHuaweiId.getOpenId());
        arguments.putString(FIELD_AVATAR_URI_STRING, authHuaweiId.getAvatarUriString());
        arguments.putArray(FIELD_SCOPE_ARRAY, parseScopeSet(authHuaweiId.getAuthorizedScopes()));
        arguments.putMap(FIELD_ACCOUNT, parseAccount(authHuaweiId.getHuaweiAccount()));
        return arguments;
    }

    public static WritableArray parseScopeSet(Set<Scope> scopeSet) {
        WritableArray array = Arguments.createArray();
        Iterator<Scope> scopeIterator = scopeSet.iterator();
        while (scopeIterator.hasNext()) {
            Scope scope = scopeIterator.next();
            array.pushString(scope.getScopeUri());
        }
        return array;
    }

    private static WritableMap parseAccount(Account account) {
        if (account == null) {
            return null;
        }
        
        WritableMap arguments = Arguments.createMap();
        arguments.putString(FIELD_NAME, account.name);
        arguments.putString(FIELD_TYPE, account.type);
        return arguments;
    }

    public static AuthHuaweiId toAuthHuaweiId(ReadableMap readableMap) {
        String openId = readableMap.getString(FIELD_OPEN_ID);
        String uid = readableMap.getString(FIELD_UID);
        String displayName = readableMap.getString(FIELD_DISPLAY_NAME);
        String photoUrl = readableMap.getString(FIELD_PHOTO_URL);
        String accessToken = readableMap.getString(FIELD_ACCESS_TOKEN);
        String serviceCountryCode = readableMap.getString(FIELD_SERVICE_COUNTRY_CODE);
        int status = readableMap.getInt(FIELD_STATUS);
        int gender = readableMap.getInt(FIELD_GENDER);
        Set<Scope> scopeList = (Set<Scope>) (Object) toSet(readableMap.getArray(FIELD_SCOPE_ARRAY));
        String serverAuthCode = readableMap.getString(FIELD_SERVER_AUTH_CODE);
        String unionId = readableMap.getString(FIELD_UNION_ID);
        String countryCode = readableMap.getString(FIELD_COUNTRY_CODE);
        return AuthHuaweiId.build(openId,
            uid,
            displayName,
            photoUrl,
            accessToken,
            serviceCountryCode,
            status,
            gender,
            scopeList,
            serverAuthCode,
            unionId,
            countryCode);
    }

    private static Set<Object> toSet(ReadableArray readableArray) {
        Set<Object> mySet = new HashSet<>();
        for (int index = 0; index < readableArray.size(); index++) {
            String scopeUri = readableArray.getString(index);
            mySet.add(scopeUri == null ? new Scope() : new Scope(scopeUri));
        }
        return mySet;
    }

    public static List<Scope> toScopeList(ReadableArray scopeArray) {
        List<Scope> scopeList = new ArrayList<>();
        for (int index = 0; index < scopeArray.size(); index++) {
            String scopeUri = scopeArray.getString(index);
            scopeList.add(scopeUri == null ? new Scope() : new Scope(scopeUri));
        }
        return scopeList;
    }

    public static ReadableArray getScopeArray(ReadableMap readableMap) {
        return readableMap.getArray(FIELD_SCOPE_ARRAY);
    }

    public static WritableMap parseStatus(Status status) {
        WritableMap map = Arguments.createMap();
        map.putString(FIELD_ERROR_STRING, status.getErrorString());
        map.putString(FIELD_STATUS_MESSAGE, status.getStatusMessage());
        map.putInt(FIELD_STATUS_CODE, status.getStatusCode());
        return map;
    }

    public static void handleError(final Promise promise, Exception e) {
        String errCode = "GENERIC_ERR";
        if (e instanceof ApiException) {
            errCode = String.valueOf(((ApiException) e).getStatusCode());
        }

        promise.reject(errCode, e.getMessage());
    }
}
