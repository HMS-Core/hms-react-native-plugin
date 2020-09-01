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

package com.huawei.hms.rn.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.view.ReactViewGroup;
import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.HuaweiMapOptions;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.UiSettings;
import com.huawei.hms.maps.common.util.DistanceCalculator;
import com.huawei.hms.maps.model.CameraPosition;
import com.huawei.hms.maps.model.Circle;
import com.huawei.hms.maps.model.GroundOverlay;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.LatLngBounds;
import com.huawei.hms.maps.model.MapStyleOptions;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.PointOfInterest;
import com.huawei.hms.maps.model.Polygon;
import com.huawei.hms.maps.model.Polyline;
import com.huawei.hms.maps.model.TileOverlay;
import com.huawei.hms.rn.map.utils.ReactUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RNHMSMapView extends MapView implements OnMapReadyCallback, LifecycleEventListener,
        HuaweiMap.CancelableCallback, HuaweiMap.OnMapLoadedCallback, HuaweiMap.SnapshotReadyCallback,
        HuaweiMap.InfoWindowAdapter, HuaweiMap.OnCameraIdleListener,
        HuaweiMap.OnCameraMoveCanceledListener, HuaweiMap.OnCameraMoveListener, HuaweiMap.OnCameraMoveStartedListener,
        HuaweiMap.OnCircleClickListener, HuaweiMap.OnGroundOverlayClickListener,
        HuaweiMap.OnInfoWindowClickListener, HuaweiMap.OnInfoWindowCloseListener,
        HuaweiMap.OnInfoWindowLongClickListener,
        HuaweiMap.OnMapClickListener, HuaweiMap.OnMapLongClickListener,
        HuaweiMap.OnMarkerClickListener, HuaweiMap.OnMarkerDragListener,
        HuaweiMap.OnMyLocationButtonClickListener, HuaweiMap.OnMyLocationClickListener,
        HuaweiMap.OnPoiClickListener, HuaweiMap.OnPolygonClickListener, HuaweiMap.OnPolylineClickListener {
    private static final String TAG = RNHMSMapView.class.getSimpleName();
    private final Runnable measureAndLayout = () -> {
        measure(
                MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
        layout(getLeft(), getTop(), getRight(), getBottom());
    };
    private ReactContext mReactContext;
    private LocationManager mLocationManager;
    private HuaweiMap mHuaweiMap;
    private UiSettings mUiSettings;
    private boolean mUseAnimation = false;
    private int mAnimationDuration = 250;
    private boolean mMyLocationEnabled = false;
    private boolean mMyLocationButtonEnabled = true;
    private boolean mCompassEnabled = true;
    private boolean mZoomControlsEnabled = true;
    private Map<Circle, RNHMSCircleView> circleMap = new HashMap<>();
    private Map<Marker, RNHMSMarkerView> markerMap = new HashMap<>();
    private Map<Polygon, RNHMSPolygonView> polygonMap = new HashMap<>();
    private Map<Polyline, RNHMSPolylineView> polylineMap = new HashMap<>();
    private Map<GroundOverlay, RNHMSGroundOverlayView> groundOverlayMap = new HashMap<>();
    private Map<TileOverlay, RNHMSTileOverlayView> tileOverlayMap = new HashMap<>();
    private List<MapLayerView> allMapLayerViews = new ArrayList<>();

    public RNHMSMapView(final Context context, HuaweiMapOptions huaweiMapOptions) {
        super(context, huaweiMapOptions);
        if (context instanceof ReactContext) {
            mReactContext = (ReactContext) context;
        }
        Object systemService = context.getSystemService(Context.LOCATION_SERVICE);
        if (systemService instanceof LocationManager) {
            mLocationManager = (LocationManager) systemService;
        }

        this.onCreate(null);
        this.getMapAsync(this);
        this.requestLayout();
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }

    @Override
    public void onMapReady(HuaweiMap huaweiMap) {
        mHuaweiMap = huaweiMap;
        mUiSettings = mHuaweiMap.getUiSettings();
        mHuaweiMap.setInfoWindowAdapter(this);
        mHuaweiMap.setOnCameraIdleListener(this);
        mHuaweiMap.setOnCameraMoveCanceledListener(this);
        mHuaweiMap.setOnCameraMoveListener(this);
        mHuaweiMap.setOnCameraMoveStartedListener(this);
        mHuaweiMap.setOnCircleClickListener(this);
        mHuaweiMap.setOnGroundOverlayClickListener(this);
        mHuaweiMap.setOnInfoWindowClickListener(this);
        mHuaweiMap.setOnInfoWindowCloseListener(this);
        mHuaweiMap.setOnInfoWindowLongClickListener(this);
        mHuaweiMap.setOnMapClickListener(this);
        mHuaweiMap.setOnMapLoadedCallback(this);
        mHuaweiMap.setOnMapLongClickListener(this);
        mHuaweiMap.setOnMarkerClickListener(this);
        mHuaweiMap.setOnMarkerDragListener(this);
        mHuaweiMap.setOnMyLocationButtonClickListener(this);
        mHuaweiMap.setOnMyLocationClickListener(this);
        mHuaweiMap.setOnPoiClickListener(this);
        mHuaweiMap.setOnPolygonClickListener(this);
        mHuaweiMap.setOnPolylineClickListener(this);
        mReactContext.addLifecycleEventListener(this);

        sendEvent(Manager.Event.MAP_READY, null);
    }

    @Override
    public void onHostResume() {
        setMyLocationEnabled(hasLocationPermission() && mMyLocationEnabled);
    }

    @Override
    public void onHostPause() {
        setMyLocationEnabled(false);
    }

    @Override
    public void onHostDestroy() {
        mReactContext.removeLifecycleEventListener(this);
    }

    @Override
    public void onFinish() {
        sendEvent(Manager.Event.CAMERA_UPDATE_FINISHED, null);
    }

    @Override
    public void onCancel() {
        sendEvent(Manager.Event.CAMERA_UPDATE_CANCELED, null);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        RNHMSMarkerView markerView = markerMap.get(marker);
        if (markerView != null) {
            return markerView.getWrappedInfoWindowView();
        }
        return null;
    }

    @Override
    public void onCameraIdle() {
        sendEvent(Manager.Event.CAMERA_IDLE,
                ReactUtils.getWritableMapFromCameraPosition(mHuaweiMap.getCameraPosition()));
    }

    @Override
    public void onCameraMoveCanceled() {
        sendEvent(Manager.Event.CAMERA_MOVE_CANCELED, null);
    }

    @Override
    public void onCameraMove() {
        sendEvent(Manager.Event.CAMERA_MOVE,
                ReactUtils.getWritableMapFromCameraPosition(mHuaweiMap.getCameraPosition()));
    }

    @Override
    public void onCameraMoveStarted(int i) {
        WritableMap wm = new WritableNativeMap();
        wm.putInt("reason", i);
        sendEvent(Manager.Event.CAMERA_MOVE_STARTED, wm);
    }

    @Override
    public void onCircleClick(Circle circle) {
        RNHMSCircleView circleView = circleMap.get(circle);
        if (circleView != null) {
            circleView.sendEvent(MapLayerViewManager.Event.CLICK, null);
        }
    }

    @Override
    public void onGroundOverlayClick(GroundOverlay groundOverlay) {
        RNHMSGroundOverlayView groundOverlayView = groundOverlayMap.get(groundOverlay);
        if (groundOverlayView != null) {
            groundOverlayView.sendEvent(MapLayerViewManager.Event.CLICK, null);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        RNHMSMarkerView markerView = markerMap.get(marker);
        if (markerView != null) {
            markerView.sendEvent(RNHMSMarkerView.Manager.Event.INFO_WINDOW_CLICK, null);
        }
    }

    @Override
    public void onInfoWindowClose(Marker marker) {
        RNHMSMarkerView markerView = markerMap.get(marker);
        if (markerView != null) {
            markerView.sendEvent(RNHMSMarkerView.Manager.Event.INFO_WINDOW_CLOSE, null);
        }
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        RNHMSMarkerView markerView = markerMap.get(marker);
        if (markerView != null) {
            markerView.sendEvent(RNHMSMarkerView.Manager.Event.INFO_WINDOW_LONG_CLICK, null);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        WritableMap wm = ReactUtils.getWritableMapFromProjectionOnLatLng(mHuaweiMap.getProjection(), latLng);
        sendEvent(Manager.Event.MAP_CLICK, wm);
    }

    @Override
    public void onMapLoaded() {
        sendEvent(Manager.Event.MAP_LOADED, null);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        WritableMap wm = ReactUtils.getWritableMapFromProjectionOnLatLng(mHuaweiMap.getProjection(), latLng);
        sendEvent(Manager.Event.MAP_LONG_CLICK, wm);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        RNHMSMarkerView markerView = markerMap.get(marker);
        if (markerView != null) {
            markerView.sendEvent(RNHMSMarkerView.Manager.Event.CLICK, null);
        }
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        RNHMSMarkerView markerView = markerMap.get(marker);
        if (markerView != null) {
            markerView.sendEvent(RNHMSMarkerView.Manager.Event.DRAG_START, null);
        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        RNHMSMarkerView markerView = markerMap.get(marker);
        if (markerView != null) {
            markerView.sendEvent(RNHMSMarkerView.Manager.Event.DRAG, null);
        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        RNHMSMarkerView markerView = markerMap.get(marker);
        if (markerView != null) {
            markerView.sendEvent(RNHMSMarkerView.Manager.Event.DRAG_END, null);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        sendEvent(Manager.Event.MY_LOCATION_BUTTON_CLICK, null);
        return false;
    }

    @Override
    public void onMyLocationClick(Location location) {
        sendEvent(Manager.Event.MY_LOCATION_CLICK, null);
    }

    @Override
    public void onPoiClick(PointOfInterest pointOfInterest) {
        WritableMap wm = ReactUtils.getWritableMapPointOfInterest(pointOfInterest);
        sendEvent(Manager.Event.POI_CLICK, wm);
    }

    @Override
    public void onPolygonClick(Polygon polygon) {
        RNHMSPolygonView polygonView = polygonMap.get(polygon);
        if (polygonView != null) {
            polygonView.sendEvent(MapLayerViewManager.Event.CLICK, null);
        }
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        RNHMSPolylineView polylineView = polylineMap.get(polyline);
        if (polylineView != null) {
            polylineView.sendEvent(MapLayerViewManager.Event.CLICK, null);
        }
    }

    @Override
    public void onSnapshotReady(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        String bitmapString = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
        WritableMap wm = new WritableNativeMap();
        wm.putString("bitmap", bitmapString);
        sendEvent(Manager.Event.SNAPSHOT_READY, wm);
    }

    public boolean hasLocationPermission() {
        int fineLoc = ActivityCompat.checkSelfPermission(mReactContext, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLoc = ActivityCompat.checkSelfPermission(mReactContext, Manifest.permission.ACCESS_COARSE_LOCATION);

        return fineLoc == PackageManager.PERMISSION_GRANTED || coarseLoc == PackageManager.PERMISSION_GRANTED;
    }

    private void clear() {
        if (mHuaweiMap != null) {
            mHuaweiMap.clear();
        }
    }

    private void takeSnapshot() {
        if (mHuaweiMap != null) {
            mHuaweiMap.snapshot(this);
        }
    }

    private void resetZoomPreference() {
        if (mHuaweiMap != null) {
            mHuaweiMap.resetMinMaxZoomPreference();
        }
    }

    private void stopAnimation() {
        if (mHuaweiMap != null) {
            mHuaweiMap.stopAnimation();
        }
    }

    private void setCameraPosition(ReadableArray args) {
        if (ReactUtils.hasValidElement(args, 0, ReadableType.Map)) {
            CameraPosition cameraPosition = ReactUtils.getCameraPositionFromReadableMap(args.getMap(0));
            applyCameraUpdate(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    private void setCoordinates(ReadableArray args) {
        if (ReactUtils.hasValidElement(args, 0, ReadableType.Map)) {
            LatLng latLng = ReactUtils.getLatLngFromReadableMap(args.getMap(0));
            if (ReactUtils.hasValidElement(args, 1, ReadableType.Number)) {
                float zoom = (float) args.getDouble(1);
                applyCameraUpdate(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
            } else {
                applyCameraUpdate(CameraUpdateFactory.newLatLng(latLng));
            }
        }
    }

    private void setBounds(ReadableArray args) {
        if (ReactUtils.hasValidElement(args, 0, ReadableType.Array)
                && ReactUtils.hasValidElement(args, 1, ReadableType.Number)) {
            LatLngBounds latLngBounds = ReactUtils.getLatLngBoundsFromReadableArray(args.getArray(0));
            int padding = args.getInt(1);
            if (ReactUtils.hasValidElement(args, 2, ReadableType.Number)
                    && ReactUtils.hasValidElement(args, 3, ReadableType.Number)) {
                int width = args.getInt(2);
                int height = args.getInt(3);
                applyCameraUpdate(CameraUpdateFactory.newLatLngBounds(latLngBounds, width, height, padding));
            } else {
                applyCameraUpdate(CameraUpdateFactory.newLatLngBounds(latLngBounds, padding));
            }
        }
    }

    private void scrollBy(ReadableArray args) {
        if (ReactUtils.hasValidElement(args, 0, ReadableType.Number)
                && ReactUtils.hasValidElement(args, 0, ReadableType.Number)) {
            float xPixel = (float) args.getDouble(0);
            float yPixel = (float) args.getDouble(1);
            applyCameraUpdate(CameraUpdateFactory.scrollBy(xPixel, yPixel));
        }
    }

    private void zoomBy(ReadableArray args) {
        if (ReactUtils.hasValidElement(args, 0, ReadableType.Number)) {
            float amount = (float) args.getDouble(0);
            if (ReactUtils.hasValidElement(args, 1, ReadableType.Map)) {
                ReadableMap focus = args.getMap(1);
                applyCameraUpdate(CameraUpdateFactory.zoomBy(amount, ReactUtils.getPointFromReadableMap(focus)));
            } else {
                applyCameraUpdate(CameraUpdateFactory.zoomBy(amount));
            }
        }
    }

    private void zoomTo(ReadableArray args) {
        if (ReactUtils.hasValidElement(args, 0, ReadableType.Number)) {
            float zoom = (float) args.getDouble(0);
            applyCameraUpdate(CameraUpdateFactory.zoomTo(zoom));
        }
    }

    private void zoomIn() {
        applyCameraUpdate(CameraUpdateFactory.zoomIn());
    }

    private void zoomOut() {
        applyCameraUpdate(CameraUpdateFactory.zoomOut());
    }

    private void sendEvent(Manager.Event event, @Nullable WritableMap wm) {
        Log.i(TAG, "Sending event: " + event.getName());
        mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), event.getName(), wm);
    }

    public void addMapLayer(View child, int index) {
        if (child instanceof MapLayerView) {
            allMapLayerViews.add(index, (MapLayerView) child);
            if (child instanceof RNHMSCircleView) {
                RNHMSCircleView circleView = (RNHMSCircleView) child;
                Circle circle = circleView.addTo(mHuaweiMap);
                circleMap.put(circle, circleView);
            }
            if (child instanceof RNHMSMarkerView) {
                RNHMSMarkerView markerView = (RNHMSMarkerView) child;
                Marker marker = markerView.addTo(mHuaweiMap);
                markerMap.put(marker, markerView);
            }
            if (child instanceof RNHMSPolygonView) {
                RNHMSPolygonView polygonView = (RNHMSPolygonView) child;
                Polygon polygon = polygonView.addTo(mHuaweiMap);
                polygonMap.put(polygon, polygonView);
            }
            if (child instanceof RNHMSPolylineView) {
                RNHMSPolylineView polylineView = (RNHMSPolylineView) child;
                Polyline polyline = polylineView.addTo(mHuaweiMap);
                polylineMap.put(polyline, polylineView);
            }
            if (child instanceof RNHMSGroundOverlayView) {
                RNHMSGroundOverlayView groundOverlayView = (RNHMSGroundOverlayView) child;
                GroundOverlay groundOverlay = groundOverlayView.addTo(mHuaweiMap);
                groundOverlayMap.put(groundOverlay, groundOverlayView);
            }
            if (child instanceof RNHMSTileOverlayView) {
                RNHMSTileOverlayView tileOverlayView = (RNHMSTileOverlayView) child;
                TileOverlay tileOverlay = tileOverlayView.addTo(mHuaweiMap);
                tileOverlayMap.put(tileOverlay, tileOverlayView);
            }
        }
    }

    public void removeMapLayer(int index) {
        MapLayerView view = allMapLayerViews.remove(index);
        view.removeFrom(mHuaweiMap);
    }

    public int getMapLayerSize() {
        return allMapLayerViews.size();
    }

    public View getMapLayer(int index) {
        return allMapLayerViews.get(index);
    }

    private void setBuildingsEnabled(boolean buildingsEnabled) {
        if (mHuaweiMap != null) {
            mHuaweiMap.setBuildingsEnabled(buildingsEnabled);
        }
    }

    private void setDescription(String description) {
        if (mHuaweiMap != null && description != null) {
            mHuaweiMap.setContentDescription(description);
        }
    }

    private void setMapStyle(String mapStyle) {
        if (mHuaweiMap != null && mapStyle != null) {
            mHuaweiMap.setMapStyle(new MapStyleOptions(mapStyle));
            setCompassEnabled(mCompassEnabled);
            setMyLocationButtonEnabled(mMyLocationButtonEnabled);
            setZoomControlsEnabled(mZoomControlsEnabled);
        }
    }

    private void setMyLocationEnabled(boolean myLocationEnabled) {
        if (mHuaweiMap != null
                && mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                && mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            mHuaweiMap.setMyLocationEnabled(hasLocationPermission() && myLocationEnabled);
            setMyLocationButtonEnabled(mMyLocationButtonEnabled);
        }
    }

    private void setMapPadding(ReadableMap mapPadding) {
        if (mHuaweiMap != null && mapPadding != null) {
            int left = 0;
            int top = 0;
            int right = 0;
            int bottom = 0;
            if (ReactUtils.hasValidKey(mapPadding, "left", ReadableType.Number)) {
                left = mapPadding.getInt("left");
            }
            if (ReactUtils.hasValidKey(mapPadding, "top", ReadableType.Number)) {
                top = mapPadding.getInt("top");
            }
            if (ReactUtils.hasValidKey(mapPadding, "right", ReadableType.Number)) {
                right = mapPadding.getInt("right");
            }
            if (ReactUtils.hasValidKey(mapPadding, "bottom", ReadableType.Number)) {
                bottom = mapPadding.getInt("bottom");
            }
            mHuaweiMap.setPadding(left, top, right, bottom);
        }
    }

    private void setMarkerClustering(boolean markerClustering) {
        if (mHuaweiMap != null) {
            mHuaweiMap.setMarkersClustering(markerClustering);
        }
    }

    private void setMyLocationButtonEnabled(boolean myLocationButtonEnabled) {
        if (mUiSettings != null) {
            mUiSettings.setMyLocationButtonEnabled(myLocationButtonEnabled);
        }
    }

    private void setScrollGesturesEnabledDuringRotateOrZoom(boolean scrollGesturesEnabledDuringRotateOrZoom) {
        if (mUiSettings != null) {
            mUiSettings.setScrollGesturesEnabledDuringRotateOrZoom(scrollGesturesEnabledDuringRotateOrZoom);
        }
    }

    private void setCamera(CameraPosition cameraPosition) {
        if (mHuaweiMap != null && cameraPosition != null) {
            mHuaweiMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    private void setCompassEnabled(boolean compassEnabled) {
        if (mUiSettings != null) {
            mUiSettings.setCompassEnabled(compassEnabled);
        }
    }

    private void setLatLngBoundsForCameraTarget(ReadableArray latLngBoundsForCameraTarget) {
        if (mHuaweiMap != null && latLngBoundsForCameraTarget != null) {
            mHuaweiMap.setLatLngBoundsForCameraTarget(
                    ReactUtils.getLatLngBoundsFromReadableArray(latLngBoundsForCameraTarget));
        }
    }

    private void setMapType(int mapType) {
        if (mHuaweiMap != null) {
            mHuaweiMap.setMapType(mapType);
        }
    }

    private void setMaxZoomPreference(int maxZoomPreference) {
        if (mHuaweiMap != null) {
            mHuaweiMap.setMaxZoomPreference(maxZoomPreference);
        }
    }

    private void setMinZoomPreference(int minZoomPreference) {
        if (mHuaweiMap != null) {
            mHuaweiMap.setMinZoomPreference(minZoomPreference);
        }
    }

    private void setRotateGesturesEnabled(boolean rotateGesturesEnabled) {
        if (mUiSettings != null) {
            mUiSettings.setRotateGesturesEnabled(rotateGesturesEnabled);
        }
    }

    private void setScrollGesturesEnabled(boolean scrollGesturesEnabled) {
        if (mUiSettings != null) {
            mUiSettings.setScrollGesturesEnabled(scrollGesturesEnabled);
        }
    }

    private void setTiltGesturesEnabled(boolean tiltGesturesEnabled) {
        if (mUiSettings != null) {
            mUiSettings.setTiltGesturesEnabled(tiltGesturesEnabled);
        }
    }

    private void setZoomControlsEnabled(boolean zoomControlsEnabled) {
        if (mUiSettings != null) {
            mUiSettings.setZoomControlsEnabled(zoomControlsEnabled);
        }
    }

    private void setZoomGesturesEnabled(boolean zoomGesturesEnabled) {
        if (mUiSettings != null) {
            mUiSettings.setZoomGesturesEnabled(zoomGesturesEnabled);
        }
    }

    private void applyCameraUpdate(CameraUpdate cameraUpdate) {
        if (mHuaweiMap != null && cameraUpdate != null) {
            if (mUseAnimation) {
                mHuaweiMap.animateCamera(cameraUpdate, mAnimationDuration, this);
            } else {
                mHuaweiMap.moveCamera(cameraUpdate);
            }
        }
    }

    private void setUseAnimation(boolean useAnimation) {
        mUseAnimation = useAnimation;
    }

    private void setAnimationDuration(int animationDuration) {
        mAnimationDuration = animationDuration;
    }

    public interface MapLayer {
        /**
         * Adds a layer on the map
         *
         * @param huaweiMap Huawei map which the layer will be created on
         * @return Layer created on map
         */
        Object addTo(HuaweiMap huaweiMap);

        /**
         * Removes a layer on the map
         *
         * @param huaweiMap Huawei map which the layer will be removed from
         */
        void removeFrom(HuaweiMap huaweiMap);
    }

    public static abstract class MapLayerView extends ReactViewGroup implements MapLayer {
        private ReactContext mReactContext;

        public MapLayerView(Context context) {
            super(context);
            mReactContext = (ReactContext) context;
        }

        public void sendEvent(ReactUtils.NamedEvent event, @Nullable WritableMap wm) {
            Log.i(TAG, "Sending event: " + event.getName());
            mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), event.getName(), wm);
        }
    }

    public static abstract class MapLayerViewManager<T extends MapLayerView> extends ViewGroupManager<T> {
        public enum Event implements ReactUtils.NamedEvent {
            CLICK("onClick");

            private String name;

            Event(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }
        }

        @Nullable
        @Override
        public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
            return ReactUtils.getExportedCustomDirectEventTypeConstantsFromEvents(Event.values());
        }
    }

    public static class Manager extends ViewGroupManager<RNHMSMapView> {
        @NonNull
        @Override
        public String getName() {
            return "RNHMSMapView";
        }

        @NonNull
        @Override
        protected RNHMSMapView createViewInstance(@NonNull ThemedReactContext reactContext) {
            return new RNHMSMapView(reactContext, new HuaweiMapOptions());
        }

        @Override
        public void addView(RNHMSMapView parent, View child, int index) {
            parent.addMapLayer(child, index);
        }

        @Override
        public void removeViewAt(RNHMSMapView parent, int index) {
            parent.removeMapLayer(index);
        }

        @Override
        public int getChildCount(RNHMSMapView view) {
            return view.getMapLayerSize();
        }

        @Override
        public View getChildAt(RNHMSMapView view, int index) {
            return view.getMapLayer(index);
        }

        public enum Event implements ReactUtils.NamedEvent {
            MAP_READY("onMapReady"),
            CAMERA_UPDATE_FINISHED("onCameraUpdateFinished"),
            CAMERA_UPDATE_CANCELED("onCameraUpdateCanceled"),
            CAMERA_IDLE("onCameraIdle"),
            CAMERA_MOVE_CANCELED("onCameraMoveCanceled"),
            CAMERA_MOVE("onCameraMove"),
            CAMERA_MOVE_STARTED("onCameraMoveStarted"),
            MAP_CLICK("onMapClick"),
            MAP_LOADED("onMapLoaded"),
            MAP_LONG_CLICK("onMapLongClick"),
            MY_LOCATION_BUTTON_CLICK("onMyLocationButtonClick"),
            MY_LOCATION_CLICK("onMyLocationClick"),
            POI_CLICK("onPoiClick"),
            SNAPSHOT_READY("onSnapshotReady");

            private String mapEventName;

            Event(String mapEventName) {
                this.mapEventName = mapEventName;
            }

            public String getName() {
                return mapEventName;
            }
        }

        @Nullable
        @Override
        public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
            return ReactUtils.getExportedCustomDirectEventTypeConstantsFromEvents(Event.values());
        }

        public enum Command implements ReactUtils.NamedCommand {
            CLEAR("clear"),
            TAKE_SNAPSHOT("takeSnapshot"),
            RESET_ZOOM_PREFERENCE("resetMinMaxZoomPreference"),
            STOP_ANIMATION("stopAnimation"),
            SET_CAMERA_POSITION("setCameraPosition"),
            SET_COORDINATES("setCoordinates"),
            SET_BOUNDS("setBounds"),
            SCROLL_BY("scrollBy"),
            ZOOM_BY("zoomBy"),
            ZOOM_IN("zoomIn"),
            ZOOM_OUT("zoomOut"),
            ZOOM_TO("zoomTo");

            private String mapCommandName;

            Command(String mapCommandName) {
                this.mapCommandName = mapCommandName;
            }

            public String getName() {
                return mapCommandName;
            }
        }

        @Override
        public void receiveCommand(@NonNull RNHMSMapView root, String commandId, @Nullable ReadableArray args) {
            ReactUtils.NamedCommand namedCommand = ReactUtils.getCommand(commandId, Command.values());
            assert namedCommand != null;
            if (namedCommand instanceof Command) {
                switch ((Command) namedCommand) {
                    case CLEAR:
                        root.clear();
                        break;
                    case TAKE_SNAPSHOT:
                        root.takeSnapshot();
                        break;
                    case RESET_ZOOM_PREFERENCE:
                        root.resetZoomPreference();
                        break;
                    case STOP_ANIMATION:
                        root.stopAnimation();
                        break;
                    case SET_CAMERA_POSITION:
                        assert args != null;
                        root.setCameraPosition(args);
                        break;
                    case SET_COORDINATES:
                        assert args != null;
                        root.setCoordinates(args);
                        break;
                    case SET_BOUNDS:
                        assert args != null;
                        root.setBounds(args);
                        break;
                    case SCROLL_BY:
                        assert args != null;
                        root.scrollBy(args);
                        break;
                    case ZOOM_BY:
                        assert args != null;
                        root.zoomBy(args);
                        break;
                    case ZOOM_TO:
                        assert args != null;
                        root.zoomTo(args);
                        break;
                    case ZOOM_IN:
                        root.zoomIn();
                        break;
                    case ZOOM_OUT:
                        root.zoomOut();
                        break;
                    default:
                        break;
                }
            }
        }

        @ReactProp(name = "useAnimation")
        public void setUseAnimation(RNHMSMapView view, boolean useAnimation) {
            view.setUseAnimation(useAnimation);
        }

        @ReactProp(name = "animationDuration")
        public void setAnimationDuration(RNHMSMapView view, int animationDuration) {
            view.setAnimationDuration(animationDuration);
        }

        @ReactProp(name = "camera")
        public void setCamera(RNHMSMapView view, ReadableMap camera) {
            if (camera != null) {
                CameraPosition cameraPosition = ReactUtils.getCameraPositionFromReadableMap(camera);
                view.setCamera(cameraPosition);
            }
        }

        @ReactProp(name = "compassEnabled")
        public void setCompassEnabled(RNHMSMapView view, boolean compassEnabled) {
            view.mCompassEnabled = compassEnabled;
            view.setCompassEnabled(compassEnabled);
        }

        @ReactProp(name = "latLngBoundsForCameraTarget")
        public void setLatLngBoundsForCameraTarget(RNHMSMapView view, ReadableArray latLngBoundsForCameraTarget) {
            view.setLatLngBoundsForCameraTarget(latLngBoundsForCameraTarget);
        }

        @ReactProp(name = "mapType")
        public void setMapType(RNHMSMapView view, int mapType) {
            view.setMapType(mapType);
        }

        @ReactProp(name = "maxZoomPreference")
        public void setMaxZoomPreference(RNHMSMapView view, int maxZoomPreference) {
            view.setMaxZoomPreference(maxZoomPreference);
        }

        @ReactProp(name = "minZoomPreference")
        public void setMinZoomPreference(RNHMSMapView view, int minZoomPreference) {
            view.setMinZoomPreference(minZoomPreference);
        }

        @ReactProp(name = "rotateGesturesEnabled")
        public void setRotateGesturesEnabled(RNHMSMapView view, boolean rotateGesturesEnabled) {
            view.setRotateGesturesEnabled(rotateGesturesEnabled);
        }

        @ReactProp(name = "scrollGesturesEnabled")
        public void setScrollGesturesEnabled(RNHMSMapView view, boolean scrollGesturesEnabled) {
            view.setScrollGesturesEnabled(scrollGesturesEnabled);
        }

        @ReactProp(name = "tiltGesturesEnabled")
        public void setTiltGesturesEnabled(RNHMSMapView view, boolean tiltGesturesEnabled) {
            view.setTiltGesturesEnabled(tiltGesturesEnabled);
        }

        @ReactProp(name = "zoomControlsEnabled")
        public void setZoomControlsEnabled(RNHMSMapView view, boolean zoomControlsEnabled) {
            view.mZoomControlsEnabled = zoomControlsEnabled;
            view.setZoomControlsEnabled(zoomControlsEnabled);
        }

        @ReactProp(name = "zoomGesturesEnabled")
        public void setZoomGesturesEnabled(RNHMSMapView view, boolean zoomGesturesEnabled) {
            view.setZoomGesturesEnabled(zoomGesturesEnabled);
        }

        @ReactProp(name = "buildingsEnabled")
        public void setBuildingsEnabled(RNHMSMapView view, boolean buildingsEnabled) {
            view.setBuildingsEnabled(buildingsEnabled);
        }

        @ReactProp(name = "description")
        public void setDescription(RNHMSMapView view, String description) {
            view.setDescription(description);
        }

        @ReactProp(name = "mapStyle")
        public void setMapStyle(RNHMSMapView view, String mapStyle) {
            view.setMapStyle(mapStyle);
        }

        @ReactProp(name = "myLocationEnabled")
        public void setMyLocationEnabled(RNHMSMapView view, boolean myLocationEnabled) {
            view.mMyLocationEnabled = myLocationEnabled;
            view.setMyLocationEnabled(myLocationEnabled);
        }

        @ReactProp(name = "mapPadding")
        public void setMapPadding(RNHMSMapView view, ReadableMap mapPadding) {
            view.setMapPadding(mapPadding);
        }

        @ReactProp(name = "markerClustering")
        public void setMarkerClustering(RNHMSMapView view, boolean markerClustering) {
            view.setMarkerClustering(markerClustering);
        }

        @ReactProp(name = "myLocationButtonEnabled")
        public void setMyLocationButtonEnabled(RNHMSMapView view, boolean myLocationButtonEnabled) {
            view.mMyLocationButtonEnabled = myLocationButtonEnabled;
            view.setMyLocationButtonEnabled(myLocationButtonEnabled);
        }

        @ReactProp(name = "scrollGesturesEnabledDuringRotateOrZoom")
        public void setScrollGesturesEnabledDuringRotateOrZoom(
                RNHMSMapView view, boolean scrollGesturesEnabledDuringRotateOrZoom) {
            view.setScrollGesturesEnabledDuringRotateOrZoom(scrollGesturesEnabledDuringRotateOrZoom);
        }
    }

    public static class Module extends ReactContextBaseJavaModule {
        Module(@NonNull ReactApplicationContext reactContext) {
            super(reactContext);
        }

        @NonNull
        @Override
        public String getName() {
            return "RNHMSMapViewModule";
        }

        @ReactMethod
        public void getHuaweiMapInfo(final int viewId, final Promise promise) {
            UIManagerModule uiManager = getReactApplicationContext().getNativeModule(UIManagerModule.class);
            uiManager.addUIBlock(nativeViewHierarchyManager -> {
                View view = nativeViewHierarchyManager.resolveView(viewId);
                if (view instanceof RNHMSMapView) {
                    RNHMSMapView myView = (RNHMSMapView) view;

                    if (myView.mHuaweiMap != null) {
                        promise.resolve(ReactUtils.getWritableMapFromHuaweiMap(myView.mHuaweiMap));
                    } else {
                        promise.reject("map_not_ready", "Huawei map is not ready");
                    }
                } else {
                    Log.e(TAG, "Expected view to be instance of RNHMSMapView, but found: " + view);
                    promise.reject("my_view", "Unexpected view type");
                }
            });
            Log.i(TAG, "getHuaweiMapInfo() executed");
        }

        @ReactMethod
        public void getPointFromCoordinate(final int viewId, final ReadableMap coordinate, final Promise promise) {
            UIManagerModule uiManager = getReactApplicationContext().getNativeModule(UIManagerModule.class);
            uiManager.addUIBlock(nativeViewHierarchyManager -> {
                View view = nativeViewHierarchyManager.resolveView(viewId);
                if (view instanceof RNHMSMapView) {
                    RNHMSMapView myView = (RNHMSMapView) view;
                    if (myView.mHuaweiMap != null) {
                        promise.resolve(ReactUtils.getWritableMapFromPoint(
                                myView.mHuaweiMap.getProjection()
                                        .toScreenLocation(ReactUtils.getLatLngFromReadableMap(coordinate))));
                    } else {
                        promise.reject("map_not_ready", "Huawei map is not ready");
                    }
                } else {
                    Log.e(TAG, "Expected view to be instance of RNHMSMapView, but found: " + view);
                    promise.reject("my_view", "Unexpected view type");
                }
            });
            Log.i(TAG, "getPointFromCoordinate() executed");
        }

        @ReactMethod
        public void getCoordinateFromPoint(final int viewId, final ReadableMap point, final Promise promise) {
            UIManagerModule uiManager = getReactApplicationContext().getNativeModule(UIManagerModule.class);
            uiManager.addUIBlock(nativeViewHierarchyManager -> {
                View view = nativeViewHierarchyManager.resolveView(viewId);
                if (view instanceof RNHMSMapView) {
                    RNHMSMapView myView = (RNHMSMapView) view;
                    if (myView.mHuaweiMap != null) {
                        promise.resolve(ReactUtils.getWritableMapFromLatLng(
                                myView.mHuaweiMap.getProjection()
                                        .fromScreenLocation(ReactUtils.getPointFromReadableMap(point))));
                    } else {
                        promise.reject("map_not_ready", "Huawei map is not ready");
                    }
                } else {
                    Log.e(TAG, "Expected view to be instance of RNHMSMapView, but found: " + view);
                    promise.reject("my_view", "Unexpected view type");
                }
            });
            Log.i(TAG, "getCoordinateFromPoint() executed");
        }

        @ReactMethod
        public void getDistance(final ReadableMap from, final ReadableMap to, final Promise promise) {
            LatLng fromLatLng = ReactUtils.getLatLngFromReadableMap(from);
            LatLng toLatLng = ReactUtils.getLatLngFromReadableMap(to);
            if (fromLatLng != null && toLatLng != null) {
                promise.resolve(DistanceCalculator.computeDistanceBetween(fromLatLng, toLatLng));
            } else {
                promise.reject("invalid_inputs", "Invalid coordinates");
            }
        }
    }
}