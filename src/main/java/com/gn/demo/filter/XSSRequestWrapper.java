package com.gn.demo.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @Auther: Lee
 * @Date: 2018-08-14
 * @Description: request的包装类
 */
@Slf4j
public class XSSRequestWrapper extends HttpServletRequestWrapper {

    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        //获取所有参数值的集合
        String[] results = this.getParameterMap().get(name);
        if (results != null && results.length > 0) {
            int length = results.length;
            for (int i = 0; i < length; i++) {
                //过滤参数值
                results[i] = HtmlUtils.htmlEscape(results[i]);
            }
            return results;
        }
        return null;
    }

}