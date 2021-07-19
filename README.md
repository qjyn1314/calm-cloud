# calm-cloud
### 实践搭建springcloud的maven工程

# 项目中的技术选型是：
### 服务注册中心、服务配置：
nacos

### 服务之间的调用：
openfeign

### 服务网关：
gateway

### 用户登录：
spring-security

### 持久层框架：
mybatis-plus

### 数据库驱动：
mybatis-plus的dynamic-datasource
### 选用实践的版本：
spring-boot 版本：2.3.8.RELEASE

spring-cloud 版本：Hoxton.SR9

### 工程的端口号
calm-web:10081

calm-gateway:10082

calm-admin:10083

calm-xxl-job:10084

calm-job-execute:10085

calm-gen:10086

calm-auth:10087

sequence-service:10088

user-service:10089


### 工程的结构

#### calm-parent  包括：swagger、redis、jasypt、email、jwt、nacos、openfeign、springboot-admin
#### calm-common  包括：undertow、security
#### calm-core    包括：mysql、mybatis-plus、mybatis-plus-dynamic-datasource
#### calm-admin   监控服务
#### calm-auth    认证服务
#### calm-user    用户服务
#### calm-web     web页面
#### calm-job-admin   分布式定时任务管理
#### calm-job-execute 分布式定时任务执行器
#### calm-gen     代码生成(main方法执行)

账号：qjyn1314@163.com

密码：123456

部署sql：

calm-core/src/main/resources/calm_deploy.sql

calm-core/src/main/resources/init_datasource_conf.sql

calm-core/src/main/resources/init_sys_user.sql

calm-core/src/main/resources/tables_calm_job.sql
