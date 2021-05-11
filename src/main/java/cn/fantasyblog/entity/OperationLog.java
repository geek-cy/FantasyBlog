package cn.myBlog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-13 23:21
 */
@ApiModel("操作日志")
@NoArgsConstructor
@Data
@TableName("sys_operation_log")
public class OperationLog implements Serializable {
    @ApiModelProperty("ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("异常详情")
    private byte[] exceptionDetail;

    @ApiModelProperty("类型[info,error...]")
    private String logType;

    @ApiModelProperty("执行方法名称")
    private String method;

    @ApiModelProperty("参数名称")
    private String params;

    @ApiModelProperty("请求IP")
    private String requestIp;

    @ApiModelProperty("请求耗时")
    private Long time;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("浏览器")
    private String browser;

    @ApiModelProperty("IP来源")
    private String address;

    @ApiModelProperty("请求结果")
    private int status;

    public interface Table{
        String ID = "id";
        String CREATE_TIME = "create_time";
        String DESCRIPTION = "description";
        String EXCEPTION_DETAIL = "exception_detail";
        String LOG_TYPE = "log_type";
        String METHOD = "method";
        String PARAMS = "params";
        String REQUEST_IP = "request_ip";
        String TIME = "time";
        String USERNAME = "username";
        String BROWSER = "browser";
        String ADDRESS = "address";
        String STATUS = "status";
    }
}
