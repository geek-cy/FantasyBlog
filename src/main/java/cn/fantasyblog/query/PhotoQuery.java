package cn.fantasyblog.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-14 15:41
 */
@ApiModel("相册查询条件")
@Data
public class PhotoQuery implements Serializable {

    @ApiModelProperty("描述")
    String description;

    @ApiModelProperty("开始创建日期")
    private String startDate;

    @ApiModelProperty("结束创建日期")
    private String endDate;

    @ApiModelProperty("前台是否显示")
    private Boolean display;
}
