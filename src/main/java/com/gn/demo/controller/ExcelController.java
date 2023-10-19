package com.gn.demo.controller;

import com.gn.demo.service.TempService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ExcelController {

    private final TempService tempService;

    public ExcelController(TempService tempService) {
        this.tempService = tempService;
    }

    @GetMapping("/getTemp")
    @ApiOperation("下载模板")
    public void getTemp(HttpServletResponse response) {
        tempService.getTemp(response);
    }

    @PostMapping(value = "/simpleRead", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("传入excel")
    public void simpleRead(@RequestPart("file") MultipartFile file) {
        tempService.simpleRead(file);
    }
}