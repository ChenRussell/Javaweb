package injection.test;

import injection.service.InjectionService;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class testsave {

	@Test
	public void testIt(){
		
		//����һ��IOC����
		ApplicationContext context = new FileSystemXmlApplicationContext(  
                "classpath:applicationContext.xml");  //��Ҫ�������ļ�һ��
		
		//ͨ��IOC���������Ӧ����
        InjectionService injectionService = (InjectionService) context.getBean("injectionService");
        injectionService.save("ppppp");
	}
}
