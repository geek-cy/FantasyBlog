package cn.myBlog.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-01 13:42
 */
@ApiModel("标签查询条件")
@Data
public class TagQuery implements Serializable {
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("开始创建日期")
    private String startDate;

    @ApiModelProperty("开始结束日期")
    private String endDate;
}
