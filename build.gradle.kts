val accompanist_version by extra("0.32.0")
val activity_compose_version by extra("1.7.2")
val activity_ktx_version by extra("1.7.2")
val android_test_rules_version by extra("1.5.0")
val android_test_version by extra("1.5.2")
val appcompat_version by extra("1.6.1")
val coil_version by extra("2.4.0")
val collections_immutable_version by extra("0.3.5")
val compose_bom_version by extra("2023.09.00")
val constraintlayout_compose_version by extra("1.0.1")
val compose_compiler_version by extra("1.5.3")
val core_ktx_version by extra("1.12.0")
val core_testing_version by extra("2.2.0")
val coroutine_version by extra("1.7.3")
val crashlytics_plugin_version by extra("2.9.4")
val datastore_version by extra("1.0.0")
val dependency_update_version by extra("0.48.0")
val firebase_bom_version by extra("31.3.0")
val google_services_version by extra("4.3.15")
val junit_version by extra("1.1.5")
val kotlin_version by extra("1.9.10")
val koin_version by extra("3.5.0")
val koin_android_version by extra("3.5.0")
val koin_android_compose_version by extra("3.5.0")
val ksp_version by extra("1.9.10-1.0.13")
val lifecycle_version by extra("2.6.2")
val material_version by extra("1.9.0")
val mockito_version by extra("5.1.0")
val mockito_inline_version by extra("5.2.0")
val navigation_compose_version by extra("2.7.2")
val splashscreen_version by extra("1.0.1")
val timber_version by extra("5.0.1")
val moshi_version by extra("1.15.0")
val retrofit_version by extra("2.9.0")
val room_version by extra("2.5.2")

plugins {
    val gradle_version = "8.1.1"
    val kotlin_version = "1.9.10"
    val ksp_version = "1.9.10-1.0.13"
    val dependency_update_version = "0.48.0"
    val ktlintVersion = "11.5.1"

    id("com.android.application") version "$gradle_version" apply false
    id("com.android.library") version "$gradle_version" apply false
    id("org.jetbrains.kotlin.android") version "$kotlin_version" apply false
    id("com.google.devtools.ksp") version "$ksp_version" apply false
    id("com.github.ben-manes.versions") version "$dependency_update_version" apply false
    id("org.jlleitschuh.gradle.ktlint") version "$ktlintVersion" apply false

//    id "com.google.gms.google-services" version "$google_services_version" apply false
//    id "com.google.firebase.crashlytics" version "$crashlytics_plugin_version" apply false
}
