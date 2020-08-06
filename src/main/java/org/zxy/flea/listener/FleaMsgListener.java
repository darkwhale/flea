package org.zxy.flea.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.util.ImageUtil;

@Component
@RabbitListener(queues = FleaConst.AMQP_QUEUE)
public class FleaMsgListener {

    @RabbitHandler
    public void process(String msg) {
        ImageUtil.deleteImage(msg);
    }

}
