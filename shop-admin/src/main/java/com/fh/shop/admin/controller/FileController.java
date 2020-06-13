package com.fh.shop.admin.controller;

import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.common.SystemConstant;
import com.fh.shop.admin.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@RequestMapping("/file")
@Controller
public class FileController {

    @RequestMapping("/uploadImage")
    @ResponseBody
    public ServerResponse uploadImage(@RequestParam MultipartFile imageInfo, HttpServletRequest request) {
        // 将文件通过网络上传到服务器的硬盘上
        // 当你使用springMVC或者MVC框架的时候，上传的过程框架已经帮你做完了
        // 把框架上传后的临时文件拷贝到指定的文件夹下并且重命名
        // 1.自动创建文件夹    2.生成uuid的文件名并且加上后缀名     3.io流读取和写入
        // 相对路径 绝对路径
        // 开发环境的目录结构    编译部署后的目录结构
        // 如何根据相对路径获取绝对路径
        try {
            InputStream is = imageInfo.getInputStream();
            String filename = imageInfo.getOriginalFilename();
            String realPath = request.getServletContext().getRealPath(SystemConstant.UPLOAD_IMAGE_PATH);
            String uploadedFileName = FileUtil.copyFile(is, filename, realPath);
            return ServerResponse.success(SystemConstant.UPLOAD_IMAGE_PATH+uploadedFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }

    @RequestMapping("/uploadFile")
    @ResponseBody
    public ServerResponse uploadFile(@RequestParam MultipartFile fileInfo, HttpServletRequest request) {
        // 将文件通过网络上传到服务器的硬盘上
        // 当你使用springMVC或者MVC框架的时候，上传的过程框架已经帮你做完了
        // 把框架上传后的临时文件拷贝到指定的文件夹下并且重命名
        // 1.自动创建文件夹    2.生成uuid的文件名并且加上后缀名     3.io流读取和写入
        // 相对路径 绝对路径
        // 开发环境的目录结构    编译部署后的目录结构
        // 如何根据相对路径获取绝对路径
        try {
            InputStream is = fileInfo.getInputStream();
            String filename = fileInfo.getOriginalFilename();
            String uploadedFileName = FileUtil.copyFile(is, filename, SystemConstant.UPLOAD_FILE_PATH);
            return ServerResponse.success(SystemConstant.UPLOAD_FILE_PATH+uploadedFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }
}
