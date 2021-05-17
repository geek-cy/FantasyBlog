package cn.fantasyblog.component;

import cn.fantasyblog.common.Constant;
import cn.fantasyblog.dto.Event;
import cn.fantasyblog.service.MailService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


/**
 * @Description
 * @Author Cy
 * @Date 2021/5/13 23:49
 */
@Slf4j
@Component
public class EventConsumer {

    @Autowired
    MailService mailService;

    @KafkaListener(topics = {Constant.LIKE})
    public void handlerLike(ConsumerRecord<String,String> record) {
        Event event = JSONObject.parseObject(record.value(), Event.class);
        // 发送点赞通知
        String subject = "访客"+ event.getVisitorName() + "赞了你的文章" + event.getArticleId();
        // 推送到邮箱
        mailService.sendSimpleMail(Constant.EMAIL,subject,Constant.BLOG_ADMIN);
        log.info(subject);
    }

    @KafkaListener(topics = {Constant.COMMENT})
    public void handlerComment(ConsumerRecord<String,String> record){
        Event event = JSONObject.parseObject(record.value(), Event.class);
        // 发送评论通知
        String subject = "访客"+ event.getVisitorName() + "评论了你的文章" + event.getArticleId();
        // 推送到邮箱
        mailService.sendSimpleMail(Constant.EMAIL,subject,Constant.BLOG_ADMIN);
        log.info(subject);
    }
}
