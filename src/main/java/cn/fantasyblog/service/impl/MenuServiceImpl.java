package cn.fantasyblog.service.impl;

import cn.fantasyblog.common.Constant;
import cn.fantasyblog.dao.MenuMapper;
import cn.fantasyblog.entity.Menu;
import cn.fantasyblog.service.MenuService;
import cn.fantasyblog.vo.InitInfoVO;
import cn.fantasyblog.vo.MenuCheckboxVO;
import cn.fantasyblog.vo.MenuSelectVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.fantasyblog.exception.BadRequestException;
import cn.fantasyblog.utils.MenuTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-19 0:10
 */
@Service
@CacheConfig(cacheNames = "menu")
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    @Cacheable
    public InitInfoVO menu(Long userId) {
        List<Menu> menus = menuMapper.listMenuByUserId(userId);
        return InitInfoVO.init(menus);
    }

    @Override
    @Cacheable
    public List<Menu> listAll() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.select(Menu.Table.ID, Menu.Table.PID, Menu.Table.TITLE, Menu.Table.HREF, Menu.Table.AUTHORITY, Menu.Table.ICON, Menu.Table.SORT, Menu.Table.TYPE, Menu.Table.STATUS, Menu.Table.CREATE_TIME, Menu.Table.UPDATE_TIME);
        return menuMapper.selectList(wrapper);
    }

    @Override
    @Cacheable
    public Long countAll() {
        return Long.valueOf(menuMapper.selectCount(null));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void saveOfUpdate(Menu menu) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq(Menu.Table.TITLE,menu.getTitle());
        // 保存
        if (menu.getId() == null) {
            // 检查菜单标题是否唯一
            if(menuMapper.selectOne(wrapper) != null){
                throw new BadRequestException("菜单标题已存在");
            }
            menuMapper.insert(menu);
        } else{
            // 更新
            // 检查菜单标题是否唯一
            List<Menu> menus = menuMapper.selectList(wrapper);
            menus = menus.stream().filter(m->!m.getId().equals(menu.getId())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(menus)){
                throw new BadRequestException("菜单标题已存在");
            }
            menuMapper.updateById(menu);
        }
    }

    @Override
    @Cacheable
    public List<MenuSelectVO> listBySelectTree() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.select(Menu.Table.ID, Menu.Table.PID, Menu.Table.TITLE);
        List<Menu> menus = menuMapper.selectList(wrapper);
        List<MenuSelectVO> treeList = new ArrayList<>();
        for (Menu menu : menus) {
            MenuSelectVO menuSelectVO = new MenuSelectVO();
            menuSelectVO.setValue(menu.getId());
            menuSelectVO.setName(menu.getTitle());
            menuSelectVO.setPid(menu.getPid());
            treeList.add(menuSelectVO);
        }
        return MenuTreeUtil.toSelectTree(treeList, Constant.MENU_TREE_START);
    }

    @Override
    @Cacheable
    public Menu getById(Long id) {
        return menuMapper.selectById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    @Override
    public void removeById(Long id) {
        menuMapper.deleteById(id);
    }

    @Override
    @Cacheable
    public List<MenuCheckboxVO> listByCheckboxTree() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.select(Menu.Table.ID, Menu.Table.PID, Menu.Table.TITLE);
        List<Menu> menus = menuMapper.selectList(wrapper);
        List<MenuCheckboxVO> treeList = new ArrayList<>();
        for (Menu menu : menus) {
            MenuCheckboxVO menuCheckboxVO = new MenuCheckboxVO();
            menuCheckboxVO.setId(menu.getId());
            menuCheckboxVO.setParentId(menu.getPid());
            menuCheckboxVO.setTitle(menu.getTitle());
            menuCheckboxVO.setCheckArr(Constant.MENU_TREE_NOT_SELECTED);
            treeList.add(menuCheckboxVO);
        }
        return MenuTreeUtil.toCheckBoxTree(treeList, Constant.MENU_TREE_START);
    }
}
