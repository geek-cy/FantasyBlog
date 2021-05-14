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
 * @Date 2021-04-25 22:34
 */
@ApiModel("菜单角色关联表")
@Data
@TableName("sys_role_menu")
public class RoleMenu implements Serializable {
    @ApiModelProperty("主键:角色Id")
    @TableId
    private Long roleId;

    @ApiModelProperty("主键:菜单Id")
    @TableId
    private Long menuId;

    public interface Table{
        String ROLE_ID = "role_id";
        String MENU_ID = "menu_id";
    }
}
