package cn.fantasyblog.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-07 11:06
 */
@ApiModel("角色查询条件")
@Data
public class RoleQuery implements Serializable {

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("开始日期")
    private String startDate;

    @ApiModelProperty("结束日期")
    private String endDate;
}
