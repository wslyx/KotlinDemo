plugins {
    kotlin("jvm") version "2.1.21"
}

group = "top.superyaxi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.9.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

// build.gradle.kts
tasks.wrapper {
    distributionUrl = "https://mirrors.aliyun.com/macports/distfiles/gradle/gradle-${gradleVersion}-bin.zip"
}
