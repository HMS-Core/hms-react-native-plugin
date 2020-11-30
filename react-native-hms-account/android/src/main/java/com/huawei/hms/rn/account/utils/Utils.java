/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;
import com.huawei.hms.support.hwid.ui.HuaweiIdAuthButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Utils {
    private static final String FIELD_ACCESS_TOKEN = "accessToken";
    private static final String FIELD_DISPLAY_NAME = "displayName";
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_FAMILY_NAME = "familyName";
    private static final String FIELD_GIVEN_NAME = "givenName";
    private static final String FIELD_ID_TOKEN = "idToken";
    private static final String FIELD_AUTHORIZATION_CODE = "authorizationCode";
    private static final String FIELD_UNION_ID = "unionId";
    private static final String FIELD_AGE_RANGE = "ageRange";
    private static final String FIELD_COUNTRY_CODE = "countryCode";
    private static final String FIELD_AVATAR_URI_STRING = "avatarUriString";
    private static final String FIELD_EXPRESSION_TIME_SECS = "expressionTimeSecs";
    private static final String FIELD_SERVICE_COUNTRY_CODE = "serviceCountryCode";
    private static final String FIELD_UID = "uid";
    private static final String FIELD_OPEN_ID = "openId";
    private static final String FIELD_GENDER = "gender";
    private static final String FIELD_DESCRIBE_CONTENTS = "describeContentsInAuthHuaweiId";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_TYPE = "type";
    private static final String FIELD_STATUS = "status";
    private static final String FIELD_AUTHORIZED_SCOPES = "authorizedScopes";
    private static final String FIELD_EXTENSION_SCOPE = "extensionScopes";
    private static final String FIELD_PHOTO_URL = "photoUrl";
    private static final String FIELD_SCOPE_ARRAY = "authScopeList";
    private static final String FIELD_SERVER_AUTH_CODE = "serverAuthCode";
    private static final String FIELD_ACCOUNT = "account";
    private static final String FIELD_ERROR_STRING = "errorCode";
    private static final String FIELD_STATUS_MESSAGE = "statusMessage";
    private static final String FIELD_STATUS_CODE = "statusCode";
    private static final String FIELD_COLOR_POLICY = "colorPolicy";
    private static final String FIELD_CORNER_RADIUS = "cornerRadius";
    private static final String FIELD_THEME = "theme";

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
        arguments.putString(FIELD_ID_TOKEN, authHuaweiId.getIdToken());
        arguments.putString(FIELD_AUTHORIZATION_CODE, authHuaweiId.getAuthorizationCode());
        arguments.putString(FIELD_UNION_ID, authHuaweiId.getUnionId());
        arguments.putString(FIELD_AGE_RANGE, authHuaweiId.getAgeRange());
        arguments.putString(FIELD_COUNTRY_CODE, authHuaweiId.getCountryCode());
        arguments.putString(FIELD_AVATAR_URI_STRING, authHuaweiId.getAvatarUriString());
        arguments.putInt(FIELD_EXPRESSION_TIME_SECS, (int) authHuaweiId.getExpirationTimeSecs());
        arguments.putString(FIELD_SERVICE_COUNTRY_CODE, authHuaweiId.getServiceCountryCode());
        arguments.putString(FIELD_UID, authHuaweiId.getUid());
        arguments.putString(FIELD_OPEN_ID, authHuaweiId.getOpenId());
        arguments.putInt(FIELD_GENDER, authHuaweiId.getGender());
        arguments.putInt(FIELD_DESCRIBE_CONTENTS, authHuaweiId.describeContents());
        arguments.putInt(FIELD_STATUS, authHuaweiId.getStatus());
        arguments.putArray(FIELD_AUTHORIZED_SCOPES, parseScopeSet(authHuaweiId.getAuthorizedScopes()));
        arguments.putArray(FIELD_EXTENSION_SCOPE, parseScopeSet(authHuaweiId.getExtensionScopes()));
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

    public static WritableMap parseButton(HuaweiIdAuthButton button) {
        if (button == null) {
            return null;
        }
        WritableMap arguments = Arguments.createMap();
        arguments.putInt(FIELD_COLOR_POLICY, button.getColorPolicy());
        arguments.putInt(FIELD_CORNER_RADIUS, button.getCornerRadius());
        arguments.putInt(FIELD_THEME, button.getTheme());
        return arguments;
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

    public static HuaweiIdAuthParams toHuaweiIdAuthParams(ReadableArray requestOption, String huaweiIdAuthParams, ReadableArray scopeList, Promise promise) {
        HuaweiIdAuthParamsHelper builder;

        if(huaweiIdAuthParams.equals("DEFAULT_AUTH_REQUEST_PARAM")){
            builder = new HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM);
        } else if (huaweiIdAuthParams.equals("DEFAULT_AUTH_REQUEST_PARAM_GAME")){
            builder = new HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM_GAME);
        } else {
            promise.reject("3003", "Invalid huaweiIdAuthParams Parameter");
            return null;
        }

        ArrayList<String> listData = new ArrayList<>();
        List<Scope> scopeListData = toScopeList(scopeList);
        if (requestOption != null) {
            for (int i = 0; i <requestOption.size(); i++) {
                listData.add(requestOption.getString(i));
            }
        }

        if (listData.contains("profile"))
            builder.setProfile();

        if (listData.contains("idToken"))
            builder.setIdToken();

        if (listData.contains("accessToken"))
            builder.setAccessToken();

        if (listData.contains("mobileNumber"))
            builder.setMobileNumber();

        if (listData.contains("email"))
            builder.setEmail();

        if (listData.contains("shippingAddress"))
            builder.setShippingAddress();

        if (listData.contains("uid"))
            builder.setUid();

        if (listData.contains("id"))
            builder.setId();

        if (listData.contains("authorizationCode"))
            builder.setAuthorizationCode();

        if (scopeList != null)
            builder.setScopeList(scopeListData);

        return builder.createParams();
    }

    public static AuthHuaweiId toAuthHuaweiId(ReadableMap readableMap) {

        String openId = (String) Utils.argumentNullCheck(readableMap, FIELD_OPEN_ID);
        String uid = (String) Utils.argumentNullCheck(readableMap, FIELD_UID);
        String displayName = (String) Utils.argumentNullCheck(readableMap, FIELD_DISPLAY_NAME);
        String photoUrl = (String) Utils.argumentNullCheck(readableMap, FIELD_PHOTO_URL);
        String accessToken = (String) Utils.argumentNullCheck(readableMap, FIELD_ACCESS_TOKEN);
        String serviceCountryCode = (String) Utils.argumentNullCheck(readableMap, FIELD_SERVICE_COUNTRY_CODE);
        int status =  readableMap.hasKey(FIELD_STATUS) ? readableMap.getInt(FIELD_STATUS) : 0;
        int gender =  readableMap.hasKey(FIELD_GENDER) ? readableMap.getInt(FIELD_GENDER) : -1;
        Set<Scope> scopeList = readableMap.hasKey(FIELD_SCOPE_ARRAY) ?  (Set<Scope>) (Object) toSet(readableMap.getArray(FIELD_SCOPE_ARRAY)) : new HashSet();
        String serverAuthCode = (String) Utils.argumentNullCheck(readableMap, FIELD_SERVER_AUTH_CODE);
        String unionId = (String) Utils.argumentNullCheck(readableMap, FIELD_UNION_ID);
        String countryCode = (String) Utils.argumentNullCheck(readableMap, FIELD_COUNTRY_CODE);

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
        if(scopeArray != null){
            for (int index = 0; index < scopeArray.size(); index++) {
                String scopeUri = scopeArray.getString(index);
                scopeList.add(scopeUri == null ? new Scope() : new Scope(scopeUri));
            }
        }
        return scopeList;
    }

    public static ReadableArray getScopeArray(ReadableMap readableMap) {
        ReadableArray fieldScope = (ReadableArray) Utils.argumentNullCheck(readableMap, FIELD_SCOPE_ARRAY);
        return fieldScope;
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

    public static Object argumentNullCheck(final ReadableMap readableMap, final String key) {
            if (readableMap.hasKey(key)) {
            switch(readableMap.getType(key)){
                case String:
                    return readableMap.getString(key);
                case Number:
                    return readableMap.getInt(key);
                case Map:
                    return readableMap.getMap(key);
                case Array:
                    return readableMap.getArray(key);
            }
        }
        return null;
    }

    public static Long argumentNullCheckAndConvert(final ReadableMap readableMap, final String key){
        if (readableMap.hasKey(key)) {
            return ((Double) readableMap.getDouble(key)).longValue();
        }
        return null;
    }

}
