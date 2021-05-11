package cn.fantasyblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Cy
 * @Date 2021-05-06 19:34
 */
@ApiModel("点赞")
@Data
@TableName("t_like")
@NoArgsConstructor
@AllArgsConstructor
public class Like implements Serializable {

    @ApiModelProperty("主键ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("文章ID")
    private Long articleId;

    @ApiModelProperty("访客ID")
    private Long visitorId;

    @ApiModelProperty("点赞状态[0:未点赞,1:点赞]")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    public Like(Long articleId,Long visitorId,Integer status){
        this.articleId = articleId;
        this.visitorId = visitorId;
        this.status = status;
    }

    public interface Table{
         String ID = "id";
         String ARTICLE_ID = "article_id";
         String VISITOR_ID = "visitor_id";
         String STATUS = "status";
    }
}
