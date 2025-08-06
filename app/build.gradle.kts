plugins {
    id("com.android.application")
}

android {
    namespace = "com.map.hunter"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.map.hunter"
        minSdk = 23
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        viewBinding = true
    }

    signingConfigs {
        create("release") {
            storeFile = file("map.p12")
            storePassword = "1234"
            keyAlias = "Hussein"
            keyPassword = "1234"
            storeType = "pkcs12"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }

    kotlin {
        jvmToolchain(17)
    }

    lint {
        disable += "MissingTranslation"
        checkReleaseBuilds = false
        abortOnError = false
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-Xlint:deprecation")
}

repositories {
    google()
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.browser:browser:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("commons-io:commons-io:2.6")
    implementation("com.github.franmontiel:LocaleChanger:0.9.2")
    implementation("com.github.skydoves:powermenu:2.1.8")
}
