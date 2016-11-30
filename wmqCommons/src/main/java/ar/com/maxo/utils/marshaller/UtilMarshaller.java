package ar.com.maxo.utils.marshaller;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;

public class UtilMarshaller {
	
	private static final Logger logger= Logger.getLogger(UtilMarshaller.class);
	
	public static String Object2XML(Object entity) {
		XStream xstream = new XStream();
		String xml = xstream.toXML(entity);
		logger.info("Marshall >>>>>>>> " + xml);
		return xml;
	}
	
	public static Object XML2Object(String xml) {
		XStream xstream = new XStream();
		Object entity = xstream.fromXML(xml);
		return (Object) entity;
	}
	
	public static String Object2XML_JAXB(Object entity, Class clazz) {
		StringWriter sw = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
			sw = new StringWriter();
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			jaxbMarshaller.marshal(entity, sw);
		} catch (JAXBException e) {
			logger.info(e);
		}
		return sw.toString();
	}
	
	public static Object XML2Object_JAXB(String xml, Class clazz) {
		Object entity = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			
			StringReader sr = new StringReader(xml);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			entity = jaxbUnmarshaller.unmarshal(sr);
		} catch (JAXBException e) {
			logger.info(e);
		}
		return entity;
	}
}

















