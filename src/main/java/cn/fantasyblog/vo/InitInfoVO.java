package cn.myBlog.vo;

import cn.myBlog.common.Constant;
import cn.myBlog.entity.Menu;
import cn.myBlog.utils.MenuTreeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 后台初始化信息
 * @Author Cy
 * @Date 2021-03-19 0:15
 */
@ApiModel("初始化数据")
@Data
public class InitInfoVO implements Serializable {
    @ApiModelProperty("菜单列表")
    private List<MenuVO> menuInfo;

    @ApiModelProperty("主页信息")
    private HomeInfo homeInfo;

    @ApiModelProperty("logo信息")
    private LogoInfo logoInfo;

    public static InitInfoVO init(List<Menu> menuList) {
        List<MenuVO> menuInfo = new ArrayList<>();
        for (Menu e : menuList) {
            MenuVO menuVO = new MenuVO();
            BeanUtils.copyProperties(e,menuVO);
            /*menuVO.setId(e.getId());
            menuVO.setPid(e.getPid());
            menuVO.setHref(e.getHref());
            menuVO.setTitle(e.getTitle());
            menuVO.setIcon(e.getIcon());
            menuVO.setTarget(e.getTarget());*/
            menuInfo.add(menuVO);
        }
        HomeInfo homeInfo = new HomeInfo();
        homeInfo.setHref(Constant.HOME_HREF);
        homeInfo.setTitle(Constant.HOME_TITLE);

        LogoInfo logoInfo = new LogoInfo();
        logoInfo.setHref(Constant.LOGO_HREF);
        logoInfo.setTitle(Constant.LOGO_TITLE);
        logoInfo.setImage(Constant.LOGO_IMAGE);

        InitInfoVO initInfoVO = new InitInfoVO();
        initInfoVO.setMenuInfo(MenuTreeUtil.toTree(menuInfo));
        initInfoVO.setHomeInfo(homeInfo);
        initInfoVO.setLogoInfo(logoInfo);
        return initInfoVO;
    }
    @Data
    private static class HomeInfo {
        private String title;

        private String href;
    }

    @Data
    private static class LogoInfo {
        private String title;

        private String image;

        private String href;
    }
}
