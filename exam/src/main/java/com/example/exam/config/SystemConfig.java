package com.example.exam.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author siney
 * @createTime 2020-10-21
 **/
@Configuration
public class SystemConfig {

    @Value("${config.tmp-picture-path}")
    public String tmpPicturePath;

    @Value("${config.picture-path}")
    public String picturePath;

}
