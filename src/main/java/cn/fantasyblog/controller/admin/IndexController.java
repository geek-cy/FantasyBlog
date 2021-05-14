package cn.fantasyblog.controller.admin;

import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.entity.User;
import cn.fantasyblog.service.*;
import cn.fantasyblog.vo.IndexVO;
import cn.fantasyblog.vo.InitInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Api(tags ="后台：控制面板")
@Controller
@RequestMapping("/admin")
public class IndexController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private AccessLogService accessLogService;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private AnnounceService announceService;

    @Autowired
    private RedisService redisService;

    @ApiOperation("初始化菜单")
    @ResponseBody
    @GetMapping("/init")
    /**
     * 响应主体初始化
     */
    public ResponseEntity<Object> init(HttpSession session, HttpServletRequest request) {
        Long userId = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) {
                // 接收上一次的cookie
                userId = Long.valueOf(cookie.getValue());
                break;
            }
        }
        // 第一次登录
        if (userId == null) {
            Object o = session.getAttribute("user");
            if (o != null) {
                User user = (User) o;
                userId = user.getId();
            }
            // 重置菜单缓存
            redisService.deleteMenu();
        }
        if (userId != null) {
            InitInfoVO initInfoVO = menuService.menu(userId);
            return new ResponseEntity<>(initInfoVO, HttpStatus.OK);
        } else return new ResponseEntity<>("当前用户未登录，无法初始化", HttpStatus.BAD_REQUEST);
    }

    @ApiOperation("访问后台首页")
    @AccessLog("访问后台首页")
    @GetMapping
    public String toIndex() {
        return "admin/home/index";
    }

    @ApiOperation("查询控制面板数据")
    @ResponseBody
    @GetMapping("/indexData")
    public ResponseEntity<Object> index() {
        IndexVO indexVO = new IndexVO();
        indexVO.setArticleCount(articleService.countAll());
        indexVO.setCategoryCount(categoryService.countAll());
        indexVO.setTagCount(tagService.countAll());
        indexVO.setCommentCount(commentService.countAll());
        indexVO.setUserCount(userService.countAll());
        indexVO.setVisitorCount(visitorService.countAll());
        indexVO.setViewCount(accessLogService.countAll());
        indexVO.setMessageCount(messageService.countAll());
        indexVO.setAccessLogs(accessLogService.listNewest());
        indexVO.setOperationLogs(operationLogService.listNewest());
        indexVO.setComments(commentService.listNewest());
        indexVO.setMessages(messageService.listNewest());
        indexVO.setArticles(articleService.listNewest());
        indexVO.setFrontViews(accessLogService.countByLast7Days());
        indexVO.setBackViews(operationLogService.countByLast7Days());
        indexVO.setAnnounces(announceService.listNewest());
        return new ResponseEntity<>(indexVO,HttpStatus.OK);
    }
}
