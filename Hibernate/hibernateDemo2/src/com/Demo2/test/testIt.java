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
 * ����һ�Զ������ϵ���༶--->ѧ����
 * */
import com.Demo2.bean2.Student;

public class testIt {
	
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	
	@Before
	public void init(){
		//�������ö���
		Configuration config = new Configuration().configure();
		//��������ע�����
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		//�����Ự��������
		sessionFactory = config.buildSessionFactory(serviceRegistry);
		//�Ự����
		session = sessionFactory.openSession();
		//��������
		transaction = session.beginTransaction();
	}
	
	
	@After
	public void destory(){
		transaction.commit(); //�ύ����
		session.close(); //�رջỰ�������ͷſ��ܵ������ݿ����ӳ����
		sessionFactory.close(); //�رջỰ����	
	}
	
	@Test
	public void testSaveUser(){     
		//���ϣ����ѧ��������Ӷ�Ӧ�İ༶��ţ���Ҫ�ڰ༶�����ѧ��������������ϵ
		Grade g = new Grade("Javaһ��","2014��Java�������һ��");
		Student stu1 = new Student("�Ƹ���","��");
		Student stu2 = new Student("��ǧ��","Ů");
		g.getStudents().add(stu1);
		g.getStudents().add(stu2);		

		session.save(g);
		session.save(stu1);
		session.save(stu2);
		
	}


	
}
