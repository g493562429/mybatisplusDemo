package com.gn.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @Author sarlin_gn
 * @Date 2021/12/6 10:02
 * @Desc
 */
@Slf4j
@Component
public class FileUtil {

    /**
     * 读取文件内容
     * @param path path
     * @return String
     */
    public static String readFile(String path) {
        String str;
        try {
            File file = new File(path);
            log.info("readFile.file:{}", file.getName());
            FileReader fileReader = new FileReader(file);
            Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            int ch;
            StringBuilder sb = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            str = sb.toString();
            return str;
        } catch (Exception e) {
            log.error("读取文件内容出错:", e);
            return null;
        }
    }
}
