# basic-framework

![](https://img.shields.io/badge/Spring%20Boot-2.3.10.RELEASE-green) ![](https://img.shields.io/badge/Spring%20Security-2.3.10.RELEASE-green) ![](https://img.shields.io/badge/Redis-5.0.7-red) ![](https://img.shields.io/badge/MyBatis--Plus-3.3.1-yellow) ![](https://img.shields.io/badge/JWT-0.9.1-yellowgreen)



## 项目介绍

> 基础框架,包含权限管理、操作日志、入口/出口留痕、限流等功能



## 项目结构

```
.
├── pom.xml
├──doc                 ----------------------- 文档、sql脚本
├── rjsoft-common      ----------------------- 常用模块包含通用工具类等
├── rjsoft-core        ----------------------- 模型层
├── rjsoft-dao         ----------------------- 持久层
├── rjsoft-service     ----------------------- 业务层
└── rjsoft-web         ----------------------- web层 controller、配置、aop、filter、interceptor等
```



> 文章定时任务依赖分布式定时任务调度平台[XXL-JOB](https://www.xuxueli.com/xxl-job/), 启动项目前请先启动XXL-JOB调度中心(doc文档中包含一份已编译好的调度中心可直接使用,但主要该中心数据库为公司服务器)， 如不需要此功能可删除配置文件和maven依赖中以下内容：

- 配置文件：

```yml
xxl:
  job:
    admin:
      #### 调度中心部署跟地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行"执行器心跳注册"和"任务结果回调"；为空则关闭自动注册；
      addresses: http://127.0.0.1:8080/xxl-job-admin
      ### 执行器通讯TOKEN [选填]：非空时启用
    accessToken:
    ### 执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册
    executor:
      appname: basic-framework
      ### 执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题。
      address:
      ### 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯实用；地址信息用于 "执行器注册" 和 "调度中心请求并触发任务"；
      ip:
      ### 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口
      port: 9999
      ### 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；
      logpath: ../xxl-job/jobhandler
      ### 执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；
      logretentiondays: 30
```

- maven依赖
```xml
    声明部分：
        <dependency>
            <groupId>com.xuxueli</groupId>
            <artifactId>xxl-job-core</artifactId>
            <version>${xxl-job.version}</version>
        </dependency>
    引用部分：
        <dependency>
            <groupId>com.xuxueli</groupId>
            <artifactId>xxl-job-core</artifactId>
        </dependency>
```

- 数据库表关系
    ![表关系](doc/basic-frame.png)



