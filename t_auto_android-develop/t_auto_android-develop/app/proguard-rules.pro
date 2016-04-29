# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Volumes/data/devel/android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-libraryjars libs
-keep class android.support.v7.app.** { *; }
-keep interface android.support.v7.app.** { *; }

#keep javascriptInterface : added 2016-04-18
-keepclassmembers class * implements com.sktelecom.tdrive$JavaScriptInterface {
      <methods>;
}
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-dontwarn com.mixpanel.**

-keep class com.mixpanel.android.abtesting.** { *; }
-keep class com.mixpanel.android.mpmetrics.** { *; }
-keep class com.mixpanel.android.surveys.** { *; }
-keep class com.mixpanel.android.util.** { *; }
-keep class com.mixpanel.android.java_websocket.** { *; }

-keepattributes InnerClasses

-keep class **.R
-keep class **.R$* {
<fields>;
}

# for okhttp3
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# for okio
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

-dontwarn org.mozilla.javascript.tools.**

# for gson
-keep class com.google.gson.annotations.SerializedName { *; }
-keep class com.google.gson.annotations.Expose { *; }

# for stetho
-dontwarn com.facebook.stetho.**

# for network data
-keepclassmembers class * { @com.google.gson.annotations.SerializedName <fields>; }
-keepclassmembers class * { @com.google.gson.annotations.Expose <fields>; }

