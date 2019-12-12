[![](https://jitpack.io/v/DeMonLiu623/DeMonStar.svg)](https://jitpack.io/#DeMonLiu623/DeMonStar)

# DeMonStar

一个集网络请求封装，列表优化，自定义View，通用工具类一体的简化开发成本的Android基础架构。    

### 使用

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```
dependencies {
	        implementation 'com.github.DeMonLiu623:DeMonStar:v1.0'
	}
```
#### 环境
1. 支持Java&Kotlin语言。  
2. AndroidX
3. minSdkVersion = 16
4. AndroidStudio 3.x

#### butterknife

如果是使用java开发需要使用butterknife，则需要在你的app.build.gradle中添加：  

```
android {
    ...
    compileOptions {
        targetCompatibility JavaVersion.VERSION_1_8
        sourceCompatibility JavaVersion.VERSION_1_8
    }
    }
    
    dependencies {
        implementation "com.jakewharton:butterknife:10.2.0"
        annotationProcessor "com.jakewharton:butterknife-compiler:10.2.0"
    }
```

#### permission

最少需要添加如下4个权限：  
```
 <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```


### 整体简介

|Library|说明|框架|
|-:-|-:-|-:-|
|BaseFrame|基础架构：MVP+Retrofit2.0+RxJava2.0，RxBus|Retrofit2.0，RxJava2.0|
|BaseList|列表优化：空视图，上拉加载更多，拖拽删除等|BaseRecyclerViewAdapterHelper|
|BasUI|自定义View：Dialog，PopWindow封装，ClearWriteEditText,CircleImageView等|Glide4.x，知乎matisse|
|BaseUtil|通用方法集合：des加密解密，文件操作，状态栏工具，Toasty封装等|Toasty|









