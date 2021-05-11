package cn.myBlog.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-14 21:43
 */
@ApiModel("统一返回结果")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult implements Serializable {
    @ApiModelProperty("响应码")
    private Integer code;

    @ApiModelProperty("响应消息")
    private String msg;

    @ApiModelProperty("响应数据")
    private Object data;

    public JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public static JsonResult build(Integer code, String message) {
        return new JsonResult(code, message);
    }

    public static JsonResult build(Integer code, String message, Object data) {
        return new JsonResult(code, message, data);
    }

    public static JsonResult ok() {
        JsonResult result = new JsonResult();
        result.setCode(200);
        result.setMsg("OK");
        return result;
    }

    public static JsonResult ok(Object data) {
        JsonResult result = new JsonResult();
        result.setCode(200);
        result.setMsg("OK");
        result.setData(data);
        return result;
    }

    public static JsonResult fail(String msg) {
        JsonResult result = new JsonResult();
        result.setCode(500);
        result.setMsg(msg);
        return result;
    }
}
