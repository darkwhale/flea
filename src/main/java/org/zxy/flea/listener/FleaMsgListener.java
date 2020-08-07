package org.zxy.flea.listener;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.util.ImageUtil;

import java.util.List;

@Component
public class FleaMsgListener {

    @RabbitListener(queuesToDeclare = @Queue(FleaConst.AMQP_QUEUE))
    public void process(String msg) {
        ImageUtil.deleteImage(msg);
    }


    @RabbitListener(queuesToDeclare = @Queue(FleaConst.AMPQ_QUEUE_BATCH))
    public void processBatch(List<String> msg) {
        ImageUtil.deleteImage(msg);
    }

}
