<h1 align="center"><a href="https://www.jeebud.com" target="_blank"> Jeebud </a></h1>

<p align="center">
一款基于SpringBoot2.X后台管理系统
</p>
<p align="center">
<img alt="license" src="https://img.shields.io/github/license/itfuyun/jeebud?color=blue&style=flat-square"/>
</p>

> Jeebud 可以看作Jee+bud的组合，JEE代表企业级，而bud英文意思为嫩芽，因此Jeebud象征着企业级开发的一株嫩芽，企业或个人可以使用Jeebud快速实现自己业务，Jeebud致力于做简洁的后台开发脚手架，也希望能帮助个人快速交付产品

## 技术选型
### 后端
技术 | 名称 | 官网
----|------|----
Spring Boot | 核心框架  | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
Apache Shiro | 安全框架  | [http://shiro.apache.org/](http://shiro.apache.org/)
Spring Data Jpa | ORM框架  | [https://spring.io/projects/spring-data-jpa](https://spring.io/projects/spring-data-jpa)
Thymeleaf | 模板引擎  | [http://www.thymeleaf.org/](http://www.thymeleaf.org/)
Velocity | 模板引擎  | [http://velocity.apache.org/](http://velocity.apache.org/)
Redis | 分布式缓存数据库  | [https://redis.io/](https://redis.io/)
Quartz | 作业调度框架  | [http://www.quartz-scheduler.org/](http://www.quartz-scheduler.org/)
WxJava | 微信开发SDK  | [https://github.com/Wechat-Group/WxJava](https://github.com/Wechat-Group/WxJava)
Lombok | 精简代码  | [https://projectlombok.org/](https://projectlombok.org/)
Swagger2 | 接口文档  | [http://swagger.io/](http://swagger.io/)
Maven | 项目构建管理  | [http://maven.apache.org/](http://maven.apache.org/)

### 前端
技术 | 名称 | 官网
----|------|----
EasyWeb | UI框架  | [https://easyweb.vip/](https://easyweb.vip/)
Editor.md | markdown编辑器  | [https://www.mdeditor.com/](https://www.mdeditor.com/)
CKeditor4 | 富文本编辑器  | [https://ckeditor.com/ckeditor-4/](https://ckeditor.com/ckeditor-4/)
Ztree | 树插件  | [http://www.treejs.cn/v3/main.php#_zTreeInfo](http://www.treejs.cn/v3/main.php#_zTreeInfo)

更多...

## 项目结构
``` lua
jeebud
├── jeebud-common -- 项目公共模块，提供工具类、常量类
├── jeebud-core -- 底层一些封装，其他第三方集成
├── jeebud-modules -- 业务系统模块
|    ├── module-gen -- 简易代码生成器
|    ├── module-quartz -- 定时任务模块
|    ├── module-upload -- 文件上传模块
|    ├── module-wechat -- 微信公众号模块（开发中）
|    └── module-upms -- 用户权限系统
└── jeebud-platform -- 系统平台，提供项目配置、启动类、静态资源文件
```

## 主要功能

- 用户管理
- 角色管理
- 权限管理
- 日志管理
- 定时任务
- 代码生成
- 富文本编辑器
- Markdown编辑器
- Websocket
- 邮件发送

## 在线体验

- 体验地址：[ http://jeebud.itfuyun.com/admin ]( http://jeebud.itfuyun.com/admin )

- 账号密码: test/123456

## 文档

- 官网地址：[https://www.jeebud.com](https://www.jeebud.com)

- 文档地址：[https://www.jeebud.com/docs/quick-start.html](https://www.jeebud.com/docs/quick-start.html)

## 版权声明
Jeebud 使用[MIT协议](https://github.com/itfuyun/jeebud/blob/master/LICENSE)

## 联系方式

- QQ：15774124
- 邮箱：itfuyun@gmail.com

## 界面预览

<img src="http://blog-img.itfuyun.com/blog/20191031/8Cxk0Mphvaxx.png?imageslim" alt="用户管理" style="zoom:80%;" />



<img src="http://blog-img.itfuyun.com/blog/20191031/IHoqTgi39X2v.png?imageslim" alt="权限" style="zoom:80%;" />



<img src="http://blog-img.itfuyun.com/blog/20191031/AxPUo9xueisy.png?imageslim" alt="授权" style="zoom:80%;" />



<img src="http://blog-img.itfuyun.com/blog/20191031/1qwid9sTIGgQ.png?imageslim" alt="日志" style="zoom:80%;" />



<img src="http://blog-img.itfuyun.com/blog/20191031/0EcKmogUkkYG.png?imageslim" alt="定时任务" style="zoom:80%;" />



<img src="http://blog-img.itfuyun.com/blog/20191031/BObOOhJ9f2MP.png?imageslim" alt="websocket" style="zoom:80%;" />



<img src="http://blog-img.itfuyun.com/blog/20191031/GDCHUpB6M3rU.png?imageslim" alt="markdown" style="zoom:80%;" />



<img src="http://blog-img.itfuyun.com/blog/20191031/uXn4BGNcpNId.png?imageslim" alt="富文本" style="zoom:80%;" />

## 鸣谢
Jeebud部分功能参考以下优秀的开源项目，排名不分先后

- [Ruoyi](http://www.ruoyi.vip)
- [el-admin 后台管理系统](https://auauz.net)
- [litemall](https://gitee.com/linlinjava/litemall)

以及其他使用到的开源项目
