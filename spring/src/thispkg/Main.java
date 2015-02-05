package thispkg;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("thispkg.xml");
		StudentHolder sh = context.getBean("studentHolder", StudentHolder.class);
		sh.display();
		sh.displayName();
		System.out.println("==============================");
		MyInterface in = (MyInterface) context.getBean("randomClass", RandomClass.class);;
		in.speak();
	}

}
