package ar.com.maxo;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.log4j.Logger;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

public class JmsConsumer {

  private static String host = "daixapp0.datacenter.osde.ar";
  private static int port = 1414;
  private static String channel = "CANAL_JAVA";
  private static String queueManagerName = "QM.EVENTOS.OSDE.D";
  private static String destinationName = "DEST_CVPREST_PRUEBA";
  private static boolean isTopic = false;

  private static int timeout = 15000; // in ms or 15 seconds
  private static int delay = 3000;

  private static final Logger logger = Logger.getLogger(JmsConsumer.class);

  /**
   * Metodo para consumir un solo mensaje de una cola WMQ 7.5.3
   * 
   * @author maximiliano.torchio
   */
  public void consume() {
    Connection connection = null;
    Session session = null;
    Destination destination = null;
    MessageConsumer consumer = null;

    try {
      // Create a connection factory
      JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
      JmsConnectionFactory cf = ff.createConnectionFactory();

      // Set the properties
      cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, host);
      cf.setIntProperty(WMQConstants.WMQ_PORT, port);
      cf.setStringProperty(WMQConstants.WMQ_CHANNEL, channel);
      cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
      cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, queueManagerName);

      // Create JMS objects
      connection = cf.createConnection();
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      if (isTopic) {
        destination = session.createTopic(destinationName);
      }
      else {
        destination = session.createQueue(destinationName);
      }
      consumer = session.createConsumer(destination);

      connection.start();
      logger.info("Conectado a la cola");

      // And, receive the message
      Message message = consumer.receive(timeout);
      if (message != null) {
    	  logger.info("Received message:\n" + message);
      }
      else {
    	  logger.info("No message received!\n");
      }

    }
    catch (JMSException jmsex) {
      logger.info(jmsex);
    }
    finally {
      if (consumer != null) {
        try {
          consumer.close();
        }
        catch (JMSException jmsex) {
          logger.info("No se pudo cerrar el consumidor.");
        }
      }

      if (session != null) {
        try {
          session.close();
        }
        catch (JMSException jmsex) {
        	logger.info("No se pudo cerrar la sesion.");
        }
      }

      if (connection != null) {
        try {
          connection.close();
        }
        catch (JMSException jmsex) {
        	logger.info("No se pudo cerrar la conexión.");
        }
      }
    }
  }
  
  /**
   * Metodo para consumir mensajes de una cola WMQ 7.5.3, 
   * 	utilizando un listener.
   * 
   * @author maximiliano.torchio
   */
  public void listen() {
	    Connection connection = null;
	    Session session = null;
	    Destination destination = null;
	    MessageConsumer consumer = null;

	    try {
	      // Create a connection factory
	      JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
	      JmsConnectionFactory cf = ff.createConnectionFactory();

	      // Set the properties
	      cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, host);
	      cf.setIntProperty(WMQConstants.WMQ_PORT, port);
	      cf.setStringProperty(WMQConstants.WMQ_CHANNEL, channel);
	      cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
	      cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, queueManagerName);

	      // Create JMS objects
	      connection = cf.createConnection();
	      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	      destination = session.createQueue(destinationName);
	      consumer = session.createConsumer(destination);

	      consumer.setMessageListener( new JmsListener() );
	      
	      connection.start();
	      logger.info("Conectado a la cola");
	      logger.info("Escuchando de la cola...");

	      while(true) {
			try {	        		
				Thread.sleep(delay);	        	
			} catch (InterruptedException e) {
				logger.info(e);
			}
	      }

	    } catch (JMSException jmsex) {
	      logger.info(jmsex);
	    } finally {
	      if (consumer != null) {
	        try {
	          consumer.close();
	        }
	        catch (JMSException jmsex) {
	          logger.info("No se pudo cerrar el consumidor.");
	        }
	      }

	      if (session != null) {
	        try {
	          session.close();
	        }
	        catch (JMSException jmsex) {
	        	logger.info("No se pudo cerrar la sesion.");
	        }
	      }

	      if (connection != null) {
	        try {
	          connection.close();
	        }
	        catch (JMSException jmsex) {
	        	logger.info("No se pudo cerrar la conexión.");
	        }
	      }
	    }
	  }
}
