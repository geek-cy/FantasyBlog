package cn.myBlog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-18 21:39
 */
@ApiModel("留言")
@Data
@TableName("t_message")
public class Message implements Serializable {

    @ApiModelProperty("主键:ID")
    @TableId
    private Long id;

    @ApiModelProperty("父级留言ID")
    private Long pid;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("内容")
    @NotBlank(message = "评论内容不能为空")
    @Length(max = 80, message = "评论内容不能超过80个字符")
    private String content;

    @ApiModelProperty("昵称")
    @Pattern(regexp = "^[A-Za-z\\u4E00-\\u9FA5]{2,8}$", message = "昵称只能为字母或汉字，长度为2-8个字符")
    private String nickname;

    @ApiModelProperty("网址")
    @URL(message = "地址格式不正确")
    private String link;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty("审核状态[0:审核未过, 1:等待审核, 2:审核通过")
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

    public interface Table{
        String ID = "id";
        String CONTENT = "content";
        String NICKNAME = "nickname";
        String CREATE_TIME = "create_time";
        String STATUS = "status";
    }
}
