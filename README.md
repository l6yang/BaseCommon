# BaseCommon
BaseLib<br>`[gson泛型(com.github.ikidou:TypeBuilder:1.0依赖问题)`](https://jitpack.io/#ikidou/TypeBuilder/1.0)
# 如何将自己的项目发布到Jcenter（Bintray）
<!--##### 一、准备工作-->
### 1、Bintray官网：https://bintray.com/
* 1.1、注册Bintray账号，最终需要通过这个账号发布到JCenter上。
![organization](https://github.com/l6yang/BaseCommon/blob/master/images/organization.png?raw=true)
`这里有一个坑，现在这个地址默认是注册组织的，注册后会有一个月试用期（推荐注册使用个人类型账户）。`
<br>`个人类型注册地址：`https://bintray.com/signup/oss
<br>`个人类型注册地址：`https://bintray.com/signup/oss
<br>`个人类型注册地址：`https://bintray.com/signup/oss
![personal](https://github.com/l6yang/BaseCommon/blob/master/images/personal.png?raw=true)
<br>这里是没有填写组织名称的。可以使用第三方注册，常用的就是 github账号，需要注意：**如果github账号绑定的邮箱不是Gmail邮箱，Bintray无法注册成功**。（本人用的是Gmail邮箱，其他邮箱不知是否可用，若嫌麻烦Gmail麻烦，可以试一下）。
<br>[`Gmail邮箱注册和登陆参考链接（亲测可用）`](https://jingyan.baidu.com/article/36d6ed1f63b9831bce48837f.html)
* 1.2、注册成功后进入主页面，鼠标移至右上角用户名处，在弹出的对话框中点击**Edit Profile**，然后在左边的菜单栏点击**API Key**，再次输入密码即可看到你的API Key，点击show旁边的copy图标，即可将API Key复制到粘贴板。流程如下图：<br>
![api_key](https://github.com/l6yang/BaseCommon/blob/master/images/api_key.png?raw=true) 
<br>**先将copy的API Key粘贴在文本文档中（项目中的local.properties也可，可参考APK签名配置），稍后要用到**
* 1.3、创建Maven仓库
主页面左边点击**Add New Repository（不是Add New Organization，不要点错）**，如图
![maven](https://github.com/l6yang/BaseCommon/blob/master/images/maven.png?raw=true) 
### 2、GitHub官网：（此步可忽略，不推荐）https://github.com/
[`可参考此篇文章`](http://blog.csdn.net/p10010/article/details/51336332)
### 3、项目配置
* 3.1、在你的build.gradle（注意是`Project中的build.gradle`）
<br>`加入此行代码`：classpath 'com.novoda:bintray-release:0.5.0'
<br>![build.gradle](https://github.com/l6yang/BaseCommon/blob/master/images/build.png?raw=true)
* 3.2、`此外若项目中有中文（注释或说明）,需加以下代码（同样在Project中的build.gradle）`
<br>tasks.withType(Javadoc) {
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;options {
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;encoding "UTF-8"
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;charSet 'UTF-8'
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;links "http://docs.oracle.com/javase/7/docs/api"
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>}
<br>![utf8](https://github.com/l6yang/BaseCommon/blob/master/images/utf8.png?raw=true)
* 3.3、`Library配置（library工程的build.gradle）`
<br>`加入以下配置：`
<br>apply plugin: 'com.novoda.bintray-release'
<br>publish {
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;repoName = 'base'           //bintray仓库名
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;userOrg = 'l6yang'           //bintray注册的用户名
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;groupId = 'com.loyal'            //compile引用时的第1部分groupId
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;artifactId = 'baseCommon'       //compile引用时的第2部分项目名
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;publishVersion = '1.0.0'       //compile引用时的第3部分版本号
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;desc = 'This is a baseLib for extents'//描述（可不写）
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;website = 'https://github.com/l6yang/BaseCommon' //github项目地址（可不写）
}
<br>`完整配置如下图：`
![config](https://github.com/l6yang/BaseCommon/blob/master/images/config.png?raw=true)
### 4、发布项目
在Android Studio的terminal或者cmd（注意要先进入工程根目录）中，执行命令
<br>***MAC***
<br>./gradlew clean build bintrayUpload -PbintrayUser=BINTRAY_USERNAME -PbintrayKey=BINTRAY_KEY -PdryRun=false
<br>***WINDOWS***
<br>gradlew clean build bintrayUpload -PbintrayUser=BINTRAY_USERNAME -PbintrayKey=BINTRAY_KEY -PdryRun=false
<br>其中BINTRAY_USERNAME是账号名，BINTRAY_KEY是API KEY
<br>如果最后显示*BUILD SUCCESSFUL*，表示发布成功了
<br>打开创建的仓库，就可以看到我们上传的Library（依次是base[仓库名称]-->baseCommon[项目名]-->新页面中右手边找到**Add to JCenter**），填写说明，并提交审核，就可以静静的等待了，基本上几个小时就可以通过。
### 4、提示
* 4.1、在配置信息的时候一定要注意Gradle与Bintray版本对应，否则会导致所有步骤都没有问题，并且提交JCenter审核也通过了，就是引用不到库。
* 4.2、依赖使用
<br>等待审核通过后，将其引用到项目中即可。
<br>compile（implementation） 'com.loyal:baseCommon:1.0.0'
# --以上就是如何将自己的项目发布到JCenter--
**2018年1月26日**
