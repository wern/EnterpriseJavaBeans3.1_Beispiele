/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package hello;
import java.util.Properties;

import javax.naming.InitialContext;

public class HelloWorldClient {
	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		props.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
		props.put("java.naming.provider.url", "jnp://localhost:1099");
		InitialContext context = new InitialContext(/*props*/);
		HelloWorld hello = (HelloWorld)context.lookup("HelloWorldImpl/remote");
		System.out.println(hello.hello("World"));
	}
}
