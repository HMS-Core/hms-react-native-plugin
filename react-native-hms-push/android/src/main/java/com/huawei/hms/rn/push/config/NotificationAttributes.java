/*
    Copyright 2020-2023. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.rn.push.config;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.huawei.hms.rn.push.constants.Core;
import com.huawei.hms.rn.push.constants.NotificationConstants;
import com.huawei.hms.rn.push.constants.ResultCode;
import com.huawei.hms.rn.push.utils.BundleUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationAttributes {
    private final String id;
    private final String message;
    private final double fireDate;
    private final String title;
    private final String ticker;
    private final boolean showWhen;
    private final boolean autoCancel;
    private final String largeIcon;
    private final String largeIconUrl;
    private final String smallIcon;
    private final String bigText;
    private final String subText;
    private final String bigPictureUrl;
    private final String shortcutId;
    private final String number;
    private final String channelId;
    private final String channelName;
    private final String channelDescription;
    private final String sound;
    private final String color;
    private final String group;
    private final boolean groupSummary;
    private final boolean playSound;
    private final boolean vibrate;
    private final double vibrateDuration;
    private final String actions;
    private final boolean invokeApp;
    private final String tag;
    private final String repeatType;
    private final double repeatTime;
    private final boolean ongoing;
    private final boolean allowWhileIdle;
    private final boolean dontNotifyInForeground;
    private final String data;

    public NotificationAttributes(Bundle bundle) {

        id = BundleUtils.get(bundle, NotificationConstants.ID);
        message = BundleUtils.get(bundle, NotificationConstants.MESSAGE);
        fireDate = BundleUtils.getD(bundle, NotificationConstants.FIRE_DATE);
        title = BundleUtils.get(bundle, NotificationConstants.TITLE);
        ticker = BundleUtils.get(bundle, NotificationConstants.TICKER);
        showWhen = BundleUtils.getB(bundle, NotificationConstants.SHOW_WHEN);
        autoCancel = BundleUtils.getB(bundle, NotificationConstants.AUTO_CANCEL);
        largeIcon = BundleUtils.get(bundle, NotificationConstants.LARGE_ICON);
        largeIconUrl = BundleUtils.get(bundle, NotificationConstants.LARGE_ICON_URL);
        smallIcon = BundleUtils.get(bundle, NotificationConstants.SMALL_ICON);
        bigText = BundleUtils.get(bundle, NotificationConstants.BIG_TEXT);
        subText = BundleUtils.get(bundle, NotificationConstants.SUB_TEXT);
        bigPictureUrl = BundleUtils.get(bundle, NotificationConstants.BIG_PICTURE_URL);
        shortcutId = BundleUtils.get(bundle, NotificationConstants.SHORTCUT_ID);
        number = BundleUtils.get(bundle, NotificationConstants.NUMBER);
        channelId = BundleUtils.get(bundle, NotificationConstants.CHANNEL_ID);
        channelName = BundleUtils.get(bundle, NotificationConstants.CHANNEL_NAME);
        channelDescription = BundleUtils.get(bundle, NotificationConstants.CHANNEL_DESCRIPTION);
        sound = BundleUtils.get(bundle, NotificationConstants.SOUND);
        color = BundleUtils.get(bundle, NotificationConstants.COLOR);
        group = BundleUtils.get(bundle, NotificationConstants.GROUP);
        groupSummary = BundleUtils.getB(bundle, NotificationConstants.GROUP_SUMMARY);
        playSound = BundleUtils.getB(bundle, NotificationConstants.PLAY_SOUND);
        vibrate = BundleUtils.getB(bundle, NotificationConstants.VIBRATE);
        vibrateDuration = BundleUtils.getD(bundle, NotificationConstants.VIBRATE_DURATION);
        actions = BundleUtils.get(bundle, NotificationConstants.ACTIONS);
        invokeApp = BundleUtils.getB(bundle, NotificationConstants.INVOKE_APP);
        tag = BundleUtils.get(bundle, NotificationConstants.TAG);
        repeatType = BundleUtils.get(bundle, NotificationConstants.REPEAT_TYPE);
        repeatTime = BundleUtils.getD(bundle, NotificationConstants.REPEAT_TIME);
        ongoing = BundleUtils.getB(bundle, NotificationConstants.ONGOING);
        allowWhileIdle = BundleUtils.getB(bundle, NotificationConstants.ALLOW_WHILE_IDLE);
        data = BundleUtils.convertJSON(bundle.getBundle(NotificationConstants.DATA));
        dontNotifyInForeground = BundleUtils.getB(bundle, NotificationConstants.DONT_NOTIFY_IN_FOREGROUND);
    }

    private NotificationAttributes(JSONObject json) {

        try {
            id = json.has(NotificationConstants.ID) ? json.getString(NotificationConstants.ID) : null;
            message = json.has(NotificationConstants.MESSAGE) ? json.getString(NotificationConstants.MESSAGE) : Core.DEFAULT_MESSAGE;
            fireDate = json.has(NotificationConstants.FIRE_DATE) ? json.getDouble(NotificationConstants.FIRE_DATE) : 0.0;
            title = json.has(NotificationConstants.TITLE) ? json.getString(NotificationConstants.TITLE) : null;
            ticker = json.has(NotificationConstants.TICKER) ? json.getString(NotificationConstants.TICKER) : null;
            showWhen = !json.has(NotificationConstants.SHOW_WHEN) || json.getBoolean(NotificationConstants.SHOW_WHEN);
            autoCancel = !json.has(NotificationConstants.AUTO_CANCEL) || json.getBoolean(NotificationConstants.AUTO_CANCEL);
            largeIcon = json.has(NotificationConstants.LARGE_ICON) ? json.getString(NotificationConstants.LARGE_ICON) : null;
            largeIconUrl = json.has(NotificationConstants.LARGE_ICON_URL) ? json.getString(NotificationConstants.LARGE_ICON_URL) : null;
            smallIcon = json.has(NotificationConstants.SMALL_ICON) ? json.getString(NotificationConstants.SMALL_ICON) : null;
            bigText = json.has(NotificationConstants.BIG_TEXT) ? json.getString(NotificationConstants.BIG_TEXT) : null;
            subText = json.has(NotificationConstants.SUB_TEXT) ? json.getString(NotificationConstants.SUB_TEXT) : null;
            bigPictureUrl = json.has(NotificationConstants.BIG_PICTURE_URL) ? json.getString(NotificationConstants.BIG_PICTURE_URL) : null;
            shortcutId = json.has(NotificationConstants.SHORTCUT_ID) ? json.getString(NotificationConstants.SHORTCUT_ID) : null;
            number = json.has(NotificationConstants.NUMBER) ? json.getString(NotificationConstants.NUMBER) : null;
            channelId = json.has(NotificationConstants.CHANNEL_ID) ? json.getString(NotificationConstants.CHANNEL_ID) : null;
            channelName = json.has(NotificationConstants.CHANNEL_NAME) ? json.getString(NotificationConstants.CHANNEL_NAME) : null;
            channelDescription = json.has(NotificationConstants.CHANNEL_DESCRIPTION) ? json.getString(NotificationConstants.CHANNEL_DESCRIPTION) : null;
            sound = json.has(NotificationConstants.SOUND) ? json.getString(NotificationConstants.SOUND) : null;
            color = json.has(NotificationConstants.COLOR) ? json.getString(NotificationConstants.COLOR) : null;
            group = json.has(NotificationConstants.GROUP) ? json.getString(NotificationConstants.GROUP) : null;
            groupSummary = json.has(NotificationConstants.GROUP_SUMMARY) && json.getBoolean(NotificationConstants.GROUP_SUMMARY);
            playSound = !json.has(NotificationConstants.PLAY_SOUND) || json.getBoolean(NotificationConstants.PLAY_SOUND);
            vibrate = !json.has(NotificationConstants.VIBRATE) || json.getBoolean(NotificationConstants.VIBRATE);
            vibrateDuration = json.has(NotificationConstants.VIBRATE_DURATION) ? json.getDouble(NotificationConstants.VIBRATE_DURATION) : 1000;
            actions = json.has(NotificationConstants.ACTIONS) ? json.getString(NotificationConstants.ACTIONS) : null;
            invokeApp = !json.has(NotificationConstants.INVOKE_APP) || json.getBoolean(NotificationConstants.INVOKE_APP);
            tag = json.has(NotificationConstants.TAG) ? json.getString(NotificationConstants.TAG) : null;
            repeatType = json.has(NotificationConstants.REPEAT_TYPE) ? json.getString(NotificationConstants.REPEAT_TYPE) : null;
            repeatTime = json.has(NotificationConstants.REPEAT_TIME) ? json.getDouble(NotificationConstants.REPEAT_TIME) : 0.0;
            ongoing = json.has(NotificationConstants.ONGOING) && json.getBoolean(NotificationConstants.ONGOING);
            allowWhileIdle = json.has(NotificationConstants.ALLOW_WHILE_IDLE) && json.getBoolean(NotificationConstants.ALLOW_WHILE_IDLE);
            dontNotifyInForeground = json.has(NotificationConstants.DONT_NOTIFY_IN_FOREGROUND) && json.getBoolean(NotificationConstants.DONT_NOTIFY_IN_FOREGROUND);
            data = json.has(NotificationConstants.DATA) ? json.getString(NotificationConstants.DATA) : null;
        } catch (IllegalStateException | JSONException | NumberFormatException | NullPointerException e) {
            throw new IllegalStateException(ResultCode.ERROR, e);
        }
    }

    @NonNull
    public static NotificationAttributes fromJson(String json) throws JSONException {

        JSONObject jsonObject = new JSONObject(json);
        return new NotificationAttributes(jsonObject);
    }

    public Bundle toBundle() {

        Bundle bundle = new Bundle();
        BundleUtils.set(bundle, NotificationConstants.ID, id);
        BundleUtils.set(bundle, NotificationConstants.MESSAGE, message);
        BundleUtils.setD(bundle, NotificationConstants.FIRE_DATE, fireDate);
        BundleUtils.set(bundle, NotificationConstants.TITLE, title);
        BundleUtils.set(bundle, NotificationConstants.TICKER, ticker);
        BundleUtils.setB(bundle, NotificationConstants.SHOW_WHEN, showWhen);
        BundleUtils.setB(bundle, NotificationConstants.AUTO_CANCEL, autoCancel);
        BundleUtils.set(bundle, NotificationConstants.LARGE_ICON, largeIcon);
        BundleUtils.set(bundle, NotificationConstants.LARGE_ICON_URL, largeIconUrl);
        BundleUtils.set(bundle, NotificationConstants.SMALL_ICON, smallIcon);
        BundleUtils.set(bundle, NotificationConstants.BIG_TEXT, bigText);
        BundleUtils.set(bundle, NotificationConstants.SUB_TEXT, subText);
        BundleUtils.set(bundle, NotificationConstants.BIG_PICTURE_URL, bigPictureUrl);
        BundleUtils.set(bundle, NotificationConstants.SHORTCUT_ID, shortcutId);
        BundleUtils.set(bundle, NotificationConstants.NUMBER, number);
        BundleUtils.set(bundle, NotificationConstants.CHANNEL_ID, channelId);
        BundleUtils.set(bundle, NotificationConstants.CHANNEL_NAME, channelName);
        BundleUtils.set(bundle, NotificationConstants.CHANNEL_DESCRIPTION, channelDescription);
        BundleUtils.set(bundle, NotificationConstants.SOUND, sound);
        BundleUtils.set(bundle, NotificationConstants.COLOR, color);
        BundleUtils.set(bundle, NotificationConstants.GROUP, group);
        BundleUtils.setB(bundle, NotificationConstants.GROUP_SUMMARY, groupSummary);
        BundleUtils.setB(bundle, NotificationConstants.PLAY_SOUND, playSound);
        BundleUtils.setB(bundle, NotificationConstants.VIBRATE, vibrate);
        BundleUtils.setD(bundle, NotificationConstants.VIBRATE_DURATION, vibrateDuration);
        BundleUtils.set(bundle, NotificationConstants.ACTIONS, actions);
        BundleUtils.setB(bundle, NotificationConstants.INVOKE_APP, invokeApp);
        BundleUtils.set(bundle, NotificationConstants.TAG, tag);
        BundleUtils.set(bundle, NotificationConstants.REPEAT_TYPE, repeatType);
        BundleUtils.setD(bundle, NotificationConstants.REPEAT_TIME, repeatTime);
        BundleUtils.setB(bundle, NotificationConstants.ONGOING, ongoing);
        BundleUtils.setB(bundle, NotificationConstants.ALLOW_WHILE_IDLE, allowWhileIdle);
        BundleUtils.setB(bundle, NotificationConstants.DONT_NOTIFY_IN_FOREGROUND, dontNotifyInForeground);
        BundleUtils.set(bundle, NotificationConstants.DATA, data);
        return bundle;
    }

    public JSONObject toJson() {

        JSONObject json = new JSONObject();
        try {
            json.put(NotificationConstants.ID, id);
            json.put(NotificationConstants.MESSAGE, message);
            json.put(NotificationConstants.FIRE_DATE, fireDate);
            json.put(NotificationConstants.TITLE, title);
            json.put(NotificationConstants.TICKER, ticker);
            json.put(NotificationConstants.SHOW_WHEN, showWhen);
            json.put(NotificationConstants.AUTO_CANCEL, autoCancel);
            json.put(NotificationConstants.LARGE_ICON, largeIcon);
            json.put(NotificationConstants.LARGE_ICON_URL, largeIconUrl);
            json.put(NotificationConstants.SMALL_ICON, smallIcon);
            json.put(NotificationConstants.BIG_TEXT, bigText);
            json.put(NotificationConstants.BIG_PICTURE_URL, bigPictureUrl);
            json.put(NotificationConstants.SUB_TEXT, subText);
            json.put(NotificationConstants.SHORTCUT_ID, shortcutId);
            json.put(NotificationConstants.NUMBER, number);
            json.put(NotificationConstants.CHANNEL_ID, channelId);
            json.put(NotificationConstants.CHANNEL_NAME, channelName);
            json.put(NotificationConstants.CHANNEL_DESCRIPTION, channelDescription);
            json.put(NotificationConstants.SOUND, sound);
            json.put(NotificationConstants.COLOR, color);
            json.put(NotificationConstants.GROUP, group);
            json.put(NotificationConstants.GROUP_SUMMARY, groupSummary);
            json.put(NotificationConstants.PLAY_SOUND, playSound);
            json.put(NotificationConstants.VIBRATE, vibrate);
            json.put(NotificationConstants.VIBRATE_DURATION, vibrateDuration);
            json.put(NotificationConstants.ACTIONS, actions);
            json.put(NotificationConstants.INVOKE_APP, invokeApp);
            json.put(NotificationConstants.TAG, tag);
            json.put(NotificationConstants.REPEAT_TYPE, repeatType);
            json.put(NotificationConstants.REPEAT_TIME, repeatTime);
            json.put(NotificationConstants.ONGOING, ongoing);
            json.put(NotificationConstants.ALLOW_WHILE_IDLE, allowWhileIdle);
            json.put(NotificationConstants.DONT_NOTIFY_IN_FOREGROUND, dontNotifyInForeground);
            json.put(NotificationConstants.DATA, data);
        } catch (JSONException e) {
            Log.e("NotificationAttributes", ResultCode.ERROR, e);
            return new JSONObject();
        }
        return json;
    }

    public String getId() {

        return id;
    }

    public String getSound() {

        return sound;
    }

    public String getMessage() {

        return message;
    }

    public String getTitle() {

        return title;
    }

    public String getData() {
        return data;
    }

    public String getNumber() {

        return number;
    }

    public String getRepeatType() {

        return repeatType;
    }

    public double getFireDate() {

        return fireDate;
    }

    public String getTicker() {

        return ticker;
    }

    public boolean isShowWhen() {

        return showWhen;
    }

    public boolean isAutoCancel() {

        return autoCancel;
    }

    public String getLargeIcon() {

        return largeIcon;
    }

    public String getLargeIconUrl() {

        return largeIconUrl;
    }

    public String getSmallIcon() {

        return smallIcon;
    }

    public String getBigText() {

        return bigText;
    }

    public String getSubText() {

        return subText;
    }

    public String getBigPictureUrl() {

        return bigPictureUrl;
    }

    public String getShortcutId() {

        return shortcutId;
    }

    public String getChannelId() {

        return channelId;
    }

    public String getChannelName() {

        return channelName;
    }

    public String getChannelDescription() {

        return channelDescription;
    }

    public String getColor() {

        return color;
    }

    public String getGroup() {

        return group;
    }

    public boolean isGroupSummary() {

        return groupSummary;
    }

    public boolean isPlaySound() {

        return playSound;
    }

    public boolean isVibrate() {

        return vibrate;
    }

    public double getVibrateDuration() {

        return vibrateDuration;
    }

    public String getActions() {

        return actions;
    }

    public boolean isInvokeApp() {

        return invokeApp;
    }

    public String getTag() {

        return tag;
    }

    public double getRepeatTime() {

        return repeatTime;
    }

    public boolean isOngoing() {

        return ongoing;
    }

    public boolean isAllowWhileIdle() {

        return allowWhileIdle;
    }

    public boolean isDontNotifyInForeground() {

        return dontNotifyInForeground;
    }

}
