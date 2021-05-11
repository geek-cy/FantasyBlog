package cn.myBlog.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description 数据表格统一返回结果
 * @Author Cy
 * @Date 2021-03-14 21:39
 */
@ApiModel("数据表格统一返回结果")
@Data
@EqualsAndHashCode(callSuper = true)//让其生成的equals方法和hashcode方法包含父类属性
public class TableResult extends JsonResult implements Serializable {
    @ApiModelProperty("记录总数")
    private Long count;

    public static TableResult tableOk(Object data,Long count){
        TableResult result = new TableResult();
        result.setCode(0);
        result.setMsg("ok");
        result.setCount(count);
        result.setData(data);
        return result;
    }
}
