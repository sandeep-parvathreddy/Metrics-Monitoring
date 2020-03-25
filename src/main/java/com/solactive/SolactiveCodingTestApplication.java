package com.solactive;

import com.solactive.util.LoggingInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class SolactiveCodingTestApplication extends WebMvcConfigurationSupport {

	public static void main(String[] args) {
		SpringApplication.run(SolactiveCodingTestApplication.class, args);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/**");
	}
}
