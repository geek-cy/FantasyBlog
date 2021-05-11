package cn.fantasyblog.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @Description
 * @Author Cy
 * @Date 2021-05-09 20:05
 */
/**
 * type : 字段数据类型
 * analyzer : 分词器类型 ik_max_word做最细粒度的拆分
 * index : 是否索引(默认:true)
 * Keyword : 短语,不进行分词
 */
@Data
@Document(indexName = "article_document",type = "docs",shards = 1,replicas = 0)
public class ArticleDocument implements Serializable {

    @Id
    private Long id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String summary;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String content;

    @Field(type = FieldType.Boolean)
    private Boolean published;

    @Field(type = FieldType.Integer)
    private Integer status;

    public interface Table {
        String ID = "id";
        String TITLE = "title";
        String SUMMARY = "summary";
        String CONTENT = "content";
        String PUBLISHED = "published";
        String STATUS = "status";
    }
}
