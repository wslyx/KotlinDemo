1,
```text
// build.gradle.kts
tasks.wrapper {
    distributionUrl = "https://mirrors.aliyun.com/macports/distfiles/gradle/gradle-${gradleVersion}-bin.zip"
}
```
2,
```bash
.\gradlew wrapper --gradle-version 8.13  # 自动使用配置中的镜像，版本使用实际的版本
```
