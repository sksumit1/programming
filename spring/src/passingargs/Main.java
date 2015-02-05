package passingargs;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("passingargs.xml");
		Thinker th = context.getBean("thinker", Thinker.class);
		Magician mg = context.getBean("magician", Magician.class);
		th.think("Let me have pizza");
		String predictedThought = mg.getThoughts();
		System.out.println(predictedThought);
	}

}
