# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\android-sdk/tools/proguard/proguard-android.txt
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

-dontwarn com.tendcloud.tenddata.**
-keep class com.tendcloud.** {*;}
-keep public class com.tendcloud.tenddata.** { public protected *;}
-keepclassmembers class com.tendcloud.tenddata.**{
public void *(***);
}
-keep class com.talkingdata.sdk.TalkingDataSDK {public *;}
-keep class com.apptalkingdata.** {*;}

##使用注解防止代码混淆
-keep,allowobfuscation @interface com.example.wenjunzhong.testnewfeature.annotation.NotProguard

-keep @com.example.wenjunzhong.testnewfeature.annotation.NotProguard class *
-keepclassmembers class * {
    @com.example.wenjunzhong.testnewfeature.annotation.NotProguard *;
}


##  release 版本不输出log being
## 注意：要这个功能生效必须把getDefaultProguardFile('proguard-android.txt') 改为getDefaultProguardFile('proguard-android-optimize.txt')
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static *** d(...);
    public static *** w(...);
    public static *** v(...);
    public static *** i(...);
}
##  release 版本不输出log end
