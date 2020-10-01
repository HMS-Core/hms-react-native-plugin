# React-Native HMS DTM 

## Table of Contents
- [Introduction](#introduction)
- [Installation Guide](#installation-guide)
- [API Reference](#api-reference)
- [Configuration and Description](#configuration-and-description)
- [Sample Project](#sample-project)
- [Questions or Issues](#questions-or-issues)
- [Licensing and Terms](#licensing-and-terms)
  

## Introduction

The React Native DTM Plugin enables communication between Huawei DTM SDK and React Native platform. 
It exposes all functionality provided bt Huawei DTM SDK.

HUAWEI Dynamic Tag Manager (DTM) is a dynamic tag management system. With DTM, it allows you to dynamically update its tags in a web-based user interface to track specific events and report them to third-party analytics platforms, as well as tracking types of marketing activities.

## Installation Guide

Before using Reactive-Native DTM Plugin code, ensure that the RN development environment has been installed and completed. Also you need to have Huawei developer account to use Huawei Mobile Services and thus the HMS DTM Plugin. You can sign in/register to Huawei developer website from [here](https://developer.huawei.com/consumer/en/console).

### Creating a Project in AppGallery Connect
Creating an app in AppGallery Connect is required in order to communicate with the Huawei services. To create an app, perform the following steps:

**Step 1:** Sign in to [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html)  and select **My projects**.

**Step 2:** Select your project from the project list or create a new one by clicking the **Add Project** button.

**Step 3:** Go to **Project Setting** > **General information**, and click **Add app**.
If an app exists in the project and you need to add a new one, expand the app selection area on the top of the page and click **Add app**.

**Step 4:** On the **Add app** page, enter the app information, and click **OK**.

### Configuring the Signing Certificate Fingerprint

A signing certificate fingerprint is used to verify the authenticity of an app when it attempts to access an HMS Core (APK) through the HMS SDK. Before using the HMS Core (APK), you must locally generate a signing certificate fingerprint and configure it in the **AppGallery Connect**. You can refer to 3rd and 4th steps of [Generating a Signing Certificate](https://developer.huawei.com/consumer/en/codelab/HMSPreparation/index.html#2) codelab tutorial for the certificate generation. Perform the following steps after you have generated the certificate.

**Step 1:** Sign in to [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html) and select your project from **My Projects**. Then go to **Project Setting** > **General information**. In the **App information** field, click the  icon next to SHA-256 certificate fingerprint, and enter the obtained **SHA-256 certificate fingerprint**.

**Step 2:** After completing the configuration, click **OK** to save the changes. (Check mark icon)


#### **Prerequisites**

**Step 1:** The Analytics service uses capabilities of HUAWEI Analytics Kit to report analytics events. 
For details, please refer to [Service Enabling](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/service-enabling-0000001050745155).

**Step 2:** Go to **Project Setting > General information** page, under the **App information** field, click **agconnect-services.json** to download the configuration file.

**Step 3:** Copy the **agconnect-services.json** file to the **project-dir/android/app** directory of your project.

**Step 4:** Open the **build.gradle** file in the **project-dir/android** directory of your project.
- Navigate to the **buildscript** section and configure the Maven repository address and agconnect plugin for the HMS SDK.
  ```gradle
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
        classpath 'com.huawei.agconnect:agcp:1.4.1.300'
    }
  }
  ```
- Go to **allprojects** and configure the Maven repository address for the HMS SDK.
  
  ```gradle
  allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://developer.huawei.com/repo/' }
    }
  }
  ```

**Step 5:** Open the **build.gradle** file in the **project-dir/android/app** directory.
- Add `apply plugin: 'com.huawei.agconnect'` line after other `apply` entries.
```gradle
apply plugin: 'com.android.application'
apply plugin: 'com.huawei.agconnect'
```
- Set your package name in **defaultConfig > applicationId** and set **minSdkVersion** to **19** or higher. Package name must match with the **package_name** entry in **agconnect-services.json** file. 
```gradle
defaultConfig {
    applicationId "<package_name>"
    minSdkVersion 19
    /*
    * <Other configurations>
    */
}

dependencies {    
    /*
    * <Other dependencies>
    */
    implementation 'com.huawei.agconnect:agconnect-core:1.4.1.300'
}
```

>Note:
    - *Before obtaining the **agconnect-services.json** file, ensure that you have enabled the HUAWEI Analytics service. For details, please refer to [Enabling HUAWEI Analytics Kit](https://developer.huawei.com/consumer/en/doc/HMSCore-Guides/android-config-agc-0000001050163815-V5#EN-US_TOPIC_0000001050163815__li1031751692618).*
    - *If you have performed any modification such as setting the data storage location and enabling or managing some APIs, you need to download the latest **agconnect-services.json** file and use the file to replace the existing one in the **app** directory of your Android Studio project.*
    - *agconnect-services.json's package_name must be equals to your android application's package name*

**Step 6:** Create a file **project-dir/android/app/keystore_file.jks** that contains a reference to your keystore which you generated using [Generating a Signing Certificate](https://developer.huawei.com/consumer/en/codelab/HMSPreparation/index.html#2). Fill the required parameters in **project-dir/android/app/build.gradle** file.
```
signingConfigs {
    config {
        storeFile file('<keystore_file>.jks')
        storePassword '<keystore_password>'
        keyAlias '<key_alias>'
        keyPassword '<key_password>'
    }
}
...
```
> Warning: Keep this file private and don't include it on the public source control.

**Step 7:** Go to **'project-dir/android/app/proguard-rules.pro'** and put the related configurations given below. 

```
-ignorewarnings
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep class com.hianalytics.android.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}
```

If you are using AndResGuard, add it to the allowlist in the obfuscation configuration file.

```
"R.string.hms*",
"R.string.connect_server_fail_prompt_toast",
"R.string.getting_message_fail_prompt_toast",
"R.string.no_available_network_prompt_toast",
"R.string.third_app_*",
"R.string.upsdk_*",
"R.layout.hms*",
"R.layout.upsdk_*",
"R.drawable.upsdk*",
"R.color.upsdk*",
"R.dimen.upsdk*",
"R.style.upsdk*", 
"R.string.agc*"
```

### Enabling HUAWEI Analytics
The Analytics service uses capabilities of HUAWEI Analytics Kit to report analytics events. 
For details, please refer to [Service Enabling](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/service-enabling-0000001050745155).


### Using the Debug Mode
During development, you can enable the debug mode to view the event records in real time, observe the results, and adjust the event tracking scheme as needed.

- **Huawei Analytics**
To enable or disable the debug mode, perform the following steps:

    **Step 1.**  Run the following command on an Android device to enable the debug mode:
    ```bash
        adb shell setprop debug.huawei.hms.analytics.app <package_name>
    ```

    **Step 2.**  After the debug mode is enabled, all events will be reported in real time.


    **Step 3.**  Run the following command to disable the debug mode:
    ```bash
        adb shell setprop debug.huawei.hms.analytics.app .none.
    ```
- **Third-Party Platforms**
If you use a third-party template (such as a template of Adjust or AppsFlyer), run the following 

    **Step 1.**  command to enable the debug mode:
    ```bash
        adb shell setprop debug.huawei.hms.dtm.app <package_name>
    ```   

    **Step 2.**  Run the following command to disable the debug mode:
    ```bash
        adb shell setprop debug.huawei.hms.dtm.app .none.
    ```

### For Third Party Analytics Platforms

If you are using a third party platform, you must do the configurations of the platform you are using.

- Examples for some platforms:
Google Analytics => [Get started with Google Analytics](https://firebase.google.com/docs/analytics/get-started?platform=android)

- Apps Flyer => [Get started with Apps Flyer](https://support.appsflyer.com/hc/en-us/articles/207033486-Getting-started-step-by-step#basic-attribution)


### Operations on the Server
To access the DTM portal, perform the following steps:

**Step 1.**  Sign in to **AppGallery Connect** and click **My projects**.

**Step 2.**  Find your app project, and click the desired app name.

**Step 3.**  On the **Project Setting** page, go to **Growing** > **Dynamic Tag Manager**

>Note: *Before using DTM, you need to set a data storage location for your app. If the data storage location is not set, the system prompts you to set it when you go to the DTM portal.*

#### Permission
Configuration access is controlled by permission. Only the team account holder and administrator can manage permissions. Other team members have operation permissions corresponding to their roles in the team. For details, please refer to [Managing Teams and Roles](https://developer.huawei.com/consumer/en/doc/distribution/app/agc-team_account_mgt).
Visit [here](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides-V5/server-dev-0000001050043921-V5) for Permission List and more details.

#### Configuration
The configuration is a general term for all resources in DTM, including **Overview, Variable, Condition, Tag, Group, Version,** and **Visual Event**. Everything you do is done in configuration, so creating configurations is your first step. Configuration management includes importing, exporting, editing, and deleting configurations.

- **Creation:** Before using DTM, you need to create a configuration for managing resources.
- **Import:** You can directly import local configuration data to DTM.
- **Export:** You can export configuration data of any version or workspace from DTM.
- **Edit:** You can change the name and operation record storage duration of a configuration, and specify whether to report data to HUAWEI Analytics.
- **Deletion:** You can delete a configuration version that you have not released.

##### Creating a Configuration

**Step 1.**  Click **Create configuration** on the DTM portal.

**Step 2.**  In the **Create configuration** dialog box that is displayed, set **Configuration name, Operation record**, and **Tracked data**.

**Step 3.**  Click **OK** to create the configuration

**Step 4.**  View the configuration code in the dialog box displayed. The configuration code is used to differentiate configurations.

**Step 5.**  View the configuration on the **Configuration management** page. You can click its name to access the configuration page.

>Note:
    - *Only the team account holder can create configurations.*
    - *DTM will automatically fill in the app package name with that entered during app creation. You cannot change the app package name when creating a configuration.*
    - *You can create only one configuration for an app.*

##### Importing a Configuration
You can import a configuration from a specified file to the current draft configuration. During import, the server will verify the format and integrity of the imported file. To prevent verification failure, you are advised to import a configuration file exported in Exporting a Configuration.

**Step 1.**  Click **Import** on the right of a configuration on the **Configuration management** page.

**Step 2.**  Select a local JSON file, specify an import policy, and click **Import**

>Note:
    - *The imported configuration must contain at least one variable, condition, or tag.*
    - *The import policies include Overwrite and Merge. If you select Overwrite, variables, conditions, tags, and groups in the draft configuration will be overwritten with the imported ones.*
    - *If you select Merge, you need to further specify the rule for processing variables, conditions, tags, and groups with the same names as existing ones.*
    - *Overwriting old content with new one: Overwrites data in the draft with imported data.*
    - *Renaming new content: Automatically renames imported data with the same name as existing data, and retains both imported data and existing data.*
    - *Retaining old content: Discards imported data with the same name as existing data.*

##### Exporting a Configuration
You can export historical configuration or draft configuration to a JSON file. The file can be used to synchronize the configuration between apps or restore the configuration to a historical version. For details, please refer to Importing a Configuration.

**Step 1.**  Click **Export** on the right of a configuration on the **Configuration management** page.

**Step 2.**  Select a configuration version and click **Export** to export it.

>Note: *In the **Export configuration** dialog box, **Draft version** indicates to export data in the current draft configuration, including content modified after new version creation.*

##### Editing a Configuration
After a configuration is created, you can modify its name, operation record storage duration, and tracked data report settings.

**Step 1.**  Click **Edit** on the right of a configuration on the Configuration management page.

**Step 2.**  Modify **Configuration name**, **Operation record**, or **Tracked data** and click **OK**.

##### Deleting a Configuration

**Step 1.**  Click **Delete** on the right of a configuration on the **Configuration management** page.

**Step 2.**  Confirm the information in the dialog box and click **OK**.

>Note: *A configuration with a released version cannot be deleted.*


##### Creating a Configuration

**Step 1.**  Click **Create configuration** on the DTM portal.

**Step 2.**  In the **Create configuration** dialog box that is displayed, set **Configuration name, Operation record,** and **Tracked data**.

**Step 3.**  Click **OK** to create the configuration

**Step 4.**  View the configuration code in the dialog box displayed. The configuration code is used to differentiate configurations.

**Step 5.**  View the configuration on the **Configuration management** page. You can click its name to access the configuration page.

>Note:
    - *Only the [team account holder](https://developer.huawei.com/consumer/en/doc/start/10124) can create configurations.*
    - *DTM will automatically fill in the app package name with that entered during app creation. You cannot change the app package name when creating a configuration.*
    - *You can create only one configuration for an app.*

##### Importing a Configuration
You can import a configuration from a specified file to the current draft configuration. During import, the server will verify the format and integrity of the imported file. To prevent verification failure, you are advised to import a configuration file exported in **Exporting a Configuration**.

**Step 1.**  Click **Import** on the right of a configuration on the **Configuration management** page.

**Step 2.**  Select a local JSON file, specify an import policy, and click **Import**

>Note:
    - *The imported configuration must contain at least one variable, condition, or tag.*
    - *The import policies include **Overwrite** and **Merge**. If you select **Overwrite**, variables, conditions, tags, and groups in the draft configuration will be overwritten with the imported ones.*
    - *If you select **Merge**, you need to further specify the rule for processing variables, conditions, tags, and groups with the same names as existing ones.*
        - *1. Overwriting old content with new one: Overwrites data in the draft with imported data.*
        - *2. Renaming new content: Automatically renames imported data with the same name as existing data, and retains both imported data and existing data.*
        - *3. Retaining old content: Discards imported data with the same name as existing data.*

##### Exporting a Configuration
You can export historical configuration or draft configuration to a JSON file. The file can be used to synchronize the configuration between apps or restore the configuration to a historical version. For details, please refer to **Importing a Configuration**.

**Step 1.**  Click **Export** on the right of a configuration on the **Configuration management** page.

**Step 2.**  Select a configuration version and click **Export** to export it

>Note: *In the Export configuration dialog box, Draft version indicates to export data in the current draft configuration, including content modified after new version creation.*

##### Editing a Configuration
After a configuration is created, you can modify its name, operation record storage duration, and tracked data report settings.

**Step 1.**  Click **Edit** on the right of a configuration on the **Configuration management** page.

**Step 2.**  Modify **Configuration name, Operation record,** or **Tracked data** and click **OK**.

##### Deleting a Configuration

**Step 1.**  Click **Delete** on the right of a configuration on the **Configuration management** page.

**Step 2.**  Confirm the information in the dialog box and click **OK**.

>Note: *A configuration with a released version cannot be deleted.*


#### Overview
The **Overview** page displays the change history and operation records of variables, conditions, and tags in the current configuration version. You can click **View change** in the **Operation** column of a record to view change details. You can also create a version on the **Overview** tab page.

##### Viewing the Change History

**Step 1.**  Create, modify, or delete a variable, condition, or tag on the corresponding tab page, and click the **Overview** tab to view the change history. Here, a variable is used as an example.

**Step 2.**  Click the name of a variable, condition, or tag in **Change history**, and view the change details. Here, a variable is used as an example.

**Step 3.**  View variable, condition, or tag changes in the current version compared with the previous version. You need to create a version.
    - Click **Create** version, set related parameters, and click **OK**. Then, go to the **Overview** tab page and view the version creation record.
    - Modify a variable, condition, or tag in the previous version, and then click the **Overview** tab. In the **Change history** area, click **View change** in the **Operation** column of the modified variable, condition, or tag. Here, a variable is used as an example.
    - View specific changes on the **View change** page displayed

##### Viewing an Operation Record

Create, modify, or delete a variable, condition, tag, or group on the corresponding tab page, click the **Overview** tab, and view the operation record in the **Operation** records area. You can also export operation records into an Excel file.


#### Variable
A variable is a placeholder used in a condition or tag. For example, the **App Name** variable indicates the name of an Android app. DTM provides preset variables which can be used to configure most tags and conditions. You can also create your own custom variables. Currently, DTM provides 18 types of preset variables and 6 types of custom variables. Preset variable values can be obtained from the app without specifying any information. For a custom variable, you need to specify the mode to obtain its value

| **Variable**             | **Description**                                                                       | 
|--------------------------|---------------------------------------------------------------------------------------|
| Event Name               | Name of a triggered event.                                                            |
| Random Number            | Random integer ranging from 0 to 2,147,483,647.                                       |
| Configuration ID         | ID of a configuration.                                                                |
| Configuration Version    | Release version of a configuration.                                                   |
| SDK Version              | SDK version number.                                                                   |
| App ID                   | App ID (Android), which must be set to the app package name.                          |
| App Name                 | Name of an app.                                                                       |
| App Version Name         | Name of an app version.                                                               |
| App Version Code         | Code of an app version.                                                               |
| Screen Resolution        | Screen resolution of the device where the app is running. The value is in Width x     |
|                          | Height format.                                                                        |
| Platform                 | Operating system (Android).                                                           |
| OS Version               | Version of the operating system where the app is running. The value is a string,      |
|                          | for example, 8.1.0.                                                                   |
| Device ID                | ID of a device.                                                                       |
| Device Name              | Name of a device.                                                                     |
| Language                 | Language set by the user.                                                             |
| OAID                     | Huawei advertising ID.                                                                |
| Limit OAID tracking      | Indicates whether to enable ad                                                        |
| Device IP address        | IP address of a device.         
                                                     

| **Variable Type**        | **Description**                                                                       | 
|--------------------------|---------------------------------------------------------------------------------------|
| User property            | User property, which is used to obtain the user property value when the tracking tag  |
|                          | is executed. The user property value is set by HUAWEI Analytics. You can also set     |
|                          | a default value for a user property on the DTM portal. The default value will be used | 
|                          | for the user property if no value is obtained during tag execution. If no default     |
|                          | value is set, the user property will be left empty when no value is obtained.         |
| Event parameter          | Event parameter, which is generally used to obtain the parameter value of             |
|                          | the reported event from the event context during tag execution.                       |
| Mapping table            | A mapping table contains a group of key-value pairs. You can enter a value and obtain | 
|                          | the corresponding parameter value from the table. With the mapping table, you can     |
|                          | make the variable value automatically change according to the input value.            |
|                          | The following table is a mapping table example. In the example, multiple versions     |
|                          | of an app have been released, and the version codes are 1.0, 2.0, and 3.0             |
|                          | respectively. If the defined variable Version ID needs to be set to different values  |
|                          | for different app versions, you can define the value mapping for each version in      |
|                          | a mapping table. During tag execution, the version code is matched to obtain          |
|                          | the variable value.                                                                   |
| Constant                 | You can set a constant value for a variable of this type.                             |
| Function call            | To obtain the value of a custom variable, you can set the variable type to            |
|                          | Function Call and specify the path of a class which has implemented the               |
|                          | ICustomVariable API. During tag execution, the variable will be set to the value      |
|                          | returned by the specified class. When configuring a Function call variable on         |
|                          | the DTM portal, you can configure parameter values which can be used in the class of  |
|                          | implemented APIs.                                                                     |
| Branch                   | Branch variables support the following data types: integer, float, Boolean, character,| 
|                          | array, and key-value pair. When using the Branch extension template, you can reference|
|                          | relevant Branch variables based on the value ranges of Branch extension template      |
|                          | fields.                                                                               |


| **If {{App Version Code}} Is**       | **Set {{Version ID}} To**                                                 | 
|--------------------------------------|---------------------------------------------------------------------------|
| 1.0                                  | value_version1.0                                                          |
| 2.0                                  | value_version2.0                                                          |
| 3.0                                  | value_version3.0                                                          |


##### Creating a Preset Variable

**Step 1.**  Click **Configure** on the right of **Preset variables** on the **Variable** page.

**Step 2.**  In the **Configure preset variable** dialog box that is displayed, select the preset variable to be created and click **OK**.

**Step 3.**  View the created preset variable on the **Variable** page.

>Note: *You can click a preset variable to go to the preset variable page and view resources referencing the variable.*

##### Deleting a Preset Variable

**Step 1.**  Click **Configure** on the right of **Preset variables** on the **Variable** page.

**Step 2.**  In the **Configure preset** variable dialog box that is displayed, deselect the preset variable to be deleted and click **OK**.

>Note: *If a variable is referenced by other resources, the variable cannot be deleted. To delete it, you need to delete relevant reference relationships.*


##### Creating a Custom Variable

**Step 1.**  Click **Create** on the right of **Custom variables** on the **Variable** page.

**Step 2.**  On the **Create custom variable** page that is displayed, enter a variable name, select a variable type, set related parameters, and click **Save**.

**Step 3.**  View the created custom variable on the **Variable** page.

>Note:
    - *If the reference icon (Click to enlarge) is displayed next to the text box of a variable parameter, you can click the icon to reference a created variable. A referenced variable is marked with **{{}}** on the page.*
    - *Variables cannot be referenced cyclically. In addition, a variable cannot reference itself. Otherwise, variable creation will fail.*

##### Creating a Custom Variable (Function Call)
A custom variable of the **Function call** type can call a class method of an app and pass relevant parameter key values. 

**Step 1.**  Create a variable (Event Parameter)
- Click Create on the right of Custom variables on the Variable  page.
- On the **Create custom variable** page that is displayed, enter a variable name, select **Event Parameter** type, enter a variable  event parameter key, and click **Save**.

- **A Event Parameter Example**;
    - Name: app_price
    - Type: Event parameter
    - Event parameter key: price

**Step 2.**  Create a variable (Function Call)
- Click Create on the right of Custom variables on the Variable  page.
- On the **Create custom variable** page that is displayed, enter a variable name, select **Function Call** type, enter your class path , add at least one variable and click **Save**.

>Note: *Your class name and variable name must be the same.*

    - **A Function Call Example**;
        - Name: CustomVariable
        - Type: Function call
        - Class Path: com.huawei.hms.rn.dtm.interfaces.CustomVariable
        - Parameter: key:price , value:{{app_price}}

**Step 3.**  Create a condition
- Click **Create** on the **Condition** page.
- On the **Create condition** page that is displayed, enter a condition name, select **Custom** type, and select **Some events** trigger and add you can click **Add** to add multiple trigger conditions.
- Click **Save**

    - **A Condition**;
        -  Name: PurchaseEvent
        - Type: Custom
        - Trigger: Some events
        - Variable: EventName, Operator :Equals, Value:Purchase
        - Variable: app_price, Operator: Equals, Value:40
        - Variable: CustomVariable, Operator: Equals, Value:40

**Step 4.**  Create a tag
- Click **Create** on the **Tag** page.
- On the **Create tag** page that is displayed, enter a tag name, select **HUAWEI Analytics** extension template,select a operation, enter a event name , add parameters for addition or modification, add a trigger condition and add a exception condition.

>Note: *You must add at least one condition.*

- **A Tag**;
    - Name: HiTest
    - Extension: HUAWEI Analytics
    - Operation: Add Event
    - Event Name: HiPurchase
    - Parameters for addition or modification=> Key: HiPrice, Value:{{app_price}}
    - Parameters for addition or modification=> Key: HiPriceCall, Value:{{CustomVariable}}
    - Parameters for addition or modification=> Key: HiCurrency, Value:{{app_currency}}
    - Parameters for addition or modification=> Key: Puchase{{Event Name}}, Value:Purchase

**Step 5.**  Create a group
- Click **Create** on the Group page.
- On the **Create group** page, enter a group name and click **OK**.
- Click on the **Operation** button on the left of your group name and add variables,connditions, add tags.

- Variable (Function call): CustomVariable
    - Variable (Event Parameter): app_price
    - Variable (Event Parameter): app_currency
    - Condition: PurchaseEvent
    - Tag: HiTest

**Step 6.**  Create a version
- Click **Create version** on the **Overview** page or click **Create** on the **Version** page.
- On the **Create version** page that is displayed, enter the version name and description, and click **OK**.
- View the created version on the **Version** page.
- On the **Version** details page that is displayed, view the overview, operation records, variables, conditions, and tags of the version. 
- Click **Release**
- Click **Download**
- Put the downloaded config file under **your app**> **src**> **main**> **assets**> **containers** folder.

**Step 7.**  In your demo application, call the customFunction() method similar to the example below.
```javaScript
import React from 'react';
import HmsDTMModule from '@hmscore/react-native-hms-dtm/src';

 async exampleCustomFunc() {
    try {
      var eventId = "Purchase"
      const params=[
        {
          hasCustom:true,
          value:"price",
          key:"40"
        },
        {
          value:"name",
          key:"pencil"
        },
        {
          value:"discountPrice",
          key:"40"
        },
        {
          value:"color",
          key:"red"
        }
      ]

    await HmsDTMModule.customFunction(eventId, params)

```

**Step 8.**  Return value via getValue interface in CustomVariable class

```java
package com.huawei.hms.rn.dtm.interfaces;
import com.huawei.hms.dtm.ICustomVariable;
import java.util.Map;

public class CustomVariable implements ICustomVariable {

    @Override
    public String getValue(Map<String, Object> map) {
       String returnValue="";

        for (String key :  map.keySet()) {
            Object value = map.get(key);
            if (value instanceof String ) {
                returnValue = (String) value;
            }
        }
        return returnValue;
    }
}
```

**Step 9.**  Check your EventNames on **App Debugging**


#### Condition
- A condition is the prerequisite for triggering a tag and determines when the tag is executed. A tag must contain at least one trigger condition.
- A condition consists of three elements: name, type, and trigger condition.
- The name and type are mandatory.
- If Trigger is set to All events, all types of events will trigger the condition. If Trigger is set to Some events, you need to further specify trigger conditions for the condition.

- Each trigger condition consists of three parts: variable, operator, and attribute value. You can currently choose between 2 types of conditions and 16 types of operators.
- Currently, condition management includes creating, copying, and deleting a condition.
- Creating a condition: You can create a condition for triggering a tag.
- Copying a condition: You can copy any condition you have created. You can then quickly modify it and add a new condition.
- Deleting a condition: You can delete a condition that is not being referenced.
- Click [More Details](https://developer.huawei.com/consumer/en/doc/07189836#h1-1579502667391-0)

#### Tag
A tag is used in your app to track events. DTM supports the **HUAWEI Analytics** and custom function templates, as well as many third-party tag extension templates. With DTM, you do not need to add additional third-party tracking tags in your app. You can set parameters and trigger conditions for a tag in DTM, and release the configuration version to track events. You can also update and release tags for your app in DTM after you have released it, so you can adjust tag configurations in real time.

Supported tag extension templates

**Tag Extension Template**                                     
 - [HUAWEI Analytics](https://developer.huawei.com/consumer/en/doc/development/HMS-Guides/64646044#h1-1589536569911)      

 - [Adjust](https://developer.huawei.com/consumer/en/doc/HMSCore-Guides-V5/extension-template-fields-0000001050041992-V5#EN-US_TOPIC_0000001054944417__section6698918202218)               
 - [AppsFlyer](https://developer.huawei.com/consumer/en/doc/HMSCore-Guides-V5/extension-template-fields-0000001050041992-V5#EN-US_TOPIC_0000001054944417__section1981115211212)          
 - [Google Analytics (Firebase)](https://developer.huawei.com/consumer/en/doc/HMSCore-Guides-V5/extension-template-fields-0000001050041992-V5#EN-US_TOPIC_0000001054944417__section218616417221)                                  
 - [Custom function](https://developer.huawei.com/consumer/en/doc/HMSCore-Guides-V5/extension-template-fields-0000001050041992-V5#EN-US_TOPIC_0000001054944417__section18330918240)

 - [Custom URL](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/server-dev-0000001050043921) 

 - [Kochava event tracking](https://developer.huawei.com/consumer/en/doc/HMSCore-Guides-V5/extension-template-fields-0000001050041992-V5#EN-US_TOPIC_0000001054944417__section151309112311)                                      
 - [Floodlight counter](https://developer.huawei.com/consumer/en/doc/HMSCore-Guides-V5/extension-template-fields-0000001050041992-V5#EN-US_TOPIC_0000001054944417__section1881815216231)                                        
 - [Floodlight sales](https://developer.huawei.com/consumer/en/doc/HMSCore-Guides-V5/extension-template-fields-0000001050041992-V5#EN-US_TOPIC_0000001054944417__section199114222314)                                         
 - [Google Ads remarketing](https://developer.huawei.com/consumer/en/doc/HMSCore-Guides-V5/extension-template-fields-0000001050041992-V5#EN-US_TOPIC_0000001054944417__section63151927202412)                                       
 - [Singular](https://developer.huawei.com/consumer/en/doc/HMSCore-Guides-V5/extension-template-fields-0000001050041992-V5#EN-US_TOPIC_0000001054944417__section270674716246)

 - [Branch](https://developer.huawei.com/consumer/en/doc/HMSCore-Guides-V5/extension-template-fields-0000001050041992-V5#EN-US_TOPIC_0000001054944417__section2811191122513)      

##### Creating a Tag

**Step 1.**  Click Create on the **Tag** page.

**Step 2.**  On the **Create tag** page that is displayed, enter a tag name, select a tag extension template, and set related parameters.

**Step 3.**  On the **Create tag** page, set **Trigger condition** and **Exception condition** for the tag. When the specified trigger condition is met, events will be reported to the analytics platform. When the specified exception condition is met, events will be blocked and not reported to the analytics platform.

**Step 4.**  Click **Save**.       

>Note: 
    - *If the reference icon **(+)** is displayed next to the text box of a tag parameter, you can click the icon to reference a created variable. A referenced variable is marked with **{{}}** on the page.*
    -*A tag must contain at least one trigger condition. If both trigger and exception conditions are configured for a tag, the exception condition is triggered in priority.*

###### A Sample For The Custom Tag

**Step 1.**  Create a Variable (Parameter Event)
    - Name: app_type
    - Type: Event parameter
    - Event parameter key: type

**Step 2.**  Create a Condition (Custom)
    - Name: type_condition
    - Type: Custom
    - Trigger: Some events
    - Variable: app_type, Operator: Equals, Value:big

**Step 3.**  Create a Tag (Custom Function)
    - Name: CustomTag
    - Extension: Custom function
    - Class path: com.huawei.hms.rn.dtm.interfaces.CustomTag
    - Variable: Key: app_type, Value:big

>Note:  *classpath=> your ICustomTag interface class path*


##### Modifying a Tag

**Step 1.**  Click a tag on the Tag page.

**Step 2.**  On the Edit tag page that is displayed, modify the tag configurations and conditions.

**Step 3.**  Click Save.

##### Copying a Tag

**Step 1.**  Click **Copy** next to a tag on the **Tag** page.

**Step 2.**  On the **Copy tag** page that is displayed, modify the tag configurations and conditions.

**Step 3.**  Click **Save**.

##### Pausing/Executing a Tag

**Step 1.**  Click **Pause** next to a tag on the **Tag** page. Confirm the information in the dialog box and click **OK**. The pause icon will be displayed ahead a paused tag. In addition, a paused tag will not be triggered to report events in configuration versions created later.

**Step 2.**  Click **Execute** next to the paused tag to resume it. After the paused tag is resumed, the pause icon ahead the tag name will disappear and the tag can be triggered to report events in configuration versions created later.

##### Deleting a Tag

**Step 1.**  Click Delete next to a tag on the Tag page.

**Step 2.**  Confirm the information in the dialog box and click OK.

>Note: *A deleted tag cannot be restored. Before deleting a tag, ensure that the tag is no longer used. If a tag is not needed for a while, you can pause the tag. For details, please refer to [Pausing/Executing a Tag](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/web-server-dev-0000001050777506#EN-US_TOPIC_0000001055544346__section667811115511).*


#### Group
You can group elements such as variable, condition, tag from the **Group** page according to the content of the data you send. You can make changes and delete your groups.

##### Creating a Group

**Step 1.**  Click **Create** on the **Group** page.

**Step 2.**  On the **Create group** page, enter a group name and click **OK**.

**Step 3.**  View the created group on the **Group** page.

>Note: *By default, the Non-archived items group has been created in DTM. All uncategorized resources are archived in this group.*

##### Moving Resources in a Group to Another Group

**Step 1.**  On the **Group** page, select a resource to be moved in a group.

**Step 2.**  Click **Move** to on the right of the group and select a target group to which the resource is moved.

##### Modifying a Group

**Step 1.**  On the **Group** page, click **Operation** and select Rename group on the right of a group.

**Step 2.**  Modify the group name on the **Rename group** page.

**Step 3.**  Click **OK**.

##### Deleting a Group

**Step 1.**  On the Group page, click **Operation** and select **Delete group** on the right of a group.

**Step 2.**  Confirm the information in the dialog box and click **OK**.

>Note: *If a group contains resources, you need to remove the resources from the group before deleting it*

#### Version
A version is a snapshot of a configuration at a time point. It can be used to record different phases of a configuration. You can create a version for a configuration anytime to save the latest configuration. After creating a version, you can click the version name to view details about the version. On the Version details page of a version, you can download, preview, and release the version. Only a released version can be automatically loaded by apps on devices.

##### Creating a Version

**Step 1.**  Click **Create version** on the Overview page or click **Create** on the **Version** page.

**Step 2.**  On the **Create version** page that is displayed, enter the version name and description, and click **OK**.

**Step 3.**  View the created version on the **Version** page.

##### Viewing Version Details
**Step 1.**  Click a version on the **Version** page.

**Step 2.**  On the **Version details** page that is displayed, view the overview, operation records, variables, conditions, and tags of the version. You can also download, preview, release, export, modify, or delete the version on this page.

##### Downloading a Version

**Step 1.**  Click **Download** on the **Version details** page.

**Step 2.**  Select a local directory and click **Save**. The configuration version will be saved as a JSON file that can be loaded and executed by the DTM SDK.

##### Previewing a Version
You can preview a configuration version to check whether it can be executed as expected. You can start your app on the debugging device through methods provided on the **Preview** page. Your app will automatically load the latest configuration version, enabling you to verify the configuration version before releasing it.

**Step 1.**  Click **Preview** on the **Version details** page.

**Step 2.**  On the **Preview** page that is displayed, view preview links generated for the app bound to the configuration by default. To change the app package name, directly enter a new app package name in the text box previous **Generate preview** and click **Generate preview**.

**Step 3.**  Preview your app configuration version. Three methods for previewing an app configuration version are displayed under **Start preview**. You can preview your app configuration version using any of the methods.

**Step 4.**  Stop previewing your app configuration version. Three methods for stopping previewing an app configuration version are displayed under **End preview**. You can stop previewing your app configuration version using any of the methods. The usage is similar to that for previewing an app configuration version.

##### Releasing a Version
After creating a configuration version, you need to release it so that the DTM SDK can automatically load the configuration version in the next update period. If a configuration has multiple released versions, the DTM SDK will load the latest released version. To ensure that a version can be executed as expected, you can debug the version through version preview before releasing it.

**Step 1.**  Click **Release** on the **Version details** page.

**Step 2.**  Confirm the information in the dialog box and click **OK**.

**Step 3.**  View the person releasing the version and release time in the version information after successful release.

>Note: *The DTM SDK automatically loads configuration at an interval of 6 hours.*

##### Modifying a Version

**Step 1.**  Go to **Operation** > **Modify description** on the **Version details** page.

**Step 2.**  On the **Modify version** page that is displayed, modify the version name and description.

**Step 3.**  Click **OK**.

##### Exporting a Version
Similar to version export in Exporting a Configuration, you can export the variables, conditions, and tags of the current configuration version to a JSON file. The exported file can be used in Import a Configuration.

**Step 1.**  Go to **Operation** > **Export version** on the **Version details** page.

**Step 2.**  Select a local directory and click **Save**.

##### Deleting a Version

**Step 1.**  Go to **Operation** > **Delete version** on the **Version details** page.

**Step 2.**  Confirm the information in the dialog box and click **OK**.

>Note: *The latest version and current online version released of a configuration cannot be deleted.*


### Using NPM

**Step 1.**  Download plugin using command below.

```bash
    npm i @hmscore/react-native-hms-dtm
```

**Step 2.**  The **node_modules** directory structure should be like given below.

            demo-app
                |_ node_modules
                    |_ @hmscore
                        |_ react-native-hms-dtm
                        ...

>Note:  *Make sure to complete [Enabling HUAWEI Analytics](#enabling-huawei-analytics) and [Operations on the Server](#operations-on-the-server) steps in your project.*

### Download Link
To integrate the plugin, follow the steps below:

**Step 1.**  Download the React Native Analytics Plugin and place **react-native-hms-dtm** under **node_modules/hmscore** of your React Native project, as shown in the directory tree below.

            demo-app
                |_ node_modules
                    |_ @hmscore
                        |_ react-native-hms-dtm
                        ...


**Step 2.**  Open the build.gradle file in the android directory of your React Native project, and change the value of **minSdkVersion** in buildscript to **19**.

```java 
    defaultConfig {               
        applicationId "<package_name>"            
        minSdkVersion 19            
        /*                  
        * <Other configurations>                  
        */         
    }
```

>Note:  *The **applicationId** must match with the **package_name** entry in the **agconnect-services.json** file.*

**Step 3.**  Configure the signature in android according to the signature file information.
```java 
android {    
    /*     
    * <Other configurations>     
    */    
    signingConfigs {       
        release {            
            storeFile file('<keystore_file>')            
            storePassword '<keystore_password>'            
            keyAlias '<key_alias>'            
            keyPassword '<key_password>'        
        }    
    }   

    buildTypes {        
        debug {            
            signingConfig signingConfigs.release        
        }       
        release {            
            signingConfig signingConfigs.release        
        }    
    }
}
```

**Step 4.**  Replace <keystore_file>, <keystore_password>, <key_alias>, and <key_password> fields with matching entries in your signature file.For further details, please refer to Configuring the Maven Repository Address for the HMS Core SDK.

**Step 5.**  Add the following lines to the android/settings.gradle file in your project:

```java 
    rootProject.name = 'RNHmsDTM Demo'
    apply from: file("../node_modules/@react-native-community/cli-platform-android/native_modules.gradle"); applyNativeModulesSettingsGradle(settings)
    include ':react-native-hms-dtm'
    project(':react-native-hms-dtm').projectDir = new File(rootProject.projectDir, '../node_modules/@hmscore/react-native-hms-dtm/android')
    include ':app'
```

**Step 6.**  Insert the following lines inside the dependencies block in **android/app/build.gradle**:

```java 
dependencies {    
    implementation project(":react-native-hms-dtm")    
    ...   
    implementation 'com.huawei.agconnect:agconnect-core:1.4.1.300'
}
```

**Step 7.**  Synchronize the project.
Click **Sync Now** and wait until synchronization is complete.

**Step 8.**  Open the **project-dir** > **android** > **app** > **src** > **main** > **java** > <package_name> > **MainApplication.java** file.

Import the following classes to the MainApplication.java file of your project.import **com.huawei.hms.rn.HmsDtmPackage**. 

Then, add the **HmsDtmPackage** to your **getPackages** method.
In the end, your file will be similar to the following:

```java 
package com.huawei.hms.rn.dtm.demo;
...
import com.huawei.hms.rn.dtm.HmsDtmPackage;

public class MainApplication extends Application implements ReactApplication {

private final ReactNativeHost mReactNativeHost =
    new ReactNativeHost(this) {
        ...
        @Override
        protected List<ReactPackage> getPackages() {
        List<ReactPackage> packages = new PackageList(this).getPackages();
        packages.add(new HmsDtmPackage());
        return packages;
        }
        ...
    };
}
```

>Note:  *Make sure to complete [Enabling HUAWEI Analytics](#enabling-huawei-analytics) and [Operations on the Server](#operations-on-the-server) steps in your project*


## API Reference

### HmsDtmModule

#### Public Method Summary

  | Return Type        | Function                                    | Description                                     |  
  |--------------------|---------------------------------------------|-------------------------------------------------|
  |  Promise(String)   | onEvent(eventType, bundle)                  | Reports an event.                               |
  |  Promise(String)   | customFunction(eventType, paramArray)       | It is a special case of the onEvent method.     |
  |                    |                                             | Used to trigger CustomVariables and CustomTags. |
  |                    |                                             | It is preferred for performing operations on    |
  |                    |                                             | Function Call and Custom Tags.                  | 
  | Promise(boolean)   | enableLogger                                | Enables HMS Plugin Method Analytics.                 |
  | Promise(boolean)   | disableLogger                               | Disables HMS Plugin Method Analytics.                |

#### Public Methods

##### onEvent(String eventId, ReadableMap map)
- This API is called to record events.

| Parameters       | Description                                                         |
|------------------|---------------------------------------------------------------------|
| eventId          | ID of a customized event. The value cannot be empty and can contain |
|                  | a maximum of 256 characters, including digits, letters,             |
|                  | and underscores (_). It cannot start with a digit or underscore.    |
|                  | The value of this parameter cannot be an ID of an automatically     | 
|                  | collected event.                                                    |
| map              | Information carried by the event. The size of the data in the "map" |
|                  | cannot exceed 200 KB                                                |


###### Call Example

```JavaScript
  async customEvent() {

    var eventId = "Campaign"
    try {
      const bundle = {
        "name": "superOpportunity",
        "discountRate": "30",
        "prerequisite": false,
        "expirationTime": "20.10.2020",
      }
      var res = await HmsDTMModule.onEvent(eventId, bundle)
      console.log("Response: "+ res)
    } catch (e) {
      console.log("Error: " + JSON.stringify(e))
    }

  }
```

For more information, refer to the Analytics [onEvent](https://developer.huawei.com/consumer/en/doc/development/HMS-Plugin-Guides-V1/recording-events-0000001052746055-V1) function description.


##### customFunction(String eventId, ReadableArray array)
- This API is called to record events.

| Parameters       | Description                                                         |
|------------------|---------------------------------------------------------------------|
| eventId          | ID of a customized event. The value cannot be empty and can contain |
|                  | a maximum of 256 characters, including digits, letters,             |
|                  | and underscores (_). It cannot start with a digit or underscore.    |
|                  | The value of this parameter cannot be an ID of an automatically     | 
|                  | collected event.                                                    |
| array            | Information carried by the event.The size of the data in the "array"|
|                  | cannot exceed 200 KB                                                |

###### Call Example

```JavaScript
    var eventId = "Test"
    try {
      
      const params=[
        {
          hasCustom:true,
          value:"type",
          key:"big"
        },
        {
          hasCustom:false
          value:"id",
          key:"123456"
        },
        {
          value:"name",
          key:"room"
        }
      ]

      var res = await HmsDTMModule.customFunction(eventId, params)
      console.log("Response: "+ res)
    } catch (e) {
      console.log("Error: " + JSON.stringify(e))
    }

  }
```

##### enableLogger()

This method enables HMSLogger capability which is used for sending usage analytics of DTM SDK's methods to improve the service quality.

###### Call Example

```JavaScript
  async enableLogger() {
    try {
      var res = await HmsDTMModule.enableLogger()
      console.log("HMSLogger State: "+ res)
    } catch (e) {
      console.log("Error: " + JSON.stringify(e))
    }
  }
```

##### disableLogger() 

This method disables HMSLogger capability which is used for sending usage analytics of DTM SDK's methods to improve the service quality.

###### Call Example

```JavaScript
  async disableLogger() {
    try {
      var res = await HmsDTMModule.disableLogger()
      console.log("HMSLogger State: "+ res)
    } catch (e) {
      console.log("Error: " + JSON.stringify(e))
    }
  }
```

## Configuration and Description
   
Include the following permission in your app's AndroidManifest file. 

```xml
    <uses-permission android:name="android.permission.INTERNET" />
```

## Sample Project

This plugin includes a demo project in the **example** folder, there you can find more usage examples.

<img src="example/assets/images/dtm.screenshot.jpg" width = 300px style="margin:1em">

## Questions or Issues
If you have questions about how to use HMS samples, try the following options:
- [Stack Overflow](https://stackoverflow.com/questions/tagged/huawei-mobile-services) is the best place for any programming questions. Be sure to tag your question with 
**huawei-mobile-services**.
- [Github](https://github.com/HMS-Core/hms-react-native-plugin) is the official repository for these plugins, You can open an issue or submit your ideas.
- [Huawei Developer Forum](https://forums.developer.huawei.com/forumPortal/en/home?fid=0101187876626530001) HMS Core Module is great for general questions, or seeking recommendations and opinions.
- [Huawei Developer Docs](https://developer.huawei.com/consumer/en/doc/overview/HMS-Core-Plugin) is place to official documentation for all HMS Core Kits, you can find detailed documentations in there.

If you run into a bug in our samples, please submit an issue to the Repository.


## Licensing and Terms  
Huawei React-Native SDK is licensed under [Apache 2.0 license](LICENCE)

