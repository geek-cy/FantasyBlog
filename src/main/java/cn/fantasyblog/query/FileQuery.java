package cn.fantasyblog.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 本地存储条件查询
 * @Author Cy
 * @Date 2021/5/12 20:16
 */
@ApiModel("文件条件")
@Data
public class LocalStorageQuery {

    @ApiModelProperty("文件名")
    private String name;

    @ApiModelProperty("开始创建日期")
    private String startDate;

    @ApiModelProperty("结束创建日期")
    private String endDate;
}
