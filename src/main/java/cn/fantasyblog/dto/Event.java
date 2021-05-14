package cn.fantasyblog.dto;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 事件
 * @Author Cy
 * @Date 2021/5/14 0:06
 */
@Getter
public class Event {

    /**
     * 主题
     */
    private String topic;

    /**
     * 触发访客
     */
    private Long visitorId;

    /**
     * 事件类型
     */
    private Integer type;

    /**
     * 文章Id
     */
    private Long articleId;

    private final Map<String,Object> data = new HashMap<>();

    public String getTopic(){
        return topic;
    }

    // 此设计减少构造器
    public Event setTopic(String topic){
        this.topic = topic;
        return this;
    }

    public Event setVisitorId(Long visitorId){
        this.visitorId = visitorId;
        return this;
    }

    public Event setType(Integer type){
        this.type = type;
        return this;
    }

    public Event setArticleId(Long articleId){
        this.articleId = articleId;
        return this;
    }

    public Event setData(String key,Object value){
        this.data.put(key, value);
        return this;
    }
}
