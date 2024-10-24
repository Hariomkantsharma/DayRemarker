plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.haridroid.dayremarker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.haridroid.dayremarker"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.appcompat)
    implementation(libs.recyclerview)
    implementation(libs.cardview)

    // Room components
    implementation (libs.androidx.room.runtime)

    // Room Compiler (for annotation processing)
    annotationProcessor (libs.room.compiler)

    // For Kotlin projects, use kapt instead of annotationProcessor
//    kapt( libs.room.compiler.v252)

    // Optional: Room Kotlin Extensions and Coroutines support
    implementation (libs.room.ktx)
    implementation(libs.kotlinx.coroutines.android)
    // Optional: Room Paging support (if you're using Paging Library)
    implementation (libs.androidx.room.paging)



}