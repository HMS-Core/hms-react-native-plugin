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

package com.huawei.hms.rn.account.utils;

import android.accounts.Account;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;

import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AccountIcon;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.api.client.Status;
import com.huawei.hms.support.api.entity.auth.Scope;
import com.huawei.hms.support.feature.result.AbstractAuthAccount;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;
import com.huawei.hms.support.hwid.ui.HuaweiIdAuthButton;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Utils {
    private static final String FIELD_AUTH_HUAWEI_ID = "authHuaweiId";

    private static final String FIELD_AUTH_ACCOUNT = "authAccount";

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

    private static final String FIELD_SCOPE_ARRAY = "authScopeList";

    private static final String FIELD_SERVER_AUTH_CODE = "serverAuthCode";

    private static final String FIELD_ACCOUNT = "account";

    private static final String FIELD_ERROR_STRING = "errorCode";

    private static final String FIELD_STATUS_MESSAGE = "statusMessage";

    private static final String FIELD_STATUS_CODE = "statusCode";

    private static final String FIELD_COLOR_POLICY = "colorPolicy";

    private static final String FIELD_CORNER_RADIUS = "cornerRadius";

    private static final String FIELD_THEME = "theme";

    private static final String FIELD_ACCOUNT_FLAG = "accountFlag";

    private static final String FIELD_CARRIERID = "carrierId";

    private static final String ICON = "icon";

    private static final String ICON_DESCRIPTION = "description";

    public static ReadableMap parseAuthHuaweiId(AuthHuaweiId authHuaweiId, Context context) {
        if (authHuaweiId == null) {
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
        arguments.putString(FIELD_SERVICE_COUNTRY_CODE, authHuaweiId.getServiceCountryCode());
        arguments.putString(FIELD_UID, authHuaweiId.getUid());
        arguments.putString(FIELD_OPEN_ID, authHuaweiId.getOpenId());
        arguments.putInt(FIELD_GENDER, authHuaweiId.getGender());
        arguments.putInt(FIELD_STATUS, authHuaweiId.getStatus());
        arguments.putArray(FIELD_AUTHORIZED_SCOPES, parseScopeSet(authHuaweiId.getAuthorizedScopes()));
        arguments.putArray(FIELD_EXTENSION_SCOPE, parseScopeSet(authHuaweiId.getExtensionScopes()));
        arguments.putMap(FIELD_ACCOUNT, parseAccount(authHuaweiId.getHuaweiAccount(context)));

        return arguments;
    }

    public static ReadableMap parseAuthAccount(AuthAccount authAccount, Context context) {
        if (authAccount == null) {
            return null;
        }
        WritableMap arguments = Arguments.createMap();
        arguments.putString(FIELD_ACCESS_TOKEN, authAccount.getAccessToken());
        arguments.putString(FIELD_DISPLAY_NAME, authAccount.getDisplayName());
        arguments.putString(FIELD_EMAIL, authAccount.getEmail());
        arguments.putString(FIELD_FAMILY_NAME, authAccount.getFamilyName());
        arguments.putString(FIELD_GIVEN_NAME, authAccount.getGivenName());
        arguments.putString(FIELD_ID_TOKEN, authAccount.getIdToken());
        arguments.putString(FIELD_AUTHORIZATION_CODE, authAccount.getAuthorizationCode());
        arguments.putString(FIELD_UNION_ID, authAccount.getUnionId());
        arguments.putString(FIELD_AGE_RANGE, authAccount.getAgeRange());
        arguments.putString(FIELD_COUNTRY_CODE, authAccount.getCountryCode());
        arguments.putString(FIELD_AVATAR_URI_STRING, authAccount.getAvatarUriString());
        arguments.putString(FIELD_SERVICE_COUNTRY_CODE, authAccount.getServiceCountryCode());
        arguments.putString(FIELD_UID, authAccount.getUid());
        arguments.putString(FIELD_OPEN_ID, authAccount.getOpenId());
        arguments.putInt(FIELD_GENDER, authAccount.getGender());
        arguments.putInt(FIELD_STATUS, authAccount.getStatus());
        arguments.putArray(FIELD_AUTHORIZED_SCOPES, parseScopeSet(authAccount.getAuthorizedScopes()));
        arguments.putArray(FIELD_EXTENSION_SCOPE, parseScopeSet(authAccount.getExtensionScopes()));
        arguments.putMap(FIELD_ACCOUNT, parseAccount(authAccount.getAccount(context)));
        arguments.putInt(FIELD_ACCOUNT_FLAG, authAccount.getAccountFlag());
        arguments.putInt(FIELD_CARRIERID, authAccount.getCarrierId());
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

    public static HuaweiIdAuthParams toHuaweiIdAuthParams(ReadableArray requestOption, String huaweiIdAuthParams,
        ReadableArray scopeList, Promise promise) {
        HuaweiIdAuthParamsHelper huaweiIdBuilder;

        if (huaweiIdAuthParams.equals("DEFAULT_AUTH_REQUEST_PARAM")) {
            huaweiIdBuilder = new HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM);
        } else if (huaweiIdAuthParams.equals("DEFAULT_AUTH_REQUEST_PARAM_GAME")) {
            huaweiIdBuilder = new HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM_GAME);
        } else {
            promise.reject("3003", "Invalid Parameter");
            return null;
        }

        ArrayList<String> huaweiIdListData = new ArrayList<>();
        List<Scope> scopeListData = toScopeList(scopeList);
        if (requestOption != null) {
            for (int i = 0; i < requestOption.size(); i++) {
                huaweiIdListData.add(requestOption.getString(i));
            }
        }

        if (huaweiIdListData.contains("profile")) {
            huaweiIdBuilder.setProfile();
        }

        if (huaweiIdListData.contains("idToken")) {
            huaweiIdBuilder.setIdToken();
        }

        if (huaweiIdListData.contains("accessToken")) {
            huaweiIdBuilder.setAccessToken();
        }

        if (huaweiIdListData.contains("mobileNumber")) {
            huaweiIdBuilder.setMobileNumber();
        }

        if (huaweiIdListData.contains("email")) {
            huaweiIdBuilder.setEmail();
        }

        if (huaweiIdListData.contains("shippingAddress")) {
            huaweiIdBuilder.setShippingAddress();
        }

        if (huaweiIdListData.contains("uid")) {
            huaweiIdBuilder.setUid();
        }

        if (huaweiIdListData.contains("id")) {
            huaweiIdBuilder.setId();
        }

        if (huaweiIdListData.contains("authorizationCode")) {
            huaweiIdBuilder.setAuthorizationCode();
        }

        if (scopeList != null) {
            huaweiIdBuilder.setScopeList(scopeListData);
        }

        return huaweiIdBuilder.createParams();
    }

    public static AccountAuthParams toAccountAuthParams(ReadableArray requestOption, String accountdAuthParams,
        ReadableArray scopeList, Promise promise) {
        AccountAuthParamsHelper accountAuthBuilder;

        if (accountdAuthParams.equals("DEFAULT_AUTH_REQUEST_PARAM")) {
            accountAuthBuilder = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM);
        } else if (accountdAuthParams.equals("DEFAULT_AUTH_REQUEST_PARAM_GAME")) {
            accountAuthBuilder = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM_GAME);
        } else {
            promise.reject("3003", "Invalid AccountAuthParams Parameter");
            return null;
        }

        ArrayList<String> accountListData = new ArrayList<>();
        List<Scope> scopeListData = toScopeList(scopeList);
        if (requestOption != null) {
            for (int i = 0; i < requestOption.size(); i++) {
                if (requestOption.getType(i).name().equals("String")) {
                    accountListData.add(requestOption.getString(i));
                } else if (requestOption.getType(i).name().equals("Map")) {
                    ReadableMap rm = requestOption.getMap(i);
                    if (rm.hasKey("idTokenSignAlg")) {
                        accountAuthBuilder.setIdTokenSignAlg(rm.getInt("idTokenSignAlg"));
                    }
                }

            }
        }

        if (accountListData.contains("profile")) {
            accountAuthBuilder.setProfile();
        }

        if (accountListData.contains("idToken")) {
            accountAuthBuilder.setIdToken();
        }

        if (accountListData.contains("accessToken")) {
            accountAuthBuilder.setAccessToken();
        }

        if (accountListData.contains("mobileNumber")) {
            accountAuthBuilder.setMobileNumber();
        }

        if (accountListData.contains("email")) {
            accountAuthBuilder.setEmail();
        }

        if (accountListData.contains("uid")) {
            accountAuthBuilder.setUid();
        }

        if (accountListData.contains("id")) {
            accountAuthBuilder.setId();
        }

        if (accountListData.contains("authorizationCode")) {
            accountAuthBuilder.setAuthorizationCode();
        }

        if (accountListData.contains("carrierId")) {
            accountAuthBuilder.setCarrierId();
        }

        if (scopeList != null) {
            accountAuthBuilder.setScopeList(scopeListData);
        }

        return accountAuthBuilder.createParams();
    }

    public static <T extends AbstractAuthAccount> T toAuthResult(ReadableMap readableMap, String authType) {
        T buildAuth = null;

        String openId = (String) Utils.argumentNullCheck(readableMap, FIELD_OPEN_ID);
        String unionId = (String) Utils.argumentNullCheck(readableMap, FIELD_UNION_ID);
        String uid = (String) Utils.argumentNullCheck(readableMap, FIELD_UID);
        Set<Scope> scopeList = readableMap.hasKey(FIELD_SCOPE_ARRAY) ? (Set<Scope>) (Object) toSet(
            readableMap.getArray(FIELD_SCOPE_ARRAY)) : new HashSet();
        String displayName = (String) Utils.argumentNullCheck(readableMap, FIELD_DISPLAY_NAME);
        String photoUrl = (String) Utils.argumentNullCheck(readableMap, FIELD_AVATAR_URI_STRING);
        int gender = readableMap.hasKey(FIELD_GENDER) ? readableMap.getInt(FIELD_GENDER) : -1;
        String accessToken = (String) Utils.argumentNullCheck(readableMap, FIELD_ACCESS_TOKEN);
        String serviceCountryCode = (String) Utils.argumentNullCheck(readableMap, FIELD_SERVICE_COUNTRY_CODE);
        int status = readableMap.hasKey(FIELD_STATUS) ? readableMap.getInt(FIELD_STATUS) : 0;
        String serverAuthCode = (String) Utils.argumentNullCheck(readableMap, FIELD_SERVER_AUTH_CODE);
        String countryCode = (String) Utils.argumentNullCheck(readableMap, FIELD_COUNTRY_CODE);
        int carrierId = readableMap.hasKey(FIELD_CARRIERID) ? readableMap.getInt(FIELD_CARRIERID) : -1;

        if (authType.equals(FIELD_AUTH_HUAWEI_ID)) {
            buildAuth = (T) AuthHuaweiId.build(openId, uid, displayName, photoUrl, accessToken, serviceCountryCode,
                status, gender, scopeList, serverAuthCode, unionId, countryCode);
        } else if (authType.equals(FIELD_AUTH_ACCOUNT)) {
            buildAuth = (T) AuthAccount.build(openId, uid, displayName, photoUrl, accessToken, serviceCountryCode,
                status, gender, scopeList, serverAuthCode, unionId, countryCode, carrierId);
        }

        return buildAuth;
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
        if (scopeArray != null) {
            for (int index = 0; index < scopeArray.size(); index++) {
                String scopeUri = scopeArray.getString(index);
                scopeList.add(scopeUri == null ? new Scope() : new Scope(scopeUri));
            }
        }
        return scopeList;
    }

    public static ReadableArray getScopeArray(ReadableMap readableMap) {
        return (ReadableArray) Utils.argumentNullCheck(readableMap, FIELD_SCOPE_ARRAY);
    }

    public static WritableMap parseStatus(Status status) {
        WritableMap map = Arguments.createMap();
        map.putString(FIELD_ERROR_STRING, status.getErrorString());
        map.putString(FIELD_STATUS_MESSAGE, status.getStatusMessage());
        map.putInt(FIELD_STATUS_CODE, status.getStatusCode());
        return map;
    }

    public static WritableMap parseAccountIcon(AccountIcon accountIcon) {
        if (accountIcon == null) {
            return null;
        }
        WritableMap arguments = Arguments.createMap();
        arguments.putString(ICON, String.valueOf(bitmapToByteArray(accountIcon.getIcon())));
        arguments.putString(ICON_DESCRIPTION, accountIcon.getDescription());
        return arguments;
    }

    public static String bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
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
            switch (readableMap.getType(key)) {
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

    public static Long argumentNullCheckAndConvert(final ReadableMap readableMap, final String key) {
        if (readableMap.hasKey(key)) {
            return ((Double) readableMap.getDouble(key)).longValue();
        }
        return null;
    }

}
