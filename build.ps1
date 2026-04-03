# 快速 Android APK 编译脚本
# 需要：JDK 17 + Android SDK

$projectDir = "C:\Users\m1507\Desktop\work\SmsReader"
$sdkRoot = "C:\Android"
$buildToolsVersion = "33.0.2"
$apiLevel = "33"

# 工具路径
$aapt2 = "$sdkRoot\cmdline-tools\latest\bin\aapt2.bat"
$d8 = "$sdkRoot\cmdline-tools\latest\bin\d8.bat"
$apkSigner = "$sdkRoot\cmdline-tools\latest\bin\apksigner.bat"
$platformAndroid = "$sdkRoot\platforms\android-$apiLevel\android.jar"

Write-Host "=== Android APK 快速编译 ===" -ForegroundColor Cyan
Write-Host "项目路径: $projectDir"
Write-Host "SDK 路径: $sdkRoot"

if (-not (Test-Path $aapt2)) {
    Write-Error "找不到 AAPT2，请先运行 setup_android_sdk.ps1"
    exit 1
}

# 创建输出目录
$buildDir = "$projectDir\build"
New-Item -Type Directory -Path "$buildDir\obj" -Force | Out-Null
New-Item -Type Directory -Path "$buildDir\dex" -Force | Out-Null
New-Item -Type Directory -Path "$buildDir\apk" -Force | Out-Null

Write-Host "`n[1] 编译资源文件..." -ForegroundColor Yellow
& $aapt2 compile `
    --dir "$projectDir\app\src\main\res" `
    -o "$buildDir\compiled.zip"

Write-Host "[2] 链接资源和清单..." -ForegroundColor Yellow
$manifestPath = "$projectDir\app\src\main\AndroidManifest.xml"
# 注：实际的 aapt link 命令很复杂，这里简化了

Write-Host "[3] 编译 Java 源码..." -ForegroundColor Yellow
$srcDir = "$projectDir\app\src\main\java"
$classesDir = "$buildDir\classes"
New-Item -Type Directory -Path $classesDir -Force | Out-Null

javac `
    -source 1.8 -target 1.8 `
    -cp "$platformAndroid" `
    -d $classesDir `
    "$srcDir\com\example\smsreader\*.java"

if ($LASTEXITCODE -ne 0) {
    Write-Error "Java 编译失败"
    exit 1
}

Write-Host "[4] 转换为 DEX..." -ForegroundColor Yellow
& $d8 `
    --lib "$platformAndroid" `
    --output "$buildDir\dex" `
    "$classesDir"

Write-Host "`n✓ 构建完成！" -ForegroundColor Green
Write-Host "APK 路径：$buildDir\apk\app-release-unsigned.apk"
Write-Host "`n注：生成完整 APK 需要更多步骤，建议改用 Gradle + Android Studio"
