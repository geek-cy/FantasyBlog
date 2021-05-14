package cn.fantasyblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-02 16:23
 */
@ApiModel("评论")
@Data
@TableName("t_comment")
public class Comment implements Serializable {

    @ApiModelProperty("ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("ID")
    private Long pid;

    @ApiModelProperty("文章ID")
    private Long articleId;

    @ApiModelProperty("访客ID")
    private Long visitorId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("内容")
    @NotBlank(message = "评论内容不能为空")
    @Length(max = 80, message = "评论内容不能超过80个字符")
    private String content;

    @ApiModelProperty("审核状态[0:审核未过, 1:等待审核, 2:审核通过]")
    private int status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("浏览器")
    private String browser;

    @ApiModelProperty("系统")
    private String os;

    @ApiModelProperty("IP来源")
    private String address;

    @ApiModelProperty("请求IP")
    private String requestIp;

    @ApiModelProperty("父级评论昵称")
    private String parentNickname;

    @ApiModelProperty("访客")
    @TableField
    private Visitor visitor;

    @ApiModelProperty("回复评论列表")
    @TableField
    private List<Comment> children;

    public interface Table{
        String ID = "id";
        String PID = "pid";
        String ARTICLE_ID = "article_id";
        String VISITOR_ID = "visitor_id";
        String USER_ID = "user_id";
        String CONTENT = "content";
        String STATUS = "status";
        String CREATE_TIME = "create_time";
        String BROWSER = "browser";
        String OS = "os";
        String ADDRESS = "address";
        String REQUEST_IP = "request_ip";
        String PARENT_NICKNAME = "parent_nickname";
    }

}
