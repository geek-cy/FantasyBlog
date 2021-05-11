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
 * @Date 2021-04-17 15:47
 */
@ApiModel("归档页面数据")
@Data
public class ArchivesVO implements Serializable {

    @ApiModelProperty("文章日期统计")
    private List<ArticleDateVO> articleDates;

    @ApiModelProperty("文章分类")
    private Page<Article> pageInfo;
}
