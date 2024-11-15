package com.safetrade.safe_trade.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix="file.upload")
public class FileUploadConfig {
    private String path;
    private String url;
}
