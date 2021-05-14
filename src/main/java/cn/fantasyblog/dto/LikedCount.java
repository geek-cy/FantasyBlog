package cn.fantasyblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 点赞数量 DTO。用于存储从 Redis 取出来的被点赞数量
 * @Author Cy
 * @Date 2021-05-06 22:23
 */
@Data
@AllArgsConstructor
public class LikedCount implements Serializable {

    private Long key;
    private Integer count;
}
