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

package com.huawei.hms.rn.location.helpers;

import java.util.List;

import android.util.Log;
import android.location.Location;

import com.huawei.hms.location.LocationResult;
import com.huawei.hms.location.HWLocation;
import com.huawei.hms.location.LocationAvailability;
import com.huawei.hms.location.LocationCallback;

import com.huawei.hms.rn.location.utils.LocationUtils;


public class LocationCallbackWithHandler extends LocationCallback {

    private static final String TAG = LocationCallbackWithHandler.class.getSimpleName();
    private ResultHandler mResultHandler;

    public LocationCallbackWithHandler(ResultHandler resultHandler) {
        mResultHandler = resultHandler;
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        Log.i(TAG, "requestLocationUpdatesWithCallback callback onLocationResult print");
        if (locationResult == null) {
            return;
        }

        Log.i(TAG, "requestLocationUpdatesWithCallback callback  onLocationResult locationResult is not null");
        List<Location> locations = locationResult.getLocations();
        if (!locations.isEmpty()) {
            Log.i(TAG, "[old]requestLocationUpdatesWithCallback callback onLocationResult location is empty");
        }

        for (Location location : locations) {
            Log.i(TAG,
                  "[old]onLocationResult location[Longitude,Latitude,Accuracy]:"
                  + location.getLongitude() + "," + location.getLatitude() + ","
                  + location.getAccuracy());

            mResultHandler.handleResult(location);
        }

        List<HWLocation> hwLocationList = locationResult.getHWLocationList();
        if (!locations.isEmpty()) {
            Log.i(TAG, "[new]requestLocationUpdatesWithCallback callback onLocationResult location is empty");
        }

        // TODO: handle/emit HWLocation?
        // Maybe create a seperate event for HWLocations
        for (HWLocation hwLocation : hwLocationList) {
            Log.i(TAG, "[new]onLocationResult location:" + LocationUtils.hwLocationToString(hwLocation));
        }
    }

    @Override
    public void onLocationAvailability(LocationAvailability locationAvailability) {
        Log.i(TAG, "requestLocationUpdatesWithCallback callback onLocationAvailability print");
        if (locationAvailability != null) {
            boolean flag = locationAvailability.isLocationAvailable();
            Log.i(TAG, "onLocationAvailability isLocationAvailable:" + flag);
        }
    }
}
