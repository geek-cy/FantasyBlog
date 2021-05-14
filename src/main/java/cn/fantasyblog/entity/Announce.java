package cn.fantasyblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-22 20:30
 */
@ApiModel("通知")
@Data
@TableName("sys_announcement")
public class Announce implements Serializable {

    @ApiModelProperty("ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("标题")
    @NotNull(message = "标题不能为空")
    @Length(max = 20, message = "标题长度在20个字符以内")
    private String title;

    @ApiModelProperty("内容")
    @NotNull(message = "内容不能为空")
    @Length(max = 100, message = "内容长度在100个字符以内")
    private String content;

    @ApiModelProperty("排序值")
    @NotNull(message = "排序值不能为空")
    @Length(min = 1,max = 1024,message = "排序值在1-1024之间")
    private Integer sort;

    @ApiModelProperty("是否显示")
    private Boolean display;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    public interface Table{
        String ID = "id";

        String TITLE = "title";

        String CONTENT = "content";

        String SORT = "sort";

        String DISPLAY = "display";

        String CREATE_TIME = "create_time";

        String UPDATE_TIME = "update_time";
    }
}
