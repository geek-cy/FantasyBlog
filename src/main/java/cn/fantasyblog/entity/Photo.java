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
 * @Description
 * @Author Cy
 * @Date 2021-04-14 14:12
 */
@Data
@ApiModel("相册")
@TableName("t_photo")
public class Photo implements Serializable {

    @ApiModelProperty("主键:ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("地址")
    private String url;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("排序值")
    private Integer sort;

    @ApiModelProperty("是否前台显示")
    private Boolean display;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    public interface Table{
        String DESCRIPTION = "description";
        String CREATE_TIME = "create_time";
        String UPDATE_TIME = "update_time";
        String DISPLAY = "display";
        String URL = "url";
        String SORT = "sort";
    }
}
