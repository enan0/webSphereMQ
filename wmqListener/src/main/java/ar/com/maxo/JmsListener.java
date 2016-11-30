package ar.com.maxo;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import ar.com.utils.entities.Person;
import ar.com.utils.marshaller.UtilMarshaller;


public class JmsListener implements MessageListener {
	private static final Logger logger = Logger.getLogger(JmsListener.class);

	@Override
	public void onMessage(Message message) {
		if( message != null ) {
			TextMessage textMessage = (TextMessage) message;
			try {
				logger.info("\nReceived message:\n" + message);
				
				//logger.info("");
				//logger.info(">>>>>>> Object::Message: " + message.getStringProperty(JmsConstants.JMS_IBM_MQMD_PUTAPPLNAME));
				
				Person person = (Person) UtilMarshaller.XML2Object_JAXB( textMessage.getText(), Person.class );
				logger.info("");
				logger.info(">>>>>>> Object::Event: ");
				logger.info("EventName: " + person.getEventName() + " NameSpace: " + person.getNameSpace() + 
						" Source: " + person.getSource() + " Address: " + person.getAddress() );
				logger.info("");
				logger.info(">>>>>>> Object::Person: ");
				logger.info("Nombre:" + person.getFirstName() + " " + person.getSurName() ); 
				logger.info("DNI: " + person.getDni() ); logger.info("age: " + person.getAge() );
				
				//message.acknowledge();
			} catch (JMSException e) {
				logger.info(e);
			}   
		} else {
	    	logger.info("No message received!\n");
	    }
	}
}
