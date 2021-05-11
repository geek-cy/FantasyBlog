package cn.fantasyblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-14 20:44
 */
@ApiModel("用户")
@Data
@TableName("sys_user")
public class User implements Serializable {
    @ApiModelProperty("主键:ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    @Length(min = 3, max = 16, message = "用户名长度为3-16个字符")
    @Pattern(regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5\\s·]+$", message = "用户名不能有特殊字符")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("昵称")
    @NotBlank(message = "昵称不能为空")
    @Length(min = 3, max = 16, message = "昵称长度为3-16个字符")
    @Pattern(regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5\\s·]+$", message = "\"昵称不能有特殊字符")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("性别[false:女, true:男]")
    private Boolean sex;

    @ApiModelProperty("邮箱")
    @Email
    private String email;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("启用状态[0:停用, 1:启用]")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("角色ID")
    @TableField(exist = false)
    @NotNull(message = "请为用户选择一个角色")
    private Long roleId;

    @ApiModelProperty("角色")
    @TableField(exist = false)
    private Role role;

    public interface Table {
        String ID = "id";
        String USERNAME = "username";
        String PASSWORD = "password";
        String NICKNAME = "nickname";
        String AVATAR = "avatar";
        String EMAIL = "email";
        String SEX = "sex";
        String PHONE = "phone";
        String STATUS = "status";
        String CREATE_TIME = "create_time";
        String UPDATE_TIME = "update_time";
    }
}
