package cn.fantasyblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 后台：事件管理
 * @Author Cy
 * @Date 2021/5/13 17:38
 */
@ApiModel("后台：通知管理")
@Data
@TableName("t_notice")
public class Notice implements Serializable {

    @ApiModelProperty("ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("文章ID")
    private Long articleId;

    @ApiModelProperty("访客ID")
    private Long visitorId;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("事件类型:0:评论, 1:点赞，2:留言，3:删除评论,4:取消点赞，5：删除留言")
    private Integer type;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("IP来源")
    private String address;

    @ApiModelProperty("请求IP")
    private String requestIp;

    public interface Table{
        String ID = "id";
        String ARTICLEId = "article_id";
        String VISITORId = "visitor_id";
        String CONTENT = "content";
        String TYPE = "type";
        String CREATE_TIME = "create_time";
        String ADDRESS = "address";
        String REQUEST_IP = "request_ip";
    }
}
