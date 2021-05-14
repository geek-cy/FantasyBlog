package cn.fantasyblog.event;

import cn.fantasyblog.common.Constant;
import cn.fantasyblog.dao.NoticeMapper;
import cn.fantasyblog.dto.Event;
import cn.fantasyblog.entity.Notice;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.Date;


/**
 * @Description
 * @Author Cy
 * @Date 2021/5/13 23:49
 */
@Slf4j
@Component
public class EventConsumer {

    @Autowired
    private NoticeMapper noticeMapper;

    @KafkaListener(topics = {Constant.LIKE})
    public void handlerLike(ConsumerRecord<Object,Object> record) {
        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        // 发送点赞通知
        Notice notice = new Notice();
        notice.setArticleId(event.getArticleId());
        notice.setType(event.getType());
        notice.setVisitorId(event.getVisitorId());
        notice.setCreateTime(new Date());
        noticeMapper.insert(notice);
      /*  Map<String,Object> content = new HashMap<>();
        content.put("articleId",event.getArticleId());
        content.put("type",event.getType());
        content.put("visitorId",event.getVisitorId());*/

       /* if(!event.getData().isEmpty()){
            for(Map.Entry<String,Object> entry : event.getData().entrySet()){
                content.put(entry.getKey(),entry.getValue());
            }
        }*/

//        notice.setContent(JSONObject.toJSONString(content));
    }

}
