package cn.fantasyblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.fantasyblog.entity.ArticleTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {
    /**
     * 批量添加文章标签
     *
     * @param articleId 文章ID
     * @param tagList   标签列表
     */
    void insertBatch(@Param("articleId") Long articleId, @Param("tagIdList") List<Long> tagList);
}
