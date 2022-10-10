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

declare module "@hmscore/react-native-hms-ar" {
  
    import { Component } from "react";

    interface AugmentedImageObject {

        /**
         * The name of the image file to detect in the assets folder.
         */
        imgFileFromAsset: string;

        /**
         * Specified physical width in meters for image.
         */
        widthInMeters: number;

        /**
         * The image name.
         */
        imgName: string;
    }

    interface PlaneText extends RGBAColor {

        /**
         * The text that will appear when the plane is detected.
         */
        text?: string;
    }

    interface PlaneImage {

        /**
         * The texture that will appear when the plane is detected.
         */
        image?: string;
    }

    interface RGBAColor {
        red?: number;
        blue?: number;
        green?: number;
        alpha?: number;
    }

    interface Semantic {
        mode?: SemanticMode;
        showSemanticModeSupportedInfo?: boolean;
    }

    interface HandDetail {

        /**
         * The box color.
         */
        boxColor?: RGBAColor;

        /**
         * The box width.
         */
        lineWidth?: number;

        /**
         * Enable to draw box.
         */
        drawBox?: boolean;

        /**
         * Enable to draw skeleton line.
         */
        drawLine?: boolean;

        /**
         * Enable to draw skeleton points.
         */
        drawPoint?: boolean;

        /**
         * The skeleton line width
         */
        lineWidthSkeleton?: number;

        /**
         * The skeleton point size.
         */
        pointSize?: number;

        /**
         * The skeleton line color.
         */
        lineColor?: RGBAColor;

        /**
         * The skeleton point color.
         */
        pointColor?: RGBAColor;

        /**
         * Front camera or rear camera setting.
         */
         cameraLensFacing?: CameraLensFacing;

        /**
         * The lighting estimate mode.
         */        
         lightMode?: LightMode;
        
         /**
          * Semantic recognition mode.
          */
         semantic?: Semantic;
         
         /**
          * The power consumption mode.
          */
         powerMode?: PowerMode;
 
         /**
          * The focus mode.
          */
         focusMode?: FocusMode;
 
         /**
          * The update mode.
          */
         updateMode?: UpdateMode;
    }

    interface Hand {
        hand: HandDetail;
    }

    interface Hand {
        hand: HandDetail;
    }

    interface HandProps extends BaseProps {

        /**
         * Set configurations of AR Hand View.
         */
        config: Hand;
    }

    interface BodyDetail {

        /**
         * Enable to draw skeleton line.
         */
        drawLine?: boolean;

        /**
         * Enable to draw skeleton points.
         */
        drawPoint?: boolean;
        
        /**
         * The skeleton line width.
         */
        lineWidth?: number;

        /**
         * The skeleton point size.
         */
        pointSize?: number;

        /**
         * The skeleton line color.
         */
        lineColor?: RGBAColor;

        /**
         * The skeleton point color.
         */
        pointColor?: RGBAColor;

        /**
         * The lighting estimate mode.
         */        
         lightMode?: LightMode;
        
         /**
          * Semantic recognition mode
          */
         semantic?: Semantic;
         
         /**
          * The power consumption mode.
          */
         powerMode?: PowerMode;
 
         /**
          * The focus mode.
          */
         focusMode?: FocusMode;
 
         /**
          * The update mode.
          */
         updateMode?: UpdateMode;
    }

    interface Body {
        body: BodyDetail;
    }

    interface BodyProps extends BaseProps {

        /**
         * Set configurations of AR Body View.
         */
        config: Body;
    }

    interface Healty {

        /**
         * Health check progress.
         */
        handleProcessProgressEvent?: (num: number) => void;

        /**
         * The status of detection of the face.
         */
        handleEvent?: (status: string) => void;

        /**
         * Detection result.
         */
        handleResult?: (result: string) => void;
    }

    interface FaceDetail {

        /**
         * Enable to health mode.
         */
        enableHealthDevice?: boolean;

        /**
         * If "enableHealtyDevice" is true, the events here are triggered.
         */
        healty?: Healty;

        /**
         * Enable to multiple face detection features.
         */
        multiFace?: boolean;
        
        /**
         * Draw the face.
         */
        drawFace?: boolean;

        /**
         * Size of points.
         */
        pointSize?: number;

        /**
         * If "texturePath" is empty, it is created according to the color code in this field.
         */
        depthColor?: RGBAColor;

        /**
         * The texture is created according to the image given from the assets folder.
         */
        texturePath: string;

        /**
         * Front camera or rear camera setting.
         */
        cameraLensFacing?: CameraLensFacing;

        /**
         * The lighting estimate mode.
         */        
        lightMode?: LightMode;
        
        /**
         * Semantic recognition mode
         */
        semantic?: Semantic;
        
        /**
         * The power consumption mode.
         */
        powerMode?: PowerMode;

        /**
         * The focus mode.
         */
        focusMode?: FocusMode;

        /**
         * The update mode.
         */
        updateMode?: UpdateMode;
    }

    interface Face {
        face: FaceDetail;
    }

    interface FaceProps extends BaseProps {

        /**
         * Set configurations of AR Face View.
         */
        config: Face;
    }

    interface WorldDetail {
        /**
         * The file name in the asset folder of the 3D object to be added when the screen is clicked.
         */
        objectName?: string;

        /**
         * The file name of the texture of the object to be added when clicking on the screen in the asset folder.
         */
        objectTexture?: string;

        /**
         * Enable to draw planes.
         */
        showPlanes?: boolean;

        /**
         * Configuration for planes not listed.
         */
        planeOther?: PlaneText | PlaneImage;

        /**
         * Configuration to specify the wall.
         */
        planeWall?: PlaneText | PlaneImage;

        /**
         * Configuration to specify the floor.
         */
        planeFloor?: PlaneText | PlaneImage;

        /**
         * Configuration to specify the seat.
         */
        planeSeat?: PlaneText | PlaneImage;

        /**
         * Configuration to specify the table.
         */
        planeTable?: PlaneText | PlaneImage;

        /**
         * Configuration to specify the ceiling.
         */
        planeCeiling?: PlaneText | PlaneImage;

        /**
         * Sets the maximum memory size for storing map data.
         */
        maxMapSize?: number;

        /**
         * 2D image information to tracking.
         */
        augmentedImages?: AugmentedImageObject[];

        /**
         * The plane finding mode.
         */
        planeFindingMode?: PlaneFindingMode;

        /**
         * The lighting estimate mode.
         */
        lightMode?: LightMode;
        
        /**
         * Semantic recognition mode.
         */
        semantic?: Semantic;
        
        /**
         * The power consumption mode.
         */
        powerMode?: PowerMode;

        /**
         * The focus mode.
         */
        focusMode?: FocusMode;

        /**
         * The update mode.
         */
        updateMode?: UpdateMode;

        /**
         * Enable to draw line for Augmented Images.
         */
         drawLineAI?: boolean;

         /**
          * Enable to draw points for Augmented Images.
          */
         drawPointAI?: boolean;
 
         /**
          * The line width for Augmented Images.
          */
         lineWidthAI?: number;
 
         /**
          * The point size for Augmented Images.
          */
         pointSizeAI?: number;
 
         /**
          * The line color for Augmented Images.
          */
         lineColorAI?: RGBAColor;
 
         /**
          * The point color for Augmented Images.
          */
         pointColorAI?: RGBAColor;
    }

    interface World {
        world: WorldDetail;
    }

    interface WorldProps extends BaseProps {

        /**
         * Set configurations of AR World View.
         */
        config: World;
    }

    interface AugmentedImageDetail {

        /**
         * 2D image information to tracking.
         */
        augmentedImages?: AugmentedImageObject[];

        /**
         * The lighting estimate mode.
         */
         lightMode?: LightMode;
        
         /**
          * Semantic recognition mode.
          */
         semantic?: Semantic;
         
         /**
          * The power consumption mode.
          */
         powerMode?: PowerMode;
 
         /**
          * The focus mode.
          */
         focusMode?: FocusMode;
 
         /**
          * The update mode.
          */
         updateMode?: UpdateMode;

         /**
         * Enable to draw line.
         */
        drawLine?: boolean;

        /**
         * Enable to draw points.
         */
        drawPoint?: boolean;

        /**
         * The line width
         */
        lineWidth?: number;

        /**
         * The point size.
         */
        pointSize?: number;

        /**
         * The line color.
         */
        lineColor?: RGBAColor;

        /**
         * The point color.
         */
        pointColor?: RGBAColor;
    }

    interface AugmentedImage {
        augmentedImage: AugmentedImageDetail;
    }

    interface AugmentedImageProps extends DrawProps, HandleCameraProps {

        /**
         * Set configurations of AR Augmented Image View.
         */
        config: AugmentedImage;
    }

    interface WorldBodyDetail {

        /**
         * The file name in the asset folder of the 3D object to be added when the screen is clicked.
         */
         objectName?: string;

         /**
          * The file name of the texture of the object to be added when clicking on the screen in the asset folder.
          */
         objectTexture?: string;
 
         /**
          * Enable to draw planes.
          */
         showPlanes?: boolean;
 
         /**
          * Configuration for planes not listed.
          */
         planeOther?: PlaneText | PlaneImage;
 
         /**
          * Configuration to specify the wall.
          */
         planeWall?: PlaneText | PlaneImage;
 
         /**
          * Configuration to specify the floor.
          */
         planeFloor?: PlaneText | PlaneImage;
 
         /**
          * Configuration to specify the seat.
          */
         planeSeat?: PlaneText | PlaneImage;
 
         /**
          * Configuration to specify the table.
          */
         planeTable?: PlaneText | PlaneImage;
 
         /**
          * Configuration to specify the ceiling.
          */
         planeCeiling?: PlaneText | PlaneImage;

        /**
         * Sets the maximum memory size for storing map data.
         */
        maxMapSize?: number;

        /**
         * 2D image information to tracking.
         */
        augmentedImages?: AugmentedImageObject[];

        /**
         * The plane finding mode.
         */
        planeFindingMode?: PlaneFindingMode;

        /**
         * Enable to draw skeleton line.
         */
        drawLine?: boolean;

        /**
         * Enable to draw skeleton points.
         */
        drawPoint?: boolean;
        
        /**
         * The skeleton line width.
         */
        lineWidth?: number;

        /**
         * The skeleton point size.
         */
        pointSize?: number;

        /**
         * The skeleton line color.
         */
        lineColor?: RGBAColor;

        /**
         * The skeleton point color.
         */
        pointColor?: RGBAColor;
        
        /**
         * The lighting estimate mode.
         */
        lightMode?: LightMode;
        
        /**
         * Semantic recognition mode.
         */
        semantic?: Semantic;
        
        /**
         * The power consumption mode.
         */
        powerMode?: PowerMode;

        /**
         * The focus mode.
         */
        focusMode?: FocusMode;

        /**
         * The update mode.
         */
        updateMode?: UpdateMode;
    }

    interface WorldBody {
        worldBody: WorldBodyDetail;
    }

    interface WorldBodyProps extends BaseProps {

        /**
         * Set configurations of AR World Body View.
         */
        config: WorldBody;
    }

    interface SceneMeshDetail {
        objectName?: string;
        objectTexture?: string;
        
        /**
         * The lighting estimate mode.
         */
         lightMode?: LightMode;
        
         /**
          * Semantic recognition mode.
          */
         semantic?: Semantic;
         
         /**
          * The power consumption mode.
          */
         powerMode?: PowerMode;
 
         /**
          * The focus mode.
          */
         focusMode?: FocusMode;
 
         /**
          * The update mode.
          */
         updateMode?: UpdateMode;
    }

    interface SceneMesh {
        sceneMesh: SceneMeshDetail;
    }

    interface SceneMeshProps extends BaseProps {

        /**
         * Set configurations of AR Scene Mesh View.
         */
        config: SceneMesh;
    }

    export default class ARView extends Component< HandProps | FaceProps | WorldBodyProps | BodyProps | WorldProps | AugmentedImageProps | SceneMeshProps, any > {
        constructor(props: HandProps | FaceProps | WorldBodyProps | BodyProps | WorldProps | AugmentedImageProps | SceneMeshProps);
    }

    export class HmsARModule {

        /**
         * Check if the AR Engine Service APK is ready.
         */
        static isAREngineReady(): Promise<boolean>;

        /**
         * Opens the AR Engine app page in the AppGallery.
         */
        static navigateToAppMarket(): void;

        /**
         * Enables HMSLogger capability which is used for sending usage analytics of AR Engine SDK's methods to improve the service quality.
         */
        static enableLogger(): void;

        /**
         * Disables HMSLogger capability which is used for sending usage analytics of AR Engine SDK's methods to improve the service quality.
         */
        static disableLogger(): void;

        static LightMode: LightMode;
        static CameraLensFacing: CameraLensFacing;
        static SemanticMode: SemanticMode;
        static PowerMode: PowerMode;
        static FocusMode: FocusMode;
        static UpdateMode: UpdateMode;
        static PlaneFindingMode: PlaneFindingMode;
        static TracingState: TracingState;
        static HandTypes: HandTypes;
        static HandSkeletonType: HandSkeletonType;
        static BodySkeletonType: BodySkeletonType;
        static SemanticPlaneLabel: SemanticPlaneLabel;
        static PlaneTypes: PlaneTypes;
        static CoordinateSystemType: CoordinateSystemType;
    }

    interface BaseProps extends DrawProps, MessageListenerProps, HandleCameraProps { }

    interface DrawProps {
        /**
         * Register a callback method on gl surface view's onDrawFrame method.
         */
        onDrawFrame: (e: string) => void;
    }

    interface MessageListenerProps {
        messageListener: (text: string) => void;
    }

    interface HandleCameraProps {
        handleCameraConfig: (e: string) => void;
        handleCameraIntrinsics: (e: string) => void;
    }

    /**
     * The lighting estimate mode.
     */
    export enum LightMode {
        
        /**
         * None.
         */
        NONE = 0,

        /**
         * Enable the lighting intensity estimate capability.
         */
        AMBIENT_INTENSITY = 1,

        /**
         * Enable the ambient lighting estimate capability.
         */
        ENVIRONMENT_LIGHTING = 2,

        /**
         * Enable the lighting environment texture estimate capability.
         */
        ENVIRONMENT_TEXTURE = 4,

        /**
         * Enable all lighting estimate capabilities.
         */
        ALL = 65535
    }

    /**
     * The camera type, which can be front or rear.
     */
    export enum CameraLensFacing {

        /**
         * Rear camera.
         */
        REAR = 1,

        /**
         * Front camera.
         */
        FRONT = 2
    }

    /**
     * Semantic recognition mode
     */
    export enum SemanticMode {
        
        /**
         * None.
         */
        NONE = 0,
        
        /**
         * Plane.
         */
        PLANE = 1,

        /**
         * Target.
         */
        TARGET = 2,

        /**
         * All.
         */
        ALL = 3
    }

    /**
     * The power consumption mode.
     */
    export enum PowerMode {

        /**
         * Common mode.
         */
        NORMAL = 1,

        /**
         * Power saving mode.
         */
        POWER_SAVING = 2,

        /**
         * Ultra power saving mode.
         */
        ULTRA_POWER_SAVING = 3,

        /**
         * Performance first.
         */
        PERFORMANCE_FIRST = 4
    }

    /**
     * The focus mode.
     */
    export enum FocusMode {

        /**
         * Fixed focus to infinity focus.
         */
        FIXED_FOCUS = 1,

        /**
         * Auto focus.
         */
        AUTO_FOCUS = 2
    }

    /**
     * The update mode.
     */
    export enum UpdateMode {

        /**
         * The update() method of ARSession returns data only when a new frame is available.
         */
        BLOCKING = 1,

        /**
         * The update() method of ARSession returns data immediately.
         */
        LATEST_CAMERA_IMAGE = 2
    }

    /**
     * The plane finding mode.
     */
    export enum PlaneFindingMode {

        /**
         * Plane detection is disabled.
         */
        DISABLED = 1,

        /**
         * Plane detection is enabled, including horizontal and vertical planes.
         */
        ENABLE = 2,

        /**
         * Only the vertical plane is detected.
         */
        HORIZONTAL_ONLY = 3,

        /**
         * Only the vertical plane is detected.
         */
        VERTICAL_ONLY = 4
    }

    /**
     * The tracking status of the trackable object.
     */
    export enum TracingState {

        /**
         * Unknown.
         */
        UNKNOWN_STATE = 0,

        /**
         * Tracking status.
         */
        TRACKING = 1,

        /**
         * Paused status.
         */
        PAUSED = 2,

        /**
         * Stopped status.
         */
        STOPPED = 3,
    }

    /**
     * The type of hand, which can be left or right.
     */
    export enum HandTypes {

        /**
         * Unknown or the hand type cannot be distinguished.
         */
        AR_HAND_UNKNOWN = 0,

        /**
         * Right hand.
         */
        AR_HAND_RIGHT = 1,

        /**
         * Left hand.
         */
        AR_HAND_LEFT = 2,
    }

    /**
     * The hand skeleton point types.
     */
    export enum HandSkeletonType {

        /**
         * Unknown.
         */
        UNKNOWN = 0,

        /**
         * The root point of the hand bone, that is, the wrist.
         */
        ROOT = 1,

        /**
         * Pinky knuckle 1.
         */
        PINKY_1 = 2,

        /**
         * Pinky knuckle 2.
         */
        PINKY_2 = 3,

        /**
         * Pinky knuckle 3.
         */
        PINKY_3 = 4,

        /**
         * Pinky finger tip.
         */
        PINKY_4 = 5,

        /**
         * Ring finger knuckle 1.
         */
        RING_1 = 6,

        /**
         * Ring finger knuckle 2.
         */
        RING_2 = 7,

        /**
         * Ring finger knuckle 3.
         */
        RING_3 = 8,

        /**
         * Ring finger tip.
         */
        RING_4 = 9,

        /**
         * Middle finger knuckle 1.
         */
        MIDDLE_1 = 10,

        /**
         * Middle finger knuckle 2.
         */
        MIDDLE_2 = 11,

        /**
         * Middle finger knuckle 3.
         */
        MIDDLE_3 = 12,

        /**
         * Middle finger tip.
         */
        MIDDLE_4 = 13,

        /**
         * Index finger knuckle 1.
         */
        INDEX_1 = 14,

        /**
         * Index finger knuckle 2.
         */
        INDEX_2 = 15,

        /**
         * Index finger knuckle 3.
         */
        INDEX_3 = 16,

        /**
         * Index finger tip.
         */
        INDEX_4 = 17,

        /**
         * Thumb knuckle 1.
         */
        THUMB_1 = 18,

        /**
         * Thumb knuckle 2.
         */
        THUMB_2 = 19,

        /**
         * Thumb knuckle 3.
         */
        THUMB_3 = 20,

        /**
         * Thumb tip.
         */
        THUMB_4 = 21,

        /**
         * Number of knuckles.
         */
        LENGTH = 22
    }

    /**
     * The body skeleton point types.
     */
    export enum BodySkeletonType {
        /**
         * Unknown.
         */
        UNKNOWN = 0,

        /**
         * Head.
         */
        HEAD = 1,

        /**
         * Neck.
         */
        NECK = 2,

        /**
         * Right shoulder.
         */
        R_SHO = 3,

        /**
         * Right elbow.
         */
        R_ELBOW = 4,

        /**
         * Right wrist.
         */
        R_WRIST = 5,

        /**
         * Left shoulder.
         */
        L_SHO = 6,

        /**
         * Left elbow.
         */
        L_ELBOW = 7,

        /**
         * Left wrist.
         */
        L_WRIST = 8,

        /**
         * Right hip.
         */
        R_HIP = 9,

        /**
         * Right knee.
         */
        R_KNEE = 10,

        /**
         * Right ankle.
         */
        R_ANKLE = 11,

        /**
         * Left hip joint.
         */
        L_HIP = 12,

        /**
         * Left knee.
         */
        L_KNEE = 13,

        /**
         * Left ankle.
         */
        L_ANKLE = 14,

        /**
         * Center of hip joint.
         */
        HIP_MID = 15,

        /**
         * Right ear.
         */
        R_EAR = 16,

        /**
         * Right eye.
         */
        R_EYE = 17,

        /**
         * Nose.
         */
        NOSE = 18,

        /**
         * Left eye.
         */
        L_EYE = 19,

        /**
         * Left ear.
         */
        L_EAR = 20,

        /**
         * Spine.
         */
        SPINE = 21,

        /**
         * Right toe.
         */
        R_TOE = 22,

        /**
         * Left toe.
         */
        L_TOE = 23,

        /**
         * Number of joints, instead of a joint point.
         */
        LENGTH = 24
    }

    /**
     * Semantic types of the current plane.
     */
    export enum SemanticPlaneLabel {
        
        /**
         * Other.
         */
        PLANE_OTHER = 0,
        
        /**
         * Wall.
         */
        PLANE_WALL = 1,
        
        /**
         * Floor.
         */
        PLANE_FLOOR = 2,
        
        /**
         * Seat.
         */
        PLANE_SEAT = 3,

        /**
         * Table.
         */
        PLANE_TABLE = 4,

        /**
         * Ceiling.
         */
        PLANE_CEILING = 5,

        /**
         * Door.
         */
        PLANE_DOOR = 6,

        /**
         * Window.
         */
        PLANE_WINDOW = 7,

        /**
         * Bed.
         */
        PLANE_BED = 8
    }

    /**
     * The plane type.
     */
    export enum PlaneTypes {

        /**
         * A horizontal plane facing up (such as the ground and desk platform).
         */
        HORIZONTAL_UPWARD_FACING = 0,

        /**
         * A horizontal plane facing down (such as the ceiling).
         */
        HORIZONTAL_DOWNWARD_FACING = 1,

        /**
         * A vertical plane.
         */
        VERTICAL_FACING = 2,

        /**
         * Unsupported type.
         */
        UNKNOWN_FACING = 3,
    }

    /**
     * The coordinate system type.
     */
    export enum CoordinateSystemType {
        
        /**
         * Unknown coordinate system.
         */
        UNKNOWN = 0,

        /**
         * World coordinate system.
         */
        "3D_WORLD" = 1,

        /**
         * Local coordinate system.
         */
        "3D_SELF" = 2,

        /**
         * OpenGL NDC coordinate system.
         */
        "2D_IMAGE" = 3,

        /**
         * Camera coordinate system.
         */
        "3D_CAMERA" = 4
    }
}