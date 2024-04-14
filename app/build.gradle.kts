plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.androidx.navigation.safeargs.ktx)

}

android {
    namespace = "com.internshipavito"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.internshipavito"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.material)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.squareup.retrofit2.retrofit)
    implementation(libs.squareup.okhttp3)
    implementation(libs.squareup.retrofit2.moshi.converter)
    implementation(libs.squareup.retrofit2.moshi)
    implementation(libs.jetbrains.coroutines.core)
    implementation(libs.jetbrains.coroutines.android)
    implementation(libs.facebook.fresco)
    implementation(libs.androidx.paging.common.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.paging.runtime.ktx)

    kapt(libs.androidx.room.compiler)
    kapt(libs.square.moshi.kotlin.codegen)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}