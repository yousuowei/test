package org.yousuowei.test.web.jms.service.ejb.handler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.yousuowei.test.web.jms.service.ejb.ifc.JmsConstants;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = JmsConstants.TOPIC_NAME) })
public class TopicJmsHandler implements MessageListener {

	public void onMessage(Message message) {
		TextMessage ms = (TextMessage) message;
		try {
			System.out.println("one:" + ms.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
