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

declare module "@hmscore/react-native-hms-map" {
  import * as React from "react";
  import { NativeSyntheticEvent, ViewProps } from "react-native";

  /**
   *  Defines the longitude and latitude, in degrees.
   */
  export interface LatLng {
    /**
     *  Latitude. The value ranges from –90 to 90.
     */
    latitude: number;

    /**
     *  Longitude. The value ranges from –180 to 180 (excluded).
     */
    longitude: number;
  }

  /**
   *  A rectangular area using a pair of longitude and latitude.
   */
  export interface LatLngBounds {
    /**
     *  Northeast corner of the bound.
     */
    northeast: LatLng;

    /**
     *  Southwest corner of the bound.
     */
    southwest: LatLng;
  }

  /**
   *  Contains information of UI and gesture controls of the map.
   */
  export interface UiSettings {
    /**
     *  Whether the compass is enabled for the map.
     */
    isCompassEnabled: boolean;

    /**
     *  Whether the my-location icon is enabled for the map.
     */
    isMyLocationButtonEnabled: boolean;

    /**
     *  Whether rotate gestures are enabled for the map.
     */
    isRotateGesturesEnabled: boolean;

    /**
     *  Whether scroll gestures are enabled for the map.
     */
    isScrollGesturesEnabled: boolean;

    /**
     *  Whether scroll gestures are enabled during rotation or zooming.
     */
    isScrollGesturesEnabledDuringRotateOrZoom: boolean;

    /**
     *  Whether tilt gestures are enabled for the map.
     */
    isTiltGesturesEnabled: boolean;

    /**
     *  Whether zoom controls are enabled.
     */
    isZoomControlsEnabled: boolean;

    /**
     *  Whether zoom gestures are enabled for the map.
     */
    isZoomGesturesEnabled: boolean;
  }

  /**
   *  Contains all camera position parameters.
   */
  export interface CameraPosition {
    /**
     *  Longitude and latitude of the location that the camera is pointing at.
     */
    target: LatLng;

    /**
     *  Zoom level near the center of the screen.
     */
    zoom?: number;

    /**
     *  Angle of the camera from the nadir (directly facing the Earth's surface).
     */
    tilt?: number;

    /**
     *  Direction that the camera is pointing in.
     */
    bearing?: number;
  }

  /**
   *  It contains four points that define a tetragon visible in the
   *  camera of a map. The tetragon may be a trapezoid instead of
   *  rectangle because the camera may tilt. If the camera is
   *  directly over the center of the visible region, the shape is
   *  rectangular. If the camera tilts, the shape will be a trapezoid
   *  whose smallest side is closest to the point of view.
   */
  export interface VisibleRegion {
    /**
     *  Far left corner of the camera..
     */
    farLeft: LatLng;

    /**
     *  Far right corner of the camera.
     */
    farRight: LatLng;

    /**
     *  Near left corner of the camera.
     */
    nearLeft: LatLng;

    /**
     *  Near right corner of the camera.
     */
    nearRight: LatLng;

    /**
     *  Smallest bounding box that includes the visible region.
     */
    latLngBounds: LatLngBounds;
  }

  /**
   *  Contains map information.
   */
  export interface HuaweiMap {
    /**
     *  The current position of the camera.
     */
    cameraPosition: CameraPosition;

    /**
     *  The current map type.
     */
    mapType: number;

    /**
     *  The maximum zoom level at the current camera position.
     */
    maxZoomLevel: number;

    /**
     *  The minimum zoom level at the current camera position.
     */
    minZoomLevel: number;

    /**
     *  The current visible region on the map.
     */
    visibleRegion: VisibleRegion;

    /**
     *  The current UI settings.
     */
    uiSettings: UiSettings;

    /**
     *  Whether the 3D building layer is enabled for a map.
     */
    isBuildingsEnabled: boolean;

    /**
     *  Whether my location layer is enabled for a map.
     */
    isMyLocationEnabled: boolean;
  }

  /**
   *  Coordinates of a location on the screen, in pixels.
   */
  export interface Point {
    x: number;
    y: number;
  }

  /**
   *  Contains attributes about the POI.
   */
  export interface PointOfInterest {
    /**
     *  Position of the POI.
     */
    latLng: LatLng;

    /**
     *  Name of the POI.
     */
    name: string;

    /**
     *  Place id of the POI.
     */
    placeId: string;
  }

  /**
   *  Contains information of clicked(or long-clicked) place on the map.
   */
  export interface ProjectionOnLatLng {
    /**
     *  Point of the place.
     */
    point: Point;

    /**
     *  Coordinate of the place.
     */
    coordinate: LatLng;

    /**
     *  The current visible region on the map.
     */
    visibleRegion: VisibleRegion;
  }

  /**
   *  Type of the stroke pattern used for a polyline or the outline
   *  of a polygon or circle.
   *  DASH = 0;
   *  DOT = 1;
   *  GAP = 2;
   */
  export type PatternItemType = 0 | 1 | 2;

  /**
   *  The stroke pattern of a polyline or the outline of a polygon or circle
   */
  export interface PatternItem {
    /**
     *  Type of the stroke pattern.
     */
    type: PatternItemType;

    /**
     *  Length of a gap, in pixels.
     *  Not needed for `DOT` type
     */
    length?: number;
  }

  /**
   *  Hue colors defined in sdk.
   *  RED = 0.0;
   *  ORANGE = 30.0;
   *  YELLOW = 60.0;
   *  GREEN = 120.0;
   *  CYAN = 180.0;
   *  AZURE = 210.0;
   *  BLUE = 240.0;
   *  VIOLET = 270.0;
   *  MAGENTA = 300.0;
   *  ROSE = 330.0;
   */
  export type Hue =
    | 0.0
    | 30.0
    | 60.0
    | 120.0
    | 180.0
    | 210.0
    | 240.0
    | 270.0
    | 300.0
    | 330.0;

  /**
   *  Creates the definition of a bitmap image.
   */
  export interface BitmapDescriptor {
    /**
     *  Creates object for default marker icons in
     *  different colors using different hue values.
     *  Possible values are 0, 30, 60, 120, 180, 210, 240, 270, 300, 330
     */
    hue?: Hue;

    /**
     *  Creates object using an image resource in the assets directory.
     */
    asset?: string;

    /**
     *  Creates object using  the name of an image file
     *  in the internal storage.
     */
    file?: string;

    /**
     *  Creates object using the absolute path to an image resource.
     */
    path?: string;

    /**
    *  Creates object using the uri of the image resource.
    */
    uri?: string;

    /**
    *  Width of the image for images that we get from uri.
    */
    width?: number;

    /**
    *  Height of the image for images that we get from uri.
    */
    height?: number;
  }

  /**
   *  Type of the cap that is applied at the start or end vertex of a polyline.
   *  BUTT = 0;
   *  SQUARE = 1;
   *  ROUND = 2;
   *  CUSTOM = 3;
   */
  export type CapType = 0 | 1 | 2 | 3;

  /**
   *  Defines a cap that is applied at the start or end vertex of a polyline.
   */
  export interface Cap extends BitmapDescriptor {
    /**
     *  Type of the cap.
     */
    type: CapType;

    /**
     *  Reference stroke width, in pixels when using `CUSTOM` type cap.
     */
    refWidth?: number;
  }

  export interface TileProvider {
    /**
     *  URL String for tiles. Ex: "https://a.tile.openstreetmap.org/{z}/{x}/{y}.png"
     */
    url: string;

    /**
     *  List of zoom levels enabled for tile provider.
     */
    zoom?: number[];

    /**
     *  Width of a tile, in pixels.
     */
    width?: number;

    /**
     *  Height of a tile, in pixels.
     */
    height?: number;
  }

  export interface CustomTile {
    /**
     *  Name of the file under assets folder
     */
    asset: string;

    /**
     *  Horizontal tile index.
     */
    x: number;

    /**
     *  Vertical tile index.
     */
    y: number;

    /**
     *  Zoom level.
     */
    zoom: number;

    /**
     *  Width of a tile, in pixels.
     */
    width?: number;

    /**
     *  Height of a tile, in pixels.
     */
    height?: number;
  }

  /**
   * FORWARDS = 0
   * BACKWARDS = 1
   */
  export type FillMode = 0 | 1;

  /**
   * RESTART = 1
   * REVERSE = 2
   */
  export type RepeatMode = 1 | 2;

  /**
   *   LINEAR = 0
   * ACCELERATE = 1
   * ANTICIPATE = 2
   * BOUNCE = 3
   * DECELERATE = 4
   * OVERSHOOT = 5
   * ACCELERATE_DECELERATE = 6
   * FAST_OUT_LINEAR_IN = 7
   * FAST_OUT_SLOW_IN = 8
   * LINEAR_OUT_SLOW_IN = 9
   */
  export type Interpolator = 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9;

  /**
   * Marker animation that rotates the marker
   */
  export interface Rotate {
    fromDegree: number;
    toDegree: number;
    duration?: number;
    fillMode?: FillMode;
    repeatCount?: number;
    repeatMode?: RepeatMode;
    interpolator?: Interpolator;
  }

  /**
   * Marker animation that changes the opacity of marker
   */
  export interface Alpha {
    fromAlpha: number;
    toAlpha: number;
    duration?: number;
    fillMode?: FillMode;
    repeatCount?: number;
    repeatMode?: RepeatMode;
    interpolator?: Interpolator;
  }

  /**
   * Marker animation that scales the marker vertically and horizontally
   */
  export interface Scale {
    fromX: number;
    toX: number;
    fromY: number;
    toY: number;
    duration?: number;
    fillMode?: FillMode;
    repeatCount?: number;
    repeatMode?: RepeatMode;
    interpolator?: Interpolator;
  }

  /**
   * Marker animation that moves the marker to the target coordinate
   */
  export interface Translate {
    latitude: number;
    longitude: number;
    duration?: number;
    fillMode?: FillMode;
    repeatCount?: number;
    repeatMode?: RepeatMode;
    interpolator?: Interpolator;
  }

  /**
   * Default options for common fields in all animation types. 
   */
  export interface DefaultAnimationOptions {
    duration?: number;
    fillMode?: FillMode;
    repeatCount?: number;
    repeatMode?: RepeatMode;
    interpolator?: Interpolator;
  }

  /**
   * Animation object to specify what type of animations will be applied to the marker.
   */
  export interface MarkerAnimation {
    rotate?: Rotate;
    alpha?: Alpha;
    scale?: Scale;
    translate?: Translate;
  }

  /**
   *  Events triggered by the map.
   */
  export interface MapEvent<T = {}> extends NativeSyntheticEvent<T> { }

  /**
   *  Props for <HMSCircle> component.
   */
  export interface HMSCircleProps {
    /**
     *  Center of the circle.
     */
    center: LatLng;

    /**
     *  Radius of the circle.
     */
    radius: number;

    /**
     *  Whether the circle is tappable.
     */
    clickable?: boolean;

    /**
     *  The fill color of the circle, in ARGB format.
     */
    fillColor?: number | number[];

    /**
     *  The stroke color of the circle's outline.
     */
    strokeColor?: number | number[];

    /**
     *  The stroke width of the circle's outline.
     */
    strokeWidth?: number;

    /**
     *  Stroke pattern of the circle's outline.
     */
    strokePattern?: PatternItem[];

    /**
     *  Whether the circle is visible.
     */
    visible?: boolean;

    /**
     *  The z-index of the circle. The z-index indicates the overlapping order
     *  of the circle. A circle with a larger z-index overlaps that with
     *  a smaller z-index. Circles with the same z-index overlap each other
     *  in a random order. By default, the z-index is 0.
     */
    zIndex?: number;

    /**
     *  Event listener for clicks on the cricle.
     */
    onClick?: (event: MapEvent<{}>) => void;
  }

  /**
   *  React component that shows a circle object on the map.
   */
  export class HMSCircle extends React.Component<HMSCircleProps, any> { }

  /**
   *  The joint type for a polyline or the outline of a polygon.
   *  DEFAULT = 0;
   *  BEVEL = 1;
   *  ROUND = 2;
   */
  export type JointType = 0 | 1 | 2;

  /**
   *  Props for <HMSPolygon> component.
   */
  export interface HMSPolygonProps {
    /**
     *  Vertex coordinates of the polygon.
     */
    points?: LatLng[];

    /**
     *  Holes in the polygon.
     */
    holes?: LatLng[][];

    /**
     *  Whether the polygon is tappable.
     */
    clickable?: boolean;

    /**
     *  The fill color of the polygon, in ARGB format.
     */
    fillColor?: number | number[];

    /**
     *  Whether each segment of the polygon is drawn as a geodesic.
     */
    geodesic?: boolean;

    /**
     *  The stroke color of the polygon's outline.
     */
    strokeColor?: number | number[];

    /**
     *  The joint type of the polygon.
     */
    strokeJointType: JointType;

    /**
     *  The stroke width of the polygon's outline, in pixels.
     */
    strokeWidth?: number;

    /**
     *  The stroke pattern of the polygon's outline. By default,
     *  the stroke pattern is solid, represented by null.
     */
    strokePattern?: PatternItem[];

    /**
     *  Whether the polygon is visible.
     */
    visible?: boolean;

    /**
     *  The z-index of the polygon. The z-index indicates the overlapping order
     *  of the polygon. A polygon with a larger z-index overlaps that with
     *  a smaller z-index. Polygons with the same z-index overlap each other in
     *  a random order.
     */
    zIndex?: number;

    /**
     *  Event listener for clicks on the polygon.
     */
    onClick?: (event: MapEvent<{}>) => void;
  }

  /**
   *  React component that shows a polygon object on the map.
   *  A polygon can be convex or concave. It can span
   *  the 180 meridian and have holes that are not filled in.
   */
  export class HMSPolygon extends React.Component<HMSPolygonProps, any> { }

  /**
   *  Props for <HMSPolyline> component.
   */
  export interface HMSPolylineProps {
    /**
     *  Vertex set of the polyline.
     */
    points?: LatLng[];

    /**
     *  Whether the polyline is tappable.
     */
    clickable?: boolean;

    /**
     *  The stroke color of a polyline in ARGB format.
     */
    color?: number | number[];

    /**
     *  Whether each segment of the polyline is drawn as a geodesic.
     */
    geodesic?: boolean;

    /**
     *  The joint type of all vertices of the polyline,
     *  except the start and end vertices.
     */
    jointType: JointType;

    /**
     *  The stroke pattern of the polyline.
     */
    pattern?: PatternItem[];

    /**
     *  The start vertex of the polyline.
     */
    startCap?: Cap;

    /**
     *  The end vertex of the polyline.
     */
    endCap?: Cap;

    /**
     *  The stroke width of the polyline.
     */
    width?: number;

    /**
     *  Whether the polyline is visible.
     */
    visible?: boolean;

    /**
     *  The z-index of a polyline. The z-index indicates the overlapping order
     *  of a polyline. A polyline with a larger z-index overlaps that with
     *  a smaller z-index. Polylines with the same z-index overlap each other
     *  in any order.
     */
    zIndex?: number;

    /**
     *  Event listener for clicks on the polyline.
     */
    onClick?: (event: MapEvent<{}>) => void;
  }

  /**
   *  React component that shows a polyline object, which is a list of
   *  vertices where line segments are drawn between consecutive
   *  vertices, on the map.
   */
  export class HMSPolyline extends React.Component<HMSPolylineProps, any> { }

  /**
   *  Props for <HMSMarker> component.
   */
  export interface HMSMarkerProps {
    /**
     *  The position of a marker.
     */
    coordinate: LatLng;

    /**
     *  Whether the marker can be dragged.
     */
    draggable?: boolean;

    /**
     *  The icon of the marker.
     */
    icon?: BitmapDescriptor;

    /**
     *  The transparency of the marker.
     */
    alpha?: number;

    /**
     *  Whether the marker is flatly attached to the map.
     */
    flat?: boolean;

    /**
     *  The anchor point of the marker. The anchor point is used to anchor
     *  a marker image to the map.
     *  The coordinates [0, 0], [1, 0], [0, 1], and [1, 1] respectively
     *  indicate the top-left, top-right, bottom-left, and bottom-right
     *  corners of the marker image.
     */
    markerAnchor?: [number, number];

    /**
     *  The anchor point of the marker's information window. The anchor point
     *  is used to anchor a marker image to the map.
     *  The coordinates [0, 0], [1, 0], [0, 1], and [1, 1] respectively
     *  indicate the top-left, top-right, bottom-left, and bottom-right
     *  corners of the marker image.
     */
    infoWindowAnchor?: [number, number];

    /**
     *  The rotation angle of the marker.
     */
    rotation?: number;

    /**
     *  The title of the marker.
     */
    title?: string;

    /**
     *  The text of the marker.
     */
    snippet?: string;

    /**
     *  Whether the marker is visible.
     */
    visible?: boolean;

    /**
     *  The z-index of the marker. The z-index indicates the overlapping order
     *  of a marker. A marker with a larger z-index overlaps that with
     *  a smaller z-index. Markers with the same z-index overlap each other
     *  in a random order.
     */
    zIndex?: number;

    /**
     *  Whether the marker can be clustered.
     */
    clusterable?: boolean;

    /**
    *  Whether the animation does the default action on marker click.
    */
    defaultActionOnClick?: boolean;

    /**
     *  Listener for the event called when the marker is clicked.
     */
    onClick?: (event: MapEvent<{}>) => void;

    /**
     *  Listener for the event called when the marker starts being dragged.
     */
    onDragStart?: (event: MapEvent<{}>) => void;

    /**
     *  Listener for the event called when the marker is being dragged.
     */
    onDrag?: (event: MapEvent<{}>) => void;

    /**
     *  Listener for the event called when the marker dragging is complete.
     */
    onDragEnd?: (event: MapEvent<{}>) => void;

    /**
     *  Event listener for clicks on the information window.
     */
    onInfoWindowClick?: (event: MapEvent<{}>) => void;

    /**
     *  Listener for the event called when information window is closed.
     */
    onInfoWindowClose?: (event: MapEvent<{}>) => void;

    /**
     *  Event listener for long clicks on the information window.
     */
    onInfoWindowLongClick?: (event: MapEvent<{}>) => void;

    /**
     *  Listener for the event called animation starts.
     */
    onAnimationStart?: (event: MapEvent<{}>) => void;

    /**
     *  Listener for the event called animation starts.
     */
    onAnimationEnd?: (event: MapEvent<{}>) => void;
  }

  /**
   *  React component that shows a marker object, an icon placed at
   *  a specified position, on the map.
   */
  export class HMSMarker extends React.Component<HMSMarkerProps, any> {
    /**
     *  Shows infromation window.
     */
    showInfoWindow(): void;

    /**
     *  Hides infromation window.
     */
    hideInfoWindow(): void;

    /**
     *  Starts marker animation.
     */
    startAnimation(): void;

    /**
     *  Cleans the animation which is previously set
     */
    cleanAnimation(): void;

    /**
     * Sets the marker animation
     */
    setAnimation(markerAnimation: MarkerAnimation, defaultAnimationOptions: DefaultAnimationOptions): void;

  }

  /**
   *  React component that shows infromation window on a marker
   */
  export class HMSInfoWindow extends React.Component<ViewProps, any> { }

  export interface LatLngWithSize extends LatLng {
    /**
     *  Height of a ground overlay, in meters
     */
    height: number;

    /**
     *  Width of a ground overlay, in meters
     */
    width: number;
  }

  /**
   *  Props for <HMSGroundOverlay> component.
   */
  export interface HMSGroundOverlayProps {
    /**
     *  The image for the ground overlay.
     */
    image: BitmapDescriptor;

    /**
     *  The position of the ground overlay.
     */
    coordinate: LatLngWithSize | LatLng[];

    /**
     *  The alignment (that is, anchor point) of a ground overlay.
     *  The coordinates [0, 0], [1, 0], [0, 1], and [1, 1] respectively
     *  indicate the top-left, top-right, bottom-left, and bottom-right
     *  corners of the ground overlay. If no anchor point is set, the center
     *  point (0.5, 0.5) of the ground overlay will be used by default.
     */
    anchor?: [number, number];

    /**
     *  The bearing of the ground overlay, in degrees clockwise from north.
     *  The value ranges from 0 to 360 (excluded).
     */
    bearing?: number;

    /**
     *  Whether the ground overlay is tappable.
     */
    clickable?: boolean;

    /**
     *  The transparency of the ground overlay.The value ranges from 0 to 1.
     *  The value 0 indicates opaque and the value 1 indicates transparent.
     */
    transparency?: number;

    /**
     *  Whether the ground overlay is visible.
     */
    visible?: boolean;

    /**
     *  The z-index of the ground overlay. The z-index indicates
     *  the overlapping order of a ground overlay. A ground overlay with
     *  a larger z-index overlaps that with a smaller z-index. Ground overlays
     *  with the same z-index overlap each other in a random order.
     *  By default, the z-index is 0.
     */
    zIndex?: number;

    /**
     *  Listener for the event called when the ground overlay is clicked.
     */
    onClick?: (event: MapEvent<{}>) => void;
  }

  /**
   *  React component that shows a ground overlay object, an image
   *  that is fixed to the map.
   */
  export class HMSGroundOverlay extends React.Component<HMSGroundOverlayProps, any> { }

  /**
   *  Props for <HMSTileOverlay> component.
   */
  export interface HMSTileOverlayProps {
    /**
     *  The provider of the tile overlay.
     */
    tileProvider: TileProvider | CustomTile[];

    /**
     *  Whether the tile overlay fades in.
     */
    fadeIn?: boolean;

    /**
     *  The transparency of the tile overlay.
     */
    transparency?: number;

    /**
     *  Whether the tile overlay is visible.
     */
    visible?: boolean;

    /**
     *  The z-index of the tile overlay. The z-index indicates the overlapping
     *  order of a tile overlay. A tile overlay with a larger z-index overlaps
     *  that with a smaller z-index. Tile overlays with the same z-index
     *  overlap each other in any order.
     */
    zIndex?: number;
  }

  /**
   *  React component that shows a tile overlay object,
   *  which is a set of images to be displayed on a map.
   */
  export class HMSTileOverlay extends React.Component<HMSTileOverlayProps, any> {
    /**
     *  Clears the cache of the tile overlay.
     */
    clearTileCache(): void;
  }

  /**
   *  Padding on a map.
   */
  export interface MapPadding {
    /**
     *  Distance from the visible region to the right edge
     *  of the map, in pixels.
     */
    right?: number;

    /**
     *  Distance from the visible region to the left edge
     *  of the map, in pixels.
     */
    left?: number;

    /**
     *  Distance from the visible region to the top edge
     *  of the map, in pixels.
     */
    top?: number;

    /**
     *  Distance from the visible region to the bottom edge
     *  of the map, in pixels.
     */
    bottom?: number;
  }

  /**
   *  Contains bitmap string of the snapshot image.
   */
  export interface SnapshotImage {
    /**
     *  Bitmap string of the snapshot image.
     */
    bitmap: string;
  }

  /**
   *  The type of the map.
   *  NONE = 0; Empty grid map.
   *  NORMAL = 1; Basic map.
   *  TERRAIN = 3; Terrain map.
   */
  export type MapType = 0 | 1 | 3;

  /**
   *  TOP = 48; 
   *  BOTTOM = 80; 
   *  START = 8388611; 
   *  END = 8388613; 
   */
  export type Gravity = 48 | 80 | 8388611 | 8388613;

  /**
   *  Props for <MapView> component.
   */
  export interface HMSMapProps extends ViewProps {

    /**
     * Sets the color of the default cluster marker. 
     * The color value is in ARGB format.
     */
    markerClusterColor?: number | number[];

    /**
     * Sets the icon of the custom cluster marker. 
     * If the bitmapDescriptor parameter is empty for the setMarkerClusterIcon method,
     * the color, image, and text color of the cluster marker set using setMarkerClusterColor, 
     * setMarkerClusterIcon, and setMarkerClusterTextColor will be cleared. 
     * In this case, the default cluster marker style will be used.
     */
    markerClusterIcon?: BitmapDescriptor;

    /**
     * Sets the text color of the custom cluster marker.
     * The color value is in ARGB format.
     */
    markerClusterTextColor?: number | number[];

    /**
     * Indicates whether to enable the traffic status layer. 
     * The options are true (yes) and false (no).
     * The default value is false.
     */
    trafficEnabled?: boolean;

    /**
     * Sets a fixed screen center for zooming.
     * The passed screen center is valid only when true is passed to setGestureScaleByMapCenter prop.
     * To cancel the function of setting a fixed screen center,
     * you only need to pass false to setGestureScaleByMapCenter prop without calling setPointToCenter.
     */
    pointToCenter?: Point;

    /**
     * Specifies whether a fixed screen center can be set for zooming.
     * If the function is enabled, the map will be zoomed based on the passed fixed screen center. 
     */
    gestureScaleByMapCenter?: boolean;

    /**
     *  Starting position of the camera on the map.
     */
    camera?: CameraPosition;

    /**
     *  Bounds to constraint the camera target so that the camera target does
     *  not move outside the bounds when a user scrolls the map.
     */
    latLngBoundsForCameraTarget?: LatLng[];

    /**
     *  Whether to use animation on camera update.
     */
    useAnimation?: boolean;

    /**
     *  Duration of the animation in ms.  By default, the camera animation
     *  takes 250 milliseconds
     */
    animationDuration?: number;

    /**
     *  Whether to enable the compass for the map.
     */
    compassEnabled?: boolean;

    /**
     *  Type of the map.
     */
    mapType?: MapType;

    /**
     *  The preferred minimum zoom level of the camera. The value must be
     *  greater than or equal to the minimum zoom level (0) supported by
     *  the HUAWEI Map SDK. If the preferred minimum zoom level is higher
     *  than the current maximum zoom level, the SDK uses the preferred
     *  minimum zoom level as both the minimum and maximum zoom levels.
     *  Assume that the current minimum and maximum zoom levels are
     *  4 and 10, respectively. If you set the preferred minimum zoom level
     *  to 15, the SDK uses the value 15 as both the minimum and maximum zoom
     *  levels. That is, the zoom level of the camera is fixed at 15.
     */
    minZoomPreference?: number;

    /**
     *  The preferred maximum zoom level of the camera. If the preferred
     *  maximum zoom level is lower than the current minimum zoom level,
     *  the SDK uses the preferred maximum zoom level as both the minimum
     *  and maximum zoom levels. Assume that the current minimum and maximum
     *  zoom levels are 6 and 15, respectively. If you set the preferred
     *  maximum zoom level to 4, the SDK uses the value 4 as both the minimum
     *  and maximum zoom levels. That is, the zoom level of the camera is
     *  fixed at 4.
     */
    maxZoomPreference?: number;

    /**
     *  Whether rotate gestures are enabled for the map.
     */
    rotateGesturesEnabled?: boolean;

    /**
     *  Whether scroll gestures are enabled for the map.
     */
    scrollGesturesEnabled?: boolean;

    /**
     *  Whether tilt gestures are enabled for the map.
     */
    tiltGesturesEnabled?: boolean;

    /**
     *  Whether to enable the zoom function for the camera.
     */
    zoomControlsEnabled?: boolean;

    /**
     *  Whether zoom gestures are enabled for the map.
     */
    zoomGesturesEnabled?: boolean;

    /**
     *  Whether the 3D building layer is enabled for the map.
     */
    buildingsEnabled?: boolean;

    /**
     *  the content description to the map. If the auxiliary mode is enabled,
     *  voice description about the map will be provided.
     */
    description?: string;

    /**
     *  JSON string for styling the map.
     */
    mapStyle?: string;

    /**
     *  Whether my location layer is enabled for the map.
     */
    myLocationEnabled?: boolean;

    /**
     *  The padding on the map. You can use this props to define the visible
     *  region on a map so that a signal can be sent to the map indicating that
     *  some portions around the map edges may be obscured. For example, icons
     *  such as the zoom controls and compass will be moved to adapt to
     *  the visible region, and the camera will move in relative to
     *  the center of the visible region.
     */
    mapPadding?: MapPadding;

    /**
     *  Whether the markers can be clustered.
     */
    markerClustering?: boolean;

    /**
     *  Whether to enable the my location icon for a map.
     */
    myLocationButtonEnabled?: boolean;

    /**
     *  Whether to enable scroll gestures during rotation or zooming.
     */
    scrollGesturesEnabledDuringRotateOrZoom?: boolean;

    /**
     *  Listener for the event called when the HuaweiMap object is ready
     */
    onMapReady?: (event: MapEvent<{}>) => void;

    /**
     *  Listener for the event when a camera update task is complete
     */
    onCameraUpdateFinished?: (event: MapEvent<{}>) => void;

    /**
     *  Listener for the event when a camera update task is canceled.
     */
    onCameraUpdateCanceled?: (event: MapEvent<{}>) => void;

    /**
     *  Listener for the event when the camera movement ends.
     */
    onCameraIdle?: (event: MapEvent<CameraPosition>) => void;

    /**
     *  Listener for the event when the camera movement cancelled.
     */
    onCameraMoveCanceled?: (event: MapEvent<{}>) => void;

    /**
     *  Listener for the event when the camera moves
     */
    onCameraMove?: (event: MapEvent<{ CameraPosition }>) => void;

    /**
     *  Listener for the event when the camera movement started.
     */
    onCameraMoveStarted?: (event: MapEvent<{ reason: number }>) => void;

    /**
     *  Event listener for clicks on the map.
     */
    onMapClick?: (event: MapEvent<ProjectionOnLatLng>) => void;

    /**
     *  Listener for the event called when the map loading is completed
     */
    onMapLoaded?: (event: MapEvent<{}>) => void;

    /**
     *  Event listener for long clicks on the map.
     */
    onMapLongClick?: (event: MapEvent<ProjectionOnLatLng>) => void;

    /**
     *  Listener for the event called when my location button is clicked
     */
    onMyLocationButtonClick?: (event: MapEvent<{}>) => void;

    /**
     *  Event listener for clicks on the location icon.
     */
    onMyLocationClick?: (event: MapEvent<{}>) => void;

    /**
     *  Event listener for clicks on POIs
     */
    onPoiClick?: (event: MapEvent<PointOfInterest>) => void;

    /**
     *  Listener for the event called when a snapshot is taken on the map.
     *  To take a snapshot use `takeSnapshot()` function.
     */
    onSnapshotReady?: (event: MapEvent<SnapshotImage>) => void;
  }

  /**
   *  React component that shows a map.
   */
  export default class HMSMap extends React.Component<HMSMapProps, any> {
    /**
     *  Obtains all attributes of the Huawei map object
     */
    getHuaweiMapInfo(): Promise<HuaweiMap>;

    /**
     *  Obtains a location on the screen corresponding to a longitude
     *  and latitude. The location on the screen is specified in
     *  screen pixels (instead of display pixels) relative to the top left
     *  corner of the map (instead of the top left corner of the screen).
     */
    getPointFromCoordinate(coordinate: LatLng): Promise<Point>;

    /**
     *  Obtains the longitude and latitude of a location on the screen.
     *  The location on the screen is specified in
     *  screen pixels (instead of display pixels) relative to the top left
     *  corner of the map (instead of the top left corner of the screen).
     */
    getCoordinateFromPoint(point: Point): Promise<LatLng>;

    /**
     *  Calculates the distance between two coordinate points.
     */
    getDistance(from: LatLng, to: LatLng): Promise<number>;

    /**
     *  Removes all circles, markers, polylines, and ground overlays
     *  from the map.
     */
    clear(): void;

    /**
     *  Takes a snapshot of a map. If an interactive map is difficult or
     *  impossible to use, you can use snapshots in your app. For example,
     *  you can use this function to generate images and use them to display
     *  a thumbnail of the map in your app, or display a snapshot in
     *  the notification center.
     */
    takeSnapshot(): void;

    /**
     *  Deletes all maximum and minimum zoom levels set previously.
     */
    resetMinMaxZoomPreference(): void;

    /**
     *  Stops the current animation process of the camera.
     *  When this method is called, the camera stops moving
     *  immediately and remains in that position.
     */
    stopAnimation(): void;

    /**
     *  Updates the camera position
     */
    setCameraPosition(cameraPosition: CameraPosition): void;

    /**
     *  Sets the longitude and latitude of the center as well as the zoom level
     *  of a map's view.
     */
    setCoordinates(
      /**
       *  Longitude and latitude of the center of a map's view.
       */
      latLng: LatLng,

      /**
       *  Desired zoom level of the camera.
       */
      zoom?: number,
    ): void;

    /**
     *  Centers a region on the screen by setting the width and height of
     *  the region, longitude and latitude bounds, and the padding between
     *  the region edges and the longitude/latitude bounding box edges.
     */
    setBounds(
      /**
       *  Longitude and latitude bounds to be displayed.
       */
      latLngBounds: LatLng[],

      /**
       *  Space between the region edges and bounding box edges, in pixels.
       */
      padding: number,

      /**
       *  Bounding box height, in pixels.
       */
      width?: number,

      /**
       *  Desired zoom level of the camera.
       */
      height?: number,
    ): void;

    /**
     *  Moves the center of a map's view by pixel on the screen.
     */
    scrollBy(
      /**
       *  Number of pixels to scroll horizontally.
       */
      x: number,

      /**
       *  Number of pixels to scroll vertically.
       */
      y: number,
    ): void;

    /**
     *  Changes the zoom level of the camera by a specified incremental value
     *  and sets a specified point as the focus. The distance to the earth
     *  surface is shortened if amount has a positive value and prolonged
     *  if amount has a negative value. In the method, focus is the center
     *  point for zooming in or out.
     */
    zoomBy(
      /**
       *  Incremental value to change the zoom level.
       */
      amount: number,

      /**
       *  Coordinates of a point to be set as the focus.
       */
      focus?: Point,
    ): void;

    /**
     *  Sets the zoom level of the camera to a specified value.
     */
    zoomTo(
      /**
       *  Desired zoom level.
       */
      zoom: number,
    ): void;

    /**
     *  Increases the zoom level of the camera by 1.
     */
    zoomIn(): void;

    /**
     *  Decreases the zoom level of the camera by 1.
     */
    zoomOut(): void;

    /**
     * Enables HMSLogger
     */
    enableLogger(): void;

    /**
     * Disables HMSLogger
     */
    disableLogger(): void;

    /**
     * Gets the layer info about map layer objects
     * @param ref Reference object 
     */
    getLayerInfo(ref: object): Promise<object>;

    /**
     * Gets the layer options info about map layer objects
     * @param ref Reference object 
     */
    getLayerOptionsInfo(ref: object): Promise<object>;
  }
}
