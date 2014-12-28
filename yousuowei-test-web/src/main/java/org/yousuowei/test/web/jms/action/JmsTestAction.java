package org.yousuowei.test.web.jms.action;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.yousuowei.test.web.action.BaseAction;
import org.yousuowei.test.web.jms.service.ejb.ifc.JmsConstants;
import org.yousuowei.test.web.jms.service.ejb.ifc.TopicJmsIfc;


/**
 * 访问地址： http://localhost:8080/myTest/test/testJms!sendQueue.action?sendMsg=jie
 * http://localhost:8080/myTest/test/testJms!sendTopic.action?sendMsg=jie
 * 
 * @author jie
 * 
 */
public class JmsTestAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9110496077825013109L;
	private ConnectionFactory cf;
	private Queue queue;
	private Topic topic;

	// request
	private String sendMsg = "jie";

	/**
	 * 点对点发送msg
	 * 
	 * @throws IOException
	 */
	public void sendQueue() throws IOException {
		try {
			InitialContext ctx = new InitialContext();
			cf = (ConnectionFactory) ctx
					.lookup(JmsConstants.QUEUE_FACTORY_NAME);
			queue = (Queue) ctx.lookup(JmsConstants.QUEUE_NAME);
			Connection connection = cf.createConnection();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer messageProducer = session.createProducer(queue);
			TextMessage textMsg = session.createTextMessage();
			textMsg.setText(sendMsg);
			messageProducer.send(textMsg);
			connection.close();
			System.out.println("成功发送消息！");
		} catch (Exception e) {
			System.out.println("发送消息失败！");
		}
	}

	// public JmsTestAction() {
	// createGetter();
	// }

	/**
	 * 发布订阅信息 首先要创立订阅者createGetter()
	 */
	public void sendTopic() {
		try {
			InitialContext ctx = new InitialContext();
			cf = (TopicConnectionFactory) ctx
					.lookup(JmsConstants.TOPIC_FACTORY_NAME);
			topic = (Topic) ctx.lookup(JmsConstants.TOPIC_NAME);
			Connection connection = cf.createConnection();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer messageProducer = session.createProducer(topic);
			TextMessage textMsg = session.createTextMessage();
			textMsg.setText(sendMsg);
			messageProducer.send(textMsg);
			connection.close();
			System.out.println("成功发送消息！");
		} catch (Exception e) {
			System.out.println("发送消息失败！");
		}
	}

	public void createGetter() {
		TopicConnection topicCon = null;
		TopicConnectionFactory topicFc = null;
		Topic topic = null;
		InitialContext ic = null;

		try {
			ic = new InitialContext();
			topicFc = (TopicConnectionFactory) ic
					.lookup(JmsConstants.TOPIC_FACTORY_NAME);
			topic = (Topic) ic.lookup(JmsConstants.TOPIC_NAME);
		} catch (NamingException e) {
			e.printStackTrace();
		}

		try {
			topicCon = topicFc.createTopicConnection();
			TopicSession ts = topicCon.createTopicSession(false,
					Session.AUTO_ACKNOWLEDGE);
			TopicSubscriber tsub = ts.createSubscriber(topic);
			tsub.setMessageListener((MessageListener) ic
					.lookup(TopicJmsIfc.JNDI));
		} catch (JMSException e) {
			e.printStackTrace(System.err);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		try {
			InitialContext ctx = new InitialContext();
			QueueConnection connection = null;
			QueueSession session = null;
			QueueConnectionFactory factory = (QueueConnectionFactory) ctx
					.lookup("QueueConnectionFactory");
			connection = factory.createQueueConnection();
			session = connection.createQueueSession(false,
					QueueSession.AUTO_ACKNOWLEDGE);
			Destination destination = (Queue) ctx.lookup("queue/myQueue");
			MessageProducer messageProducer = session
					.createProducer(destination);
			TextMessage msg = session.createTextMessage();
			msg.setText("jie");
			messageProducer.send(msg);
			connection.close();
			System.out.println("成功发送消息！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getSendMsg() {
		return sendMsg;
	}

	public void setSendMsg(String sendMsg) {
		this.sendMsg = sendMsg;
	}

}
