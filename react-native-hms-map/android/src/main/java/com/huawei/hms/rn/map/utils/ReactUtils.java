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

package com.huawei.hms.rn.map.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.util.ArrayMap;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.MapBuilder;

import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.Projection;
import com.huawei.hms.maps.UiSettings;
import com.huawei.hms.maps.common.util.CoordinateConverter;
import com.huawei.hms.maps.model.BitmapDescriptor;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.ButtCap;
import com.huawei.hms.maps.model.CameraPosition;
import com.huawei.hms.maps.model.Cap;
import com.huawei.hms.maps.model.Circle;
import com.huawei.hms.maps.model.CircleOptions;
import com.huawei.hms.maps.model.CustomCap;
import com.huawei.hms.maps.model.Dash;
import com.huawei.hms.maps.model.Dot;
import com.huawei.hms.maps.model.Gap;
import com.huawei.hms.maps.model.GroundOverlay;
import com.huawei.hms.maps.model.GroundOverlayOptions;
import com.huawei.hms.maps.model.HeatMap;
import com.huawei.hms.maps.model.HeatMapOptions;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.LatLngBounds;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.maps.model.PatternItem;
import com.huawei.hms.maps.model.PointOfInterest;
import com.huawei.hms.maps.model.Polygon;
import com.huawei.hms.maps.model.PolygonOptions;
import com.huawei.hms.maps.model.Polyline;
import com.huawei.hms.maps.model.PolylineOptions;
import com.huawei.hms.maps.model.RoundCap;
import com.huawei.hms.maps.model.SquareCap;
import com.huawei.hms.maps.model.Tile;
import com.huawei.hms.maps.model.TileOverlay;
import com.huawei.hms.maps.model.TileOverlayOptions;
import com.huawei.hms.maps.model.TileProvider;
import com.huawei.hms.maps.model.UrlTileProvider;
import com.huawei.hms.maps.model.VisibleRegion;
import com.huawei.hms.maps.model.animation.AlphaAnimation;
import com.huawei.hms.maps.model.animation.Animation;
import com.huawei.hms.maps.model.animation.RotateAnimation;
import com.huawei.hms.maps.model.animation.ScaleAnimation;
import com.huawei.hms.maps.model.animation.TranslateAnimation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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
        if (rm != null && hasValidKey(rm, "latitude", ReadableType.Number) && hasValidKey(rm, "longitude",
            ReadableType.Number)) {
            if (hasValidKey(rm, "isGCJ02", ReadableType.Boolean) && rm.getBoolean("isGCJ02")) {
                return new CoordinateConverter().convert(
                    new LatLng(rm.getDouble("latitude"), rm.getDouble("longitude")));
            }
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

    public static int getColorFromRgbaArray(ReadableArray array) {
        return Color.argb(array.getInt(0), array.getInt(1), array.getInt(2), array.getInt(3));
    }

    public static Map<Float, Float> toFloatMap(ReadableMap readableMap) {
        Map<Float, Float> map = new HashMap<>();
        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();

        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();

            map.put(Float.parseFloat(key), ((Double) readableMap.getDouble(key)).floatValue());
        }
        return map;
    }

    public static Map<Float, Integer> toFloatIntegerMap(ReadableMap readableMap) {
        Map<Float, Integer> map = new HashMap<>();
        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();

        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();

            map.put(Float.parseFloat(key), getColorFromRgbaArray(readableMap.getArray(key)));
        }
        return map;
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

    public static WritableMap getWritableMapFromCircle(Circle obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putMap("center", getWritableMapFromLatLng(obj.getCenter()));
        wm.putInt("fillColor", obj.getFillColor());
        wm.putString("id", obj.getId());
        wm.putDouble("radius", obj.getRadius());
        wm.putInt("strokeColor", obj.getStrokeColor());
        wm.putArray("strokePattern", mapList(obj.getStrokePattern(), ReactUtils::getWritableMapPatternItem));
        wm.putDouble("strokeWidth", obj.getStrokeWidth());
        wm.putDouble("zIndex", obj.getZIndex());
        wm.putBoolean("isClickable", obj.isClickable());
        wm.putBoolean("isVisible", obj.isVisible());
        return wm;
    }

    public static WritableMap getWritableMapFromCircleOptions(CircleOptions obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putMap("center", getWritableMapFromLatLng(obj.getCenter()));
        wm.putInt("fillColor", obj.getFillColor());
        wm.putDouble("radius", obj.getRadius());
        wm.putInt("strokeColor", obj.getStrokeColor());
        wm.putArray("strokePattern", mapList(obj.getStrokePattern(), ReactUtils::getWritableMapPatternItem));
        wm.putDouble("strokeWidth", obj.getStrokeWidth());
        wm.putDouble("zIndex", obj.getZIndex());
        wm.putBoolean("isClickable", obj.isClickable());
        wm.putBoolean("isVisible", obj.isVisible());
        return wm;
    }

    public static WritableMap getWritableMapFromGroundOverlay(GroundOverlay obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putDouble("bearing", obj.getBearing());
        wm.putMap("bounds", getWritableMapFromLatLngBounds(obj.getBounds()));
        wm.putDouble("height", obj.getHeight());
        wm.putString("id", obj.getId());
        wm.putMap("position", getWritableMapFromLatLng(obj.getPosition()));
        wm.putDouble("transparency", obj.getTransparency());
        wm.putDouble("width", obj.getWidth());
        wm.putDouble("zIndex", obj.getZIndex());
        wm.putBoolean("isClickable", obj.isClickable());
        wm.putBoolean("isVisible", obj.isVisible());
        return wm;
    }

    public static WritableMap getWritableMapFromGroundOverlayOptions(GroundOverlayOptions obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putDouble("anchorU", obj.getAnchorU());
        wm.putDouble("anchorV", obj.getAnchorV());
        wm.putDouble("bearing", obj.getBearing());
        wm.putMap("bounds", getWritableMapFromLatLngBounds(obj.getBounds()));
        wm.putDouble("height", obj.getHeight());
        wm.putMap("location", getWritableMapFromLatLng(obj.getLocation()));
        wm.putDouble("transparency", obj.getTransparency());
        wm.putDouble("width", obj.getWidth());
        wm.putDouble("zIndex", obj.getZIndex());
        wm.putBoolean("isClickable", obj.isClickable());
        wm.putBoolean("isVisible", obj.isVisible());
        return wm;
    }

    public static WritableMap getWritableMapFromMarker(Marker obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putDouble("alpha", obj.getAlpha());
        wm.putString("id", obj.getId());
        wm.putMap("position", getWritableMapFromLatLng(obj.getPosition()));
        wm.putDouble("rotation", obj.getRotation());
        wm.putString("snippet", obj.getSnippet());
        wm.putString("title", obj.getTitle());
        wm.putDouble("zIndex", obj.getZIndex());
        wm.putBoolean("isClusterable", obj.isClusterable());
        wm.putBoolean("isDraggable", obj.isDraggable());
        wm.putBoolean("isFlat", obj.isFlat());
        wm.putBoolean("isInfoWindowShown", obj.isInfoWindowShown());
        wm.putBoolean("isVisible", obj.isVisible());
        return wm;
    }

    public static WritableMap getWritableMapFromMarkerOptions(MarkerOptions obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putDouble("alpha", obj.getAlpha());
        wm.putDouble("markerAnchorU", obj.getMarkerAnchorU());
        wm.putDouble("markerAnchorV", obj.getMarkerAnchorV());
        wm.putDouble("infoWindowAnchorU", obj.getInfoWindowAnchorU());
        wm.putDouble("infoWindowAnchorV", obj.getInfoWindowAnchorV());
        wm.putMap("position", getWritableMapFromLatLng(obj.getPosition()));
        wm.putDouble("rotation", obj.getRotation());
        wm.putString("snippet", obj.getSnippet());
        wm.putString("title", obj.getTitle());
        wm.putDouble("zIndex", obj.getZIndex());
        wm.putBoolean("isClusterable", obj.ismClusterable());
        wm.putBoolean("isDraggable", obj.isDraggable());
        wm.putBoolean("isFlat", obj.isFlat());
        wm.putBoolean("isVisible", obj.isVisible());
        return wm;
    }

    public static WritableMap getWritableMapFromPolygon(Polygon obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }

        wm.putArray("points", getWritableArrayFromLatLngList(obj.getPoints()));
        wm.putArray("holes", getWritableArrayFromListOfLatLngList(obj.getHoles()));
        wm.putInt("fillColor", obj.getFillColor());
        wm.putString("id", obj.getId());
        wm.putInt("strokeColor", obj.getStrokeColor());
        wm.putArray("strokePattern", mapList(obj.getStrokePattern(), ReactUtils::getWritableMapPatternItem));
        wm.putDouble("strokeWidth", obj.getStrokeWidth());
        wm.putDouble("zIndex", obj.getZIndex());
        wm.putInt("strokeJointType", obj.getStrokeJointType());
        wm.putBoolean("isClickable", obj.isClickable());
        wm.putBoolean("isVisible", obj.isVisible());
        wm.putBoolean("isGeodesic", obj.isGeodesic());
        return wm;
    }

    private static WritableArray getWritableArrayFromListOfLatLngList(List<List<LatLng>> holes) {
        WritableArray holesArray = new WritableNativeArray();
        if (holes == null) {
            return holesArray;
        }
        for (List<LatLng> hole : holes) {
            holesArray.pushArray(getWritableArrayFromLatLngList(hole));
        }

        return holesArray;
    }

    private static WritableArray getWritableArrayFromLatLngList(List<LatLng> points) {
        WritableArray pointsArray = new WritableNativeArray();
        if (points == null) {
            return pointsArray;
        }

        for (LatLng latLng : points) {
            WritableMap wm = getWritableMapFromLatLng(latLng);
            pointsArray.pushMap(wm);
        }

        return pointsArray;
    }

    public static WritableMap getWritableMapFromPolygonOptions(PolygonOptions obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putArray("points", getWritableArrayFromLatLngList(obj.getPoints()));
        wm.putArray("holes", getWritableArrayFromListOfLatLngList(obj.getHoles()));
        wm.putInt("fillColor", obj.getFillColor());
        wm.putInt("strokeColor", obj.getStrokeColor());
        wm.putArray("strokePattern", mapList(obj.getStrokePattern(), ReactUtils::getWritableMapPatternItem));
        wm.putDouble("strokeWidth", obj.getStrokeWidth());
        wm.putDouble("zIndex", obj.getZIndex());
        wm.putBoolean("isClickable", obj.isClickable());
        wm.putBoolean("isVisible", obj.isVisible());
        wm.putBoolean("isGeodesic", obj.isGeodesic());
        wm.putInt("strokeJointType", obj.getStrokeJointType());
        return wm;
    }

    public static WritableMap getWritableMapFromPolyline(Polyline obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putArray("points", getWritableArrayFromLatLngList(obj.getPoints()));
        wm.putInt("color", obj.getColor());
        wm.putString("id", obj.getId());
        wm.putArray("pattern", mapList(obj.getPattern(), ReactUtils::getWritableMapPatternItem));
        wm.putDouble("width", obj.getWidth());
        wm.putDouble("zIndex", obj.getZIndex());
        wm.putBoolean("isClickable", obj.isClickable());
        wm.putBoolean("isVisible", obj.isVisible());
        wm.putBoolean("isGeodesic", obj.isGeodesic());
        wm.putInt("jointType", obj.getJointType());
        return wm;
    }

    public static WritableMap getWritableMapFromPolylineOptions(PolylineOptions obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putArray("points", getWritableArrayFromLatLngList(obj.getPoints()));
        wm.putInt("color", obj.getColor());
        wm.putArray("pattern", mapList(obj.getPattern(), ReactUtils::getWritableMapPatternItem));
        wm.putDouble("width", obj.getWidth());
        wm.putDouble("zIndex", obj.getZIndex());
        wm.putBoolean("isClickable", obj.isClickable());
        wm.putBoolean("isVisible", obj.isVisible());
        wm.putBoolean("isGeodesic", obj.isGeodesic());
        wm.putInt("jointType", obj.getJointType());

        return wm;
    }

    public static WritableMap getWritableMapFromTileOverlay(TileOverlay obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putBoolean("fadeIn", obj.getFadeIn());
        wm.putDouble("transparency", obj.getTransparency());
        wm.putString("id", obj.getId());
        wm.putDouble("zIndex", obj.getZIndex());
        wm.putBoolean("isVisible", obj.isVisible());
        return wm;
    }

    public static WritableMap getWritableMapFromTileOverlayOptions(TileOverlayOptions obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putBoolean("fadeIn", obj.getFadeIn());
        wm.putDouble("transparency", obj.getTransparency());
        wm.putDouble("zIndex", obj.getZIndex());
        wm.putBoolean("isVisible", obj.isVisible());
        return wm;
    }

    public static HeatMapOptions.RadiusUnit getRadiusUnitFromString(String radiusUnit) {
        if ("METER".equals(radiusUnit)) {
            return HeatMapOptions.RadiusUnit.METER;
        }
        return HeatMapOptions.RadiusUnit.PIXEL;
    }

    public static WritableMap getWritableMapFromHeatMap(HeatMap obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putString("ID", obj.getId());
        wm.putString("radiusUnit", obj.getRadiusUnit().toString());
        return wm;
    }

    public static WritableMap getWritableMapFromHeatMapOptions(HeatMapOptions obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putMap("color", toWritableMap(obj.getColor()));
        wm.putMap("intensity", toWritableMap(obj.getIntensity()));
        wm.putMap("opacity", toWritableMap(obj.getOpacity()));
        wm.putMap("radius", toWritableMap(obj.getRadius()));
        wm.putString("radiusUnit", obj.getRadiusUnit().toString());
        wm.putString("dataset", obj.getHeatMapData());
        wm.putInt("resourceID", obj.getResourceId());
        wm.putBoolean("isVisible", obj.getVisible());
        return wm;
    }

    public static WritableMap toWritableMap(Map<Float, ?> map) {
        WritableMap writableMap = new WritableNativeMap();
        Iterator iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            Object value = pair.getValue();

            if (value instanceof Float) {
                writableMap.putDouble((pair.getKey().toString()), (Float) value);
            } else if (value instanceof Integer) {
                writableMap.putInt((pair.getKey().toString()), (Integer) value);
            }
            iterator.remove();
        }

        return writableMap;
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
        if (rm != null && hasValidKey(rm, "x", ReadableType.Number) && hasValidKey(rm, "y", ReadableType.Number)) {
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

    public static WritableMap getWritableMapPatternItem(PatternItem obj) {
        WritableMap wm = new WritableNativeMap();
        if (obj == null) {
            return wm;
        }
        wm.putInt("type", obj.type);
        wm.putDouble("length", obj.length);
        return wm;
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
                return getCustomCapFromBitmapDescriptor(bitmapDescriptor,
                    rm.hasKey("refWidth") ? (float) rm.getDouble("refWidth") : null);
            default:
                return defaultCap;
        }
    }

    public static CustomCap getCustomCapFromBitmapDescriptor(BitmapDescriptor bitmapDescriptor, Float refWidth) {
        if (refWidth != null) {
            return new CustomCap(bitmapDescriptor, refWidth);
        }
        return new CustomCap(bitmapDescriptor);
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
                    boolean isZoomSet = hasValidKey(rm, "zoom", ReadableType.Array) && (rm.getArray("zoom").size() > 0);
                    ArrayList<Object> zoomList = isZoomSet ? rm.getArray("zoom").toArrayList() : new ArrayList<>();
                    return new UrlTileProvider(width, height) {
                        @Override
                        public URL getTileUrl(int x, int y, int zoom) {
                            if (isZoomSet && !zoomList.contains((double) zoom)) {
                                return null;
                            }
                            try {
                                return new URL(urlBeforeFormat.replace("{x}", String.valueOf(x))
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

    public static TileProvider getTileProviderFromReadableArray(ReadableArray ra, Context context) {
        final int defaultWidth = 256;
        final int defaultHeight = 256;
        HashMap<List<Integer>, List<Object>> map = new HashMap<>();
        for (int i = 0; i < ra.size(); i++) {
            ReadableMap rm = ra.getMap(i);
            List<Integer> set = Arrays.asList(rm.getInt("x"), rm.getInt("y"), rm.getInt("zoom"));
            map.put(set, Arrays.asList(rm.getString("asset"),
                hasValidKey(rm, "width", ReadableType.Number) ? rm.getInt("width") : defaultWidth,
                hasValidKey(rm, "height", ReadableType.Number) ? rm.getInt("height") : defaultHeight));
        }
        return (x, y, zoom) -> {
            List<Integer> list = Arrays.asList(x, y, zoom);
            if (map.containsKey(list)) {
                String path = (String) map.get(list).get(0);
                int width = (int) map.get(list).get(1);
                int height = (int) map.get(list).get(2);
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open(path));
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    return new Tile(width, height, stream.toByteArray());
                } catch (final OutOfMemoryError | IOException e) {
                    Log.d(TAG, e.getLocalizedMessage());
                }
            }
            return new Tile(defaultWidth, defaultHeight, null);
        };
    }

    public static Map<String, Object> getExportedCustomDirectEventTypeConstantsFromEvents(NamedEvent[] eventList) {
        Map<String, Object> obj = new ArrayMap<>();
        for (NamedEvent event : eventList) {
            obj.put(event.getName(), MapBuilder.of("registrationName", event.getName()));
        }
        return obj;
    }

    public static Map<String, Integer> getCommandsMap(NamedCommand[] commandList) {
        Map<String, Integer> obj = new ArrayMap<>();
        for (int i = 0; i < commandList.length; i++) {
            obj.put(commandList[i].getName(), i);
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

    public static Animation getAnimationFromCommandArgs(ReadableMap map, ReadableMap defaults, String key) {
        Animation animation;
        switch (key) {
            case "alpha": //ALPHA
                float fromAlpha = (float) map.getDouble("fromAlpha");
                float toAlpha = (float) map.getDouble("toAlpha");
                animation = new AlphaAnimation(fromAlpha, toAlpha);
                break;
            case "rotate": //ROTATE
                float fromDegree = (float) map.getDouble("fromDegree");
                float toDegree = (float) map.getDouble("toDegree");
                animation = new RotateAnimation(fromDegree, toDegree);
                break;
            case "scale": //SCALE
                float fromX = (float) map.getDouble("fromX");
                float fromY = (float) map.getDouble("fromY");
                float toX = (float) map.getDouble("toX");
                float toY = (float) map.getDouble("toY");
                animation = new ScaleAnimation(fromX, toX, fromY, toY);
                break;
            case "translate": //TRANSLATE
                LatLng target = new LatLng(map.getDouble("latitude"), map.getDouble("longitude"));
                animation = new TranslateAnimation(target);
                break;
            default:
                animation = null;
                break;
        }
        if (animation != null && map != null) {
            if (map.hasKey("duration")) {
                animation.setDuration(map.getInt("duration"));
            } else if (defaults != null && defaults.hasKey("duration")) {
                animation.setDuration(defaults.getInt("duration"));
            }

            if (map.hasKey("fillMode")) {
                animation.setFillMode(map.getInt("fillMode"));
            } else if (defaults != null && defaults.hasKey("fillMode")) {
                animation.setFillMode(defaults.getInt("fillMode"));
            }

            if (map.hasKey("repeatCount")) {
                animation.setRepeatCount(map.getInt("repeatCount"));
            } else if (defaults != null && defaults.hasKey("repeatCount")) {
                animation.setRepeatCount(defaults.getInt("repeatCount"));
            }

            if (map.hasKey("repeatMode")) {
                animation.setRepeatMode(map.getInt("repeatMode"));
            } else if (defaults != null && defaults.hasKey("repeatMode")) {
                animation.setRepeatMode(defaults.getInt("repeatMode"));
            }

            if (map.hasKey("interpolator")) {
                animation.setInterpolator(getInterpolatorFromInt(map.getInt("interpolator")));
            } else if (defaults != null && defaults.hasKey("interpolator")) {
                animation.setInterpolator(getInterpolatorFromInt(defaults.getInt("interpolator")));
            }
        }
        return animation;

    }

    private static Interpolator getInterpolatorFromInt(int interpolator) {
        switch (interpolator) {
            case 1:
                return new AccelerateInterpolator();
            case 2:
                return new AnticipateInterpolator();
            case 3:
                return new BounceInterpolator();
            case 4:
                return new DecelerateInterpolator();
            case 5:
                return new OvershootInterpolator();
            case 6:
                return new AccelerateDecelerateInterpolator();
            case 7:
                return new FastOutLinearInInterpolator();
            case 8:
                return new FastOutSlowInInterpolator();
            case 9:
                return new LinearOutSlowInInterpolator();
            case 0:
            default:
                return new LinearInterpolator();
        }
    }

    public static WritableMap getWritableMapFromAnimation(Animation animation) {
        WritableMap map = new WritableNativeMap();
        map.putInt("duration", (int) animation.getDuration());
        map.putInt("fillMode", animation.getFillMode());
        map.putInt("repeatCount", animation.getRepeatCount());
        map.putInt("repeatMode", animation.getRepeatMode());
        int interpolator;
        if (animation.getInterpolator() instanceof LinearInterpolator) {
            interpolator = 0;
        } else if (animation.getInterpolator() instanceof AccelerateInterpolator) {
            interpolator = 1;
        } else if (animation.getInterpolator() instanceof AnticipateInterpolator) {
            interpolator = 2;
        } else if (animation.getInterpolator() instanceof BounceInterpolator) {
            interpolator = 3;
        } else if (animation.getInterpolator() instanceof DecelerateInterpolator) {
            interpolator = 4;
        } else if (animation.getInterpolator() instanceof OvershootInterpolator) {
            interpolator = 5;
        } else if (animation.getInterpolator() instanceof AccelerateDecelerateInterpolator) {
            interpolator = 6;
        } else {
            interpolator = 0;
        }

        map.putInt("interpolator", interpolator);

        if (animation instanceof AlphaAnimation) {
            map.putDouble("fromAlpha", ((AlphaAnimation) animation).getFromAlpha());
            map.putDouble("toAlpha", ((AlphaAnimation) animation).getToAlpha());
        } else if (animation instanceof RotateAnimation) {
            map.putDouble("fromDegree", ((RotateAnimation) animation).getFromDegree());
            map.putDouble("toDegree", ((RotateAnimation) animation).getToDegree());
        } else if (animation instanceof ScaleAnimation) {
            map.putDouble("fromX", ((ScaleAnimation) animation).getFromX());
            map.putDouble("fromY", ((ScaleAnimation) animation).getFromY());
            map.putDouble("toX", ((ScaleAnimation) animation).getToX());
            map.putDouble("toY", ((ScaleAnimation) animation).getToY());
        } else if (animation instanceof TranslateAnimation) {
            map.putMap("target", getWritableMapFromLatLng(((TranslateAnimation) animation).getTarget()));
        }

        return map;
    }
}
