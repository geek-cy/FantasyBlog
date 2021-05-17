/*
package cn.fantasyblog.component;

import cn.fantasyblog.service.CommentService;
import cn.fantasyblog.service.LikeService;
import cn.fantasyblog.service.ViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

*/
/**
 * @Description
 * @Author Cy
 * @Date 2021/5/17 19:55
 *//*

@Slf4j
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    private LikeService likeService;

    @Autowired
    private ViewService viewService;

    @Autowired
    private CommentService commentService;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    */
/**
     * redis key失效监听
     *//*

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("开始执行");
        log.info("进行持久化");

    }
}
*/
