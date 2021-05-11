package cn.fantasyblog.controller.front;

import cn.fantasyblog.anntation.AccessLog;
import cn.fantasyblog.anntation.OperationLog;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.common.JsonResult;
import cn.fantasyblog.entity.Comment;
import cn.fantasyblog.service.CommentService;
import cn.fantasyblog.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-03 16:26
 */
@Api("前台:评论页面")
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    CommentService commentService;

    @ApiOperation("新增评论")
    @OperationLog("新增评论")
    @PostMapping
    public JsonResult saveComment(@Validated @RequestBody Comment comment, HttpServletRequest request){
        comment.setCreateTime(new Date());
        comment.setBrowser(StringUtils.getBrowser(request));
        comment.setOs(StringUtils.getClientOS(request));
        comment.setRequestIp(StringUtils.getIp(request));
        comment.setAddress(StringUtils.getCityInfo(comment.getRequestIp()));
        comment.setStatus(Constant.AUDIT_WAIT);
        commentService.save(comment);
        return JsonResult.ok();
    }

    @ApiOperation("查询文章评论")
    @AccessLog("查询文章评论")
    @GetMapping("/listByArticleId/{articleId}")
    public ResponseEntity<Object> listByArticleId(@PathVariable("articleId")Long articleId,
                                                  @RequestParam(value = "current",defaultValue = Constant.PAGE)Integer current,
                                                  @RequestParam(value = "size",defaultValue = Constant.PAGE_SIZE)Integer size){
        Page<Comment> pageInfo = commentService.listByArticleId(articleId,current,size);
        return new ResponseEntity<>(pageInfo, HttpStatus.OK);
    }

}
