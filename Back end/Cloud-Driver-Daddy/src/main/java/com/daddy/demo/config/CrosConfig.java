package com.daddy.demo.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//解决跨域问题的代码
@Configuration
@ServletComponentScan(value = {"com.daddy.demo.filter"})
public class CrosConfig implements WebMvcConfigurer {
}


