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

package com.huawei.hms.rn.map.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.ArrayMap;
import android.util.Log;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.MapBuilder;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.Projection;
import com.huawei.hms.maps.UiSettings;
import com.huawei.hms.maps.model.BitmapDescriptor;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.ButtCap;
import com.huawei.hms.maps.model.CameraPosition;
import com.huawei.hms.maps.model.Cap;
import com.huawei.hms.maps.model.CustomCap;
import com.huawei.hms.maps.model.Dash;
import com.huawei.hms.maps.model.Dot;
import com.huawei.hms.maps.model.Gap;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.LatLngBounds;
import com.huawei.hms.maps.model.PatternItem;
import com.huawei.hms.maps.model.PointOfInterest;
import com.huawei.hms.maps.model.RoundCap;
import com.huawei.hms.maps.model.SquareCap;
import com.huawei.hms.maps.model.Tile;
import com.huawei.hms.maps.model.TileProvider;
import com.huawei.hms.maps.model.UrlTileProvider;
import com.huawei.hms.maps.model.VisibleRegion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReactUtils {
    private static final String TAG = ReactUtils.class.getSimpleName();

    public interface NamedEvent {
        /**
         * Gets name of the event
         *
         * @return String of name of the event
         */
        String getName();
    }

    public interface NamedCommand {
        /**
         * Gets name of the command
         *
         * @return String of name of the command
         */
        String getName();
    }

    public interface Mapper<T, R> {
        /**
         * Used to map classes
         *
         * @param in mapped from
         * @return mapped to
         */
        R map(T in);
    }

    public static <R> List<R> mapReadableArray(ReadableArray array, Mapper<ReadableMap, R> mapper) {
        List<R> list = new ArrayList<>();
        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                list.add(mapper.map(array.getMap(i)));
            }
        }
        return list;
    }

    public static <R> List<R> mapDoubleReadableArray(ReadableArray array, Mapper<ReadableArray, R> mapper) {
        List<R> list = new ArrayList<>();
        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                list.add(mapper.map(array.getArray(i)));
            }
        }
        return list;
    }

    public static <T> WritableArray mapList(List<T> list, Mapper<T, WritableMap> mapper) {
        WritableArray array = new WritableNativeArray();
        if (list != null) {
            for (T item : list) {
                array.pushMap(mapper.map(item));
            }
        }
        return array;
    }

    public static boolean hasValidKey(ReadableMap rm, String key, ReadableType type) {
        return rm.hasKey(key) && rm.getType(key) == type;
    }

    public static boolean hasValidElement(ReadableArray ra, int index, ReadableType type) {
        return !ra.isNull(index) && ra.getType(index) == type;
    }

    public static LatLng getLatLngFromReadableMap(ReadableMap rm) {
        if (rm != null && hasValidKey(rm, "latitude", ReadableType.Number)
                && hasValidKey(rm, "longitude", ReadableType.Number)) {
            return new LatLng(rm.getDouble("latitude"), rm.getDouble("longitude"));
        }
        return null;
    }

    public static WritableMap getWritableMapFromLatLng(LatLng obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putDouble("latitude", obj.latitude);
        wm.putDouble("longitude", obj.longitude);
        return wm;
    }

    public static WritableMap getWritableMapFromUiSettings(UiSettings obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putBoolean("isCompassEnabled", obj.isCompassEnabled());
        wm.putBoolean("isMyLocationButtonEnabled", obj.isMyLocationButtonEnabled());
        wm.putBoolean("isRotateGesturesEnabled", obj.isRotateGesturesEnabled());
        wm.putBoolean("isScrollGesturesEnabled", obj.isScrollGesturesEnabled());
        wm.putBoolean("isScrollGesturesEnabledDuringRotateOrZoom", obj.isScrollGesturesEnabledDuringRotateOrZoom());
        wm.putBoolean("isTiltGesturesEnabled", obj.isTiltGesturesEnabled());
        wm.putBoolean("isZoomControlsEnabled", obj.isZoomControlsEnabled());
        wm.putBoolean("isZoomGesturesEnabled", obj.isZoomGesturesEnabled());
        return wm;
    }

    public static WritableMap getWritableMapFromHuaweiMap(HuaweiMap obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putMap("cameraPosition", getWritableMapFromCameraPosition(obj.getCameraPosition()));
        wm.putInt("mapType", obj.getMapType());
        wm.putDouble("maxZoomLevel", obj.getMaxZoomLevel());
        wm.putDouble("minZoomLevel", obj.getMinZoomLevel());
        wm.putMap("visibleRegion", getWritableMapFromVisibleRegion(obj.getProjection().getVisibleRegion()));
        wm.putMap("uiSettings", getWritableMapFromUiSettings(obj.getUiSettings()));
        wm.putBoolean("isBuildingsEnabled", obj.isBuildingsEnabled());
        wm.putBoolean("isMyLocationEnabled", obj.isMyLocationEnabled());
        return wm;
    }

    public static CameraPosition getCameraPositionFromReadableMap(ReadableMap rm) {
        CameraPosition.Builder cameraPositionBuilder = new CameraPosition.Builder();
        if (rm != null && hasValidKey(rm, "target", ReadableType.Map)) {
            cameraPositionBuilder.target(getLatLngFromReadableMap(rm.getMap("target")));
            if (hasValidKey(rm, "zoom", ReadableType.Number)) {
                cameraPositionBuilder.zoom((float) rm.getDouble("zoom"));
            }
            if (hasValidKey(rm, "bearing", ReadableType.Number)) {
                cameraPositionBuilder.bearing((float) rm.getDouble("bearing"));
            }
            if (hasValidKey(rm, "tilt", ReadableType.Number)) {
                cameraPositionBuilder.tilt((float) rm.getDouble("tilt"));
            }
        }
        return cameraPositionBuilder.build();
    }

    public static WritableMap getWritableMapFromCameraPosition(CameraPosition obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putMap("target", getWritableMapFromLatLng(obj.target));
        wm.putDouble("zoom", obj.zoom);
        wm.putDouble("tilt", obj.tilt);
        wm.putDouble("bearing", obj.bearing);
        return wm;
    }

    public static LatLngBounds getLatLngBoundsFromReadableArray(ReadableArray rm) {
        LatLngBounds.Builder latLngBoundsBuilder = new LatLngBounds.Builder();
        if (rm != null) {
            List<LatLng> listLatLng = getLatLngListFromReadableArray(rm);
            for (LatLng latLng : listLatLng) {
                latLngBoundsBuilder.include(latLng);
            }
        }
        return latLngBoundsBuilder.build();
    }

    public static WritableMap getWritableMapFromLatLngBounds(LatLngBounds obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putMap("northeast", getWritableMapFromLatLng(obj.northeast));
        wm.putMap("southwest", getWritableMapFromLatLng(obj.southwest));
        wm.putMap("center", getWritableMapFromLatLng(obj.getCenter()));
        return wm;
    }

    public static WritableMap getWritableMapFromPoint(Point obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putInt("x", obj.x);
        wm.putInt("y", obj.y);
        return wm;
    }

    public static Point getPointFromReadableMap(ReadableMap rm) {
        if (rm != null && hasValidKey(rm, "x", ReadableType.Number)
                && hasValidKey(rm, "y", ReadableType.Number)) {
            return new Point(rm.getInt("x"), rm.getInt("y"));
        }
        return null;
    }

    public static WritableMap getWritableMapPointOfInterest(PointOfInterest obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putMap("latLng", getWritableMapFromLatLng(obj.latLng));
        wm.putString("name", obj.name);
        wm.putString("placeId", obj.placeId);
        return wm;
    }

    public static WritableMap getWritableMapFromProjectionOnLatLng(Projection objProjection, LatLng objLatLng) {
        WritableMap wm = new WritableNativeMap();
        if (objProjection == null || objLatLng == null) {
            return wm;
        }
        wm.putMap("point", getWritableMapFromPoint(objProjection.toScreenLocation(objLatLng)));
        wm.putMap("coordinate", getWritableMapFromLatLng(objLatLng));
        wm.putMap("visibleRegion", getWritableMapFromVisibleRegion(objProjection.getVisibleRegion()));
        return wm;
    }

    public static WritableMap getWritableMapFromVisibleRegion(VisibleRegion obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putMap("farLeft", getWritableMapFromLatLng(obj.farLeft));
        wm.putMap("farRight", getWritableMapFromLatLng(obj.farRight));
        wm.putMap("nearLeft", getWritableMapFromLatLng(obj.nearLeft));
        wm.putMap("nearRight", getWritableMapFromLatLng(obj.nearRight));
        wm.putMap("latLngBounds", getWritableMapFromLatLngBounds(obj.latLngBounds));
        return wm;
    }

    public static List<LatLng> getLatLngListFromReadableArray(ReadableArray ra) {
        return mapReadableArray(ra, ReactUtils::getLatLngFromReadableMap);
    }

    public static List<List<LatLng>> getListOfLatLngListFromReadableArray(ReadableArray ra) {
        return mapDoubleReadableArray(ra, ReactUtils::getLatLngListFromReadableArray);
    }

    public static BitmapDescriptor getBitmapDescriptorFromReadableMap(ReadableMap rm) {
        if (rm != null) {
            if (hasValidKey(rm, "hue", ReadableType.Number)) {
                return BitmapDescriptorFactory.defaultMarker((float) rm.getDouble("hue"));
            } else if (hasValidKey(rm, "asset", ReadableType.String)) {
                return BitmapDescriptorFactory.fromAsset(rm.getString("asset"));
            } else if (hasValidKey(rm, "file", ReadableType.String)) {
                return BitmapDescriptorFactory.fromFile(rm.getString("file"));
            } else if (hasValidKey(rm, "path", ReadableType.String)) {
                return BitmapDescriptorFactory.fromPath(rm.getString("path"));
            }
        }
        return BitmapDescriptorFactory.defaultMarker();
    }

    public static PatternItem getPatternItemFromReadableMap(ReadableMap rm) {
        PatternItem defaultPatternItem = new Dot();
        if (rm == null) {
            return defaultPatternItem;
        }
        int type = -1;
        float length = 0;
        if (hasValidKey(rm, "type", ReadableType.Number)) {
            type = rm.getInt("type");
            if (hasValidKey(rm, "length", ReadableType.Number)) {
                length = (float) rm.getDouble("length");
            }
        }
        switch (type) {
            case PatternItem.TYPE_DASH:
                return new Dash(length);
            case PatternItem.TYPE_DOT:
                return new Dot();
            case PatternItem.TYPE_GAP:
                return new Gap(length);
            default:
                return defaultPatternItem;
        }
    }

    public static Cap getCapFromReadableMap(ReadableMap rm) {
        Cap defaultCap = new ButtCap();
        if (rm == null) {
            return defaultCap;
        }
        int type = -1;
        if (hasValidKey(rm, "type", ReadableType.Number)) {
            type = rm.getInt("type");
        }
        switch (type) {
            case Cap.TYPE_BUTT_CAP:
                return new ButtCap();
            case Cap.TYPE_SQUARE_CAP:
                return new SquareCap();
            case Cap.TYPE_ROUND_CAP:
                return new RoundCap();
            case Cap.TYPE_CUSTOM_CAP:
                BitmapDescriptor bitmapDescriptor = getBitmapDescriptorFromReadableMap(rm);
                if (hasValidKey(rm, "refWidth", ReadableType.Number)) {
                    return new CustomCap(bitmapDescriptor, (float) rm.getDouble("refWidth"));
                }
                return new CustomCap(bitmapDescriptor);
            default:
                return defaultCap;
        }
    }

    public static List<PatternItem> getPatternItemListFromReadableArray(ReadableArray ra) {
        return mapReadableArray(ra, ReactUtils::getPatternItemFromReadableMap);
    }

    public static TileProvider getTileProviderFromReadableMap(ReadableMap rm) {
        final int defaultWidth = 256;
        final int defaultHeight = 256;
        if (rm != null) {
            if (hasValidKey(rm, "url", ReadableType.String)) {
                int width = defaultWidth;
                int height = defaultHeight;
                String urlBeforeFormat = rm.getString("url");
                if (hasValidKey(rm, "width", ReadableType.Number)) {
                    width = rm.getInt("width");
                }
                if (hasValidKey(rm, "height", ReadableType.Number)) {
                    height = rm.getInt("height");
                }
                if (urlBeforeFormat != null) {
                    return new UrlTileProvider(width, height) {
                        @Override
                        public URL getTileUrl(int x, int y, int zoom) {
                            try {
                                return new URL(urlBeforeFormat
                                        .replace("{x}", String.valueOf(x))
                                        .replace("{y}", String.valueOf(y))
                                        .replace("{z}", String.valueOf(zoom)));
                            } catch (MalformedURLException e) {
                                Log.w(TAG, e.getMessage());
                                return null;
                            }
                        }
                    };
                }
            }
        }
        return null;
    }

    public static Map<String, Object> getExportedCustomDirectEventTypeConstantsFromEvents(NamedEvent[] eventList) {
        Map<String, Object> obj = new ArrayMap<>();
        for (NamedEvent event : eventList) {
            obj.put(event.getName(), MapBuilder.of("registrationName", event.getName()));
        }
        return obj;
    }

    public static NamedCommand getCommand(String commandId, NamedCommand[] commands) {
        for (NamedCommand command : commands) {
            if (command.getName().equals(commandId)) {
                return command;
            }
        }
        return null;
    }
}
