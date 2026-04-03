# 短信读取器 (SMS Reader)

一个简单的 Android 应用，演示如何使用 ContentResolver 读取系统短信数据库，并用 ListView 展示短信列表。

## 实验目的

1. 掌握利用内容解析者的方式访问系统短信数据库
2. 掌握利用 DDMS 模拟发送短信
3. 掌握查看虚拟机中文件的方式
4. 掌握 ListView 展示数据

## 核心代码

### 权限声明 (AndroidManifest.xml)
```xml
<uses-permission android:name="android.permission.READ_SMS" />
```

### 读取短信 (MainActivity.java)
```java
Uri smsUri = Uri.parse("content://sms/inbox");
Cursor cursor = getContentResolver().query(
    smsUri,
    new String[]{"address", "date", "body"},
    null, null, "date DESC"
);
```

## 构建和运行

### 方式一：GitHub Actions（推荐，无需本地配置）

1. **创建 GitHub 仓库**
   - 访问 [github.com/new](https://github.com/new)
   - 新建仓库名 `SmsReader`

2. **上传代码**
   ```bash
   cd c:\Users\m1507\Desktop\work\SmsReader
   git init
   git add .
   git commit -m "Initial commit"
   git branch -M main
   git remote add origin https://github.com/<YOUR_USERNAME>/SmsReader.git
   git push -u origin main
   ```

3. **自动构建**
   - 推送后，GitHub Actions 会自动编译
   - 在 Actions 标签页查看构建结果
   - 在 Artifacts 中下载 `app-debug.apk`

4. **安装到模拟器或真机**
   ```bash
   adb install -r app-debug.apk
   ```

### 方式二：本地构建（需要 Android SDK）

1. **设置 Android SDK 路径**
   ```
   修改 local.properties:
   sdk.dir=C:\Users\m1507\AppData\Local\Android\Sdk
   ```

2. **构建 APK**
   ```bash
   gradlew assembleDebug
   ```

3. **生成的 APK 位置**
   ```
   app\build\outputs\apk\debug\app-debug.apk
   ```

## 测试步骤

1. 在 Android 模拟器中安装 APK
2. 用 DDMS 或模拟器 Phone 应用发送模拟短信
3. 打开应用，查看短信列表
4. 点击短信查看详情

## 文件结构

```
SmsReader/
├── app/src/main/
│   ├── AndroidManifest.xml
│   ├── java/com/example/smsreader/
│   │   ├── MainActivity.java          # 主界面
│   │   ├── SmsInfo.java               # 短信数据类
│   │   └── SmsAdapter.java            # ListView 适配器
│   └── res/
│       └── layout/
│           ├── activity_main.xml      # 主布局
│           └── item_sms.xml           # 短信行布局
├── build.gradle                       # 项目配置
├── settings.gradle
├── gradlew                            # Gradle 包装脚本
└── .github/workflows/build.yml        # GitHub Actions 配置
```

## 权限说明

- `READ_SMS`：读取短信（Android 6.0+ 需动态申请）

## 注意事项

- 需要 API Level 21+ (Android 5.0)
- 模拟器需打开 SMS 功能
- 某些定制 ROM 可能限制短信访问

## 许可证

MIT
