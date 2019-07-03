
```
maven { url "https://jitpack.io" }

implementation 'com.github.XSation.AgileUtils:limitapp:master-SNAPSHOT'
implementation 'com.github.XSation.AgileUtils:netutils:master-SNAPSHOT'
implementation 'com.github.XSation.AgileUtils:dbutils:master-SNAPSHOT'
implementation 'com.github.XSation.AgileUtils:common:master-SNAPSHOT'
```


- common
    - AppUtils application中初始化
    - DisplayUtils
    - KeyboardUtils
    - MD5Utils
    - SpUtils
    - ToastUtils
    - AssetsUtils
    - FileUtils
- netutils
    - common
    - api 'com.squareup.okhttp3:okhttp:3.14.2'
    - api 'com.google.code.gson:gson:2.8.5'
    - api 'com.facebook.fresco:fresco:1.13.0'
    - NetUtil 提供各种封装好的网络
- limitapp
    - netutils
    - limitutils

- dbutils
    - api 'org.litepal.android:core:1.6.1'
