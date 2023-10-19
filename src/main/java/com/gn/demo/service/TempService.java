package com.gn.demo.service;/*
 * @Description:
 * @Author: GuiNing
 * @Date: 2023/4/25 15:17
 */

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface TempService {
    void getTemp(HttpServletResponse response);

    void simpleRead(MultipartFile file);
}
