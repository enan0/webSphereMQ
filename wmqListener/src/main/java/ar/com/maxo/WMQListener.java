package ar.com.maxo;

public class WMQListener {

	public static void main(String[] args) {

		System.out.println("=====/ JMS Listener /=====");
		JmsConsumer jmslist = new JmsConsumer();
		jmslist.listen();


	}

}
