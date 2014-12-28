package org.yousuowei.test.web.jms.service.ejb.handler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.yousuowei.test.web.jms.service.ejb.ifc.JmsConstants;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = JmsConstants.QUEUE_NAME) })
public class QueueJmsHandler implements MessageListener {

	public void onMessage(Message message) {
		TextMessage ms = (TextMessage) message;
		try {
			System.out.println("one:" + ms.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
