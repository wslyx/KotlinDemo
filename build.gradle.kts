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
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

// build.gradle
tasks.wrapper {
    distributionUrl = "https://mirrors.aliyun.com/macports/distfiles/gradle/gradle-${gradleVersion}-bin.zip"
}
