package cn.fantasyblog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-19 0:23
 */
@ApiModel("下拉菜单")
@Data
public class MenuSelectVO implements Serializable {
    @ApiModelProperty("标题")
    private String name;

    @ApiModelProperty("ID")
    private Long value;

    @ApiModelProperty("父级菜单ID")
    private Long pid;

    @ApiModelProperty("子级菜单列表")
    private List<MenuSelectVO> children;
}
