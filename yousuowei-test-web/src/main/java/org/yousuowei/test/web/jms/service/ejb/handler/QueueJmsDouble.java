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
public class QueueJmsDouble implements MessageListener {

	public void onMessage(Message message) {
		TextMessage ms = (TextMessage) message;
		try {
			System.out.println("two:" + ms.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}