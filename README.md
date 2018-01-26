# BaseCommon
BaseLib
# 如何利用Android Studio将自己的项目发布到Jcenter（Bintray）
<!--##### 一、准备工作-->
### 1、Bintray官网：https://bintray.com/
注册Bintray账号，最终需要通过这个账号发布到JCenter上。
![organization](https://github.com/l6yang/BaseCommon/blob/master/images/organization.png?raw=true)
`这里有一个坑，现在这个地址默认是注册组织的，注册后会有一个月试用期（推荐注册使用个人类型账户）。`
<br>`个人类型注册地址：`https://bintray.com/signup/oss
<br>`个人类型注册地址：`https://bintray.com/signup/oss
<br>`个人类型注册地址：`https://bintray.com/signup/oss
![personal](https://github.com/l6yang/BaseCommon/blob/master/images/personal.png?raw=true)
<br>这里是没有填写组织名称的。可以使用第三方注册，常用的就是 github账号，需要注意：如果github账号绑定的邮箱不是gmail邮箱，Bintray无法注册成功。所以这个时候你需要完成第一步然后通过gmail邮箱完成注册。
<br>[`Gmail邮箱注册和登陆参考链接（亲测可用）`](https://jingyan.baidu.com/article/36d6ed1f63b9831bce48837f.html)
### 2、GitHub官网：（此步可忽略，不推荐忽略）https://github.com/
[`可参考此篇文章`](http://blog.csdn.net/p10010/article/details/51336332)
### 3、项目配置
在你的build.gradle（注意是`Project中的build.gradle`）加入下面一行代码
![build.gradle](https://github.com/l6yang/BaseCommon/blob/master/images/build.png?raw=true)
<br>`classpath 'com.novoda:bintray-release:0.5.0'`
<br>`此外若项目中有中文（注释及说明）,需加以下代码`
![utf8](https://github.com/l6yang/BaseCommon/blob/master/images/utf8.png?raw=true)
<br>　　`tasks.withType(Javadoc) {`
<br>　　　　`options {`
<br>　　　　　　`encoding "UTF-8"`
<br>　　　　　　`charSet 'UTF-8'`
<br>　　　　　　`links "http://docs.oracle.com/javase/7/docs/api"`
<br>　　　　`}`
<br>　　`}`
<br>

