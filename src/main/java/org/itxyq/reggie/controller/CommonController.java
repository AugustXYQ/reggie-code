package org.itxyq.reggie.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.itxyq.reggie.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @author xyq 13127
 * @version 1.0.0
 * @date 2023/9/3
 * @description 文件上传和下载
 **/
@Slf4j
@RestController
@Api(value = "/common", tags = "测试CommonController相关api")
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    @ApiOperation(value = "文件上传", notes = "文件上传")
    @ApiImplicitParam(name = "file", value = "MultipartFile", required = true, dataType = "MultipartFile")
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        //原始文件名
        String originalFileName = file.getOriginalFilename();
        //截出来会包含点
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));

        //使用UUID重新生成文件名,防止文件名重复造成文件覆盖
        String fileName = UUID.randomUUID() + suffix;
        //创建一个目录对象
        File dir = new File(basePath);
        if (!dir.exists()) {
            //目录不存在 则创建
            dir.mkdirs();
        }

        //file是一个临时文件,需要转存到指定位置,否则本次请求结束后文件会被删除
        try {
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(fileName);
    }

    @ApiOperation(value = "文件下载", notes = "文件下载")
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try (//输入流 通过输入流读取文件内容
             FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
             //输出流 通过输出流将文件写回浏览器 在浏览器展示图片
             ServletOutputStream outputStream = response.getOutputStream()) {
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
