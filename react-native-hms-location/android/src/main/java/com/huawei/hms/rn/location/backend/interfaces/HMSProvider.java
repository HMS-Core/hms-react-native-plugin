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

package com.huawei.hms.rn.location.backend.interfaces;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.PermissionChecker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public abstract class HMSProvider {
    private Context ctx;
    private ActivityHolder activityHolder;
    private EventSender eventSender;
    private PermissionHandler permissionHandler;

    protected Map<Integer, PendingIntent> requests = new HashMap<>();

    /**
     * Build and return all the constants.
     *
     * @return JSONObject containing all the constants
     * @throws JSONException if something goes wrong while building up the object
     */
    public abstract JSONObject getConstants() throws JSONException;

    public HMSProvider(Context ctx) {
        this.ctx = ctx;
    }

    public Activity getActivity() {
        if (this.activityHolder == null) {
            return null;
        }
        return this.activityHolder.getActivity();
    }

    public Context getContext() {
        return this.ctx;
    }

    public boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = PermissionChecker.checkSelfPermission(getContext(), permission);
            return PackageManager.PERMISSION_GRANTED == result;
        } else {
            return true;
        }
    }

    public void requestPermission(int requestNo, String permission) {
        String[] permissions = new String[1];
        permissions[0] = permission;
        requestPermissions(requestNo, permissions);
    }

    public void requestPermissions(int requestNo, String[] permissions) {
        this.getPermissionHandler().requestPermissions(requestNo, permissions);
    }

    public EventSender getEventSender() {
        return eventSender;
    }

    public void setEventSender(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    public PermissionHandler getPermissionHandler() {
        return permissionHandler;
    }

    public void setPermissionHandler(PermissionHandler permissionHandler) {
        this.permissionHandler = permissionHandler;
    }

    public void setActivityHolder(ActivityHolder activityHolder) {
        this.activityHolder = activityHolder;
    }

    public PendingIntent buildPendingIntent(int requestCode, String action) {
        Intent intent = new Intent();
        intent.setPackage(getActivity().getApplicationContext().getPackageName());
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(),
                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        this.requests.put(requestCode, pendingIntent);
        return pendingIntent;
    }
    
    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        return false;
    }
}
