package cn.fantasyblog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-07 22:45
 */
@ApiModel("用户角色关联表")
@Data
@TableName("sys_role_user")
public class RoleUser implements Serializable {

    @ApiModelProperty("主键:角色Id")
    @TableId
    private Long roleId;

    @ApiModelProperty("主键:角色I")
    @TableId
    private Long userId;

    public interface Table{
        String ROLE_ID = "role_id";
        String USER_ID = "user_id";
    }
}
