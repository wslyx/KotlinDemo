pluginManagement {
    repositories {
        mavenLocal()
        maven("https://maven.aliyun.com/repository/public")
        maven("https://mirrors.huaweicloud.com/repository/maven/")
        maven("https://mirrors.tuna.tsinghua.edu.cn/maven/repos/public")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    // repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        maven("https://maven.aliyun.com/repository/public")
        maven("https://mirrors.huaweicloud.com/repository/maven/")
        maven("https://mirrors.tuna.tsinghua.edu.cn/maven/repos/public")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        mavenCentral()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "KotlinDemo"
