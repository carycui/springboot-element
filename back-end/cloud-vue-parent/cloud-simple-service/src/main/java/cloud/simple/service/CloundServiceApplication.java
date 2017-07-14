package cloud.simple.service;

import cloud.simple.service.util.FastJsonMessageConverterFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

//@EnableDiscoveryClient
@SpringBootApplication
//@EnableEurekaClient
@MapperScan(basePackages={"cloud.simple.service.dao","com.framework.common.base"})
public class CloundServiceApplication  extends SpringBootServletInitializer{
	
    public static void main(String[] args) {
    	SpringApplication.run(CloundServiceApplication.class, args);
    }

    /**
     * 消息转换器机，SpringMVC使用消息转换器实现请求报文和对象、对象和响应报文之间的自动转换
     */
   /* @Bean
    public HttpMessageConverters configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        return new HttpMessageConverters(FastJsonMessageConverterFactory.createConverter());
    }*/

}	
