package cn.fantasyblog.vo;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.entity.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-28 14:21
 */
@ApiModel("前台主页数据")
@Data
public class HomeVO implements Serializable {
    @ApiModelProperty("置顶文章列表")
    private List<Article> topArticles;

    @ApiModelProperty("推荐文章列表")
    private List<Article> recommendArticles;

    @ApiModelProperty("文章分页")
    private Page<Article> pageInfo;
}
