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

package com.huawei.hms.rn.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.facebook.react.bridge.Dynamic;
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
import com.huawei.hms.maps.model.BitmapDescriptor;
import com.huawei.hms.maps.model.CameraPosition;
import com.huawei.hms.maps.model.Circle;
import com.huawei.hms.maps.model.GroundOverlay;
import com.huawei.hms.maps.model.HeatMap;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.LatLngBounds;
import com.huawei.hms.maps.model.MapStyleOptions;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.PointOfInterest;
import com.huawei.hms.maps.model.Polygon;
import com.huawei.hms.maps.model.Polyline;
import com.huawei.hms.maps.model.TileOverlay;
import com.huawei.hms.rn.map.logger.HMSLogger;
import com.huawei.hms.rn.map.utils.ReactUtils;
import com.huawei.hms.rn.map.utils.UriIconController;
import com.huawei.hms.rn.map.utils.UriIconView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HMSMapView extends MapView implements UriIconView, OnMapReadyCallback, LifecycleEventListener,
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
    private static final String TAG = HMSMapView.class.getSimpleName();
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
    private Map<Circle, HMSCircleView> circleMap = new HashMap<>();
    private Map<Marker, HMSMarkerView> markerMap = new HashMap<>();
    private Map<Polygon, HMSPolygonView> polygonMap = new HashMap<>();
    private Map<Polyline, HMSPolylineView> polylineMap = new HashMap<>();
    private Map<GroundOverlay, HMSGroundOverlayView> groundOverlayMap = new HashMap<>();
    private Map<TileOverlay, HMSTileOverlayView> tileOverlayMap = new HashMap<>();
    private Map<HeatMap, HMSHeatMapView> heatMapMap = new HashMap<>();
    private List<MapLayerView> allMapLayerViews = new ArrayList<>();

    private CameraPosition initialCameraPosition;
    private int initialMapType;
    private boolean initialBuildingsEnabled;
    private String initialDescription;
    private String initialMapStyle;
    private boolean initialMyLocationEnabled;
    private int[] initialMapPadding;
    private boolean initialMarkerClustering;
    private boolean initialMyLocationButtonEnabled;
    private boolean initialCompassEnabled;
    private boolean initialZoomControlsEnabled;
    private Point centerCoordinates = new Point();
    private UriIconController uriIconController;
    private String styleId;
    private String previewId;

    private final HMSLogger logger;

    public HMSMapView(final Context context, HuaweiMapOptions huaweiMapOptions) {
        super(context, huaweiMapOptions);
        logger = HMSLogger.getInstance(context);
        uriIconController = new UriIconController(context, this);
        if (context instanceof ReactContext) {
            mReactContext = (ReactContext) context;
        }
        Object systemService = context.getSystemService(Context.LOCATION_SERVICE);
        if (systemService instanceof LocationManager) {
            mLocationManager = (LocationManager) systemService;
        }

        this.onCreate(null);
        this.getMapAsync(this);
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

        initializeMap();
        logger.sendSingleEvent("HMSMap.onMapReady");
        sendEvent(Manager.Event.MAP_READY, null);
    }

    private void initializeMap() {
        setCamera(initialCameraPosition);
        setMapType(initialMapType);
        setBuildingsEnabled(initialBuildingsEnabled);
        setDescription(initialDescription);
        setMapStyle(initialMapStyle);
        setMyLocationEnabled(initialMyLocationEnabled);
        setMyLocationButtonEnabled(initialMyLocationButtonEnabled);
        setMarkerClustering(initialMarkerClustering);
        setCompassEnabled(initialCompassEnabled);
        setZoomControlsEnabled(initialZoomControlsEnabled);
        setStyleId(styleId);
        setPreviewId(previewId);
        if (initialMapPadding != null) {
            mHuaweiMap.setPadding(initialMapPadding[0], initialMapPadding[1], initialMapPadding[2], initialMapPadding[3]);
        }
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
        logger.sendSingleEvent("HMSMap.onFinish");
        sendEvent(Manager.Event.CAMERA_UPDATE_FINISHED, null);
    }

    @Override
    public void onCancel() {
        logger.sendSingleEvent("HMSMap.onCancel");
        sendEvent(Manager.Event.CAMERA_UPDATE_CANCELED, null);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        HMSMarkerView markerView = markerMap.get(marker);
        if (markerView != null) {
            return markerView.getWrappedInfoWindowView();
        }
        return null;
    }

    @Override
    public void onCameraIdle() {
        logger.sendSingleEvent("HMSMap.onCameraIdle");
        sendEvent(Manager.Event.CAMERA_IDLE,
                ReactUtils.getWritableMapFromCameraPosition(mHuaweiMap.getCameraPosition()));
    }

    @Override
    public void onCameraMoveCanceled() {
        logger.sendSingleEvent("HMSMap.onCameraMoveCanceled");
        sendEvent(Manager.Event.CAMERA_MOVE_CANCELED, null);
    }

    @Override
    public void onCameraMove() {
        logger.sendSingleEvent("HMSMap.onCameraMove");
        sendEvent(Manager.Event.CAMERA_MOVE,
                ReactUtils.getWritableMapFromCameraPosition(mHuaweiMap.getCameraPosition()));
    }

    @Override
    public void onCameraMoveStarted(int i) {
        WritableMap wm = new WritableNativeMap();
        wm.putInt("reason", i);
        logger.sendSingleEvent("HMSMap.onCameraMoveStarted");
        sendEvent(Manager.Event.CAMERA_MOVE_STARTED, wm);
    }

    @Override
    public void onCircleClick(Circle circle) {
        HMSCircleView circleView = circleMap.get(circle);
        if (circleView != null) {
            logger.sendSingleEvent("HMSMap.onCircleClick");
            circleView.sendEvent(MapLayerViewManager.Event.CLICK, null);
        }
    }

    @Override
    public void onGroundOverlayClick(GroundOverlay groundOverlay) {
        HMSGroundOverlayView groundOverlayView = groundOverlayMap.get(groundOverlay);
        if (groundOverlayView != null) {
            logger.sendSingleEvent("HMSMap.onGroundOverlayClick");
            groundOverlayView.sendEvent(MapLayerViewManager.Event.CLICK, null);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        HMSMarkerView markerView = markerMap.get(marker);
        if (markerView != null) {
            logger.sendSingleEvent("HMSMap.onInfoWindowClick");
            markerView.sendEvent(HMSMarkerView.Manager.Event.INFO_WINDOW_CLICK, null);
        }
    }

    @Override
    public void onInfoWindowClose(Marker marker) {
        HMSMarkerView markerView = markerMap.get(marker);
        if (markerView != null) {
            logger.sendSingleEvent("HMSMap.onInfoWindowClose");
            markerView.sendEvent(HMSMarkerView.Manager.Event.INFO_WINDOW_CLOSE, null);
        }
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        HMSMarkerView markerView = markerMap.get(marker);
        if (markerView != null) {
            logger.sendSingleEvent("HMSMap.onInfoWindowLongClick");
            markerView.sendEvent(HMSMarkerView.Manager.Event.INFO_WINDOW_LONG_CLICK, null);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        WritableMap wm = ReactUtils.getWritableMapFromProjectionOnLatLng(mHuaweiMap.getProjection(), latLng);
        logger.sendSingleEvent("HMSMap.onMapClick");
        sendEvent(Manager.Event.MAP_CLICK, wm);
    }

    @Override
    public void onMapLoaded() {
        logger.sendSingleEvent("HMSMap.onMapLoaded");
        sendEvent(Manager.Event.MAP_LOADED, null);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        WritableMap wm = ReactUtils.getWritableMapFromProjectionOnLatLng(mHuaweiMap.getProjection(), latLng);
        logger.sendSingleEvent("HMSMap.onMapLongClick");
        sendEvent(Manager.Event.MAP_LONG_CLICK, wm);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        HMSMarkerView markerView = markerMap.get(marker);
        if (markerView != null) {
            logger.sendSingleEvent("HMSMap.onMarkerClick");
            markerView.sendEvent(HMSMarkerView.Manager.Event.CLICK, null);

            if (!markerView.defaultActionOnClick) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        HMSMarkerView markerView = markerMap.get(marker);
        if (markerView != null) {
            logger.sendSingleEvent("HMSMap.onMarkerDragStart");
            markerView.sendEvent(HMSMarkerView.Manager.Event.DRAG_START, ReactUtils.getWritableMapFromLatLng(marker.getPosition()));
        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        HMSMarkerView markerView = markerMap.get(marker);
        if (markerView != null) {
            logger.sendSingleEvent("HMSMap.onMarkerDrag");
            markerView.sendEvent(HMSMarkerView.Manager.Event.DRAG, ReactUtils.getWritableMapFromLatLng(marker.getPosition()));
        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        HMSMarkerView markerView = markerMap.get(marker);
        if (markerView != null) {
            logger.sendSingleEvent("HMSMap.onMarkerDragEnd");
            markerView.sendEvent(HMSMarkerView.Manager.Event.DRAG_END, ReactUtils.getWritableMapFromLatLng(marker.getPosition()));
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        logger.sendSingleEvent("HMSMap.onMyLocationButtonClick");
        sendEvent(Manager.Event.MY_LOCATION_BUTTON_CLICK, null);
        return false;
    }

    @Override
    public void onMyLocationClick(Location location) {
        logger.sendSingleEvent("HMSMap.onMyLocationClick");
        sendEvent(Manager.Event.MY_LOCATION_CLICK, null);
    }

    @Override
    public void onPoiClick(PointOfInterest pointOfInterest) {
        WritableMap wm = ReactUtils.getWritableMapPointOfInterest(pointOfInterest);
        logger.sendSingleEvent("HMSMap.onPoiClick");
        sendEvent(Manager.Event.POI_CLICK, wm);
    }

    @Override
    public void onPolygonClick(Polygon polygon) {
        HMSPolygonView polygonView = polygonMap.get(polygon);
        if (polygonView != null) {
            logger.sendSingleEvent("HMSMap.onPolygonClick");
            polygonView.sendEvent(MapLayerViewManager.Event.CLICK, null);
        }
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        HMSPolylineView polylineView = polylineMap.get(polyline);
        if (polylineView != null) {
            logger.sendSingleEvent("HMSMap.onPolylineClick");
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
        logger.sendSingleEvent("HMSMap.takeSnapshot");
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
            if (child instanceof HMSCircleView) {
                HMSCircleView circleView = (HMSCircleView) child;
                Circle circle = circleView.addTo(mHuaweiMap);
                circleMap.put(circle, circleView);
            }
            if (child instanceof HMSMarkerView) {
                HMSMarkerView markerView = (HMSMarkerView) child;
                Marker marker = markerView.addTo(mHuaweiMap);
                markerMap.put(marker, markerView);
            }
            if (child instanceof HMSPolygonView) {
                HMSPolygonView polygonView = (HMSPolygonView) child;
                Polygon polygon = polygonView.addTo(mHuaweiMap);
                polygonMap.put(polygon, polygonView);
            }
            if (child instanceof HMSPolylineView) {
                HMSPolylineView polylineView = (HMSPolylineView) child;
                Polyline polyline = polylineView.addTo(mHuaweiMap);
                polylineMap.put(polyline, polylineView);
            }
            if (child instanceof HMSGroundOverlayView) {
                HMSGroundOverlayView groundOverlayView = (HMSGroundOverlayView) child;
                GroundOverlay groundOverlay = groundOverlayView.addTo(mHuaweiMap);
                groundOverlayMap.put(groundOverlay, groundOverlayView);
            }
            if (child instanceof HMSTileOverlayView) {
                HMSTileOverlayView tileOverlayView = (HMSTileOverlayView) child;
                TileOverlay tileOverlay = tileOverlayView.addTo(mHuaweiMap);
                tileOverlayMap.put(tileOverlay, tileOverlayView);
            }
            if (child instanceof HMSHeatMapView) {
                HMSHeatMapView hmsHeatMapView = (HMSHeatMapView) child;
                HeatMap heatMap = hmsHeatMapView.addTo(mHuaweiMap);
                heatMapMap.put(heatMap, hmsHeatMapView);
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
        } else {
            initialBuildingsEnabled = buildingsEnabled;
        }
    }

    private void setDescription(String description) {
        if (mHuaweiMap != null && description != null) {
            mHuaweiMap.setContentDescription(description);
        } else {
            initialDescription = description;
        }
    }

    private void setMapStyle(String mapStyle) {
        if (mHuaweiMap != null && mapStyle != null) {
            mHuaweiMap.setMapStyle(new MapStyleOptions(mapStyle));
            setCompassEnabled(mCompassEnabled);
            setMyLocationButtonEnabled(mMyLocationButtonEnabled);
            setZoomControlsEnabled(mZoomControlsEnabled);
        } else {
            initialMapStyle = mapStyle;
        }
    }

    private void setStyleId(String styleId) {
        if (mHuaweiMap != null && styleId != null) {
            mHuaweiMap.setStyleId(styleId);
        }
    }

    private void setPreviewId(String previewId) {
        if (mHuaweiMap != null && previewId != null) {
            mHuaweiMap.previewId(previewId);
        }
    }

    private void setMyLocationEnabled(boolean myLocationEnabled) {
        if (mHuaweiMap != null
                && mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                && mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            try {
                mHuaweiMap.setMyLocationEnabled(hasLocationPermission() && myLocationEnabled);
                setMyLocationButtonEnabled(mMyLocationButtonEnabled);
            } catch (Exception exception) {
                Log.w(TAG, exception.getLocalizedMessage());
            }
        } else {
            initialMyLocationEnabled = myLocationEnabled;
        }
    }

    private void setMapPadding(ReadableMap mapPadding) {
        if (mapPadding != null) {
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
            if (mHuaweiMap != null) {
                mHuaweiMap.setPadding(left, top, right, bottom);
            } else {
                initialMapPadding = new int[]{left, top, right, bottom};
            }

        }
    }

    private void setMarkerClustering(boolean markerClustering) {
        if (mHuaweiMap != null) {
            mHuaweiMap.setMarkersClustering(markerClustering);
        } else {
            initialMarkerClustering = markerClustering;
        }
    }

    private void setMyLocationButtonEnabled(boolean myLocationButtonEnabled) {
        if (mUiSettings != null) {
            mUiSettings.setMyLocationButtonEnabled(myLocationButtonEnabled);
        } else {
            initialMyLocationButtonEnabled = myLocationButtonEnabled;
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
        } else {
            initialCameraPosition = cameraPosition;
        }
    }

    private void setCompassEnabled(boolean compassEnabled) {
        if (mUiSettings != null) {
            mUiSettings.setCompassEnabled(compassEnabled);
        } else {
            initialCompassEnabled = compassEnabled;
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
        } else {
            initialMapType = mapType;
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
        } else {
            initialZoomControlsEnabled = zoomControlsEnabled;
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

    private void setPointToCenter(ReadableMap setPointToCenter) {
        centerCoordinates = ReactUtils.getPointFromReadableMap(setPointToCenter);
        if (mHuaweiMap != null) {
            mHuaweiMap.setPointToCenter(centerCoordinates.x, centerCoordinates.y);
        }
    }

    private void setGestureScaleByMapCenter(boolean gestureScaleByMapCenter) {
        if (mUiSettings != null) {
            mUiSettings.setGestureScaleByMapCenter(gestureScaleByMapCenter);
            mHuaweiMap.setPointToCenter(centerCoordinates.x, centerCoordinates.y);
        }
    }

    private void setTrafficEnabled(boolean trafficEnabled) {
        if (mHuaweiMap != null) {
            mHuaweiMap.setTrafficEnabled(trafficEnabled);
        }
    }

    private void setMarkerClusterColor(int color) {
        if (mUiSettings != null) {
            mUiSettings.setMarkerClusterColor(color);
        }
    }

    private void setMarkerClusterTextColor(int color) {
        if (mUiSettings != null) {
            mUiSettings.setMarkerClusterTextColor(color);
        }
    }

    private void setMarkerClusterIcon(ReadableMap icon) {
        if (icon.hasKey("uri")) {
            uriIconController.setUriIcon(icon);
            return;
        }
        if (mUiSettings != null) {
            BitmapDescriptor bitmapDescriptor = ReactUtils.getBitmapDescriptorFromReadableMap(icon);
            setUriIcon(bitmapDescriptor, null);
        }
    }

    private void setLogoPosition(int gravity) {
        if (mUiSettings != null) {
            mUiSettings.setLogoPosition(gravity);
        }
    }

    private void setLogoPadding(ReadableMap logoPadding) {
        int start = 0;
        int top = 0;
        int end = 0;
        int bottom = 0;
        if (ReactUtils.hasValidKey(logoPadding, "paddingStart", ReadableType.Number)) {
            start = logoPadding.getInt("paddingStart");
        }
        if (ReactUtils.hasValidKey(logoPadding, "paddingTop", ReadableType.Number)) {
            top = logoPadding.getInt("paddingTop");
        }
        if (ReactUtils.hasValidKey(logoPadding, "paddingEnd", ReadableType.Number)) {
            end = logoPadding.getInt("paddingEnd");
        }
        if (ReactUtils.hasValidKey(logoPadding, "paddingBottom", ReadableType.Number)) {
            bottom = logoPadding.getInt("paddingBottom");
        }
        if (mUiSettings != null) {
            mUiSettings.setLogoPadding(start, top, end, bottom);
        }
    }

    @Override
    public void setUriIcon(BitmapDescriptor bitmapDescriptor, ReadableMap options) {
        mUiSettings.setMarkerClusterIcon(bitmapDescriptor);
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

        /**
         * Gets layer information
         *
         * @return WritableMap layer information
         */
        WritableMap getInfo();

        /**
         * Gets layer options information
         *
         * @return WritableMap layer options information
         */
        WritableMap getOptionsInfo();
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

    public static class Manager extends ViewGroupManager<HMSMapView> {
        private HMSLogger logger;

        public Manager(Context context) {
            super();
            logger = HMSLogger.getInstance(context);
        }

        @NonNull
        @Override
        public String getName() {
            return "HMSMapView";
        }

        @NonNull
        @Override
        protected HMSMapView createViewInstance(@NonNull ThemedReactContext reactContext) {
            logger.startMethodExecutionTimer("HMSMap");

            HuaweiMapOptions huaweiMapOptions = new HuaweiMapOptions();

            huaweiMapOptions.liteMode(Module.liteMod);

            HMSMapView view = new HMSMapView(reactContext, huaweiMapOptions);

            logger.sendSingleEvent("HMSMap");
            return view;
        }

        @Override
        public void addView(HMSMapView parent, View child, int index) {
            parent.addMapLayer(child, index);
        }

        @Override
        public void removeViewAt(HMSMapView parent, int index) {
            parent.removeMapLayer(index);
        }

        @Override
        public int getChildCount(HMSMapView view) {
            return view.getMapLayerSize();
        }

        @Override
        public View getChildAt(HMSMapView view, int index) {
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

        @Nullable
        @Override
        public Map<String, Integer> getCommandsMap() {
            return ReactUtils.getCommandsMap(Command.values());
        }

        @Override
        public void receiveCommand(@NonNull HMSMapView root, int commandId, @Nullable ReadableArray args) {
            if (commandId < Command.values().length) {
                switch (Command.values()[commandId]) {
                    case CLEAR:
                        logger.startMethodExecutionTimer("HMSMap.clear");
                        root.clear();
                        logger.sendSingleEvent("HMSMap.clear");
                        break;
                    case TAKE_SNAPSHOT:
                        logger.startMethodExecutionTimer("HMSMap.takeSnapshot");
                        root.takeSnapshot();
                        break;
                    case RESET_ZOOM_PREFERENCE:
                        logger.startMethodExecutionTimer("HMSMap.resetMinMaxZoomPreference");
                        root.resetZoomPreference();
                        logger.sendSingleEvent("HMSMap.resetMinMaxZoomPreference");
                        break;
                    case STOP_ANIMATION:
                        logger.startMethodExecutionTimer("HMSMap.stopAnimation");
                        root.stopAnimation();
                        logger.sendSingleEvent("HMSMap.stopAnimation");
                        break;
                    case SET_CAMERA_POSITION:
                        assert args != null;
                        logger.startMethodExecutionTimer("HMSMap.setCameraPosition");
                        root.setCameraPosition(args);
                        logger.sendSingleEvent("HMSMap.setCameraPosition");
                        break;
                    case SET_COORDINATES:
                        assert args != null;
                        logger.startMethodExecutionTimer("HMSMap.setCoordinates");
                        root.setCoordinates(args);
                        logger.sendSingleEvent("HMSMap.setCoordinates");
                        break;
                    case SET_BOUNDS:
                        assert args != null;
                        logger.startMethodExecutionTimer("HMSMap.setBounds");
                        root.setBounds(args);
                        logger.sendSingleEvent("HMSMap.setBounds");
                        break;
                    case SCROLL_BY:
                        assert args != null;
                        logger.startMethodExecutionTimer("HMSMap.scrollBy");
                        root.scrollBy(args);
                        logger.sendSingleEvent("HMSMap.scrollBy");
                        break;
                    case ZOOM_BY:
                        assert args != null;
                        logger.startMethodExecutionTimer("HMSMap.zoomBy");
                        root.zoomBy(args);
                        logger.sendSingleEvent("HMSMap.zoomBy");
                        break;
                    case ZOOM_TO:
                        assert args != null;
                        logger.startMethodExecutionTimer("HMSMap.zoomTo");
                        root.zoomTo(args);
                        logger.sendSingleEvent("HMSMap.zoomTo");
                        break;
                    case ZOOM_IN:
                        logger.startMethodExecutionTimer("HMSMap.zoomIn");
                        root.zoomIn();
                        logger.sendSingleEvent("HMSMap.zoomIn");
                        break;
                    case ZOOM_OUT:
                        logger.startMethodExecutionTimer("HMSMap.zoomOut");
                        root.zoomOut();
                        logger.sendSingleEvent("HMSMap.zoomOut");
                        break;
                    default:
                        break;
                }
            }
        }

        @ReactProp(name = "logoPosition")
        public void setLogoPosition(HMSMapView view, int logoPosition) {
            logger.startMethodExecutionTimer("HMSMap.logoPosition");
            view.setLogoPosition(logoPosition);
            logger.sendSingleEvent("HMSMap.logoPosition");
        }

        @ReactProp(name = "logoPadding")
        public void setLogoPadding(HMSMapView view, ReadableMap mapPadding) {
            logger.startMethodExecutionTimer("HMSMap.logoPadding");
            view.setLogoPadding(mapPadding);
            logger.sendSingleEvent("HMSMap.logoPadding");
        }

        @ReactProp(name = "markerClusterColor")
        public void setMarkerClusterColor(HMSMapView view, Dynamic color) {
            if (color.getType() == ReadableType.Array) {
                view.setMarkerClusterColor(ReactUtils.getColorFromRgbaArray(color.asArray()));
            } else if (color.getType() == ReadableType.Number) {
                view.setMarkerClusterColor(color.asInt());
            }
        }

        @ReactProp(name = "markerClusterTextColor")
        public void setMarkerClusterTextColor(HMSMapView view, Dynamic color) {
            if (color.getType() == ReadableType.Array) {
                view.setMarkerClusterTextColor(ReactUtils.getColorFromRgbaArray(color.asArray()));
            } else if (color.getType() == ReadableType.Number) {
                view.setMarkerClusterTextColor(color.asInt());
            }
        }

        @ReactProp(name = "markerClusterIcon")
        public void setMarkerClusterIcon(HMSMapView view, ReadableMap icon) {
            view.setMarkerClusterIcon(icon);
        }


        @ReactProp(name = "pointToCenter")
        public void setPointToCenter(HMSMapView view, ReadableMap pointToCenter) {
            logger.startMethodExecutionTimer("HMSMap.pointToCenter");
            view.setPointToCenter(pointToCenter);
            logger.sendSingleEvent("HMSMap.pointToCenter");
        }

        @ReactProp(name = "gestureScaleByMapCenter")
        public void setGestureScaleByMapCenter(HMSMapView view, boolean gestureScaleByMapCenter) {
            logger.startMethodExecutionTimer("HMSMap.gestureScaleByMapCenter");
            view.setGestureScaleByMapCenter(gestureScaleByMapCenter);
            logger.sendSingleEvent("HMSMap.gestureScaleByMapCenter");
        }

        @ReactProp(name = "trafficEnabled")
        public void setTrafficEnabled(HMSMapView view, boolean trafficEnabled) {
            logger.startMethodExecutionTimer("HMSMap.trafficEnabled");
            view.setTrafficEnabled(trafficEnabled);
            logger.sendSingleEvent("HMSMap.trafficEnabled");
        }

        @ReactProp(name = "useAnimation")
        public void setUseAnimation(HMSMapView view, boolean useAnimation) {
            logger.startMethodExecutionTimer("HMSMap.useAnimation");
            view.setUseAnimation(useAnimation);
            logger.sendSingleEvent("HMSMap.useAnimation");
        }

        @ReactProp(name = "animationDuration")
        public void setAnimationDuration(HMSMapView view, int animationDuration) {
            logger.startMethodExecutionTimer("HMSMap.animationDuration");
            view.setAnimationDuration(animationDuration);
            logger.sendSingleEvent("HMSMap.animationDuration");
        }

        @ReactProp(name = "camera")
        public void setCamera(HMSMapView view, ReadableMap camera) {
            logger.startMethodExecutionTimer("HMSMap.camera");
            if (camera != null) {
                CameraPosition cameraPosition = ReactUtils.getCameraPositionFromReadableMap(camera);
                view.setCamera(cameraPosition);
            }
            logger.sendSingleEvent("HMSMap.camera");
        }

        @ReactProp(name = "compassEnabled")
        public void setCompassEnabled(HMSMapView view, boolean compassEnabled) {
            logger.startMethodExecutionTimer("HMSMap.compassEnabled");
            view.mCompassEnabled = compassEnabled;
            view.setCompassEnabled(compassEnabled);
            logger.sendSingleEvent("HMSMap.compassEnabled");
        }

        @ReactProp(name = "latLngBoundsForCameraTarget")
        public void setLatLngBoundsForCameraTarget(HMSMapView view, ReadableArray latLngBoundsForCameraTarget) {
            logger.startMethodExecutionTimer("HMSMap.latLngBoundsForCameraTarget");
            view.setLatLngBoundsForCameraTarget(latLngBoundsForCameraTarget);
            logger.sendSingleEvent("HMSMap.latLngBoundsForCameraTarget");
        }

        @ReactProp(name = "mapType")
        public void setMapType(HMSMapView view, int mapType) {
            logger.startMethodExecutionTimer("HMSMap.mapType");
            view.setMapType(mapType);
            logger.sendSingleEvent("HMSMap.mapType");
        }

        @ReactProp(name = "maxZoomPreference")
        public void setMaxZoomPreference(HMSMapView view, int maxZoomPreference) {
            logger.startMethodExecutionTimer("HMSMap.maxZoomPreference");
            view.setMaxZoomPreference(maxZoomPreference);
            logger.sendSingleEvent("HMSMap.maxZoomPreference");
        }

        @ReactProp(name = "minZoomPreference")
        public void setMinZoomPreference(HMSMapView view, int minZoomPreference) {
            logger.startMethodExecutionTimer("HMSMap.minZoomPreference");
            view.setMinZoomPreference(minZoomPreference);
            logger.sendSingleEvent("HMSMap.minZoomPreference");
        }

        @ReactProp(name = "rotateGesturesEnabled")
        public void setRotateGesturesEnabled(HMSMapView view, boolean rotateGesturesEnabled) {
            logger.startMethodExecutionTimer("HMSMap.rotateGesturesEnabled");
            view.setRotateGesturesEnabled(rotateGesturesEnabled);
            logger.sendSingleEvent("HMSMap.rotateGesturesEnabled");
        }

        @ReactProp(name = "scrollGesturesEnabled")
        public void setScrollGesturesEnabled(HMSMapView view, boolean scrollGesturesEnabled) {
            logger.startMethodExecutionTimer("HMSMap.scrollGesturesEnabled");
            view.setScrollGesturesEnabled(scrollGesturesEnabled);
            logger.sendSingleEvent("HMSMap.scrollGesturesEnabled");
        }

        @ReactProp(name = "tiltGesturesEnabled")
        public void setTiltGesturesEnabled(HMSMapView view, boolean tiltGesturesEnabled) {
            logger.startMethodExecutionTimer("HMSMap.tiltGesturesEnabled");
            view.setTiltGesturesEnabled(tiltGesturesEnabled);
            logger.sendSingleEvent("HMSMap.tiltGesturesEnabled");
        }

        @ReactProp(name = "zoomControlsEnabled")
        public void setZoomControlsEnabled(HMSMapView view, boolean zoomControlsEnabled) {
            logger.startMethodExecutionTimer("HMSMap.zoomControlsEnabled");
            view.mZoomControlsEnabled = zoomControlsEnabled;
            view.setZoomControlsEnabled(zoomControlsEnabled);
            logger.sendSingleEvent("HMSMap.zoomControlsEnabled");
        }

        @ReactProp(name = "zoomGesturesEnabled")
        public void setZoomGesturesEnabled(HMSMapView view, boolean zoomGesturesEnabled) {
            logger.startMethodExecutionTimer("HMSMap.zoomGesturesEnabled");
            view.setZoomGesturesEnabled(zoomGesturesEnabled);
            logger.sendSingleEvent("HMSMap.zoomGesturesEnabled");
        }

        @ReactProp(name = "buildingsEnabled")
        public void setBuildingsEnabled(HMSMapView view, boolean buildingsEnabled) {
            logger.startMethodExecutionTimer("HMSMap.buildingsEnabled");
            view.setBuildingsEnabled(buildingsEnabled);
            logger.sendSingleEvent("HMSMap.buildingsEnabled");
        }

        @ReactProp(name = "description")
        public void setDescription(HMSMapView view, String description) {
            logger.startMethodExecutionTimer("HMSMap.description");
            view.setDescription(description);
            logger.sendSingleEvent("HMSMap.description");
        }

        @ReactProp(name = "mapStyle")
        public void setMapStyle(HMSMapView view, String mapStyle) {
            logger.startMethodExecutionTimer("HMSMap.mapStyle");
            view.setMapStyle(mapStyle);
            logger.sendSingleEvent("HMSMap.mapStyle");
        }

        @ReactProp(name = "styleId")
        public void setStyleId(HMSMapView view, String styleId) {
            logger.startMethodExecutionTimer("HMSMap.setStyleId");
            view.setStyleId(styleId);
            logger.sendSingleEvent("HMSMap.setStyleId");
        }

        @ReactProp(name = "previewId")
        public void setPreviewId(HMSMapView view, String previewId) {
            logger.startMethodExecutionTimer("HMSMap.setPreviewId");
            view.setPreviewId(previewId);
            logger.sendSingleEvent("HMSMap.setPreviewId");
        }

        @ReactProp(name = "myLocationEnabled")
        public void setMyLocationEnabled(HMSMapView view, boolean myLocationEnabled) {
            logger.startMethodExecutionTimer("HMSMap.myLocationEnabled");
            view.mMyLocationEnabled = myLocationEnabled;
            view.setMyLocationEnabled(myLocationEnabled);
            logger.sendSingleEvent("HMSMap.myLocationEnabled");
        }

        @ReactProp(name = "mapPadding")
        public void setMapPadding(HMSMapView view, ReadableMap mapPadding) {
            logger.startMethodExecutionTimer("HMSMap.mapPadding");
            view.setMapPadding(mapPadding);
            logger.sendSingleEvent("HMSMap.mapPadding");
        }

        @ReactProp(name = "markerClustering")
        public void setMarkerClustering(HMSMapView view, boolean markerClustering) {
            logger.startMethodExecutionTimer("HMSMap.markerClustering");
            view.setMarkerClustering(markerClustering);
            logger.sendSingleEvent("HMSMap.markerClustering");
        }

        @ReactProp(name = "myLocationButtonEnabled")
        public void setMyLocationButtonEnabled(HMSMapView view, boolean myLocationButtonEnabled) {
            logger.startMethodExecutionTimer("HMSMap.myLocationButtonEnabled");
            view.mMyLocationButtonEnabled = myLocationButtonEnabled;
            view.setMyLocationButtonEnabled(myLocationButtonEnabled);
            logger.sendSingleEvent("HMSMap.myLocationButtonEnabled");
        }

        @ReactProp(name = "scrollGesturesEnabledDuringRotateOrZoom")
        public void setScrollGesturesEnabledDuringRotateOrZoom(
                HMSMapView view, boolean scrollGesturesEnabledDuringRotateOrZoom) {
            logger.startMethodExecutionTimer("HMSMap.scrollGesturesEnabledDuringRotateOrZoom");
            view.setScrollGesturesEnabledDuringRotateOrZoom(scrollGesturesEnabledDuringRotateOrZoom);
            logger.sendSingleEvent("HMSMap.scrollGesturesEnabledDuringRotateOrZoom");
        }
    }

    public static class Module extends ReactContextBaseJavaModule {
        private HMSLogger logger;

        protected static volatile boolean liteMod = false;

        Module(@NonNull ReactApplicationContext reactContext) {
            super(reactContext);
            logger = HMSLogger.getInstance(reactContext);
        }

        @NonNull
        @Override
        public String getName() {
            return "HMSMapViewModule";
        }

        @ReactMethod
        public void getHuaweiMapInfo(final int viewId, final Promise promise) {
            logger.startMethodExecutionTimer("HMSMapModule.getHuaweiMapInfo");
            UIManagerModule uiManager = getReactApplicationContext().getNativeModule(UIManagerModule.class);
            uiManager.addUIBlock(nativeViewHierarchyManager -> {
                View view = nativeViewHierarchyManager.resolveView(viewId);
                if (view instanceof HMSMapView) {
                    HMSMapView myView = (HMSMapView) view;

                    if (myView.mHuaweiMap != null) {
                        promise.resolve(ReactUtils.getWritableMapFromHuaweiMap(myView.mHuaweiMap));
                    } else {
                        promise.reject("map_not_ready", "Huawei map is not ready");
                    }
                } else {
                    Log.e(TAG, "Expected view to be instance of HMSMapView, but found: " + view);
                    promise.reject("wrong_view_type", "Unexpected view type");
                }
            });
            Log.i(TAG, "getHuaweiMapInfo() executed");
            logger.sendSingleEvent("HMSMapModule.getHuaweiMapInfo");
        }

        @ReactMethod
        public void getPointFromCoordinate(final int viewId, final ReadableMap coordinate, final Promise promise) {
            logger.startMethodExecutionTimer("HMSMapModule.getPointFromCoordinate");
            UIManagerModule uiManager = getReactApplicationContext().getNativeModule(UIManagerModule.class);
            uiManager.addUIBlock(nativeViewHierarchyManager -> {
                View view = nativeViewHierarchyManager.resolveView(viewId);
                if (view instanceof HMSMapView) {
                    HMSMapView myView = (HMSMapView) view;
                    if (myView.mHuaweiMap != null) {
                        promise.resolve(ReactUtils.getWritableMapFromPoint(
                                myView.mHuaweiMap.getProjection()
                                        .toScreenLocation(ReactUtils.getLatLngFromReadableMap(coordinate))));
                    } else {
                        promise.reject("map_not_ready", "Huawei map is not ready");
                    }
                } else {
                    Log.e(TAG, "Expected view to be instance of HMSMapView, but found: " + view);
                    promise.reject("wrong_view_type", "Unexpected view type");
                }
            });
            Log.i(TAG, "getPointFromCoordinate() executed");
            logger.sendSingleEvent("HMSMapModule.getPointFromCoordinate");

        }

        @ReactMethod
        public void getCoordinateFromPoint(final int viewId, final ReadableMap point, final Promise promise) {
            logger.startMethodExecutionTimer("HMSMapModule.getCoordinateFromPoint");
            UIManagerModule uiManager = getReactApplicationContext().getNativeModule(UIManagerModule.class);
            uiManager.addUIBlock(nativeViewHierarchyManager -> {
                View view = nativeViewHierarchyManager.resolveView(viewId);
                if (view instanceof HMSMapView) {
                    HMSMapView myView = (HMSMapView) view;
                    if (myView.mHuaweiMap != null) {
                        promise.resolve(ReactUtils.getWritableMapFromLatLng(
                                myView.mHuaweiMap.getProjection()
                                        .fromScreenLocation(ReactUtils.getPointFromReadableMap(point))));
                    } else {
                        promise.reject("map_not_ready", "Huawei map is not ready");
                    }
                } else {
                    Log.e(TAG, "Expected view to be instance of HMSMapView, but found: " + view);
                    promise.reject("wrong_view_type", "Unexpected view type");
                }
            });
            Log.i(TAG, "getCoordinateFromPoint() executed");
            logger.sendSingleEvent("HMSMapModule.getCoordinateFromPoint");
        }

        @ReactMethod
        public void getDistance(final ReadableMap from, final ReadableMap to, final Promise promise) {
            logger.startMethodExecutionTimer("HMSMapModule.getDistance");
            LatLng fromLatLng = ReactUtils.getLatLngFromReadableMap(from);
            LatLng toLatLng = ReactUtils.getLatLngFromReadableMap(to);
            if (fromLatLng != null && toLatLng != null) {
                promise.resolve(DistanceCalculator.computeDistanceBetween(fromLatLng, toLatLng));
            } else {
                promise.reject("invalid_inputs", "Invalid coordinates");
            }
            logger.sendSingleEvent("HMSMapModule.getDistance");
        }

        @ReactMethod
        public void getLayerInfo(final int viewId, final Promise promise) {
            logger.startMethodExecutionTimer("HMSMapModule.getLayerInfo");
            UIManagerModule uiManager = getReactApplicationContext().getNativeModule(UIManagerModule.class);
            uiManager.addUIBlock(nativeViewHierarchyManager -> {
                View view = nativeViewHierarchyManager.resolveView(viewId);
                if (view instanceof MapLayerView) {
                    MapLayerView myView = (MapLayerView) view;
                    WritableMap wm = myView.getInfo();
                    if (wm != null) {
                        promise.resolve(wm);
                    } else {
                        promise.reject("view_is_null", "Received null instead of a view");
                    }
                } else {
                    promise.reject("wrong_view_type", "Unexpected view type");
                }
            });
            logger.sendSingleEvent("HMSMapModule.getLayerInfo");
        }

        @ReactMethod
        public void getLayerOptionsInfo(final int viewId, final Promise promise) {
            logger.startMethodExecutionTimer("HMSMapModule.getLayerOptionsInfo");
            UIManagerModule uiManager = getReactApplicationContext().getNativeModule(UIManagerModule.class);
            uiManager.addUIBlock(nativeViewHierarchyManager -> {
                View view = nativeViewHierarchyManager.resolveView(viewId);
                if (view instanceof MapLayerView) {
                    MapLayerView myView = (MapLayerView) view;
                    WritableMap wm = myView.getOptionsInfo();
                    if (wm != null) {
                        promise.resolve(wm);
                    } else {
                        promise.reject("view_is_null", "Received null instead of a view");
                    }
                } else {
                    promise.reject("wrong_view_type", "Unexpected view type");
                }
            });
            logger.sendSingleEvent("HMSMapModule.getLayerOptionsInfo");
        }

        @ReactMethod
        public void enableLogger(final Promise promise) {
            logger.enableLogger();
            promise.resolve(null);
        }

        @ReactMethod
        public void disableLogger(final Promise promise) {
            logger.disableLogger();
            promise.resolve(null);
        }

        @ReactMethod
        public static synchronized void setLiteMod(boolean liteMod,final Promise promise) {
            Module.liteMod = liteMod;
            promise.resolve(liteMod);
        }
    }
}