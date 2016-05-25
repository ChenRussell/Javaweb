Java高并发秒杀API项目：

1.创建项目：maven指令

2.修改Servlet版本为3.1
  补全项目结构

3.修改junit版本为4.11（使用注解方式测试）
  pom.xml补全项目依赖：
	日志：java日志
	实现slf4j接口并整合
	数据库相关依赖
	DAO框架：MyBatis依赖
	MyBatis自身实现的spring整合依赖
	Servlet web相关依赖
	Spring核心依赖
	Spring DAO依赖
	Spring web相关依赖
	Spring test 相关依赖

4.秒杀业务分析

5.DAO层设计与开发（接口设计+SQL编写）：
	数据库设计
	DAO层实体和接口编码
	基于myBatis实现DAO
		myBatis官方文档：http://www.mybatis.org/mybatis-3/zh/index.html
		mybatis-config.xml
		XML提供DAO层SQL配置
	myBatis整合Spring
		spring-framework-reference
	DAO层单元测试编码和问题排查

6.秒杀Service接口设计

7.实现SeckillService接口
	所有编译期异常转化为运行期异常 ？
	枚举 ？
