package cn.fantasyblog.vo;

import cn.fantasyblog.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-22 20:18
 */
@ApiModel("后台主页数据")
@Data
public class IndexVO implements Serializable {
    @ApiModelProperty("文章数量")
    private Long articleCount;

    @ApiModelProperty("分类数量")
    private Long categoryCount;

    @ApiModelProperty("标签数量")
    private Long tagCount;

    @ApiModelProperty("评论数量")
    private Long commentCount;

    @ApiModelProperty("用户数量")
    private Long userCount;

    @ApiModelProperty("访客数量")
    private Long visitorCount;

    @ApiModelProperty("浏览量")
    private Long viewCount;

    @ApiModelProperty("留言数量")
    private Long messageCount;

    @ApiModelProperty("最近访问日志列表")
    private List<AccessLog> accessLogs;

    @ApiModelProperty("最近操作日志列表")
    private List<OperationLog> operationLogs;

    @ApiModelProperty("最近评论列表")
    private List<Comment> comments;

    @ApiModelProperty("最近留言列表")
    private List<Message> messages;

    @ApiModelProperty("最近文章列表")
    private List<Article> articles;

    @ApiModelProperty("前台流量日期统计")
    private List<ViewDateVO> frontViews;

    @ApiModelProperty("后台流量日期统计")
    private List<ViewDateVO> backViews;

    @ApiModelProperty("公告列表")
    private List<Notice> notices;
}
