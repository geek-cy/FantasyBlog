package cn.fantasyblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description
 * @Author Cy
 * @Date 2021/5/15 16:39
 */
@Data
@AllArgsConstructor
public class CommentCount {

    private Long key;
    private Integer count;
}
