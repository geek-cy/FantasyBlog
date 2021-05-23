/*
package cn.fantasyblog.component;

import cn.fantasyblog.dto.Event;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

*/
/**
 * @Description
 * @Author Cy
 * @Date 2021/5/13 23:46
 *//*

@Component
@Slf4j
public class EventProducer {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    // 处理事件
    public void fireEvent(Event event){
        // 将事件发布到kafka中指定的主题
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }
}
*/
