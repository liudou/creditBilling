-optimizationpasses 5 
-dontusemixedcaseclassnames 
-dontskipnonpubliclibraryclasses 
-dontpreverify 
-verbose 
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/* 
-keep public class * extends android.app.Activity 
-keep public class * extends android.app.Application 
-keep public class * extends android.app.Service 
-keep public class * extends android.content.BroadcastReceiver 
-keep public class * extends android.content.ContentProvider 
-keep public class com.android.vending.licensing.ILicensingService
-keep class com.speedbird.pay2b.listener.**{*;}
-keep class com.speedbird.pay2b.SBPay2BManager{*;}
-keep class com.speedbird.pay2b.client.**{*;}
-keep class com.speedbird.pay2b.ui.**{*;}
-keepclasseswithmembernames class * { 
    native <methods>; 
} 
-keepclasseswithmembernames class * { 
    public <init>(android.content.Context, android.util.AttributeSet); 
} 
-keepclasseswithmembernames class * { 
    public <init>(android.content.Context, android.util.AttributeSet, int); 
} 
-keepclassmembers enum * { 
    public static **[] values(); 
    public static ** valueOf(java.lang.String); 
} 
-keep class * implements android.os.Parcelable { 
  public static final android.os.Parcelable$Creator *; 
} 