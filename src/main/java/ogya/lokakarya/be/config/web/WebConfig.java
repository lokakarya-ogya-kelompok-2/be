package ogya.lokakarya.be.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ogya.lokakarya.be.config.web.interceptor.RequestTimingInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private RequestTimingInterceptor requestTimingInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(requestTimingInterceptor);
    }
}
