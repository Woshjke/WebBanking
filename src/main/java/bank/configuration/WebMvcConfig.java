package bank.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


/**
 * Spring MVC configuration
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "bank.controllers",
        "bank.model",
        "bank.model.entity",
        "bank.model.dao",
        "bank.services",
        "bank.configuration"
})

public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver vr = new InternalResourceViewResolver();
        vr.setPrefix("/WEB-INF/static/pages/");
        vr.setSuffix(".jsp");
        return vr;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/css/**")
                .addResourceLocations("/WEB-INF/resources/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/resources/img/**")
                .addResourceLocations("/WEB-INF/resources/img/").setCachePeriod(31556926);
        registry.addResourceHandler("/resources/js/**")
                .addResourceLocations("/WEB-INF/resources/js/").setCachePeriod(31556926);
    }
}
