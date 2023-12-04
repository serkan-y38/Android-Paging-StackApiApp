plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    jvmToolchain(17)
}

android {
    namespace = "com.yilmaz.stackapiapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yilmaz.stackapiapp"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.compose.material3:material3-window-size-class:1.1.2")
    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")

    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-android-compiler:2.45")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.4")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Retrofit - okhttp
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    // KotlinX Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    // Kotlin coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    // Room
    implementation("androidx.room:room-runtime:2.6.0")
    annotationProcessor("androidx.room:room-compiler:2.6.0")
    //noinspection KaptUsageInsteadOfKsp
    kapt("androidx.room:room-compiler:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")
    implementation("androidx.room:room-paging:2.6.0")
    testImplementation("androidx.room:room-testing:2.6.0")

    // Paging
    implementation("androidx.paging:paging-compose:3.3.0-alpha02")
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")
    testImplementation("androidx.paging:paging-common-ktx:3.2.1")

    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}