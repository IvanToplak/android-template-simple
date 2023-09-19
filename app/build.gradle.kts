plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dependency.update)
    alias(libs.plugins.ktlint)
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
        val composeCompilerVersion = libs.versions.androidxComposeCompiler.get().toString()
        kotlinCompilerExtensionVersion = composeCompilerVersion
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
    // Kotlin
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.collections.immutable)

    // Android
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.android.material)
    implementation(libs.androidx.core.splashscreen)

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.material.iconsCore)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.constraintlayout.compose)

    // Android Studio Preview support
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Accompanist
    implementation(libs.accompanist.permissions)

    // Lifecycle dependencies
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    testImplementation(libs.koin.test)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Coil
    implementation(libs.coil.kt.compose)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    // DataStore
    implementation(libs.androidx.dataStore.preferences)

    // Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.moshi)

    // Moshi
    implementation(libs.moshi)
    ksp(libs.moshi.kotlin.codegen)

    // Logging
    implementation(libs.timber)

    // Test
    // Unit testing framework
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.compose.ui.testManifest)

    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.ext)

    // Mockito
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.inline)

    // Test Coroutines
    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.kotlin.stdlib)
    testImplementation(libs.kotlin.reflect)
    testImplementation(libs.androidx.core.testing)
}
