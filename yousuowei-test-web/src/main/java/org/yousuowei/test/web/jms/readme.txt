1，jboss下面配置消息队列服务器 （jboss服务器/server/default/deploy/***-service.xml文件）
<server>
发送点对点
<mbean code="org.jboss.mq.server.jmx.Queue" name="jboss.mq.destination:service=Queue,name=myQueue">
		<depends optional-attribute-name="DestinationManager">
			jboss.mq:service=DestinationManager
		</depends>
</mbean>
这个是发布订阅
<mbean code="org.jboss.mq.server.jmx.Topic" name="jboss.mq.destination:service=Queue,name=myTopic">
		<depends optional-attribute-name="DestinationManager">
			jboss.mq:service=DestinationManager
		</depends>
</mbean>
</server>

2，创建点对点 消息监听bean（注意：只能修饰为消息驱动bean，不可再修饰为有状态或者无状态bean）
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/myQueue") })
public class MyJMS implements MessageListener {

	@Override
	public void onMessage(Message message) {
		TextMessage ms = (TextMessage) message;
		try {
			System.out.println(ms.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

3，点对点消息发送bean
public void testJmsSend() {
		try {
            //QueueConnectionFactory：（点对点消息传递 ）为在jboss里面jndiView里（TopicConnectionFactory：订阅消息传递）
			cf = (ConnectionFactory) Utils.lookupEJB("QueueConnectionFactory");
			queue = (Queue) Utils.lookupEJB("queue/myQueue");
			Connection connection = cf.createConnection();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer messageProducer = session.createProducer(queue);
			TextMessage msg = session.createTextMessage();
			msg.setText("jie");
			messageProducer.send(msg);
			connection.close();
			System.out.println("成功发送消息！");
		} catch (Exception e) {
			System.out.println("发送消息失败！");
		}
	}
	
创建订阅消息bean（可创建多个对应topic/myTopic）
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/myTopic") })
public class TopicJmsHandler implements MessageListener {

	@Override
	public void onMessage(Message message) {
		TextMessage ms = (TextMessage) message;
		try {
			System.out.println("one:" + ms.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}

    /**
	 * 发布订阅信息 首先要创立订阅者createGetter()
	 */
	public void sendTopic() {
		try {
			InitialContext ctx = new InitialContext();
			cf = (TopicConnectionFactory) ctx
					.lookup("TopicConnectionFactory");
			topic = (Topic) ctx.lookup("topic/myTopic");
			Connection connection = cf.createConnection();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer messageProducer = session.createProducer(topic);
			TextMessage textMsg = session.createTextMessage();
			textMsg.setText("msg");
			messageProducer.send(textMsg);
			connection.close();
			System.out.println("成功发送消息！");
		} catch (Exception e) {
			System.out.println("发送消息失败！");
		}
	}
	