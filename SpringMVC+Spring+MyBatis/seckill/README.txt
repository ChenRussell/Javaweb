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

8.基于Spring托管Service依赖（即实现）
	Spring IOC功能理解：
		对象工厂+依赖管理  ----->  一致的访问接口（获取任意实例）
	项目业务对象依赖：
		SeckillService 依赖于： SeckillDao + SuccessKilledDao 依赖于：SqlSessionFactory 依赖于：DataSource...
	IOC使用：
		XML配置 -----> package-scan -----> Annotation注解（@Service   @Autowired）

9.Spring声明式事务配置
	配置事务管理器：
		<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	使用注解来管理事务行为：
		<tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
	@Transactional

10.集成测试Service逻辑
	logback.xml ？

11.前端交互设计 *****

12.Restful接口设计

13.整合配置SpringMVC框架

14.使用SpringMVC实现Restful接口

15.基于bootstrap开发页面结构
	http://www.runoob.com/bootstrap/bootstrap-environment-setup.html

16.交互逻辑编程
	cookie登录交互：
		http://www.bootcdn.cn/
		引入js文件时charset="GBK"解决中文乱码问题
	计时交互
		countdown函数使用
	秒杀交互
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());

17.项目总结

