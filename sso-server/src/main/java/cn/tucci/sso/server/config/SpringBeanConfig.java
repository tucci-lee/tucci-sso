package cn.tucci.sso.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author tucci.lee
 */
@Configuration
public class SpringBeanConfig {

    /**
     * json返回的long类型转换string，原因：前端long类型丢精度
     *
     * @return Customizer
     */
    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.serializerByType(Long.TYPE, ToStringSerializer.instance)
                .serializerByType(Long.class, ToStringSerializer.instance)
                .build();
    }

    @Bean
    public SimpleAsyncTaskExecutor simpleAsyncTaskExecutor(){
        return new SimpleAsyncTaskExecutor();
    }

    /**
     * filter级别的跨域处理
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> filterRegistrationBean() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
