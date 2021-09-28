# basic-framework

![](https://img.shields.io/badge/Spring%20Boot-2.3.10.RELEASE-green)![](https://img.shields.io/badge/Spring%20Security-2.3.10.RELEASE-green)![](https://img.shields.io/badge/Redis-5.0.7-red)![](https://img.shields.io/badge/MyBatis--Plus-3.3.1-yellow)![](https://img.shields.io/badge/JWT-0.9.1-yellowgreen)



## 项目介绍

> 基础框架,包含权限管理、操作日志、入口/出口留痕、限流等功能



## 项目结构

```
.
├── pom.xml
├──doc                 ----------------------- 文档、sql脚本
├── rjsoft-common      ----------------------- 常用模块包含通用工具类等
├── rjsoft-core    	   ----------------------- 模型层
├── rjsoft-dao         ----------------------- 持久层
├── rjsoft-service     ----------------------- 业务层
└── rjsoft-web         ----------------------- web层 controller、配置、aop、filter、interceptor等
```



> 文章定时任务以来分布式定时任务调度平台[XXL-JOB](https://www.xuxueli.com/xxl-job/), 启动项目前请先启动XXL-JOB调度中心(doc文档中包含一份已编译好的调度中心可直接使用,但主要该中心数据库为公司服务器)

