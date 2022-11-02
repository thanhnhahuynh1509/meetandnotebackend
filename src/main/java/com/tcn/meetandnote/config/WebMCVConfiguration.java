package com.tcn.meetandnote.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
@EnableWebMvc
public class WebMCVConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = "assets";
        String absolutePath = new File(location).getAbsolutePath();
        System.out.println(absolutePath);
        registry.addResourceHandler("/" + location + "/**").addResourceLocations("file:" + absolutePath + "/");
    }
}
