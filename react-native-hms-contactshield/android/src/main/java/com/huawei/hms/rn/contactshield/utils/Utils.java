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

package com.huawei.hms.rn.contactshield.utils;

import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.huawei.hms.contactshield.ContactDetail;
import com.huawei.hms.contactshield.ContactSketch;
import com.huawei.hms.contactshield.ContactWindow;
import com.huawei.hms.contactshield.DiagnosisConfiguration;
import com.huawei.hms.contactshield.PeriodicKey;
import com.huawei.hms.contactshield.ScanInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {

    public static WritableArray getIntArray(int[] i) {
        WritableArray wa = new WritableNativeArray();
        for (int intArray : i) {
            wa.pushInt(intArray);
        }
        return wa;
    }

    public static WritableArray getByteArray(byte[] b) {
        WritableArray wa = new WritableNativeArray();
        for (byte byteArray : b) {
            wa.pushInt(byteArray);
        }
        return wa;
    }

    public static WritableMap fromContactSketchDataToMap(ContactSketch contactSketch) {
        WritableMap wm = new WritableNativeMap();
        if (contactSketch == null) {
            return null;
        }

        wm.putArray("attenuationDurations", getIntArray(contactSketch.getAttenuationDurations()));
        wm.putInt("daysSinceLastHit", contactSketch.getDaysSinceLastHit());
        wm.putInt("maxRiskValue", contactSketch.getMaxRiskValue());
        wm.putInt("numberOfHits", contactSketch.getNumberOfHits());
        wm.putInt("summationRiskValue", contactSketch.getSummationRiskValue());
        return wm;
    }

    public static WritableArray fromContactDetailsListToMap(List<ContactDetail> contactDetailList) {
        WritableArray array = new WritableNativeArray();
        for (ContactDetail contactDetail : contactDetailList) {
            array.pushMap(fromContactDetailsDataToMap(contactDetail));
        }
        return array;
    }

    public static WritableMap fromContactDetailsDataToMap(ContactDetail contactDetail) {
        WritableMap wm = new WritableNativeMap();
        if (contactDetail == null) {
            return null;
        }
        wm.putInt("attenuationRiskValue", contactDetail.getAttenuationRiskValue());
        wm.putInt("durationMinutes", contactDetail.getDurationMinutes());
        wm.putInt("totalRiskValue", contactDetail.getTotalRiskValue());
        wm.putInt("totalRiskLevel", contactDetail.getInitialRiskLevel());
        wm.putArray("attenuationDurations", getIntArray(contactDetail.getAttenuationDurations()));
        wm.putDouble("dayNumber", contactDetail.getDayNumber());
        return wm;
    }

    public static WritableArray fromPeriodicKeyListToMap(List<PeriodicKey> periodicKeyList) {
        if (periodicKeyList == null) {
            return null;
        }
        WritableArray array = new WritableNativeArray();
        for (PeriodicKey periodicKey : periodicKeyList) {
            array.pushMap(fromPeriodicKeyToMap(periodicKey));
        }
        return array;
    }

    public static WritableMap fromPeriodicKeyToMap(PeriodicKey periodicKey) {
        WritableMap wm = new WritableNativeMap();
        if (periodicKey == null) {
            return null;
        }
        wm.putArray("content", getByteArray(periodicKey.getContent()));
        wm.putInt("initialRiskLevel", periodicKey.getInitialRiskLevel());
        wm.putDouble("periodicKeyLifeTime", periodicKey.getPeriodicKeyLifeTime());
        wm.putDouble("periodicKeyValidTime", periodicKey.getPeriodicKeyValidTime());
        wm.putInt("reportType", periodicKey.getReportType());
        return wm;
    }


    public static WritableArray fromContactWindowListToMap(List<ContactWindow> contactWindowList) {
        if (contactWindowList == null) {
            return null;
        }
        WritableArray array = new WritableNativeArray();
        for (ContactWindow contactWindow : contactWindowList) {
            array.pushMap(fromContactWindowToMap(contactWindow));
        }
        return array;
    }

    public static WritableMap fromContactWindowToMap(ContactWindow contactWindow) {
        WritableMap wm = new WritableNativeMap();
        WritableArray scanMap = new WritableNativeArray();
        if (contactWindow == null) {
            return null;
        }

        wm.putInt("reportType", contactWindow.getReportType());
        wm.putDouble("dateMillis", contactWindow.getDateMillis());

        for (final ScanInfo scanInfo : contactWindow.getScanInfos()) {
            scanMap.pushMap(fromScanInfoToMap(scanInfo));
        }
        wm.putArray("scanInfos", scanMap);
        return wm;
    }

    public static WritableMap fromScanInfoToMap(ScanInfo scanInfo) {
        WritableMap wm = new WritableNativeMap();
        if (scanInfo == null) {
            return null;
        }

        wm.putInt("averageAttenuation", scanInfo.getAverageAttenuation());
        wm.putInt("minimumAttenuation", scanInfo.getMinimumAttenuation());
        wm.putInt("secondsSinceLastScan", scanInfo.getSecondsSinceLastScan());

        return wm;
    }

    public static WritableMap fromDiagnosisConfigurationToMap(DiagnosisConfiguration diagnosisConfiguration){
        WritableMap wm = new WritableNativeMap();

        wm.putArray("attenuationDurationThresholds", getIntArray(diagnosisConfiguration.getAttenuationDurationThresholds()));
        wm.putArray("attenuationRiskValues", getIntArray(diagnosisConfiguration.getAttenuationRiskValues()));
        wm.putArray("daysAfterContactedRiskValues", getIntArray(diagnosisConfiguration.getDaysAfterContactedRiskValues()));
        wm.putArray("durationRiskValues", getIntArray(diagnosisConfiguration.getDurationRiskValues()));
        wm.putArray("initialRiskLevelRiskValues", getIntArray(diagnosisConfiguration.getInitialRiskLevelRiskValues()));
        wm.putInt("minimumRiskValueThreshold", diagnosisConfiguration.getMinimumRiskValueThreshold());

        return wm;
    }
}
