package org.zxy.flea.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.zxy.flea.consts.FleaConst;

import java.util.List;

@Component
@Slf4j
public class FleaMsgListener {


    @RabbitListener(queuesToDeclare = @Queue(FleaConst.AMQP_QUEUE))
    public void process(String msg) {
        log.info("我msg: {}", msg);


    }


    @RabbitListener(queuesToDeclare = @Queue(FleaConst.AMPQ_QUEUE_BATCH))
    public void processBatch(List<String> msg) {
        log.info("我们msg: {}", msg);

    }


}
