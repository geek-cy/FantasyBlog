package cn.fantasyblog.service;

import cn.fantasyblog.entity.Tag;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.query.TagQuery;

import java.util.List;

public interface TagService {
    /**
     * 根据文章Id查询标签
     * @param id 文章Id
     * @return 标签列表
     */
    List<Tag> listByArticleId(Long id);

    /**
     * 文章内查询所有标签
     * @return 标签列表
     */
    List<Tag> listAll();

    /**
     * 后台查询所有标签
     * @return
     */
    Page<Tag> listTableByTag(Integer current, Integer size, TagQuery tagQuery);

    /**
     * 标签数量
     * @return
     */
    Long countAll();

    /**
     * 根据id删除标签
     * @param id
     */
    void remove(Long id);

    /**
     * 批量删除标签
     * @param idList
     */
    void removeList(List<Long> idList);

    /**
     * 新增或更新标签
     * @param tag
     */
    void saveOrUpdate(Tag tag);

    /**
     * 根据Id获取标签
     * @param id
     * @return
     */
    Tag getById(Long id);

    /**
     * 查询颜色
     * @return
     */
    List<String> color();

    /**
     * 前台查询所有标签（统计文章数目）
     * @return 分类列表
     */
    List<Tag> listArticleCountByTag();
}
