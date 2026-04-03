# 初始化 Gradle Wrapper
$projectDir = "C:\Users\m1507\Desktop\work\SmsReader"
$wrapperDir = "$projectDir\gradle\wrapper"

# 创建目录
New-Item -Type Directory -Path $wrapperDir -Force | Out-Null

# 创建 gradle-wrapper.properties
$properties = @"
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.0-bin.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
"@

Set-Content -Path "$wrapperDir\gradle-wrapper.properties" -Value $properties

# 创建 gradlew.bat (Windows 批处理脚本)
# 这是一个简化版本，实际使用需要完整的 wrapper jar

Write-Host "✓ gradle-wrapper.properties 已创建"
Write-Host ""
Write-Host "下一步需要下载 gradle-wrapper.jar："
Write-Host "1. 访问: https://raw.githubusercontent.com/gradle/gradle/master/gradle/wrapper/gradle-wrapper.jar"
Write-Host "2. 保存到: $wrapperDir\gradle-wrapper.jar"
Write-Host ""
Write-Host "或者，下载完整的 Gradle Wrapper 文件包并解压..."
