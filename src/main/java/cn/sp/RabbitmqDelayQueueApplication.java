package cn.sp;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableRabbit
@SpringBootApplication
public class RabbitmqDelayQueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqDelayQueueApplication.class, args);
	}

}
