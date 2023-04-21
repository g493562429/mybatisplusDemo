package com.gn.demo.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.demo.dto.PageDTO;

import java.util.List;

/**
 * @Author sarlin_gn
 * @Date 2021/5/8 15:39
 * @Desc 获取页面参数工具类
 */
public class PageUtil {

    /**
     * 根据PageDTO获取分页对象
     *
     * @param pageDTO pageDTO
     * @param <T>     <T>
     * @return <T>
     */
    public static <T> Page<T> getPage(PageDTO pageDTO) {
        if (pageDTO != null) {
            return getPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        }
        return new Page<>(1, 999);
    }

    /**
     * 获取分页对象
     *
     * @param pageNum  页码
     * @param pageSize 页面大小
     * @param <T>      <T>
     * @return <T>
     */
    private static <T> Page<T> getPage(Integer pageNum, Integer pageSize) {
        if (pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0) {
            return new Page<>(pageNum, pageSize);
        }
        return new Page<>(1, 999);
    }

    private static <T> Page<T> getPage(Integer pageNum, Integer pageSize, List<T> list) {
        Page<T> page = PageUtil.getPage(pageNum, pageSize);
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.size());
        page.setRecords(list.subList(startIndex, endIndex));
        return page;
    }

}
