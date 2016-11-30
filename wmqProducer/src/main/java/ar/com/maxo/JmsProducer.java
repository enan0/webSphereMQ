package ar.com.maxo;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

public class JmsProducer {

  private static String host = "daixapp0.datacenter.osde.ar";
  private static int port = 1414;
  private static String channel = "CANAL_JAVA";
  private static String queueManagerName = "QM.EVENTOS.OSDE.D";
  private static String destinationName = "DEST_CVPREST_PRUEBA";
  private static boolean isTopic = false;
  private static boolean acknowledge = false;
  
  private static final Logger logger= Logger.getLogger(JmsProducer.class);
  
  /**
   * Metodo para enviar un mensaje a una cola WMQ 7.5.3
   * @param String - mensaje a enviar
   * 
   * @author maximiliano.torchio
   */
  public void produce(String stringMessage) {
    Connection connection = null;
    Session session = null;
    Destination destination = null;
    MessageProducer producer = null;

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
      logger.info("Conexión a la cola creada.");
      if ( acknowledge ) {
    	  session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
      } else {    	  
    	  session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      }
      if (isTopic) {
        destination = session.createTopic(destinationName);
      }
      else {
        destination = session.createQueue(destinationName);
      }
      producer = session.createProducer(destination);

      TextMessage message = null;
      if( stringMessage != null ) {
    	  message = session.createTextMessage(stringMessage);
      } else {
	      long uniqueNumber = System.currentTimeMillis() % 1000;
	      message = session.createTextMessage("JmsProducer: Your lucky number today is "
	          + uniqueNumber);
      }
      
      connection.start();
      logger.info("Conectado a la cola");

      producer.send(message);
      logger.info("Mensaje enviado:\n" + message);

    }
    catch (JMSException jmsex) {
    	logger.info(jmsex);
    }
    finally {
      if (producer != null) {
        try {
          producer.close();
        }
        catch (JMSException jmsex) {
        	logger.info("No se pudo cerrar el producer.");
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
   * Metodo para enviar un mensaje predefinido a una cola WMQ 7.5.3
   * (msg: "JmsProducer: Your lucky number today is <lucky_number>")
   * 
   * @author maximiliano.torchio
   */
  public void produce() {
	  produce(null);
  }
}