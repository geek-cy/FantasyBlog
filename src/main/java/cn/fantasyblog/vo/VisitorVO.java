package cn.fantasyblog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-02 19:04
 */

@ApiModel("访客登录")
@Data
public class VisitorVO implements Serializable {

    @ApiModelProperty("身份信息：用户名或邮箱")
    @NotNull
    String certificate;

    @ApiModelProperty("密码")
    @NotNull
    String password;
}
