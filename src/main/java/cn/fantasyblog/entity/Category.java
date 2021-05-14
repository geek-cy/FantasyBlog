package cn.fantasyblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-21 15:07
 */
@ApiModel("分类")
@Data
@TableName("t_category")
public class Category implements Serializable {

    @ApiModelProperty("Id")
    @TableId(type = IdType.AUTO)
    private Long Id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("是否前台显示")
    private Boolean display;

    @ApiModelProperty("简介")
    private String introduction;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("关联文章数量")
    @TableField(exist = false)
    private Long articleCount;

    public interface Table {
        String ID = "id";
        String NAME = "name";
        String DISPLAY = "display";
        String INTRODUCTION = "introduction";
        String COLOR = "color";
        String CREATE_TIME = "create_time";
        String UPDATE_TIME = "update_time";
    }
}
