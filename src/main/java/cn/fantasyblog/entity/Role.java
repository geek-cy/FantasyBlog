package cn.fantasyblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-14 20:55
 */
@ApiModel("角色")
@Data
@TableName("sys_role")
public class Role implements Serializable {
    @ApiModelProperty("主键:ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("名称")
    @NotBlank(message = "角色名称不能为空")
    @Length(min = 2, max = 16, message = "角色名称长度为2-16个字符")
    private String roleName;

    @ApiModelProperty("描述")
    @Length(max = 100, message = "描述长度在100个字符以内")
    private String description;

    @ApiModelProperty("级别")
    @Max(value = 1024, message = "角色级别数值不能超过1024")
    @Min(value = 1, message = "角色级别数值不能小于1")
    private Integer sort;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("开启状态[0:停用, 1:开启]")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("菜单ID列表")
    @NotEmpty(message = "权限不能为空")
    @TableField(exist = false)
    private List<Long> menuIdList;

    @ApiModelProperty("关联用户数量")
    @TableField(exist = false)
    private Integer userCount;

    public interface Table {
        String ID = "id";
        String ROLE_NAME = "role_name";
        String DESCRIPTION = "description";
        String SORT = "sort";
        String COLOR = "color";
        String STATUS = "status";
        String CREATE_TIME = "create_time";
        String UPDATE_TIME = "update_time";
    }
}
