package cn.fantasyblog.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description 事件
 * @Author Cy
 * @Date 2021/5/14 0:06
 */
@Data
@Accessors(chain = true)
public class Event {

    /**
     * 主题
     */
    private String topic;

    /**
     * 触发访客
     */
    private String visitorName;

    /**
     * 事件类型
     */
    private Integer type;

    /**
     * 文章Id
     */
    private Long articleId;

}
