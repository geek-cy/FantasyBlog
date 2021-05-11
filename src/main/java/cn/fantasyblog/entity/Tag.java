package cn.myBlog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-21 14:47
 */
@ApiModel("标签")
@Data
@TableName("t_tag")
public class Tag implements Serializable {

    @ApiModelProperty("Id")
    @TableId(type = IdType.AUTO)
    private Long Id;

    @ApiModelProperty("名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("关联文章数量")
    @TableField(exist = false)
    private Integer articleCount;

    public interface Table {
        String ID = "id";
        String NAME = "name";
        String COLOR = "color";
        String CREATE_TIME = "create_time";
        String UPDATE_TIME = "update_time";
    }
}
