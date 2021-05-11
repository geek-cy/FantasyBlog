package cn.myBlog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-19 0:18
 */
@ApiModel("菜单")
@Data
public class MenuVO implements Serializable {
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("父级菜单ID")
    private Long pid;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("链接")
    private String href;

    @ApiModelProperty("页面跳转方式")
    private String target;

    @ApiModelProperty("子级菜单列表")
    private List<MenuVO> child;
}
