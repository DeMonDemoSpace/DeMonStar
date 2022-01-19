[![](https://jitpack.io/v/DeMonLiu623/DeMonStar.svg)](https://jitpack.io/#DeMonLiu623/DeMonStar)

# DeMonStar

一个集网络请求封装，列表优化，自定义View，通用工具类一体的简化开发成本的Android基础架构。    

#### Development Environment
1. 支持Java&Kotlin语言。  
2. AndroidX
3. minSdkVersion = 16
4. AndroidStudio 3.x

#### Butterknife

如果是使用java开发需要使用butterknife，则需要在你的app/build.gradle中添加：  

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

#### Permission

最少需要添加如下4个权限：  
```
 <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```


### Introduction

|Library|说明|所含框架|  
|--|--|--|  
|BaseFrame|基础架构：MVP+Retrofit2.0+RxJava2.0，RxBus|okhttp3,Retrofit2.0,RxJava2.0,rxlifecycle,Gson|  
|BaseList|列表优化：空视图，上拉加载更多，拖拽删除等|BaseRecyclerViewAdapterHelper|  
|BaseUI|自定义View：Dialog，PopWindow封装，ClearWriteEditText,CircleImageView等|Glide4.x，知乎matisse|  
|BaseUtil|通用方法集合：des加密解密，文件操作，状态栏工具，Toasty封装等|Toasty|  


### More

请看demo代码。

### Bugs or Questions

1. issues
2. email:757454343@qq.com

### MIT LICENSE

```
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```








