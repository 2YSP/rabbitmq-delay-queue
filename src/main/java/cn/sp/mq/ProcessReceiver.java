package cn.sp.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by 2YSP on 2020/1/31.
 */
@Component
public class ProcessReceiver {

  @RabbitListener(queues = MqConfig.DELAY_PROCESS_QUEUE)
  public void execute(String msg){
    System.out.println(System.currentTimeMillis() + " 接收到的延迟消息:" + msg);
  }

}
