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

declare module "@hmscore/react-native-hms-scan" {
  /**
   *  Address Style
   *  Residential: 0
   *  Other: -1
   *  Office: 1
   */
  export const AddressType = {
    /**
     *  Family address.
     */
    Residential: 0,

    /**
     *  Unknown type.
     */
    Other: -1,

    /**
     *  Work address.
     */
    Office: 1,
  };
  
  export type AddressTypeType = 0 | -1 | 1;

  /**
   *  Object that contains address details..
   */
  export interface AddressInfo {
    /**
     *  Address information array.
     */
    addressDetails?: String[];

    /**
     *  Address type.
     */
    addressType?: AddressTypeType;
  }

  /**
   *  Object that contains corner point coordinates.
   */
  export interface CornerPoint {
    /**
     *  X coordinate.
     */
    x?: number;

    /**
     *  Y coordinate.
     */
    y?: number;
  }

  /**
   *  Object that contains driver details.
   */
  export interface DriverInfo {
    /**
     *  Street.
     */
    avenue?: String;

    /**
     *  Driver license number.
     */
    certificateNumber?: String;

    /**
     *  Driver license type.
     */
    certificateType?: String;

    /**
     *  City.
     */
    city?: String;

    /**
     *  Country where the driver license is issued.
     */
    countryOfIssue?: String;

    /**
     *  Birth date.
     */
    dateOfBirth?: String;

    /**
     *  Expiration date of the driver license.
     */
    dateOfExpire?: String;

    /**
     *  Issue date of the driver license.
     */
    dateOfIssue?: String;

    /**
     *  Last name.
     */
    familyName?: String;

    /**
     *  First name.
     */
    givenName?: String;

    /**
     *  Middle name.
     */
    middleName?: String;

    /**
     *  Province or state in the address.
     */
    province?: String;

    /**
     *  Gender.
     */
    sex?: String;

    /**
     *  ZIP code.
     */
    zipCode?: String;
  }

  /**
   *  Email Address Types
   */
  export const EmailAddressType = {
    /**
     *  Work email address.
     */
    Office: 1,

    /**
     *  Family email address.
     */
    Residential: 0,

    /**
     *  Unknown email address.
     */
    Other: -1,
  };
  
  /**
   *  Email Address Types
   *  Residential: 0
   *  Other: -1
   *  Office: 1
   */
  export type EmailAddressTypeType = 0 | -1 | 1;

  /**
   *  Object that contains email details.
   */
  export interface EmailContent {
    /**
     *  Email address.
     */
    addressInfo?: String;

    /**
     *  Email address type.
     */
    addressType?: EmailAddressTypeType;

    /**
     *  Email content.
     */
    bodyInfo?: String;

    /**
     *  Subject of an email.
     */
    subjectInfo?: String;
  }

  /**
   *  Object that contains event time details.
   */
  export interface EventTime {
    /**
     *  Day.
     */
    day?: number;

    /**
     *  Hours.
     */
    hours?: number;

    /**
     *  Determines whether the time is in UTC format.
     */
    isUTCTime?: boolean;

    /**
     *  Minutes.
     */
    minutes?: number;

    /**
     *  Month.
     */
    month?: number;

    /**
     *  Barcode value.
     */
    originalValue?: String;

    /**
     *  Seconds.
     */
    seconds?: number;

    /**
     *  Year.
     */
    year?: number;
  }

  /**
   *  Object that contains url details.
   */
  export interface LinkUrl {
    /**
     *  URL.
     */
    linkvalue?: String;

    /**
     *  Title.
     */
    theme?: String;
  }

  /**
   *  Object that contains location coordinates.
   */
  export interface LocationCoordinate {
    /**
     *  Latitude.
     */
    latitude?: number;

    /**
     *  Longitude.
     */
    longitude?: number;
  }

  /**
   *  Object that contains person details.
   */
  export interface PeopleName {
    /**
     *  Last name.
     */
    familyName?: String;

    /**
     *  Full name of a contact.
     */
    fullName?: String;

    /**
     *  First name.
     */
    givenName?: String;

    /**
     *  Middle name.
     */
    middleName?: String;

    /**
     *  Prefix of a contact name.
     */
    namePrefix?: String;

    /**
     *  Suffix of a contact name.
     */
    nameSuffix?: String;

    /**
     *  Contact name spelling.
     */
    spelling?: String;
  }

  /**
   *  Object that contains sms details.
   */
  export interface SmsContent {
    /**
     *  SMS information.
     */
    msgContent?: String;

    /**
     *  Phone number.
     */
    destPhoneNumber?: String;
  }

  /**
   *  Phone number types
   */
  export const PhoneNumberType = {
    /**
     *  Fax number.
     */
    Fax: 0,

    /**
     *  Home phone number.
     */
    Residential: 1,

    /**
     *  Mobile number.
     */
    Cellphone: 2,

    /**
     *  Unknown phone number.
     */
    Other: -1,

    /**
     *  Work phone number.
     */
    Office: 3,
  };


  /**
   *  Phone Number Types
   *  Fax: 0
   *  Residential: 1
   *  Cellphone: 2
   *  Other: -1
   *  Office: 3
   */
  export type PhoneNumberTypeType = 0 | -1 | 1 | 2 | 3;

  /**
   *  Object that contains phone number details.
   */
  export interface TelPhoneNumber {
    /**
     *  Phone number.
     */
    telPhoneNumber?: String;

    /**
     *  Phone number type.
     */
    useType?: PhoneNumberTypeType;
  }

  /**
   *  Wifi Modes
   */
  export const WifiModeType = {
    /**
     *  Open Wi-Fi.
     */
    NoPass: 0,

    /**
     *  Wi-Fi in WEP mode.
     */
    WEP: 1,

    /**
     *  Wi-Fi in WPA mode.
     */
    WPA: 2,
  };

  /**
   *  Wifi Modes
   *  NoPass: 0
   *  WEP: 1
   *  WPA: 2
   */
  export type WifiModeTypeType = 0 |  1 | 2 ;

  /**
   *  Object that contains wifi connection details.
   */
  export interface WiFiConnectionInfo {
    /**
     *  Wi-Fi password.
     */
    password?: String;

    /**
     *  SSID.
     */
    ssidNumber?: String;

    /**
     *  Wi-Fi encryption mode.
     */
    cipherMode?: WifiModeTypeType;
  }

  /**
   *  Object that contains contact details.
   */
  export interface ContactDetail {
    /**
     *  Address information.
     */
    addressesInfos?: AddressInfo[];

    /**
     *  Company information.
     */
    company?: String;

    /**
     *  URL information.
     */
    contactLinks?: String[];

    /**
     *  Email content.
     */
    eMailContents?: EmailContent[];

    /**
     *  Note.
     */
    note?: String;

    /**
     *  Contact information.
     */
    peopleName?: PeopleName;

    /**
     *  Phone number list.
     */
    telPhoneNumbers?: TelPhoneNumber[];

    /**
     *  Title.
     */
    title?: string;
  }

  /**
   *  Object that contains event details.
   */
  export interface EventInfo {
    /**
     *  Calendar event description.
     */
    abstractInfo?: String;

    /**
     *  Start date of a calendar event.
     */
    beginTime?: EventTime;

    /**
     *  End date of a calendar event.
     */
    closeTime?: EventTime;

    /**
     *  Calendar event status information.
     */
    condition?: String;

    /**
     *  Calendar event location information.
     */
    placeInfo?: String;

    /**
     *  Calendar event organizer information.
     */
    sponsor?: String;

    /**
     *  Calendar event summary.
     */
    theme?: String;
  }

  /**
   *  Object that contains barcode rectangle details.
   */
  export interface BorderRect {
    /**
     *  Left x coordinate.
     */
    left?: number;

    /**
     *  Top y coordinate.
     */
    top?: number;

    /**
     *  Right x coordinate.
     */
    right?: number;

    /**
     *  Bottom y coordinate.
     */
    bottom?: number;

    /**
     *  Exact value of x coordinate center.
     */
    exactCenterX?: number;

    /**
     *  Exact value of y coordinate center.
     */
    exactCenterY?: number;

    /**
     *  Rounded value of x coordinate center.
     */
    centerX?: number;

    /**
     *  Rounded value of y coordinate center.
     */
    centerY?: number;

    /**
     *  List of corner points.
     */
    cornerPoints?: CornerPoint[];
  }

  /**
   *  Text options for MultiCameraRequest object.
   */
  export interface ScanTextOptions {
    /**
     *  Text color. Default value: -16777216(black)
     */
    textColor?: number;

    /**
     *  Text size. Default value: 35
     */
    textSize?: number;

    /**
     *  Indicates whether the text is visible. Default value:  true
     */
    showText?: boolean;

    /**
     *  Indicates whether to limit the text in rectangle bounds.
     *  Default value:  false
     */
    showTextOutBounds?: boolean;

    /**
     *  Text background color. Default value:  0(transparent)
     */
    textBackgroundColor?: number;

    /**
     *  Indicates whether the text auto size itself. Default value:  false
     */
    autoSizeText?: boolean;

    /**
     *  Minimum text size. Default value:  24
     */
    minTextSize?: number;

    /**
     *  Granularity. Default value:  2
     */
    granularity?: number;
  }

  /**
   *  Scan Types
   */
  export const ScanType = {
    /**
     *  Unknown barcode format.
     */
    Other: -1,

    /**
     *  All supported barcode formats.
     */
    All: 0,

    /**
     *  Code 128.
     */
    Code128: 64,

    /**
     *  Code 39.
     */
    Code39: 16,

    /**
     *  Code 93.
     */
    Code93: 32,

    /**
     *  Codabar.
     */
    Codabar: 4096,

    /**
     *  Data Matrix.
     */
    DataMatrix: 4,

    /**
     *  EAN-13.
     */
    EAN13: 128,

    /**
     *  EAN-8.
     */
    EAN8: 256,

    /**
     *  ITF-14.
     */
    ITF14: 512,

    /**
     *  QR code.
     */
    QRCode: 1,

    /**
     *  UPC-A.
     */
    UPCCod: 1024,

    /**
     *  UPC-E.
     */
    UPCCod: 2048,

    /**
     *  PDF-417.
     */
    Pdf417: 8,

    /**
     *  Aztec.
     */
    Aztec: 2,
  };

  /**
   *  Options for scan types
   *  Other: -1
   *  All: 0
   *  Code128: 64
   *  Code39: 16
   *  Code93: 32
   *  Codabar: 4096
   *  DataMatrix: 4
   *  EAN13: 128
   *  EAN8: 256
   *  ITF14: 512
   *  QRCode: 1
   *  UPCCodeA: 1024
   *  UPCCodeE: 2048
   *  Pdf417: 8
   *  Aztec: 2
   */
  export type ScanTypeType =
    | -1
    | 0
    | 64
    | 16
    | 32
    | 4096
    | 4
    | 128
    | 256
    | 512
    | 1
    | 1024
    | 2048
    | 8
    | 2;

  /**
   *  Scan Modes
   */
  export const ScanMode = {
    /**
     *  Multi processor Sync Mode..
     */
    Sync: 444,

    /**
     *  Multi processor Async Mode..
     */
    Async: 555,
  };

  /**
   *  Scan Modes
   *  Sync: 444
   *  Async: 555
   */
  export type ScanModeType = 444 | 555;

  /**
   *  Scan Forms
   */
  export const ScanForm = {
    /**
     *  Unknown barcode content.
     */
    Other: -1,

    /**
     *  Contact information.
     */
    ContactDetail: 1009,

    /**
     *  Email information.
     */
    EmailContent: 1002,

    /**
     *  ISBN.
     */
    ISBNNumber: 1012,

    /**
     *  Phone number.
     */
    TelPhoneNumber: 1003,

    /**
     *  Product information.
     */
    ArticleNumber: 1001,

    /**
     *  SMS content.
     */
    SMS: 1005,

    /**
     *  Text.
     */
    PureText: 1004,

    /**
     *  URL.
     */
    Url: 1006,

    /**
     *  Wi-Fi.
     */
    WIFIConnectInfo: 1007,

    /**
     *  Location.
     */
    LocationCoordinate: 1011,

    /**
     *  Calendar event.
     */
    EventInfo: 1008,

    /**
     *  Driver license information.
     */
    DriverInfo: 1010,
  };

  
  /**
   *  Scan Forms
   *  Other: -1
   *  ContactDetail: 1009
   *  EmailContent: 1002
   *  ISBNNumber: 1012
   *  TelPhoneNumber: 1003
   *  ArticleNumber: 1001
   *  SMS: 1005
   *  PureText: 1004
   *  Url: 1006
   *  WIFIConnectInfo: 1007
   *  LocationCoordinate: 1011
   *  EventInfo: 1008
   *  DriverInfo: 1010
   */
  export type ScanFormType =
    | -1
    | 1009
    | 1002
    | 1012
    | 1003
    | 1001
    | 1005
    | 1004
    | 1006
    | 1007
    | 1011
    | 1008
    | 1010;

  /**
   *  Information returned when the startDefaultView, startCustomizedView,
   *  decodeWithBitmap, decodeMultiSync, decodeMultiAsync and
   *  startMultiProcessorCamera APIs are succesfully called.
   */
  export interface ScanResponse {
    /**
     *  HMS Scan Version.
     */
    hmsScanVersion?: number;

    /**
     *  Barcode corner point information.
     */
    cornerPoints?: CornerPoint[];

    /**
     *  Byte array.
     */
    originValueByte?: number[];

    /**
     *  Barcode information.
     */
    originalValue?: String;

    /**
     *  Barcode format.
     */
    scanType?: ScanTypeType;

    /**
     *  Barcode content type.
     */
    scanTypeForm?: ScanFormType;

    /**
     *  Barcode value.
     */
    showResult?: String;

    /**
     *  Barcode zoom ratio.
     */
    zoomValue?: number;

    /**
     *  SMS information.
     */
    smsContent?: SmsContent;

    /**
     *  Email content.
     */
    emailContent?: EmailContent;

    /**
     *  Phone number.
     */
    telPhoneNumber?: TelPhoneNumber;

    /**
     *  URL bookmark.
     */
    linkUrl?: LinkUrl;

    /**
     *  Wi-Fi connection info.
     */
    wifiConnectionInfo?: WiFiConnectionInfo;

    /**
     *  Location information.
     */
    locationCoordinate?: LocationCoordinate;

    /**
     *  Driver license information.
     */
    driverInfo?: DriverInfo;

    /**
     *  Contact information.
     */
    contactDetail?: ContactDetail;

    /**
     *  Calendar event.
     */
    eventInfo?: EventInfo;

    /**
     *  Barcode rectangle information
     */
    borderRect?: BorderRect;
  }

  /**
   *  Request information of the startCustomizedView API.
   */
  export interface CustomizedViewRequest {
    /**
     *  Barcode type.
     */
    scanType: ScanTypeType;

    /**
     *  List of additional barcode types.
     */
    additionalScanTypes?: ScanTypeType[];

    /**
     *  Height of scan area. Default value: 240
     */
    rectHeight?: number;

    /**
     *  Width of scan area. Default value: 240
     */
    rectWidth?: number;

    /**
     *  Availability of the flash button under dim light.
     */
    flashOnLightChange?: boolean;

    /**
     *  Availability of the flash button. Default value: true
     */
    isFlashAvailable?: boolean;

    /**
     *  Availability of gallery button. Default value: true
     */
    isGalleryAvailable?: boolean;

    /**
     *  Start customized view in continuous scan mode. Default value: true
     */
    continuouslyScan?: boolean;
  }

  /**
   *  Request information of the buildBitmap API.
   */
  export interface BuildBitmapRequest {
    /**
     *  Barcode content.
     */
    content: String;

    /**
     *  Barcode type. Default value: ScanType.QrCode
     */
    type?: ScanTypeType;

    /**
     *  Barcode width. Default value: 700
     */
    width?: number;

    /**
     *  Barcode height. Default value: 700
     */
    height?: number;

    /**
     *  Barcode color. Default value: -16777216(black)
     */
    bitmapColor?: number;

    /**
     *  Barcode margin. Default value: 1
     */
    margin?: number;

    /**
     *  Barcode background color. Default value: -1(white)
     */
    backgroundColor?: number;

     /**
     *  QR Code Error Correction Level.
     */
    qrErrorCorrectionLevel?: number;

    /**
     *  Barcode QR Logo Bitmap.
     */
    qrLogoBitmap?: String;

  }

  /**
   *  Request information of the decodeWithBitmap, decodeMultiSync
   *  and decodeMultiAsync APIs.
   */
  export interface DecodeRequest {
    /**
     *  The base64 string of the image.
     */
    data: String;

    /**
     *  Barcode type.
     */
    scanType: ScanTypeType;

    /**
     *  List of additional barcode types.
     */
    additionalScanTypes?: ScanTypeType[];
  }

  /**
   *  Request information of the startDefaultView API.
   */
  export interface DefaultViewRequest {
    /**
     *  Barcode type.
     */
    scanType: ScanTypeType;

    /**
     *  List of additional barcode types.
     */
    additionalScanTypes?: ScanTypeType[];
  }

  /**
   *  Request information of the startMultiProcessorCamera API.
   */
  export interface MultiCameraRequest {
    /**
     *  Scan mode.
     */
    scanMode: ScanModeType;

    /**
     *  Barcode type.
     */
    scanType: ScanTypeType;

    /**
     *  List of additional barcode types.
     */
    additionalScanTypes?: ScanTypeType[];

    /**
     *  Color list. Default value: [-256](yellow)
     */
    colorList?: number[];

    /**
     *  Stroke width of rectangles. Default value: 4.0
     */
    strokeWidth?: number;

    /**
     *  Availability of gallery button. Default value: true
     */
    isGalleryAvailable?: boolean;

    /**
     *  Text options for Multi Processor Camera.
     */
    scanTextOptions?: ScanTextOptions;
  }

  /**
   *  Provides basic capabilities of HUAWEI Scan Kit.
   */
  export const Utils = {
    /**
     *  Disables HMSLogger capability which is used for sending usage
     *  analytics of Scan SDK's methods to improve the service quality.
     */
    disableLogger(): void;,

    /**
     *  Enables HMSLogger capability which is used for sending usage
     *  analytics of Scan SDK's methods to improve the service quality.
     */
    enableLogger(): void;,

    /**
     *  Bitmap decoding API.
     */
    decodeWithBitmap(request: DecodeRequest): Promise<ScanResponse>;,

    /**
     *  Generates 1D or 2D barcodes.
     */
    buildBitmap(request: BuildBitmapRequest): Promise<String>;,

    /**
     *  Starts the barcode scanning UI of Huawei.
     */
    startDefaultView(request: DefaultViewRequest): Promise<ScanResponse>;,
  };

  /**
   *  Contains the methods for requesting and checking th camera and storage permissions.
   */
  export const Permission = {
    /**
     *  Checks whether your app has camera and storage permissions.
     */
    hasCameraAndStoragePermission(): Promise<boolean>;,

    /**
     *  Requests camera and storage permissions for your app.
     */
    requestCameraAndStoragePermission(): Promise<boolean>;,
  };

  /**
   *  Contains the methods for starting a camera or decoding an image
   *  in Multi Processor modes.
   */
  export const MultiProcessor = {
    /**
     *  Scans barcodes synchronously in MultiProcessor mode.
     */
    decodeMultiSync(request: DecodeRequest): Promise<ScanResponse[]>;,

    /**
     *  Scans barcodes asynchronously in MultiProcessor mode.
     */
    decodeMultiAsync(request: DecodeRequest): Promise<ScanResponse[]>;,

    /**
     *  Starts multi processor barcode scanning UI of Huawei React Native Scan Plugin.
     */
    startMultiProcessorCamera(request: MultiCameraRequest): Promise<ScanResponse[]>;,

    /**
     *  Adds listener for `onMultiProcessorResponse` event which is triggered
     *  when camera detect a barcode.
     */
    onMultiProcessorResponseListenerAdd(
      listenerFn: (response: ScanResponse) => void,
    ): void;,

    /**
     *  Removes the listener for `onMultiProcessorResponse` event.
     */
    onMultiProcessorResponseListenerRemove(): void;,

    /**
     *  Removes all event listeners.
     */
    allListenersRemove(): void;,
  };

  /**
   *  Contains the methods for Customized View operations, including
   *  starting a camera in Customized View mode.
   */
  export const CustomizedView = {
    /**
     *  Controls views of camera preview and barcode scanning in
     *  Customized View mode.
     */
    startCustomizedView(request: CustomizedViewRequest): Promise<ScanResponse>;,

    /**
     *  Pauses barcode scanning.
     */
    pauseContinuouslyScan(): Promise<boolean>;,

    /**
     *  Resumes barcode scanning.
     */
    resumeContinuouslyScan(): Promise<boolean>;,

    /**
     *  switchLight.
     */
    switchLight(): Promise<boolean>;,

    /**
     *  getLightStatus.
     */
    getLightStatus(): Promise<boolean>;,

    /**
     *  Adds listener for `onResponse` event which is triggered when
     *  continuous scanning option enabled and the camera detects a barcodee.
     */
    onResponseListenerAdd(listenerFn: (response: ScanResponse) => void): void;,

    /**
     *  Removes the listener for `onResponse` event.
     */
    onResponseListenerRemove(): void;,

    /**
     *  Adds listener for `onStart` event.
     */
    onStartListenerAdd(listenerFn: () => void): void;,

    /**
     *  Removes the listener for `onStart` event.
     */
    onStartListenerRemove(): void;,

    /**
     *  Adds listener for `onResume` event.
     */
    onResumeListenerAdd(listenerFn: () => void): void;,

    /**
     *  Removes the listener for `onResume` event.
     */
    onResumeListenerRemove(): void;,

    /**
     *  Adds listener for `onPause` event.
     */
    onPauseListenerAdd(listenerFn: () => void): void;,

    /**
     *  Removes the listener for `onPause` event.
     */
    onPauseListenerRemove(): void;,

    /**
     *  Adds listener for `onDestroy` event.
     */
    onDestroyListenerAdd(listenerFn: () => void): void;,

    /**
     *  Removes the listener for `onDestroy` event.
     */
    onDestroyListenerRemove(): void;,

    /**
     *  Adds listener for `onStop` event.
     */
    onStopListenerAdd(listenerFn: () => void): void;,

    /**
     *  Removes the listener for `onStop` event.
     */
    onStopListenerRemove(): void;,

    /**
     *  Removes all event listeners.
     */
    allListenersRemove(): void;,
  };
}
