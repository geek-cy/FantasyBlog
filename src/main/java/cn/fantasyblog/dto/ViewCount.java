package cn.fantasyblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Cy
 * @Date 2021-05-07 18:23
 */
@Data
@AllArgsConstructor
public class ViewCount implements Serializable {

    private Long key;
    private Integer count;
}
