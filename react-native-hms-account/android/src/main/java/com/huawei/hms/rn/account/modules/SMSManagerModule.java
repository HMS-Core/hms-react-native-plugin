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

package com.huawei.hms.rn.account.modules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.api.CommonStatusCodes;
import com.huawei.hms.rn.account.utils.Utils;
import com.huawei.hms.support.api.client.Status;
import com.huawei.hms.support.sms.ReadSmsManager;
import com.huawei.hms.support.sms.common.ReadSmsConstant;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

public class SMSManagerModule extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = "SMSManager";

    private static final String HASHING_ALGORITHM_SHA_256 = "SHA-256";
    private static final String ERROR_MESAGE_DIGEST_IS_NULL = "MessageDigest is null";
    private static final String FIELD_BASE_64_HASH = "base64Hash";
    private static final String FIELD_ERROR = "Error: ";
    private static final String FIELD_STATUS = "Status";
    private static final String FIELD_MESSAGE = "Message";

    public SMSManagerModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @NonNull
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void startReadSMSManager(final Promise promise) {
        Task<Void> task = ReadSmsManager.start(Objects.requireNonNull(getCurrentActivity()));
        task.addOnFailureListener(e -> Utils.handleError(promise, e));

        IntentFilter intentFilter = new IntentFilter(ReadSmsConstant.READ_SMS_BROADCAST_ACTION);
        getCurrentActivity().registerReceiver(new SMSBroadcastReceiver(promise), intentFilter);
    }

    @ReactMethod
    public void getHashCode(Promise promise) {
        MessageDigest messageDigest = getMessageDigest();
        if (messageDigest == null) {
            promise.reject(ERROR_MESAGE_DIGEST_IS_NULL);
        } else {
            String packageName = Objects.requireNonNull(getCurrentActivity()).getPackageName();
            String signature = getSignature(getCurrentActivity(), packageName);
            String appInfo = packageName + " " + signature;
            messageDigest.update(appInfo.getBytes(StandardCharsets.UTF_8));
            byte[] hashSignature = messageDigest.digest();
            hashSignature = Arrays.copyOfRange(hashSignature, 0, 9);
            String base64Hash = Base64.encodeToString(hashSignature, Base64.NO_PADDING | Base64.NO_WRAP);
            base64Hash = base64Hash.substring(0, 11);
            WritableMap base64HashMap = Arguments.createMap();
            base64HashMap.putString(FIELD_BASE_64_HASH, base64Hash);
            promise.resolve(base64HashMap);
        }
    }

    private MessageDigest getMessageDigest() {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(HASHING_ALGORITHM_SHA_256);
        } catch (NoSuchAlgorithmException ex) {
            Log.e(MODULE_NAME, ex.getMessage());
        }
        return messageDigest;
    }

    private String getSignature(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        Signature[] signatureArrs;
        try {
            signatureArrs = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
        return Objects.requireNonNull(signatureArrs)[0].toCharsString();
    }

    private static class SMSBroadcastReceiver extends BroadcastReceiver {
        private final Promise promise;

        public SMSBroadcastReceiver(Promise promise) {
            this.promise = promise;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Status status = bundle.getParcelable(ReadSmsConstant.EXTRA_STATUS);
                if (Objects.requireNonNull(status).getStatusCode() != CommonStatusCodes.SUCCESS) {
                    promise.reject(FIELD_ERROR+Objects.requireNonNull(status).getStatusCode());
                } else {
                    WritableMap map = Arguments.createMap();
                    map.putMap(FIELD_STATUS, Utils.parseStatus(Objects.requireNonNull(status)));
                    map.putString(FIELD_MESSAGE, bundle.getString(ReadSmsConstant.EXTRA_SMS_MESSAGE));
                    promise.resolve(map);
                }
            }
        }
    }
}
