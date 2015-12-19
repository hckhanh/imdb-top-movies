# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/hckhanh/Android/Sdk/tools/proguard/proguard-android.txt
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

-dontwarn java.beans.**
-dontwarn org.apache.http.**
-dontwarn com.squareup.okhttp.**
-dontwarn com.fasterxml.jackson.**
-dontwarn org.simpleframework.xml.**
-dontwarn org.springframework.core.convert.support.**

-keepclassmembers class com.demo.imdb.top.movies.data.** {
    <fields>;
}

-keepattributes Signature
-keepattributes InnerClasses

-dontwarn
-dontnote