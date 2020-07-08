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

package com.huawei.hms.rn.location;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import android.os.Looper;
import android.location.Location;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReadableMap;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.HWLocation;
import com.huawei.hms.location.LocationAvailability;
import com.huawei.hms.location.LocationCallback;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.location.LocationSettingsResponse;
import com.huawei.hms.location.SettingsClient;

import com.huawei.hms.rn.location.helpers.Exceptions;
import com.huawei.hms.rn.location.helpers.ResultHandler;
import com.huawei.hms.rn.location.helpers.LocationCallbackWithHandler;
import com.huawei.hms.rn.location.helpers.Constants.Event;
import com.huawei.hms.rn.location.utils.LocationUtils;
import com.huawei.hms.rn.location.utils.PermissionUtils;
import com.huawei.hms.rn.location.utils.ReactUtils;


public class RNFusedLocationModule extends ReactContextBaseJavaModule implements ResultHandler {

    private final ReactApplicationContext reactContext;

    protected static final String TAG = RNFusedLocationModule.class.getSimpleName();

    private FusedLocationProviderClient fusedLocationProviderClient;
    private HashMap<String, LocationCallback> locationCallbackMap;

    private SettingsClient settingsClient;

    public RNFusedLocationModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(reactContext);
        settingsClient = LocationServices.getSettingsClient(reactContext);
        locationCallbackMap = new HashMap<String, LocationCallback>();
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> priorityConstants = new HashMap<>();
        priorityConstants.put("PRIORITY_BALANCED_POWER_ACCURACY", LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        priorityConstants.put("PRIORITY_HIGH_ACCURACY", LocationRequest.PRIORITY_HIGH_ACCURACY);
        priorityConstants.put("PRIORITY_LOW_POWER", LocationRequest.PRIORITY_LOW_POWER);
        priorityConstants.put("PRIORITY_NO_POWER", LocationRequest.PRIORITY_NO_POWER);


        final Map<String, Object> eventConstants = new HashMap<>();
        eventConstants.put("SCANNING_RESULT", Event.SCANNING_RESULT.getVal());

        final Map<String, Object> constants = new HashMap<>();
        constants.put("PriorityConstants", priorityConstants);
        constants.put("Events", eventConstants);

        return constants;
    }

    @Override
    public String getName() {
        return "HMSFusedLocation";
    }

    @ReactMethod
    public void flushLocations(final Promise promise) {
        Log.i(TAG, "flushLocations begin");
        fusedLocationProviderClient.flushLocations()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "flushLocations::onSuccess");
                        promise.resolve(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "flushLocations::onFailure -> " + e.getMessage());
                        promise.reject(e);
                    }
                });
    }

    @ReactMethod
    public void checkLocationSettings(final ReadableMap locationRequestMap, final Promise promise) {
        if (LocationUtils.checkForObstacles(getCurrentActivity(), fusedLocationProviderClient, promise)) {
            return;
        }

        settingsClient.checkLocationSettings(LocationUtils.fromReadableMapToLocationSettingsRequest.map(locationRequestMap))
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "checkLocationSettings::onSuccess");
                        promise.resolve(LocationUtils.fromLocationSettingsStatesResponseToWritableMap(locationSettingsResponse.getLocationSettingsStates()));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "checkLocationSettings::onFailure -> " + e.getMessage());
                        promise.reject(e);
                    }
                });
    }

    @ReactMethod
    public void getLastLocation(final Promise promise) {
        Log.i(TAG, "getLastLocation begin");

        if (LocationUtils.checkForObstacles(getCurrentActivity(), fusedLocationProviderClient, promise)) {
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.i(TAG, "getLastLocation::onSuccess -> " + location.toString());
                promise.resolve(LocationUtils.fromLocationToWritableMap(location));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "getLastLocation::onFailure -> " + e.getMessage());
                promise.reject(e);
            }
        });

        Log.i(TAG, "getLastLocation end");
    }

    @ReactMethod
    public void getLastLocationWithAddress(final ReadableMap map, final Promise promise) {
        Log.i(TAG, "getLastLocationWithAddress begin");

        if (LocationUtils.checkForObstacles(getCurrentActivity(), fusedLocationProviderClient, promise)) {
            return;
        }

        LocationRequest locationRequest = LocationUtils.fromReadableMapToLocationRequest.map(map);
        Log.i(TAG, "getLastLocationWithAddress locationRequest -> " + locationRequest.toString());
        fusedLocationProviderClient.getLastLocationWithAddress(locationRequest)
                .addOnSuccessListener(new OnSuccessListener<HWLocation>() {
                    @Override
                    public void onSuccess(HWLocation hwLocation) {
                        Log.i(TAG, "getLastLocationWithAddress::onSuccess");
                        if (hwLocation == null) {
                            Log.i(TAG, "hwLocation is null");
                            promise.reject(new Exceptions.NoHWLocation());
                            return;
                        }

                        Log.i(TAG, "getLastLocationWithAddress::onSuccess location -> " + LocationUtils.hwLocationToString(hwLocation));
                        promise.resolve(LocationUtils.fromHWLocationToWritableMap(hwLocation));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "getLastLocationWithAddress::onFailure -> " + e.getMessage());
                        promise.reject(e);
                    }
                });

        Log.i(TAG, "getLastLocationWithAddress end");
    }

    @ReactMethod
    public void getLocationAvailability(final Promise promise) {
        Log.i(TAG, "getLocationAvailability begin");
        if (LocationUtils.checkForObstacles(getCurrentActivity(), fusedLocationProviderClient, promise)) {
            return;
        }

        fusedLocationProviderClient.getLocationAvailability()
                .addOnSuccessListener(new OnSuccessListener<LocationAvailability>() {
                    @Override
                    public void onSuccess(LocationAvailability locationAvailability) {
                        Log.i(TAG, "getLocationAvailability::onSuccess -> ");
                        promise.resolve(Boolean.toString(locationAvailability.isLocationAvailable()));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "getLocationAvailability::onFailure:" + e.getMessage());
                        promise.reject(e);
                    }
                });

        Log.i(TAG, "getLocationAvailability end");
    }

    @ReactMethod
    public void setMockLocation(ReadableMap map, final Promise promise) {
        Log.i(TAG, "setMockLocation begin");

        if (LocationUtils.checkForObstacles(getCurrentActivity(), fusedLocationProviderClient, promise)) {
            return;
        }

        Location location = new Location("RN-MOCK");
        location.setLongitude(map.getDouble("longitude"));
        location.setLatitude(map.getDouble("latitude"));
        fusedLocationProviderClient.setMockLocation(location).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void v) {
                Log.i(TAG, "setMockLocation::onSuccess");
                promise.resolve(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "setMockLocation::onFailure -> " + e.getMessage());
                promise.reject(e);
            }
        });

        Log.i(TAG, "setMockLocation end");
    }

    @ReactMethod
    public void setMockMode(final boolean shouldMock, final Promise promise) {
        Log.i(TAG, "setMockMode -> shouldMock=" + shouldMock);

        if (LocationUtils.checkForObstacles(getCurrentActivity(), fusedLocationProviderClient, promise)) {
            return;
        }

        fusedLocationProviderClient.setMockMode(shouldMock).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void v) {
                Log.i(TAG, "setMockMode::onSuccess");
                promise.resolve(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "setMockMode::onFailure -> " + e.getMessage());
                promise.reject(e);
            }
        });
        Log.i(TAG, "setMockMode end");
    }

    @ReactMethod
    private void requestLocationUpdatesWithCallback(final ReadableMap readableMap, final Promise promise) {
        Log.i(TAG, "requestLocationUpdatesWithCallback start");

        if (LocationUtils.checkForObstacles(getCurrentActivity(), fusedLocationProviderClient, promise)) {
            return;
        }

        final String id = readableMap.getString("id");
        final LocationRequest locationRequest = LocationUtils.fromReadableMapToLocationRequest.map(readableMap);

        if (locationCallbackMap.get(id) != null) {
            Log.i(TAG, "requestLocationUpdatesWithCallback: this callback id already exists");
            promise.reject(new Exceptions.DuplicateIdError());
            return;
        }

        // Create locationCallback
        LocationCallback locationCallback = new LocationCallbackWithHandler(this);
        locationCallbackMap.put(id, (LocationCallback) locationCallback);

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "requestLocationUpdatesWithCallback onSuccess");
                        promise.resolve(ReactUtils.basicMap("id", id));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.i(TAG, "requestLocationUpdatesWithCallback onFailure:" + e.getMessage());
                        promise.reject(e);
                    }
                });

        Log.i(TAG, "call requestLocationUpdatesWithCallback success.");
    }

    @ReactMethod
    public void removeLocationUpdates(final String id, final Promise promise) {
        LocationCallback callback = locationCallbackMap.get(id);

        if (callback == null) {
            Log.i(TAG, "removeLocationUpdates callback is null");
            promise.reject(new Exceptions.EmptyCallback());
            return;
        }

        fusedLocationProviderClient.removeLocationUpdates(callback).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "removeLocationUpdates::onSuccess");
                promise.resolve(ReactUtils.basicMap("id", id));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.i(TAG, "removeLocationUpdates::onSuccess");
                promise.reject(e);
            }
        });
    }

    @ReactMethod
    public void requestPermission() {
        PermissionUtils.requestLocationPermission(getCurrentActivity());
    }

    @ReactMethod
    public void hasPermission(final Promise promise) {
        promise.resolve(PermissionUtils.hasLocationPermission(getCurrentActivity()));
    }

    public void handleResult(Location location) {
        WritableMap params = LocationUtils.fromLocationToWritableMap(location);
        ReactUtils.sendEvent(getReactApplicationContext(), Event.SCANNING_RESULT.getVal(), params);
    }

}
