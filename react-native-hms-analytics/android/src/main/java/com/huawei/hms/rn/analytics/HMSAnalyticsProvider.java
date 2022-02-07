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

package com.huawei.hms.rn.analytics;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.huawei.hms.analytics.HiAnalytics;

import java.util.Arrays;

public class HMSAnalyticsProvider extends ContentProvider {

    private final String TAG = HMSAnalyticsProvider.class.getSimpleName();
    private final String[] routePolicyList = new String[]{"CN", "DE", "SG", "RU"};

    /**
     * Method overrides to send start up events.
     * <p>
     * Configurations will be read from AndroidManifest.xml
     * If analytics enabled value is set to false, startup events won't be send.
     * If route policy value is set, startup events will be sent to the corresponding region.
     *
     * @return true
     */
    @Override
    public boolean onCreate() {
        try {
            Log.d(TAG, "HMSAnalyticsContentProvider -> onCreate");
            ApplicationInfo ai = this.getContext().getPackageManager().getApplicationInfo(this.getContext().getPackageName(), PackageManager.GET_META_DATA);
            boolean isEnabled = ai.metaData.getBoolean("hms_is_analytics_enabled", true);

            if (!isEnabled) {
                return true;
            }

            String routePolicy = ai.metaData.getString("hms_analytics_route_policy");

            if (Arrays.asList(routePolicyList).contains(routePolicy)) {
                HiAnalytics.getInstance(this.getContext(), routePolicy);
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "HiAnalytics=> Invalid  routePolicy!, Initialization failed. Message: " + e.getMessage());
        }
        HiAnalytics.getInstance(this.getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
