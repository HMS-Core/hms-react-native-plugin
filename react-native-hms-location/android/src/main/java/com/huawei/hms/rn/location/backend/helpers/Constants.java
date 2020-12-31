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

package com.huawei.hms.rn.location.backend.helpers;

public class Constants {
    public static final String KEY_CONTENT_TITLE = "contentTitle";
    public static final String KEY_CONTENT_TEXT = "contentText";
    public static final String KEY_DEF_TYPE = "defType";
    public static final String KEY_RESOURCE_NAME = "resourceName";
    public static final String DEFAULT_CONTENT_TITLE = "Location Kit";
    public static final String DEFAULT_CONTENT_TEXT = "Service is running...";
    public static final String DEFAULT_DEF_TYPE = "mipmap";
    public static final String DEFAULT_RESOURCE_NAME = "ic_launcher";

    public enum Event {
        LOCATION("onLocation"),
        ACTIVITY_IDENTIFICATION("onActivityIdentification"),
        ACTIVITY_CONVERSION("onActivityConversion"),
        GEOFENCE("onGeofence");

        private String val;

        Event(String val) {
            this.val = val;
        }

        public String getVal() {
            return this.val;
        }

        @Override
        public String toString() {
            return this.val;
        }
    }
}
