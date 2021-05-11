package cn.fantasyblog.service;

import cn.fantasyblog.entity.Category;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.fantasyblog.query.CategoryQuery;

import java.util.List;

public interface CategoryService {
    /**
     * 后台查询所有分类
     * @return 分类列表
     */
    Page<Category> listTableByCategory(int current, int size, CategoryQuery categoryQuery);

    /**
     * 前台查询所有分类（统计文章数目）
     *
     * @return 分类列表
     */
    List<Category> listArticleCountByCategory();

    /**
     * 分类数量
     */
    Long countAll();

    /**
     * 根据ID删除分类
     */
    void remove(Long id);

    /**
     * 批量删除分类
     * @param idList
     */
    void removeList(List<Long> idList);
    /**
     * 新增或编辑分类
     * @return
     */
    void saveOrUpdate(Category category);

    /**
     * 查询颜色
     * @return
     */
    List<String> listColor();

    /**
     * 根据Id查询分类
     * @param id
     * @return
     */
    Category getById(Long id);

    /**
     * 查询所有分类
     * @return
     */
    List<Category> listAll();
}
