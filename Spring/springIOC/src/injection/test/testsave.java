package injection.test;

import injection.service.InjectionService;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class testsave {

	@Test
	public void testIt(){
		
		//申请一个IOC容器
		ApplicationContext context = new FileSystemXmlApplicationContext(  
                "classpath:applicationContext.xml");  //这要跟配置文件一样
		
		//通过IOC容器获得相应对象
        InjectionService injectionService = (InjectionService) context.getBean("injectionService");
        injectionService.save("ppppp");
	}
}
