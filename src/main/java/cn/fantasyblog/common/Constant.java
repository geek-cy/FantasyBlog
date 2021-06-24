package cn.fantasyblog.common;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-14 17:07
 */
public interface Constant {
    String HOME_TITLE = "首页";

    String HOME_HREF = "/admin/page/home/dashboard";

    String LOGO_TITLE = "FantasyBlog";

    String LOGO_IMAGE = "/static/admin/layuimini/images/logo.png";

    String LOGO_HREF = "./";

    Integer MAX_TOP_ARTICLES = 6;

    Integer MAX_RECOMMEND_ARTICLES = 4;

    String bloomArticleId = "bloom::ArticleId";
    // 分页起始数
    String PAGE = "1";

    // 后台分页显示数
    String PAGE_LIMIT = "10";

    // 前台分页显示数
    String PAGE_SIZE = "6";

    Integer FILTER_BY_DAY = 1;

    Integer FILTER_BY_MONTH = 2;

    Integer FILTER_BY_YEAR = 3;

    /**
     * 点赞事件
     */
    String LIKE = "like";

    /**
     * 评论事件
     */
    String COMMENT = "comment";

    /**
     * 点赞数
     */
    String LIKE_COUNT = "like_count";

    /**
     * 浏览量
     */
    String VIEW_COUNT = "view_count";

    /**
     * 评论量
     */
    String COMMENT_COUNT = "comment_count";

    /**
     * redis键符号
     */
    String KEY = "::";

    /**
     * 菜单键
     */
    String MENU = "menu";

    /**
     * 用于IP定位转换
     */
    String REGION = "内网IP|内网IP";

    /**
     * 默认颜色
     */
    String DEFAULT_COLOR = "#D5F5E3";

    /**
     * 请求耗时等级
     */
    Integer LOW_REQUEST_TIME_RANK = 1;

    Integer MID_REQUEST_TIME_RANK = 2;

    Integer HIGH_REQUEST_TIME_RANK = 3;

    Integer LOW_REQUEST_TIME = 10;

    Integer HIGH_REQUEST_TIME = 1000;

    /**
     * 默认密码
     */
    String DEFAULT_PASSWORD = "123456";

    /**
     * 默认头像
     */
    String DEFAULT_AVATAR = "https://gravatar.loli.net/avatar/f2c02ce7474e4b228a576f7e47f00bd1?d=mp&v=1.3.10";

    /**
     * 菜单树根结点
     */
    Long MENU_TREE_ROOT = 0L;

    /**
     * 菜单树根结点名称
     */
    String MENU_TREE_ROOT_NAME = "根目录";

    /**
     * 菜单树开始结点
     */
    Long MENU_TREE_START = -1L;

    /**
     * 菜单树复选框未选中
     */
    String MENU_TREE_NOT_SELECTED = "0";

    /**
     * 菜单树复选框选中
     */
    Integer MENU_TREE_SELECTED = 1;

    /**
     * 评论链表根节点
     */
    Long COMMENT_LINKED_LIST_ROOT = 0L;

    /**
     * 访客Id
     */
    String VISITOR_ID = "visitorId";

    /**
     * 访客名称
     */
    String VISITOR_NAME = "visitorName";

    /**
     * 访客激活状态
     */
    Integer VISITOR_ENABLE = 1;

    /**
     * 访客未激活状态
     */
    Integer VISITOR_DISABLE = 0;

    String ACTIVATION_EMAIL = "激活账号";
    /**
     * 审核状态
     */
    Integer AUDIT_PASS = 2;

    Integer AUDIT_WAIT = 1;

    Integer AUDIT_NOT_PASS = 0;

    /**
     * 用户状态
     */
    Integer USER_ENABLE = 1;

    Integer USER_DISABLE = 0;

    /**
     * 显示
     */
    Integer DISPLAY = 1;

    /**
     * 隐藏
     */
    Integer HIDDEN = 0;


    Integer SUCCESS = 1;

    Integer FAILURE = 0;

    Integer NEWEST_PAGE_SIZE = 10;

    String USER = "user";

    /**
     * 搜索高亮前标签
     */
    String HIGH_LIGHT_PRE_TAGS = "<em class='search-keyword'>";

    String HIGH_LIGHT_POST_TAGS = "</em>";

    Integer GITHUB_CONFIG = 1;

    String HTTP = "http://";

    String HTTPS = "https://";

    String EMAIL = "geek_cy@qq.com";

    String BLOG_ADMIN = "http://192.168.31.92:8000/admin";

    String DOMAIN = "http://www.fantasyblog.cn";

    /**
     * redis存访问量
     */
    String COUNT = "count";

}
