package com.Demo2.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.*;

import com.Demo2.bean.Grade;
/*
 * 单向一对多关联关系（班级--->学生）
 * */
import com.Demo2.bean2.Student;

public class testIt {
	
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	
	@Before
	public void init(){
		//创建配置对象
		Configuration config = new Configuration().configure();
		//创建服务注册对象
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		//创建会话工厂对象
		sessionFactory = config.buildSessionFactory(serviceRegistry);
		//会话对象
		session = sessionFactory.openSession();
		//开启事务
		transaction = session.beginTransaction();
	}
	
	
	@After
	public void destory(){
		transaction.commit(); //提交事务
		session.close(); //关闭会话，若不释放可能导致数据库连接池溢出
		sessionFactory.close(); //关闭会话工厂	
	}
	
	@Test
	public void testSaveUser(){     
		//如果希望在学生表中添加对应的班级编号，需要在班级中添加学生，建立关联关系
		Grade g = new Grade("Java一班","2014级Java软件开发一班");
		Student stu1 = new Student("黄复贵","男");
		Student stu2 = new Student("杨千嬅","女");
		g.getStudents().add(stu1);
		g.getStudents().add(stu2);		

		session.save(g);
		session.save(stu1);
		session.save(stu2);
		
	}


	
}
