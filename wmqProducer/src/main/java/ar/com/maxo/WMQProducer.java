package ar.com.maxo;

import ar.com.utils.entities.Person;
import ar.com.utils.marshaller.UtilMarshaller;


public class WMQProducer {

	public static void main(String[] args) {

		System.out.println("=====/ JMS Prod /=====");
		JmsProducer jmsprod = new JmsProducer();
		
		Person carlo = new Person("Carlo", "S", 33444555L, 45);
		String msg = UtilMarshaller.Object2XML_JAXB(carlo, Person.class);
		jmsprod.produce(msg);
	}

}
