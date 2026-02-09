# RETROFIT https://square.github.io/retrofit/
# R8 full mode https://github.com/square/retrofit/issues/3751#issuecomment-1192043644
# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
# Keep classes for NewRelic instrumentation
# https://docs.newrelic.com/docs/mobile-monitoring/new-relic-mobile-android/install-configure/configure-proguard-or-dexguard-android-apps/#proguard-library
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
# To enable the workaround in the [RetrofitDynamicBaseUrlBinder]
-keep class retrofit2.Retrofit { *; }
-keep class okhttp3.HttpUrl { *; }

# With R8 full mode generic signatures are stripped for classes that are not
# kept. Suspend functions are wrapped in continuations where the type argument
# is used.
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# MOSHI https://github.com/square/moshi/tree/master?tab=readme-ov-file#r8--proguard

# OKHTTP https://square.github.io/okhttp/features/r8_proguard/
# Keep classes for NewRelic instrucmentation
# https://docs.newrelic.com/docs/mobile-monitoring/new-relic-mobile-android/install-configure/configure-proguard-or-dexguard-android-apps/#proguard-library
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**
# To enable the workaround in the [RequestInterceptor] for [NewRelic.addHTTPHeadersTrackingFor]
-keep class okhttp3.Headers { *; }

# Moshi's models https://medium0.com/androiddevelopers/practical-proguard-rules-examples-5640a3907dc9
-keep class com.jfc.template.core.network.request.** { *; }
-keep class com.jfc.template.core.network.response.** { *; }

# Add proguard for preRelease build type to avoid crash on Chucker logs item click
# Issue: https://github.com/ChuckerTeam/chucker/issues/749
# Gson proguard example: https://github.com/google/gson/blob/main/examples/android-proguard-example/proguard.cfg
