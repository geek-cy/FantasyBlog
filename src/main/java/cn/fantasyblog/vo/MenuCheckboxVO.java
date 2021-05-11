package cn.myBlog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-19 0:21
 */
@Data
@ApiModel("多选菜单")
public class MenuCheckboxVO implements Serializable {
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("父级菜单ID")
    private Long parentId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("选中属性")
    private String checkArr;

    @ApiModelProperty("子级菜单列表")
    private List<MenuCheckboxVO> children;
}
