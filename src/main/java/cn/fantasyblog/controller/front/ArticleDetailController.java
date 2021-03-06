package cn.fantasyblog.controller.front;


import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.anntation.OperationLog;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.dto.Event;
import cn.fantasyblog.entity.Article;
/*import cn.fantasyblog.component.EventProducer;*/
import cn.fantasyblog.exception.BadRequestException;
import cn.fantasyblog.service.*;
import cn.fantasyblog.utils.UserInfoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-28 14:50
 */
@Api(tags = "前台：文章详情页")
@Controller
public class ArticleDetailController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisService redisService;

    @ApiOperation("文章详情页面")
//    @AccessLog("文章详情页面")
    @GetMapping("/article/{id}")
    public String articleDetail(@PathVariable("id") Long id, Model model) {
        redisService.incrementView(id);
        Article detail = articleService.getDetailById(id);
        Article prev = articleService.getPrevPreviewById(id);
        Article next = articleService.getNextPreviewById(id);
        Long likedCount = redisService.getLikedCount(id);
        Long viewCount = redisService.getViewCount(id);
        Long commentCount = redisService.getCommentCount(id);
        model.addAttribute("article", detail);
        model.addAttribute("likes", likedCount);
        model.addAttribute("views", viewCount);
        model.addAttribute("comments",commentCount);
        model.addAttribute("prevPreview", prev);
        model.addAttribute("nextPreview", next);
        return "front/article";
    }

    @ApiOperation("点赞文章")
    @OperationLog("点赞文章")
    @PutMapping("/article/{id}/likes")
    public ResponseEntity<Object> articleLike(@PathVariable("id") Long id) {
        Long visitorId = UserInfoUtil.getVisitorId();
        if (visitorId == null) throw new BadRequestException("您未登录请先登录");
        redisService.saveLiked(id, visitorId);
        // 触发点赞事件
/*        Event event = new Event().setTopic(Constant.LIKE).setArticleId(id).setVisitorName(UserInfoUtil.getVisitorName());
        eventProducer.fireEvent(event);*/
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
