package com.example.exam.config;

import com.example.exam.interceptor.PermissionInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfigs implements WebMvcConfigurer {

    private final Logger logger =  LoggerFactory.getLogger(WebConfigs.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOrigins("*")
                //是否允许证书 不再默认开启
                .allowCredentials(true)
                //设置允许的方法
                .allowedMethods("PUT","GET","DELETE","POST")
                .allowedHeaders("*")
                //跨域允许时间
                .maxAge(3600);
        logger.info(" [*** loading Cors mapping ***] <- operate method[addCorsMappings] <- class["+this.getClass().getName()+"] ");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PermissionInterceptor())
                .addPathPatterns("/exam/**")
                .excludePathPatterns("/exam/getCode", "/exam/login", "/exam/register", "/exam/check");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
