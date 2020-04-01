package cn.sp.mq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

/**
 * Created by 2YSP on 2020/1/31.
 * 消息后置处理器：用来设置消息的header及其属性
 *
 *
 */
public class ExpirationMessagePostProcessor implements MessagePostProcessor{

  private final Long ttl;// 毫秒

  public ExpirationMessagePostProcessor(Long ttl){
    this.ttl = ttl;
  }

  @Override
  public Message postProcessMessage(Message message) throws AmqpException {
    // 设置message的失效时间
    message.getMessageProperties().setExpiration(ttl.toString());
    return message;
  }
}
