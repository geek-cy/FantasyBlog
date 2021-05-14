package cn.fantasyblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Cy
 * @Date 2021/5/12 20:12
 */
@ApiModel("本地存储")
@Data
@Accessors(chain = true)
@TableName("sys_local_storage")
public class LocalStorage implements Serializable {

    @ApiModelProperty("主键:ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("真实文件名")
    @NotBlank
    private String realName;

    @ApiModelProperty("文件名")
    @NotBlank
    private String name;

    @ApiModelProperty("文件格式")
    @NotBlank
    private String suffix;

    @ApiModelProperty("路径")
    @NotBlank
    private String path;

    @ApiModelProperty("类型")
    @NotBlank
    private String type;

    @ApiModelProperty("大小")
    @NotBlank
    private String size;

    @ApiModelProperty("创建时间")
    @NotNull
    private Date createTime;

    @ApiModelProperty("更新时间")
    @NotNull
    private Date updateTime;

    public interface Table {
        String ID = "id";
        String REAL_NAME = "real_name";
        String NAME = "name";
        String SUFFIX = "suffix";
        String PATH = "path";
        String TYPE = "type";
        String SIZE = "size";
        String CREATE_TIME = "create_time";
        String UPDATE_TIME = "update_time";
    }
}
