package cn.fantasyblog.service;

import cn.fantasyblog.entity.Visitor;

import java.util.List;

public interface ViewService {
    /**
     * 持久化浏览量
     * @param flag 是否持久化
     */
    Integer transViewCount(boolean flag);

}
