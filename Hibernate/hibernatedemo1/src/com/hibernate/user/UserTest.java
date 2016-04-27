
package com.hibernate.user;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.*;

import com.demo1.bean.User;
public class UserTest {

	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	@Before
	public void init(){
		//�������ö���
		Configuration config = new Configuration().configure();
		//��������ע�����
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		//�����ػ���������
		sessionFactory = config.buildSessionFactory(serviceRegistry);
		//�ػ�����
		session = sessionFactory.openSession();
		//��������
		transaction = session.beginTransaction();
	}
	
	@After
	public void destory(){
		transaction.commit(); //�ύ����
		session.close(); //�رջػ�
		sessionFactory.close(); //�رջػ�����
		
	}
	@Test
	public void testSaveUser(){
		//�����û�����
		User user = new User("�Ƹ��󿩿�","123","1151650717@qq.com","sz",20,"13051189772","no");
		session.save(user); //�������������ݿ�
		
	}
}
