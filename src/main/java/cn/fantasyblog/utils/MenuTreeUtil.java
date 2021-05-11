package cn.myBlog.utils;

import cn.myBlog.common.Constant;
import cn.myBlog.vo.MenuCheckboxVO;
import cn.myBlog.vo.MenuSelectVO;
import cn.myBlog.vo.MenuVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 菜单树工具类
 * @Author Cy
 * @Date 2021-03-19 0:21
 */
public class MenuTreeUtil {
    /**
     * @param treeList 数据库里获取到的全量菜单列表
     * @return 返回第一级节点即pid=0
     */
    public static List<MenuVO> toTree(List<MenuVO> treeList) {
        List<MenuVO> retList = new ArrayList<>();
        // 获取第一级节点
        for (MenuVO parent : treeList) {
            if (Constant.MENU_TREE_ROOT.equals(parent.getPid())) {
                retList.add(findChild(parent, treeList));
            }
        }
        return retList;
    }

    /*public static List<MenuVO> toTree(List<MenuVO> treeList) {
        List<MenuVO> retList = new ArrayList<>();
        // 获取第一级节点
        for (MenuVO parent : treeList) {
            if (Constant.MENU_TREE_ROOT.equals(parent.getPid())) {
                retList = getTreeDataLoop(parent, treeList);
            }
        }
        return retList;
    }*/


    /*private static List<MenuVO> getTreeDataLoop(MenuVO parent, List<MenuVO> treeList) {
        // 新建一个list来存放一级菜单
        List<MenuVO> tree = new ArrayList<>();
        Map<Long, MenuVO> map = new HashMap<>();
        // 将所有的数据，以键值对的形式装入map中
        for (MenuVO child : treeList) {
            map.put(child.getId(), child);
        }
        for (MenuVO child : treeList) {
            // 如果id是父级的话就放入tree中
            if (parent.getId().equals(child.getPid())) {
                tree.add(child);
            } else {
                // 子级通过父id获取到父级的类型
                parent = map.get(child.getPid());
                // 父级获得子级，再将子级放到对应的父级中
                parent.getChild().add(child);
            }
        }
        return tree;
    }*/
    // 递归获得父菜单的所有孩子菜单
    private static MenuVO findChild(MenuVO parent, List<MenuVO> treeList) {
        for (MenuVO child : treeList) {
            if (parent.getId().equals(child.getPid())) {
                if (parent.getChild() == null) {
                    parent.setChild(new ArrayList<>());
                }
                parent.getChild().add(findChild(child, treeList));
            }
        }
        return parent;
    }

    public static List<MenuSelectVO> toSelectTree(List<MenuSelectVO> treeList, Long pid) {
        MenuSelectVO root = new MenuSelectVO();
        root.setName(Constant.MENU_TREE_ROOT_NAME);
        root.setValue(Constant.MENU_TREE_ROOT);
        root.setPid(pid);
        treeList.add(root);
        List<MenuSelectVO> retList = new ArrayList<>();
        for (MenuSelectVO parent : treeList) {
            if (pid.equals(parent.getPid())) {
                retList.add(findChildSelect(parent, treeList));
            }
        }
        return retList;
    }

    private static MenuSelectVO findChildSelect(MenuSelectVO parent, List<MenuSelectVO> treeList) {
        for (MenuSelectVO children : treeList) {
            if (parent.getValue().equals(children.getPid())) {
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(findChildSelect(children, treeList));
            }
        }
        return parent;
    }

    public static List<MenuCheckboxVO> toCheckBoxTree(List<MenuCheckboxVO> treeList, Long pid) {
        MenuCheckboxVO root = new MenuCheckboxVO();
        root.setTitle(Constant.MENU_TREE_ROOT_NAME);
        root.setId(Constant.MENU_TREE_ROOT);
        root.setParentId(pid);
        root.setCheckArr(Constant.MENU_TREE_NOT_SELECTED);
        treeList.add(root);
        List<MenuCheckboxVO> retList = new ArrayList<>();
        for (MenuCheckboxVO parent : treeList) {
            if (pid.equals(parent.getParentId())) {
                retList.add(findChildCheckBox(parent, treeList));
            }
        }
        return retList;
    }

    private static MenuCheckboxVO findChildCheckBox(MenuCheckboxVO parent, List<MenuCheckboxVO> treeList) {
        for (MenuCheckboxVO children : treeList) {
            if (parent.getId().equals(children.getParentId())) {
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(findChildCheckBox(children, treeList));
            }
        }
        return parent;
    }
}
