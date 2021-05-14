package cn.fantasyblog.query;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Author Cy
 * @Date 2021/5/14 16:59
 */
@ApiModel("通知查询条件")
@Data
public class NoticeQuery {

    @ApiModelProperty("开始创建日期")
    private String startDate;

    @ApiModelProperty("结束创建日期")
    private String endDate;

    @ApiModelProperty("事件类型")
    private Integer type;
}
