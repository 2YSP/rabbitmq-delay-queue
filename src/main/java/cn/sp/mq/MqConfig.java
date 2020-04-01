package cn.sp.mq;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 2YSP on 2020/1/31.
 */
@Configuration
public class MqConfig {

  /**
   * TTL配置在消息上的缓冲队列
   */
  public static final String DELAY_QUEUE_PER_MESSAGE_TTL = "delay_queue_per_message_ttl";

  /**
   * TTL配置在队列上的缓冲队列
   */
  public static final String DELAY_QUEUE_PER_QUEUE_TTL = "delay_queue_per_queue_ttl";

  /**
   * 实际消费队列
   */
  public static final String DELAY_PROCESS_QUEUE = "delay_process_queue";

  /**
   * 死信队列名称
   */
  public static final String DELAY_EXCHANGE = "delay_exchange";

  @Bean
  Queue delayQueuePerMessageTTL(){
    return QueueBuilder.durable(DELAY_QUEUE_PER_MESSAGE_TTL)
        .withArgument("x-dead-letter-exchange",DELAY_EXCHANGE)
        .withArgument("x-dead-letter-routing-key",DELAY_PROCESS_QUEUE)
        .build();
  }

  @Bean
  Queue delayQueuePerQueueTTL(){
    return QueueBuilder.durable(DELAY_QUEUE_PER_QUEUE_TTL)
        .withArgument("x-dead-letter-exchange",DELAY_EXCHANGE)
        .withArgument("x-dead-letter-routing-key",DELAY_PROCESS_QUEUE)
        .withArgument("x-message-ttl",10)  //   设置队列的过期时间
        .build();
  }


  @Bean
  Queue delayProcessQueue(){
    return QueueBuilder.durable(DELAY_PROCESS_QUEUE).build();
  }

  /**
   * 死信交换机
   * @return
   */
  @Bean
  DirectExchange delayExchange(){
    return new DirectExchange(DELAY_EXCHANGE);
  }

  /**
   * 将DLX绑定到实际消费队列
   * @param delayProcessQueue
   * @param delayExchange
   * @return
   */
  @Bean
  Binding dlxBinding(Queue delayProcessQueue,DirectExchange delayExchange){
    return BindingBuilder.bind(delayProcessQueue).to(delayExchange)
        .with(DELAY_PROCESS_QUEUE);
  }

// 不用@RabbitListener注解就需要建一个监听容器用于存放消费者
//  @Bean
//  SimpleMessageListenerContainer processContainer(ConnectionFactory connectionFactory,ProcessReceiver processReceiver){
//    SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
//    messageListenerContainer.setConnectionFactory(connectionFactory);
//    messageListenerContainer.setQueueNames(DELAY_PROCESS_QUEUE);
//    messageListenerContainer.setMessageListener(new MessageListenerAdapter(processReceiver));
//    return messageListenerContainer;
//  }
}
