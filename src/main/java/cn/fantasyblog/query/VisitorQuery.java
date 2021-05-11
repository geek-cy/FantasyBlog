package cn.myBlog.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-03 19:32
 */
@ApiModel("访客查询条件")
@Data
public class VisitorQuery implements Serializable {

    @ApiModelProperty("用户名")
    String username;

    @ApiModelProperty("邮箱")
    String email;

    @ApiModelProperty("开始创建日期")
    private String startDate;

    @ApiModelProperty("结束创建日期")
    private String endDate;
}
