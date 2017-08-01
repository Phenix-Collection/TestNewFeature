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

##使用注解防止代码混淆 begin
-keep,allowobfuscation @interface com.example.wenjunzhong.testnewfeature.annotation.NotProguard

-keep @com.example.wenjunzhong.testnewfeature.annotation.NotProguard class *
-keepclassmembers class * {
    @com.example.wenjunzhong.testnewfeature.annotation.NotProguard *;
}
##使用注解防止代码混淆  end


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

##这条规则配置特别强大，它可以把你的代码以及所使用到的各种第三方库代码统统移动到同一个包下，
##可能有人知道这条配置，但仅仅知道它还不能发挥它最大的作用，默认情况下，你只要在 rules 文件中写上 -repackageclasses 这一行代码就可以了，
##它会把上述的代码文件都移动到根包目录下，即在 / 包之下
-repackageclasses

#指定压缩级别
-optimizationpasses 5

#不跳过非公共的库的类成员
-dontskipnonpubliclibraryclassmembers

#把混淆类中的方法名也混淆了
-useuniqueclassmembernames



