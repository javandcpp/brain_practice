# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Applications/adt-bundle-mac-x86_64-20140702/sdk/tools/proguard/proguard-android.txt
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

# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Applications/adt-bundle-mac-x86_64-20140702/sdk/tools/proguard/proguard-android.txt
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
#----------------------------------------------------loneswordman----------------------------------------------------
-verbose
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontpreverify
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontoptimize
-ignorewarnings

-dontobfuscate

-dontwarn android.support.**
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keepattributes Signature
-keepattributes *Annotation*

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}
#Activity
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
#enum
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#reflect
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keep public class * implements java.io.Serializable{
 public protected private *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}


-keep public class javax.**
-keep public class android.webkit.**
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application {*;}
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference

-keep class android.support.v4.view.** {*;}
-keep class com.lidroid.**{*;}
-keep class * extends java.lang.annotation.Annotation {*;}
-keep class com.google.gson.** {*;}
-keep class com.nineoldandroids.**{*;}
-keep class com.nostra13.universalimageloader.**{*;}
-keep class com.readystatesoftware.**{*;}
-keep class android.support.design.** {*;}

-dontwarn de.greenrobot.**
-keep class de.greenrobot.** {*;}

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-keep public class com.wlibao.entity.**{*;}
-keep public class * extends android.widget.BaseAdapter {*;}

-keepclasseswithmembers public class com.yzk.yiliaoapp.activity.**{*;}
-keepclasseswithmembers public class com.yzk.yiliaoapp.im.activity.**{*;}
-keepclasseswithmembers public class com.yzk.yiliaoapp.fragment.**{*;}
-keepclasseswithmembers public class com.yzk.yiliaoapp.im.fragment.**{*;}


-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#----------------------------------------------------loneswordman----------------------------------------------------





#-------极光推送混淆-----------

-dontwarn cn.jpush.**
-keepattributes  EnclosingMethod,Signature
-keep class cn.jpush.** { *; }
-keepclassmembers class ** {
    public void onEvent*(**);
}


#------volley混淆--------
-keep class com.android.volley.** {*;}
-keep class com.android.volley.toolbox.** {*;}
-keep class com.android.volley.Response$* { *; }
-keep class com.android.volley.Request$* { *; }
-keep class com.android.volley.RequestQueue$* { *; }
-keep class com.android.volley.toolbox.HurlStack$* { *; }
-keep class com.android.volley.toolbox.ImageLoader$* { *; }





-keep class com.hp.hpl.sparta.** {*;}
-dontwarn com.hp.hpl.sparta.**
-keep class net.sourceforge.pinyin4j.** {*;}
-dontwarn net.sourceforge.pinyin4j.**
-keep class bolts.* {*;}
-dontwarn bolts.**
-keep class com.parse.** {*;}
-dontwarn com.parse.**
-keep class com.hsg.sdk.common.** {*;}
-dontwarn com.hsg.sdk.common.**

#---------gSON--------
-keepattributes Signature
-keep class sun.misc.Unsafe {*;}
-keep class com.google.gson.examples.android.model.** {*;}

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {*;
}
# EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {*;}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }


# Facebook
-keep class com.facebook.** {*;}
-keep interface com.facebook.** {*;}
-keep enum com.facebook.** {*;}


-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}


#svg
-keepclasseswithmembers class com.caverock.androidsvg.** {*;}
-keep class com.caverock.** {*;}
-dontwarn com.caverock.**




