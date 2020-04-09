package shopping.demo.Config.SpringMVC;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shopping.demo.Config.Interceptor.LoginInterceptor;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/createUser").setViewName("userInsert");
        registry.addViewController("/changePassword").setViewName("changePassword");
        registry.addViewController("/toInsert").setViewName("goodsInsert");
    }
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/adminHome.html");
    }
}
