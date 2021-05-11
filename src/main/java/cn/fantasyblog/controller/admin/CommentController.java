package cn.myBlog.controller.admin;

import cn.myBlog.anntation.AccessLog;
import cn.myBlog.anntation.OperationLog;
import cn.myBlog.common.Constant;
import cn.myBlog.common.JsonResult;
import cn.myBlog.common.TableResult;
import cn.myBlog.entity.Comment;
import cn.myBlog.query.CommentQuery;
import cn.myBlog.service.CommentService;
import cn.myBlog.utils.StringUtils;
import cn.myBlog.utils.UserInfoUtil;
import cn.myBlog.vo.AuditVO;
import cn.myBlog.vo.ReplyVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-09 21:20
 */
@Api("评论功能")
@RestController
@RequestMapping("/admin/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @ApiOperation("后台分页查询评论")
    @AccessLog("后台分页查询评论")
    @PreAuthorize("hasAuthority('blog:comment:query')")
    @GetMapping
    public TableResult listTableByPage(@RequestParam(value = "page",defaultValue = Constant.PAGE)Integer page,
                                       @RequestParam(value = "limit",defaultValue = Constant.PAGE_LIMIT)Integer size,
                                       CommentQuery commentQuery){
        Page<Comment> pageInfo = commentService.listTableByPage(page, size, commentQuery);
        return TableResult.tableOk(pageInfo.getRecords(),pageInfo.getTotal());
    }

    @ApiOperation("后台删除评论")
    @OperationLog("后台删除评论")
    @PreAuthorize("hasAuthority('blog:comment:delete')")
    @DeleteMapping("/{id}")
    public JsonResult remove(@NotNull @PathVariable("id")Long id){
        commentService.remove(id);
        return JsonResult.ok();
    }

    @ApiOperation("后台批量删除评论")
    @OperationLog("后台批量删除评论")
    @PreAuthorize("hasAuthority('blog:comment:delete')")
    @DeleteMapping
    public JsonResult removeList(@NotEmpty @RequestBody List<Long> idList){
        commentService.removeList(idList);
        return JsonResult.ok();
    }

    @ApiOperation("回复评论")
    @PreAuthorize("hasAuthority('blog:comment:reply')")
    @OperationLog("回复评论")
    @PostMapping
    public JsonResult reply(@Validated @RequestBody ReplyVO replyVO, HttpServletRequest request) {
        Comment comment = new Comment();
        comment.setPid(replyVO.getPid());
        comment.setArticleId(replyVO.getArticleId());
        comment.setContent(replyVO.getReply());
        comment.setBrowser(StringUtils.getBrowser(request));
        comment.setOs(StringUtils.getClientOS(request));
        comment.setRequestIp(StringUtils.getIp(request));
        comment.setAddress(StringUtils.getCityInfo(comment.getRequestIp()));
        comment.setStatus(Constant.AUDIT_WAIT);
        comment.setCreateTime(new Date());;
        comment.setUserId(UserInfoUtil.getUserId());
        commentService.reply(comment);
        return JsonResult.ok();
    }

    @ApiOperation("审核评论")
    @OperationLog("审核评论")
    @PreAuthorize("hasAuthority('blog:comment:audit')")
    @PutMapping("/audit")
    public JsonResult audit(@RequestBody AuditVO auditVO){
        commentService.audit(auditVO);
        return JsonResult.ok();
    }
}
