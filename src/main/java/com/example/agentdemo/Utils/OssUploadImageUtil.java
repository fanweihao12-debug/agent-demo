package com.example.agentdemo.Utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.example.agentdemo.Config.OssProperties;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Component
public class OssUploadImageUtil {

    @Resource
    private OssProperties ossProperties;

    public String uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("上传文件不能为空");
        }

        String originalFilename = file.getOriginalFilename();

        String suffix = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String fileName = "images/" + UUID.randomUUID() + suffix;

        OSS ossClient = new OSSClientBuilder().build(
                ossProperties.getEndpoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret()
        );

        try (InputStream inputStream = file.getInputStream()) {
            ossClient.putObject(
                    ossProperties.getBucketName(),
                    fileName,
                    inputStream
            );
        } catch (Exception e) {
            throw new RuntimeException("图片上传失败", e);
        } finally {
            ossClient.shutdown();
        }

        return ossProperties.getUrlPrefix() + "/" + fileName;
    }
}
