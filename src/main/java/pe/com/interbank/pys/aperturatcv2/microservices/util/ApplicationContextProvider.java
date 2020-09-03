package pe.com.interbank.pys.aperturatcv2.microservices.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
@Component
public class ApplicationContextProvider implements ApplicationContextAware {
	
	private static ApplicationContext context;
    
    public static ApplicationContext getApplicationContext() {
        return context;
    }

	@Override
	public void setApplicationContext(ApplicationContext ac) {
		if (ac instanceof ConfigurableApplicationContext) {
			((ConfigurableApplicationContext) ac).registerShutdownHook();
		}
		setStaticApplicationContext(ac);
	}
	private static void setStaticApplicationContext(ApplicationContext ac) {
		context = ac;
	}
	
	public static <T> T getBean(String name, Class<T> type) {
		return getApplicationContext().getBean(name,type);		
	}
	
	public static MessageChannel getChannel(String name){
		return getBean(name, MessageChannel.class);
	}


}
