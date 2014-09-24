package spring.practice.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		//first();
		two();
	}

	private static void two() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/di.xml");
		MessageRenderer messageRenderer = context.getBean("messageRenderer", MessageRenderer.class);
		
		messageRenderer.print();
	}

	public static void first() {
		MessageProvider messageProvider;
		MessageRenderer messageRenderer;
		
		//messageProvider = new HiWorldMessageProvider();
		messageProvider = new HelloWorldMessageProvider();
		
		messageRenderer = new DefaultMessageRenderer();
		messageRenderer = new NameMessageRenderer("Yoonsung");
		
		messageRenderer.setMessageProvider(messageProvider);
		messageRenderer.print();
	}
}
