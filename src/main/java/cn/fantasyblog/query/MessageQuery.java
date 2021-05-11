package cn.fantasyblog.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-20 18:45
 */
@ApiModel("留言查询条件")
@Data
public class MessageQuery {
    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("开始创建日期")
    private String startDate;

    @ApiModelProperty("借宿创建日期")
    private String endDate;

    @ApiModelProperty("审核状态[0:审核未过, 2:等待审核, 3:审核通过]")
    private Integer status;
}
