import DesktopSettings.DIRECTORY_MAIN_CLASS
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm(name = "desktop")
    
    sourceSets {
        val desktopMain by getting
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.androidx.compose)
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.bundles.ktor)
            implementation(libs.datastore)
            implementation(libs.datastore.preferences)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.koin.core)
            implementation(libs.navigation.compose)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.ktor.client.okhttp)
        }
    }
}

android {
    namespace = SettingsDefault.PACKAGE_NAME
    compileSdk = AndroidSettings.ACTUAL_COMPILE_SDK
    defaultConfig {
        applicationId = SettingsDefault.PACKAGE_NAME
        minSdk = AndroidSettings.ACTUAL_MIN_SDK
        targetSdk = AndroidSettings.ACTUAL_TARGET_SDK
        versionCode = AndroidSettings.ACTUAL_VERSION_CODE
        versionName = AndroidSettings.ACTUAL_VERSION_ANDROID
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = DIRECTORY_MAIN_CLASS
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = SettingsDefault.PACKAGE_NAME
            packageVersion = DesktopSettings.ACTUAL_VERSION_DESKTOP
            windows {
                iconFile.set(project.file("src/desktopMain/resources/icons/coding-tv.ico"))
            }
        }
    }
}

tasks.register("generateVersion") {
    doLast {
        val versionFile = file("src/desktopMain/kotlin/br/com/conding/tv/resources/DesktopVersion.kt")
        versionFile.writeText(
            """
            package br.com.conding.tv.resources

            internal object DesktopVersion {
                const val NUMBER_VERSION = "${DesktopSettings.ACTUAL_VERSION_DESKTOP}"
            }
            
            """.trimIndent()
        )
    }
}

tasks.getByName("compileKotlinDesktop").dependsOn("generateVersion")
