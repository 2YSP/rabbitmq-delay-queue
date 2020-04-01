package cn.sp.controller;

import cn.sp.mq.ExpirationMessagePostProcessor;
import cn.sp.mq.MqConfig;
import java.util.Objects;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 2YSP on 2020/1/31.
 */
@RestController
public class TestController {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @GetMapping("/delay_queue/test")
  public String test(@RequestParam("ttl") Long ttl) {
    if (Objects.isNull(ttl)){
      throw new IllegalArgumentException("参数ttl不能为空");
    }
    rabbitTemplate.convertAndSend(MqConfig.DELAY_QUEUE_PER_MESSAGE_TTL,
        (Object) String.format("延迟%s毫秒的消息", ttl), new ExpirationMessagePostProcessor(ttl));
    System.out.println(System.currentTimeMillis() + " 发送消息成功");
    return "发送成功";
  }
}
