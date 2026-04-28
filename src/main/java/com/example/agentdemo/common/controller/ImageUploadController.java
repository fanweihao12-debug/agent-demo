package com.example.agentdemo.common.controller;

import com.example.agentdemo.Utils.OssUploadImageUtil;
import com.example.agentdemo.common.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/common")
public class ImageUploadController {

    @Resource
    private OssUploadImageUtil ossUploadImageUtil;

    @PostMapping("/image")
    public Result<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String imageUrl = ossUploadImageUtil.uploadImage(file);
        return Result.success(imageUrl);
    }
}
