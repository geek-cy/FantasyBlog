package cn.fantasyblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-03-21 14:21
 */
@ApiModel("文章")
@Data
@Accessors(chain = true)
@TableName("t_article")
//@JsonIgnoreProperties("handler")
public class Article implements Serializable {

    @ApiModelProperty("ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("标题")
    @NotBlank(message = "文章标题不能为空")
    @Length(max = 100, message = "文章标题长度不能超过100")
    private String title;

    @ApiModelProperty("摘要")
    private String summary;

    @ApiModelProperty("html内容")
    @NotBlank(message = "文章内容不能为空")
    private String content;

    @ApiModelProperty("markdown内容")
    private String textContent;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("类型：1:原创，2:转载，3:翻译")
    private Integer type;

    @ApiModelProperty("浏览量")
    private Integer views;

    @ApiModelProperty("点赞量")
    private Integer likes;

    @ApiModelProperty("评论量")
    private Integer comments;

    @ApiModelProperty("开启赞赏")
    private Boolean appreciable;

    @ApiModelProperty("开启评论")
    private Boolean commentable;

    @ApiModelProperty("开启置顶")
    private Boolean top;

    @ApiModelProperty("开启推荐")
    private Boolean recommend;

    @ApiModelProperty("是否发布")
    private Boolean published;

    @ApiModelProperty("排序评分")
    private Integer sort;

    @ApiModelProperty("作者ID")
    private Long authorId;

    @ApiModelProperty("分类ID")
    private Long categoryId;

    @ApiModelProperty("审核状态 0:审核未过，1:等待审核,2:审核通过")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    //外键
    @ApiModelProperty("分类")
    @TableField(exist = false)
    private Category category;

    @ApiModelProperty("标签ID列表")
    @TableField(exist = false)
    private List<Tag> tagList;

    @ApiModelProperty("作者")
    @TableField(exist = false)
    private User author;

    public interface Table {
        String ID = "id";
        String TITLE = "title";
        String SUMMARY = "summary";
        String CONTENT = "content";
        String TEXT_CONTENT = "text_content";
        String COVER = "cover";
        String TYPE = "type";
        String VIEWS = "views";
        String LIKES = "likes";
        String COMMENTS = "comments";
        String APPRECIABLE = "appreciable";
        String COMMENTABLE = "commentable";
        String TOP = "top";
        String RECOMMEND = "recommend";
        String PUBLISHED = "published";
        String SORT = "sort";
        String AUTHOR_ID = "author_id";
        String CATEGORY_ID = "category_id";
        String STATUS = "status";
        String CREATE_TIME = "create_time";
        String UPDATE_TIME = "update_time";
    }
}
