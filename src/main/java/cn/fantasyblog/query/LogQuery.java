package cn.myBlog.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-13 22:45
 */
@ApiModel("日志查询条件")
@Data
public class LogQuery implements Serializable {
    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("请求IP")
    private String requestIp;

    @ApiModelProperty("浏览器")
    private String browser;

    @ApiModelProperty("IP来源")
    private String address;

    @ApiModelProperty("开始创建日期")
    private String startDate;

    @ApiModelProperty("结束创建日期")
    private String endDate;

    @ApiModelProperty("耗时级别[1:<10ms, 2:10~1000ms, 3:>1000ms]")
    private Integer timeRank;

    @ApiModelProperty("用户名")
    private String username;
}
