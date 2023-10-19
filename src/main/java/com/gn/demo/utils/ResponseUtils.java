package com.gn.demo.utils;
/*
 * @Description: 使用response输出JSON
 * @Author: GuiNing
 * @Date: 2023/9/5 11:56
 */

import com.alibaba.fastjson.JSON;
import com.gn.demo.config.security.dto.output.ApiResult;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
public class ResponseUtils {


    /**
     * 使用response输出JSON
     *
     * @param response
     * @param result
     */
    public static void out(ServletResponse response, ApiResult result) {
        PrintWriter out = null;

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSONString(result));
        } catch (Exception e) {
            log.error(e + "输出JSON出错");
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 响应内容
     * @param httpServletResponse
     * @param msg
     * @param status
     */
    public static void getResponse(HttpServletResponse httpServletResponse, String msg, Integer status) {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = httpServletResponse.getWriter()) {
            writer.print(JSON.toJSONString(new ApiResult(status, msg, null)));
        } catch (Exception e) {
            log.error("响应报错:", e);
        }
    }
}
