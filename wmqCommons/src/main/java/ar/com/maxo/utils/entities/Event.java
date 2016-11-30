package ar.com.maxo.utils.entities;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="event")
public class Event {
	
	private HashMap<String, String> stringHeaders;
		
	public Event() {
		setDefaultHeaders();
	}
	
	private void setDefaultHeaders() {
		this.stringHeaders = new HashMap<String, String>();
		this.stringHeaders.put("eventname", "eventname");
		this.stringHeaders.put("namespace", "namespace");
		this.stringHeaders.put("source", "source");
		this.stringHeaders.put("address", extractAddress() );
	}
	
	public String getSource() {
		return this.stringHeaders.get("source");
	}
	
	public String getEventName() {
		return this.stringHeaders.get("eventname");
	}
	
	public String getNameSpace() {
		return this.stringHeaders.get("namespace");
	}
	
	public String getAddress() {
		return this.stringHeaders.get("address");
	}
	
	private String extractAddress() {
		String ip;
		try {
			InetAddress addres = Inet4Address.getLocalHost();

			ip = addres.getHostName() + "(" + addres.getHostAddress() + ")";
		} catch (UnknownHostException uhe) {
			ip = "noAddres";
		}
		return ip;
	}
}
