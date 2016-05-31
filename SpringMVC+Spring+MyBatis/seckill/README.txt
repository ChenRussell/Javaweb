Java�߲�����ɱAPI��Ŀ��Spring+SpringMVC+MyBatis����
	��ɱҵ�񳡾����е��͵ġ���������
	��ɱ/����������Խ��Խ����

1.������Ŀ��mavenָ��
	ָ�mvn archetype:generate -DgroupId=org.seckill -DartifactId=seckill -DarchetypeArtifactId=maven-archetype-webapp -DarchetypeCatalog=internal
	-DgroupId=org.seckill -DartifactId=seckill   ----->  ��Ŀ����
	-DarchetypeArtifactId=maven-archetype-webapp  -----> ʹ��maven��webappԭ�ʹ�����Ŀ
	pom.xmlΪmaven��׼�����ļ�

2.�޸�Servlet�汾Ϊ3.1
	ֱ�Ӳο�E:\apache-tomcat-8.0.33\webapps\examples\WEB-INF�µ�web.xml
  ��ȫ��Ŀ�ṹ

3.pom.xml�޸�junit�汾Ϊ4.11��ʹ��ע�ⷽʽ����junit���в��ԣ�
  	��ȫ��Ŀ������Ѱ������������ַ��http://www.mvnrepository.com/��  ----->  ��jar������,Ӧ�������
		��־��java��־��slf4j + logback��
		ʵ��slf4j�ӿڲ�����
		���ݿ����������
			mysql-connector-java��������ֻ������������ʱ�Ż�ʹ�ã���ȫmaven������Χ��<scope>runtime</scope>
		DAO��ܣ�MyBatis����
		MyBatis����ʵ�ֵ�spring��������
		Servlet web�������
		Spring������4.1.7.RELEASE����
			Spring��������
			Spring DAO������jdbc + tx��
			Spring web�������
			Spring test �������

4.��ɱҵ�����
	��ɱҵ��ĺ��ģ�
		���Ĵ���
	�û���Կ���ҵ�������
		����� + ��¼������ϸ  ----->  ����������  ----->  ׼ȷ�������
	Mysqlʵ����ɱҵ����ѵ����   �ѵ�����-����������
		���������� + �м�������      ��
			���������ڼ���棨update����
	��ɱ���ܣ�
		��ɱ�ӿڱ�¶�������û�ͨ��������������ǰ֪����ɱ�ӿں���������Զ���ǰ��ɱ��
		ִ����ɱ
		��ز�ѯ��
			�б��ѯ
			����ҳ��ѯ


DAO�����ݷ��ʲ㣩��Ʊ���
5.DAO������뿪�����ӿ����+SQL��д����
	���ݿ���ƣ�
		���Table
	DAO��ʵ��ͽӿڱ��룺
		���ʵ�壨entity����
			���ݿ�����ֱ�Ӷ�Ӧjavaʵ���е�������ԣ�
				Table ----->  Entity��seckill_id -----> seckillId      start_time -----> startTime��
			SuccessKilledʵ���а���Seckillʵ�壨���Ϲ�ϵ��
		���ʵ��entity��Ӧ��dao�ӿڣ�
			Seckill -----> SeckillDao    SuccessKilled -----> SuccessKilledDao   ����Ƹ�ʵ��������ݿ��ҵ�񷽷���
				/*����id��ѯSuccessKilled��Я����ɱ��Ʒ����ʵ��*/
				SuccessKilled queryByIdWithSeckill(@Param("seckillId") int seckillId,@Param("userPhone") String userPhone);
	����myBatisʵ��DAO��
		MyBatis��
			���� + SQL = Entity/List
			ͨ��XML�ṩSQL,MyBatis�ڲ�Mapper�Զ�ʵ��DAO�ӿ�,����Ҫ��дdao�����ʵ����
			���ݿ� <----->  ӳ��  <-----> ����
			�����ݿ������{����seckill_id}ӳ�䵽entity����{seckillId}
			��entity�����������ӳ�䵽���ݿ�
			һ��ORM�����ϵӳ����
			*****  ��Ҫ������Ȼ����Ϊ��ͨsql����ѯ������صľ��Ƕ���Ҫ����JDBC/MyBatis/Hibernate��ӳ�����װ��
		myBatis�ٷ��ĵ���
            http://www.mybatis.org/mybatis-3/zh/index.html
        ����MyBatisȫ�������ļ�������ȫ�����ã�
			mybatis-config.xml
		����MyBatis SQLӳ���ļ���mapper,Ϊdao�ӿڷ����ṩsql������ã�
			SeckillDao.xml
			SuccessKilledDao.xml
	myBatis����Spring��
		spring-framework-reference�ĵ�
		spring/spring-dao.xml��
			��һ�����������ݿ���ز���
			�ڶ������������ݿ����ӳ�
			������������sqlSessionFactory����
			���Ĳ�������ɨ��dao�ӿڰ�,MyBatis�ڲ�Mapper�Զ���̬ʵ��dao�ӿں��ע�뵽spring������
	DAO�㵥Ԫ���Ա���������Ų飺
		����spring��junit������,ʹ��junit����ʱ����springIOC������
			@RunWith(SpringJUnit4ClassRunner.class)
			�ڴ�֮ǰMyBatis�ڲ�Mapper�Զ�ʵ�ֵ�daoʵ�����ѱ�ע��springIOC������
		����junit spring�������ļ�����MyBatis��Spring������������ļ�����
			@ContextConfiguration({"classpath:spring/spring-dao.xml"})
		ע��daoʵ����������
			@Resource
            private SeckillDao seckillDao;
            ֱ�Ӵ�springIOC��������SeckillDaoʵ���������в���


Service��Ʊ���
6.��ɱService�ӿ���ƣ�
	DAOƴ�ӵ��߼���Service�����,DAO�㲻Ӧ�����߼�����,�߼�Ӧ����Service�����
	service����
		���service�ӿں�ʵ����
	exception����
		���service�ӿ�����Ҫ���쳣,�����ظ���ɱ,��ɱ�ѹر�...
	dto����
		���ݴ����,����web��service������ݴ���,��ʵ��service�ӿڷ������ݵķ�װ

7.ʵ��SeckillService�ӿڣ�
	���б������쳣ת��Ϊ�������쳣��
		spring����ʽ����ֻ��������ڵ��쳣����rollback�ع�
	���SeckillServiceImpl��������߼�û�д���,�ͷ���new SeckillExecution(seckillId, SeckillStateEnums.SUCCESS,successKilled)
	���SeckillServiceImpl��������߼��׳��쳣,��try catch�ѿ��ܵ�����Ƴ����쳣�׳�,��SeckillController������׳����쳣��Ϣ���в�ͬ��new SeckillExecution����
	SeckillController��return new SeckillResult<SeckillExecution>(true,seckillExecution);ͳһ�����ݷ�װģʽ��
		ҳ������ajax���󷵻����ͣ���װjson��������Ϊ����T
	spring��������(������)�쳣�ͻ�ع������ύ
	��ö�ٷ�װ����ʾ�����ֵ䣺
		�½�enums��,����Ϊenum

8.����Spring�й�Service��������ʵ�֣�
	Spring IOC������⣺
		���󹤳� + ��������  ----->  һ�µķ��ʽӿڣ���ȡ����ʵ����
	��Ŀҵ�����������
		SeckillService �����ڣ� SeckillDao + SuccessKilledDao �����ڣ�SqlSessionFactory �����ڣ�DataSource...
	IOCʹ�ã�
		XML���� -----> package-scan -----> Annotationע�⣺
			spring-service.xml��
			    <!--ɨ��service��������ʹ��ע������� -->
                <context:component-scan base-package="org.seckill.service"></context:component-scan>
			SeckillServiceImpl�У�
				class SeckillServiceImpl��@Service��
				private SeckillDao seckillDao;   private SuccessKilledDao successKilledDao;��
					��ǰMyBatis�ڲ�Mapper�Ѿ�ʵ��dao�ӿڲ�ע��Spring������,ֱ��ע��Service����@Autowired

9.Spring����ʽ��������
	�׳��������쳣ʱSpring����ʽ����rollback�ع�
	���������������
		<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	ʹ��ע��������������Ϊ��
		<tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
	SeckillServiceImpl�µ�executeSeckill������
		@Transactional
	����ֻ��������ֻ��һ���޸Ĳ���,����Ҫ�������

10.���ɲ���Service�߼���
	ע��seckillService����
		@Autowired
		private SeckillService seckillService;
	logback.xml��
		Log4J��Apache��һ������Դ������Ŀ(http://logging.apache.org/log4j/docs/)������һ����־��������
		ͨ��ʹ��Log4J,����ָ����־��Ϣ�����Ŀ�ĵأ�����ÿһ����־�������ʽ��������־��Ϣ�ļ���������Щ����ͨ��һ�������ļ����������á�
	ע�⿴����̨�������


Web��Ʊ���
11.ǰ�˽������ *****

12.Restful�ӿ����
	Restful������һ�����ŵ�URL������ʽ��
		GET   /seckill/list   ��ɱ�б�
		GET   /seckill/{id}/detail   ����ҳ
		POST  /seckill/{id}/{md5}/execution   ִ����ɱ
		POST  /seckill/{seckillId}/execution
		DELETE  /seckill/{id}/delete
		/user/{uid}/followers   ----->  ��ע���б�

13.��������SpringMVC���
	web.xml��
		����SpringMVC���������Servlet��DispatcherServlet
		����SpringMVC��Ҫ���ص������ļ�,ʵ�������ܵ����ϣ�
			spring-dao.xml   spring-service.xml   spring-web.xml
            MyBatis -> Spring  -> SpringMVC
        ƥ����������   ��/��
    spring-web.xml��
    	����SpringMVC��
			����SpringMVCע��ģʽ    ----->   �Զ�ע�����ע���URL�����뷽����ӳ��
			����ʹ�á�/��������ӳ��
			����jsp,��ʾViewResolver     org.springframework.web.servlet.view.JstlView
			ɨ��web��ص�bean   ----->  SeckillController ��
				<context:component-scan base-package="org.seckill.web"></context:component-scan>

14.ʹ��SpringMVCʵ��Restful�ӿ�

15.����bootstrap����ҳ��ṹ
	http://www.runoob.com/bootstrap/bootstrap-environment-setup.html

16.�����߼����
	cookie��¼������
		http://www.bootcdn.cn/
		����js�ļ�ʱcharset="GBK"���������������
	��ʱ����
		countdown����ʹ��
	��ɱ����
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());

17.��Ŀ�ܽ�

