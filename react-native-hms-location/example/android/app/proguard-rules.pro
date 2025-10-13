-ignorewarnings 
-keepattributes *Annotation* 
-keepattributes Exceptions 
-keepattributes InnerClasses 
-keepattributes Signature 
# React Native specific rules
-keep class com.facebook.react.** { *; }
-keep class com.facebook.hermes.** { *; }
-keep class com.facebook.soloader.** { *; }
-keep class com.facebook.jni.** { *; }
-keep class com.facebook.debug.** { *; }
-keep class com.facebook.featureflags.** { *; }
# Add this for the specific library causing the issue
-keep class com.facebook.react.featureflags.** { *; }
# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}
# Keep JavaScript interface methods
-keepclassmembers class * {
    @com.facebook.react.uimanager.annotations.ReactProp <methods>;
    @com.facebook.react.uimanager.annotations.ReactPropGroup <methods>;
}
# HMS specific rules (your existing ones)
-keep class com.hianalytics.android.**{*;} 
-keep class com.huawei.updatesdk.**{*;} 
-keep class com.huawei.hms.**{*;}
-repackageclasses