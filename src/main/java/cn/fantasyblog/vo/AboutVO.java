package cn.fantasyblog.vo;

import cn.fantasyblog.entity.Category;
import cn.fantasyblog.entity.Photo;
import cn.fantasyblog.entity.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-14 17:14
 */
@ApiModel("关于我页面数据")
@Data
public class AboutVO implements Serializable {
    @ApiModelProperty("文章数量")
    private Long articleCount;

    @ApiModelProperty("分类数量")
    private Long categoryCount;

    @ApiModelProperty("标签数量")
    private Long tagCount;

    @ApiModelProperty("分类列表")
    private List<Category> categories;

    @ApiModelProperty("标签列表")
    private List<Tag> tags;

    @ApiModelProperty("文章日期统计")
    private List<ArticleDateVO> articleDates;

    @ApiModelProperty("照片列表")
    private List<Photo> photos;
}
