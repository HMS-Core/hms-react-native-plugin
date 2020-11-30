# React-Native HMS MAP

---

## Contents

  - [1. Introduction](#1-introduction)
  - [2. Installation Guide](#2-installation-guide)
  - [3. API Reference](#3-api-reference)
  - [4. Configuration and Description](#4-configuration-and-description)
  - [5. Sample Project](#5-sample-project)
  - [6. Questions or Issues](#6-questions-or-issues)
  - [7. Licensing and Terms](#7-licensing-and-terms)

---

## 1. Introduction

This module enables communication between Huawei Map SDK and React Native platform. It exposes all functionality provided by Huawei Map SDK.

---

## 2. Installation Guide

### AppGallery Connect Integration

* Add maven repository address and AppGallery Connect service dependencies into 'android/build.gradle' file.

```
maven {url 'https://developer.huawei.com/repo/'}
classpath 'com.huawei.agconnect:agcp:1.4.1.300'
```

* Add AppGallery Connect plugin into 'android/app/build.gradle' file.

```groovy
apply plugin: 'com.huawei.agconnect'
```

* Download 'agconnect-services.json' file and put it under 'android/app' folder.

* Put keystore file under 'android/app' folder. Add signing configuration into 'android/app/build.gradle' file.

```groovy
signingConfigs {
        release {
            storeFile file('<keystore>')
            storePassword '<storePassword>'
            keyAlias '<keyAlias>'
            keyPassword '<keyPassword>'
        }

        debug {
            storeFile file('<keystore>')
            storePassword '<storePassword>'
            keyAlias '<keyAlias>'
            keyPassword '<keyPassword>'
        }
    }
    buildTypes {
        debug {
            signingConfig signingConfigs.debug
        }
        release {
            signingConfig signingConfigs.release
            minifyEnabled enableProguardInReleaseBuilds
            proguardFiles getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
        }
    }
```

### Integrating the React Native HMS Map

#### Using NPM

- You can download the module from [npm](https://www.npmjs.com/package/@hmscore/react-native-hms-map)

  `npm i @hmscore/react-native-hms-map`

#### Using Download Link

- Download the module and copy it into *node_modules/@hmscore* folder. If *@hmscore* folder does not exist, create one. The folder structure can be seen below;

```
project-name
    |_ node_modules
        |_ @hmscore
        	|_ react-native-hms-map
        |_ ...
```

- Add following lines into 'android/settings.gradle' file

```gradle
include ':react-native-hms-map'
project(':react-native-hms-map').projectDir = new File(rootProject.projectDir, '../node_modules/@hmscore/react-native-hms-map/android')
```

- Add 'react-native-hms-map' dependency into 'android/app/build.gradle' file.

```groovy
implementation project(":react-native-hms-map")
```

- Add 'HMSMapPackage' to your application.

```java
import com.huawei.hms.rn.map.HMSMapPackage;
...
...

@Override
protected List<ReactPackage> getPackages() {
  @SuppressWarnings("UnnecessaryLocalVariable")
  List<ReactPackage> packages = new PackageList(this).getPackages();
  packages.add(new HMSMapPackage());
  return packages;
}
```

</details>

Run the app by executing following command.

```bash
react-native run-android
```

---

## 3. API Reference

### Data Types

#### LatLng

Defines the longitude and latitude, in degrees.

| Field       | Required | Type   | Description                                              |
| ----------- | -------- | ------ | -------------------------------------------------------- |
| `latitude`  | yes      | number | Latitude. The value ranges from –90 to 90.               |
| `longitude` | yes      | number | Longitude. The value ranges from –180 to 180 (excluded). |

#### LatLngWithSize

Used for showing ground overlay images.

| Field       | Required | Type   | Description                                              |
| ----------- | -------- | ------ | -------------------------------------------------------- |
| `latitude`  | yes      | number | Latitude. The value ranges from –90 to 90.               |
| `longitude` | yes      | number | Longitude. The value ranges from –180 to 180 (excluded). |
| `height`    | yes      | number | Height of a ground overlay, in meters                    |
| `width`     | yes      | number | Width of a ground overlay, in meters.                    |

#### LatLngBounds

Used for showing ground overlay images.

| Field       | Type                | Description                                              |
| ----------- | ------------------- | -------------------------------------------------------- |
| `northeast` | [LatLng](#latlng) | Latitude. The value ranges from –90 to 90.               |
| `southwest` | [LatLng](#latlng) | Longitude. The value ranges from –180 to 180 (excluded). |

#### UiSettings

Contains information of UI and gesture controls of the map.

| Field                                       | Type    | Description                                                  |
| ------------------------------------------- | ------- | ------------------------------------------------------------ |
| `isCompassEnabled`                          | boolean | Shows whether the compass is enabled for the map.            |
| `isMyLocationButtonEnabled`                 | boolean | Shows whether the my-location icon is enabled for the map.   |
| `isRotateGesturesEnabled`                   | boolean | Shows whether rotate gestures are enabled for the map.       |
| `isScrollGesturesEnabled`                   | boolean | Shows whether scroll gestures are enabled for the map.       |
| `isScrollGesturesEnabledDuringRotateOrZoom` | boolean | Shows whether scroll gestures are enabled during rotation or zooming. |
| `isTiltGesturesEnabled`                     | boolean | Shows whether tilt gestures are enabled for the map.         |
| `isZoomControlsEnabled`                     | boolean | Shows whether zoom controls are enabled.                     |
| `isZoomGesturesEnabled`                     | boolean | Shows whether zoom gestures are enabled for the map.         |

#### CameraPosition

Contains all camera position parameters.

| Field     | Required | Type                | Description                                                  |
| --------- | -------- | ------------------- | ------------------------------------------------------------ |
| `target`  | yes      | [LatLng](#latlng) | Longitude and latitude of the location that the camera is pointing at. |
| `zoom`    | no       | number              | Zoom level near the center of the screen.                    |
| `tilt`    | no       | number              | Angle of the camera from the nadir (directly facing the Earth's surface). |
| `bearing` | no       | number              | Direction that the camera is pointing in.                    |

#### VisibleRegion

It contains four points that define a tetragon visible in the camera of a map. The tetragon may be a trapezoid instead of rectangle because the camera may tilt. If the camera is directly over the center of the visible region, the shape is rectangular. If the camera tilts, the shape will be a trapezoid whose smallest side is closest to the point of view.

| Field          | Type                            | Description                                             |
| -------------- | ------------------------------- | ------------------------------------------------------- |
| `farLeft`      | [LatLng](#latlng)             | Far left corner of the camera.                          |
| `farRight`     | [LatLng](#latlng)             | Far right corner of the camera.                         |
| `nearLeft`     | [LatLng](#latlng)             | Near left corner of the camera.                         |
| `nearRight`    | [LatLng](#latlng)             | Near right corner of the camera.                        |
| `latLngBounds` | [LatLngBounds](#latlngbounds) | Smallest bounding box that includes the visible region. |

#### HuaweiMap

Contains map information.

| Field                 | Type                                | Description                                               |
| --------------------- | ----------------------------------- | --------------------------------------------------------- |
| `cameraPosition`      | [CameraPosition](#cameraposition) | The current position of the camera.                       |
| `mapType`             | number                              | The current map type.                                     |
| `maxZoomLevel`        | number                              | The maximum zoom level at the current camera position.    |
| `minZoomLevel`        | number                              | The minimum zoom level at the current camera position.    |
| `visibleRegion`       | [VisibleRegion](#visibleregion)   | The current visible region on the map.                    |
| `uiSettings`          | [UiSettings](#uisettings)         | The current UI settings.                                  |
| `isBuildingsEnabled`  | boolean                             | Shows whether the 3D building layer is enabled for a map. |
| `isMyLocationEnabled` | boolean                             | Shows whether my location layer is enabled for a map.     |

#### Point

Coordinates of a location on the screen, in pixels.

| Field | Required | Type   | Description                         |
| ----- | -------- | ------ | ----------------------------------- |
| `x`   | yes      | number | Coordinate on the screen in x-axis. |
| `y`   | yes      | number | Coordinate on the screen in y-axis. |

#### PointOfInterest

Contains attributes about the POI.

| Field     | Type                | Description          |
| --------- | ------------------- | -------------------- |
| `latLng`  | [LatLng](#latlng) | Position of the POI. |
| `name`    | string              | Name of the POI.     |
| `placeId` | string              | Place id of the POI. |

#### ProjectionOnLatLng

Contains information of clicked(or long-clicked) place on the map.

| Field           | Type                              | Description                            |
| --------------- | --------------------------------- | -------------------------------------- |
| `point`         | [Point](#point)                 | Point of the place.                    |
| `coordinate`    | [LatLng](#latlng)               | Coordinate of the place.               |
| `visibleRegion` | [VisibleRegion](#visibleregion) | The current visible region on the map. |

#### PatternItem

The stroke pattern of a polyline or the outline of a polygon or circle.

| Field    | Required | Type                                  | Description                                                  |
| -------- | -------- | ------------------------------------- | ------------------------------------------------------------ |
| `type`   | yes      | [PatternItemType](#patternitemtype) | Type of the stroke pattern.                                  |
| `length` | no       | number                                | Length, in pixels. It is not needed for `DOT` pattern item type. |

#### BitmapDescriptor

Creates the definition of a bitmap image.

| Field   | Required | Type          | Description                                                  |
| ------- | -------- | ------------- | ------------------------------------------------------------ |
| `hue`   | no       | [Hue](#hue) | Creates object for default marker icons in different colors using different hue values. |
| `asset` | no       | string        | Creates object using an image resource in the assets directory. |
| `file`  | no       | string        | Creates object using  the name of an image file in the internal storage. |
| `path`  | no       | string        | Creates object using the absolute path to an image resource. |

#### Cap

Defines a cap that is applied at the start or end vertex of a polyline. It extends [BitmapDescriptor](#bitmapdescriptor) to show an image if `CUSTOM` type cap is used.

| Field      | Required | Type                  | Description                                                  |
| ---------- | -------- | --------------------- | ------------------------------------------------------------ |
| `type`     | yes      | [CapType](#captype) | Type of the cap.                                             |
| `refWidth` | no       | number                | Reference stroke width, in pixels when using `CUSTOM` type cap. |
| `hue`      | no       | [Hue](#hue)         | Creates object for default marker icons in different colors using different hue values. |
| `asset`    | no       | string                | Creates object using an image resource in the assets directory. |
| `file`     | no       | string                | Creates object using  the name of an image file in the internal storage. |
| `path`     | no       | string                | Creates object using the absolute path to an image resource. |

#### TileProvider

Provides tile images for TileOverlay component.

| Field    | Required | Type   | Description                                                  |
| -------- | -------- | ------ | ------------------------------------------------------------ |
| `url`    | yes      | string | URL String for tiles. Ex: "https://a.tile.openstreetmap.org/{z}/{x}/{y}.png". {z} -> Zoom level of a tile. {x} -> X coordinate of a tile. The value ranges from 0 to 2^zoom minus 1. {y} -> Y coordinate of a tile. The value ranges from 0 to 2^zoom minus 1. |
| `width`  | no       | number | Width of a tile, in pixels.                                  |
| `height` | no       | number | Height of a tile, in pixels.                                 |

#### CustomTile

A tile object when user wants to overlay not all map but only one or more tile with a local image.

| Field    | Required | Type   | Description                                                  |
| -------- | -------- | ------ | ------------------------------------------------------------ |
| `x`    | yes      | number | X coordinate of the tile. |
| `y`  | yes       | number | Y coordinate of the tile. |
| `asset` | yes       | string | Image resource in the assets directory. |
| `zoom` | yes       | number | Zoom level where the tile is shown. |
| `width`   | no | number | Width of a tile, in pixels.|
| `height`  | no | number | Height of a tile, in pixels. |

#### MapPadding

Padding on the map.

| Field    | Required | Type   | Description                                                  |
| -------- | -------- | ------ | ------------------------------------------------------------ |
| `right`  | no       | number | Distance from the visible region to the right edge of the map, in pixels. |
| `left`   | no       | number | Distance from the visible region to the left edge of the map, in pixels. |
| `top`    | no       | number | Distance from the visible region to the top edge of the map, in pixels. |
| `bottom` | no       | number | Distance from the visible region to the bottom edge of the map, in pixels. |

#### SnapshotImage

Bitmap string of the snapshot image.

| Field    | Type   | Description                          |
| -------- | ------ | ------------------------------------ |
| `bitmap` | string | Bitmap string of the snapshot image. |

#### MarkerAnimation

The animation set in order to animate a marker. When more than one animation type is written, the marker will be animate accordingly all at the same time. There are animation specific fields (which is required) and common fields (not required). You can write the common values in DefaultAnimationOptions object if they all the same for animation or you can write common field in each animation object and these written values override the default values.

| Field       | Required | Type                      | Description                                                  |
| ----------- | -------- | ------------------------- | ------------------------------------------------------------ |
| `rotate`    | no       | [Rotate](#rotate)       | Rotation animation from one degree to another degree.        |
| `alpha`     | no       | [Alpha](#alpha)         | Animated opacity change of the marker. 0 is transparent, 1 is opaque. |
| `scale`     | no       | [Scale](#scale)         | Animated change of dimensions of the marker.                 |
| `translate` | no       | [Translate](#translate) | Animated location change.                                    |

##### DefaultAnimationOptions

| Field          | Required | Type                          | Description                                                  |
| -------------- | -------- | ----------------------------- | ------------------------------------------------------------ |
| `duration`     | no       | number                        | Duration of the animation.                                   |
| `fillMode`     | no       | [FillMode](#fillmode)         | If FORWARDS, When animation ends, marker's last frame is displayed. If BACKWARDS, when animation ends, marker's first frame is displayed. |
| `repeatCount`  | no       | number                        | The number of repetition of the animation. Put **-1** if it's infinite. |
| `repeatMode`   | no       | [RepeatMode](#repeatmode)     | If REVERSE then after the animation ends, it animates from last frame to first frame. If REPEAT, after the animation ends it will go back the first frame and repeat the animation. |
| `interpolator` | no       | [Interpolator](#interpolator) | You can choose custom interpolators supported by Android platforms. See interpolator section in constants for options. |

##### Rotate

| Field          | Required | Type                          | Description                                                  |
| -------------- | -------- | ----------------------------- | ------------------------------------------------------------ |
| `fromDegree`   | yes      | number                        | Starting angle of the marker.                                |
| `toDegree`     | yes      | number                        | Final angle of the marker.                                   |
| `duration`     | no       | number                        | Duration of the animation. This will override the default value. |
| `fillMode`     | no       | [FillMode](#fillmode)         | If FORWARDS, When animation ends, marker's last frame is displayed. If BACKWARDS, when animation ends, marker's first frame is displayed. This will override the default value. |
| `repeatCount`  | no       | [RepeatMode](#repeatmode)     | The number of repetition of the animation. Put **-1** if it's infinite. This will override the default value. |
| `repeatMode`   | no       | number                        | If REVERSE then after the animation ends, it animates from last frame to first frame. If REPEAT, after the animation ends it will go back the first frame and repeat the animation. This will override the default value. |
| `interpolator` | no       | [Interpolator](#interpolator) | You can choose custom interpolators supported by Android platforms. See [interpolator](#interpolator) section in constants for options. This will override the default value. |

##### Alpha

| Field          | Required | Type                          | Description                                                  |
| -------------- | -------- | ----------------------------- | ------------------------------------------------------------ |
| `fromAlpha`    | yes      | number                        | Starting opacity of the marker. It must be in range [0,1]    |
| `toAlpha`      | yes      | number                        | Final opacity of the animation. It must be in range [0,1]    |
| `duration`     | no       | number                        | Duration of the animation. This will override the default value. |
| `fillMode`     | no       | [FillMode](#fillmode)         | If FORWARDS, When animation ends, marker's last frame is displayed. If BACKWARDS, when animation ends, marker's first frame is displayed. This will override the default value. |
| `repeatCount`  | no       | number                        | The number of repetition of the animation. Put **-1** if it's infinite. This will override the default value. |
| `repeatMode`   | no       | [RepeatMode](#repeatmode)     | If REVERSE then after the animation ends, it animates from last frame to first frame. If REPEAT, after the animation ends it will go back the first frame and repeat the animation. This will override the default value. |
| `interpolator` | no       | [Interpolator](#interpolator) | You can choose custom interpolators supported by Android platforms. See interpolator section in constants for options. This will override the default value. |

##### Scale

| Field          | Required | Type                          | Description                                                  |
| -------------- | -------- | ----------------------------- | ------------------------------------------------------------ |
| `fromX`        | yes      | number                        | Starting horizontal scale of the marker.                     |
| `toX`          | yes      | number                        | Final horizontal scale of the marker.                        |
| `fromY`        | yes      | number                        | Starting vertical scale of the marker.                       |
| `toY`          | yes      | number                        | Final vertical scale of the marker.                          |
| `duration`     | no       | number                        | Duration of the animation. This will override the default value. |
| `fillMode`     | no       | [FillMode](#fillmode)         | If FORWARDS, When animation ends, marker's last frame is displayed. If BACKWARDS, when animation ends, marker's first frame is displayed. This will override the default value. |
| `repeatCount`  | no       | number                        | The number of repetition of the animation. Put **-1** if it's infinite. This will override the default value. |
| `repeatMode`   | no       | [RepeatMode](#repeatmode)     | If REVERSE then after the animation ends, it animates from last frame to first frame. If REPEAT, after the animation ends it will go back the first frame and repeat the animation. This will override the default value. |
| `interpolator` | no       | [Interpolator](#interpolator) | You can choose custom interpolators supported by Android platforms. See interpolator section in constants for options. This will override the default value. |

##### Translate

| Field          | Required | Type                          | Description                                                  |
| -------------- | -------- | ----------------------------- | ------------------------------------------------------------ |
| `latitude`     | yes      | number                        | Target location's latitude.                                  |
| `longtitude`   | yes      | number                        | Target location's longitude.                                 |
| `duration`     | no       | number                        | Duration of the animation.                                   |
| `fillMode`     | no       | [FillMode](#fillmode)         | If FORWARDS, When animation ends, marker's last frame is displayed. If BACKWARDS, when animation ends, marker's first frame is displayed. |
| `repeatCount`  | no       | number                        | The number of repetition of the animation. Put -1 if it's infinite. |
| `repeatMode`   | no       | [RepeatMode](#repeatmode)     | If REVERSE then after the animation ends, it animates from last frame to first frame. If REPEAT, after the animation ends it will go back the first frame and repeat the animation. |
| `interpolator` | no       | [Interpolator](#interpolator) | You can choose custom interpolators supported by Android platforms. See interpolator section in constants for options. |

### Constants

#### MapType

The type of the map.

| Key      | Type    | Value | Description     |
| -------- | ------- | ----- | --------------- |
| `NONE`   | number | 0     | Empty grid map. |
| `NORMAL` | number | 1     | Basic map.      |

#### Hue

Hue colors for markers.

| Key       | Type   | Value | Description    |
| --------- | ------ | ----- | -------------- |
| `RED`     | number | 0.0   | Red marker     |
| `ORANGE`  | number | 30.0  | Orange marker  |
| `YELLOW`  | number | 60.0  | Yellow marker  |
| `GREEN`   | number | 120.0 | Green marker   |
| `CYAN`    | number | 180.0 | Cyan marker    |
| `AZURE`   | number | 210.0 | Azure marker   |
| `BLUE`    | number | 240.0 | Blue marker    |
| `VIOLET`  | number | 270.0 | Violet marker  |
| `MAGENTA` | number | 300.0 | Magenta marker |
| `ROSE`    | number | 330.0 | Rose marker    |

#### JointType

The joint type for a polyline or the outline of a polygon.

| Key       | Type    | Value | Description  |
| --------- | ------- | ----- | ------------ |
| `DEFAULT` | number | 0     | Default type |
| `BEVEL`   | number | 1     | Flat bevel   |
| `ROUND`   | number | 2     | Round        |

#### PatternItemType

| Key    | Type    | Value | Description                                                  |
| ------ | ------- | ----- | ------------------------------------------------------------ |
| `DASH` | number | 0     | Represents a dash used in the stroke pattern for a polyline or the outline of a polygon or circle. |
| `DOT`  | number | 1     | Represents a dot used in the stroke pattern for a polyline or the outline of a polygon or circle. |
| `GAP`  | number | 2     | Represents a gap used in the stroke pattern for a polyline or the outline of a polygon or circle. |

#### CapType

| Key      | Type    | Value | Description                                                  |
| -------- | ------- | ----- | ------------------------------------------------------------ |
| `BUTT`   | number | 0     | Defines a cap that is squared off exactly at the start or end vertex of a polyline. |
| `SQUARE` | number | 1     | Sets the start or end vertex of a polyline to the square type. |
| `ROUND`  | number | 2     | Represents a semicircle with a radius equal to a half of the stroke width. The semicircle will be centered at the start or end vertex of a polyline. |
| `CUSTOM` | number | 3     | Customizes the cap style for a polyline by using custom bitmap image and width. |

#### Reason

| Key                   | Type    | Value | Description                                                  |
| --------------------- | ------- | ----- | ------------------------------------------------------------ |
| `GESTURE`             | number | 1     | Animation started in response to user gestures on a map.     |
| `API_ANIMATION`       | number | 2     | Non-gesture animation started in response to a user operation. |
| `DEVELOPER_ANIMATION` | number | 3     | Animation that you started.                                  |

#### FillMode

| Key         | Type    | Value | Description                                            |
| ----------- | ------- | ----- | ------------------------------------------------------ |
| `FORWARDS`  | number | 0     | The last frame is displayed after the animation ends.  |
| `BACKWARDS` | number | 1     | The first frame is displayed after the animation ends. |

#### RepeatMode

| Key       | Type    | Value | Description                                                  |
| --------- | ------- | ----- | ------------------------------------------------------------ |
| `RESTART` | number | 1     | The animation is replayed from the start after it ends.      |
| `REVERSE` | number | 2     | The animation is replayed from the end in reverse order after it ends. |

#### Interpolator

| Key                     | Type    | Value | Description                                                  |
| ----------------------- | ------- | ----- | ------------------------------------------------------------ |
| `LINEAR`                | number | 0     | [See the visualization](https://www.youtube.com/watch?v=6UL7PXdJ6-E) |
| `ACCELERATE`            | number | 1     | [See the visualization](https://www.youtube.com/watch?v=6UL7PXdJ6-E) |
| `ANTICIPATE`            | number | 2     | [See the visualization](https://www.youtube.com/watch?v=6UL7PXdJ6-E) |
| `BOUNCE`                | number | 3     | [See the visualization](https://www.youtube.com/watch?v=6UL7PXdJ6-E) |
| `DECELERATE`            | number | 4     | [See the visualization](https://www.youtube.com/watch?v=6UL7PXdJ6-E) |
| `OVERSHOOT`             | number | 5     | [See the visualization](https://www.youtube.com/watch?v=6UL7PXdJ6-E) |
| `ACCELERATE_DECELERATE` | number | 6     | [See the visualization](https://www.youtube.com/watch?v=6UL7PXdJ6-E) |
| `FAST_OUT_LINEAR_IN`    | number | 7     | [See the visualization](https://www.youtube.com/watch?v=6UL7PXdJ6-E) |
| `FAST_OUT_SLOW_IN`      | number | 8     | [See the visualization](https://www.youtube.com/watch?v=6UL7PXdJ6-E) |
| `LINEAR_OUT_SLOW_IN`    | number | 9     | [See the visualization](https://www.youtube.com/watch?v=6UL7PXdJ6-E) |

### HMSMap

React component that shows Huawei Map.

#### Props

| Prop                                    | Required | Type                                | Description                                                  |
| --------------------------------------- | -------- | ----------------------------------- | ------------------------------------------------------------ |
| camera                                  | no       | [CameraPosition](#cameraposition) | Starting position of the camera on the map.                  |
| latLngBoundsForCameraTarget             | no       | [LatLng](#latlng)[]               | Bounds to constraint the camera target so that the camera target does not move outside the bounds when a user scrolls the map. |
| useAnimation                            | no       | boolean                             | Whether to use animation on camera update.                   |
| animationDuration                       | no       | number                              | Duration of the animation in ms.  By default, the camera animation takes 250 milliseconds. |
| compassEnabled                          | no       | boolean                             | Whether to enable the compass for the map.                   |
| mapType                                 | no       | [MapType](#maptype)               | Type of the map.                                             |
| minZoomPreference                       | no       | number                              | The preferred minimum zoom level of the camera. The value must be greater than or equal to the minimum zoom level (0) supported by the HUAWEI Map SDK. If the preferred minimum zoom level is higher than the current maximum zoom level, the SDK uses the preferred minimum zoom level as both the minimum and maximum zoom levels. Assume that the current minimum and maximum zoom levels are 4 and 10, respectively. If you set the preferred minimum zoom level to 15, the SDK uses the value 15 as both the minimum and maximum zoom levels. That is, the zoom level of the camera is fixed at 15. |
| maxZoomPreference                       | no       | number                              | The preferred maximum zoom level of the camera. If the preferred maximum zoom level is lower than the current minimum zoom level, the SDK uses the preferred maximum zoom level as both the minimum and maximum zoom levels. Assume that the current minimum and maximum zoom levels are 6 and 15, respectively. If you set the preferred maximum zoom level to 4, the SDK uses the value 4 as both the minimum and maximum zoom levels. That is, the zoom level of the camera is fixed at 4. |
| rotateGesturesEnabled                   | no       | boolean                             | Whether rotate gestures are enabled for the map.             |
| scrollGesturesEnabled                   | no       | boolean                             | Whether scroll gestures are enabled for the map.             |
| tiltGesturesEnabled                     | no       | boolean                             | Whether tilt gestures are enabled for the map.               |
| zoomControlsEnabled                     | no       | boolean                             | Whether to enable the zoom function for the camera.          |
| zoomGesturesEnabled                     | no       | boolean                             | Whether zoom gestures are enabled for the map.               |
| buildingsEnabled                        | no       | boolean                             | Whether the 3D building layer is enabled for the map.        |
| description                             | no       | string                              | The content description to the map. If the auxiliary mode is enabled, voice description about the map will be provided. |
| mapStyle                                | no       | string                              | JSON string for styling the map.                             |
| myLocationEnabled                       | no       | boolean                             | Whether my location layer is enabled for the map.            |
| mapPadding                              | no       | [MapPadding](#mappadding)         | The padding on the map. You can use this props to define the visible region on a map so that a signal can be sent to the map indicating that some portions around the map edges may be obscured. For example, icons such as the zoom controls and compass will be moved to adapt to the visible region, and the camera will move in relative to the center of the visible region. |
| markerClustering                        | no       | boolean                             | Whether the markers can be clustered.                        |
| myLocationButtonEnabled                 | no       | boolean                             | Whether to enable the my location icon for a map.            |
| scrollGesturesEnabledDuringRotateOrZoom | no       | boolean                             | Whether to enable scroll gestures during rotation or zooming. |
| onMapReady                              | no       | function                            | Function to handle the event when the map object is ready. It obtains an event information object as the argument which has nativeEvent as key and an empty object as value. |
| onCameraUpdateFinished                  | no       | function                            | Function to handle the event when the a camera update task is complete. It obtains an event information object as the argument which has nativeEvent as key and an empty object as value. |
| onCameraUpdateCanceled                  | no       | function                            | Function to handle the event when a camera update task is canceled. It obtains an event information object as the argument which has nativeEvent as key and an empty object as value. |
| onCameraIdle                            | no       | function                            | Function to handle the event when the camera movement ends. It obtains an event information object as the argument which has nativeEvent as key and a [CameraPosition](#cameraposition) object as value. |
| onCameraMoveCanceled                    | no       | function                            | Function to handle the event when the camera movement cancelled. It obtains an event information object as the argument which has nativeEvent as key and an empty object as value. |
| onCameraMove                            | no       | function                            | Function to handle the event when the camera moves. It obtains an event information object as the argument which has nativeEvent as key and an [CameraPosition](#cameraposition) object as value. |
| onCameraMoveStarted                     | no       | function                            | Function to handle the event when the camera movement started. It obtains an event information object as the argument which has nativeEvent as key and an object including [Reason](#reason) as value. |
| onMapClick                              | no       | function                            | Function to handle the event when the map is clicked. It obtains an event information object as the argument which has nativeEvent as key and a [ProjectionOnLatLng](#projectiononlatlng) object as value. |
| onMapLoaded                             | no       | function                            | Function to handle the event when the map loading is completed. It obtains an event information object as the argument which has nativeEvent as key and an empty object as value. |
| onMapLongClick                          | no       | function                            | Function to handle the event when the map is long clicked. It obtains an event information object as the argument which has nativeEvent as key and a ProjectionOnLatLng object as value. |
| onMyLocationButtonClick                 | no       | function                            | Function to handle the event when my location button is clicked. It obtains an event information object as the argument which has nativeEvent as key and an empty object as value. |
| onMyLocationClick                       | no       | function                            | Function to handle the event when my location icon on the map is clicked. It obtains an event information object as the argument which has nativeEvent as key and an empty object as value. |
| onPoiClick                              | no       | function                            | Function to handle the event when a POI is clicked. It obtains an event information object as the argument which has nativeEvent as key and a [PointOfInterest](#pointofinterest) object as value. |
| onSnapshotReady                         | no       | function                            | Function to handle the event when a snapshot is taken on the map. It obtains an event information object as the argument which has nativeEvent as key and a [SnapshotImage](#snapshotimage) object as value. |

#### Events

|Event| Description |
|--|--|
|`onMapReady` | Triggered when the map object is ready. |
|`onCameraUpdateFinished`|Triggered when the a camera update task is complete.|
|`onCameraUpdateCanceled`|Triggered when a camera update task is canceled.|
|`onCameraIdle`|Triggered when the camera movement ends.|
|`onCameraMoveCanceled`|Triggered when the camera movement cancelled.|
|`onCameraMove`|Triggered when when the camera moves.|
|`onCameraMoveStarted`|Triggered when the camera movement started.|
|`onMapClick`|Triggered when the map is clicked.|
|`onMapLoaded`|Triggered when the map loading is completed.|
|`onMapLongClick`|Triggered when the map is long clicked.|
|`onMyLocationButtonClick`|Triggered when my location button is clicked.|
|`onPoiClick`|Triggered when a POI is clicked.|
|`onMyLocationClick`|Triggered when my location icon on the map is clicked.|
|`onSnapshotReady`|Triggered when a snapshot is taken on the map.|

#### Methods


Methods can be used with component reference which can be declared with the ref property of React components.

##### getHuaweiMapInfo()

Obtains all attributes of the Huawei map object.

###### Return Type

| Return                             | Description                                                  |
| ---------------------------------- | ------------------------------------------------------------ |
| Promise<[HuaweiMap](#huaweimap)> | A promise is returned with [HuaweiMap](#huaweimap) object if the operation is successful. |

###### Call Example

```jsx
mapViewRef.getHuaweiMapInfo().then(info => console.log(info));
```

###### Response Example

```jsx
{
  farLeft: { latitude: -5.190444589630992, longitude: -38.01630381736629 },
  farRight: { latitude: 35.76354190569813, longitude: -1.4828659557394042 },
  latLngBounds: {
    center: { latitude: 5.464801075e-7, longitude: -8.026137727767946e-7 },
    northeast: { latitude: 35.76354190569813, longitude: 38.01630221213875 },
    southwest: { latitude: -35.763540812737915, longitude: -38.01630381736629 },
  },
  nearLeft: { latitude: -35.763540812737915, longitude: 1.4828643505118677 },
  nearRight: { latitude: 5.19044593105372, longitude: 38.01630221213875 },
}
```

##### getPointFromCoordinate(latLng: [LatLng](#latlng))

Obtains a location on the screen corresponding to a longitude and latitude. The location on the screen is specified in screen pixels (instead of display pixels) relative to the top left corner of the map (instead of the top left corner of the screen).

###### Parameters

| Argument | Required | Type                | Description                |
| -------- | -------- | ------------------- | -------------------------- |
| latLng   | yes      | [LatLng](#latlng) | The coordinate on the map. |

###### Return Types

| Return                     | Description                                                  |
| -------------------------- | ------------------------------------------------------------ |
| Promise<[Point](#point)> | A promise is returned with [Point](#point) object if the operation is successful. |

###### Call Example

```jsx
mapViewRef.getPointFromCoordinate({latitude: 43.234, longitude: 41.3452}).then(point => console.log(point.x, point.y));
```

##### getCoordinateFromPoint(point: [Point](#point))

Obtains the longitude and latitude of a location on the screen. The location on the screen is specified in screen pixels (instead of display pixels) relative to the top left corner of the map (instead of the top left corner of the screen).

###### Parameters

| Argument | Required | Type              | Description             |
| -------- | -------- | ----------------- | ----------------------- |
| point    | yes      | [Point](#point) | The point on the screen |

###### Return Type

| Return                       | Description                                                  |
| ---------------------------- | ------------------------------------------------------------ |
| Promise<[LatLng](#latlng)> | A promise is returned with [LatLng](#latlng) object if the operation is successful. |

###### Call Example

```jsx
mapViewRef.getCoordinateFromPoint({x: 12, y: 20}).then(coordinate => console.log(coordinate.latitude, coordinate.longitude))
```

##### getDistance(from: [LatLng](#latlng), to: [LatLng](#latlng))

Obtains a location on the screen corresponding to a longitude and latitude. The location on the screen is specified in screen pixels (instead of display pixels) relative to the top left corner of the map (instead of the top left corner of the screen).

###### Parameters

| Argument | Required | Type                | Description                     |
| -------- | -------- | ------------------- | ------------------------------- |
| from     | yes      | [LatLng](#latlng) | Location of the starting point. |
| to       | yes      | [LatLng](#latlng) | Location of the end point.      |

###### Return Types

| Return            | Description                                                  |
| ----------------- | ------------------------------------------------------------ |
| Promise<`number`> | A promise is returned with [Point](#point) object if the operation is successful. |

###### Call Example

```jsx
mapViewRef.getDistance({latitude: 23.45, longitutde: 34.56},{latitude: 21.45, longitutde: 37.56}).then(result => console.log(result)
```


##### clear()

Removes all circles, markers, polylines, and ground overlays from the map.

###### Call Example

```jsx
mapViewRef.clear();
```


##### takeSnapshot()

Takes a snapshot of a map. If an interactive map is difficult or impossible to use, you can use snapshots in your app. For example, you can use this function to generate images and use them to display a thumbnail of the map in your app, or display a snapshot in the notification center.

```jsx
    let mapViewRef;
    <HMSMap
	    ref={(ref) => {mapViewRef = ref;}}
	    onSnapshotReady={(e) => console.log("Snapshot Bitmap String: " + e.nativeEvent.bitmap)}
	/>
	<Button
		title="Take Snapshot"
		onPress={() => {mapViewRef.takeSnapshot()}}
	/>
```


##### resetMinMaxZoomPreference()

Deletes all maximum and minimum zoom levels set previously.

###### Call Example

```jsx
mapViewRef.resetMinMaxZoomPreference();
```


##### stopAnimation()

Stops the current animation process of the camera. When this method is called, the camera stops moving immediately and remains in that position.

###### Call Example

```jsx
mapViewRef.stopAnimation();
```
##### setCameraPosition(cameraPosition: [CameraPosition](#cameraposition))

Sets a new position for the camera of a map.

###### Parameters

| Argument | Required | Type                | Description                     |
| -------- | -------- | ------------------- | ------------------------------- |
| cameraPosition| yes      | [CameraPosition](#cameraposition) | Position of the camera to be set.|

###### Call Example

```jsx
mapViewRef.setCameraPosition({target: {latitude: 41, longitude: 29}, zoom: 13, tilt: 40});
```

##### setCoordinates(latLng: [LatLng](#latlng), zoom?: number)

Sets the longitude and latitude of the center of a map's view. And it also sets the zoom level if provided

###### Parameters

| Argument | Required | Type                | Description                     |
| -------- | -------- | ------------------- | ------------------------------- |
| latLng   | yes      | [LatLng](#latlng) | Desired longitude and latitude. |
| zoom     | no       | number              | Desired zoom.                   |

###### Call Example

```jsx
mapViewRef.setCoordinates({latitude: 45.543, longitude: 24.432}, 12)
```


##### setBounds(latLngBounds: [LatLng](#latlng)[], padding: number, width?: number, height?: number)

Centers a region on the screen by setting the longitude and latitude bounds and the padding between the region edges, the longitude/latitude bounding box edges, and the width and height of the region if provided.

###### Parameters

| Argument     | Required | Type                  | Description                                                  |
| ------------ | -------- | --------------------- | ------------------------------------------------------------ |
| latLngBounds | yes      | [LatLng](#latlng)[] | Longitude and latitude bounds to be displayed.               |
| padding      | yes      | number                | Space between the region edges and bounding box edges, in pixels. |
| width        | no       | number                | Bounding box width, in pixels.                               |
| height       | no       | number                | Bounding box height, in pixels.                              |

###### Call Example

```jsx
mapViewRef.setBounds([{latitude: 43, longitude: 32},{latitude: 42.5, longitude: 31.3}], 4)
```

##### scrollBy(x: number, y: number)

Moves the center of a map's view by pixel on the screen.

###### Parameters

| Argument | Required | Type   | Description                              |
| -------- | -------- | ------ | ---------------------------------------- |
| x        | yes      | number | Number of pixels to scroll horizontally. |
| y        | yes      | number | Number of pixels to scroll vertically.   |

###### Call Example

```jsx
mapViewRef.scrollBy(4, 10)
```

##### zoomBy(amount: number, focus?: [Point](#point))

Changes the zoom level of the camera by a specified incremental value and sets a specified point as the focus if provided.

###### Parameters

| Argument | Required | Type              | Description                                               |
| -------- | -------- | ----------------- | --------------------------------------------------------- |
| amount   | yes      | number            | Incremental value to change the zoom level of the camera. |
| focus    | no       | [Point](#point) | Coordinates of a point to be set as the focus.            |

###### Call Example

```jsx
mapViewRef.zoomBy(4, {x: 15, y: 20})
```

##### zoomTo(zoom: number)

Sets the zoom level of the camera to a specified value

###### Parameters 

| Argument | Required | Type   | Description         |
| -------- | -------- | ------ | ------------------- |
| zoomTo   | yes      | number | Desired zoom level. |

###### Call Example

```jsx
mapViewRef.zoomTo(16);
```

##### zoomIn()

Increases the zoom level of the camera by 1.

###### Call Example

```
mapViewRef.zoomIn();
```

##### zoomOut()

Decreases the zoom level of the camera by 1.

###### Call Example

```jsx
mapViewRef.zoomOut();
```

##### enableLogger()

Enables HMSLogger which logs actions in Logcat terminal.

###### Call Example

```js
HMSMap.module.enableLogger();
```

##### disableLogger()

Disables HMSLogger which logs actions in Logcat terminal.

###### Call Example

```jsx
HMSMap.module.disableLogger();
```

##### getLayerInfo(ref: object)

Obtains all attributes of the the selected map layer.

###### Parameters 

| Argument | Required | Type   | Description         |
| -------- | -------- | ------ | ------------------- |
| ref   | yes      | object | The reference to the layer component.|

###### Return Types

| Return | Description   |
| ------- | ------------ |
| Promise<`object`> | A promise is returned with LayerInfo object if the operation is successful.  |


###### Call Example

```jsx
mapViewRef.getLayerInfo(markerRef).then(info => console.log(info));
```
###### Response Example

```jsx
{
  alpha: 0.800000011920929,
  id: "Marker102",
  isClusterable: false,
  isDraggable: true,
  isFlat: true,
  isInfoWindowShown: false,
  isVisible: true,
  position: { latitude: -10, longitude: 0 },
  rotation: 30,
  snippet: null,
  title: null,
  zIndex: 0,
}
```
##### getLayerOptionsInfo(ref: object)

Obtains all attributes of the the selected map layer options.

###### Parameters 

| Argument | Required | Type   | Description         |
| -------- | -------- | ------ | ------------------- |
| ref   | yes      | object | The reference to the layer options component.|

###### Return Types

| Return | Description   |
| ------- | ------------ |
| Promise<`object`> | A promise is returned with LayerInfoOptions object if the operation is successful.  |


###### Call Example

```jsx
mapViewRef.getLayerOptionsInfo(markerRef).then(info => console.log(info));
```
###### Response Example

```jsx
{
  alpha: 0.800000011920929,
  infoWindowAnchorU: 0.5,
  infoWindowAnchorV: 0.5,
  isClusterable: false,
  isDraggable: true,
  isFlat: true,
  isVisible: true,
  markerAnchorU: 0.5,
  markerAnchorV: 0.5,
  position: { latitude: -10, longitude: 0 },
  rotation: 30,
  snippet: null,
  title: null,
  zIndex: 0,
}
```

#### Usage

```jsx
import HMSMap from '@hmscore/react-native-hms-map';

<HMSMap />
```

Using all the props

```jsx
import HMSMap, {MapTypes} from '@hmscore/react-native-hms-map';
<HMSMap
  camera={{
    target: {latitude: 41, longitude: 29},
    zoom: 11,
    bearing: 5,
    tilt: 70,
  }}
  latLngBoundsForCameraTarget={[
    {latitude: 40, longitude: 28},
    {latitude: 42, longitude: 30},
  ]}
  useAnimation={true} // move the map camera in animation
  animationDuration={2000} // animation duration
  compassEnabled={true} // show compass
  mapType={MapTypes.NORMAL} // use normal map
  minZoomPreference={10} // set preferred minimum camera zoom levels.
  maxZoomPreference={20} // set preferred maximum camera zoom levels.
  rotateGesturesEnabled={true} // enable rotate gesture
  scrollGesturesEnabled={true} // enable scroll gesture
  tiltGesturesEnabled={true} // enable tilt gesture
  zoomControlsEnabled={true} // show zoom controls
  zoomGesturesEnabled={true} // enable zoom gesture
  buildingsEnabled={true}
  description="Huawei Map"
  mapStyle={'[{"mapFeature":"all","options":"labels.icon","paint":{"icon-type":"night"}}]'}
  myLocationEnabled={true} // show location
  mapPadding={{right: 200, left: 10, top: 10, bottom: 10}}
  markerClustering={true}
  myLocationButtonEnabled={true} // show location button
  scrollGesturesEnabledDuringRotateOrZoom={true} // enable scroll gestures during rotate and zoom
  onCameraUpdateFinished={(e) => console.log("HMSMap onCameraUpdateFinished", e.nativeEvent)}
  onCameraUpdateCanceled={(e) => console.log("HMSMap onCameraUpdateCanceled", e.nativeEvent)}
  onCameraIdle={(e) => console.log("HMSMap onCameraIdle", e.nativeEvent)}
  onMapReady={(e) => console.log("HMSMap onMapReady", e.nativeEvent)}
  onCameraMoveCanceled={(e) => console.log("HMSMap onCameraMoveCanceled", e.nativeEvent)}
  onCameraMove={(e) => console.log("HMSMap onCameraMove", e.nativeEvent)}
  onCameraMoveStarted={(e) => console.log("HMSMap onCameraMoveStarted", e.nativeEvent)}
  onMapClick={(e) => console.log("HMSMap onMapClick", e.nativeEvent)}
  onMapLoaded={(e) => console.log("HMSMap onMapLoaded", e.nativeEvent)}
  onMapLongClick={(e) => console.log("HMSMap onMapLongClick", e.nativeEvent)}
  onMyLocationButtonClick={(e) => console.log("HMSMap onMyLocationButtonClick", e.nativeEvent)}
  onMyLocationClick={(e) => console.log("HMSMap onMyLocationClick", e.nativeEvent)}
  onPoiClick={(e) => console.log("HMSMap onPoiClick", e.nativeEvent)}  
  onSnapshotReady={(e) => console.log("HMSMap onSnapshotReady", e.nativeEvent)} 
  />
```

Camera Movement

```jsx
import HMSMap from '@hmscore/react-native-hms-map';
let mapView;

<HMSMap
  camera={{target: {latitude: 50, longitude: 50}}}
  ref={(e) => (mapView = e)}/>

// Method 1: Increase the camera zoom level by 1 and retain other attribute settings.
mapView.zoomIn();

// Method 2: Decrease the camera zoom level by 1 and retain other attribute settings.
mapView.zoomOut();

// Method 3: Set the camera zoom level to a specified value and retain other attribute settings.
mapView.zoomTo(10.5);

// Method 4: Increase or decrease the camera zoom level by a specified value.
mapView.zoomBy(1.5);

// Method 5: Move the camera to the specified center point and increase or decrease the camera zoom level by a specified value. 
mapView.zoomBy(2, {x: 0, y: 0});

// Method 6: Set the latitude and longitude of the camera and retain other attribute settings.
mapView.setCoordinates({latitude: 41, longitude: 29});

// Method 7: Set the visible region and padding.
mapView.setBounds([{latitude: 41.5, longitude: 28.5},{latitude: 40.5, longitude: 29.5}], 1);

// Method 8: Set the center point and zoom level of the camera.
mapView.setCoordinates({latitude: 41, longitude: 29}, 12);

// Method 9: Scroll the camera by specified pixels.
mapView.scrollBy(100, 100)

// Method 10: Specify the camera position.
mapView.setCameraPosition({target: {latitude: 41, longitude: 29}, zoom: 13, tilt: 40});
```

Obtain information

```jsx
import HMSMap from '@hmscore/react-native-hms-map';
let mapView;

<HMSMap
  camera={{target: {latitude: 50, longitude: 50}}}
  ref={(e) => (mapView = e)}/>

// Get map information
mapView
    .getHuaweiMapInfo()
    .then((a) => console.log(a))
    .catch((a) => console.log(a));

// Get Coordinate From Point
mapView
    .getCoordinateFromPoint({x: 100, y: 100})
    .then((a) => console.log(a))
    .catch((a) => console.log(a));

// Get Point From Coordinate
mapView
    .getPointFromCoordinate({latitude: 0, longitude: 0})
    .then((a) => console.log(a))
    .catch((a) => console.log(a));

// Calculate distance between coordinates
HMSMap.module
    .getDistance(
        {latitude: 41, longitude: 29},
        {latitude: 41, longitude: 28},
    )
    .then((a) => console.log(a))
    .catch((a) => console.log(a));
```

### HMSMarker

#### Props

| Prop                  | Required | Type                                    | Description                                                  |
| --------------------- | -------- | --------------------------------------- | ------------------------------------------------------------ |
| coordinate            | yes      | [LatLng](#latlng)                     | The position of a marker.                                    |
| draggable             | no       | boolean                                 | Whether the marker can be dragged.                           |
| icon                  | no       | [BitmapDescriptor](#bitmapdescriptor) | The icon of the marker.                                      |
| alpha                 | no       | number                                  | The transparency of the marker.                              |
| flat                  | no       | boolean                                 | Whether the marker is flatly attached to the map.            |
| markerAnchor          | no       | [number, number]                        | The anchor point of the marker. The anchor point is used to anchor a marker image to the map. The coordinates [0, 0], [1, 0], [0, 1], and [1, 1] respectively indicate the top-left, top-right, bottom-left, and bottom-right corners of the marker image. |
| infoWindowAnchor      | no       | [number, number]                        | The anchor point of the marker's information window. The anchor point is used to anchor a marker image to the map. The coordinates [0, 0], [1, 0], [0, 1], and [1, 1] respectively indicate the top-left, top-right, bottom-left, and bottom-right corners of the marker image. |
| rotation              | no       | number                                  | The rotation angle of the marker.                            |
| title                 | no       | string                                  | The title of the marker.                                     |
| snippet               | no       | string                                  | The text of the marker.                                      |
| visible               | no       | boolean                                 | Whether the marker is visible.                               |
| clusterable           | no       | boolean                                 | Whether the marker can be clustered.                         |
| defaultActionOnClick  | no       | boolean                                 | If true, click action causes the default behaviour. If false, click action ignored and the user needs to implement behaviours in callback. |
| zIndex                | no       | number                                  | The z-index of the marker. The z-index indicates the overlapping order of a marker. A marker with a larger z-index overlaps that with a smaller z-index. Markers with the same z-index overlap each other in a random order. |
| onClick               | no       | function                                | Function to handle the event when the marker is clicked. It obtains an event information object as the argument which has nativeEvent as key and an empty object as value. |
| onDragStart           | no       | function                                | Function to handle the event when the marker starts being dragged. It obtains an event information object as the argument which has nativeEvent as key and an empty object as value. |
| onDrag                | no       | function                                | Function to handle the event when the marker is being dragged. It obtains an event information object as the argument which has nativeEvent as key and an empty object as value. |
| onDragEnd             | no       | function                                | Function to handle the event when the marker dragging is complete. It obtains an event information object as the argument which has nativeEvent as key and an empty object as value. |
| onInfoWindowClick     | no       | function                                | Function to handle the event when the information window of the marker is clicked. It obtains an event information object as the argument which has nativeEvent as key and an empty object as value. |
| onInfoWindowClose     | no       | function                                | Function to handle the event when the information window of the marker is closed. It obtains an event information object as the argument which has nativeEvent as key and an empty object as value. |
| onInfoWindowLongClick | no       | function                                | Function to handle the event when the information window of the marker is long clicked. It obtains an event information object as the argument which has nativeEvent as key and an empty object as value. |

#### Events

|Event| Description |
|--|--|
|`onClick` | Triggered when the marker is clicked. |
|`onDragStart`|Triggered when the marker starts being dragged.|
|`onDrag`|Triggered when the marker is being dragged.|
|`onDragEnd`|Triggered when the marker dragging is complete.|
|`onInfoWindowClick`|Triggered when the information window of the marker is clicked.|
|`onInfoWindowClose`|Triggered when the information window of the marker is closed.|
|`onInfoWindowLongClick`|Triggered when the information window of the marker is long clicked.|
|`onAnimationStart`|Triggered when a marker animation starts (This event will be triggered for every type of animation even if they start at the same time).|
|`onAnimationEnd`|Triggered when a marker animation ends (This event will be triggered for every type of animation even if they end at the same time).|

#### Methods

##### showInfoWindow()

Shows the information window attached to the marker.

###### Call Example

```jsx
markerRef.showInfoWindow();
```


##### hideInfoWindow()

Hides the information window attached to the marker.

###### Call Example

```jsx
markerRef.hideInfoWindow();
```


##### setAnimation(Animation: [MarkerAnimation](#markeranimation), defaultAnimationOptions: [DefaultAnimationOptions](#defaultanimationoptions))

Sets the animation with animation object and default values.

###### Parameters

| Argument      | Required | Type                                | Description                                                  |
| ------------- | -------- | ----------------------------------- | ------------------------------------------------------------ |
| animation     | yes      | [MarkerAnimation](#markeranimation) | Custom animation object to set how animation will be animated when triggering [startAnimation()](#startAnimation()) |
| defaultAnimationOptions | no       | [DefaultAnimationOptions](#defaultanimationoptions)     | Coordinates of a point to be set as the focus.               |

###### Call Example

```jsx
markerRef.setAnimation({rotate: {fromAngle: 0, toAngle: 150, duration: 10000}});
```


##### startAnimation()

Starts the animation which is previously set.

###### Call Example

```jsx
markerRef.startAnimation();
```
##### cleanAnimation()

Removes the animation from the marker.

###### Call Example

```jsx
markerRef.cleanAnimation(); 
```

#### Usage

```jsx
import HMSMap, {HMSMarker} from '@hmscore/react-native-hms-map';

<HMSMap>
  <HMSMarker
    coordinate={{latitude: 0, longitude: 5}}
    title="Hello"
    snippet="My Friend"
    draggable={true}
    flat={true}
    icon={{
      asset: "ic_launcher.png", // under assets folder
    }}
    alpha={0.8}
    markerAnchor={[0.5, 0.5]}
    infoWindowAnchor={[0.5, 0.5]}
    rotation={30.0}
    visible={true}
    zIndex={3}
    clusterable={false}
    onClick={(e) => console.log("Marker onClick", e.nativeEvent)}
    onDragStart={(e) => console.log("Marker onDragStart", e.nativeEvent)}
    onDrag={(e) => console.log("Marker onDrag", e.nativeEvent)}
    onDragEnd={(e) => console.log("Marker onDragEnd", e.nativeEvent)}
    onInfoWindowClick={(e) => console.log("Marker onInfoWindowClick", e.nativeEvent)}
    onInfoWindowClose={(e) => console.log("Marker onInfoWindowClose", e.nativeEvent)}
    onInfoWindowLongClick={(e) => console.log("Marker onInfoWindowLongClick", e.nativeEvent)} />
</HMSMap>
```

Using cluster

```jsx
import HMSMap, {HMSMarker} from '@hmscore/react-native-hms-map';

<HMSMap markerClustering={true}>
  <HMSMarker coordinate={{latitude: 0, longitude: -2}} clusterable />
  <HMSMarker coordinate={{latitude: 1, longitude: -1}} clusterable />
  <HMSMarker coordinate={{latitude: 0, longitude: 0}} clusterable />
  <HMSMarker coordinate={{latitude: 1, longitude: 1}} clusterable />
  <HMSMarker coordinate={{latitude: 0, longitude: 2}} clusterable />
  <HMSMarker coordinate={{latitude: -1, longitude: -1}} clusterable />
  <HMSMarker coordinate={{latitude: -1, longitude: 1}} clusterable />
</HMSMap>
```

Set and start animation

```jsx
import {RepeatMode, Interpolator, FillMode} from '@hmscore/react-native-hms-map'

let animation = {  
	rotate: {  
		fromDegree: 0,  
		toDegree: 270,  
		duration: 5000,
		repeatMode: RepeatMode.REVERSE,
		repeatCount: -1
	},  
	alpha: {  
		fromAlpha: 0,  
		toAlpha: 1,  
	},  
	scale: {  
		fromX: 1,  
		fromY: 1,  
		toX: 3,  
		toY: 3,  
		duration: 10000,  
	},  
	translate: {  
		latitude: 4.196762137072112,
		longitude: 15.686358445008585,
		fillMode: FillMode.BACKWARDS,
		interpolator: Interpolator.BOUNCE,
		repeatCount: 3,
	},  
};

// If you don't specify a field in default or animation object,
// Android defaults will be applied.

let defaultValues = {
	duration: 1000,
	fillMode: FillMode.FORWARDS,
	repeatCount: 1,
	repeatMode: RepeatMode.RESTART,
	interpolator: Interpolator.LINEAR,
};

<Button 
	title="Animate!"
	onPress={() => {
		markerViewRef.setAnimation(animation, defaultValues);
		markerViewRef.startAnimation();
	}}
/>
```

### HMSInfoWindow

React component that shows custom information window for markers. It must be a child of a Marker component in order to show the custom information window. It extends View component provided by React-Native.

#### Usage

```jsx
import {TouchableHighlight, View} from "react-native";
import HMSMap, {HMSMarker} from '@hmscore/react-native-hms-map';

<HMSMap>
  <HMSMarker
    coordinate={{latitude: 0, longitude: 0}}
    onInfoWindowClose={(e) => console.log("Marker onInfoWindowClose", e.nativeEvent)}>
    <HMSInfoWindow>
      <TouchableHighlight
        onPress={() => console.log("Marker onInfoWindowClick")}
        onLongPress={() => console.log("Marker onInfoWindowLongClick")}>
        <View style={{backgroundColor: "yellow"}}>
          <Text style={{backgroundColor: "orange"}}>Hello</Text>
          <Text>I am a marker</Text>
        </View>
      </TouchableHighlight>
    </HMSInfoWindow>
  </HMSMarker>
</HMSMap>
```

### HMSPolyline

#### Props

| Prop      | Required | Type                            | Description                                                  |
| --------- | -------- | ------------------------------- | ------------------------------------------------------------ |
| points    | no       | [LatLng](#latlng)[]           | Vertex coordinates of the polyline.                          |
| clickable | no       | boolean                         | Whether the polyline is tappable.                            |
| color     | no       | number                          | The stroke color of a polyline in ARGB format.               |
| geodesic  | no       | boolean                         | Whether each segment of the polyline is drawn as a geodesic. |
| jointType | no       | [JointType](#jointtype)       | The joint type of all vertices of the polyline, except the start and end vertices. |
| width     | no       | number                          | The stroke width of the polyline's outline, in pixels.       |
| pattern   | no       | [PatternItem](#patternitem)[] | Stroke pattern of the polyline.                              |
| startCap  | no       | [Cap](#cap)                   | The start vertex of the polyline.                            |
| endCap    | no       | [Cap](#cap)                   | The end vertex of the polyline.                              |
| visible   | no       | boolean                         | Whether the polyline is visible.                             |
| zIndex    | no       | number                          | The z-index of the polyline. The z-index indicates the overlapping order of the polyline. A polyline with a larger z-index overlaps that with a smaller z-index. Polylines with the same z-index overlap each other in a random order. |
| onClick   | no       | function                        | Function to handle the event when the polyline is clicked. It obtains an event information object as the argument which has nativeEvent as key and an empty object as value. |

#### Events

|Event| Description |
|--|--|
|`onClick` | Triggered when the polyline is clicked. |

#### Usage

```jsx
import HMSMap, {HMSPolyline, PatternItemTypes, JointTypes, CapTypes} from '@hmscore/react-native-hms-map';
<HMSMap>
  <HMSPolyline // Simple example
    points={[
      {latitude: 0, longitude: -10},
      {latitude: -25, longitude: 15},
      {latitude: -5, longitude: 15},
    ]}
  />
  <HMSPolyline // Complex example
    points={[
      {latitude: 0, longitude: 5},
      {latitude: -25, longitude: 5},
      {latitude: 5, longitude: -25},
    ]}
    clickable={true}
    geodesic={true}
    color={-1879018753} // transparent blue(0x900072FF)
    jointType={JointTypes.BEVEL}
    pattern={[{type: PatternItemTypes.DASH, length: 20}]}
    startCap={{type: CapTypes.ROUND}}
    endCap={{
      type: CapTypes.CUSTOM,
      refWidth: 1000,
      asset: "ic_launcher.png", // under assets folder
    }}
    visible={true}
    width={40.0}
    zIndex={2}
    onClick={(e) => console.log("Polyline onClick", e.nativeEvent)}
  />
</HMSMap>
```

### HMSPolygon

#### Props

| Prop            | Required | Type                            | Description                                                  |
| --------------- | -------- | ------------------------------- | ------------------------------------------------------------ |
| points          | no       | [LatLng](#latlng)[]           | Vertex coordinates of the polygon.                           |
| holes           | no       | [LatLng](#latlng)[][]         | Holes in the polygon.                                        |
| clickable       | no       | boolean                         | Whether the polygon is tappable.                             |
| fillColor       | no       | number                          | The fill color of the circle, in ARGB format.                |
| geodesic        | no       | boolean                         | Whether each segment of the polygon is drawn as a geodesic.  |
| strokeColor     | no       | number                          | The stroke color of the polygon's outline.                   |
| strokeJointType | no       | [JointType](#jointtype)       | The joint type of the polygon.                               |
| strokeWidth     | no       | number                          | The stroke width of the polygon's outline, in pixels.        |
| strokePattern   | no       | [PatternItem](#patternitem)[] | Stroke pattern of the polygon's outline. By default, the stroke pattern is solid, represented by null. |
| visible         | no       | boolean                         | Whether the polygon is visible.                              |
| zIndex          | no       | number                          | The z-index of the polygon. The z-index indicates the overlapping order of the polygon. A polygon with a larger z-index overlaps that with a smaller z-index. Polygons with the same z-index overlap each other in a random order. |
| onClick         | no       | function                        | Function to handle the event when the polygon is clicked. It obtains an event information object as the argument which has nativeEvent as key and an empty object as value. |

#### Events

|Event| Description |
|--|--|
|`onClick` | Triggered when the polygon is clicked. |

#### Usage

```jsx
import HMSMap, {HMSPolygon, PatternItemTypes, JointTypes, CapTypes} from '@hmscore/react-native-hms-map';
<HMSMap>
  <HMSPolygon// Simple example
    points={[
      {latitude: 25, longitude: 0},
      {latitude: 30, longitude: -5},
      {latitude: 35, longitude: 0},
      {latitude: 30, longitude: 5},
    ]}
  />
  <HMSPolygon// Complex example
    points={[
      {latitude: 20, longitude: 20},
      {latitude: -20, longitude: 20},
      {latitude: -20, longitude: -20},
      {latitude: 20, longitude: -20},
    ]}
    holes={[
      [
        {latitude: 5, longitude: 15},
        {latitude: 15, longitude: 15},
        {latitude: 15, longitude: 5},
      ],
      [
        {latitude: -15, longitude: -15},
        {latitude: 15, longitude: -15},
        {latitude: -15, longitude: 15},
      ],
    ]}
    clickable={true}
    geodesic={true}
    fillColor={538066306} // very transparent blue(0x20123D82)
    strokeColor={-256} // yellow(0xFFFFFF00)
    strokeJointType={JointTypes.BEVEL}
    strokePattern={[
      {type: PatternItemTypes.DASH, length: 20},
      {type: PatternItemTypes.DOT},
      {type: PatternItemTypes.GAP, length: 20},
    ]}
    zIndex={2}
    onClick={(e) => console.log("Polygon onClick", e.nativeEvent)}
  />
</HMSMap>
```

### HMSCircle

#### Props

| Prop          | Required | Type                            | Description                                                  |
| ------------- | -------- | ------------------------------- | ------------------------------------------------------------ |
| center        | yes      | [LatLng](#latlng)             | Center of the circle.                                        |
| radius        | yes      | string                          | Radius of the circle.                                        |
| clickable     | no       | boolean                         | Whether the circle is clickable.                             |
| fillColor     | no       | number                          | The fill color of the circle, in ARGB format.                |
| strokeColor   | no       | number                          | The stroke color of the circle's outline.                    |
| strokeWidth   | no       | number                          | The stroke width of the circle's outline.                    |
| strokePattern | no       | [PatternItem](#patternitem)[] | Stroke pattern of the circle's outline.                      |
| visible       | no       | boolean                         | Whether the circle is visible.                               |
| zIndex        | no       | number                          | The z-index of the circle. The z-index indicates the overlapping order of the circle. A circle with a larger z-index overlaps that with a smaller z-index. Circles with the same z-index overlap each other in a random order. By default, the z-index is 0. |
| onClick       | no       | function                        | Function to handle the event when the circle is clicked. It obtains an event information object as the argument which has nativeEvent as key and an empty object as value. |

#### Events

|Event| Description |
|--|--|
|`onClick` | Triggered when the circle is clicked. |

#### Usage

```jsx
import HMSMap, {HMSCircle, PatternItemTypes} from '@hmscore/react-native-hms-map';
<HMSMap>
  <HMSCircle// Simple example
    center={{latitude: 10, longitude: 0}}
    radius={900000}
  />
  
  <HMSCircle// Complex example
    center={{latitude: 5, longitude: 0}}
    radius={900000}
    clickable={true}
    fillColor={-1879018753} // transparent blue(0x900072FF)
    strokeWidth={10}
    strokeColor={-256} // yellow(0xFFFFFF00)
    strokePattern={[
      {type: PatternItemTypes.DASH, length: 20},
      {type: PatternItemTypes.DOT},
      {type: PatternItemTypes.GAP, length: 20},
    ]}
    visible={true}
    zIndex={2}
    onClick={(e) => console.log("Circle onClick", e.nativeEvent)}
  />
</HMSMap>
```

### HMSGroundOverlay

#### Props

| Prop         | Required | Type                                                         | Description                                                  |
| ------------ | -------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| coordinate   | yes      | [LatLngWithSize](#latlngwithsize) or [LatLng](#latlng)[] | The position of the ground overlay.                          |
| image        | yes      | [BitmapDescriptor](#bitmapdescriptor)                      | The image for the ground overlay.                            |
| clickable    | no       | boolean                                                      | Whether the ground overlay is tappable.                      |
| anchor       | no       | [number, number]                                             | The alignment (that is, anchor point) of a ground overlay. The coordinates [0, 0], [1, 0], [0, 1], and [1, 1] respectively indicate the top-left, top-right, bottom-left, and bottom-right corners of the ground overlay. If no anchor point is set, the center point (0.5, 0.5) of the ground overlay will be used by default. |
| bearing      | no       | number                                                       | The bearing of the ground overlay, in degrees clockwise from north. The value ranges from 0 to 360 (excluded). |
| visible      | no       | boolean                                                      | Whether the ground overlay is visible.                       |
| transparency | no       | number                                                       | The transparency of the ground overlay.The value ranges from 0 to 1. The value 0 indicates opaque and the value 1 indicates transparent. |
| zIndex       | no       | number                                                       | The z-index of the ground overlay. The z-index indicates the overlapping order of a ground overlay. A ground overlay with a larger z-index overlaps that with a smaller z-index. Ground overlays with the same z-index overlap each other in a random order. By default, the z-index is 0. |
| onClick      | no       | function                                                     | Function to handle the event when the ground overlay is clicked. It obtains an event information object as the argument which has nativeEvent as key and an empty object as value. |

#### Events

|Event| Description |
|--|--|
|`onClick` | Triggered when the ground overlay is clicked. |

#### Usage

```jsx
import HMSMap, {HMSGroundOverlay} from '@hmscore/react-native-hms-map';
<HMSMap>
  <HMSGroundOverlay // Simple example
    image={{hue: 30.0}}// use orange marker image
    coordinate={[ // coordinate bounds for the image
      {latitude: 5, longitude: -5},
      {latitude: -15, longitude: 5},
    ]}
  />
  <HMSGroundOverlay // Complex example
    image={{asset: "ic_launcher.png" /* under assets folder*/}}
    coordinate={{ // giving exact coordinate with sizes
      latitude: -10,
      longitude: -10,
      height: 1000000,
      width: 1000000,
    }}
    anchor={[0.5, 0.5]}
    bearing={20}
    clickable={true}
    transparency={0.5}
    visible={true}
    zIndex={3}
    onClick={(e) =>
      console.log("GroundOverlay onClick e:", e.nativeEvent)
    }
  />
</HMSMap>
```

### HMSTileOverlay

#### Props

| Prop         | Required | Type                            | Description                                                  |
| ------------ | -------- | ------------------------------- | ------------------------------------------------------------ |
| tileProvider | yes      | [TileProvider](#tileprovider) or [CustomTile](#customtile)[] | The provider of the tile overlay.                           |
| fadeIn       | no       | boolean                         | Whether the tile overlay fades in.                           |
| transparency | no       | number                          | The transparency of the tile overlay.                        |
| visible      | no       | boolean                         | Whether the tile overlay is visible.                         |
| zIndex       | no       | number                          | The z-index of the tile overlay. The z-index indicates the overlapping order of a tile overlay. A tile overlay with a larger z-index overlaps that with a smaller z-index. Tile overlays with the same z-index overlap each other in any order. |

#### Methods

##### clearTileCache()

Clears the cache of the tile overlay.

###### Call Example

```jsx
tileOverlayRef.clearTileCache();
```

#### Usage

```jsx
import HMSMap, {HMSTileOverlay, MapTypes} from '@hmscore/react-native-hms-map';
<HMSMap mapType={MapTypes.NONE}> // Using empty map
  <HMSTileOverlay
    tileProvider={{url: "https://a.tile.openstreetmap.org/{z}/{x}/{y}.png",}}
  />
    <HMSTileOverlay
    tileProvider={[
      {
        "x": 3,
        "y": 4,
        "zoom": 3,
        "asset": "tile.png"
      },
      {
        "x": 4,
        "y": 4,
        "zoom": 3,
        "asset": "tile.png"
      }
    ]}
  />
</HMSMap>
```

---

## 4. Configuration and Description

### Configuring Obfuscation Scripts

  

Do not obfuscate HMS Core SDK before compiling the APK. Otherwise, function errors may occur. The configuration file in Android Studio is **proguard-rules.pro**

  

1. Open the obfuscation configuration file of your Android project.

2. Add configuration to ensure that HMS Core SDK is not obfuscated.

```
-ignorewarnings
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keep class com.hianalytics.android.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}
-repackageclasses
```

3. If you have used AndResGuard, add the AndResGuard allowlist to the obfuscation configuration file.

```
"R.string.hms*","R.string.connect_server_fail_prompt_toast",
"R.string.getting_message_fail_prompt_toast",
"R.string.no_available_network_prompt_toast",
"R.string.third_app_*",
"R.string.upsdk_*",
"R.layout.hms*",
"R.layout.upsdk_*",
"R.drawable.upsdk*",
"R.color.upsdk*",
"R.dimen.upsdk*",
"R.style.upsdk*
```

---

## 5. Sample Project

This plugin includes a demo project in the [example](example) folder, there you can find more usage examples.

---

## 6. Questions or Issues

If you have questions about how to use HMS samples, try the following options:
- [Stack Overflow](https://stackoverflow.com/questions/tagged/huawei-mobile-services) is the best place for any programming questions. Be sure to tag your question with 
**huawei-mobile-services**.
- [Github](https://github.com/HMS-Core/hms-react-native-plugin) is the official repository for these plugins, You can open an issue or submit your ideas.
- [Huawei Developer Forum](https://forums.developer.huawei.com/forumPortal/en/home?fid=0101187876626530001) HMS Core Module is great for general questions, or seeking recommendations and opinions.
- [Huawei Developer Docs](https://developer.huawei.com/consumer/en/doc/overview/HMS-Core-Plugin) is place to official documentation for all HMS Core Kits, you can find detailed documentations in there.

If you run into a bug in our samples, please submit an issue to the [GitHub repository](https://github.com/HMS-Core/hms-react-native-plugin).

---

## 7. Licensing and Terms

Huawei React-Native Plugin is licensed under [Apache 2.0 license](LICENCE)
