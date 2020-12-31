# React-Native HMS Health

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

This module enables communication between **Huawei Health SDK** and React Native platform. It exposes the functionalities provided by Huawei Health SDK.

---

## 2. Installation Guide

Before you get started, you must register as a HUAWEI Developer and complete identity verification on the [HUAWEI Developer](https://developer.huawei.com/consumer/en/) website. For details, please refer to [Register a HUAWEI ID](https://developer.huawei.com/consumer/en/doc/10104).



### Creating a Project in AppGallery Connect

Creating an app in AppGallery Connect is required in order to communicate with the Huawei services. To create an app, perform the following steps:

**Step 1.** Sign in to [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html)  and select **My projects**.

**Step 2.** Select your project from the project list or create a new one by clicking the **Add Project** button.

**Step 3.** Go to **Project Setting** > **General information**, and click **Add app**.
If an app exists in the project and you need to add a new one, expand the app selection area on the top of the page and click **Add app**.

**Step 4.** On the **Add app** page, enter the app information, and click **OK**.

- A signing certificate fingerprint is used to verify the authenticity of an app when it attempts to access an HMS Core service through the HMS Core SDK. Before using HMS Core (APK), you must locally generate a signing certificate fingerprint and configure it in AppGallery Connect. Ensure that the JDK has been installed on your computer.
- To use HUAWEI Push, you need to enable the Push service. For details, please refer to [Enabling Services](https://developer.huawei.com/consumer/en/doc/distribution/app/agc-enable_service#h1-1574822945685).



### Configuring the Signing Certificate Fingerprint

**Step 1.** Go to **Project Setting > General information**. In the **App information** field, click the icon next to SHA-256 certificate fingerprint, and enter the obtained **SHA256 certificate fingerprint**.

**Step 2.** After completing the configuration, click check mark.



### Applying Huawei ID and Health Kit

**Step 1.** Register for Huawei ID by following steps in [this link](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/apply-id-0000001050069756).

**Step 2.**  Apply for Health Kit by following steps in [this link.](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/apply-kitservice-0000001050071707)

**Step 3.**  Open **AndroidManifest.xml** file and add this add the following code to application section. For **value="APP ID"**, make sure that the value is set to the app ID that has been generated while registering for Huawei ID in **step 1**.

```xml
<meta-data
android:name="com.huawei.hms.client.appid"
android:value="APP ID"/>
```



### Integrating React Native Health Plugin

**Step 1:** Sign in to [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html) and select **My projects**.

**Step 2:** Find your app project, and click the desired app name.

**Step 3:** Go to **Project Setting** > **General information**. In the **App information** section, click **agconnect-service.json** to download the configuration file.

**Step 4:** Create a React Native project if you do not have one.

**Step 5:** Copy the **agconnect-service.json** file to the **android/app** directory of your React Native project.

**Step 6:** Copy the signature file that generated in [Generating a Signing Certificate](https://developer.huawei.com/consumer/en/codelab/HMSPreparation/index.html#3) section, to the android/app directory of your React Native project.

**Step 7:** Check whether the **agconnect-services.json** file and signature file are successfully added to the **android/app** directory of the React Native project.

**Step 8:** Open the **build.gradle** file in the **android** directory of your React Native project.

- Go to **buildscript** then configure the Maven repository address and agconnect plugin for the HMS SDK.

```groovy
buildscript {    
  repositories {        
    google()        
    jcenter()        
    maven { url 'https://developer.huawei.com/repo/' }   
  }    

  dependencies {        
    /*          
      * <Other dependencies>        
      */   
    classpath 'com.huawei.agconnect:agcp:1.4.2.301'    
  }
}
```

- Go to **allprojects** then configure the Maven repository address for the HMS SDK.

```groovy
allprojects {
  repositories {
    /*          
      * <Other repositories>        
      */  
    maven { url 'https://developer.huawei.com/repo/' }
  }
}
```

**Step 9:** Open the **build.gradle** file in the **android/app** directory of your React Native project.

- Set your package name in **defaultConfig** > **applicationId** and set **minSdkVersion** to **21** or **higher**.

- Package name must match with the **package_name** entry in **agconnect-services.json** file.

```groovy
defaultConfig {
  applicationId "<package_name>"
  minSdkVersion 21
  /*
   * <Other configurations>
   */
}
```

- Copy the signature file that generated in [Generating a Signing Certificate](https://developer.huawei.com/consumer/en/codelab/HMSPreparation/index.html#3) to **android/app** directory.

- Configure the signature in **android** according to the signature file information and configure Obfuscation Scripts.

```groovy
android {
  /*
   * <Other configurations>
   */

  signingConfigs {
    config {
      storeFile file('<keystore_file>.jks')
      storePassword '<keystore_password>'
      keyAlias '<key_alias>'
      keyPassword '<key_password>'
    }
  }

  buildTypes {
    debug {
      signingConfig signingConfigs.config
    }
    release {
      signingConfig signingConfigs.config
      minifyEnabled enableProguardInReleaseBuilds
      ...
    }
  }
}
```

#### Option 1: Using NPM

**Step 1:**  Download plugin using command below.

```bash
npm i @hmscore/react-native-hms-health
```

**Step 2:**  Run your project. 

- Run the following command to the project directory.

```bash
react-native run-android  
```

#### Option 2: Download Link

To integrate the plugin, follow the steps below:

**Step 1:** Download the React Native Health Plugin and place **react-native-hms-health** under **node_modules/@hmscore** of your React Native project, as shown in the directory tree below.

```
demo-app
  |_ node_modules
    |_ @hmscore
      |_ react-native-hms-health
...
```

**Step 2:** Open **build.gradle** file which is located under project.dir > android > app directory.

- Configure build dependencies.

```groovy
buildscript {
  ...
  dependencies {
    /*
    * <Other dependencies>
    */
    implementation project(":react-native-hms-health")    
    ...    
  }
}
```

**Step 3:** Add the following lines to the **android/settings.gradle** file in your project:

```groovy
include ':app'
include ':react-native-hms-health'
project(':react-native-hms-heealth').projectDir = new File(rootProject.projectDir, '../node_modules/@hmscore/react-native-hms-health/android')
```

**Step 4:**  Import the following classes to the **MainApplication.java** file of your project.

```java
import com.huawei.hms.rn.health.HmsHealthPackage;
```

Then, add the **HmsHealthPackage()** to your **getPackages** method. In the end, your file will be similar to the following:

```java 
import com.huawei.hms.rn.health.HmsHealthPackage;
...
@Override
protected List<ReactPackage> getPackages() {
    List<ReactPackage> packages = new PackageList(this).getPackages();
    packages.add(new HmsHealthPackage()); // <-- Add this line 
    return packages;
}
...
```

**Step 5:**  Run your project. 

- Run the following command to the project directory.

```bash
react-native run-android  
```

---

## 3. API Reference

### Health Kit Data Types and Fields Usage

You can see the usable data types and fields at the current version of the Health Kit. 

#### Public Atomic Data Types

Data types in this table allow write and read operations from a third-party app.

| Data Type Name                         | Fields                                                       | Description                                               |
| -------------------------------------- | ------------------------------------------------------------ | --------------------------------------------------------- |
| DT_CONTINUOUS_STEPS_DELTA              | `FIELD_STEPS_DELTA`                                          | Step count.                                               |
| DT_CONTINUOUS_DISTANCE_DELTA           | `FIELD_DISTANCE_DELTA`                                       | Distance.                                                 |
| DT_CONTINUOUS_CALORIES_BURNT           | `FIELD_CALORIES`                                             | Calories burnt.                                           |
| DT_INSTANTANEOUS_HEART_RATE            | `FIELD_BPM`                                                  | Heart rate.                                               |
| DT_CONTINUOUS_SLEEP                    | `SLEEP_STATE`                                                | Sleep state.                                              |
| DT_INSTANTANEOUS_HEIGHT                | `FIELD_HEIGHT`                                               | Height.                                                   |
| DT_INSTANTANEOUS_SPEED                 | `FIELD_SPEED`                                                | Instantaneous speed.                                      |
| DT_INSTANTANEOUS_STEPS_RATE            | `FIELD_STEP_RATE`                                            | Step cadence.                                             |
| DT_CONTINUOUS_EXERCISE_INTENSITY       | `FIELD_INTENSITY`                                            | Medium- and high-intensity                                |
| DT_CONTINUOUS_ACTIVITY_SEGMENT         | `FIELD_TYPE_OF_ACTIVITY`                                     | Active fragment.                                          |
| DT_INSTANTANEOUS_POWER_SAMPLE          | `FIELD_POWER`                                                | Instantaneous power generated when performing an activity |
| DT_INSTANTANEOUS_HYDRATE               | `FIELD_HYDRATE`                                              | Water taken over a single drink.                          |
| DT_INSTANTANEOUS_BODY_TEMPERATURE      | `FIELD_TEMPERATURE` `FIELD_MEASURE_BODY_PART_OF_TEMPERATURE` | Body temperature.                                         |
| DT_INSTANTANEOUS_BIKING_PEDALING_RATE  | `FIELD_RPM`                                                  | Pedaling rate.                                            |
| DT_INSTANTANEOUS_BIKING_WHEEL_ROTATION | `FIELD_RPM`                                                  | Wheel speed.                                              |
| DT_INSTANTANEOUS_BODY_TEMPERATURE_REST | `FIELD_TEMPERATURE` `FIELD_MEASURE_BODY_PART_OF_TEMPERATURE` | Body temperature when resting.                            |
| DT_INSTANTANEOUS_BODY_WEIGHT           | `FIELD_BODY_WEIGHT` `FIELD_BMI` `FIELD_BODY_FAT` `FIELD_BODY_FAT_RATE` `FIELD_MUSCLE_MASS` `FIELD_BASAL_METABOLISM` `FIELD_MOISTURE` `FIELD_MOISTURE_RATE` `FIELD_VISCERAL_FAT_LEVEL` `FIELD_BONE_SALT` `FIELD_PROTEIN_RATE` `FIELD_BODY_AGE` `FIELD_BODY_SCORE` `FIELD_SKELETAL_MUSCLEL_MASS` `FIELD_IMPEDANCE` | Weight.                                                   |
| DT_INSTANTANEOUS_BLOOD_GLUCOSE         | `FIELD_LEVEL` `FIELD_MEASURE_TIME` `FIELD_SAMPLE_SOURCE`     | Blood glucose concentration.                              |
| DT_INSTANTANEOUS_BLOOD_PRESSURE        | `FIELD_SYSTOLIC_PRESSURE` `FIELD_DIASTOLIC_PRESSURE` `FIELD_BODY_POSTURE` `FIELD_MEASURE_BODY_PART_OF_BLOOD_PRESSURE` | Blood pressure.                                           |
| DT_INSTANTANEOUS_SPO2                  | `FIELD_SATURATION` `FIELD_OXYGEN_SUPPLY_FLOW_RATE` `FIELD_OXYGEN_THERAPY` `FIELD_SPO2_MEASUREMENT_MECHANISM` `FIELD_SPO2_MEASUREMENT_APPROACH` | Blood oxygen.                                             |
| DT_INSTANTANEOUS_LOCATION_SAMPLE       | `FIELD_LATITUDE` `FIELD_LONGITUDE` `FIELD_PRECISION` `FIELD_ALTITUDE` | GPS position.                                             |
| DT_INSTANTANEOUS_NUTRITION_FACTS       | `FIELD_NUTRIENTS_FACTS` `FIELD_MEAL` `FIELD_FOOD`            | Food and nutrition facts.                                 |
| DT_INSTANTANEOUS_CERVICAL_MUCUS        | `FIELD_TEXTURE` `FIELD_AMOUNT`                               | Cervical mucus.                                           |
| DT_INSTANTANEOUS_CERVICAL_STATUS       | `FIELD_POSITION` `FIELD_DILATION_STATUS` `FIELD_FIRMNESS_LEVEL` | Cervical state.                                           |
| DT_CONTINUOUS_MENSTRUAL_FLOW           | `FIELD_VOLUME`                                               | Menstrual volume.                                         |
| DT_INSTANTANEOUS_OVULATION_DETECTION   | `FIELD_DETECTION_RESULT`                                     | Ovulation test result                                     |
| DT_INSTANTANEOUS_VAGINAL_SPECKLE       | `FIELD_APPEARANCE`                                           | Vaginal spotting.                                         |
| DT_INSTANTANEOUS_STRESS                | `SCORE` `GRADE` `MEASURE_TYPE`                               | Stress.                                                   |

#### Public Statistical Data Types

Data types in this table can only be read (but not written) by third-party apps.

| Data Type Name                                       | Fields                                                       | Description                  |
| ---------------------------------------------------- | ------------------------------------------------------------ | ---------------------------- |
| DT_CONTINUOUS_STEPS_TOTAL                            | `FIELD_STEPS`                                                | Step count.                  |
| DT_CONTINUOUS_DISTANCE_TOTAL                         | `FIELD_DISTANCE`                                             | Distance.                    |
| DT_CONTINUOUS_CALORIES_BURNT_TOTAL                   | `FIELD_CALORIES_TOTAL`                                       | Calories burnt.              |
| POLYMERIZE_CONTINUOUS_HEART_RATE_STATISTICS          | `FIELD_AVG` `FIELD_MAX` `FIELD_MIN`                          | Heart rate.                  |
| POLYMERIZE_CONTINUOUS_BODY_WEIGHT_STATISTICS         | `FIELD_AVG` `FIELD_MAX` `FIELD_MIN`                          | Weight.                      |
| DT_STATISTICS_SLEEP                                  | `FALL_ASLEEP_TIME` `WAKEUP_TIME` `LIGHT_SLEEP_TIME` `DEEP_SLEEP_TIME` `DREAM_TIME` `AWAKE_TIME` `ALL_SLEEP_TIME` `WAKE_UP_CNT` `DEEP_SLEEP_PART` `SLEEP_SCORE` `SLEEP_LATENCY` `GO_BED_TIME` `SLEEP_EFFICIENCY` | Sleep statistics.            |
| DT_CONTINUOUS_STEPS_RATE_STATISTIC                   | `FIELD_AVG` `FIELD_MAX` `FIELD_MIN`                          | Step cadence.                |
| POLYMERIZE_CONTINUOUS_BODY_BLOOD_GLUCOSE_STATISTICS  | `FIELD_AVG` `FIELD_MAX` `FIELD_MIN`  `FIELD_CORRELATION_WITH_MEALTIME` `FIELD_CORRELATION_WITH_SLEEP_STATE` `FIELD_SAMPLE_SOURCE` `FIELD_MEAL` | Blood glucose concentration. |
| POLYMERIZE_CONTINUOUS_BODY_BLOOD_PRESSURE_STATISTICS | `FIELD_SYSTOLIC_PRESSURE_AVG` `FIELD_SYSTOLIC_PRESSURE_MIN` `FIELD_SYSTOLIC_PRESSURE_MAX` `FIELD_DIASTOLIC_PRESSURE_AVG` `FIELD_DIASTOLIC_PRESSURE_MIN` `FIELD_DIASTOLIC_PRESSURE_MAX` `FIELD_BODY_POSTURE` `FIELD_MEASURE_BODY_PART_OF_BLOOD_PRESSURE` | Blood pressure.              |
| POLYMERIZE_CONTINUOUS_SPO2_STATISTICS                | `FIELD_SATURATION_AVG` `FIELD_SATURATION_MIN` `FIELD_SATURATION_MAX` `FIELD_OXYGEN_SUPPLY_FLOW_RATE_AVG` `FIELD_OXYGEN_SUPPLY_FLOW_RATE_MIN` `FIELD_OXYGEN_SUPPLY_FLOW_RATE_MAX` `FIELD_OXYGEN_THERAPY` `FIELD_SPO2_MEASUREMENT_MECHANISM` `FIELD_SPO2_MEASUREMENT_APPROACH ` | Blood oxygen.                |
| POLYMERIZE_CONTINUOUS_SPEED_STATISTICS               | `FIELD_AVG` `FIELD_MAX` `FIELD_MIN`                          | Instantaneous speed.         |
| POLYMERIZE_CONTINUOUS_EXERCISE_INTENSITY_STATISTICS  | `FIELD_INTENSITY` ``FIELD_SPAN`                              | Medium- and high-intensity.  |
| POLYMERIZE_CONTINUOUS_HEIGHT_STATISTICS              | `FIELD_AVG` `FIELD_MAX` `FIELD_MIN`                          | Height.                      |
| DT_INSTANTANEOUS_STRESS_STATISTICS                   | `STRESS_AVG` `STRESS_MAX`  `STRESS_MIN` `STRESS_LAST` `MEASURE_COUNT` | Stress.                      |

#### Fields of the Atomic and Statistical Data Types

You can find detailed information the fields mentioned above. You can use both value of Enums and [CustomFieldValue](#CustomFieldValue) constants.

In the third column, **M** indicates **Mandatory** fields, **O** indicates **Optional** fields.

|Name|type|M/O|Unit|Description|
|--|--|--|--|--|
| FIELD_PRECISION | double | M | Meter | - |
| FIELD_ALTITUDE | double | M | Meter | - |
| FIELD_TYPE_OF_ACTIVITY | int | M | Enum | See [ActivityType](#activitytype) |
| FIELD_BPM | float | M | Times | (0, 255) |
| FIELD_SPAN | int | M | Millisecond | - |
| FIELD_DISTANCE | float | M | Meter | - |
| FIELD_DISTANCE_DELTA | float | M | Meter | Value range over 1 second: (0, 100]<br/>Value range over 1 minute: (0, 6000]<br/>Value range over 1 hour: (0, 360000] |
| FIELD_HEIGHT | float | M | Meter | (0.4, 2.6) |
| FIELD_STEPS_DELTA | int | M | Step | Value range over 1 second: (0, 10]Value range over 1 minute: (0, 600]Value range over 1 hour: (0, 36000] |
| FIELD_STEPS | int | M | Step | - |
| FIELD_LATITUDE | double | M | Degree | - |
| FIELD_LONGITUDE | double | M | Degree | - |
| FIELD_BODY_WEIGHT | float | M | kg | (1, 560) |
| FIELD_BMI | float | O | kg/m² | [1.0, 200.0] |
| FIELD_BODY_FAT | float | O | kg | (1, 560) |
| FIELD_BODY_FAT_RATE | float | O | Percentage | (0.0, 100.0] |
| FIELD_MUSCLE_MASS | float | O | kg | (0.1, 150) |
| FIELD_BASAL_METABOLISM | float | O | Kcal/day | (0, 10000) |
| FIELD_MOISTURE | float | O | kg | (0, 500). Accurate to the first decimal place. |
| FIELD_MOISTURE_RATE | float | O | Percentage | (0.0, 100.0]. Accurate to the first decimal place. |
| FIELD_VISCERAL_FAT_LEVEL | float | O | - | [1.0, 59.0]. Accurate to the first decimal place. |
| FIELD_BONE_SALT | float | O | kg | [0.5, 5.0] |
| FIELD_PROTEIN_RATE | float | O | Percentage | (0.0, 100.0]. Accurate to the first decimal place. |
| FIELD_BODY_AGE | int | O | - | [5, 99] |
| FIELD_BODY_SCORE | float | O | Percentage | (0.0, 100.0]. Accurate to the first decimal place. |
| FIELD_SKELETAL_MUSCLEL_MASS | float | O | kg | (1.0, 150) |
| FIELD_IMPEDANCE | float | O | Ohm | (0.1, 100000.0) |
| FIELD_SPEED | float | M | Meter/second | - |
| FIELD_RPM | float | M | Rotations/minute | - |
| FIELD_STEP_RATE | float | M | Steps/minute | - |
| FIELD_CALORIES | double | M | Kcal | Value range over 1 second: (0, 0.555555555555556)<br/>Value range over 1 minute: (0, 33.333333333333333)<br/>Value range over 1 hour: (0, 2000] |
| FIELD_CALORIES_TOTAL | double | M | Kcal |  |
| FIELD_POWER | float | M | Watt | - |
| FIELD_HYDRATE | float | M | Liter | - |
| FIELD_MEAL | int | O | - | - |
| FIELD_FOOD | string | O | - | - |
| FIELD_NUTRIENTS_FACTS | Map<String,  float> | M | Calories/gram | - |
| FIELD_AVG | float | M | - | - |
| FIELD_MAX | float | M | - | - |
| FIELD_MIN | float | M | - | - |
| FIELD_APPEARANCE | int | M | - | The value can only be 1. |
| FIELD_INTENSITY | float | M | Intensity value/minute | The intensity value is related to the duration and activity strength.<br/>- One intensity value is recorded for one minute of a moderate-intensity activity.<br/>- Two intensity values are recorded for one minute of a high-intensity activity. |
| FALL_ASLEEP_TIME | long | M | Millisecond | - |
| WAKE_UP_TIME | long | M | Millisecond | - |
| SLEEP_SCORE | int | O | - | - |
| SLEEP_LATENCY | int | O | Millisecond | - |
| GO_BED_TIME | long | O | Millisecond | - |
| SLEEP_EFFICIENCY | int | O | - | - |
| LIGHT_SLEEP_TIME | int | M | Minute | - |
| DEEP_SLEEP_TIME | int | M | Minute | - |
| DREAM_TIME | int | M | Minute | - |
| AWAKE_TIME | int | M | Minute | - |
| ALL_SLEEP_TIME | int | M | Minute | - |
| WAKE_UP_CNT | int | M | Times | - |
| DEEP_SLEEP_PART | int | M | Times | - |
| SLEEP_STATE | int | M | Enum | The values can be:<br/>1: light sleep<br/>2: REM sleep<br/>3: deep sleep<br/>4: awake<br/>5: nap |
| SCORE | int | M | Score | [1, 99] |
| GRADE | int | M | Enum | The values can be:<br/>1: relaxed<br/>2: normal<br/>3: medium<br/>4: high |
| MEASURE_TYPE | int | O | Enum | The values can be:<br/>1: active<br/>2: passive |
| STRESS_AVG | int | M | Score | - |
| STRESS_MAX | int | M | Score | - |
| STRESS_MIN | int | M | Score | - |
| STRESS_LAST | int | M | Score | - |
| MEASURE_COUNT | int | M | Times | - |
| FIELD_SYSTOLIC_PRESSURE | float | M | mmHg | [40, 300] |
| FIELD_SYSTOLIC_PRESSURE_AVG | float | M | mmHg | - |
| FIELD_SYSTOLIC_PRESSURE_MIN | float | M | mmHg | - |
| FIELD_SYSTOLIC_PRESSURE_MAX | float | M | mmHg | - |
| FIELD_DIASTOLIC_PRESSURE | float | M | mmHg | [30, 200] |
| FIELD_DIASTOLIC_PRESSURE_AVG | float | M | mmHg | - |
| FIELD_DIASTOLIC_PRESSURE_MIN | float | M | mmHg | - |
| FIELD_DIASTOLIC_PRESSURE_MAX | float | M | mmHg | - |
| FIELD_BODY_POSTURE | int | O | Enum | Body posture during blood pressure measurement. The values can be:<br/>1: standing<br/>2: sitting<br/>3: lying<br/>4: semi-reclining |
| FIELD_MEASURE_BODY_PART_OF_BLOOD_PRESSURE | int | O | Enum | Blood pressure measurement position. The values can be:<br/>1: left wrist<br/>2: right wrist<br/>3: left upper arm<br/>4: right upper arm |
| FIELD_LEVEL | float | M | mmol/L | [1.0, 100.0]. It is recommended that the number be accurate to the first decimal place. |
| FIELD_MEASURE_TIME | int | M | Enum | Time for measuring blood glucose. The values can be:<br/>1: before breakfast (on an empty stomach)<br/>2: after breakfast<br/>3: before lunch<br/>4: after lunch<br/>5: before dinner<br/>6: after dinner<br/>7: before bedtime<br/>8: in the wee hours of the morning<br/>9: in random times |
| FIELD_CORRELATION_WITH_MEALTIME | int | O | - | - |
| FIELD_CORRELATION_WITH_SLEEP_STATE | int | O | - | - |
| FIELD_SAMPLE_SOURCE | int | O | Enum | Source from which the blood glucose is measured. The values can be:<br/>1: interstitial fluid<br/>2: capillary blood<br/>3: plasma<br/>4: serum<br/>5: tears<br/>6: whole blood |
| FIELD_SATURATION | float | M | Percentage | - |
| FIELD_SATURATION_AVG | float | M | Percentage | - |
| FIELD_SATURATION_MIN | float | M | Percentage | - |
| FIELD_SATURATION_MAX | float | M | Percentage | - |
| FIELD_OXYGEN_SUPPLY_FLOW_RATE | float | M | L/min | - |
| FIELD_OXYGEN_SUPPLY_FLOW_RATE_AVG | float | M | L/min | - |
| FIELD_OXYGEN_SUPPLY_FLOW_RATE_MIN | float | M | L/min | - |
| FIELD_OXYGEN_SUPPLY_FLOW_RATE_MAX | float | M | L/min | - |
| FIELD_OXYGEN_THERAPY | int | O | Enum | The values can be:<br/>1: oxygen therapy via the nasal cannula |
| FIELD_SPO2_MEASUREMENT_MECHANISM | int | O | Enum | The values can be:<br/>1: oxygen saturation measured in peripheral capillaries |
| FIELD_SPO2_MEASUREMENT_APPROACH | int | O | Enum | The values can be:<br/>1: oxygen saturation measured by the pulse oximetry. |
| FIELD_TEMPERATURE | float | M | Degree Celsius | - |
| FIELD_MEASURE_BODY_PART_OF_TEMPERATURE | int | O | - | - |
| FIELD_TEXTURE | int | M | Enum | The values can be:<br/>1: dry<br/>2: sticky<br/>3: creamy<br/>4: watery<br/>5: Egg white |
| FIELD_AMOUNT | int | M | Enum | The values can be:<br/>1: light<br/>2: medium<br/>3: heavy |
| FIELD_POSITION | int | O | Enum | The values can be:<br/>1: low<br/>2: medium<br/>3: high |
| FIELD_DILATION_STATUS | int | O | Enum | The values can be:<br/>1: closed<br/>2: partially opened<br/>3: fully opened |
| FIELD_FIRMNESS_LEVEL | int | O | Enum | The values can be:<br/>1: soft<br/>2: medium<br/>3: firm |
| FIELD_VOLUME | int | M | Enum | The values can be:<br/>1: Very light (spotting)<br/>2: light<br/>3: medium<br/>4: heavy |
| FIELD_DETECTION_RESULT | int | M | Enum | The values can be:<br/>1: negative<br/>2: positive |




### Data Types

#### DateMap
| Field     | Type     | Description                                                  |
| --------- | -------- | ------------------------------------------------------------ |
| startTime | string   | Start time of the interval. The format of the time is `YYYY-MM-DD hh:mm:ss` **Mandatory** |
| endTime   | string   | End time of the interval. The format of the time is `YYYY-MM-DD hh:mm:ss` **Mandatory** |
| timeUnit  | [TimeUnit](#timeunit) | Selected time unit for time instance. For date strings, use `MILLISECONDS`. |

#### DataCollector
| Field             | Type             | Description                                                  |
| ----------------- | ---------------- | ------------------------------------------------------------ |
| dataType          | [DataType](#datatype)         | DataType of the data collector. **Mandatory**                |
| dataGenerateType  | [DataCollectorConstants](#DataCollectorConstants) | The type of the data collector, such as `DATA_TYPE_RAW` or `DATA_TYPE_DERIVED`. |
| dataCollectorName | string           | Name of the data collector                                   |
| dataStreamName    | string           | Name of the data stream.                                     |
| deviceId          | string           | Id of the device.                                            |
| isLocalized       | boolean          | Whether the data collector is originated from the local device. The default value is false (non-local device). |
#### GroupOptions

| Field          | Type        | Description                                                  |
| -------------- | ----------- | ------------------------------------------------------------ |
| groupByTime    | [GroupByTime](#groupbytime) | Time interval that the result will be grouped. **Mandatory** |
| inputDataType  | [DataType](#datatype) | Input data type.                                             |
| outputDataType | [DataType](#datatype) | Output data type that will be. **Mandatory** if inputDataType was written. |

#### GroupByTime

| Field    | Type     | Description                                                  |
| -------- | -------- | ------------------------------------------------------------ |
| duration | number   | Duration of the grouped data. For instance, `{duration: 3, timeUnit: HmsDataController.HOURS}` indicates that the data groups will be 3 hours long. **Mandatory** |
| timeUnit | [TimeUnit](#timeunit) | Time unit of the duration.                                   |



#### ActivityRecord

| Field            | Type            | Description                                                  |
| ---------------- | --------------- | ------------------------------------------------------------ |
| activityRecordId | string          | Unique ActivityRecord id. **Mandatory** |
| name             | string          | The name of the activity record.                             |
| description      | string          | The description of the activity record.                      |
| activityType     | [ActivityType](#activitytype)    | The activity type associated with the activity record **Mandatory** |
| startTime        | string          | Start time of the activity formatted `YYYY-MM-DD hh:mm:ss` **Mandatory** |
| endTime          | string          | End time of the activity formatted `YYYY-MM-DD hh:mm:ss` It's **Mandatory** when adding an activity record. |
| timeUnit         | [TimeUnit](#timeunit)        | Time unit preference. For date strings `MILLISECONDS` should be set. |
| activitySummary  | [ActivitySummary](#activitysummary) | ActivitySummary based on the passed record.                  |

#### SamplePoint

| Field        | Type     | Description                                                  |
| ------------ | -------- | ------------------------------------------------------------ |
| startTime    | string   | Start time of the data for continuous activities. It's only **Mandatory** for **continuous** data types (such as **DT_CONTINUOUS_STEPS_DELTA**). |
| endTime      | string   | End time of the data for continuous activities. It's only **Mandatory** for **continuous** data types (such as **DT_CONTINUOUS_STEPS_DELTA**). |
| samplingTime | string   | Sampling time of the data for instantaneous activities. It's only **Mandatory** for **instantaneous** data types (such as **DT_INSTANTANEOUS_BODY_WEIGHT**). |
| fields       | [Field](#field)[]  | Fields that the sample point contains. **Mandatory**         |
| timeUnit     | [TimeUnit](#timeunit) | Selected time unit for time instance. For date strings, use `MILLISECONDS`, or null. |

#### Field
| Field        | Type     | Description                                                  |
| ------------ | -------- | ------------------------------------------------------------ |
|fieldName|[FieldConstant](#fieldconstant)|Name of the field.|
|fieldValue|dynamic|Value of the field. Type of the value depends on the type of the field.|
#### ActivitySummary

| Field       | Type        | Description                          |
| ----------- | ----------- | ------------------------------------ |
| paceSummary | [PaceSummary](#pacesummary) | Pace summary of the activity record. |
| dataSummary | [DataSummary](#datasummary) | Data summary of the activity record. |

#### PaceSummary

| Field              | Type   | Description                                                  |
| ------------------ | ------ | ------------------------------------------------------------ |
| avgPace            | number | The average pace.                                            |
| bestPace           | number | The optimal pace.                                            |
| paceMap            | object | The pace per kilometer. It takes an object which takes string for key and number for the value. |
| britishPaceMap     | object | The pace per mile. It takes an object which takes string for key and number for the value. |
| partTimeMap        | object | The segment data table in the metric system. It takes an object which takes string for key and number for the value. |
| britishPartTimeMap | object | The segment data table in the imperial system. It takes an object which takes string for key and number for the value. |
| sportHealthPaceMap | object | The Health pace records. It takes an object which takes string for key and number for the value. |

#### DataSummary

| Field         | Type          | Description                                     |
| ------------- | ------------- | ----------------------------------------------- |
| dataCollector | [DataCollector](#datacollector) | Data collector for sample points. **Mandatory** |
| samplePoints  | [SamplePoint](#samplepoint)[] | Statistical data point list. **Mandatory**      |

#### NotificationOptions

| Field       | Type    | Description                                                  |
| ----------- | ------- | ------------------------------------------------------------ |
| title       | string  | Title of the notification.                                   |
| text        | string  | Text of the notification.                                    |
| subText     | string  | Sub text of the notification.                                |
| ticker      | string  | Ticker text of the notification                              |
| chronometer | boolean | If true, there will be a chronometer on the notification.    |
| largeIcon   | string  | A large icon for notification. The string should be file name (like `heart.png`) and  the picture should be in `android/app/src/main/assets` folder. |



#### SuccessObject

| Field     | Type    | Description                                    |
| --------- | ------- | ---------------------------------------------- |
| isSuccess | boolean | Indicates whether the operation is successful. |

#### ActivityRecordResponse

| Field           | Type    | Description                                    |
| --------------- | ------- | ---------------------------------------------- |
| activityType | string | The activity type (if set) associated with the activity record. |
| appDetailsUrl | string | The detailed URL of the app related to the activity record |
| appDomainName | string | The domain name related to the activity record. |
| appVersion | string | The version of the app that has added the activity record. |
| description | string | The description of the activity record. |
| id | string | The identifier of the activity record. |
| startTime | string | The start time of the activity record. |
| endTime | string | The end time of the activity record. |
| durationTime | string | Duration of the activity. |
| name | string | The name of the activity record. |
| packageName | string | The package name of the app that has added the activity record. |
| hasDurationTime | boolean | Indicates whether the activity record has durations. |
| isKeepGoing | boolean | Indicates whether an activity record is in progress. |
| timeZone | string | The time zone. |
| activitySummary | [ActivitySummary](#activitysummary) | Activity summary of the record. |

#### ScopeResponse

| Field       | Type   | Description                                                  |
| ----------- | ------ | ------------------------------------------------------------ |
| url2Desc    | object | An object that has scope url string as key, scope description as value. |
| appIconPath | string | Path to the app icon image.                                  |
| appName     | string | App name                                                     |
| authTime    | string | The time when the permission is granted.                     |

#### ReadDataResponse

| Field      | Type                | Description                       |
| ---------- | ------------------- | --------------------------------- |
| groups     | [GroupResponse](#groupresponse)[]     | Group object for grouped samples. |
| sampleSet | [SampleSetResponse](#samplesetresponse)[] | SampleSet object.                 |

#### HuaweiIdResponse

| Field      | Type                | Description                       |
| ---------- | ------------------- | --------------------------------- |
| accessToken | string | Access token from HUAWEI ID information. |
| authorizationCode | string | Authorization code granted by the HUAWEI Account Kit server. |
| avatarUriString | string | Profile picture URI from HUAWEI ID information. |
| displayName | string | Nickname from HUAWEI ID information. |
| email | string | Email address from HUAWEI ID information. |
| familyName | string | Family name from HUAWEI ID information. |
| givenName | string | Given name from HUAWEI ID information. |
| idToken | string | idToken from HUAWEI ID information. |
| openId | string | The value differs for the same user in different apps. The value is unique in a single app. |
| unionId | string | UnionId from HUAWEI ID information. |
| authorizedScopes | string[] | Authorized scopes from HUAWEI ID information. |

#### GroupResponse
| Field      | Type                | Description                       |
| ---------- | ------------------- | --------------------------------- |
|startTime|string|Start time formatted as `YYYY-MM-DD hh:mm:ss`|
|endTime|string|End time formatted as `YYYY-MM-DD hh:mm:ss`|
|groupType|number|The group type.|
|sampleSets|[SampleSetResponse](#samplesetresponse)[]|The list of sampling datasets within the grouping time duration.|
|hasMoreSample|boolean|Whether the server has more data.|
|activityType|number|ActivityType id.|
|activity|string|Activity string.|
|activityRecord|[ActivityRecordResponse](#activityrecordresponse)|The activity record if data is grouped by activity record.|

#### SampleSetResponse

| Field         | Type                  | Description                           |
| ------------- | --------------------- | ------------------------------------- |
| dataCollector | [DataCollectorResponse](#datacollectorresponse) | Data collector of the sample set.     |
| isEmpty       | boolean               | Whether sample set has sample points. |
| samplePoints  | [SamplePointResponse](#samplepointresponse)[] | List of Sample points.                |

#### SamplePointResponse

| Field         | Type                  | Description                                                  |
| ------------- | --------------------- | ------------------------------------------------------------ |
| startTime     | string                | Start time formatted as `YYYY-MM-DD hh:mm:ss`                |
| endTime       | string                | End time formatted as `YYYY-MM-DD hh:mm:ss`                  |
| samplingTime  | string                | Sampling time formatted as `YYYY-MM-DD hh:mm:ss`             |
| dataCollector | [DataCollectorResponse](#datacollectorresponse) | Data collector of sampling point.                            |
| fieldValues   | object                | fieldValues object gets a **string** type as key and the type of the value depends on data type. |

#### DataTypeResponse

| Field  | Type            | Description              |
| ------ | --------------- | ------------------------ |
| name   | string          | Name of the data type    |
| fields | [FieldResponse](#fieldresponse)[] | Fields of the data type. |

#### FieldResponse

| Field      | Type    | Description                                                  |
| ---------- | ------- | ------------------------------------------------------------ |
| name       | string  | Field name.                                                  |
| format     | number  | Format of the field value. `INT32 = 1`, `FLOAT/DOUBLE = 2`, `STRING = 3`, `MAP = 4`, `LONG = 5` |
| isOptional | boolean | Whether the field value is optional for the data type.       |

#### DataCollectorResponse

| Field             | Type             | Description                                                  |
| ----------------- | ---------------- | ------------------------------------------------------------ |
| dataType          | [DataTypeResponse](#datatyperesponse) | DataType of the data collector.                              |
| dataGenerateType  | number | The type of the data collector, such as raw and derived. `DATA_TYPE_RAW = 0`, `DATA_TYPE_DERIVED = 1`, `DATA_TYPE_CLEAN = 2`, `DATA_TYPE_CONVERTED = 3`, `DATA_TYPE_MERGED = 4`, `DATA_TYPE_POLYMERIZED = 5`, |
| dataCollectorName | string           | Name of the data collector                                   |
| dataStreamName    | string           | Name of the data stream.                                     |
| deviceId          | string           | Id of the device.                                            |
| isLocalized       | boolean          | Whether the data collector is originated from the local device. The default value is false (non-local device). |
| deviceInfo        | object           | Device Info which data collected from.                       |
| dataStreamId      | string           | Id of the data stream.                                       |
| packageName       | string           | Name of the app package.                                     |

### Constants

Constants are directly coming from native side of the SDK. They are exposed as strings and converted to their objects in the native side of the plugin. There can be more constant than in [Health Kit Data Types and Fields Usage](#health-kit-data-types-and-fields-usage) section. In this case, please refer  [Health Kit Data Types and Fields Usage](#health-kit-data-types-and-fields-usage) section for usable data types and fields.

#### DataType

These constants must be called with `Hms...Controller.` prefix. They are shared by all controllers, so they can be reached by every controller except **HmsHealthAccount**.

For Instance,  You can write `HmsAutoRecorderController.DT_CONTINUOUS_STEPS_TOTAL` as well as`HmsDataController.DT_CONTINUOUS_STEPS_TOTAL`.

|Name|Type|
| -- |--|
| DT_UNUSED_DATA_TYPE |  string |
| DT_CONTINUOUS_STEPS_DELTA |  string |
| DT_CONTINUOUS_STEPS_TOTAL |  string |
| DT_CONTINUOUS_STEPS_RATE_STATISTIC |  string |
| DT_INSTANTANEOUS_STEPS_RATE |  string |
| DT_CONTINUOUS_ACTIVITY_SEGMENT |  string |
| DT_CONTINUOUS_CALORIES_CONSUMED |  string |
| DT_CONTINUOUS_CALORIES_BURNT |  string |
| DT_INSTANTANEOUS_CALORIES_BMR |  string |
| DT_INSTANTANEOUS_POWER_SAMPLE |  string |
| DT_INSTANTANEOUS_ACTIVITY_SAMPLE |  string |
| DT_INSTANTANEOUS_ACTIVITY_SAMPLES |  string |
| DT_INSTANTANEOUS_HEART_RATE |  string |
| DT_INSTANTANEOUS_LOCATION_SAMPLE |  string |
| DT_INSTANTANEOUS_LOCATION_TRACE |  string |
| DT_CONTINUOUS_DISTANCE_DELTA |  string |
| DT_CONTINUOUS_DISTANCE_TOTAL |  string |
| DT_CONTINUOUS_CALORIES_BURNT_TOTAL |  string |
| DT_INSTANTANEOUS_SPEED |  string |
| DT_CONTINUOUS_BIKING_WHEEL_ROTATION_TOTAL |  string |
| DT_INSTANTANEOUS_BIKING_WHEEL_ROTATION |  string |
| DT_CONTINUOUS_BIKING_PEDALING_TOTAL |  string |
| DT_INSTANTANEOUS_BIKING_PEDALING_RATE |  string |
| DT_INSTANTANEOUS_HEIGHT |  string |
| DT_INSTANTANEOUS_BODY_WEIGHT |  string |
| DT_INSTANTANEOUS_BODY_FAT_RATE |  string |
| DT_INSTANTANEOUS_NUTRITION_FACTS |  string |
| DT_INSTANTANEOUS_HYDRATE |  string |
| DT_CONTINUOUS_WORKOUT_DURATION |  string |
| DT_CONTINUOUS_EXERCISE_INTENSITY |  string |
| DT_STATISTICS_SLEEP |  string |
| DT_CONTINUOUS_SLEEP |  string |
| DT_INSTANTANEOUS_STRESS |  string |
| DT_INSTANTANEOUS_STRESS_STATISTICS |  string |
| POLYMERIZE_CONTINUOUS_WORKOUT_DURATION |  string |
| POLYMERIZE_CONTINUOUS_ACTIVITY_STATISTICS |  string |
| POLYMERIZE_CONTINUOUS_CALORIES_BMR_STATISTICS |  string |
| POLYMERIZE_STEP_COUNT_DELTA |  string |
| POLYMERIZE_DISTANCE_DELTA |  string |
| POLYMERIZE_CALORIES_CONSUMED |  string |
| POLYMERIZE_CALORIES_EXPENDED |  string |
| POLYMERIZE_CONTINUOUS_EXERCISE_INTENSITY_STATISTICS |  string |
| POLYMERIZE_CONTINUOUS_HEART_RATE_STATISTICS |  string |
| POLYMERIZE_CONTINUOUS_LOCATION_BOUNDARY_RANGE |  string |
| POLYMERIZE_CONTINUOUS_POWER_STATISTICS |  string |
| POLYMERIZE_CONTINUOUS_SPEED_STATISTICS |  string |
| POLYMERIZE_CONTINUOUS_BODY_FAT_RATE_STATISTICS |  string |
| POLYMERIZE_CONTINUOUS_BODY_WEIGHT_STATISTICS |  string |
| POLYMERIZE_CONTINUOUS_HEIGHT_STATISTICS |  string |
| POLYMERIZE_CONTINUOUS_NUTRITION_FACTS_STATISTICS |  string |
| POLYMERIZE_HYDRATION |  string |
| DT_INSTANTANEOUS_BLOOD_PRESSURE |  string |
| DT_INSTANTANEOUS_BLOOD_GLUCOSE |  string |
| DT_INSTANTANEOUS_SPO2 |  string |
| DT_INSTANTANEOUS_BODY_TEMPERATURE |  string |
| DT_INSTANTANEOUS_BODY_TEMPERATURE_REST |  string |
| DT_INSTANTANEOUS_CERVICAL_MUCUS |  string |
| DT_INSTANTANEOUS_CERVICAL_STATUS |  string |
| DT_CONTINUOUS_MENSTRUAL_FLOW |  string |
| DT_INSTANTANEOUS_OVULATION_DETECTION |  string |
| DT_INSTANTANEOUS_VAGINAL_SPECKLE |  string |
| POLYMERIZE_CONTINUOUS_BODY_BLOOD_PRESSURE_STATISTICS |  string |
| POLYMERIZE_CONTINUOUS_BODY_BLOOD_GLUCOSE_STATISTICS |  string |
| POLYMERIZE_CONTINUOUS_SPO2_STATISTICS |  string |
| POLYMERIZE_CONTINUOUS_BODY_TEMPERATURE_STATISTICS |  string |
| POLYMERIZE_CONTINUOUS_BODY_TEMPERATURE_REST_STATISTICS |  string |
| POLYMERIZE_INSTANTANEOUS_CERVICAL_MUCUS |  string |
| POLYMERIZE_INSTANTANEOUS_CERVICAL_STATUS |  string |
| POLYMERIZE_CONTINUOUS_MENSTRUAL_FLOW |  string |
| POLYMERIZE_INSTANTANEOUS_OVULATION_DETECTION |  string |
| POLYMERIZE_INSTANTANEOUS_VAGINAL_SPECKLE |  string |
| TYPE_TREADMILL_DATA |  string |
| TYPE_CROSS_TRAINER_DATA |  string |
| TYPE_TRAINING_STATUS_DATA |  string |
| TYPE_SUPPORT_SPEED_RANGE |  string |
| TYPE_SUPPORT_INCLINATION_RANGE |  string |
| TYPE_SUPPORT_LEVEL_RANGE |  string |
| TYPE_SUPPORT_HEART_RANGE |  string |
| TYPE_SUPPORT_POWER_RANGE |  string |
| TYPE_FITNESS_MACHINE_FEATURE |  string |
| TYPE_FITNESS_MACHINE_STATUS |  string |
| TYPE_FITNESS_MACHINE_CONTROL_INDICATION |  string |
| TYPE_FITNESS_MACHINE_CONTROL |  string |
| TYPE_FITNESS_EXTENSION_DATA |  string |
| TYPE_CUSTOM_BLE_COMMAND |  string |
| TYPE_FITNESS_MACHINE_PROFILE |  string |
| TYPE_MANUFACTURER_NAME |  string |
| TYPE_MODEL_NUMBER |  string |
| TYPE_SERIAL_NUMBER |  string |
| TYPE_HARDWARE_REVISION |  string |
| TYPE_FIRMWARE_REVISION |  string |
| TYPE_SOFTWARE_REVISION |  string |
| TYPE_SYSTEM_ID |  string |

#### FieldConstant

These constants must be called with `Hms...Controller.` prefix. They are shared by all controllers, so they can be reached by every controller except **HmsHealthAccount**.

For Instance,  You can write `HmsAutoRecorderController.FIELD_PRECISION` as well as`HmsDataController.FIELD_PRECISION`.

|Name|type|
|--|--|
| FIELD_PRECISION | string |
| FIELD_ALTITUDE | string |
| FIELD_TYPE_OF_ACTIVITY | string |
| FIELD_POSSIBILITY_OF_ACTIVITY | string |
| FIELD_BPM | string |
| FIELD_POSSIBILITY | string |
| FIELD_SPAN | string |
| FIELD_DISTANCE | string |
| FIELD_DISTANCE_DELTA | string |
| FIELD_HEIGHT | string |
| FIELD_STEPS_DELTA | string |
| FIELD_STEPS | string |
| FIELD_STEP_LENGTH | string |
| FIELD_LATITUDE | string |
| FIELD_LONGITUDE | string |
| FIELD_BODY_WEIGHT | string |
| FIELD_BMI | string |
| FIELD_BODY_FAT | string |
| FIELD_BODY_FAT_RATE | string |
| FIELD_MUSCLE_MASS | string |
| FIELD_BASAL_METABOLISM | string |
| FIELD_MOISTURE | string |
| FIELD_MOISTURE_RATE | string |
| FIELD_VISCERAL_FAT_LEVEL | string |
| FIELD_BONE_SALT | string |
| FIELD_PROTEIN_RATE | string |
| FIELD_BODY_AGE | string |
| FIELD_BODY_SCORE | string |
| FIELD_SKELETAL_MUSCLEL_MASS | string |
| FIELD_IMPEDANCE | string |
| FIELD_CIRCUMFERENCE | string |
| FIELD_SPEED | string |
| FIELD_RPM | string |
| FIELD_STEP_RATE | string |
| FIELD_ROTATION | string |
| FIELD_CALORIES | string |
| FIELD_CALORIES_TOTAL | string |
| FIELD_POWER | string |
| FIELD_HYDRATE | string |
| FIELD_MEAL | string |
| FIELD_FOOD | string |
| FIELD_NUTRIENTS | string |
| FIELD_NUTRIENTS_FACTS | string |
| FIELD_FRAGMENTS | string |
| FIELD_AVG | string |
| FIELD_MAX | string |
| FIELD_MIN | string |
| FIELD_MIN_LATITUDE | string |
| FIELD_MIN_LONGITUDE | string |
| FIELD_MAX_LATITUDE | string |
| FIELD_MAX_LONGITUDE | string |
| FIELD_APPEARANCE | string |
| FIELD_INTENSITY | string |
| FALL_ASLEEP_TIME | string |
| WAKE_UP_TIME | string |
| SLEEP_SCORE | string |
| SLEEP_LATENCY | string |
| GO_BED_TIME | string |
| SLEEP_EFFICIENCY | string |
| LIGHT_SLEEP_TIME | string |
| DEEP_SLEEP_TIME | string |
| DREAM_TIME | string |
| AWAKE_TIME | string |
| ALL_SLEEP_TIME | string |
| WAKE_UP_CNT | string |
| DEEP_SLEEP_PART | string |
| SLEEP_STATE | string |
| SCORE | string |
| GRADE | string |
| MEASURE_TYPE | string |
| STRESS_AVG | string |
| STRESS_MAX | string |
| STRESS_MIN | string |
| STRESS_LAST | string |
| MEASURE_COUNT | string |
| FIELD_SYSTOLIC_PRESSURE | string |
| FIELD_SYSTOLIC_PRESSURE_AVG | string |
| FIELD_SYSTOLIC_PRESSURE_MIN | string |
| FIELD_SYSTOLIC_PRESSURE_MAX | string |
| FIELD_DIASTOLIC_PRESSURE | string |
| FIELD_DIASTOLIC_PRESSURE_AVG | string |
| FIELD_DIASTOLIC_PRESSURE_MIN | string |
| FIELD_DIASTOLIC_PRESSURE_MAX | string |
| FIELD_BODY_POSTURE | string |
| FIELD_MEASURE_BODY_PART_OF_BLOOD_PRESSURE | string |
| FIELD_LEVEL | string |
| FIELD_MEASURE_TIME | string |
| FIELD_CORRELATION_WITH_MEALTIME | string |
| FIELD_CORRELATION_WITH_SLEEP_STATE | string |
| FIELD_SAMPLE_SOURCE | string |
| FIELD_SATURATION | string |
| FIELD_SATURATION_AVG | string |
| FIELD_SATURATION_MIN | string |
| FIELD_SATURATION_MAX | string |
| FIELD_OXYGEN_SUPPLY_FLOW_RATE | string |
| FIELD_OXYGEN_SUPPLY_FLOW_RATE_AVG | string |
| FIELD_OXYGEN_SUPPLY_FLOW_RATE_MIN | string |
| FIELD_OXYGEN_SUPPLY_FLOW_RATE_MAX | string |
| FIELD_OXYGEN_THERAPY | string |
| FIELD_SPO2_MEASUREMENT_MECHANISM | string |
| FIELD_SPO2_MEASUREMENT_APPROACH | string |
| FIELD_TEMPERATURE | string |
| FIELD_MEASURE_BODY_PART_OF_TEMPERATURE | string |
| FIELD_TEXTURE | string |
| FIELD_AMOUNT | string |
| FIELD_POSITION | string |
| FIELD_DILATION_STATUS | string |
| FIELD_FIRMNESS_LEVEL | string |
| FIELD_VOLUME | string |
| FIELD_DETECTION_RESULT | string |
| FIELD_TREADMILL_INSTANTANEOUS_SPEED | string |
| FIELD_TREADMILL_AVERAGE_SPEED | string |
| FIELD_TREADMILL_TOTAL_DISTANCE | string |
| FIELD_TREADMILL_INCLINATION | string |
| FIELD_TREADMILL_RAMP_ANGLE_SETTING | string |
| FIELD_TREADMILL_POSITIVE_ELEVATION_GAIN | string |
| FIELD_TREADMILL_NEGATIVE_ELEVATION_GAIN | string |
| FIELD_TREADMILL_INSTANTANEOUS_PACE | string |
| FIELD_TREADMILL_AVERAGE_PACE | string |
| FIELD_TREADMILL_TOTAL_ENERGY | string |
| FIELD_TREADMILL_ENERGY_PER_HOUR | string |
| FIELD_TREADMILL_ENERGY_PER_MINUTE | string |
| FIELD_TREADMILL_METABOLIC_EQUIVALENT | string |
| FIELD_TREADMILL_HEART_RATE | string |
| FIELD_TREADMILL_ELAPSED_TIME | string |
| FIELD_TREADMILL_REMAINING_TIME | string |
| FIELD_TREADMILL_FORCE_ON_BELT | string |
| FIELD_TREADMILL_POWER_OUTPUT | string |
| FIELD_TRAINING_STATUS | string |
| FIELD_TRAINING_STATUS_STRING | string |
| FIELD_SUPPORTED_MINIMUM_SPEED | string |
| FIELD_SUPPORTED_MAXIMUM_SPEED | string |
| FIELD_SUPPORTED_MINIMUM_INCREMENT | string |
| FIELD_SUPPORTED_INCLINATION_MIN_INCLINATION | string |
| FIELD_SUPPORTED_INCLINATION_MAX_INCLINATION | string |
| FIELD_SUPPORTED_INCLINATION_MIN_INCREMENT | string |
| FIELD_SUPPORTED_LEVEL_MIN_RESISTANCE_LEVEL | string |
| FIELD_SUPPORTED_LEVEL_MAX_RESISTANCE_LEVEL | string |
| FIELD_SUPPORTED_LEVEL_MIN_INCREMENT | string |
| FIELD_SUPPORTED_HEART_MIN_HEART_RATE | string |
| FIELD_SUPPORTED_HEART_MAX_HEART_RATE | string |
| FIELD_SUPPORTED_HEART_MIN_INCREMENT | string |
| FIELD_SUPPORTED_POWER_MIN_POWER | string |
| FIELD_SUPPORTED_POWER_MAX_POWER | string |
| FIELD_SUPPORTED_POWER_MIN_INCREMENT | string |
| FIELD_FITNESS_MACHINE_FEATURE | string |
| FIELD_FITNESS_TARGET_SETTING | string |
| FIELD_FITNESS_MACHINE_CONTROL_RESPONSE_OP_CODE | string |
| FIELD_FITNESS_MACHINE_CONTROL_REQUEST_OP_CODE | string |
| FIELD_FITNESS_MACHINE_CONTROL_RESULT_OP_CODE | string |
| FIELD_FITNESS_MACHINE_CONTROL_PARAMETER | string |
| FIELD_CROSS_TRAINER_DATA_INSTANTANEOUS_SPEED | string |
| FIELD_CROSS_TRAINER_DATA_AVERAGE_SPEED | string |
| FIELD_CROSS_TRAINER_DATA_TOTAL_DISTANCE | string |
| FIELD_CROSS_TRAINER_DATA_STEP_PER_MINUTE | string |
| FIELD_CROSS_TRAINER_DATA_AVERAGE_STEP_RATE | string |
| FIELD_CROSS_TRAINER_DATA_STRIDE_COUNT | string |
| FIELD_CROSS_TRAINER_DATA_POSITIVE_ELEVATION_GAIN | string |
| FIELD_CROSS_TRAINER_DATA_NEGATIVE_ELEVATION_GAIN | string |
| FIELD_CROSS_TRAINER_DATA_INCLINATION | string |
| FIELD_CROSS_TRAINER_DATA_RESISTANCE_LEVEL | string |
| FIELD_CROSS_TRAINER_DATA_RAMP_ANGLE_SETTING | string |
| FIELD_CROSS_TRAINER_DATA_INSTANTANEOUS_POWER | string |
| FIELD_CROSS_TRAINER_DATA_AVERAGE_POWER | string |
| FIELD_CROSS_TRAINER_DATA_TOTAL_ENERGY | string |
| FIELD_CROSS_TRAINER_DATA_ENERGY_PER_HOUR | string |
| FIELD_CROSS_TRAINER_DATA_ENERGY_PER_MINUTE | string |
| FIELD_CROSS_TRAINER_DATA_HEART_RATE | string |
| FIELD_CROSS_TRAINER_DATA_METABOLIC_EQUIVALENT | string |
| FIELD_CROSS_TRAINER_DATA_ELAPSED_TIME | string |
| FIELD_CROSS_TRAINER_DATA_REMAINING_TIME | string |
| FIELD_MACHINE_STATUS_OP_CODE | string |
| FIELD_EXTENSION_DATA_UNLOCK_CODE | string |
| FIELD_EXTENSION_DATA_HEART_RATE | string |
| FIELD_EXTENSION_DATA_TOTAL_ENERGY | string |
| FIELD_EXTENSION_DATA_DYNAMIC_ENERGY | string |
| FIELD_EXTENSION_DATA_STEP_COUNT | string |
| FIELD_MACHINE_STATUS_PARAMETER | string |
| FIELD_CHARACTERISTIC_UUID | string |
| FIELD_SERVICES_UUID | string |
| FIELD_CUSTOM_COMMAND_CONTENT | string |
| FIELD_DIS_MANUFACTURER_NAME | string |
| FIELD_DIS_MODEL_NUMBER | string |
| FIELD_DIS_SERIAL_NUMBER | string |
| FIELD_DIS_HARDWARE_REVISION | string |
| FIELD_DIS_FIRMWARE_REVISION | string |
| FIELD_DIS_SOFTWARE_REVISION | string |
| FIELD_DIS_SYSTEM_ID | string |

#### Scope

These constants can be called with prefix `HmsHealthAccount.`

For instance, `HmsHealthAccount.HEALTHKIT_STEP_READ`

|Name|Type|
|--|--|
| HEALTHKIT_HEIGHTWEIGHT_READ | string |
| HEALTHKIT_HEIGHTWEIGHT_WRITE | string |
| HEALTHKIT_HEIGHTWEIGHT_BOTH | string |
| HEALTHKIT_STEP_READ | string |
| HEALTHKIT_STEP_WRITE | string |
| HEALTHKIT_STEP_BOTH | string |
| HEALTHKIT_LOCATION_READ | string |
| HEALTHKIT_LOCATION_WRITE | string |
| HEALTHKIT_LOCATION_BOTH | string |
| HEALTHKIT_HEARTRATE_READ | string |
| HEALTHKIT_HEARTRATE_WRITE | string |
| HEALTHKIT_HEARTRATE_BOTH | string |
| HEALTHKIT_BLOODGLUCOSE_READ | string |
| HEALTHKIT_BLOODGLUCOSE_WRITE | string |
| HEALTHKIT_BLOODGLUCOSE_BOTH | string |
| HEALTHKIT_DISTANCE_READ | string |
| HEALTHKIT_DISTANCE_WRITE | string |
| HEALTHKIT_DISTANCE_BOTH | string |
| HEALTHKIT_SPEED_READ | string |
| HEALTHKIT_SPEED_WRITE | string |
| HEALTHKIT_SPEED_BOTH | string |
| HEALTHKIT_CALORIES_READ | string |
| HEALTHKIT_CALORIES_WRITE | string |
| HEALTHKIT_CALORIES_BOTH | string |
| HEALTHKIT_PULMONARY_READ | string |
| HEALTHKIT_PULMONARY_WRITE | string |
| HEALTHKIT_PULMONARY_BOTH | string |
| HEALTHKIT_STRENGTH_READ | string |
| HEALTHKIT_STRENGTH_WRITE | string |
| HEALTHKIT_STRENGTH_BOTH | string |
| HEALTHKIT_ACTIVITY_READ | string |
| HEALTHKIT_ACTIVITY_WRITE | string |
| HEALTHKIT_ACTIVITY_BOTH | string |
| HEALTHKIT_BODYFAT_READ | string |
| HEALTHKIT_BODYFAT_WRITE | string |
| HEALTHKIT_BODYFAT_BOTH | string |
| HEALTHKIT_SLEEP_READ | string |
| HEALTHKIT_SLEEP_WRITE | string |
| HEALTHKIT_SLEEP_BOTH | string |
| HEALTHKIT_NUTRITION_READ | string |
| HEALTHKIT_NUTRITION_WRITE | string |
| HEALTHKIT_NUTRITION_BOTH | string |
| HEALTHKIT_BLOODPRESSURE_READ | string |
| HEALTHKIT_BLOODPRESSURE_WRITE | string |
| HEALTHKIT_BLOODPRESSURE_BOTH | string |
| HEALTHKIT_OXYGENSTATURATION_READ | string |
| HEALTHKIT_OXYGENSTATURATION_WRITE | string |
| HEALTHKIT_OXYGENSTATURATION_BOTH | string |
| HEALTHKIT_BODYTEMPERATURE_READ | string |
| HEALTHKIT_BODYTEMPERATURE_WRITE | string |
| HEALTHKIT_BODYTEMPERATURE_BOTH | string |
| HEALTHKIT_REPRODUCTIVE_READ | string |
| HEALTHKIT_REPRODUCTIVE_WRITE | string |
| HEALTHKIT_REPRODUCTIVE_BOTH | string |
| HEALTHKIT_ACTIVITY_RECORD_READ | string |
| HEALTHKIT_ACTIVITY_RECORD_WRITE | string |
| HEALTHKIT_ACTIVITY_RECORD_BOTH | string |
| HEALTHKIT_STRESS_READ | string |
| HEALTHKIT_STRESS_WRITE | string |
| HEALTHKIT_STRESS_BOTH | string |

#### TimeUnit

These constants must be called with `Hms...Controller.` prefix. They are shared by all controllers, so they can be reached by every controller except **HmsHealthAccount**.

For Instance,  You can write `HmsAutoRecorderController.MILLISECONDS` as well as`HmsDataController.MILLISECONDS`.

|Name|Type|
|--|--|
| NANOSECONDS | string |
| MICROSECONDS | string |
| MILLISECONDS | string |
| SECONDS | string |
| MINUTES | string |
| HOURS | string |
| DAYS | string |

#### FieldFormat

These constants must be called with `Hms...Controller.` prefix. They are shared by all controllers, so they can be reached by every controller except **HmsHealthAccount**.

For Instance,  You can write `HmsAutoRecorderController.FORMAT_INT32` as well as`HmsDataController.FORMAT_INT32`.

|Name|Type|
|--|--|
| FORMAT_INT32 | string |
| FORMAT_FLOAT | string |
| FORMAT_STRING | string |
| FORMAT_MAP | string |
| FORMAT_LONG | string |
| FORMAT_DOUBLE | string |

#### CustomFieldValue

These constants must be called with `Hms...Controller.` prefix. They are shared by all controllers, so they can be reached by every controller except **HmsHealthAccount**.

For Instance,  You can write `HmsAutoRecorderController.MEAL_BREAKFAST` as well as`HmsDataController.MEAL_BREAKFAST`.

|Name|Type|
|--|--|
| MEAL_UNKNOWN | string |
| MEAL_BREAKFAST | string |
| MEAL_LUNCH | string |
| MEAL_DINNER | string |
| MEAL_SNACK | string |
| TYPE_OF_RESISTANCE_UNKNOWN | string |
| TYPE_OF_RESISTANCE_BARBELL | string |
| TYPE_OF_RESISTANCE_CABLE | string |
| TYPE_OF_RESISTANCE_DUMBBELL | string |
| TYPE_OF_RESISTANCE_KETTLEBELL | string |
| TYPE_OF_RESISTANCE_MACHINE | string |
| TYPE_OF_RESISTANCE_BODY | string |
| NUTRIENTS_FACTS_CALORIES | string |
| NUTRIENTS_FACTS_TOTAL_FAT | string |
| NUTRIENTS_FACTS_SATURATED_FAT | string |
| NUTRIENTS_FACTS_UNSATURATED_FAT | string |
| NUTRIENTS_FACTS_POLYUNSATURATED_FAT | string |
| NUTRIENTS_FACTS_MONOUNSATURATED_FAT | string |
| NUTRIENTS_FACTS_TRANS_FAT | string |
| NUTRIENTS_FACTS_CHOLESTEROL | string |
| NUTRIENTS_FACTS_SODIUM | string |
| NUTRIENTS_FACTS_POTASSIUM | string |
| NUTRIENTS_FACTS_TOTAL_CARBS | string |
| NUTRIENTS_FACTS_DIETARY_FIBER | string |
| NUTRIENTS_FACTS_SUGAR | string |
| NUTRIENTS_FACTS_PROTEIN | string |
| NUTRIENTS_FACTS_VITAMIN_A | string |
| NUTRIENTS_FACTS_VITAMIN_C | string |
| NUTRIENTS_FACTS_CALCIUM | string |
| NUTRIENTS_FACTS_IRON | string |
| BODY_POSTURE_STANDING | string |
| BODY_POSTURE_SITTING | string |
| BODY_POSTURE_LYING_DOWN | string |
| BODY_POSTURE_SEMI_RECUMBENT | string |
| MEASURE_BODY_PART_OF_BLOOD_PRESSURE_LEFT_WRIST | string |
| MEASURE_BODY_PART_OF_BLOOD_PRESSURE_RIGHT_WRIST | string |
| MEASURE_BODY_PART_OF_BLOOD_PRESSURE_LEFT_UPPER_ARM | string |
| MEASURE_BODY_PART_OF_BLOOD_PRESSURE_RIGHT_UPPER_ARM | string |
| MEASURE_TIME_RANDOM_TIME | string |
| MEASURE_TIME_BEFORE_BREAKFAST | string |
| MEASURE_TIME_AFTER_BREAKFAST | string |
| MEASURE_TIME_BEFORE_LUNCH | string |
| MEASURE_TIME_AFTER_LUNCH | string |
| MEASURE_TIME_BEFORE_DINNER | string |
| MEASURE_TIME_AFTER_DINNER | string |
| MEASURE_TIME_BEFORE_SLEEP | string |
| MEASURE_TIME_BEFORE_DAWN | string |
| FIELD_CORRELATION_WITH_MEALTIME_GENERAL | string |
| FIELD_CORRELATION_WITH_MEALTIME_FASTING | string |
| FIELD_CORRELATION_WITH_MEALTIME_BEFORE_MEAL | string |
| FIELD_CORRELATION_WITH_MEALTIME_AFTER_MEAL | string |
| CORRELATION_WITH_SLEEP_STATE_FULLY_AWAKE | string |
| CORRELATION_WITH_SLEEP_STATE_BEFORE_SLEEP | string |
| CORRELATION_WITH_SLEEP_STATE_ON_WAKING | string |
| CORRELATION_WITH_SLEEP_STATE_DURING_SLEEP | string |
| SAMPLE_SOURCE_INTERSTITIAL_FLUID | string |
| SAMPLE_SOURCE_CAPILLARY_BLOOD | string |
| SAMPLE_SOURCE_PLASMA | string |
| SAMPLE_SOURCE_SERUM | string |
| SAMPLE_SOURCE_TEARS | string |
| SAMPLE_SOURCE_WHOLE_BLOOD | string |
| OXYGEN_THERAPY_NASAL_CANULA | string |
| SPO2_MEASUREMENT_MECHANISM_PERIPHERAL_CAPILLARY | string |
| SPO2_MEASUREMENT_APPROACH_PULSE_OXIMETRY | string |
| MEASURE_BODY_PART_OF_TEMPERATURE_AXILLARY | string |
| MEASURE_BODY_PART_OF_TEMPERATURE_FINGER | string |
| MEASURE_BODY_PART_OF_TEMPERATURE_FOREHEAD | string |
| MEASURE_BODY_PART_OF_TEMPERATURE_ORAL | string |
| MEASURE_BODY_PART_OF_TEMPERATURE_RECTAL | string |
| MEASURE_BODY_PART_OF_TEMPERATURE_TEMPORAL_ARTERY | string |
| MEASURE_BODY_PART_OF_TEMPERATURE_TOE | string |
| MEASURE_BODY_PART_OF_TEMPERATURE_TYMPANIC | string |
| MEASURE_BODY_PART_OF_TEMPERATURE_WRIST | string |
| MEASURE_BODY_PART_OF_TEMPERATURE_VAGINAL | string |
| TEXTURE_DRY | string |
| TEXTURE_STICKY | string |
| TEXTURE_CREAMY | string |
| TEXTURE_WATERY | string |
| TEXTURE_EGG_WHITE | string |
| AMOUNT_LIGHT | string |
| AMOUNT_MEDIUM | string |
| AMOUNT_HEAVY | string |
| POSITION_LOW | string |
| POSITION_MEDIUM | string |
| POSITION_HIGH | string |
| DILATION_STATUS_CLOSED | string |
| DILATION_STATUS_MEDIUM | string |
| DILATION_STATUS_OPEN | string |
| FIRMNESS_LEVEL_SOFT | string |
| FIRMNESS_LEVEL_MEDIUM | string |
| FIRMNESS_LEVEL_FIRM | string |
| VOLUME_SPOTTING | string |
| VOLUME_LIGHT | string |
| VOLUME_MEDIUM | string |
| VOLUME_HEAVY | string |
| DETECTION_RESULT_NEGATIVE | string |
| DETECTION_RESULT_POSITIVE | string |
| TYPE_TIME | string |
| TYPE_INTERVALS | string |

#### SleepState

These constants must be called with `Hms...Controller.` prefix. They are shared by all controllers, so they can be reached by every controller except **HmsHealthAccount**.

For Instance,  You can write `HmsAutoRecorderController.SLEEP_DREAM` as well as`HmsDataController.SLEEP_DREAM`.

|Name|Type|
|--|--|
| SLEEP_LIGHT | string |
| SLEEP_DREAM | string |
| SLEEP_DEEP | string |
| SLEEP_AWAKE | string |
| SLEEP_NAP | string |

#### DataCollectorConstants

These constants must be called with `Hms...Controller.` prefix. They are shared by all controllers, so they can be reached by every controller except **HmsHealthAccount**.

For Instance,  You can write `HmsAutoRecorderController.DATA_TYPE_RAW` as well as`HmsDataController.DATA_TYPE_RAW`.

|Name|Type|
|--|--|
| DATA_TYPE_CLEAN | string |
| DATA_TYPE_CONVERTED | string |
| DATA_TYPE_MERGED | string |
| DATA_TYPE_POLYMERIZED | string |
| DATA_TYPE_DERIVED | string |
| DATA_TYPE_INIT | string |
| DATA_TYPE_RAW | string |
| HEALTH_DATA_QUALITY_BLOOD_PRESSURE_ESH2002 | string |
| HEALTH_DATA_QUALITY_BLOOD_PRESSURE_ESH2010 | string |
| HEALTH_DATA_QUALITY_BLOOD_PRESSURE_AAMI | string |
| HEALTH_DATA_QUALITY_BLOOD_PRESSURE_BHS_A_A | string |
| HEALTH_DATA_QUALITY_BLOOD_PRESSURE_BHS_A_B | string |
| HEALTH_DATA_QUALITY_BLOOD_PRESSURE_BHS_B_A | string |
| HEALTH_DATA_QUALITY_BLOOD_PRESSURE_BHS_B_B | string |
| HEALTH_DATA_QUALITY_BLOOD_GLUCOSE_ISO151972003 | string |
| HEALTH_DATA_QUALITY_BLOOD_GLUCOSE_ISO151972013 | string |

#### ActivityType

These constants can be called with prefix `HmsActivityRecordsController.`

For instance, `HmsActivityRecordsController.ARCHERY`

|Name | Type |
|--|--|
| MIME_TYPE_PREFIX | string |
| EXTRA_ACTION_STATUS | string |
| STATUS_ACTION_START | string |
| STATUS_ACTION_END | string |
| AEROBICS | string |
| ARCHERY | string |
| BADMINTON | string |
| BASEBALL | string |
| BASKETBALL | string |
| BIATHLON | string |
| BOXING | string |
| CALISTHENICS | string |
| CIRCUIT_TRAINING | string |
| CRICKET | string |
| CROSSFIT | string |
| CURLING | string |
| CYCLING | string |
| CYCLING_INDOOR | string |
| DANCING | string |
| DIVING | string |
| ELEVATOR | string |
| ELLIPTICAL | string |
| ERGOMETER | string |
| ESCALATOR | string |
| FENCING | string |
| FOOTBALL_AMERICAN | string |
| FOOTBALL_AUSTRALIAN | string |
| FOOTBALL_SOCCER | string |
| FLYING_DISC | string |
| GARDENING | string |
| GOLF | string |
| GYMNASTICS | string |
| HANDBALL | string |
| HIIT | string |
| HIKING | string |
| HOCKEY | string |
| HORSE_RIDING | string |
| HOUSEWORK | string |
| ICE_SKATING | string |
| IN_VEHICLE | string |
| INTERVAL_TRAINING | string |
| JUMPING_ROPE | string |
| KAYAKING | string |
| KETTLEBELL_TRAINING | string |
| KICKBOXING | string |
| KITESURFING | string |
| MARTIAL_ARTS | string |
| MEDITATION | string |
| MIXED_MARTIAL_ARTS | string |
| ON_FOOT | string |
| OTHER | string |
| P90X | string |
| PARAGLIDING | string |
| PILATES | string |
| POLO | string |
| RACQUETBALL | string |
| ROCK_CLIMBING | string |
| ROWING | string |
| ROWING_MACHINE | string |
| RUGBY | string |
| RUNNING | string |
| RUNNING_MACHINE | string |
| SAILING | string |
| SCUBA_DIVING | string |
| SCOOTER_RIDING | string |
| SKATEBOARDING | string |
| SKATING | string |
| SKIING | string |
| SLEDDING | string |
| SLEEP | string |
| SLEEP_LIGHT | string |
| SLEEP_DEEP | string |
| SLEEP_REM | string |
| SLEEP_AWAKE | string |
| SNOWBOARDING | string |
| SNOWMOBILE | string |
| SNOWSHOEING | string |
| SOFTBALL | string |
| SQUASH | string |
| STAIR_CLIMBING | string |
| STAIR_CLIMBING_MACHINE | string |
| STANDUP_PADDLEBOARDING | string |
| STILL | string |
| STRENGTH_TRAINING | string |
| SURFING | string |
| SWIMMING | string |
| SWIMMING_POOL | string |
| SWIMMING_OPEN_WATER | string |
| TABLE_TENNIS | string |
| TEAM_SPORTS | string |
| TENNIS | string |
| TILTING | string |
| UNKNOWN | string |
| VOLLEYBALL | string |
| WAKEBOARDING | string |
| WALKING | string |
| WATER_POLO | string |
| WEIGHTLIFTING | string |
| WHEELCHAIR | string |
| WINDSURFING | string |
| YOGA | string |
| ZUMBA | string |

### HmsHealthAccount

#### Function Summary

| Function                                  | Return Type                                     | Description                                                  |
| ----------------------------------------- | ----------------------------------------------- | ------------------------------------------------------------ |
| [signIn(scopes)](#hmshealthaccountsignin) | Promise\<[HuaweiIdResponse](#huaweiidresponse)> | Lets the user sign in and give the wanted permissions to the app. |

#### Functions

##### HmsHealthAccount.signIn(scopes)

This function lets the user sign in to the app.  It takes scope list that app requires to read and/or write the proper data types.

| Parameter | Type    | Description                                   |
| --------- | ------- | --------------------------------------------- |
| scopes    | [Scope](#Scope)[] | Array of scope accesses that are given to app |

| Return Type                                     | Description                      |
| ----------------------------------------------- | -------------------------------- |
| Promise\<[HuaweiIdResponse](#huaweiidresponse)> | HuaweiId of the authorized user. |

###### Example Call

```jsx
const scopes = [
  // View and save steps in HUAWEI Health Kit.
  HmsHealthAccount.HEALTHKIT_STEP_BOTH,
  // View and save height and weight in HUAWEI Health Kit.
  HmsHealthAccount.HEALTHKIT_HEIGHTWEIGHT_BOTH,
  // View and save the heart rate data in HUAWEI Health Kit.
  HmsHealthAccount.HEALTHKIT_HEARTRATE_BOTH,
  // View and save activity data
  HmsHealthAccount.HEALTHKIT_ACTIVITY_BOTH,
  //View and save workout record data
  HmsHealthAccount.HEALTHKIT_ACTIVITY_RECORD_BOTH,
];

const result = await HmsHealthAccount.signIn(scopes);
```

###### Example Response
```js
{
   "isSuccess":true,
   "body":{
      "unionId":"A2kdxx=ajwk...",
      "openId":"A2kdxx=ajwk...",
      "familyName":"Smith",
      "displayName":"+01 511*****11",
      "authorizationCode":null,
      "authorizedScopes":[
         "https://www.huawei.com/healthkit/heartrate.both",
         "https://www.huawei.com/healthkit/heightweight.both",
         "https://www.huawei.com/healthkit/activity.both",
         "https://www.huawei.com/healthkit/activityrecord.both",
         "openid",
         "https://www.huawei.com/healthkit/step.both",
         "profile",
         "https://www.huawei.com/healthkit/location.both",
         "https://www.huawei.com/healthkit/calories.both",
         "https://www.huawei.com/healthkit/nutrition.both"
      ],
      "idToken":"A2kdxx=ajwk...",
      "givenName":"John",
      "email":null,
      "avatarUriString":"",
      "accessToken":"A2kdxx=ajwk..."
   }
}
```

### HmsDataController

#### Function Summary

| Function                                                     | Return Type                               | Description                               |
| ------------------------------------------------------------ | ----------------------------------------- | ----------------------------------------- |
| [initDataController(dataTypeOptions)](#hmsdatacontrollerinitdatacontroller) | Promise\<[SuccessObject](#successobject)> | Initializes the data controller.          |
| [insert(dataCollector, sampleSet)](#hmsdatacontrollerinsert) | Promise\<[SuccessObject](#successobject)> | Inserts data to Health platform.          |
| [delete(dataCollector, dateMap)](#hmsdatacontrollerdelete)   | Promise\<[SuccessObject](#successobject)> | Deletes data from Health platform.        |
| [update(dataCollector, sampleSet, dateMap)](#hmsdatacontrollerupdate) | Promise\<[SuccessObject](#successobject)> | Updates data in Health platform.          |
| [read(dataCollector, dateMap, groupOptions)](#hmsdatacontrollerread) | Promise\<{isSuccess, body}>               | Reads data from Health platform.          |
| [readTodaySummation(dataTypeObject)](#hmsdatacontrollerreadtodaysummation) | Promise\<{isSuccess, body}>               | Reads the current day's data.             |
| [readDailySummation(dataTypeObject, startTime, endTime)](#hmsdatacontrollerreaddailysummation) | Promise\<{isSuccess, body}>               | Reads data between given dates.           |
| [clearAll()](#hmsdatacontrollerclearall)                     | Promise\<[SuccessObject](#successobject)> | Deletes all data inserted by current app. |


#### Functions

##### HmsDataController.initDataController(dataTypeOptions)

Before doing operations using DataController, you need to initialize with proper access rights. 

| Parameter       | Type                        | Description                                                  |
| --------------- | --------------------------- | ------------------------------------------------------------ |
| dataTypeOptions | Array of {dataType, access} | Array of data type list and read/write rights which will be given to data controller. The access codes are, `HmsDataController.ACCESS_READ: Read Access`, `HmsDataController.ACCESS_WRITE: Write Access` |

| Return Type                               | Description           |
| ----------------------------------------- | --------------------- |
| Promise\<[SuccessObject](#successobject)> | Empty Success object. |

###### Example Call

```jsx
const result = await HmsDataController.initDataController([
  {
    dataType: HmsDataController.DT_CONTINUOUS_STEPS_DELTA,
    hiHealthOptions: HmsDataController.ACCESS_READ,
  },
  {
    dataType: HmsDataController.DT_CONTINUOUS_STEPS_DELTA,
    hiHealthOptions: HmsDataController.ACCESS_WRITE,
  },
  {
    dataType: HmsDataController.DT_INSTANTANEOUS_HEIGHT,
    hiHealthOptions: HmsDataController.ACCESS_READ,
  },
]);
```

###### Example Response
```js
{"isSuccess": true}
```


##### HmsDataController.insert(dataCollector, sampleSet)

Inserts the user's fitness and health data into the Health platform.

| Parameter     | Type          | Description                                      |
| ------------- | ------------- | ------------------------------------------------ |
| dataCollector | [DataCollector](#datacollector) | Specifies the data type and stream for insertion |
| sampleset     | [SamplePoint](#samplepoint)[] | The data set that is inserted                    |

| Return Type                               | Description           |
| ----------------------------------------- | --------------------- |
| Promise\<[SuccessObject](#successobject)> | Empty Success object. |

###### Example Call

```jsx
const dataCollector = {
  dataType: HmsDataController.DT_CONTINUOUS_STEPS_DELTA,
  dataStreamName: "DEMO_APP",
  dataGenerateType: HmsDataController.DATA_TYPE_RAW,
};

const sampleSet = [
  {
    startTime: "2020-12-20 05:00:00",
    endTime: "2020-12-20 06:00:00",
    fields: [
      {
        fieldName: HmsDataController.FIELD_STEPS_DELTA,
        fieldValue: 90,
      },
    ],
    timeUnit: HmsDataController.MILLISECONDS,
  },
];

const result = await HmsDataController.insert(dataCollector, sampleSet);
```

###### Example Response
```js
{"isSuccess": true}
```

##### HmsDataController.delete(dataCollector, dateMap)

Deletes the user's fitness and health data from the Health platform.

| Parameter     | Type          | Description                                                  |
| ------------- | ------------- | ------------------------------------------------------------ |
| dataCollector | [DataCollector](#datacollector) | Specifies the data type and stream for deletion              |
| dateMap       | [DateMap](#datemap)       | The date map that indicates the start and end time for deletion |

| Return Type                               | Description           |
| ----------------------------------------- | --------------------- |
| Promise\<[SuccessObject](#successobject)> | Empty Success object. |

###### Example Call

```jsx
const dataCollector = {
  dataType: HmsDataController.DT_CONTINUOUS_STEPS_DELTA,
  dataStreamName: "DEMO_APP",
  dataGenerateType: HmsDataController.DATA_TYPE_RAW,
};

const dateMap = {
  startTime: "2020-12-20 05:00:00",
  endTime: "2020-12-20 21:00:00",
  timeUnit: HmsDataController.MILLISECONDS,
};

const result = await HmsDataController.delete(dataCollector, dateMap);
```

###### Example Response
```js
{"isSuccess": true}
```

##### HmsDataController.update(dataCollector, sampleSet, dateMap)

Updates the user's fitness and health data in the Health platform.

| Parameter     | Type          | Description                                                  |
| ------------- | ------------- | ------------------------------------------------------------ |
| dataCollector | [DataCollector](#datacollector) | Specifies the data type and stream for update                |
| sampleSet     | [SamplePoint](#samplepoint)[] | The new data that will be inserted                           |
| dateMap       | [DateMap](#datemap)       | The date map that indicates the start and end time for the update |

| Return Type                               | Description           |
| ----------------------------------------- | --------------------- |
| Promise\<[SuccessObject](#successobject)> | Empty Success object. |

###### Example Call

```jsx
const dataCollector = {
  dataType: HmsDataController.DT_CONTINUOUS_STEPS_DELTA,
  dataStreamName: "DEMO_APP",
  dataGenerateType: HmsDataController.DATA_TYPE_RAW,
};

const sampleSet = [
  {
    samplingTime: "2020-12-20 08:00:00",
    fields: [
      {
        fieldName: HmsDataController.FIELD_NUTRIENTS_FACTS,
        fieldValue: {
          [HmsDataController.NUTRIENTS_FACTS_TOTAL_CARBS]: 140.5,
          [HmsDataController.NUTRIENTS_FACTS_PROTEIN]: 10.0,
          [HmsDataController.NUTRIENTS_FACTS_TOTAL_FAT]: 16.6,
        },
      },
      {
        fieldName: HmsDataController.FIELD_MEAL,
        fieldValue: HmsDataController.MEAL_SNACK,
      },
      {
        fieldName: HmsDataController.FIELD_FOOD,
        fieldValue: "snack food",
      },
    ],
  },
];

const dateMap = {
  startTime: "2020-12-20 05:00:00",
  endTime: "2020-12-20 21:00:00",
  timeUnit: HmsDataController.MILLISECONDS,
};

const result = await HmsDataController.update(
  dataCollector,
  sampleSet,
  dateMap
);
```

###### Example Response
```js
{"isSuccess": true}
```

##### HmsDataController.read(dataCollector, dateMap, groupOptions)

Reads the user's fitness and health data in the Health platform.

| Parameter     | Type          | Description                                                  |
| ------------- | ------------- | ------------------------------------------------------------ |
| dataCollector | [DataCollector](#datacollector) | Specifies the data type and stream for read operation        |
| dateMap       | [DateMap](#datemap)       | The date map that indicates the start and end time for read operation |
| groupOptions  | [GroupOptions](#groupoptions)  | This is optional. If it's null, it will give the raw data from the platform. If it's not null, it will group the results by given duration and transform the given data to output data type. |

| Return Type                 | Description                                                  |
| --------------------------- | ------------------------------------------------------------ |
| Promise\<{isSuccess, body}> | Read response. body is [ReadDataResponse](#readdataresponse) |

###### Example Call

```jsx
const dataCollector = {
  dataType: HmsDataController.DT_CONTINUOUS_STEPS_DELTA,
  dataStreamName: "DEMO_APP",
  dataGenerateType: HmsDataController.DATA_TYPE_RAW,
};

const dateMap = {
  startTime: "2020-12-20 05:00:00",
  endTime: "2020-12-20 21:00:00",
  timeUnit: HmsDataController.MILLISECONDS,
};

const result = await HmsDataController.read(dataCollector, dateMap, null);
```

###### Example Response
```js
{
   "isSuccess":true,
   "body":{
      "groups":[],
      "sampleSets":[
         {
            "samplePoints":[
               {
                  "dataCollector":{
                     "isLocalized":false,
                     "deviceInfo":{
                        
                     },
                     "dataType":{
                        "fields":[
                           {
                              "isOptional":false,
                              "format":1,
                              "name":"steps_delta"
                           }
                        ],
                        "name":"com.huawei.continuous.steps.delta"
                     },
                     "deviceId":null,
                     "dataGenerateType":0,
                     "dataStreamName":"HEALTH_DEMO",
                     "dataStreamId":"raw:com.huawei.continuous.steps.delta:com.demo.health:HEALTH_DEMO",
                     "packageName":"com.demo.health",
                     "dataCollectorName":null
                  },
                  "samplingTime":"2020-12-19 21:00:00",
                  "endTime":"2020-12-19 21:00:00",
                  "startTime":"2020-12-19 05:00:00",
                  "fieldValues":{
                     "steps_delta(i)":42
                  }
               }
            ],
            "isEmpty":false,
            "dataCollector":{
               "isLocalized":false,
               "deviceInfo":{
                  
               },
               "dataType":{
                  "fields":[
                     {
                        "isOptional":false,
                        "format":1,
                        "name":"steps_delta"
                     }
                  ],
                  "name":"com.huawei.continuous.steps.delta"
               },
               "deviceId":null,
               "dataGenerateType":0,
               "dataStreamName":"HEALTH_DEMO",
               "dataStreamId":"raw:com.huawei.continuous.steps.delta:com.demo.health:HEALTH_DEMO",
               "packageName":"com.demo.health",
               "dataCollectorName":null
            }
         }
      ]
   }
}
```

##### HmsDataController.readTodaySummation(dataTypeObject)

Reads the current day's activity according to the data type

> The following data types (values of the input parameter DataType) are
> supported for the query of statistical data of the day:
>
> Step count: DT_CONTINUOUS_STEPS_DELTA
>
> Distance: DT_CONTINUOUS_DISTANCE_DELTA
>
> Heart rate: DT_INSTANTANEOUS_HEART_RATE
>
> Weight: DT_INSTANTANEOUS_BODY_WEIGHT
>
> Sleep: DT_CONTINUOUS_SLEEP
>
> Stress: DT_INSTANTANEOUS_STRESS
>
> Calories: DT_CONTINUOUS_CALORIES_BURNT
 
| Parameter      | Type                 | Description |
| -------------- | -------------------- | ----------- |
| dataTypeObject | {dataType} | DataType Object. |

| Return Type                 | Description                                                  |
| --------------------------- | ------------------------------------------------------------ |
| Promise\<{isSuccess, body}> | SampleSet response, body: [SampleSetResponse](#samplesetresponse) |

###### Example Call

```jsx
const dataType = {
  dataType: HmsDataController.DT_CONTINUOUS_STEPS_DELTA,
};

const result = await HmsDataController.readTodaySummation(dataType);
```

###### Example Response
```js
{
  "isSuccess": true,
  "body": {
    "samplePoints": [
      {
        "dataCollector": {
          "isLocalized": false,
          "deviceInfo": {},
          "dataType": {
            "fields": [{ "isOptional": false, "format": 1, "name": "steps" }],
            "name": "com.huawei.continuous.steps.total"
          },
          "deviceId": null,
          "dataGenerateType": 0,
          "dataStreamName": "default",
          "dataStreamId": "raw:com.huawei.continuous.steps.total:com.huawei.health:default",
          "packageName": "com.huawei.health",
          "dataCollectorName": "com.huawei.health"
        },
        "samplingTime": "2020-12-23 23:59:59",
        "endTime": "2020-12-23 23:59:59",
        "startTime": "2020-12-23 00:00:00",
        "fieldValues": { "steps(i)": 112 }
      }
    ],
    "isEmpty": false,
    "dataCollector": {
      "isLocalized": false,
      "deviceInfo": {},
      "dataType": {
        "fields": [{ "isOptional": false, "format": 1, "name": "steps" }],
        "name": "com.huawei.continuous.steps.total"
      },
      "deviceId": null,
      "dataGenerateType": 0,
      "dataStreamName": "default",
      "dataStreamId": "raw:com.huawei.continuous.steps.total:com.huawei.health:default",
      "packageName": "com.huawei.health",
      "dataCollectorName": "com.huawei.health"
    }
  }
}
```

##### HmsDataController.readDailySummation(dataTypeObject, startTime, endTime)

Reads the fitness and health data according to data type between given days.

> The following data types (values of the input parameter DataType) are
> supported for the query of statistical data of multiple days:
>
> Step count: DT_CONTINUOUS_STEPS_DELTA
>
> Distance: DT_CONTINUOUS_DISTANCE_DELTA
>
> Heart rate: DT_INSTANTANEOUS_HEART_RATE
>
> Weight: DT_INSTANTANEOUS_BODY_WEIGHT
>
> Sleep: DT_CONTINUOUS_SLEEP
>
> Stress: DT_INSTANTANEOUS_STRESS
>
> Calories: DT_CONTINUOUS_CALORIES_BURNT

| Parameter      | Type                 | Description                               |
| -------------- | -------------------- | ----------------------------------------- |
| dataTypeObject | {dataType} | Desired data type                         |
| startTime      | number               | The start time in the format of **YYYYMMDD**. |
| endTime        | number               | The end time in the format of **YYYYMMDD**. |

| Return Type                 | Description                                                  |
| --------------------------- | ------------------------------------------------------------ |
| Promise\<{isSuccess, body}> | SampleSet response. Body: [SampleSetResponse](#samplesetresponse) |

###### Example Call

```jsx
const dataType = {
  dataType: HmsDataController.DT_CONTINUOUS_STEPS_DELTA,
};

const startTime = 20201221;
const endTime = 20201222;

const result = await HmsDataController.readDailySummation(
  dataType,
  startTime,
  endTime
);
```

###### Example Response
```js
{
  "isSuccess": true,
  "body": {
    "samplePoints": [
      {
        "dataCollector": {
          "isLocalized": false,
          "deviceInfo": {},
          "dataType": {
            "fields": [{ "isOptional": false, "format": 1, "name": "steps" }],
            "name": "com.huawei.continuous.steps.total"
          },
          "deviceId": null,
          "dataGenerateType": 0,
          "dataStreamName": "default",
          "dataStreamId": "raw:com.huawei.continuous.steps.total:com.huawei.health:default",
          "packageName": "com.huawei.health",
          "dataCollectorName": "com.huawei.health"
        },
        "samplingTime": "2020-12-21 23:59:59",
        "endTime": "2020-12-21 23:59:59",
        "startTime": "2020-12-21 00:00:00",
        "fieldValues": { "steps(i)": 283 }
      },
      {
        "dataCollector": {
          "isLocalized": false,
          "deviceInfo": {},
          "dataType": {
            "fields": [{ "isOptional": false, "format": 1, "name": "steps" }],
            "name": "com.huawei.continuous.steps.total"
          },
          "deviceId": null,
          "dataGenerateType": 0,
          "dataStreamName": "default",
          "dataStreamId": "raw:com.huawei.continuous.steps.total:com.huawei.health:default",
          "packageName": "com.huawei.health",
          "dataCollectorName": "com.huawei.health"
        },
        "samplingTime": "2020-12-22 23:59:59",
        "endTime": "2020-12-22 23:59:59",
        "startTime": "2020-12-22 00:00:00",
        "fieldValues": { "steps(i)": 83 }
      }
    ],
    "isEmpty": false,
    "dataCollector": {
      "isLocalized": false,
      "deviceInfo": {},
      "dataType": {
        "fields": [{ "isOptional": false, "format": 1, "name": "steps" }],
        "name": "com.huawei.continuous.steps.total"
      },
      "deviceId": null,
      "dataGenerateType": 0,
      "dataStreamName": "default",
      "dataStreamId": "raw:com.huawei.continuous.steps.total:com.huawei.health:default",
      "packageName": "com.huawei.health",
      "dataCollectorName": "com.huawei.health"
    }
  }
}
```

##### HmsDataController.clearAll()

Deletes all data inserted by the current app from the device and cloud.

| Return Type                               | Description           |
| ----------------------------------------- | --------------------- |
| Promise\<[SuccessObject](#successobject)> | Empty Success object. |


###### Example Call
```jsx
const  result = await  HmsDataController.clearAll();
```

###### Example Response
```js
{"isSuccess": true}
```

### HmsActivityRecordsController

#### Function Summary

| Function                                                     | Return Type                               | Description                                                  |
| ------------------------------------------------------------ | ----------------------------------------- | ------------------------------------------------------------ |
| [beginActivityRecord(activityRecord)](#hmsactivityrecordscontrollerbeginactivityrecord) | Promise\<[SuccessObject](#successobject)> | Starts a new activity record for the current app.            |
| [endActivityRecord(recordId)](#hmsactivityrecordscontrollerendactivityrecord) | Promise<{isSuccess, body}>                | Stops the ActivityRecord of a specific ID.                   |
| [endAllActivityRecords()](#hmsactivityrecordscontrollerendallactivityrecords) | Promise<{isSuccess, body}>                | Stops all ActivityRecords.                                   |
| [addActivityRecord(activityRecord, dataCollector, sampleSet)](#hmsactivityrecordscontrolleraddactivityrecord) | Promise\<[SuccessObject](#successobject)> | Inserts a specified activity record and corresponding data to the HUAWEI Health platform. |
| [getActivityRecord(dataType, dateMap, activityRecordId, activityRecordName)](#hmsactivityrecordscontrollergetactivityrecord) | Promise\<{isSuccess, body}>               | Reads data from the HUAWEI Health platform by specifying the activity record and type. |
| [deleteActivityRecord(dataType, dateMap, activityRecordId, activityRecordName)](#hmsactivityrecordscontrollerdeleteactivityrecord) | Promise\<{isSuccess, body}>               | Deletes data from the HUAWEI Health platform by specifying the activity record and type. |

#### Functions

##### HmsActivityRecordsController.beginActivityRecord(activityRecord)

Starts a new activity record for the current app.

| Parameter      | Type           | Description                           |
| -------------- | -------------- | ------------------------------------- |
| activityRecord | [ActivityRecord](#activityrecord) | Activity record object to be started. |

| Return Type                               | Description           |
| ----------------------------------------- | --------------------- |
| Promise\<[SuccessObject](#successobject)> | Empty Success object. |

###### Example Call

```jsx
const activityRecord = {
  activityRecordId: "Record007",
  name: "MorningRun",
  description: "This is ActivityRecord begin!",
  activityType: HmsActivityRecordsController.RUNNING,
  startTime: "2020-12-24 09:39:00",
  timeUnit: HmsActivityRecordsController.MILLISECONDS,
  timeZone: "+0100",
};

const result = await HmsActivityRecordsController.beginActivityRecord(
  activityRecord
);
```

###### Example Response
```js
{"isSuccess": true}
```

##### HmsActivityRecordsController.endActivityRecord(recordId)
Stops the ActivityRecord of a specific ID.

| Parameter | Type   | Description                     |
| --------- | ------ | ------------------------------- |
| recordId  | string | Activity record id to be ended. |

| Return Type                 | Description                                                  |
| --------------------------- | ------------------------------------------------------------ |
| Promise\<{isSuccess, body}> | Array of ActivityRecord object, body: [ActivityRecordResponse](#activityrecordresponse)[] |

###### Example Call

```jsx
const activityRecordId = "Record007";

const result = await HmsActivityRecordsController.endActivityRecord(
  activityRecordId
);
```

###### Example Response
```js
{
  "isSuccess": true,
  "body": [
    {
      "activitySummary": {},
      "isKeepGoing": false,
      "hasDurationTime": true,
      "packageName": "com.demo.health",
      "endTime": "2020-12-24 10:06:40",
      "id": "AR:1608793569488",
      "appVersion": "com.demo.health",
      "appDetailsUrl": "com.demo.health",
      "activityType": "running",
      "timeZone": "+0100",
      "name": "AR:1608793569488",
      "durationTime": "00:27:40.112",
      "startTime": "2020-12-24 09:39:00",
      "description": "This is ActivityRecord begin test!: AR:1608793569488",
      "appDomainName": "com.demo.health"
    }
  ]
}
```

##### HmsActivityRecordsController.endAllActivityRecords()
Stops all ActivityRecords.

| Return Type                 | Description                                                  |
| --------------------------- | ------------------------------------------------------------ |
| Promise\<{isSuccess, body}> | Array of ActivityRecord object, body: [ActivityRecordResponse](#activityrecordresponse)[] |

###### Example Call

```jsx
const  result = await  HmsActivityRecordsController.endAllActivityRecords();
```

###### Example Response
```js
{
  "isSuccess": true,
  "body": [
    {
      "activitySummary": {},
      "isKeepGoing": false,
      "hasDurationTime": true,
      "packageName": "com.demo.health",
      "endTime": "2020-12-24 10:09:45",
      "id": "AR:1",
      "appVersion": "com.demo.health",
      "appDetailsUrl": "com.demo.health",
      "activityType": "running",
      "timeZone": "+0300",
      "name": "AR:1",
      "durationTime": "00:39:45.781",
      "startTime": "2020-12-24 09:30:00",
      "description": "This is ActivityRecord begin test!: AR:1",
      "appDomainName": "com.demo.health"
    },
    {
      "activitySummary": {},
      "isKeepGoing": false,
      "hasDurationTime": true,
      "packageName": "com.demo.health",
      "endTime": "2020-12-24 10:09:45",
      "id": "AR:2",
      "appVersion": "com.demo.health",
      "appDetailsUrl": "com.demo.health",
      "activityType": "running",
      "timeZone": "+0300",
      "name": "AR:2",
      "durationTime": "00:24:45.799",
      "startTime": "2020-12-24 09:45:00",
      "description": "This is ActivityRecord begin test!: AR:2",
      "appDomainName": "com.demo.health"
    }
  ]
}

```

##### HmsActivityRecordsController.addActivityRecord(activityRecord, dataCollector, sampleSet)
Inserts a specified activity record and corresponding data to the HUAWEI Health platform.

| Parameter      | Type           | Description                             |
| -------------- | -------------- | --------------------------------------- |
| activityRecord | [ActivityRecord](#activityrecord) | Activity record object to be added.     |
| dataCollector  | [DataCollector](#datacollector)  | Data collector for sample set.          |
| sampleSet      | [SamplePoint](#samplepoint)[]  | Sampleset object to be added to record. |

| Return Type                               | Description           |
| ----------------------------------------- | --------------------- |
| Promise\<[SuccessObject](#successobject)> | Empty Success object. |

###### Example Call

```jsx
const dataCollector = {
  dataType: HmsActivityRecordsController.DT_CONTINUOUS_STEPS_DELTA,
  dataGenerateType: HmsActivityRecordsController.DATA_TYPE_RAW,
  dataCollectorName: "test1",
};

const samplePoint = {
  fields: [
    {
      fieldName: HmsActivityRecordsController.FIELD_STEPS,
      fieldValue: 1024,
    },
  ],
};

const paceSummary = {
  bestPace: 40,
  avgPace: 20,
  paceMap: {
    "1": 10,
    "2": 12,
    "3": 13,
    "4": 16,
  },
  britishPaceMap: {
    "1": 16,
    "2": 20,
    "3": 24,
    "4": 30,
  },
  partTimeMap: {
    "1.0": 35,
  },
  britishPartTimeMap: {
    "1.0": 40,
  },
  sportHealthPaceMap: {
    "102802480": 53,
  },
};

const activitySummmary = {
  paceSummary: paceSummary,
  dataSummary: {
    dataCollector: {
      dataType: HmsActivityRecordsController.DT_CONTINUOUS_STEPS_TOTAL,
      dataGenerateType: HmsActivityRecordsController.DATA_TYPE_RAW,
      dataCollectorName: "test1",
    },
    samplePoints: [samplePoint],
  },
};

const activityRecord = {
  activityRecordId: "RecordId",
  name: "BeginActivityRecord",
  description: "This is ActivityRecord begin test!",
  activityType: HmsActivityRecordsController.RUNNING,
  startTime: "2020-12-19 18:00:00",
  endTime: "2020-12-19 19:00:00",
  timeUnit: HmsActivityRecordsController.MILLISECONDS,
  activitySummary: activitySummmary,
  timeZone: "+0800",
};

const sampleSet = [
  {
    startTime: "2020-12-19 18:45:00",
    endTime: "2020-12-19 18:58:00",
    timeUnit: HmsActivityRecordsController.MILLISECONDS,
    fields: [
      {
        fieldName: HmsActivityRecordsController.FIELD_STEPS_DELTA,
        fieldValue: 1024,
      },
    ],
  },
];

const result = await HmsActivityRecordsController.addActivityRecord(
  activityRecord,
  dataCollector,
  sampleSet
);
```

###### Example Response
```js
{"isSuccess": true}
```

##### HmsActivityRecordsController.getActivityRecord(dataType, dateMap, activityRecordId, activityRecordName)
Reads data from the HUAWEI Health platform by specifying the activity record and type.

| Parameter          | Type                 | Description                                     |
| ------------------ | -------------------- | ----------------------------------------------- |
| dataTypeObject     | {dataType} | Type of data to be gotten.                      |
| dateMap            | [DateMap](#datemap)              | Date objects that contains start and end times. |
| activityRecordId   | string               | Record id to be gotten.                         |
| activityRecordName | string               | Record name to be gotten.                       |

| Return Type                | Description                                                  |
| -------------------------- | ------------------------------------------------------------ |
| Promise\<{isSuccess, body} | Array of ActivityRecord object, body : [ActivityRecordResponse](#activityrecordresponse)[] |

###### Example Call

```jsx
const dataType= {
  dataType: HmsActivityRecordsController.DT_CONTINUOUS_STEPS_DELTA,
},
const dateMap = {
  startTime: "2020-12-19 09:00:00",
  endTime: "2020-12-19 21:00:00",
  timeUnit: HmsActivityRecordsController.MILLISECONDS,
},

const result = await HmsActivityRecordsController.getActivityRecord(
  dataType,
  dateMap,
  "AR:2",
  "BeginActivityRecord",
);
```

###### Example Response
```js
{
  "isSuccess": true,
  "body": [
    {
      "activitySummary": {
        "dataSummary": [
          {
            "dataCollector": {
              "isLocalized": false,
              "deviceInfo": {},
              "dataType": {
                "fields": [
                  { "isOptional": false, "format": 1, "name": "steps" }
                ],
                "name": "com.huawei.continuous.steps.total"
              },
              "deviceId": null,
              "dataGenerateType": 0,
              "dataStreamName": "",
              "dataStreamId": "raw:com.huawei.continuous.steps.total:com.demo.health",
              "packageName": "com.demo.health",
              "dataCollectorName": "test1"
            },
            "samplingTime": "1970-01-01 02:00:00",
            "endTime": "1970-01-01 02:00:00",
            "startTime": "1970-01-01 02:00:00",
            "fieldValues": { "steps(i)": 1024 }
          }
        ],
        "paceSummary": {
          "sportHealthPaceMap": { "102802480": 53 },
          "britishPartTimeMap": { "1.0": 40 },
          "partTimeMap": { "1.0": 35 },
          "britishPaceMap": { "1": 16, "2": 20, "3": 24, "4": 30 },
          "paceMap": { "1": 10, "2": 12, "3": 13, "4": 16 },
          "bestPace": 40,
          "avgPace": 20
        }
      },
      "isKeepGoing": false,
      "hasDurationTime": true,
      "packageName": "com.demo.health",
      "endTime": "2020-12-19 19:00:00",
      "id": "AR:2",
      "appVersion": "com.demo.health",
      "appDetailsUrl": "com.demo.health",
      "activityType": "running",
      "timeZone": "+0800",
      "name": "BeginActivityRecord",
      "durationTime": "00:00:00.000",
      "startTime": "2020-12-19 18:00:00",
      "description": "This is ActivityRecord begin test!",
      "appDomainName": "com.demo.health"
    }
  ]
}
```

##### HmsActivityRecordsController.deleteActivityRecord(dataType, dateMap, activityRecordId, activityRecordName)
Deletes data from the HUAWEI Health platform by specifying the activity record and type.

| Parameter          | Type                 | Description                                     |
| ------------------ | -------------------- | ----------------------------------------------- |
| dataTypeObject     | object | Object of data type to be deleted.           |
| dateMap            | [DateMap](#datemap)              | Date objects that contains start and end times. |
| activityRecordId   | string               | Record id to be deleted.                        |
| activityRecordName | string               | Record name to be deleted.                      |

| Return Type                | Description                                                  |
| -------------------------- | ------------------------------------------------------------ |
| Promise\<{isSuccess, body} | Array of ActivityRecord object, body: [ActivityRecordResponse](#activityrecordresponse)[] |

###### Example Call

```jsx
const dataType= {
  dataType: HmsActivityRecordsController.DT_CONTINUOUS_STEPS_DELTA,
},
const dateMap = {
  startTime: "2020-12-19 09:00:00",
  endTime: "2020-12-19 21:00:00",
  timeUnit: HmsActivityRecordsController.MILLISECONDS,
},

const result = await HmsActivityRecordsController.deleteActivityRecord(
  dataType,
  dateMap,
  null,
  null,
);
```

###### Example Response
```js
{
  "isSuccess": true,
  "body": [
    {
      "activitySummary": {
        "dataSummary": [
          {
            "dataCollector": {
              "isLocalized": false,
              "deviceInfo": {},
              "dataType": {
                "fields": [
                  { "isOptional": false, "format": 1, "name": "steps" }
                ],
                "name": "com.huawei.continuous.steps.total"
              },
              "deviceId": null,
              "dataGenerateType": 0,
              "dataStreamName": "",
              "dataStreamId": "raw:com.huawei.continuous.steps.total:com.demo.health",
              "packageName": "com.demo.health",
              "dataCollectorName": "test1"
            },
            "samplingTime": "1970-01-01 02:00:00",
            "endTime": "1970-01-01 02:00:00",
            "startTime": "1970-01-01 02:00:00",
            "fieldValues": { "steps(i)": 1024 }
          }
        ],
        "paceSummary": {
          "sportHealthPaceMap": { "102802480": 53 },
          "britishPartTimeMap": { "1.0": 40 },
          "partTimeMap": { "1.0": 35 },
          "britishPaceMap": { "1": 16, "2": 20, "3": 24, "4": 30 },
          "paceMap": { "1": 10, "2": 12, "3": 13, "4": 16 },
          "bestPace": 40,
          "avgPace": 20
        }
      },
      "isKeepGoing": false,
      "hasDurationTime": true,
      "packageName": "com.demo.health",
      "endTime": "2020-12-19 19:00:00",
      "id": "AR:2",
      "appVersion": "com.demo.health",
      "appDetailsUrl": "com.demo.health",
      "activityType": "running",
      "timeZone": "+0800",
      "name": "BeginActivityRecord",
      "durationTime": "00:00:00.000",
      "startTime": "2020-12-19 18:00:00",
      "description": "This is ActivityRecord begin test!",
      "appDomainName": "com.demo.health"
    }
  ]
}
```


### HmsAutoRecorderController

#### Function Summary

| Function                                                     | Return Type                               | Description        |
| ------------------------------------------------------------ | ----------------------------------------- | ------------------ |
| [startRecord(dataTypeObject, notificationOptions)](#hmsautorecordercontrollerstartrecord) | Promise\<[SuccessObject](#successobject)> | Starts the record. |
| [stopRecord(dataTypeObject)](#hmsautorecordercontrollerstoprecord) | Promise\<[SuccessObject](#successobject)> | Ends record.       |

#### Functions

##### HmsAutoRecorderController.startRecord(dataTypeObject, notificationOptions)

Starts a automatic recording that records the given data type.

> Currently, only the following data types can be read in real time:
>
> Total number of steps: DT_CONTINUOUS_STEPS_TOTAL

| Parameter           | Type                 | Description                                                  |
| ------------------- | -------------------- | ------------------------------------------------------------ |
| dataType            | object | The data type that will be recorded                          |
| notificationOptions | [NotificationOptions](#notificationoptions)  | Customizes the notification that will be popped when record starts. |

| Return Type                               | Description           |
| ----------------------------------------- | --------------------- |
| Promise\<[SuccessObject](#successobject)> | Empty Success object. |

###### Example Call

```jsx
const dataType = {
  dataType: HmsAutoRecorderController.DT_CONTINUOUS_STEPS_TOTAL,
};

const notificationOptions = {
  title: "AutoRecorderDemo",
  text: "It's running",
  subText: "Oh is this subtext?",
  ticker: "Ticker text",
  chronometer: false,
  largeIcon: "hearth.png",
};

const result = await HmsAutoRecorderController.startRecord(
  dataType,
  notificationOptions
);
```
###### Example Response
```jsx
{"isSuccess": true}
```

###### Example Usage

This function will send recording results regularly until end of record. In order to get the event you must initialize an **event emitter**. You can find below an example usage for autoRecorderController.

```jsx
import React from "react";
import { Text, TouchableOpacity, NativeEventEmitter } from "react-native";

import { HmsAutoRecorderController } from "@hmscore/react-native-hms-health";

export default class AutoRecorderController extends React.Component {
  componentDidMount() {
    const eventEmitter = new NativeEventEmitter(HmsAutoRecorderController);

    this.eventListener = eventEmitter.addListener(
      HmsAutoRecorderController.AUTO_RECORDER_POINT_LISTENER,
      (event) => {
        console.log(event);
      }
    );

    this.eventListener = eventEmitter;
  }

  componentWillUnmount() {
    try {
      this.eventListener.remove();
    } catch (error) {
      return;
    }
  }

  async startRecord() {
    try {
      const dataType = {
        dataType: HmsAutoRecorderController.DT_CONTINUOUS_STEPS_TOTAL,
      };

      const result = await HmsAutoRecorderController.startRecord(
        dataType,
        null
      );
      console.log("startRecord: ", result);
    } catch (error) {
      console.log(error);
    }
  }

  render() {
    return (
      <TouchableOpacity onPress={() => this.startRecord()}>
        <Text>{"startRecord"}</Text>
      </TouchableOpacity>
    );
  }
}
```



##### HmsAutoRecorderController.stopRecord(dataTypeObject)

Stops the recording that is currently active.

| Parameter | Type                 | Description                           |
| --------- | -------------------- | ------------------------------------- |
| dataType  | object | The data type that is being recorded. |

| Return Type                               | Description           |
| ----------------------------------------- | --------------------- |
| Promise\<[SuccessObject](#successobject)> | Empty Success object. |

###### Example Call

```jsx
const dataType = {
  dataType: HmsAutoRecorderController.DT_CONTINUOUS_STEPS_TOTAL,
};

const result = await HmsAutoRecorderController.stopRecord(dataType);
```
###### Example Response
```jsx
{"isSuccess": true}
```


### HmsSettingController

#### Function Summary
| Function                                                     | Return Type                               | Description                                                  |
| ------------------------------------------------------------ | ----------------------------------------- | ------------------------------------------------------------ |
| [addNewDataType(dataTypeString,  fields)](#hmssettingcontrolleraddnewdatatype) | Promise\<{isSuccess, body}>               | A unique name that starts with app's package name.           |
| [readDataType(dataTypeString)](#hmssettingcontrollerreaddatatype) | Promise\<{isSuccess, body}>               | Gives DataType object from given data type string.           |
| [disableHiHealth()](#hmssettingcontrollerdisablehihealth)    | Promise\<[SuccessObject](#successobject)> | Disables Health Kit                                          |
| [getHealthAppAuthorization()](#hmssettingcontrollergethealthappauthorization) | Promise\<{isSuccess, body}>               | Whether the user privacy authorization has been granted to Health Kit. `true: Authorized`, `false: Unauthorized` |
| [checkHealthAppAuthorization()](#hmssettingcontrollercheckhealthappauthorization) | Promise\<[SuccessObject](#successobject)> | If user not authorized, the user will be directed sign in page. |
| [enableLogger()](#hmssettingcontrollerenablelogger)          | Promise\<[SuccessObject](#successobject)> | Enables HMSLogger.                                           |
| [disableLogger()](#hmssettingcontrollerdisablelogger)        | Promise\<[SuccessObject](#successobject)> | Disables HMSLogger.                                          |

#### Functions

##### HmsSettingController.addNewDataType(dataTypeString,  fields)
Creates and adds a customized data type. The name of the created data type must be prefixed with the package name of the app. Otherwise, the creation fails.

| Parameter      | Type   | Description                                      |
| -------------- | ------ | ------------------------------------------------ |
| dataTypeString | string | The data type that is being added                |
| fields          | [FieldConstant](#fieldconstant)[] | The field value that the data type will require. |

| Return Type                 | Description                                                  |
| --------------------------- | ------------------------------------------------------------ |
| Promise\<{isSuccess, body}> | Newly created DataType object, body: [DataTypeResponse](#datatyperesponse) |

###### Example Call

```jsx
const dataTypeName = "com.demo.health.anyExtendedCustomDataType";
const fieldValue = HmsSettingController.FIELD_STEPS_DELTA;

const result = await HmsSettingController.addNewDataType(
  dataTypeName,
  fieldValue
);
```

###### Example Response
```jsx
{
  "isSuccess": true,
  "body": {
    "fields": [{ "isOptional": false, "format": 1, "name": "steps_delta" }],
    "name": "com.demo.health.anyExtendedCustomDataType"
  }
}
```

##### HmsSettingController.readDataType(dataTypeString)

Reads the data type based on the data type name. This function is used to read the customized data types of the app.

| Parameter | Type   | Description           |
| --------- | ------ | --------------------- |
| dataTypeString  | string | The data type string. |

| Return Type                 | Description                                                  |
| --------------------------- | ------------------------------------------------------------ |
| Promise\<{isSuccess, body}> | DataType object, body: [DataTypeResponse](#datatyperesponse) |

###### Example Call

```jsx
const dataTypeName = "com.demo.health.anyExtendedCustomDataType";
const result = await HmsSettingController.readDataType(
  dataTypeName
);
```

###### Example Response
```jsx
{
  "isSuccess": true,
  "body": {
    "fields": [{ "isOptional": false, "format": 1, "name": "steps_delta" }],
    "name": "com.demo.health.anyExtendedCustomDataType"
  }
}
```

##### HmsSettingController.disableHiHealth()
Disables the Health Kit function, cancels user authorization, and cancels all data records. (The task takes effect in 24 hours.)

| Return Type                               | Description           |
| ----------------------------------------- | --------------------- |
| Promise\<[SuccessObject](#successobject)> | Empty Success object. |

###### Example Call

```jsx
const  result = await  HmsSettingController.disableHiHealth();
```

###### Example Response
```jsx
{"isSuccess": true}
```

##### HmsSettingController.getHealthAppAuthorization()
Checks the user privacy authorization to Health Kit. If the user authorized body returns true, else it returns false.

| Return Type                 | Description                                                  |
| --------------------------- | ------------------------------------------------------------ |
| Promise\<{isSuccess, body}> | A boolean that indicates whether user is authorized. `true: Authorized`, `false: Unauthorized` |

###### Example Call

```jsx
const  result = await  HmsSettingController.getHealthAppAuthorization();
```

###### Example Response
```jsx
{"isSuccess": true, body: true}
```

##### HmsSettingController.checkHealthAppAuthorization()
Checks the user privacy authorization to Health Kit. If the authorization has not been granted, the user will be redirected to the authorization screen where they can authorize the Huawei Health app to open data to Health Kit.

| Return Type                               | Description           |
| ----------------------------------------- | --------------------- |
| Promise\<[SuccessObject](#successobject)> | Empty Success object. |

###### Example Call

```jsx
const  result = await  HmsSettingController.checkHealthAppAuthorization();
```

###### Example Response
```jsx
{"isSuccess": true}
```

##### HmsSettingController.enableLogger()
Enables HMSLogger.

| Return Type                               | Description           |
| ----------------------------------------- | --------------------- |
| Promise\<[SuccessObject](#successobject)> | Empty Success object. |

###### Example Call

```jsx
const  result = await  HmsSettingController.enableLogger();
```

###### Example Response
```jsx
{"isSuccess": true}
```

##### HmsSettingController.disableLogger()
Disables HMSLogger.

| Return Type                               | Description           |
| ----------------------------------------- | --------------------- |
| Promise\<[SuccessObject](#successobject)> | Empty Success object. |

###### Example Call

```jsx
const  result = await  HmsSettingController.disableLogger();
```

###### Example Response
```jsx
{"isSuccess": true}
```


### HmsConsentsController

#### Function Summary
| Function                                              | Return Type                               | Description                                                  |
| ----------------------------------------------------- | ----------------------------------------- | ------------------------------------------------------------ |
| [get(lang, appId)](#hmsconsentscontrollerget)         | Promise\<{isSuccess, body}>               | Queries the list of permissions granted to your app.         |
| [revoke(appId, scopes)](#hmsconsentscontrollerrevoke) | Promise\<[SuccessObject](#successobject)> | Revokes Health Kit related permissions granted to your app. If scopes array is **null**, revokes all permissions. |

#### Functions

##### HmsConsentsController.get(lang, appId)
Queries the list of permissions granted to your app.

| Parameter | Type          | Description                                                  |
| --------- | ------------- | ------------------------------------------------------------ |
| lang      | string        | Language code. If the specified value is invalid, **en-us** will be used. |
| appId     | string | ID of your app.                                              |

| Return Type                  | Description                                                  |
| ---------------------------- | ------------------------------------------------------------ |
| Promise\<{isSuccess, body }> | Scope object has the authorized scopes, body: [ScopeResponse](#scoperesponse) |

###### Example Call

```jsx
const  lang = "en-us";
const  appId = "111111111";

const  result = await  HmsConsentsController.get(lang, appId);
```

###### Example Response
```jsx
{
  "isSuccess": true,
  "body": {
    "url2Desc": {
      "https://www.huawei.com/healthkit/nutrition.both": "Read and write HUAWEI Health Kit data on nutrition",
      "https://www.huawei.com/healthkit/calories.both": "Read and write HUAWEI Health Kit caloric data (including metabolism)",
      "https://www.huawei.com/healthkit/location.both": "Read and write HUAWEI Health Kit location data (including route)",
      "https://www.huawei.com/healthkit/step.both": "Read and write HUAWEI Health Kit step count data",
      "https://www.huawei.com/healthkit/activityrecord.both": "Access and add activity record  in HUAWEI Health Kit",
      "https://www.huawei.com/healthkit/activity.both": "Read and write HUAWEI Health Kit workout data (including continuous data, workout, power, running form, cycling, duration, and more)",
      "https://www.huawei.com/healthkit/heightweight.both": "Read and write HUAWEI Health Kit height and weight data",
      "https://www.huawei.com/healthkit/heartrate.both": "Read and write HUAWEI Health Kit heart rate data"
    },
    "authTime": "1608657809",
    "appName": "Health RN Demo",
    "appIconPath": "https://oauth-login.cloud.huawei.com/webview/images/default_applogo.jpg"
  }
}
```

##### HmsConsentsController.revoke(appId, scopes)
Revokes Health Kit related permissions granted to your app. If scopes array is **null**, revokes all permissions.

| Parameter | Type     | Description                                                  |
| --------- | -------- | ------------------------------------------------------------ |
| appId     | string   | ID of your app.                                              |
| scopes    | string[] | List of Health Kit related permissions to be revoked. The value is the key value of url2Desc in ScopeLangItem. |

| Return Type                               | Description           |
| ----------------------------------------- | --------------------- |
| Promise\<[SuccessObject](#successobject)> | Empty Success object. |

###### Example Call

```jsx
const  appId = "111111111";
const  scopes = [
"https://www.huawei.com/healthkit/step.both",
"https://www.huawei.com/healthkit/calories.both",
"https://www.huawei.com/healthkit/step.realtime",
];

const  result = await  HmsConsentsController.get(appId, scopes);
```

###### Example Response
```jsx
{"isSuccess": true}
```


---

## 4. Configuration and Description

### Configuring Obfuscation Scripts

Before building the APK, configure the obfuscation configuration file to prevent the HMS Core SDK from being obfuscated.

Open the obfuscation configuration file proguard-rules.pro in the app directory of your project and add configurations to exclude the HMS Core SDK from obfuscation.

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