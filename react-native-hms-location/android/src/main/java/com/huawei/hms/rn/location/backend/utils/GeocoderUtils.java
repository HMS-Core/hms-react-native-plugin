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

package com.huawei.hms.rn.location.backend.utils;

import static com.huawei.hms.rn.location.backend.utils.PlatformUtils.mapperWrapper;

import com.huawei.hms.location.GetFromLocationNameRequest;
import com.huawei.hms.location.GetFromLocationRequest;
import com.huawei.hms.rn.location.backend.interfaces.Mapper;

import org.json.JSONObject;

import java.util.Locale;

public class GeocoderUtils {

    public static final Mapper<JSONObject, GetFromLocationRequest> FROM_JSON_OBJECT_TO_GET_FROM_LOCATION_REQUEST
        = mapperWrapper(
        (JSONObject jo) -> new GetFromLocationRequest(jo.getDouble("latitude"), jo.getDouble("longitude"),
            jo.getInt("maxResults")));

    public static final Mapper<JSONObject, GetFromLocationNameRequest>
        FROM_JSON_OBJECT_TO_GET_FROM_LOCATION_NAME_REQUEST = mapperWrapper((JSONObject jo) -> {
        GetFromLocationNameRequest request = new GetFromLocationNameRequest(jo.getString("locationName"),
            jo.optInt("maxResults"));
        request.setLowerLeftLatitude(jo.optDouble("lowerLeftLatitude", 0.0));
        request.setLowerLeftLongitude(jo.optDouble("lowerLeftLongitude", 0.0));
        request.setUpperRightLatitude(jo.optDouble("upperRightLatitude", 0.0));
        request.setUpperRightLongitude(jo.optDouble("upperRightLongitude", 0.0));
        return request;
    });

    public static final Mapper<JSONObject, Locale> FROM_JSON_OBJECT_TO_LOCALE = mapperWrapper(
        (JSONObject jo) -> new Locale(jo.getString("language"), jo.optString("country", ""),
            jo.optString("variant", "")));
}
