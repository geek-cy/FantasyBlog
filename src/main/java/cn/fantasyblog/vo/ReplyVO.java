package cn.myBlog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-20 19:34
 */
@ApiModel("回复")
@Data
public class ReplyVO implements Serializable {
    @ApiModelProperty("父级ID")
    @NotNull
    private Long pid;

    @ApiModelProperty("访客ID")
    private Long visitorId;

    @ApiModelProperty("文章ID")
    @NotNull
    private Long articleId;

    @ApiModelProperty("回复内容")
    @NotBlank(message = "回复内容不能为空")
    @Length(max = 80, message = "回复内容不能超过80个字符")
    private String reply;
}
