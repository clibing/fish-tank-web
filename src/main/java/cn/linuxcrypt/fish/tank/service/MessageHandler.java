package cn.linuxcrypt.fish.tank.service;


import cn.linuxcrypt.common.service.IMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @function:
 * @author: liuboxun
 * @email: wmsjhappy@gmail.com
 * @date: 2019/9/17
 * @remark:
 * @version: 1.0
 */
@Slf4j
@Service
public class MessageHandler implements IMessageHandler {
    @Resource
    private OnlineService onlineService;

    @Override
    public void doHandler(String topic, String jsonData) {
        log.info("topic: {}, data: {}", topic, jsonData);
    }
}

