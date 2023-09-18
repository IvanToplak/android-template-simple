plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.github.ben-manes.versions")
    id("org.jlleitschuh.gradle.ktlint")
//    id("com.google.gms.google-services")
//    id("com.google.firebase.crashlytics")
}

android {
    namespace = "hr.from.ivantoplak.pokemonapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "hr.from.ivantoplak.pokemonapp"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "0.0.1"
        base.archivesBaseName = "android-template-v$versionName"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("Boolean", "ENABLE_ANALYTICS", "false")
        buildConfigField("Boolean", "ENABLE_CRASHLYTICS", "false")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
        }
        release {
            // debug signing config is set for easier testing - CI should set the production one
            signingConfig = signingConfigs.getByName("debug")

            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "retrofit.pro",
            )
        }
    }

    flavorDimensions.add("default")
    productFlavors {
        create("dev") {
            dimension = "default"
            manifestPlaceholders["appName"] = "@string/app_name_dev"
            applicationIdSuffix = ".dev"
        }
        create("qa") {
            dimension = "default"
            manifestPlaceholders["appName"] = "@string/app_name_qa"
            applicationIdSuffix = ".qa"
            buildConfigField("Boolean", "ENABLE_CRASHLYTICS", "true")
        }
        create("prod") {
            dimension = "default"
            manifestPlaceholders["appName"] = "@string/app_name"
            buildConfigField("Boolean", "ENABLE_ANALYTICS", "true")
            buildConfigField("Boolean", "ENABLE_CRASHLYTICS", "true")
        }
    }

    val javaVersionString = "17"
    val javaVersion = JavaVersion.VERSION_17
    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    kotlin {
        jvmToolchain(jdkVersion = javaVersionString.toInt())
    }

    kotlinOptions {
        jvmTarget = javaVersionString
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlin.RequiresOptIn",
            // Enable experimental Compose animation APIs
            "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
            // Enable experimental coroutines APIs, including Flow
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.coroutines.FlowPreview",
        )
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        val compose_compiler_version: String by project
        kotlinCompilerExtensionVersion = compose_compiler_version
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    applicationVariants.all { variant ->
        variant.outputs.all {
            logger.quiet("\t${variant.name} version:\t${base.archivesName}")
        }
        true
    }
}

dependencies {
    val accompanist_version: String by project
    val activity_compose_version: String by project
    val activity_ktx_version: String by project
    val android_test_rules_version: String by project
    val android_test_version: String by project
    val appcompat_version: String by project
    val coil_version: String by project
    val collections_immutable_version: String by project
    val compose_bom_version: String by project
    val constraintlayout_compose_version: String by project
    val core_ktx_version: String by project
    val core_testing_version: String by project
    val coroutine_version: String by project
    val datastore_version: String by project
    val junit_version: String by project
    val kotlin_version: String by project
    val koin_version: String by project
    val koin_android_version: String by project
    val koin_android_compose_version: String by project
    val lifecycle_version: String by project
    val material_version: String by project
    val mockito_version: String by project
    val mockito_inline_version: String by project
    val navigation_compose_version: String by project
    val splashscreen_version: String by project
    val timber_version: String by project
    val moshi_version: String by project
    val retrofit_version: String by project
    val room_version: String by project

    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:$collections_immutable_version")

    // Android
    implementation("androidx.activity:activity-ktx:$activity_ktx_version")
    implementation("androidx.appcompat:appcompat:$appcompat_version")
    implementation("androidx.core:core-ktx:$core_ktx_version")
    implementation("com.google.android.material:material:$material_version")
    implementation("androidx.core:core-splashscreen:$splashscreen_version")

    // Compose
    val composeBom = platform("androidx.compose:compose-bom:$compose_bom_version")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material3:material3-window-size-class")
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.activity:activity-compose:$activity_compose_version")
    implementation("androidx.constraintlayout:constraintlayout-compose:$constraintlayout_compose_version")

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanist_version")
    implementation("com.google.accompanist:accompanist-permissions:$accompanist_version")

    // Lifecycle dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycle_version")

    // Koin
    implementation("io.insert-koin:koin-android:$koin_android_version")
    implementation("io.insert-koin:koin-androidx-compose:$koin_android_compose_version")
    testImplementation("io.insert-koin:koin-test:$koin_version")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine_version")

    // Coil
    implementation("io.coil-kt:coil-compose:$coil_version")

    // Navigation
    implementation("androidx.navigation:navigation-compose:$navigation_compose_version")

    // Room
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:$datastore_version")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit_version")

    // Moshi
    implementation("com.squareup.moshi:moshi:$moshi_version")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:$moshi_version")

    // Logging
    implementation("com.jakewharton.timber:timber:$timber_version")

    // Google services
//    implementation(platform("com.google.firebase:firebase-bom:$firebase_bom_version"))
//    implementation("com.google.firebase:firebase-analytics-ktx")
//    implementation("com.google.firebase:firebase-crashlytics-ktx")

    // Test
    // Unit testing framework
    androidTestImplementation("androidx.test:rules:$android_test_rules_version")
    androidTestImplementation("androidx.test:runner:$android_test_version")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    testImplementation("androidx.test.ext:junit:$junit_version")
    androidTestImplementation("androidx.test.ext:junit:$junit_version")

    // Mockito
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockito_version")
    testImplementation("org.mockito:mockito-inline:$mockito_inline_version")

    // Test Coroutines
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutine_version")

    testImplementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
    testImplementation("androidx.arch.core:core-testing:$core_testing_version")
}
