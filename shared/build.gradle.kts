plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

version = "1.0.0"

android {
    compileSdk = BuildConfig.compileSdkVersion

    defaultConfig {
        minSdk = BuildConfig.minSdkVersion
        targetSdk = BuildConfig.targetSdkVersion
        resourceConfigurations += listOf("en", "ja")
    }

    buildFeatures {
        buildConfig = false
    }

    buildTypes {
        getByName("release") {
            consumerProguardFiles("consumer-rules.pro")
        }
    }

    // Workaround for https://youtrack.jetbrains.com/issue/KT-43944
    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}

kotlin {
    android()
    ios()

    cocoapods {
        // Configure fields required by CocoaPods.
        summary = "Shared module for the app."
        homepage = "https://github.com/droibit/kotlin-multiplatform-mobile-template"
        authors = "Shinya Kumagai"
        license = "Apache License, Version 2.0"
        frameworkName = "Shared"

        ios.deploymentTarget = "14.0"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.Coroutines.core)
                implementation(Deps.Stately.common)

                implementation(Deps.Komol.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(Deps.Test.Kotlin.common)
                implementation(Deps.Test.Kotlin.annotationsCommon)
            }
        }
        val androidMain by getting
        val androidTest by getting {
            dependencies {
                implementation(Deps.Test.junit)
                implementation(Deps.Test.Kotlin.junit)
            }
        }
        val iosMain by getting
        val iosTest by getting
    }
}
